package com.thinkgem.jeesite.modules.rms.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowu on 2/27/16.
 */
@Service
@Transactional(readOnly = true)
public class CommonService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /***
     * 重复值检测，如果ID为空 - 直接 fieldCode = fieldValue 判断，
     * 如果ID不为空，使用 fieldCode = fieldValue AND ID != currentID的判断方式
     *
     * @param tableName  表名
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @param id         主键
     *
     * @return 存在 = true | 不存在 = false
     */
    public boolean exists(String tableName, String fieldName, String fieldValue, String id) {
        String sql = "select count(1) from " + tableName + " where " + fieldName + " = ? ";
        List<Object> args = new ArrayList<>();
        args.add(fieldValue);
        if (StringUtils.isNotBlank(id)) {
            sql += " AND id <> ?";
            args.add(id);
        }

        return ((Integer) jdbcTemplate.queryForObject(sql, args.toArray(new Object[]{args.size()}), Integer.class)) != 0;
    }
}
