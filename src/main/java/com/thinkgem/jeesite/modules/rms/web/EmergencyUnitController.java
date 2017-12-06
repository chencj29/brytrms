/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.easyui.wrapper.TreeNodeExe;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.EmergencyUnit;
import com.thinkgem.jeesite.modules.rms.service.EmergencyUnitService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 应急救援单位信息Controller
 * @author wjp
 * @version 2016-04-06
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/emergencyUnit")
public class EmergencyUnitController extends BaseController {

	@Autowired
	private EmergencyUnitService emergencyUnitService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private SystemService systemService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@RequiresPermissions("rms:emergencyUnit:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/emergencyUnit/emergencyUnit";
	}

	@RequiresPermissions("rms:emergencyUnit:view")
    @RequestMapping("get")
    @ResponseBody
    public EmergencyUnit get(@RequestParam("id") String id) {
        return emergencyUnitService.get(id);
    }

	/*@RequiresPermissions("rms:emergencyUnit:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<EmergencyUnit> page, DataTablesPage dataTablesPage, EmergencyUnit emergencyUnit, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, emergencyUnit);
		return emergencyUnitService.findDataTablesPage(page, dataTablesPage, emergencyUnit);
	}*/

	@RequiresPermissions("rms:emergencyUnit:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<EmergencyUnit> list(EmergencyUnit emergencyUnit) {
		//if(StringUtils.isBlank(emergencyUnit.getPid())) emergencyUnit.setPid("0");
        return new DataGrid<>(emergencyUnitService.findList(emergencyUnit));
    }

	@RequiresPermissions("rms:emergencyUnit:view")
	@RequestMapping(value = "form")
	public String form(EmergencyUnit emergencyUnit, Model model) {
		model.addAttribute("emergencyUnit", emergencyUnit);
		return "modules/rms/emergencyUnitForm";
	}

	@RequiresPermissions("rms:emergencyUnit:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(EmergencyUnit emergencyUnit, Model model, Message message) {
		if(StringUtils.isBlank(emergencyUnit.getPid())&& StringUtils.isNoneBlank(emergencyUnit.getUnitName())){
			emergencyUnit.setPid("0");  //设为根
			emergencyUnit.setNtype("1");//设为机构
		}else if(StringUtils.equals(emergencyUnit.getPid(),"0")){
			emergencyUnit.setNtype("1");//设为机构
		}
		if (!beanValidator(model, emergencyUnit)){
			message.setMessage("数据校验错误!");
		}
		try {
			emergencyUnitService.save(emergencyUnit);
			/*if(StringUtils.equals("2",emergencyUnit.getNtype())){//判断是否为员工 1.机构 2.员工
				String id = emergencyUnit.getId();
				// String userName = PinyinHelper.getShortPinyin(emergencyUnit.getContactName());
				String userName = "";
				String off_id = (String)jdbcTemplate.queryForMap("SELECT id from sys_office where name='应急救援单位'").get("id");
				String role_id = (String)jdbcTemplate.queryForMap("SELECT id from sys_role where name='普通用户'").get("id");

				User user = new User();
				user.preInsert();
				user.setId(id);
				user.setName(emergencyUnit.getContactName());
				if(StringUtils.isNotBlank(emergencyUnit.getContactTel())) user.setPhone(emergencyUnit.getContactTel());
				user.setCompany(new Office("1"));//设置为组织机构
				user.setOffice(new Office(off_id));
				user.setLoginName("jr_"+userName);
				Integer i=1;
				while(!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
					user.setLoginName("jr_"+userName+i++);
				}
				user.setNo(userName);
				// 设置密码
				user.setPassword(SystemService.entryptPassword("admin"));
				List<String> rolelistid = new ArrayList<>();
				rolelistid.add(role_id);
				user.setRoleIdList(rolelistid);
				// 更新用户与角色关联
				userDao.deleteUserRole(user);
				if (user.getRoleList() != null && user.getRoleList().size() > 0){
					userDao.insertUserRole(user);
				}else{
					throw new ServiceException(user.getLoginName() + "没有设置角色！");
				}
				userDao.insert(user);
				message.setMessage("成功添加员工:"+user.getName()+"，登录名为："+user.getLoginName()+"!");
				logger.info("应急救援单位信息:"+message.getMessage());
			}*/

			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:emergencyUnit:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(EmergencyUnit emergencyUnit, Message message) {
		try {
			emergencyUnitService.delete(emergencyUnit);
			//systemService.deleteUser(new User(emergencyUnit.getId()));
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:emergencyUnit:edit")
	@RequestMapping(value = "deleteTree")
	@ResponseBody
	public Message deleteTree(@RequestParam(value = "ids[]") String[] ids, Message message) {
		if(!StringUtils.isNoneBlank(ids)){
			message.setMessage("你输入的是空值！");
			return message;
		}
		try {
			emergencyUnitService.batchDelete(ids);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:emergencyUnit:view")
	@RequestMapping("getMenuList")
	@ResponseBody
	public List<TreeNodeExe> getMenuList(String ntype){
		return emergencyUnitService.getEUTree(ntype,"0");
	}




	private String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

}