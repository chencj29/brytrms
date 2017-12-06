package com.thinkgem.jeesite.modules.ams.utils;

import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaopo
 * @date 16/5/13
 */
@Service
public class JdbcTemplateHelper {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public <T> DataGrid<T> queryPage(int page, int rows, Class<T> t, String sql, Object... args) {
		//计算总记录数
		StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ").append(sql).append(" ) temp ");
		Integer totalCount = jdbcTemplate.queryForObject(totalSQL.toString(), Integer.class, args);

		// 分页sql
		StringBuffer querySql = new StringBuffer("SELECT * FROM ( SELECT * FROM ( ").append(sql).append(" ) TEMP WHERE ROWNUM <= ")
				.append(page * rows).append(" ) WHERE ROWNUM >= ").append((page - 1) * rows);
		//查询分页数据
		List tList = jdbcTemplate.query(querySql.toString(), BeanPropertyRowMapper.newInstance(t), args);

		return new DataGrid<T>(tList);
	}
}
