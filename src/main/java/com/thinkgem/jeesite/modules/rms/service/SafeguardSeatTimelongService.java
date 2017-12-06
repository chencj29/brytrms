/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardSeatTimelong;
import com.thinkgem.jeesite.modules.rms.dao.SafeguardSeatTimelongDao;

/**
 * 保障过程及座位时长对应Service
 *
 * @author xiaopo
 * @version 2016-03-29
 */
@Service
@Transactional(readOnly = true)
public class SafeguardSeatTimelongService extends CrudService<SafeguardSeatTimelongDao, SafeguardSeatTimelong> {

	public SafeguardSeatTimelong get(String id) {
		return super.get(id);
	}

	public List<SafeguardSeatTimelong> findList(SafeguardSeatTimelong safeguardSeatTimelong) {
		return super.findList(safeguardSeatTimelong);
	}

	public Page<SafeguardSeatTimelong> findPage(Page<SafeguardSeatTimelong> page, SafeguardSeatTimelong safeguardSeatTimelong) {
		return super.findPage(page, safeguardSeatTimelong);
	}

	@Transactional(readOnly = false)
	public void save(SafeguardSeatTimelong safeguardSeatTimelong) {
		super.save(safeguardSeatTimelong);
	}

	@Transactional(readOnly = false)
	public void delete(SafeguardSeatTimelong safeguardSeatTimelong) {
		super.delete(safeguardSeatTimelong);
	}

	public void batchModify(List<SafeguardSeatTimelong> seatTimelongList) {
		if (seatTimelongList == null || seatTimelongList.size() == 0)
			throw new RuntimeException("数据为空");
		// 首先删除已经存在的(相同进程,相同过程,相同座位数的数据)
		dao.deleteByTypeProcess(seatTimelongList.get(0));
		// 保存数据
		seatTimelongList.forEach(seat -> {
			save(seat);
		});
	}

	public List<SafeguardSeatTimelong> queryListBySeatSafeguardTypeCode(SafeguardSeatTimelong safeguardSeatTimelong){
		return dao.queryListBySeatSafeguardTypeCode(safeguardSeatTimelong);
	}

}