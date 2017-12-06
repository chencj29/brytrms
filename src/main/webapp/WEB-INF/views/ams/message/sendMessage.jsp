<%--
  Created by IntelliJ IDEA.
  User: xiaopo
  Date: 16/2/3
  Time: 上午11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>消息管理</title>
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
</head>
<body>
<div class="easyui-panel" title="发送新消息" fit="true">
    <div style="padding:10px 60px 20px 60px">
        <form id="send_new_message_form" method="post" style="width: 100%">
            <table cellpadding="5" style="width: 100%">
                <tr>
                    <td>消息接收机构:</td>
                    <td>
                        <select class="easyui-combobox" id="offices" name="message_group"
                                style="width:100%;height:26px;" data-options="multiple:true,panelHeight:'auto'">
                            <option value="">全选/反选</option>
                            <c:forEach items="${fns:getAllOffices()}" var="office">
                                <option value="${office.id}">${office.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>消息接收人:</td>
                    <td>
                        <select class="easyui-combobox" name="rec_id" id="rec_id" style="width:100%;height:26px;"
                                data-options="multiple:true,panelHeight:'auto'">
                            <%--<option value="">全选/反选</option>--%>
                            <c:forEach items="${fns:getAllUsers()}" var="user">
                                <option value="${user.id}">${user.name}/${user.loginName}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>消息内容:</td>
                    <td>
                        <input class="easyui-textbox" id="et-message" name="message" data-options="multiline:true"
                               style="height:150px;width: 100%;"/>
                    </td>
                </tr>
            </table>
            <div style="text-align:center;padding:5px">
                <a href="javascript:void(0)" class="easyui-linkbutton" id="submit" onclick="javascript:;">发送消息</a>
            </div>
        </form>

    </div>
</div>
<script>
    $(function () {
        $('#rec_id').combobox({
            filter: function (q, row) {
                var opts = $(this).combobox('options');
                return row[opts.textField].indexOf(q) >= 0;
            }/*,
            onSelect:function (record){
                var select = $("#rec_id");
                var valueField = select.combobox("options").valueField;
                if(!record[valueField]){
                    var data = select.combobox("getData");
                    var values = select.combobox("getValues");
                    var selectVaues = [];
                    if((data.length - 1) != values.length){
                        data.reduce(function(prev, current, index, array){
                            selectVaues.push(current[valueField]);
                        },selectVaues);
                    }else{
                        selectVaues.push(record[valueField]);
                    }
                    select.combobox('setValues', selectVaues);
                }
            },
            onUnselect:function (r) {
                if (r.value == '') { //当取消选择的是‘所有’这个选项
                    $("#rec_id").combobox("setValues", []).combobox("setText", '');
                }
            }*/
        });

        $('#offices').combobox({
            filter: function (q, row) {
                var opts = $(this).combobox('options');
                return row[opts.textField].indexOf(q) >= 0;
            },
            onSelect:function (record){
                var select = $("#offices");
                var valueField = select.combobox("options").valueField;
                if(!record[valueField]){
                    var data = select.combobox("getData");
                    var values = select.combobox("getValues");
                    var selectVaues = [];
                    if((data.length - 1) != values.length){
                        data.reduce(function(prev, current, index, array){
                            selectVaues.push(current[valueField]);
                        },selectVaues);
                    }else{
                        selectVaues.push(record[valueField]);
                    }
                    select.combobox('setValues', selectVaues);
                }
            },
            onUnselect:function (r) {
                if (r.value == '') { //当取消选择的是‘所有’这个选项
                    $("#offices").combobox("setValues", []).combobox("setText", '');
                }
            }
        });

        $('#rec_id').combobox('clear');
        $('#offices').combobox('clear');
        $('#et-message').textbox('clear');

        // 提交
        $("#submit").click(function () {
            var recs_data = $('#rec_id').combobox('getValues');
            var offices_data = $('#offices').combobox('getValues');
            if ((!recs_data || recs_data == null || recs_data.length == 0) && (!offices_data || offices_data == null || offices_data.length == 0)) {
                $.messager.slide('请选择消息接收者!');
                return false;
            }

            var data = $("#send_new_message_form").serialize();
            // 提交数据
            $.post(ctx + '/ams/message/newmessage', data, function (resp) {
                if (resp && resp.code == 1) {
                    $.messager.slide('消息发送成功!');
                    $('#rec_id').combobox('clear');
                    $('#offices').combobox('clear');
                    $('#et-message').textbox('clear');
                    setTimeout(function () {
                        MESSAGE_SOCKET_IO_CONNECT.emit('messagenotify', resp.message);
                    }, 2000);
                } else {
                    console.log(resp);
                    if (resp && resp.message) {
                        $.messager.alert('提示', resp.message);
                    } else {
                        $.messager.alert('提示', '发送消息失败,请稍后重试!');
                    }
                }
            });

        });
    });


</script>
</body>
</html>
