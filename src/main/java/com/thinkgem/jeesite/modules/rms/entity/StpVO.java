package com.thinkgem.jeesite.modules.rms.entity;

import com.thinkgem.jeesite.common.utils.StringUtils;

import java.io.Serializable;

/**
 * 过程时序设置VO，用于datagrid展示
 * Created by chencheng on 16/3/18.
 */
public class StpVO extends SafeguardTypeToProcess implements Serializable {

	private static final long serialVersionUID = 7243490072347153402L;

	/**
	 * 保障类型编号
	 */
	private String safeguardTypeCode;

	/**
	 * 保障类型名称
	 */
	private String safeguardTypeName;

	/**
	 * 保障过程编号
	 */
	private String safeguardProcessCode;

	/**
	 * 保障过程名称
	 */
	private String safeguardProcessName;

	/**
	 * 开始相对于计起表达式
	 */
	private String startDepExp;

	/**
	 * 开始相对于计达表达式
	 */
	private String startArrExp;

	/**
	 * 开始相对于过程编号
	 */
	private String startProCode;

	/**
	 * 开始相对于过程表达式
	 */
	private String startProExp;

	/**
	 * 结束相对于计起表达式
	 */
	private String endDepExp;

	/**
	 * 结束相对于计达表达式
	 */
	private String endArrExp;

	/**
	 * 结束相对于过程编号
	 */
	private String endProCode;

	/**
	 * 结束相对于过程表达式
	 */
	private String endProExp;

	/* 开始时间表达式 */
	private String startTimeExp;

	/* 结束时间表达式 */
	private String endTimeExp;

	/* 时长表达式 */
	private String processTimeExp;

	/* 符合名称 */
 	private String processCompoundName;


	public String getStartTimeExp() {
		if (StringUtils.isNotBlank(super.getStartDepFb()) && super.getStartDepRange() != null) {
			startTimeExp = "关舱门[" + (super.getStartDepFb().equals("-") ? "前" : "后") + "]" + super.getStartDepRange() + "分钟";
		} else if (StringUtils.isNotBlank(super.getStartArrFb()) && super.getStartArrRange() != null) {
			startTimeExp = "开舱门[" + (super.getStartArrFb().equals("-") ? "前" : "后") + "]" + super.getStartArrRange() + "分钟";
		} else if (StringUtils.isNotBlank(super.getStartProId()) && super.getStartProRange() != null) {
			startTimeExp = "[" + super.getStartProName() + "]" + "[" + (super.getStartProFb().equals("-") ? "前" : "后") + "]" + super.getStartProRange() + "分钟";
		}
		return startTimeExp;
	}

	public void setStartTimeExp(String startTimeExp) {
		this.startTimeExp = startTimeExp;
	}

	public String getEndTimeExp() {
		if (StringUtils.isNotBlank(super.getEndDepFb()) && super.getEndDepRange() != null) {
			endTimeExp = "关舱门[" + (super.getEndDepFb().equals("-") ? "前" : "后") + "]" + super.getEndDepRange() + "分钟";
		} else if (StringUtils.isNotBlank(super.getEndArrFb()) && super.getEndArrRange() != null) {
			endTimeExp = "开舱门[" + (super.getEndArrFb().equals("-") ? "前" : "后") + "]" + super.getEndArrRange() + "分钟";
		} else if (StringUtils.isNotBlank(super.getEndProId()) && super.getEndProRange() != null) {
			endTimeExp = "[" + super.getEndProName() + "]" + "[" + (super.getEndProFb().equals("-") ? "前" : "后") + "]" + super.getEndProRange() + "分钟";
		}
		return endTimeExp;
	}

	public void setEndTimeExp(String endTimeExp) {
		this.endTimeExp = endTimeExp;
	}

	public String getProcessTimeExp() {
		if (super.getAbsoluteRange() != null) {
			processTimeExp = "[" + super.getAbsoluteRange() + "]分钟";
		} else if (super.getPercentRange() != null) {
			processTimeExp = "总时长的[" + super.getPercentRange() + "%]";
		}
		return processTimeExp;
	}

	public void setProcessTimeExp(String processTimeExp) {
		this.processTimeExp = processTimeExp;
	}

	public String getSafeguardTypeCode() {
		return safeguardTypeCode;
	}

	public void setSafeguardTypeCode(String safeguardTypeCode) {
		this.safeguardTypeCode = safeguardTypeCode;
	}

	public String getSafeguardTypeName() {
		return safeguardTypeName;
	}

	public void setSafeguardTypeName(String safeguardTypeName) {
		this.safeguardTypeName = safeguardTypeName;
	}

	public String getSafeguardProcessCode() {
		return safeguardProcessCode;
	}

	public void setSafeguardProcessCode(String safeguardProcessCode) {
		this.safeguardProcessCode = safeguardProcessCode;
	}

	public String getSafeguardProcessName() {
		return safeguardProcessName;
	}

	public void setSafeguardProcessName(String safeguardProcessName) {
		this.safeguardProcessName = safeguardProcessName;
	}

	public String getStartDepExp() {
		return startDepExp;
	}

	public void setStartDepExp(String startDepExp) {
		this.startDepExp = startDepExp;
	}

	public String getStartArrExp() {
		return startArrExp;
	}

	public void setStartArrExp(String startArrExp) {
		this.startArrExp = startArrExp;
	}

	public String getStartProCode() {
		return startProCode;
	}

	public void setStartProCode(String startProCode) {
		this.startProCode = startProCode;
	}

	public String getStartProExp() {
		return startProExp;
	}

	public void setStartProExp(String startProExp) {
		this.startProExp = startProExp;
	}

	public String getEndDepExp() {
		return endDepExp;
	}

	public void setEndDepExp(String endDepExp) {
		this.endDepExp = endDepExp;
	}

	public String getEndArrExp() {
		return endArrExp;
	}

	public void setEndArrExp(String endArrExp) {
		this.endArrExp = endArrExp;
	}

	public String getEndProCode() {
		return endProCode;
	}

	public void setEndProCode(String endProCode) {
		this.endProCode = endProCode;
	}

	public String getEndProExp() {
		return endProExp;
	}

	public void setEndProExp(String endProExp) {
		this.endProExp = endProExp;
	}

	public String getProcessCompoundName() {
		return processCompoundName;
	}

	public void setProcessCompoundName(String processCompoundName) {
		this.processCompoundName = processCompoundName;
	}
}
