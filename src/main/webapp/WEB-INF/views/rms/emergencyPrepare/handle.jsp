<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>应急救援处理</title>
    <style type="text/css"> .tree-node { height: 18px;  white-space: nowrap;  cursor: pointer; } </style>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">

        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'">
                <div style="text-align: right;padding:5px">
                    <a class="easyui-linkbutton" id="btnClsUl" data-options="iconCls:'icon-clear'" title='清空此列表' style="width:60px">清空</a>
                </div>
                <div style="padding:5px">应急救援处理信息-接收(详情请查看消息列表)：</div>
                <div id="unreadMessagesUl" style="padding:5px"></div>
            </div>
        </div>
    </div>
        <div data-options="region:'east',split:true" title="应急救援消息收发" style="width:30%;">
            <div class="easyui-layout" data-options="fit:true,border:false">
            <div style="height:239px">
                <form id="send_new_message_form" method="post" style="width: 100%">
                    <table cellpadding="5" style="width: 100%">
                        <tr>
                            <td>接收人:</td>
                            <td>
                                <select class="easyui-validatebos" id="rec_id" name="rec_id" style="width:178px;" data-options=""></select>
                            </td>
                        </tr>
                        <tr>
                            <td>内容:</td>
                            <td>
                                <input class="easyui-textbox" id="et-message" name="message" data-options="multiline:true" style="height:75px;width: 100%;"/>
                            </td>
                        </tr>
                    </table>
                    <div style="text-align:center;padding:5px">
                        <a href="javascript:void(0)" class="easyui-linkbutton" id="submit" onclick="javascript:;">发送消息</a>
                    </div>
                </form>
            </div>
            <div data-options="region:'south'" style="height: 43%;padding:5px">
                <div style="text-align: right; ">
                    <a class="easyui-linkbutton" id="btnCls" data-options="iconCls:'icon-clear'" title='清空此列表' style="width:60px">清空</a>
                </div>
                <div style="padding:5px">已发送给：</div>
                <div id="sendEnd" style="padding:5px"></div>
            </div>
        </div>
    </div>
</div>
<script type="text/html" id="unreadMessagesTemplate">
    <li>
        <a class="cyan" >
            <div class="message-info" _id ={{send_id}} style="cursor:pointer;" onclick="sendI(this)">
                <span class="sender">{{send_name}}({{post_date}}):{{message}}</span>
            </div>
        </a>
    </li>
</script>

<script src="${ctxAssets}/scripts/aui-artTemplate/dist/template.js"></script>
<script>
    $(function () {
        $('#rec_id').combotree({
            url:ctx+'/sys/office/treeNode?type=3',
            //url: ctx + '/rms/emergencyUnit/getMenuList?ntype=2',
            required: true,
            multiple:true
        });

        // 提交
        $("#submit").click(function () {
            var recs_data = $('#rec_id').combobox('getValues');
            if (!recs_data || recs_data.length==0) {
                $.messager.slide('请选择消息接收者!');
                return false;
            }

            var data =[];
            _.map(recs_data,function(a){
                if(a.indexOf("user_")==0){
                    data.push(a.replace("user_",""));
                }
            })

            var msg = "emergencyPrepare:"+$('#et-message').val();

            var userList  = $('#rec_id').combotree('getText');
            if(!!userList){
                var hh = $("#sendEnd").html();
                var users = userList.split(",");
                var html = "<li><a class='cyan'><div style='cursor:pointer;'>"+users.join("</div></a></li><li><a class='cyan'><div style='cursor:pointer;'>")+"<div></a></li>"

                $("#sendEnd").html(hh+html);
            }
            // 提交数据
            $.post(ctx + '/ams/message/newmessage',{rec_id:data.join(","),message:msg}, function (resp) {
                if (resp && resp.code == 1) {
                    $.messager.slide('消息发送成功!');
                    $('#rec_id').combotree('clear');
                    $('#et-message').textbox('clear');
                    setTimeout(function () {
                        MESSAGE_SOCKET_IO_CONNECT.emit('messagenotify', resp.message);
                    },1000);
                } else {
                    console.log(resp);
                    $.messager.alert('提示', '发送消息失败,请稍后重试!');
                }
            });

        });

        $.get(ctx + '/ams/message/EPMsg', {}, function (resp) {
            if(resp && resp.newMessage){
                _.each(resp.unreadMessages,function(msg){
                        msgEPData.push(msg);
                })
            }
        });

        $("#btnCls").click(function(){
            $("#sendEnd").html("");
        });

        $("#btnClsUl").click(function(){
            $("#unreadMessagesUl").html("");
        });

    });

    (function timeline_looper() {
        recMsg();
        setTimeout(timeline_looper, 1000 * 3);
    })();

    flag_msgEP=true;  //救援消息开关:开
    function recMsg(){
        if(msgEPData.length>0){
            _.each(msgEPData,function(msg){
                if (!!msg) {
                    var mg = msg.message;
                    if(!!mg) msg.message = mg.replace("emergencyPrepare:","");
                    var unreadMessagesHtml = template('unreadMessagesTemplate', msg);
                    var htm = $('#unreadMessagesUl').html()+"<br/>";
                    $('#unreadMessagesUl').html(htm+unreadMessagesHtml);
                    $.get(ctx + '/ams/message/view/'+msg.id);
                }
            })
            msgEPData=[];
        }
    }

    function sendI(a){
        //debugger
        $('#rec_id').combotree('setValue', "user_"+$(a).attr("_id"));
    }

</script>

</body>