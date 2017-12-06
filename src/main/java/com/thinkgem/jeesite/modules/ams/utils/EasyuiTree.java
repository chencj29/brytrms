package com.thinkgem.jeesite.modules.ams.utils;

import com.thinkgem.jeesite.common.easyui.wrapper.TreeNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wjp on 2016/03/30.
 */
public class EasyuiTree {
    public static List<TreeNode> buildtree(List<TreeNode> nodess, String id){
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        nodes.addAll(nodess);
        List<TreeNode> treeNodes=new ArrayList<TreeNode>();
        Iterator<TreeNode> it = nodes.iterator();
        while (it.hasNext()){
            TreeNode treeNode = it.next();
            TreeNode node=new TreeNode();
            node.setId(treeNode.getId());
            node.setText(treeNode.getText());
            node.setPid(treeNode.getPid());
            node.setAttributes(treeNode.getAttributes());
            node.setIconCls(treeNode.getIconCls());
            if(id!=null && id.equals(treeNode.getPid())){
                it.remove();
                node.setChildren(buildtree(nodes, node.getId()));
                treeNodes.add(node);
            }
        }
        return treeNodes;
    }
}
