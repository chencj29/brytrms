package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.easyui.wrapper.TreeNode;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.utils.EasyuiTree;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaopo on 15/12/8.
 */
@Controller
@RequestMapping(value = "${adminPath}/rms")
public class IndexController extends BaseController {
    //一级图标对应关系 wjp 2016年5月12日14时09分54秒
    public final static Map<String,String> main_icos = new HashMap<String,String>(){{
        put("资源分配","rs");
        put("运输调度","flight_plan_pair");
        put("运输值机","check_schedule");
        put("运输服务","special_services");
        put("运输行李","luggage");
        put("运输配载","transportation_stowage");
        put("运输保障","safeguard_type");
        put("出港航班","check_in");
        put("应急救援","emergency_plan");
        put("基础数据","base");
        put("统计查询","Statistics");
        put("规则引擎","urule");
        put("系统设置","system_set");
        put("代码生成","gen_table");
        put("机场基础","aircraft_base");
        put("default","default");
    }};

    //@RequiresPermissions("rms:index:view")
    @RequestMapping("m")
    public String index(Model model){
        model.addAttribute("main_icos",main_icos);
        return "rms/m";
    }

//    @RequiresPermissions("rms:index:view")
    @RequestMapping("getMenuList")
    @ResponseBody
    public List<TreeNode> getMenuList(String id){
        List<TreeNode> nodes=new ArrayList<TreeNode>();
        List<Menu> list =  UserUtils.getMenuList();
        for (Menu menu : list) {
            if("1".equals(menu.getIsShow())){
                TreeNode treeNode=new TreeNode();
                treeNode.setId(menu.getId());
                treeNode.setPid(menu.getParent().getId());
                treeNode.setText(menu.getName());
                Map<String,Object>map = new HashMap<>();
                map.put("url",menu.getHref());
                treeNode.setAttributes(map);
                nodes.add(treeNode);
            }
        }
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        if(StringUtils.isNoneBlank(id)){
            treeNodes = EasyuiTree.buildtree(nodes,id);
        }else{
            treeNodes = EasyuiTree.buildtree(nodes,"1");
        }
        return treeNodes;
    }




}
