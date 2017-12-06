package com.thinkgem.jeesite.modules.ams.entity.common;

import java.util.regex.Pattern;

public class DtColumn {
	private String name;
	private String searchValue;

	public static final Pattern COLUMN_NAME_PATTERN = Pattern.compile("columns\\[(\\d+)\\]\\[data\\]");

	public static final DtColumn createInstance(String name, String searchValue) {
		DtColumn instance = new DtColumn();
		instance.name = name;
		instance.searchValue = searchValue;
		return instance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
}
