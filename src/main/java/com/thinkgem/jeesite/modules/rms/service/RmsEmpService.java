/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.RmsEmpDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsEmp;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工作人员基础信息Service
 *
 * @author wjp
 * @version 2016-03-09
 */
@Service
@Transactional(readOnly = true)
public class RmsEmpService extends CrudService<RmsEmpDao, RmsEmp> {

	@Autowired
	private OfficeDao officeDao;

	public RmsEmp get(String id) {
		return super.get(id);
	}

	public List<RmsEmp> findList(RmsEmp rmsEmp) {
		return super.findList(rmsEmp);
	}

	public Page<RmsEmp> findPage(Page<RmsEmp> page, RmsEmp rmsEmp) {
		return super.findPage(page, rmsEmp);
	}

	@Transactional(readOnly = false)
	public void save(RmsEmp rmsEmp) {
		Office dept = officeDao.get(rmsEmp.getDeptId());
		rmsEmp.setDept(dept.getName());
		Office post = officeDao.get(rmsEmp.getPostId());
		rmsEmp.setPost(post.getName());
		super.save(rmsEmp);
	}

	@Transactional(readOnly = false)
	public void delete(RmsEmp rmsEmp) {
		super.delete(rmsEmp);
	}

	public RmsEmp findEmpByJobNum(String jobNum) {
		return dao.findEmpByJobNum(jobNum);
	}

}