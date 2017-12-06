/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.modules.rms.entity.StpVO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Immutable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardTypeToProcess;
import com.thinkgem.jeesite.modules.rms.dao.SafeguardTypeToProcessDao;

/**
 * 保障类型保障过程关联Service
 *
 * @author chrischen
 * @version 2016-03-17
 */
@Service
@Transactional(readOnly = true)
public class SafeguardTypeToProcessService extends CrudService<SafeguardTypeToProcessDao, SafeguardTypeToProcess> {

	public SafeguardTypeToProcess get(String id) {
		return super.get(id);
	}

	public List<SafeguardTypeToProcess> findList(SafeguardTypeToProcess safeguardTypeToProcess) {
		return super.findList(safeguardTypeToProcess);
	}

	public Page<SafeguardTypeToProcess> findPage(Page<SafeguardTypeToProcess> page, SafeguardTypeToProcess safeguardTypeToProcess) {
		return super.findPage(page, safeguardTypeToProcess);
	}

	@Transactional(readOnly = false)
	public void save(SafeguardTypeToProcess safeguardTypeToProcess) {
		super.save(safeguardTypeToProcess);
	}

	@Transactional(readOnly = false)
	public void delete(SafeguardTypeToProcess safeguardTypeToProcess) {
		super.delete(safeguardTypeToProcess);
	}

	@Transactional(readOnly = false)
	public void deleteByRelate(SafeguardTypeToProcess safeguardTypeToProcess) {
		super.dao.deleteByRelate(safeguardTypeToProcess);
	}

	@Transactional(readOnly = false)
	public List<StpVO> findStpVOList(SafeguardTypeToProcess safeguardTypeToProcess) {
		return super.dao.findStpVOList(safeguardTypeToProcess);
	}

	/**
	 * @param safeguardTypeToProcess
	 */
	public void updateTimeSeq(SafeguardTypeToProcess safeguardTypeToProcess) {
		// 清空该条数据的其他字段
		dao.updateClearParams(safeguardTypeToProcess);
		// 重新设置新数据
		SafeguardTypeToProcess temp = dao.get(safeguardTypeToProcess);
//		org.springframework.beans.BeanUtils.copyProperties(temp,safeguardTypeToProcess);
		// 设置必备属性
		safeguardTypeToProcess.setCreateBy(temp.getCreateBy());
		safeguardTypeToProcess.setCreateDate(temp.getCreateDate());
		safeguardTypeToProcess.setUpdateBy(temp.getUpdateBy());
		safeguardTypeToProcess.setUpdateDate(temp.getUpdateDate());
		safeguardTypeToProcess.setDelFlag(temp.getDelFlag());
		safeguardTypeToProcess.setSafeguardTypeId(temp.getSafeguardTypeId());
		safeguardTypeToProcess.setSafeguardProcessId(temp.getSafeguardProcessId());
		//
		String selectedStatus = safeguardTypeToProcess.getSelectedStatus();
		selectedStatus = StringEscapeUtils.unescapeHtml3(selectedStatus);
		safeguardTypeToProcess.setSelectedStatus(selectedStatus);
		super.save(safeguardTypeToProcess);
	}

	public void updateSort(ArrayList<Integer> sort, ArrayList<String> ids, String typeId) {
		if (sort != null && ids != null && sort.size() == ids.size()) {
			for (int i = 0; i < sort.size(); i++) {
				dao.updateSort(ImmutableMap.of("sort",sort.get(i),"processId",ids.get(i),"typeId",typeId));
			}
		}else{
			throw new RuntimeException("数据不匹配,请联系管理员");
		}
	}
}