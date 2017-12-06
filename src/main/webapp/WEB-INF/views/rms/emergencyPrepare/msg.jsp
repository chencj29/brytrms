<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>发出预案消息</title>
    <style type="text/css">
        .tree-node {
            height: 18px;
            white-space: nowrap;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div style="height:239px">
    <form id="send_new_message_form" method="post" style="width: 100%">
        <table cellpadding="5" style="width: 100%">
            <tr>
                <td>消息接收人:</td>
                <td>
                    <select class="easyui-validatebos" id="rec_id" name="rec_id" style="width:228px;" data-options="">
                    </select>
                </td>
            </tr>
            <tr>
                <td>消息内容:</td>
                <td>
                    <input class="easyui-textbox" id="et-message" name="message" data-options="multiline:true" style="height:150px;width: 100%;"/>
                </td>
            </tr>
        </table>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" id="submit" onclick="javascript:;">发送消息</a>
        </div>
    </form>
</div>
<script type="text/html" id="msgTemplate">
    应急事件名称:{{emEventName}}
    应急救援方案ID:{{emPCode}}
    应急救援方案名称:{{emPName}}
    救援等级:{{emLevel}}
    救援单位:{{emUnit}}
</script>
<%--<script type="text/html" id="msgTemplate">
    <table>">
        <tr>
            <td>
                <span>应急救援预案</span>
            </td>
        </tr>
    </table>
    <table class="table table-striped table-condensed table-bordered table_repair">
        <tr>
            <td style="width:6%;">应急事件名称:</td>
            <td style="width:14%;">{{emEventName}}</td>
            <td style="width:6%;">应急救援方案ID</td>
            <td style="width:14%;">{{emPCode}}</td>
            <td style="width:6%;">应急救援方案名称</td>
            <td style="width:14%;">{{emPName}}</td>
            <td style="width:6%;">救援等级</td>
            <td style="width:14%;">{{emLevel}}</td>
            <td style="width:6%;">救援单位</td>
            <td style="width:14%;">{{emUnit}}</td>
        </tr>
    </table>
</script>--%>

<script src="${ctxAssets}/scripts/aui-artTemplate/dist/template.js"></script>
<script>
    $(function () {
        $('#rec_id').combotree({
            url: ctx + '/sys/office/treeNode?type=3',
            //url: ctx + '/rms/emergencyUnit/getMenuList?ntype=2',
            required: true,
            multiple: true
        });
    })

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    //给父页面传值
    $('#submit').on('click', function(){
        //debugger
        var row = parent.$('#datagrid').datagrid('getSelected');
        row.emUnit = parent.$('#modifyForm #_emergencyUnit').combotree('getText');
        var msg = template('msgTemplate',row);

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

        var msg = "emergencyPrepare:"+msg+$('#et-message').val();

        // 提交数据
        $.post(ctx + '/ams/message/newmessage',{rec_id:data.join(","),message:msg}, function (resp) {
            if (resp && resp.code == 1) {
                $.messager.slide('消息发送成功!');
                $('#rec_id').combotree('clear');
                $('#et-message').textbox('clear');

                setTimeout(function () {
                    parent.MESSAGE_SOCKET_IO_CONNECT.emit('messagenotify', resp.message);
                },1000);

                parent.layer.tips('消息已发送，请到<a style="color:red;" href="javascript:parent.addTab(\'救援消息\',\'${ctx}/rms/emergencyPrepare/handle\',\'default\');">[应急救援处理]</a></small>查看消回执！', '#datagrid', {time: 5000});
                parent.layer.close(index);
            } else {
                console.log(resp);
                $.messager.alert('提示', '发送消息失败,请稍后重试!');
            }
        });
    });



</script>
</body>