/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.easyui.wrapper.TreeNode;
import com.thinkgem.jeesite.common.easyui.wrapper.TreeNodeExe;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.utils.EasyuiTree;
import com.thinkgem.jeesite.modules.ams.utils.EasyuiTreeExe;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.EmergencyUnit;
import com.thinkgem.jeesite.modules.rms.dao.EmergencyUnitDao;

/**
 * 应急救援单位信息Service
 * @author wjp
 * @version 2016-04-06
 */
@Service
@Transactional(readOnly = true)
public class EmergencyUnitService extends CrudService<EmergencyUnitDao, EmergencyUnit> {

	public EmergencyUnit get(String id) {
		return super.get(id);
	}
	
	public List<EmergencyUnit> findList(EmergencyUnit emergencyUnit) {
		return super.findList(emergencyUnit);
	}
	
	public Page<EmergencyUnit> findPage(Page<EmergencyUnit> page, EmergencyUnit emergencyUnit) {
		return super.findPage(page, emergencyUnit);
	}
	
	@Transactional(readOnly = false)
	public void save(EmergencyUnit emergencyUnit) {
		super.save(emergencyUnit);
	}
	
	@Transactional(readOnly = false)
	public void delete(EmergencyUnit emergencyUnit) {
		super.delete(emergencyUnit);
	}

	@Transactional(readOnly = false)
	public void batchDelete(String[] ids){
		if(StringUtils.isNoneBlank(ids)){
			dao.batchDelete(ids);
		}
	}

	public List<TreeNodeExe> getEUTree(String ntype,String id) {
		List<TreeNodeExe> nodes=new ArrayList<TreeNodeExe>();
		List<EmergencyUnit> list = super.findList(new EmergencyUnit());
		for(EmergencyUnit e:list){
			TreeNodeExe treeNode=new TreeNodeExe();

			treeNode.setId(e.getId());
			treeNode.setPid(e.getPid());
			Map<String,Object> map = new HashMap<>();
			map.put("ntype",e.getNtype());
			treeNode.setAttributes(map);
			if(StringUtils.equals("1",e.getNtype())){
				treeNode.setText(e.getUnitName());
				treeNode.setIconCls("icon-chart_organisation");  //图标
				nodes.add(treeNode);
			}else if(!StringUtils.equals("1",ntype) && StringUtils.equals("2",e.getNtype())){  //1.机构 2.员工
				treeNode.setText(e.getContactName());
				treeNode.setIconCls("icon-user");          //图标
				nodes.add(treeNode);
			}
		}
		List<TreeNodeExe> treeNodes = new ArrayList<TreeNodeExe>();
		if(StringUtils.isNoneBlank(id)){
			treeNodes = EasyuiTreeExe.buildtree(nodes,id);
		}else{
			treeNodes = EasyuiTreeExe.buildtree(nodes,"0");
		}
		return treeNodes;
	}
}