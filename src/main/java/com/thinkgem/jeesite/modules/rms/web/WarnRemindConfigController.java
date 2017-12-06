/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.web;

import cn.net.metadata.datasync.monitor.factory.MonitorFactory;
import cn.net.metadata.datasync.monitor.wrapper.MonitorEntityWrapper;
import cn.net.metadata.datasync.monitor.wrapper.WarnWebEntityWrapper;
import cn.net.metadata.wrapper.ComboboxWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.WarnRemindConfig;
import com.thinkgem.jeesite.modules.rms.entity.WarnRemindConfigItem;
import com.thinkgem.jeesite.modules.rms.service.WarnRemindConfigItemService;
import com.thinkgem.jeesite.modules.rms.service.WarnRemindConfigService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 预警提醒Controller
 *
 * @author xiaowu
 * @version 2016-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/monitor")
public class WarnRemindConfigController extends BaseController {

    @Autowired
    private WarnRemindConfigService warnRemindConfigService;
    @Autowired
    private WarnRemindConfigItemService itemService;

    @RequiresPermissions("rms:monitor:view")
    @RequestMapping(value = "view")
    public String view() {
        return "rms/sys/monitor";
    }

//    @RequiresPermissions("rms:monitor:view")
//    @RequestMapping("list")
//    @ResponseBody
//    public DataTablesPage<WarnRemindConfig> list(WarnRemindConfig warnRemindConfig, Page<WarnRemindConfig> page, DataTablesPage<WarnRemindConfig> dataTablesPage, HttpServletRequest request, HttpServletResponse response, Model model) {
//        return warnRemindConfigService.findDataTablesPage(page, dataTablesPage, warnRemindConfig);
//    }

    @RequiresPermissions("rms:aircraftStand:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<WarnRemindConfig> list(WarnRemindConfig warnRemindConfig) {
        return new DataGrid<>(warnRemindConfigService.findList(warnRemindConfig));
    }

    @RequiresPermissions("rms:monitor:view")
    @RequestMapping("item-list")
    @ResponseBody
    public DataGrid<WarnRemindConfigItem> itemList(String configId) {
        WarnRemindConfigItem configItem = new WarnRemindConfigItem();
        WarnRemindConfig config = new WarnRemindConfig();
        config.setId(configId);
        configItem.setConfig(config);
        return new DataGrid<>(itemService.findList(configItem));
    }

    @RequiresPermissions("rms:monitor:view")
    @RequestMapping("field-list")
    @ResponseBody
    public List<ComboboxWrapper> fieldList(String monitorClass) {
        List<ComboboxWrapper> list = new ArrayList<>();
        MonitorFactory.getDatas().get(monitorClass).getFields().forEach((key, value) -> {
            list.add(new ComboboxWrapper(key, key, value, false));
        });
        return list;
    }


    @RequiresPermissions("rms:monitor:view")
    @RequestMapping("get")
    @ResponseBody
    public WarnRemindConfig get(@RequestParam("id") String id) {
        return warnRemindConfigService.get(id);
    }

    @RequiresPermissions("rms:monitor:edit")
    @RequestMapping("monitor-list")
    @ResponseBody
    public Map<String, MonitorEntityWrapper> monitorList() {
        return MonitorFactory.getDatas();
    }

    @RequiresPermissions("rms:monitor:edit")
    @RequestMapping("service-list")
    @ResponseBody
    public Map<String, List<String>> serviceList() {
        return MonitorFactory.getActionServices();
    }

    @RequestMapping("event-rule-view")
    public String eventRuleView(String configId, ModelMap map) {
        map.put("configId", configId);
        WarnRemindConfig config = warnRemindConfigService.get(configId);

        List<ComboboxWrapper> list = Lists.newArrayList();

        MonitorFactory.getDatas().get(config.getMonitorClass()).getFields().forEach((key, value) -> {
            list.add(new ComboboxWrapper(key, key, value, false));
        });
        map.put("fields", list);

        return "rms/sys/event-rule-view";
    }

    @RequestMapping("timer-rule-view")
    public String timerRuleView(String configId, ModelMap map) {
        map.put("configId", configId);
        WarnRemindConfig config = warnRemindConfigService.get(configId);

        List<ComboboxWrapper> list = Lists.newArrayList();

        MonitorFactory.getDatas().get(config.getMonitorClass()).getFields().forEach((key, value) -> {
            list.add(new ComboboxWrapper(key, key, value, false));
        });
        map.put("fields", list);

        return "rms/sys/timer-rule-view";
    }

    @RequiresPermissions("rms:monitor:edit")
    @RequestMapping("save")
    @ResponseBody
    public Message save(WarnRemindConfig warnRemindConfig, Model model, Message message) {
        if (!beanValidator(model, warnRemindConfig)) message.setCode(0);

        try {
            warnRemindConfigService.save(warnRemindConfig);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:monitor:edit")
    @RequestMapping("item-save")
    @ResponseBody
    public Message itemsave(@RequestParam("names[]") List<String> codes, @RequestParam("values[]") List<String> names, @RequestParam("configId") String configId, @RequestParam("class") String monitorClass, @RequestParam("configType") String configType, Message message, HttpServletRequest request) {

        WarnRemindConfig config = new WarnRemindConfig();
        config.setId(configId);


        Map<String, Object> uniqueCheckContraints = ImmutableMap.of("rules", Joiner.on(",").join(Ordering.natural().sortedCopy(codes)), "monitorClass", monitorClass, "configType", configType);

        //数据重复检验  -- EventType Update类型
        if (warnRemindConfigService.checkEventItemExist(uniqueCheckContraints) != 0) {
            message.setCode(0);
            message.setMessage("数据项已经存在, 不要重复添加!");
            return message;
        }

        // 物理删除该configId下已存在的规则
        itemService.deletePhysical(configId);

        for (int i = 0, len = codes.size(); i < len; i++) {
            WarnRemindConfigItem item = new WarnRemindConfigItem();
            item.setFieldName(names.get(i));
            item.setFieldCode(codes.get(i));
            item.setItemAvailable(1L);
            item.setConfig(config);

            itemService.save(item);
        }

        message.setCode(1);


        return message;
    }

    @RequiresPermissions("rms:monitor:edit")
    @RequestMapping("item-save-timer")
    @ResponseBody
    public Message itemsavetimer(@RequestParam("configId") String configId, @RequestParam("class") String monitorClass, @RequestParam("configType") String configType, @RequestParam("data") String data, Message message, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        List<WarnWebEntityWrapper> datas = null;
        try {
            datas = mapper.readValue(StringEscapeUtils.unescapeHtml4(data), mapper.getTypeFactory().constructParametricType(ArrayList.class, WarnWebEntityWrapper.class));
        } catch (IOException e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("将items转换为集合时失败" + e.getMessage());
        }

        if (datas != null && !Collections3.isEmpty(datas)) {
            //删除原有的items
            itemService.deletePhysical(configId);

            //重新创建
            WarnRemindConfig config = new WarnRemindConfig();
            config.setId(configId);

            try {
                for (WarnWebEntityWrapper entity : datas) {
                    WarnRemindConfigItem item = new WarnRemindConfigItem();
                    item.setConfig(config);
                    BeanUtils.copyProperties(item, entity);

                    itemService.save(item);
                }
                message.setCode(1);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                message.setCode(0);
                message.setMessage("参数转换时发生错误：" + e.getMessage());
            }
        }
        return message;
    }

    @RequiresPermissions("rms:monitor:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(@RequestParam("id") String id, Message message) {
        try {
            warnRemindConfigService.deletePhysical(id);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
            message.setCode(0);
        }
        return message;
    }
}