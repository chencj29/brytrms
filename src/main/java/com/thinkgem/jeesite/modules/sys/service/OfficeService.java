/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 机构Service
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {
    final String COMPANY_DEPT_SQL = "SELECT ID, NAME, PARENT_ID FROM SYS_OFFICE WHERE DEL_FLAG = 0 " +
            "START WITH PARENT_ID = '0' CONNECT BY PRIOR ID = PARENT_ID AND GRADE = 2 AND TYPE = 2 " +
            "ORDER BY CREATE_DATE";
    final String POST_SQL = "SELECT ID, NAME FROM SYS_OFFICE WHERE DEL_FLAG = 0 AND PARENT_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Office> findAll() {
        return UserUtils.getOfficeList();
    }

    public List<Office> findList(Boolean isAll) {
        if (isAll != null && isAll) {
            return UserUtils.getOfficeAllList();
        } else {
            return UserUtils.getOfficeList();
        }
    }

    @Transactional(readOnly = true)
    public List<Office> findList(Office office) {
        office.setParentIds(office.getParentIds() + "%");
        return dao.findByParentIdsLike(office);
    }

    @Transactional(readOnly = false)
    public void save(Office office) {
        super.save(office);
        UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }

    @Transactional(readOnly = false)
    public void delete(Office office) {
        super.delete(office);
        UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }


    public List<Map<String, Object>> getCompanyAndDeptJsonData() {
        return jdbcTemplate.queryForList(COMPANY_DEPT_SQL);
    }

    public List<Map<String, Object>> getPostData(String deptId) {
        return jdbcTemplate.queryForList(POST_SQL, deptId);
    }
}
