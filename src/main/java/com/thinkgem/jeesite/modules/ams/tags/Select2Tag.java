package com.thinkgem.jeesite.modules.ams.tags;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.ams.entity.*;
import com.thinkgem.jeesite.modules.ams.service.*;
import com.thinkgem.jeesite.modules.rms.entity.AirportTerminal;
import com.thinkgem.jeesite.modules.rms.entity.ServiceProvider;
import com.thinkgem.jeesite.modules.rms.service.AirportTerminalService;
import com.thinkgem.jeesite.modules.rms.service.ServiceProviderService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * @author chencheng
 * @ClassName: Select2Tag
 * @Description: 机型参数select2自定义标签
 * @date 2015年12月25日 下午1:45:16
 */
public class Select2Tag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private String name;

    private String dataType;

    private String dataSelected;
    
    /** 隐藏input name */
    private String hiddenInputName;
    
    private String validation;

    @Override
    public int doStartTag() throws JspException {
        String htmlStr = null;

        if (dataType.equals("apList")) {// 航班参数
            htmlStr = getAircraftParametersSelect2();
        } else if (dataType.equals("adList")) {// 地点
            htmlStr = getAmsAddressSelect2();
        } else if (dataType.equals("airlinesList")) { // 航空公司
            htmlStr = getAirlinesSelect2();
        } else if (dataType.equals("natureList")) { // 航班性质
            htmlStr = getFlightNatureSelect2();
        } else if (dataType.equals("propertiesList")) { // 航班属性
            htmlStr = getFlightPropertiesSelect2();
        } else if (dataType.equals("spList")) { // 服务提供者 代理公司
            htmlStr = getServiceProvidersSelect2();
        } else if (dataType.equals("terminalList")) { // 航站楼
            htmlStr = getTerminalsSelect2();
        }
        try {
            JspWriter out = this.pageContext.getOut();
            out.println(htmlStr);
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    /**
     * 
     * @Title: getAircraftParametersSelect2 
     * @Description: 机型select2
     * @author: chencheng
     * @date： 2016年1月16日 下午12:58:52
     * @param @return
     * @return String
     * @throws
     */
	public String getAircraftParametersSelect2() {
		StringBuffer sb = new StringBuffer();
		List<AircraftParameters> apList = getList(AircraftParameters.class, AircraftParametersService.class, sb);

		for (int i = 0; i < apList.size(); i++) {
			if (StringUtils.isNotEmpty(dataSelected) && apList.get(i).getAircraftModel().equals(dataSelected)) {
				sb.append("<option value=\"").append(apList.get(i).getAircraftModel()).append("\" hiddenInputValue=\"")
						.append("\" selected=\"selected\">").append(apList.get(i).getAircraftModel()).append("</option>");
			} else {
				sb.append("<option value=\"").append(apList.get(i).getAircraftModel()).append("\" hiddenInputValue=\"").append("\" >")
						.append(apList.get(i).getAircraftModel()).append("</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

    /**
     * 
     * @Title: getAmsAddressSelect2 
     * @Description: 航站经停地select2
     * @author: chencheng
     * @date： 2016年1月16日 下午12:58:38
     * @param @return
     * @return String
     * @throws
     */
	public String getAmsAddressSelect2() {
		StringBuffer sb = new StringBuffer();
		List<AmsAddress> adList = getList(AmsAddress.class, AmsAddressService.class, sb);
		String selectedHiddenValue = null;
		for (int i = 0; i < adList.size(); i++) {
			if (StringUtils.isNotEmpty(dataSelected) && adList.get(i).getThreeCode().equals(dataSelected)) {
				sb.append("<option value=\"").append(adList.get(i).getThreeCode()).append("\" hiddenInputValue=\"")
						.append(adList.get(i).getChName()).append("\" selected=\"selected\">").append(adList.get(i).getThreeCode())
						.append("（").append(adList.get(i).getChName()).append("）").append("</option>");
				selectedHiddenValue = adList.get(i).getChName();
			} else {
				sb.append("<option value=\"").append(adList.get(i).getThreeCode()).append("\" hiddenInputValue=\"")
						.append(adList.get(i).getChName()).append("\" >").append(adList.get(i).getThreeCode()).append("（")
						.append(adList.get(i).getChName()).append("）").append("</option>");
			}
		}
		sb.append("</select>");
		String hiddenInputStr = addHiddenInput(selectedHiddenValue);
		sb.append(hiddenInputStr);
		return sb.toString();
	}

	/**
	 * 
	 * @Title: getAirlinesSelect2 
	 * @Description: 航空公司select2
	 * @author: chencheng
	 * @date： 2016年1月16日 下午12:58:18
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String getAirlinesSelect2() {
		StringBuffer sb = new StringBuffer();
		List<FlightCompanyInfo> companyInfoList = getList(FlightCompanyInfo.class, FlightCompanyInfoService.class, sb);
		String selectedHiddenValue = null;
		for (int i = 0; i < companyInfoList.size(); i++) {
			if (StringUtils.isNotEmpty(dataSelected) && companyInfoList.get(i).getTwoCode().equals(dataSelected)) {
				sb.append("<option value=\"").append(companyInfoList.get(i).getTwoCode()).append("\" hiddenInputValue=\"")
						.append(companyInfoList.get(i).getChineseShort()).append("\" selected=\"selected\">")
						.append(companyInfoList.get(i).getTwoCode()).append("（").append(companyInfoList.get(i).getChineseShort())
						.append("）").append("</option>");
				selectedHiddenValue = companyInfoList.get(i).getChineseShort();
			} else {
				sb.append("<option value=\"").append(companyInfoList.get(i).getTwoCode()).append("\" hiddenInputValue=\"")
						.append(companyInfoList.get(i).getChineseShort()).append("\" >").append(companyInfoList.get(i).getTwoCode())
						.append("（").append(companyInfoList.get(i).getChineseShort()).append("）").append("</option>");
			}
		}
		sb.append("</select>");
		String hiddenInputStr = this.addHiddenInput(selectedHiddenValue);
        sb.append(hiddenInputStr);
		return sb.toString();
	}

	/**
	 * 
	 * @Title: getFlightNatureSelect2 
	 * @Description: 航班性质select2
	 * @author: chencheng
	 * @date： 2016年1月16日 下午12:59:26
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String getFlightNatureSelect2() {
		StringBuffer sb = new StringBuffer();
		List<FlightNature> flightNatureList = getList(FlightNature.class, FlightNatureService.class, sb);
		String selectedHiddenValue = null;
		for (int i = 0; i < flightNatureList.size(); i++) {
			if (StringUtils.isNotEmpty(dataSelected) && flightNatureList.get(i).getNatureNum().equals(dataSelected)) {
				sb.append("<option value=\"").append(flightNatureList.get(i).getNatureNum()).append("\" hiddenInputValue=\"")
						.append(flightNatureList.get(i).getNatureName()).append("\" selected=\"selected\">")
						.append(flightNatureList.get(i).getNatureNum()).append("（").append(flightNatureList.get(i).getNatureName())
						.append("）").append("</option>");
				selectedHiddenValue = flightNatureList.get(i).getNatureName();
			} else {
				sb.append("<option value=\"").append(flightNatureList.get(i).getNatureNum()).append("\" hiddenInputValue=\"")
						.append(flightNatureList.get(i).getNatureName()).append("\">").append(flightNatureList.get(i).getNatureNum())
						.append("（").append(flightNatureList.get(i).getNatureName()).append("）").append("</option>");
			}
		}
		sb.append("</select>");
		String hiddenInputStr = this.addHiddenInput(selectedHiddenValue);
		sb.append(hiddenInputStr);
		return sb.toString();
	}

    /**
     * 
     * @Title: getFlightPropertiesSelect2 
     * @Description: 航班属性select2 
     * @author: chencheng
     * @date： 2016年1月16日 下午12:59:45
     * @param @return
     * @return String
     * @throws
     */
	public String getFlightPropertiesSelect2() {
		StringBuffer sb = new StringBuffer();
		List<FlightProperties> flightPropertiesList = getList(FlightProperties.class, FlightPropertiesService.class, sb);
		String selectedHiddenValue = null;
		for (int i = 0; i < flightPropertiesList.size(); i++) {
			if (StringUtils.isNotEmpty(dataSelected) && flightPropertiesList.get(i).getPropertiesNo().equals(dataSelected)) {
				sb.append("<option value=\"").append(flightPropertiesList.get(i).getPropertiesNo()).append("\" hiddenInputValue=\"")
						.append(flightPropertiesList.get(i).getPropertiesName()).append("\" selected=\"selected\">")
						.append(flightPropertiesList.get(i).getPropertiesNo()).append("（")
						.append(flightPropertiesList.get(i).getPropertiesName()).append("）").append("</option>");
				selectedHiddenValue = flightPropertiesList.get(i).getPropertiesName();
			} else {
				sb.append("<option value=\"").append(flightPropertiesList.get(i).getPropertiesNo()).append("\" hiddenInputValue=\"")
						.append(flightPropertiesList.get(i).getPropertiesName()).append("\">")
						.append(flightPropertiesList.get(i).getPropertiesNo()).append("（")
						.append(flightPropertiesList.get(i).getPropertiesName()).append("）").append("</option>");
			}
		}
		sb.append("</select>");
		String hiddenInputStr = this.addHiddenInput(selectedHiddenValue);
		sb.append(hiddenInputStr);
		return sb.toString();
	}

    /**
     * 
     * @Title: getServiceProvidersSelect2 
     * @Description: 服务提供者select2
     * @author: chencheng
     * @date： 2016年1月16日 下午1:00:10
     * @param @return
     * @return String
     * @throws
     */
	public String getServiceProvidersSelect2() {
		StringBuffer sb = new StringBuffer();
		List<ServiceProvider> serviceProviderList = getList(ServiceProvider.class, ServiceProviderService.class, sb);
		String selectedHiddenValue = null;
		for (int i = 0; i < serviceProviderList.size(); i++) {
			if (StringUtils.isNotEmpty(dataSelected) && serviceProviderList.get(i).getServiceProviderNo().equals(dataSelected)) {
				sb.append("<option value=\"").append(serviceProviderList.get(i).getServiceProviderNo()).append("\" hiddenInputValue=\"")
						.append(serviceProviderList.get(i).getServiceProviderName()).append("\" selected=\"selected\">")
						.append(serviceProviderList.get(i).getServiceProviderName()).append("</option>");
				selectedHiddenValue = serviceProviderList.get(i).getServiceProviderName();
			} else {
				sb.append("<option value=\"").append(serviceProviderList.get(i).getServiceProviderNo()).append("\" hiddenInputValue=\"")
						.append(serviceProviderList.get(i).getServiceProviderName()).append("\">")
						.append(serviceProviderList.get(i).getServiceProviderName()).append("</option>");
			}
		}
		sb.append("</select>");
		String hiddenInputStr = this.addHiddenInput(selectedHiddenValue);
		sb.append(hiddenInputStr);
		return sb.toString();
	}

    /**
     * 
     * @Title: getTerminalsSelect2 
     * @Description: 航站楼select2 
     * @author: chencheng
     * @date： 2016年1月16日 下午1:00:27
     * @param @return
     * @return String
     * @throws
     */
	public String getTerminalsSelect2() {
		StringBuffer sb = new StringBuffer();
		List<AirportTerminal> airportTerminals = getList(AirportTerminal.class, AirportTerminalService.class, sb);
		for (int i = 0; i < airportTerminals.size(); i++) {
			if (StringUtils.isNotEmpty(dataSelected) && airportTerminals.get(i).getTerminalName().equals(dataSelected)) {
				sb.append("<option value=\"").append(airportTerminals.get(i).getTerminalName()).append("\" selected=\"selected\">")
						.append(airportTerminals.get(i).getTerminalName()).append("</option>");
			} else {
				sb.append("<option value=\"").append(airportTerminals.get(i).getTerminalName()).append("\">")
						.append(airportTerminals.get(i).getTerminalName()).append("</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

    private <T, C> List<T> getList(Class<T> t, Class<C> c, StringBuffer sb) {
        StringBuffer serviceName = new StringBuffer();
        serviceName.append(Character.toLowerCase(c.getSimpleName().charAt(0))).append(c.getSimpleName().substring(1));
        CrudService crudService = SpringContextHolder.getBean(serviceName.toString());

        // select
        sb.append("<select id=\"" + super.id + "\" hiddenInputName=\"").append(this.hiddenInputName).append("\" class=\"form-control\" name=\"" + this.name + "\" data-validation-engine=\"" + validation + "\">");
        if (!StringUtils.isNotEmpty(dataSelected)) {
            sb.append("<option value=\"").append("\">").append("</option>");
        }


        try {
            return crudService.findList((DataEntity) t.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    private String addHiddenInput(String selectedValue) {
    	StringBuffer sb = new StringBuffer();
    	if (StringUtils.isNotBlank(this.hiddenInputName)) {
    		if (StringUtils.isNotBlank(selectedValue)) {
    			sb.append("<input type=\"hidden\" name=\"").append(this.hiddenInputName).append("\" value=\"").append(selectedValue).append("\"/>");
    		} else {
    			sb.append("<input type=\"hidden\" name=\"").append(this.hiddenInputName).append("\" value=\"").append("\"/>");;
    		}
    	}
    	return sb.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataSelected() {
        return dataSelected;
    }

    public void setDataSelected(String dataSelected) {
        this.dataSelected = dataSelected;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

	public String getHiddenInputName() {
		return hiddenInputName;
	}

	public void setHiddenInputName(String hiddenInputName) {
		this.hiddenInputName = hiddenInputName;
	}
    
}
