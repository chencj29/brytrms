package com.thinkgem.jeesite.modules.ams.utils;

import com.thinkgem.jeesite.common.easyui.wrapper.TreeNode;
import com.thinkgem.jeesite.common.easyui.wrapper.TreeNodeExe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wjp on 2016/04/07.
 */
public class EasyuiTreeExe {
    public static List<TreeNodeExe> buildtree(List<TreeNodeExe> nodes, String id){
        List<TreeNodeExe> treeNodes=new ArrayList<TreeNodeExe>();
        for (TreeNodeExe treeNode : nodes) {
            TreeNodeExe node=new TreeNodeExe();
            node.setId(treeNode.getId());
            node.setText(treeNode.getText());
            node.setPid(treeNode.getPid());
            node.setIconCls(treeNode.getIconCls());
            node.setAttributes(treeNode.getAttributes());
            if(id!=null && id.equals(treeNode.getPid())){
                node.setChildren(buildtree(nodes, node.getId()));
                treeNodes.add(node);
            }
        }
        return treeNodes;
    }
}
