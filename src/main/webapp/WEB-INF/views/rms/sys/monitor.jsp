<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>预警提醒列表</title>
</head>
<body>

<div class="easyui-layout" id="mainLayout" data-options="fit:true">
    <div data-options="region:'center'">
        <div class="easyui-layout" data-options="fit:true" id="centerLayout">
            <div data-options="region:'center'">
                <table class="easyui-datagrid" title="预警提醒信息" id="datagrid">
                    <thead>
                    <tr>
                        <th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
                        <th field="configName" width="60" align="center" sortable="true">监控名称</th>
                        <th field="configType" width="40" align="center" formatter="globalFormat" sortable="true">监控类型</th>
                        <th field="monitorClass" width="100" align="center" sortable="true">监控实体</th>
                        <th field="actionService" width="60" align="center" sortable="true">执行动作编码</th>
                        <th field="messageTpl" width="100">消息模板</th>
                    </tr>
                    </thead>
                </table>
            </div>

            <div data-options="region:'south'">
                <form action="${ctx}/rms/monitor/save" id="modifyForm">
                    <input type="hidden" name="id">
                    <input type="hidden" name="configType"/>
                    <table width="100%" class="formtable">
                        <tr>
                            <td>监控名称：</td>
                            <td>
                                <input type="text" name="configName" class="easyui-textbox easyui-validatebox"
                                       data-options="validType:'length[1,50]'" invalidMessage="监控名称长度必须在1-50之间"/>
                            </td>
                            <td>监控类型：</td>
                            <td>
                                <select id="selConfigType" class="easyui-combobox" name="selConfigType" style="width:129px"
                                        data-options="panelHeight:80, editable:false, required:true, prompt:'请选择监控类型'">
                                    <option value="EVENT">提醒</option>
                                    <option value="TIMER">预警</option>
                                </select>
                            </td>
                            <td>提醒详情：</td>
                            <td>
                                <select id="selRemindDetail" class="easyui-combobox" name="remindDetail" style="width:129px"
                                        data-options="panelHeight:80, editable:false, prompt: '请选择提醒详情'">
                                    <option value="INSERT">新增</option>
                                    <option value="UPDATE">变更</option>
                                    <option value="DELETE">删除</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>监控实体：</td>
                            <td>
                                <select id="selMonitorClass" class="easyui-combobox" name="monitorClass" style="width:129px"
                                        data-options="editable:false, prompt:'请选择监控实体'">
                                </select>
                            </td>
                            <td>执行动作：</td>
                            <td colspan="3">
                                <select id="selActionService" class="easyui-combobox" name="actionService" style="width:129px"
                                        data-options="editable:false, prompt:'请选择执行动作'">
                                    <option value="socketIOEventNotiCenter">通用提醒消息处理</option>
                                    <option value="socketIOTimerNotiCenter">通用预警消息处理</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>消息模板：</td>
                            <td colspan="5">
                                <textarea name="messageTpl" id="ipMessageTpl" rows="5" cols="150" style="resize: none;"></textarea>
                                <div id="tipMessageContainer">
                                    <div class="help-block" style="color:#F00;">
                                        <div>提醒类型消息模板可以使用以下动态变量：</div>
                                        <p>
                                            <code>&#123;&#123;clazzDesc&#125;&#125;&nbsp;-&nbsp;监控实体名称</code>
                                        </p>
                                        <p>
                                            <code>&#123;&#123;tipFieldDesc&#125;&#125;&nbsp;-&nbsp;主字段名称</code>
                                        </p>
                                        <p>
                                            <code>&#123;&#123;tipFieldValue&#125;&#125;&nbsp;-&nbsp;主字段内容</code>
                                        </p>
                                        <p>
                                            <code>&#123;&#123;changeList&#125;&#125;&nbsp;-&nbsp;变更列表</code>
                                        </p>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <div style="text-align: center; margin-top:15px; margin-right:15px;">
                        <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div data-options="region:'east', collapsed:true, title:'预警提醒规则', onExpand: function() { isExpand = true; }, onCollapse: function() { isExpand = false }"
         id="ruleLayout" style="width:600px">
    </div>
</div>

<script>
    var moduleContext = "monitor", isExpand = false,flagAutoHight,
    // 拿到字典Map, key = tableName, value = columnName
            dict = dictMap([{key: "sys", value: "configType"}]),
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {"sys@configType": ["configType"]};

    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [], flagDgEdit = true, flagDgInsert = true, flagDgDel = true;

    $(function () {
        $utils.ajaxLoadFragments(ctx + "/rms/" + moduleContext + "/monitor-list", function (data) {
            $("#selMonitorClass").combobox("loadData", _.map(data, function (item) {
                return {"id": item.clazz, "text": item.desc, "value": item.clazz};
            }));
        });

        $("#selConfigType").combobox({
            onSelect: function (record) {
                $("#tipMessageContainer").html("");

                if (record.value === "TIMER") {
                    $("#selRemindDetail").combobox("disable");
                    $("#tipMessageContainer").append($("#tplTips4Timer").html());
                } else {
                    $("#selRemindDetail").combobox("enable");
                    $("#tipMessageContainer").append($("#tplTips4EventUpdate").html());
                }
            }
        });

        setTimeout(function () {
            datagrid.datagrid({
                onDblClickRow: function (index, row) {
                    if (!row) return;
                    if (row.configType !== 'TIMER') {
                        $.messager.show({title: "提示", msg: "当前记录无需添加规则项", timeout: 5000, showType: 'slide'});
                        return;
                    }
                    if (!isExpand) $("#mainLayout").layout("expand", "east");
                    var url = row.configType !== 'TIMER' ? './event-rule-view?configId=' + row.id : './timer-rule-view?configId=' + row.id;
                    $("#mainLayout").layout("panel", "east").panel("refresh", url);
                }
            });
        }, 0);

        //编辑框自动适应高度
        if (!flagAutoHight) {
            //var c = $('body>div').eq(0);
            var c = $('#datagrid').parents('.easyui-layout').eq(0);
            //c.layout('panel', 'south').outerHeight();
            //var h = (c.layout('panel', 'south').outerHeight() - ($(window).height() - $('#btnSave').offset().top) +38);
            c.layout('panel', 'south').panel('resize', {height: 200});
            //c.layout('resize', {height: 'auto'});
        }
    });


    function preSelectFn(index, row) {
        $("#selConfigType").combobox("disable");
        $("#selActionService").combobox("disable");
        $("#selMonitorClass").combobox("disable");
        $("#selRemindDetail").combobox("disable");

        $("#tipMessageContainer").html("")
        if (row.configType === 'EVENT') $("#tipMessageContainer").append($("#tplTips4EventUpdate").html());
        else $("#tipMessageContainer").append($("#tplTips4Timer").html());
    }

    function preInsertFn() {
        $("#selConfigType").combobox("enable");
        $("#selActionService").combobox("enable");
        $("#selMonitorClass").combobox("enable");
        $("#selRemindDetail").combobox("enable");
    }

    function preSaveFn() {
        if ($("#selConfigType").combobox('getValue') === 'EVENT')
            $("input[name=configType]").val($("#selRemindDetail").combobox('getValue'));
        else
            $("input[name=configType]").val("TIMER");

    }

    function afterDelSuccess() {
        if (isExpand) $("#mainLayout").layout('collapse', 'east');
        preInsertFn();
    }
</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>
<script type="text/html" id="tplTips4EventUpdate">
    <div class="help-block" style="color:#F00;">
        <div>提醒类型消息模板可以使用以下动态变量：</div>
        <p>
            <code>&#123;&#123;clazzDesc&#125;&#125;&nbsp;-&nbsp;监控实体名称</code>
        </p>
        <p>
            <code>&#123;&#123;tipFieldDesc&#125;&#125;&nbsp;-&nbsp;主字段名称</code>
        </p>
        <p>
            <code>&#123;&#123;tipFieldValue&#125;&#125;&nbsp;-&nbsp;主字段内容</code>
        </p>
        <p>
            <code>&#123;&#123;changeList&#125;&#125;&nbsp;-&nbsp;变更列表</code>
        </p>
    </div>
</script>

<script type="text/html" id="tplTips4Timer">
    <div class="help-block" style="color:#F00;">
        <div>预警类型消息模板可以使用以下动态变量：</div>
        <p>
            <code>&#123;&#123;clazzDesc&#125;&#125;&nbsp;-&nbsp;监控实体名称</code>
        </p>
        <p>
            <code>&#123;&#123;tipFieldDesc&#125;&#125;&nbsp;-&nbsp;主字段名称</code>
        </p>
        <p>
            <code>&#123;&#123;tipFieldValue&#125;&#125;&nbsp;-&nbsp;主字段内容</code>
        </p>
    </div>
</script>
</body>
