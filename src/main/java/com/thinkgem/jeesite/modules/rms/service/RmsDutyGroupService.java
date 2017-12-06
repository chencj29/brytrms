/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.rms.dao.RmsDutyGroupDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsDutyGroup;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 岗位班组管理Service
 *
 * @author xiaopo
 * @version 2016-05-19
 */
@Service
@Transactional(readOnly = true)
public class RmsDutyGroupService extends CrudService<RmsDutyGroupDao, RmsDutyGroup> {

	@Autowired
	private OfficeService officeService;

	public RmsDutyGroup get(String id) {
		return super.get(id);
	}

	public List<RmsDutyGroup> findList(RmsDutyGroup rmsDutyGroup) {
		return super.findList(rmsDutyGroup);
	}

	public Page<RmsDutyGroup> findPage(Page<RmsDutyGroup> page, RmsDutyGroup rmsDutyGroup) {
		return super.findPage(page, rmsDutyGroup);
	}

	@Transactional(readOnly = false)
	public void save(RmsDutyGroup rmsDutyGroup) {
		Office office = officeService.get(rmsDutyGroup.getPostId());
		rmsDutyGroup.setPostName(office.getName());
		if(StringUtils.isBlank(rmsDutyGroup.getId())) {
			// 判断是否有时间重复的组
			int count = dao.findDuplicateDutyGroup(rmsDutyGroup.getStartTime(), rmsDutyGroup.getEndTime(), rmsDutyGroup.getPostId());
			if(count > 0) {
				throw new RuntimeException(String.format("该岗位[%s]新增的组合已存在的组工作时间重复,请检查.", rmsDutyGroup.getPostName()));
			}
		}

		super.save(rmsDutyGroup);
	}

	@Transactional(readOnly = false)
	public void delete(RmsDutyGroup rmsDutyGroup) {
		super.delete(rmsDutyGroup);
	}


	public List<RmsDutyGroup> listDataByPostId(String postId){
		RmsDutyGroup dutyGroup = new RmsDutyGroup();
		dutyGroup.setPostId(postId);
		return dao.findAllList(dutyGroup);
	}
}