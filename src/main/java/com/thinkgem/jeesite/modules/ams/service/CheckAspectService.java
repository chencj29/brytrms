/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import cn.net.metadata.datasync.socketio.ConcurrentClientsHolder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.modules.ams.dao.CheckAspectDao;
import com.thinkgem.jeesite.modules.ams.entity.CheckAspect;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.*;
import com.thinkgem.jeesite.modules.rms.rd.ResourceType;
import com.thinkgem.jeesite.modules.rms.service.FlightPlanPairService;
import com.thinkgem.jeesite.modules.rms.service.ResourceMockDistInfoService;
import com.thinkgem.jeesite.modules.rms.service.ResourceSharingService;
import com.thinkgem.jeesite.modules.rms.wrapper.AddProgressWrapper;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 指挥中心审核操作Service
 * <p>
 * 资源系统中的的审核是全部通过service层方法拦截处理的，所以在controller层的方法可以检测部分操作异常；
 *
 * @author wjp
 * @version 2017-03-20
 */
@Service
@Transactional(readOnly = true)
public class CheckAspectService extends CrudService<CheckAspectDao, CheckAspect> {

    public static String getOciNameFromMap(Object obj) {
        if (obj instanceof Map) {
            for (Map.Entry<String, Object> stringObjectEntry : ((Map<String, Object>) obj).entrySet()) {
                if (stringObjectEntry.getKey().indexOf("OccupyingInfo") > 0) {
                    return stringObjectEntry.getKey();
                }
            }
        }
        return null;
    }

    public static void runMethod(String ociSimpleName, Map objMap, ResourceSharingService rss, String method, FlightDynamic flightDynamic, Message message) {
        try {
            Class clazz = Class.forName("com.thinkgem.jeesite.modules.rms.entity." + ociSimpleName);
            Object o = JSONArray.toJavaObject((JSON) objMap.get(ociSimpleName), clazz);
            Reflections.setFieldValue(o, "random", UUID.randomUUID().toString());
            Reflections.invokeMethodByName(rss, method, new Object[]{flightDynamic, o, message});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Object getOldEntity(String ociSimpleName, Object oci) {
        try {
//            Class clazz = Class.forName("com.thinkgem.jeesite.modules.rms.service." + ociSimpleName + "Service");
//            return ((CrudService) SpringContextHolder.getBean(clazz)).get((DataEntity) oci);
            return ((CrudService) SpringContextHolder.getBean(StringUtils.uncapitalize(ociSimpleName) + "Service")).get((DataEntity) oci);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CheckAspect get(String id) {
        return super.get(id);
    }

    public List<CheckAspect> findList(CheckAspect checkAspect) {
        return super.findList(checkAspect);
    }

    public List<CheckAspect> findAllList(CheckAspect checkAspect) {
        return dao.findAllList(checkAspect);
    }

    public Page<CheckAspect> findPage(Page<CheckAspect> page, CheckAspect checkAspect) {
        return super.findPage(page, checkAspect);
    }

    @Transactional(readOnly = false)
    public void save(CheckAspect checkAspect) {
        super.save(checkAspect);
    }

    @Transactional(readOnly = false)
    public void delete(CheckAspect checkAspect) {
        super.delete(checkAspect);
    }

    @Transactional(readOnly = false)
    public void updateList(ArrayList<String> ids, String adviceFlag, String adviceBy, Message msg) {
        if (isCheck(ids)) {
            msg.setMsg(0, "审核无效！你选中的项目，已被审核过！");
            return;
        }
        //todo 解析调用相关方法发送日志
        if (StringUtils.equals("1", adviceFlag)) {
            for (String id : ids) {
                CheckAspect checkAspect = get(id);
                if (checkAspect != null) {
//					new Thread(new RunMethodThread(checkAspect)).start();
                    //不用线程
                    new RunMethodThread(checkAspect).run();
                    checkAspect.setAdviceFlag(adviceFlag);
                    checkAspect.setAdviceBy(adviceBy);
                    dao.update(checkAspect);
                }
            }
        } else {
            dao.updateList(ImmutableMap.of("ids", ids, "adviceFlag", adviceFlag, "adviceBy", adviceBy));
        }
    }

    /**
     * 判断是否已审核过
     *
     * @param ids
     * @return
     */
    public Boolean isCheck(ArrayList<String> ids) {
        int i = dao.isChackAll(ImmutableMap.of("ids", ids));
        return i == 0 ? false : true;
    }

    //保存审核
    @Transactional(readOnly = false)
    public void addCheck(CheckAspect checkAspect, Object[] arguments) {
        //new SaveCheckAspectThread(this,checkAspect,arguments).start();
        //不用线程
        new SaveCheckAspectThread(this, checkAspect, arguments).run();
    }

    /**
     * 反射运行方法，并添加日志
     */
    public static class RunMethodThread implements Runnable {
        private CheckAspect checkAspect;
        private ResourceSharingService resourceSharingService = SpringContextHolder.getBean(ResourceSharingService.class);
        private ResourceMockDistInfoService resourceMockDistInfoService = SpringContextHolder.getBean(ResourceMockDistInfoService.class);
        private FlightPlanPairService flightPlanPairService = SpringContextHolder.getBean(FlightPlanPairService.class);

        public RunMethodThread(CheckAspect checkAspect) {
            this.checkAspect = checkAspect;
        }

        @Override
        public void run() {
            String method = checkAspect.getMethod();
            Message message = new Message();
            message.setCode(3); //标记执行
            JSONObject obj = JSONArray.parseObject(checkAspect.getArguments());
            if (method.indexOf("Manual") > 0) {//手动分配
                if (StringUtils.equals("aircraftNumManual", method)) {
                    FlightPlanPair pair = JSONArray.toJavaObject((JSON) obj.get("FlightPlanPair"), FlightPlanPair.class);
                    String expectAircraftNum = (String) obj.get("expectAircraftNum");
                    GateOccupyingInfo oci = JSONArray.toJavaObject((JSON) obj.get("GateOccupyingInfo"), GateOccupyingInfo.class);
                    oci.setRandom(UUID.randomUUID().toString());
                    Reflections.invokeMethodByName(resourceSharingService, method, new Object[]{pair, oci, message, expectAircraftNum});
                } else {
                    FlightDynamic flightDynamic = JSONArray.toJavaObject((JSON) obj.get("FlightDynamic"), FlightDynamic.class);
                    String ociClassName = getOciNameFromMap(obj);
                    if (ociClassName != null)
                        runMethod(ociClassName, obj, resourceSharingService, method, flightDynamic, message);
                }
            } else if (method.indexOf("clear") == 0) { //取消分配
                FlightDynamic flightDynamic = JSONArray.toJavaObject((JSON) obj.get("FlightDynamic"), FlightDynamic.class);
                String ociClassName = getOciNameFromMap(obj);
                if (ociClassName != null)
                    runMethod(ociClassName, obj, resourceSharingService, method, flightDynamic, message);
            } else if (method.indexOf("Auto") > 0) { //自动分配
                ResourceType type = ResourceType.valueOf((String) obj.get("resourceType"));
                Map map = (Map) obj.get("map");
                User user = JSONArray.toJavaObject((JSON) map.get("currentUser"), User.class);
                map.put("currentUser", user);
                String packageId = (String) obj.get("packageId");
                Reflections.invokeMethodByName(resourceSharingService, method, new Object[]{type, map, packageId, message});
            } else if (method.indexOf("publishMockData") == 0) { //模拟分配发布
                List<ResourceMockDistDetail> list = JSON.parseArray(obj.get("list").toString(), ResourceMockDistDetail.class);
                Reflections.invokeMethodByName(resourceMockDistInfoService, method, new Object[]{list, "wjpMockFlag"});
            } else if (method.equals("addProgress")) { //添加保障过程
                AddProgressWrapper wrapper = JSONArray.toJavaObject((JSON) obj.get("wrapper"), AddProgressWrapper.class);
                Reflections.invokeMethodByName(flightPlanPairService, method, new Object[]{wrapper, message});
            } else if (method.equals("modifySafeguardTypeCode")) {  //更新保障类型
                FlightPlanPair pair = JSONArray.toJavaObject((JSON) obj.get("FlightPlanPair"), FlightPlanPair.class);
                String safeguardTypeCode = (String) obj.get("safeguardTypeCode");
                String safeguardTypeName = (String) obj.get("safeguardTypeName");
                Reflections.invokeMethodByName(flightPlanPairService, method, new Object[]{pair, safeguardTypeCode, safeguardTypeName, message});
            } else if (method.indexOf("set") == 0 && method.indexOf("Time") > 0) { //设置实际占用时间
                FlightDynamic flightDynamic = JSONArray.toJavaObject((JSON) obj.get("FlightDynamic"), FlightDynamic.class);
                String ociClassName = getOciNameFromMap(obj);
                if (ociClassName != null)
                    runMethod(ociClassName, obj, resourceSharingService, method, flightDynamic, message);
            }

            //保存日志
            LogUtils.type2Save(checkAspect.getLog().getId(), message.getMessage());
        }
    }

    /**
     * 保存审核线程
     */
    public static class SaveCheckAspectThread extends Thread {
        private CheckAspectService checkAspectService;
        private CheckAspect checkAspect;
        private Object[] arguments;
        private ResourceMockDistInfoService resourceMockDistInfoService = SpringContextHolder.getBean(ResourceMockDistInfoService.class);
        private FlightPlanPairService flightPlanPairService = SpringContextHolder.getBean(FlightPlanPairService.class);

        public SaveCheckAspectThread(CheckAspectService checkAspectService, CheckAspect checkAspect, Object[] arguments) {
            super(SaveCheckAspectThread.class.getSimpleName());
            this.checkAspectService = checkAspectService;
            this.checkAspect = checkAspect;
            this.arguments = arguments;
        }

        @Override
        public void run() {
            String methodName = checkAspect.getMethod();
            Map<String, Object> map = new HashedMap();
            FlightDynamic flightDynamic = null;
            //处理参数
            for (int i = 0; i < arguments.length; i++) {
                //Manual
                if (methodName.indexOf("Manual") > 0) {  //手动分配
                    if (StringUtils.equals(methodName, "aircraftNumManual")) {//机位
                        if (arguments[i] instanceof FlightPlanPair) {
                            FlightPlanPair pair = (FlightPlanPair) arguments[i];
                            flightDynamic = new FlightDynamic();
                            flightDynamic.setId(pair.getId());   //临时将pairid设为表中动态存储的id
                            if (arguments[i] != null) map.put(arguments[i].getClass().getSimpleName(), arguments[i]);
                            setFlightOrPairAttr(checkAspect, pair);
                        } else if (arguments[i].getClass().getSimpleName().indexOf("OccupyingInfo") > 0) {
                            if (arguments[i] != null) map.put(arguments[i].getClass().getSimpleName(), arguments[i]);
                        } else if (arguments[i] instanceof String) {
                            map.put("expectAircraftNum", arguments[i]);
                        }
                    } else if (arguments[i] instanceof FlightDynamic) {
                        flightDynamic = (FlightDynamic) arguments[i];
                        if (arguments[i] != null) map.put(arguments[i].getClass().getSimpleName(), arguments[i]);
                        setFlightOrPairAttr(checkAspect, flightDynamic);
                    } else if (arguments[i].getClass().getSimpleName().indexOf("OccupyingInfo") > 0) {
                        if (arguments[i] != null) map.put(arguments[i].getClass().getSimpleName(), arguments[i]);
                    }
                } else if (methodName.indexOf("clear") == 0) { //取消分配
                    if (StringUtils.equals(methodName, "clearAircraftNum")) {
                        if (arguments[i] instanceof FlightPlanPair) {
                            flightDynamic = new FlightDynamic();
                            flightDynamic.setId(((FlightPlanPair) arguments[i]).getId());   //临时将pairid设为表中动态存储的id
                            if (arguments[i] != null) map.put(arguments[i].getClass().getSimpleName(), arguments[i]);
                            setFlightOrPairAttr(checkAspect, arguments[i]);
                        } else if (arguments[i].getClass().getSimpleName().indexOf("OccupyingInfo") > 0) {
                            if (arguments[i] != null) map.put(arguments[i].getClass().getSimpleName(), arguments[i]);
                        }
                    } else if (arguments[i] instanceof FlightDynamic) {
                        flightDynamic = (FlightDynamic) arguments[i];
                        setFlightOrPairAttr(checkAspect, arguments[i]);
                        if (arguments[i] != null) map.put(arguments[i].getClass().getSimpleName(), arguments[i]);
                    } else if (arguments[i].getClass().getSimpleName().indexOf("OccupyingInfo") > 0) {
                        if (arguments[i] != null) map.put(arguments[i].getClass().getSimpleName(), arguments[i]);
                    }
                } else if (methodName.indexOf("Auto") > 0) { //自动分配
                    if (arguments[i] instanceof ResourceType) {
                        if (arguments[i] != null) map.put("resourceType", arguments[i]);
                    } else if (arguments[i] instanceof Map) {
                        if (arguments[i] != null) map.put("map", arguments[i]);
                    } else if (arguments[i] instanceof String) {
                        if (arguments[i] != null) map.put("packageId", arguments[i]);
                    }
                } else if (methodName.indexOf("publishMockData") == 0) { //模拟分配发布
                    if (arguments[i] instanceof ArrayList) {
                        if (arguments[i] != null) map.put("list", arguments[i]);
                    }
                } else if (methodName.equals("addProgress")) { //添加保障过程
                    if (arguments[i] instanceof AddProgressWrapper) {
                        String pairId = ((AddProgressWrapper) arguments[i]).getPairId();
                        FlightPlanPair pair = flightPlanPairService.get(pairId);
                        setFlightOrPairAttr(checkAspect, pair);
                        flightDynamic = new FlightDynamic();
                        flightDynamic.setId(pairId);   //临时将pairid设为表中动态存储的id
                        if (arguments[i] != null) map.put("wrapper", arguments[i]);
                        if (pair != null) map.put("FlightPlanPair", pair);
                    }
                } else if (methodName.equals("modifySafeguardTypeCode")) {  //更新保障类型
                    if (arguments.length == 4) {
                        flightDynamic = new FlightDynamic();
                        flightDynamic.setId(((FlightPlanPair) arguments[0]).getId());   //临时将pairid设为表中动态存储的id
                        setFlightOrPairAttr(checkAspect, arguments[0]);
                        map.put("FlightPlanPair", arguments[0]);
                        map.put("safeguardTypeCode", arguments[1]);
                        map.put("safeguardTypeName", arguments[2]);
                    }
                } else if (methodName.indexOf("set") == 0 && methodName.indexOf("Time") > 0) { //设置实际占用时间
                    if (arguments[i] instanceof FlightDynamic) {
                        flightDynamic = (FlightDynamic) arguments[i];
                        setFlightOrPairAttr(checkAspect, flightDynamic);
                        if (arguments[i] != null) map.put(arguments[i].getClass().getSimpleName(), arguments[i]);
                    } else if (arguments[i].getClass().getSimpleName().indexOf("OccupyingInfo") > 0) {
                        if (arguments[i] != null) map.put(arguments[i].getClass().getSimpleName(), arguments[i]);
                    }
                }
            }

            checkAspect.setArguments(JsonMapper.toJsonString(map));
            checkAspect.setFlightDynamic(flightDynamic);

            //处理动作描述
            if (methodName.indexOf("Auto") > 0) { //自动分配
                Map params = (Map) map.get("map");
                String uruleName = UruleUtils.getNameById((String) map.get("packageId"));
                checkAspect.setExt1(StringUtils.tpl("[{}]分配时间：{} ~ {}", uruleName, String.valueOf(params.get("startTime")), String.valueOf(params.get("endTime"))));
            } else if (methodName.indexOf("clear") == 0) {
                String ociName = getOciNameFromMap(map);
                StringBuffer sb = new StringBuffer("清除");
                if (StringUtils.equals(methodName, "clearAircraftNum")) {
                    sb.append(LogUtils.msgPair((FlightPlanPair) map.get("FlightPlanPair")));
                } else {
                    sb.append(LogUtils.msgFlightDynamic(flightDynamic));
                }
                sb.append(" 的占用[").append(map.get(ociName).toString()).append("]！");
                checkAspect.setExt1(sb.toString());
            } else if (methodName.indexOf("Manual") > 0) {  //手动分配
                StringBuffer sb = new StringBuffer("手动分配");
                if (StringUtils.equals(methodName, "aircraftNumManual")) {//机位
                    sb.append(LogUtils.msgPair((FlightPlanPair) map.get("FlightPlanPair")));
                    sb.append("<br>[机位变动前：").append(StringUtils.toStr(((FlightPlanPair) map.get("FlightPlanPair")).getPlaceNum2())).append(",");
                    sb.append("变动后：").append(map.get("expectAircraftNum")).append("]");
                } else {
                    sb.append(LogUtils.msgFlightDynamic(flightDynamic));
                    sb.append("<br>[");
                    String ociName = getOciNameFromMap(map);
                    if (StringUtils.isNotBlank(ociName)) {
                        Object newData = map.get(ociName);
                        Object oldData = getOldEntity(ociName, newData);
                        if (newData != null && oldData != null) {
                            List<String> changeList = new ArrayList<>();
                            if (ObjectUtils.toComp(newData, oldData, changeList))
                                sb.append(StringUtils.join(changeList, "<br>"));
                        }
                    }
                    sb.append("]");
                }
                checkAspect.setExt1(sb.toString());
            } else if (methodName.indexOf("publishMockData") == 0) { //模拟分配发布
                List<ResourceMockDistDetail> list = (List<ResourceMockDistDetail>) map.get("list");
                ResourceMockDistInfo rmdInfo = resourceMockDistInfoService.get(list.get(0).getInfoId());
                if (rmdInfo != null) checkAspect.setExt1(rmdInfo.toString().replace(",", "<br>"));
            } else if (methodName.equals("addProgress")) { //添加保障过程
                AddProgressWrapper wrapper = (AddProgressWrapper) map.get("wrapper");
                FlightPlanPair pair = flightPlanPairService.get(wrapper.getPairId());
                StringBuffer msg = new StringBuffer(pair != null ? LogUtils.msgPair(pair) : "").append(",过程详情[");
                for (SafeguardProcess progress : wrapper.getInfos()) {
                    FlightPairProgressInfo info = new FlightPairProgressInfo();
                    info.setPairId(wrapper.getPairId());
                    info.setProgressCode(progress.getSafeguardProcessCode());
                    info.setProgressName(progress.getSafeguardProcessName());
                    info.setProgressRefId(progress.getId());
                    try {
                        info.setPlanStartTime(DateUtils.parseDate(progress.getRedundant1(), "yyyy-MM-dd HH:mm"));
                        info.setPlanOverTime(DateUtils.parseDate(progress.getRedundant2(), "yyyy-MM-dd HH:mm"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    msg.append("(").append(progress.toString()).append("),");
                }
                msg.append("]");
                checkAspect.setExt1(msg.toString());
            } else if (methodName.equals("modifySafeguardTypeCode")) {  //更新保障类型
                if (arguments.length == 4) {
                    FlightPlanPair flightPlanPair = (FlightPlanPair) map.get("FlightPlanPair");
                    String safeguardTypeCode = (String) map.get("safeguardTypeCode");
                    String safeguardTypeName = (String) map.get("safeguardTypeName");
                    checkAspect.setExt1(LogUtils.msgPair(flightPlanPair) + " 更新保障类型为：" + safeguardTypeName + "(" + safeguardTypeCode + ")");
                }
            } else if (methodName.indexOf("set") == 0 && methodName.indexOf("Time") > 0) { //设置实际占用时间
                StringBuffer sb = new StringBuffer("设置实际占用时间");
                sb.append(LogUtils.msgFlightDynamic(flightDynamic));
                sb.append("<br>[");
                String ociName = getOciNameFromMap(map);
                if (StringUtils.isNotBlank(ociName)) {
                    Object newData = map.get(ociName);
                    Object oldData = getOldEntity(ociName, newData);
                    if (newData != null && oldData != null) {
                        List<String> changeList = new ArrayList<>();
                        if (ObjectUtils.toComp(newData, oldData, changeList))
                            sb.append(StringUtils.join(changeList, "<br>"));
                    }
                }
                sb.append("]");
                checkAspect.setExt1(sb.toString());
            }

            checkAspectService.save(checkAspect);
            //发送资源分配审核消息
            ConcurrentClientsHolder.getSocket("/resourceCheck").emit("resourceCheck", "check");
        }
    }

    /**
     * 保存航班相关属性
     *
     * @param checkAspect
     * @param obj
     */
    public static void setFlightOrPairAttr(CheckAspect checkAspect, Object obj) {
        if (obj instanceof FlightPlanPair) {
            FlightPlanPair pair = (FlightPlanPair) obj;
            String flightNum = pair.getFlightCompanyCode() + pair.getFlightNum();
            String inoutType = "进港";
            if (pair.getFlightDynimicId() != null && pair.getFlightDynimicId2() != null) {
                flightNum += pair.getFlightCompanyCode2() + pair.getFlightNum2();
                inoutType += " 出港";
            } else if (pair.getFlightDynimicId2() != null) {
                flightNum = pair.getFlightCompanyCode2() + pair.getFlightNum2();
                inoutType = "出港";
            }
            checkAspect.setFlightNum(flightNum);
            checkAspect.setInoutTypeName(inoutType);
            checkAspect.setPlanDate(pair.getPlanDate());
        } else if (obj instanceof FlightDynamic) {
            FlightDynamic flightDynamic = (FlightDynamic) obj;
            String flightNum = flightDynamic.getFlightCompanyCode() + flightDynamic.getFlightNum();
            String inoutType = "J".equals(flightDynamic.getInoutTypeCode()) ? "进港" : "出港";
            checkAspect.setFlightNum(flightNum);
            checkAspect.setInoutTypeName(inoutType);
            checkAspect.setPlanDate(flightDynamic.getPlanDate());
        }
    }
}