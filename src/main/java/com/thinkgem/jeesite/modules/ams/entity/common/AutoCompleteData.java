
package com.thinkgem.jeesite.modules.ams.entity.common;

import java.io.Serializable;

/**
 * @ClassName: AutoCompleteData
 * @Description: 自动完成返回值
 * @author chencheng
 * @date 2015年12月24日 下午3:45:41
 */
public class AutoCompleteData implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -1402013671359079178L;
	/** 显示名称 */
	private String name;
	/** 选择值 */
	private String value;
	/** 备注说明 */
	private String desc;

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getValue() {

		return value;
	}

	public void setValue(String value) {

		this.value = value;
	}

	public String getDesc() {

		return desc;
	}

	public void setDesc(String desc) {

		this.desc = desc;
	}

}
