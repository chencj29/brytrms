/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.WarnExecutionLogDao;
import com.thinkgem.jeesite.modules.rms.entity.WarnExecutionLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 预警提醒规则Service
 *
 * @author xiaowu
 * @version 2016-01-19
 */
@Service
@Transactional(readOnly = true)
public class WarnExecutionLogService extends CrudService<WarnExecutionLogDao, WarnExecutionLog> {
    
}