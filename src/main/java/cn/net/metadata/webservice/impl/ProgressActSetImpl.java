package cn.net.metadata.webservice.impl;

import cn.net.metadata.datasync.socketio.ConcurrentClientsHolder;
import cn.net.metadata.webservice.ProgressActSetService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.modules.ams.entity.AocThirdParty;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.FlightPairWrapper;
import com.thinkgem.jeesite.modules.ams.service.AocThirdPartyService;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.dao.FlightPairProgressInfoDao;
import com.thinkgem.jeesite.modules.rms.dao.SafeguardProcessDao;
import com.thinkgem.jeesite.modules.rms.entity.FlightPairProgressInfo;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardProcess;
import com.thinkgem.jeesite.modules.rms.service.FlightPlanPairService;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jackson.map.util.BeanUtil;
import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.text.ParseException;
import java.util.*;

@WebService
public class ProgressActSetImpl implements ProgressActSetService {
    private static final String RESPONSE_SUCCESS = "success";
    private static final String RESPONSE_FAIL = "fail";
    private static final String DATE_FMT_12 = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FMT_14 = "yyyyMMddHHmmss";
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private FlightDynamicService flightDynamicService = SpringContextHolder.getBean(FlightDynamicService.class);
    private AocThirdPartyService aocThirdPartyService = SpringContextHolder.getBean(AocThirdPartyService.class);
    private SafeguardProcessDao safeguardProcessDao = SpringContextHolder.getBean(SafeguardProcessDao.class);
    private FlightPairProgressInfoDao flightPairProgressInfoDao = SpringContextHolder.getBean(FlightPairProgressInfoDao.class);
    private FlightPlanPairService flightPlanPairService = SpringContextHolder.getBean(FlightPlanPairService.class);
    private final static PropertiesLoader REALTIME_DYNAMIC_MESSAGE_CONFS = new PropertiesLoader("classpath:notification.realtime-dynamic-message-conf/config.properties");

    private static Logger logger = LoggerFactory.getLogger(ProgressActSetImpl.class);

    @Override
    @WebMethod
    public String setProgressAct(@WebParam(name = "message") String message) {
        Map<String, Object> result = null;
        try {
            result = validate(message);
        } catch (IllegalArgumentException e) {
            return createDocument(false, e.getMessage());
        }

        if (result.get("flightDynamic") != null) {
            FlightDynamic flightDynamic = (FlightDynamic) result.get("flightDynamic");
            FlightPairProgressInfo infoParms = (FlightPairProgressInfo) result.get("flightPairProgressInfo");

            String res = updataProgressInfo(infoParms, flightDynamic);
            if (StringUtils.isNotBlank(res)) return res;

        } else {
            String res = fdSafeguardProcessNullTo(result, "modify", "更新保障进程");
            if (StringUtils.isNotBlank(res)) return res;
        }

        logger.info("保障进程设置成功！");
        return createDocument();
    }

    private String updataProgressInfo(FlightPairProgressInfo infoParms, FlightDynamic flightDynamic) {
        try {
            String pairId = flightDynamicService.getPairIdByDynamic(flightDynamic);
            if (StringUtils.isBlank(pairId)) return createDocument(false, "查询配对数据出错!");
            infoParms.setPairId(pairId);

            FlightPairProgressInfo progressInfo = flightPairProgressInfoDao.getProgressInfo(infoParms);
            if (progressInfo == null)
                return createDocument(false, "没有找到" + LogUtils.msgFlightDynamic(flightDynamic) + "相关保障进程");

            if (infoParms.getActualStartTime() != null) {
                String oldStart = DateUtils.formatDate(progressInfo.getActualStartTime(), "yyyy-MM-dd HH:mm");
                progressInfo.setActualStartTime(infoParms.getActualStartTime());
                if (StringUtils.equals(oldStart, DateUtils.formatDate(progressInfo.getActualStartTime(), "yyyy-MM-dd HH:mm")))
                    progressActTimeMessageSender("PROGRESS_ACTUAL_START_TIME_RECORDING", progressInfo, oldStart);
            }
            if (infoParms.getActualOverTime() != null) {
                String oldOver = DateUtils.formatDate(progressInfo.getActualOverTime());
                progressInfo.setActualOverTime(infoParms.getActualOverTime());
                if (StringUtils.equals(oldOver, DateUtils.formatDate(progressInfo.getActualStartTime(), "yyyy-MM-dd HH:mm")))
                    progressActTimeMessageSender("PROGRESS_ACTUAL_OVER_TIME_RECORDING", progressInfo, oldOver);
            }
            if (infoParms.getOpName() != null) progressInfo.setOpName(infoParms.getOpName());
            if (infoParms.getSpecialCarCode() != null) progressInfo.setSpecialCarCode(infoParms.getSpecialCarCode());
            if (infoParms.getSpecialCarType() != null) progressInfo.setSpecialCarType(infoParms.getSpecialCarType());

            progressInfo.setRandom(UUID.randomUUID().toString());
            flightPairProgressInfoDao.update(progressInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return createDocument(false, "更新保障进程错误");
        }
        return "";
    }


    private Map<String, Object> validate(String message) throws IllegalArgumentException {
        Map<String, Object> result = new HashedMap();
        if (StringUtils.isEmpty(message)) throw new IllegalArgumentException("JSON文件内容为空，无法解析");
        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(message);
            String flightDynamicId = (String) jsonObject.get("id");  // TODO: 要根据更详情的信息得到ID，这个ID就是RMS系统中的动态ID

            //获取航班动态id，如果动态id为空时，记录：航班号，航空公司；否则获取航班动态对象
            if (!StringUtils.isEmpty(flightDynamicId)) {

                FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId.trim());
                if (null == flightDynamic) throw new IllegalArgumentException("不存在的航班记录{ID=" + flightDynamicId + "}");

                result.put("flightDynamic", flightDynamic);
            } else {
                //获取航空公司
                String flightCompanyCode = (String) jsonObject.get("flightCompanyCode");
                if (StringUtils.isBlank(flightCompanyCode)) throw new IllegalArgumentException("获取的航空公司为空！");

                //获取航班号
                String flightNum = (String) jsonObject.get("flightNum");
                if (StringUtils.isBlank(flightNum)) throw new IllegalArgumentException("获取的航班号为空！");

                Date planDate = null;
                String planDateStr = (String) jsonObject.get("planDate");
                if (StringUtils.isNotBlank(planDateStr)) {
                    if (planDateStr.contains("-")) {
                        planDate = DateUtils.parseDate(planDateStr, "yyyy-MM-dd");
                    } else {
                        planDate = DateUtils.parseDate(planDateStr, "yyyyMMdd");
                    }
                }

                FlightDynamic fdTmp = new FlightDynamic();
                fdTmp.setFlightNum(flightNum);
                fdTmp.setFlightCompanyCode(flightCompanyCode);
                //获取飞机号
                fdTmp.setAircraftNum((String) jsonObject.get("aircraftNum"));
                //进出港类型
                fdTmp.setInoutTypeCode((String) jsonObject.get("inoutTypeCode"));
                fdTmp.setPlanDate(planDate);

                result.put("flightDynamic", null);
                result.put("flightDynamicPrams", fdTmp);
            }

            String safeguardProcessCode = (String) jsonObject.get("safeguardProcessCode");
            String start = (String) jsonObject.get("start");
            String over = (String) jsonObject.get("over");
            String opName = (String) jsonObject.get("opName");
            String carType = (String) jsonObject.get("carType");
            String carCode = (String) jsonObject.get("carCode");
            String ext1 = (String) jsonObject.get("ext1");

            if (StringUtils.isBlank(safeguardProcessCode)) throw new IllegalArgumentException("保障进程编码为空");
            //result.put("safeguardProcessCode", safeguardProcessCode);

            if (StringUtils.isBlank(start) && StringUtils.isBlank(over))
                throw new IllegalArgumentException("实际开始、结束时间都为空");

            FlightPairProgressInfo info = new FlightPairProgressInfo();

            if (StringUtils.isNotBlank(start)) {
                if (start.contains("-")) info.setActualStartTime(DateUtils.parseDate(start, DATE_FMT_12));
                else info.setActualStartTime(DateUtils.parseDate(start, DATE_FMT_14));
            }
            if (StringUtils.isNotBlank(over)) {
                if (over.contains("-")) info.setActualOverTime(DateUtils.parseDate(over, DATE_FMT_12));
                else info.setActualOverTime(DateUtils.parseDate(over, DATE_FMT_14));
            }

            SafeguardProcess safeguardProcessPrams = new SafeguardProcess();
            safeguardProcessPrams.setSafeguardProcessCode(safeguardProcessCode);
            //通过保障过程code查 相关过程
            List<SafeguardProcess> safeguardProcessList = safeguardProcessDao.findListByCodeOrName(safeguardProcessPrams);
            if (safeguardProcessList.isEmpty()) throw new IllegalArgumentException("没找到提供的保障进程编码!");
            //有多个保障进程只取第一个  wjp_2017年8月23日15时03分
            info.setProgressRefId(safeguardProcessList.get(0).getId());
            info.setProgressName(safeguardProcessList.get(0).getSafeguardProcessName());

            info.setOpName(opName);
            info.setSpecialCarType(carType);
            info.setSpecialCarCode(carCode);
            info.setProgressCode(safeguardProcessCode);

            result.put("flightPairProgressInfo", info);
            result.put("ext1", ext1);

        } catch (ParseException e) {
            throw new IllegalArgumentException("无法解决的时间格式，预期的格式为TIME_14、TIME_12");
        }
        return result;
    }

    public static String createDocument(boolean success, String exception) {
        Document document = DocumentHelper.createDocument();
        Element rootElement = document.addElement("response");
        Element timeElement = rootElement.addElement("time");
        Element resultElement = rootElement.addElement("result");

        timeElement.setText(DateUtils.formatDate(new Date(), "yyyyMMddHHmmss"));
        if (success) resultElement.setText(RESPONSE_SUCCESS);
        else {
            resultElement.setText(RESPONSE_FAIL);
            Element exceptionElement = rootElement.addElement("exception");
            exceptionElement.setText(exception);
        }

        document.setXMLEncoding("UTF-8");
        return document.asXML();
    }

    public static String createDocument() {
        return createDocument(true, null);
    }

    //将空管消息中航班动态id为空的消息转为通过航班号及航空公司的航班
    public String fdSafeguardProcessNullTo(Map<String, Object> result, String methodName, String desc) {
        FlightDynamic flightDynamicPrams = (FlightDynamic) result.get("flightDynamicPrams");
        List<FlightDynamic> flightDynamics = flightDynamicService.findListSimple(flightDynamicPrams);
        if (Collections3.isEmpty(flightDynamics)) return createDocument(false, "没有找到相关的航班号和航空公司对应的动态!");

        FlightPairProgressInfo info = (FlightPairProgressInfo) result.get("flightPairProgressInfo");

        if (flightDynamics.size() == 1) {
            try {
                String res = updataProgressInfo(info, flightDynamics.get(0));
                if (StringUtils.isNotBlank(res)) return res;
            } catch (Exception e) {
                return createDocument(false, "更新航班失败：" + e.getMessage());
            }
        } else {//添加 空管消息多条数据 需审核
            //保障类型名称
            String spName = info.getProgressName() + "(" + info.getProgressCode() + ")";

            // 消息描述
            String msgDesc = StringUtils.tpl("修改航班{}{}的保障过程{},实际开始时间为{},实际结束时间为{}", flightDynamicPrams.getFlightCompanyCode(),
                    flightDynamicPrams.getFlightNum(), spName, StringUtils.toStr(info.getActualStartTime()), StringUtils.toStr(info.getActualOverTime()));

            AocThirdParty aocThirdParty = new AocThirdParty();
            aocThirdParty.setMethod(methodName);
            aocThirdParty.setDescription(msgDesc);
            aocThirdParty.setFlightCompanyCode(flightDynamicPrams.getFlightCompanyCode());
            aocThirdParty.setFlightNum(flightDynamicPrams.getFlightNum());
            aocThirdParty.setSendTime(new Date());
            aocThirdParty.setExt1(toJson(flightDynamics, info));
            aocThirdParty.setUpdateType("");
            try {
                aocThirdParty.setExt3(OBJECT_MAPPER.writeValueAsString(info));
            } catch (JsonProcessingException e) {
                logger.error("保障进程对象转json错误！" + e.getMessage());
                //e.printStackTrace();
                //return createDocument(false, "保障进程对象转json错误！");
            }
            aocThirdPartyService.save(aocThirdParty);

            ConcurrentClientsHolder.getSocket("/flightDynamicManageCheck").emit("flightDynamicManageCheck", "aocTPRMS");
        }
        return createDocument();
    }

    //将匹配上的的航班动态信息转为JSON数据 并验证是否有查得到保障过程
    public String toJson(List<FlightDynamic> flightDynamics, FlightPairProgressInfo infoParms) {
        if (!Collections3.isEmpty(flightDynamics)) {
            List<Map<String, String>> jsonFlight = new ArrayList<>();
            for (FlightDynamic f : flightDynamics) {
                String pairId = flightDynamicService.getPairIdByDynamic(f);
                if (StringUtils.isBlank(pairId)) continue;
                FlightPairProgressInfo tmpInfo = new FlightPairProgressInfo();
                BeanUtils.copyProperties(infoParms, tmpInfo);
                tmpInfo.setPairId(pairId);

                FlightPairProgressInfo progressInfo = flightPairProgressInfoDao.getProgressInfo(tmpInfo);
                if (progressInfo == null) continue;

                Map<String, String> mapFlight = new HashMap<>();
//                mapFlight.put("id", f.getId());
                mapFlight.put("id", pairId);  //将id设置pairId 最后再设置到时程中
                mapFlight.put("planDate", DateUtils.formatDate(f.getPlanDate()));
                mapFlight.put("inoutTypeCode", f.getInoutTypeCode());
                mapFlight.put("flightNum", f.getFlightCompanyCode() + f.getFlightNum());
                mapFlight.put("desc", "");
                jsonFlight.add(mapFlight);
            }

            return JSONArray.toJSONString(jsonFlight);
        }
        return "";
    }

    private void progressActTimeMessageSender(String fmtName, FlightPairProgressInfo info, String time) {
        FlightPlanPair flightPlanPair = flightPlanPairService.get(info.getPairId());

        if (flightPlanPair != null) {
            String flightNumber = "";
            if (StringUtils.isNotEmpty(flightPlanPair.getFlightNum2()))
                flightNumber += flightPlanPair.getFlightCompanyCode2() + flightPlanPair.getFlightNum2();
            if (StringUtils.isNotEmpty(flightPlanPair.getFlightNum2()) && StringUtils.isNotEmpty(flightPlanPair.getFlightNum()))
                flightNumber += "/";
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(flightPlanPair.getFlightNum()))
                flightNumber += flightPlanPair.getFlightCompanyCode() + flightPlanPair.getFlightNum();

            Map<String, Object> params = new HashMap<>();
            params.put("occur", System.currentTimeMillis());
            params.put("message", String.format(REALTIME_DYNAMIC_MESSAGE_CONFS.getProperty(fmtName), flightNumber, info.getProgressName(), time));

            try {
                ConcurrentClientsHolder.getSocket("/dynamic_realtime_message").emit("dynamic_message_dispatcher", new ObjectMapper().writeValueAsString(params));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}