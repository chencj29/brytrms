package com.thinkgem.jeesite.modules.ams.entity.common;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.CaseFormat;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateTimeConverter;

/**
 * Created by xiaopo on 15/12/10.
 */
@Component
public class DataTablesPage<T> implements Serializable {

	static {

		// 在封装之前 注册转换器
		ConvertUtils.register(new DateTimeConverter(), java.util.Date.class);
	}

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -2308901543668468684L;

	private Integer draw = 1;

	private Integer pageNo = 1;

	private Integer start = 0;

	private Integer length = 10;

	@JsonIgnore
	private Map<String, DtColumn> parseColums = new LinkedHashMap<String, DtColumn>();

	@JsonIgnore
	private List<Map<String, String>> columnsDataName = new ArrayList<>();
	@JsonIgnore
	private List<Map<String, String>> columnsSearchValue = new ArrayList<>();
	@JsonIgnore
	private List<Map<String, String>> columnsOrder = new ArrayList<>();

	/** 查询参数 */
	@JsonIgnore
	private Map<String, Object> queryParams = new HashMap<String, Object>();

	private JSONObject search;

	private Integer recordsTotal;

	private Integer recordsFiltered;

	private List<T> data;

	/**
	 * 用于排序的列名
	 */
	@JsonIgnore
	private String orderColumn;

	/**
	 * 排序是否为升序。如果{@link #orderColumn}为空，则此字段的字将被忽略
	 */
	@JsonIgnore
	private String orderAsc;

	@SuppressWarnings("unchecked")
	public void setColumns(HttpServletRequest request, Page<T> page, Object obj) {
		Map<String, String[]> paramMap = request.getParameterMap();
		fillOrdering(paramMap);
		fillColumns(paramMap);
		try {
			if (page != null && !this.queryParams.isEmpty()) {
				if (this.queryParams.get("orderBy") != null) {
					String orderBy = this.queryParams.get("orderBy").toString();
					orderBy = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, orderBy);
					page.setOrderBy(orderBy);
				}
			}
			BeanUtils.populate((T) obj, this.queryParams);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void fillOrdering(Map<String, String[]> paramMap) {
		String orderColumnIndexStr = StringUtils.trimToNull(getFirstElement(paramMap.get("order[0][column]")));
		int orderColumnIndex = NumberUtils.toInt(orderColumnIndexStr, -1);
		if (orderColumnIndex < 0) {
			return;
		}

		String columnNameParamKey = MessageFormat.format("columns[{0}][data]", String.valueOf(orderColumnIndex));
		String columnName = StringUtils.trimToNull(getFirstElement(paramMap.get(columnNameParamKey)));

		if (columnName == null) {
			return;
		}

		this.setOrderColumn(columnName);
		this.setOrderAsc(getFirstElement(paramMap.get("order[0][dir]")));

		this.queryParams.put("orderBy", this.orderColumn + " " + this.orderAsc);
	}

	private void fillColumns(Map<String, String[]> paramMap) {
		for (String paramName : paramMap.keySet()) {
			Matcher matcher = DtColumn.COLUMN_NAME_PATTERN.matcher(paramName.trim());
			if (!matcher.find()) {
				continue;
			}

			String columnIndexStr = matcher.group(1);
			if (!NumberUtils.isNumber(columnIndexStr)) {
				continue;
			}
			int columnIndex = NumberUtils.toInt(columnIndexStr);

			if (paramMap.get(paramName) == null || paramMap.get(paramName).length == 0 || StringUtils.isBlank(paramMap.get(paramName)[0])) {
				continue;
			}

			String columnName = StringUtils.trimToNull(paramMap.get(paramName)[0]);
			if (columnName == null) {
				continue;
			}
			String columnSearchValueParamName = MessageFormat.format("columns[{0}][search][value]", String.valueOf(columnIndex));
			String columnSearchValue = StringUtils.trimToNull(getFirstElement(paramMap.get(columnSearchValueParamName)));
			if (StringUtils.isNotBlank(columnSearchValue)) {
				this.addColumn(columnName, columnSearchValue);
				this.queryParams.put(columnName, columnSearchValue);
			}
		}
	}

	public void addColumn(String name, String searchValue) {
		DtColumn column = DtColumn.createInstance(name, searchValue);
		this.parseColums.put(name, column);
	}

	private String getFirstElement(String[] array) {
		if (array == null) {
			return null;
		}
		if (array.length == 0) {
			return null;
		}
		return array[0];
	}

	/**
	 * 根据column name拿到search value
	 * 
	 * @param name
	 * @return
	 */
	public String getColumnSearchValue(String name) {
		DtColumn column = this.parseColums.get(name);
		if (column == null) {
			return null;
		}
		return column.getSearchValue();
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Integer getPageNo() {
		this.pageNo = (this.getStart() / this.getLength()) + 1;
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public JSONObject getSearch() {
		return search;
	}

	public void setSearch(JSONObject search) {
		this.search = search;
	}

	public List<Map<String, String>> getColumnsDataName() {
		return columnsDataName;
	}

	public void setColumnsDataName(List<Map<String, String>> columnsDataName) {
		this.columnsDataName = columnsDataName;
	}

	public List<Map<String, String>> getColumnsSearchValue() {
		return columnsSearchValue;
	}

	public void setColumnsSearchValue(List<Map<String, String>> columnsSearchValue) {
		this.columnsSearchValue = columnsSearchValue;
	}

	public List<Map<String, String>> getColumnsOrder() {
		return columnsOrder;
	}

	public void setColumnsOrder(List<Map<String, String>> columnsOrder) {
		this.columnsOrder = columnsOrder;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getOrderAsc() {
		return orderAsc;
	}

	public void setOrderAsc(String orderAsc) {
		this.orderAsc = orderAsc;
	}

	public Map<String, DtColumn> getParseColums() {
		return parseColums;
	}

	public void setParseColums(Map<String, DtColumn> parseColums) {
		this.parseColums = parseColums;
	}

	public Map<String, Object> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map<String, Object> queryParams) {
		this.queryParams = queryParams;
	}

}
