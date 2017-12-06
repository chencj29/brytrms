package com.thinkgem.jeesite.modules.ams.web;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.MessageLog;
import com.thinkgem.jeesite.modules.ams.entity.MessageWrapper;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.MessageService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaopo
 * @date 16/5/12
 */
@Controller
@RequestMapping("${adminPath}/ams/message")
public class MessageController extends BaseController {

	@Autowired
	private MessageService messageService;

	@RequestMapping("/sendmessage")
	@RequiresPermissions("ams:message:sendmessage")
	public String sendMessageView() {
		return "ams/message/sendMessage";
	}


	@RequestMapping("/newmessage")
	@RequiresPermissions("ams:message:newmessage")
	@ResponseBody
	public Message newMessage(com.thinkgem.jeesite.modules.ams.entity.Message message, MessageLog messageLog) {
		Message result = new Message();
		if(StringUtils.isEmpty(message.getMessage())) {
			result.setMessage("消息内容不能为空！");
			return result;
		}

		try {
			Map<String, Object> resultMap = messageService.newMessage(message, messageLog);
			result.setCode(1);
			result.setMessage(JSONObject.toJSONString(resultMap));
		} catch(Exception e) {
			e.printStackTrace();
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@RequestMapping("/list")
	@RequiresPermissions("ams:message:list")
	public String list() {
		return "ams/message/messageList";
	}


	@RequestMapping("/listData")
	@RequiresPermissions("ams:message:listData")
	@ResponseBody
	public DataGrid<MessageWrapper> listData(int page, int rows, MessageWrapper messageWrapper) {
		return messageService.listData(page, rows);
	}


	@RequestMapping("/view/{id}")
	@RequiresPermissions("ams:message:viewmessage")
	@ResponseBody
	public MessageWrapper  viewMessage(@PathVariable String id) {
		MessageWrapper messageWrapper = messageService.queryOne(id);
		return messageWrapper;
	}

	@RequestMapping("/validateNewMessage")
	@RequiresPermissions("ams:message:viewmessage")
	@ResponseBody
	public HashMap<String, Object> validateNewMessage(String id, String log_id, String rec_id, String message_group) {
		HashMap<String, Object> resultMap = new HashMap<>();
		User user = UserUtils.getUser();

		List<MessageWrapper> messageWrappers = messageService.getUnreadMessages(UserUtils.getUser());

		if((StringUtils.isBlank(rec_id) || !user.getId().equals(rec_id)) && (StringUtils.isBlank(message_group) || !message_group.contains(user.getOffice().getId()))) {
			resultMap.put("newMessage", false);
			resultMap.put("unreadMessages", messageWrappers);
			resultMap.put("unreadNum", messageWrappers.size());
			return resultMap;
		}
		//
		resultMap.put("newMessage", true);
		resultMap.put("unreadMessages", messageWrappers);
		resultMap.put("unreadNum", messageWrappers.size());
		return resultMap;
	}

	@RequestMapping("/EPMsg")
	@RequiresPermissions("ams:message:EPMsg")
	@ResponseBody
	public HashMap<String, Object> emergencyPrepareMsg(){
		HashMap<String, Object> resultMap = new HashMap<>();
		User user = UserUtils.getUser();
		//
		resultMap.put("newMessage", false);
		List<MessageWrapper> messageWrappers = messageService.getEmergencyPrepareMsg(UserUtils.getUser());
		if(messageWrappers==null || messageWrappers.size()<1) return resultMap;
		resultMap.put("newMessage", true);
		resultMap.put("unreadMessages", messageWrappers);
		resultMap.put("unreadNum", messageWrappers.size());
		return resultMap;
	}

}
