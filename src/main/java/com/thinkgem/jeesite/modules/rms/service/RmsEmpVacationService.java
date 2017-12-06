/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.RmsEmpVacationDao;
import com.thinkgem.jeesite.modules.rms.entity.EmpWrapper;
import com.thinkgem.jeesite.modules.rms.entity.RmsEmp;
import com.thinkgem.jeesite.modules.rms.entity.RmsEmpVacation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工作人员请假管理Service
 *
 * @author xiaopo
 * @version 2016-05-18
 */
@Service
@Transactional(readOnly = true)
public class RmsEmpVacationService extends CrudService<RmsEmpVacationDao, RmsEmpVacation> {

	@Autowired
	private RmsEmpService rmsEmpService;

	public RmsEmpVacation get(String id) {
		return super.get(id);
	}

	public List<RmsEmpVacation> findList(RmsEmpVacation rmsEmpVacation) {
		return super.findList(rmsEmpVacation);
	}

	public Page<RmsEmpVacation> findPage(Page<RmsEmpVacation> page, RmsEmpVacation rmsEmpVacation) {
		return super.findPage(page, rmsEmpVacation);
	}

	@Transactional(readOnly = false)
	public void save(RmsEmpVacation rmsEmpVacation) {
		// 根据工号查询该用户
		RmsEmp rmsEmp = rmsEmpService.findEmpByJobNum(rmsEmpVacation.getJobNum());
		rmsEmpVacation.setDeptId(rmsEmp.getDeptId());
		rmsEmpVacation.setDeptName(rmsEmp.getDept());
		rmsEmpVacation.setPostId(rmsEmp.getPostId());
		rmsEmpVacation.setPostName(rmsEmp.getPost());
		rmsEmpVacation.setEmpName(rmsEmp.getEmpName());
		super.save(rmsEmpVacation);
	}

	@Transactional(readOnly = false)
	public void delete(RmsEmpVacation rmsEmpVacation) {
		super.delete(rmsEmpVacation);
	}

	public List<EmpWrapper> findVacationByDateAndPostIds(Map map) {
		List<EmpWrapper> list = dao.findEmpWrapperByPostId(map);
		// 根据list查询这段时间这些员工的请假
		list.stream().forEach(empWrapper -> {
			List<RmsEmpVacation> empVacationList = dao.findVacationByDateAndEmpIds((Date) map.get("startDate"), (Date) map.get("endDate"), empWrapper.getJobNum());
			empWrapper.set_empVacationList(empVacationList);
		});
		return list;
	}

}