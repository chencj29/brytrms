/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.YesterAircraftNumPair;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.YesterAircraftNumPairService;
import com.thinkgem.jeesite.modules.rms.entity.RmsEmp;
import com.thinkgem.jeesite.modules.rms.service.RmsEmpService;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 工作人员基础信息Controller
 *
 * @author wjp
 * @version 2016-03-10
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/stopPlane")
public class StopPlaneController extends BaseController {

    @Autowired
    private YesterAircraftNumPairService yesterAircraftNumPairService;

    @RequiresPermissions("rms:stopPlane:view")
    @RequestMapping("view")
    public String view() {
        return "rms/rs/stop_plane";
    }

    @RequiresPermissions("rms:stopPlane:view")
    @RequestMapping("get")
    @ResponseBody
    public YesterAircraftNumPair get(@RequestParam("id") String id) {
        YesterAircraftNumPair numPair = yesterAircraftNumPairService.get(id);
        if (!"1".equals(numPair.getPlaceStatus())) {
            numPair.setPlaceStatus("0");
        }
        return numPair;
    }

    @RequiresPermissions("rms:stopPlane:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<YesterAircraftNumPair> list(YesterAircraftNumPair yesterAircraftNumPair) {
        return new DataGrid<>(yesterAircraftNumPairService.findListByPlanDate(yesterAircraftNumPair));
    }

    @RequiresPermissions("rms:stopPlane:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(YesterAircraftNumPair numPair, Model model, Message message, HttpServletRequest request) {
        if (!beanValidator(model, numPair)) {
            message.setMessage("数据校验错误!");
        }

        try {
//            if (StringUtils.isNotBlank(numPair.getId())) {
//                YesterAircraftNumPair yesterAircraftNumPair = yesterAircraftNumPairService.get(numPair);
//                if (!StringUtils.equals("1", numPair.getPlaceStatus())) {
//                    yesterAircraftNumPair.setPlaceStatus(null);
//                } else {
//                    yesterAircraftNumPair.setPlaceStatus(numPair.getPlaceStatus());
//                }
//                yesterAircraftNumPairService.save(yesterAircraftNumPair);
//                message.setCode(1);
//            }
            if (StringUtils.isNotBlank(numPair.getId())) {
                YesterAircraftNumPair oldNumStatus = yesterAircraftNumPairService.get(numPair);

                yesterAircraftNumPairService.updateByPlaceStatuse(numPair);
                message.setCode(1);

                StringBuffer sb = new StringBuffer("更新停场占用飞机：[");
                sb.append("机号：").append(oldNumStatus.getAircraftNum()).append(",");
                sb.append("机位：").append(oldNumStatus.getPlaceNum()).append(",");
                sb.append("原机位状态：").append("1".equals(oldNumStatus.getPlaceStatus()) ? "未占用" : "占用").append(",");
                sb.append("现机位状态：").append("1".equals(numPair.getPlaceStatus()) ? "未占用" : "占用").append("]");
                LogUtils.saveLog(request, sb.toString());
            }
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

//	@RequiresPermissions("rms:rmsEmp:edit")
//	@RequestMapping(value = "delete")
//	@ResponseBody
//	public Message delete(YesterAircraftNumPair rmsEmp, Message message) {
//		try {
//			yesterAircraftNumPairService.delete(rmsEmp);
//			message.setCode(1);
//		} catch(Exception e) {
//			message.setMessage(e.getMessage());
//		}
//		return message;
//	}

}