/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.BoardingGateDao;
import com.thinkgem.jeesite.modules.rms.entity.BoardingGate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 登机口信息Service
 *
 * @author wjp
 * @version 2016-03-19
 */
@Service
@Transactional(readOnly = true)
public class BoardingGateService extends CrudService<BoardingGateDao, BoardingGate> {

    public BoardingGate get(String id) {
        return super.get(id);
    }

    public List<BoardingGate> findList(BoardingGate boardingGate) {
        return super.findList(boardingGate);
    }

    public Page<BoardingGate> findPage(Page<BoardingGate> page, BoardingGate boardingGate) {
        return super.findPage(page, boardingGate);
    }

    public void save(BoardingGate boardingGate) {
        super.save(boardingGate);
    }

    public void delete(BoardingGate boardingGate) {
        super.delete(boardingGate);
    }

    @Transactional(readOnly = true)
    public List<BoardingGate> checkStatusByArrivalGateNum(List<String> list) {
        return dao.findByBoardingGateNum(list).stream().filter(boardingGate -> !boardingGate.getBoardingGateStatus().equals("0")).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BoardingGate getByCode(String boardingGateCode) {
        return dao.getByCode(boardingGateCode);
    }

    List<BoardingGate> findAvailableBoardingGateByNature(String nature) {
        return dao.findAvailableBoardingGateByNature(nature);
    }
}