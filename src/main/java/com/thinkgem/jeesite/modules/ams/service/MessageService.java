package com.thinkgem.jeesite.modules.ams.service;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.Message;
import com.thinkgem.jeesite.modules.ams.entity.MessageLog;
import com.thinkgem.jeesite.modules.ams.entity.MessageWrapper;
import com.thinkgem.jeesite.modules.ams.utils.JdbcTemplateHelper;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.collections.map.HashedMap;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaopo
 * @date 16/5/12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MessageService {

    public static final String COMMON_QUERY_FIELD = " ml.ID logId,m.ID id,m.SEND_ID,m.SEND_NAME,m.MESSAGE,m.MESSAGE_TYPE,m.MESSAGE_GROUP,m.POST_DATE,ml.REC_ID,ml.REC_NAME,ml.STATUS ";

    private static final String INSERT_MESSAGE_SQL = "INSERT INTO AMS_MESSAGE (ID,CREATE_BY,CREATE_DATE,SEND_ID,SEND_NAME,MESSAGE,MESSAGE_GROUP,POST_DATE) VALUES(?,?,?,?,?,?,?,?)";
    private static final String INSERT_MESSAGE_notGroup_SQL = "INSERT INTO AMS_MESSAGE (ID,CREATE_BY,CREATE_DATE,SEND_ID,SEND_NAME,MESSAGE,POST_DATE) VALUES(?,?,?,?,?,?,?)";
    private static final String INSERT_MESSAGE_LOG_SQL = "INSERT INTO AMS_MESSAGE_LOG (ID,CREATE_BY,CREATE_DATE,REC_ID,REC_NAME,MESSAGE_ID,STATUS) VALUES (?,?,?,?,?,?,?)";

    private static final String QUERY_GROUP_SQL = "SELECT m.id, ml.MESSAGE_ID, m.MESSAGE, ml.REC_ID FROM AMS_MESSAGE m LEFT JOIN AMS_MESSAGE_LOG ml ON ml.MESSAGE_ID = m.ID WHERE ml.MESSAGE_ID IS NULL AND instr(m.MESSAGE_GROUP,?) >0";
    private static final String QUERY_MESSAGE_LOG_SQL = "select " + COMMON_QUERY_FIELD + " from ams_message_log ml left join ams_message m on ml.message_id = m.id  where rec_id = ? ORDER BY m.post_date DESC";
    private static final String QUERY_MESSAGE_UNREAD_SQL = "SELECT * FROM (" + QUERY_MESSAGE_LOG_SQL + " ) temp WHERE temp.status = 0  ";

    private static final String QUERY_ONE_MESSAGE_SQL = "select " + COMMON_QUERY_FIELD + " from ams_message_log ml left join ams_message m on ml.message_id = m.id  where rec_id = ? and m.id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcTemplateHelper jdbcTemplateHelper;

    public Map<String, Object> newMessage(Message message, MessageLog messageLog) {
        Map<String, Object> map = new HashedMap();
        if (message == null)
            throw new RuntimeException("message 对象不能为空");

        User loginUser = UserUtils.getUser();
        String uuid = UUID.randomUUID().toString();
        String msg = message.getMessage();
        if (StringUtils.isEmpty(msg)) msg = " ";

        // 保存message对象
        if (StringUtils.isBlank(message.getMessage_group())) {
            Object[] insertDataNotGroup = {uuid, loginUser.getId(), new Date(), loginUser.getId(), loginUser.getName(), msg , new Date()};
            jdbcTemplate.update(INSERT_MESSAGE_notGroup_SQL, insertDataNotGroup);
        } else {
            Object[] insertData = {uuid, loginUser.getId(), new Date(), loginUser.getId(), loginUser.getLoginName(), msg, message.getMessage_group(), new Date()};
            jdbcTemplate.update(INSERT_MESSAGE_SQL, insertData);
            // put message_group
            map.put("message_group", message.getMessage_group());
        }
        map.put("msg", msg);

        // 如果messageLog 对象中的rec_id存在则添加到messageLog表中
        if (messageLog != null && StringUtils.isNotBlank(messageLog.getRec_id())) {
            String[] recids = messageLog.getRec_id().split(",");
            User temp = null;
            List<String> msgLogList = new ArrayList<>();
            for (String recid : recids) {
                if (StringUtils.isBlank(recid)) continue;
                temp = UserUtils.get(recid);
                if (temp == null) continue;
                String uuidTemp = UUID.randomUUID().toString();
                //map.put("message_log", temp.getId() + "|" + uuidTemp + "|" + uuid);
                msgLogList.add(temp.getId() + "|" + uuidTemp + "|" + uuid);
                jdbcTemplate.update(INSERT_MESSAGE_LOG_SQL, uuidTemp, loginUser.getId(), new Date(), temp.getId(), temp.getLoginName(), uuid, 0);
            }
            map.put("message_log", msgLogList);
            map.put("rec_ids", messageLog.getRec_id());
        }

        return map;
    }


    public List<MessageWrapper> getUnreadMessages(User user) {
        // 查询是该用户组是否有消息

        List<Message> messageList = jdbcTemplate.query(QUERY_GROUP_SQL, BeanPropertyRowMapper.newInstance(Message.class), user.getOffice().getId());
        //把该用户组的消息插入到messagelog表中
        messageList.forEach(message -> {
            jdbcTemplate.update(INSERT_MESSAGE_LOG_SQL, UUID.randomUUID().toString(), message.getSend_id(), new Date(), user.getId(), user.getLoginName(), message.getId(), 0);
        });

        //查询messagelog表是否有未读数据
        List<MessageWrapper> messageLogs = jdbcTemplate.query(QUERY_MESSAGE_UNREAD_SQL, BeanPropertyRowMapper.newInstance(MessageWrapper.class), user.getId());

        return messageLogs;
    }

    public MessageWrapper queryOne(String id) {
        List<MessageWrapper> messageWrapper = jdbcTemplate.query(QUERY_ONE_MESSAGE_SQL, BeanPropertyRowMapper.newInstance(MessageWrapper.class), UserUtils.getUser().getId(), id);
        if (messageWrapper != null && messageWrapper.size() == 1) {
            // 修改该条信息为已读
            MessageWrapper message = messageWrapper.get(0);
            jdbcTemplate.update("UPDATE ams_message_log SET status = 1 WHERE id = ?", message.getLogId());
            return message;
        }
        return null;
    }

    public List<MessageWrapper> queryList() {
        //查询messagelog表是否有未读数据
        List<MessageWrapper> messageLogs = jdbcTemplate.query(QUERY_MESSAGE_LOG_SQL, BeanPropertyRowMapper.newInstance(MessageWrapper.class), UserUtils.getUser().getId());
        return messageLogs;
    }

    public DataGrid<MessageWrapper> listData(int page, int rows) {
        DataGrid<MessageWrapper> dataGrid = jdbcTemplateHelper.queryPage(page, rows, MessageWrapper.class, QUERY_MESSAGE_LOG_SQL, UserUtils.getUser().getId());
        return dataGrid;
    }

    public List<MessageWrapper> getEmergencyPrepareMsg(User user) {
        List<MessageWrapper> msgLogsList = getUnreadMessages(user);
        if (msgLogsList == null || msgLogsList.size() < 1) return null;
        List<MessageWrapper> msgLogs = msgLogsList.stream().
                filter(a -> (a.getMessage() != null && a.getMessage().indexOf("emergencyPrepare:") == 0)).map(a -> {
            StringBuffer tmp = new StringBuffer(a.getMessage());
            a.setMessage(tmp.substring(17, tmp.length() - 1));
            return a;
        }).collect(Collectors.toList());
        return msgLogs;
    }

}
