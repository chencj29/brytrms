/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardType;
import com.thinkgem.jeesite.modules.rms.entity.TreeVO;
import com.thinkgem.jeesite.modules.rms.service.SafeguardTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 保障类型管理Controller
 * @author chrischen
 * @version 2016-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/safeguardType")
public class SafeguardTypeController extends BaseController {

	@Autowired
	private SafeguardTypeService safeguardTypeService;

	@RequiresPermissions("rms:safeguardType:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/safeguardType/safeguardType";
	}

	@RequiresPermissions("rms:safeguardType:view"	)
	@RequestMapping("getTree")
	@ResponseBody
	public List<TreeVO> typeTree() {
		List<TreeVO> treeVOList = safeguardTypeService.getRootAndLevelOneType(null);
		return treeVOList;
	}

	@RequiresPermissions("rms:safeguardType:view"	)
	@RequestMapping("getWholeTree")
	@ResponseBody
	public List<TreeVO> getWholeTree() {
		List<TreeVO> treeVOList = safeguardTypeService.getWholeTree(null);
		return treeVOList;
	}

	@RequiresPermissions("rms:safeguardType:view")
    @RequestMapping("get")
    @ResponseBody
    public SafeguardType get(@RequestParam("id") String id) {
        return safeguardTypeService.get(id);
    }

	/*@RequiresPermissions("rms:safeguardType:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<SafeguardType> page, DataTablesPage dataTablesPage, SafeguardType safeguardType, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, safeguardType);
		return safeguardTypeService.findDataTablesPage(page, dataTablesPage, safeguardType);
	}*/

	@RequiresPermissions("rms:safeguardType:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<SafeguardType> list(SafeguardType safeguardType) {
        return new DataGrid<>(safeguardTypeService.findList(safeguardType));
    }

	@RequiresPermissions("rms:safeguardType:view")
	@RequestMapping(value = "form")
	public String form(SafeguardType safeguardType, Model model) {
		model.addAttribute("safeguardType", safeguardType);
		return "modules/rms/safeguardTypeForm";
	}

	@RequiresPermissions("rms:safeguardType:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(SafeguardType safeguardType, Model model, Message message) {
		if (!beanValidator(model, safeguardType)){
			message.setMessage("数据校验错误!");
		}

		safeguardTypeService.checkRedo(safeguardType,new String[]{"safeguardTypeCode"},message);
		if(message.isSuccess()) return message;

		try {
			safeguardTypeService.save(safeguardType);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:safeguardType:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(SafeguardType safeguardType, Message message) {
		try {
			safeguardTypeService.delete(safeguardType);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}


	@RequiresPermissions("rms:safeguardType:view")
	@RequestMapping("getSubList")
	@ResponseBody
	public List<SafeguardType> getSubList(){
		return safeguardTypeService.getSubList();
	}

	@RequiresPermissions("rms:safeguardType:view")
	@RequestMapping("listData")
	@ResponseBody
	public List<SafeguardType> listData(SafeguardType safeguardType) {
		return safeguardTypeService.findList(safeguardType);
	}
}