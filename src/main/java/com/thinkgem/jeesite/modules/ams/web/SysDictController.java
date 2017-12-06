/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;

import cn.net.metadata.wrapper.KeyValuePairsWapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.SysDict;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.SysDictService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典管理Controller
 *
 * @author xiaopo
 * @version 2015-12-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sysdict")
public class SysDictController extends BaseController {

    @Autowired
    private SysDictService sysDictService;

    /*@ModelAttribute
    public SysDict get(@RequestParam(required = false) String id) {
        SysDict entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = sysDictService.get(id);
        }
        if (entity == null) {
            entity = new SysDict();
        }
        return entity;
    }*/

    @RequiresPermissions("sysdict:view")
    @RequestMapping("get")
    @ResponseBody
    public SysDict get(@RequestParam("id") String id) {
        return sysDictService.get(id);
    }

    @RequiresPermissions("sysdict:view")
    @RequestMapping(value = {"view", ""})
    public String view() {
        return "ams/sys/sysdict";
    }

    @RequiresPermissions("sysdict:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<SysDict> list(SysDict sysDict) {
        return new DataGrid<>(sysDictService.findList(sysDict));
    }

    /*@RequiresPermissions("sysdict:view")
    @RequestMapping(value = {"list", ""})
    @ResponseBody
    public DataTablesPage<SysDict> list(Page<SysDict> page, SysDict sysDict, DataTablesPage<SysDict> dataTablesPage, HttpServletRequest request, HttpServletResponse response, Model model) {
        dataTablesPage.setColumns(request, page, sysDict);
        dataTablesPage = sysDictService.findDataTablesPage(page, dataTablesPage, sysDict);
        return dataTablesPage;
    }*/


    @RequiresPermissions("sysdict:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(SysDict sysDict, Model model, Message message) {
        if (!beanValidator(model, sysDict)) {
            message.setMessage("数据校验错误!");
        }
        try {
            sysDictService.save(sysDict);
            CacheUtils.remove(DictUtils.CACHE_DICT_MAP_TABLE_TYPE);
            CacheUtils.remove(DictUtils.CACHE_DICT_MAP_TABLE);
            CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("sysdict:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(SysDict sysDict, Message message) {
        try {
            sysDictService.delete(sysDict);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    //@RequiresPermissions("sysdict:view")
    @RequestMapping(value = "getLabels")
    @ResponseBody
    public HashMap<String, String> getLabelFromTableNameType(String tableName, String columnName) {
        HashMap<String, String> hashMap = new HashMap<>();
        List<Dict> dicts = DictUtils.getDictListByTableNameType(tableName, columnName);
        for (Dict dict : dicts) {
            hashMap.put(dict.getValue(), dict.getLabel());
        }
        return hashMap;
    }

    //@RequiresPermissions("sysdict:view")
    @RequestMapping(value = "getLabelss")
    @ResponseBody
    public Map<String, Map<String, String>> getLabelss(@RequestBody ArrayList<KeyValuePairsWapper> wrappers) {
        Map<String, Map<String, String>> result = new HashMap<>();
        wrappers.forEach(wrapper -> {
            Map<String, String> dicts = new HashMap<>();
            DictUtils.getDictListByTableNameType(wrapper.getKey(), wrapper.getValue()).forEach(dict -> {
                dicts.put(dict.getValue(), dict.getLabel());
            });
            result.put(wrapper.getKey() + "@" + wrapper.getValue(), dicts);
        });
        return result;
    }
}