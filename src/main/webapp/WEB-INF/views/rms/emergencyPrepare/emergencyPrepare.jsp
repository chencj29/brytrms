<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>应急救援预案</title>
    <style type="text/css">
        .tree-node {
            height: 18px;
            white-space: nowrap;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="应急救援预案" id="datagrid" sortName="emEventName" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="emEventName" width="100" align="center" sortable="true">应急事件名称</th>
				<th field="emPCode" width="100" align="center" sortable="true">应急救援方案ID</th>
				<th field="emPName" width="100" align="center" sortable="true">应急救援方案名称</th>
				<th field="emLevel" width="100" align="center" sortable="true">救援等级</th>
                <th data-options="field:'_emergencyUnit',width:100,formatter:formatEU">救援单位</th>
                <th data-options="field:'_operate',width:100,align:'center',formatter:formatOper">操作</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/emergencyPrepare/save" id="modifyForm">
            <input type="hidden" name="id">
            <input type="hidden" name="emPCode" id="emPCode">
            <table width="100%" class="formtable">
                <tr>
                    <td>应急事件名称：</td>
                    <td>
						<input type="text" name="emEventName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>应急救援方案名称：</td>
                    <td>
                    <select class="easyui-validatebox" id="emPName" name="emPName" style="width:130px;"
                    data-options="">
                    </select>
                    </td>
                    <td>救援等级：</td>
                    <td>
                    <input type="text" name="emLevel" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    >
                    </td>
                    <td>救援单位：</td>
                    <td>
                        <select class="easyui-validatebos" id="_emergencyUnit" name="_emergencyUnit" style="width:130px;" data-options="">
                        </select>
                    </td>
                </tr>
            </table>
            <div style="text-align: center; margin-top:15px; margin-right:15px;">
                <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
            </div>
        </form>

    </div>
</div>

<script src="${ctxAssets}/scripts/layer/layer.js"></script>
<script>
    var moduleContext = "emergencyPrepare";

    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:emergencyPrepare:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:emergencyPrepare:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:emergencyPrepare:del">flagDgDel=true;</shiro:hasPermission>

     $(function () {
        //拿到应急救援方案名称数据
        $("#emPName").combobox({
            url: ctx + '/rms/emergencyPlan/jsonData',
            valueField: 'emPlanName',
            textField: 'emPlanName',
            panelHeight: 'auto',
            panelMaxHeight: '200',
            onSelect : function(rec){ //改变单选值时，通过选中值后回填内容
                form.form('load',{'emPCode':rec.emPlanCode});
            }
        });

        $('#_emergencyUnit').combotree({
            url: ctx + '/rms/emergencyUnit/getMenuList?ntype=1',
            required: true,
            multiple:true
        });

    });

    function formatOper(val,row,index){
        var htm='<a href="#" onclick="msgObj(\''+index+'\')">[发出预案消息]</a>&nbsp;';
        /*htm+='<a href="#" onclick="editObj(\''+row.id+'\')">[编缉]</a>&nbsp;';*/
        htm+='<a href="#" onclick="delObj(\''+row.id+'\')">[删除]</a>';
        return htm;
    }

    var EUDatas= {};
    var EUNames = function (epid) {
        $.ajax({
            dataType: "json", type: "get",
            url: ctx + "/rms/" + moduleContext + "/findByEUNames?epid="+epid,
            async: false,
            success: function (resp) {
                if(resp.code==1){
                    EUDatas[epid]=resp.result[epid];
                }
            }
        });
    };

    var EUDatas= {};
    function formatEU(val,row,index){
        if(row.id){
            EUNames(row.id);
            return EUDatas[row.id].names;
        }
    }

    //发出预案消息
    function msgObj(){
        layer.open({
            type: 2,
            title: '发出预案消息。',
            shadeClose: true,
            shade: false,
            maxmin: true, //开启最大化最小化按钮
            area: ['500px', '280px'],
            content: ctx + '/rms/emergencyPrepare/msg'
        });
    }

    function editObj(id){
        form.form("load", $utils.ajaxLoadFragments(ctx + "/rms/" + moduleContext + "/get?id=" + id));
        $('#_emergencyUnit').combotree('setValues',EUDatas[id].euids);
    }

    function delObj(id){
        $.messager.confirm("提示", "确定要删除选中的记录吗？", function (r) {
            if (r) {
                $.post(ctx + "/rms/" + moduleContext + "/delete", {id: id}, function (data) {
                    if (data.code === 1) {
                        datagrid.datagrid("reload").datagrid("clearSelections");
                        form.form("reset");
                        $("input[name=id]").val("");
                    }
                    $.messager.show({title: "提示", msg: data.message, timeout: 5000, showType: 'slide'});
                    datagrid.datagrid("reload");
                });
            }
        });
    }

	/*
	//自定义验证规则。例如，定义一个 minLength 验证类 验证长度
    $.extend($.fn.validatebox.defaults.rules, {
		minLength: {
			validator: function(value, param){
				return value.length >= param[0];
			},
			message: 'Please enter at least {0} characters.'
		}
	});*/

    /**
     * form: crud表单对象
     * datagrid: 数据表格对象
     * dictColumns: 字典字段
     * comboboxFilterDefinetion: 字典类型字段过滤定义 （需传入字段名及JSON枚举值）
     *      例如：[{value: '', text: '全部'}, {value: '0', text: '不可用'}, {value: '1', text: '可用'}]
     */
    var form, datagrid, dictColumns, comboboxFilterDefinetion = function (fieldCode, enumValues) {
        return {
            field: fieldCode, type: 'combobox', options: {
                panelHeight: 'auto', data: enumValues, onChange: function (value) {
                    if (value == '')
                        datagrid.datagrid('removeFilterRule', fieldCode);
                    else
                        datagrid.datagrid('addFilterRule', {
                            field: fieldCode, op: ['equal'], value: value
                        });

                    datagrid.datagrid('doFilter');
                }
            }
        }
    },toolbarDate=[] ;//按钮数据

    var flagAutoHight, flagValidate;//编辑框自动适应高度开关，所有非空验证开关
    var flagDgEdit,flagDgDel,flagDgInsert,flagDgImp;  //修改、删除、新增、导入按钮开关

    $(function () {
        form = $("#modifyForm");


        //增加非空验证 wjp 2016-4-5
        if (!flagValidate) {
            $("#modifyForm input:visible").validatebox({
                required: true
            });
        }

        //编辑框自动适应高度
        if (!flagAutoHight) {
            //var c = $('body>div').eq(0);
            var c = $('#datagrid').parents('.easyui-layout').eq(0);
            c.layout('panel', 'south').outerHeight();
            var h = (c.layout('panel', 'south').outerHeight() - ($(window).height() - $('#btnSave').offset().top) + 38);
            c.layout('panel', 'south').panel('resize', {height: h});
            c.layout('resize', {height: 'auto'});
        }

        $("#btnSave").linkbutton('disable');
        //添加新增按钮；
        if(flagDgInsert){
            toolbarDate.push({
                text: "新增", iconCls: "icon-add",
                handler: function () {
                    datagrid.datagrid("reload").datagrid("clearSelections");
                    form.form("reset");
                    $('#_emergencyUnit').combotree('clear');
                    $("input[name=id]").val("");
                }
            });
            $("#btnSave").linkbutton('enable');
        }

        //添加删除铵钮；
        if(flagDgDel){
            toolbarDate.push({
                text: "删除",
                iconCls: "icon-remove",
                handler: function () {
                    var selected = datagrid.datagrid("getSelected");
                    if (!selected) {
                        $.messager.alert("提示", "请选择一条数据再进行操作！");
                        return false;
                    }
                    delObj(selected.id);
                }
            });
        }

        //添加导入按钮；
        if(flagDgImp){
            toolbarDate.push('-');
            toolbarDate.push({
                text: "导入",
                iconCls: "icon-tip",
                handler: function () {

                }
            });
        }

        //添加点击编辑事件；
        if(flagDgEdit){
            $("#datagrid").datagrid({
                onSelect: function (index, row) {
                    editObj(row.id);
                }
            });
            $("#btnSave").linkbutton('enable');
        }



        dictColumns = $("#datagrid th[formatter=globalFormat]").map(function () {
            return $(this).attr("field");
        });

        dictColumns = $("#datagrid th[formatter=globalFormat]").map(function () {
            return $(this).attr("field");
        });

        datagrid = $("#datagrid").datagrid({
            rownumbers: true, url: "./list", method: 'get',
            fit: ((typeof(isFit) != 'undefined' && isFit == false) ? false : true),
            fitColumns: ((typeof(isFitColumns) != 'undefined' && isFitColumns == false ) ? false : true),
            idField: "id", singleSelect: true,
            showFooter: true, remoteSort: false, multiSort: false,
            toolbar: toolbarDate
        });


        //拿到specialFilteringColumnDefinition进行合并操作
        _.each(dictColumns, function (field) {

            var enumValues = [{value: '', text: '全部'}], dictValue = getAllDictValue(field);
            // 遍历可选值，构造datagrid-filter能够识别的JSON对象
            _.each(_.allKeys(dictValue), function (key) {
                enumValues.push({value: _.propertyOf(dictValue)(key), text: _.propertyOf(dictValue)(key)});
            });

            specialFilteringColumnDefinition.push(comboboxFilterDefinetion(field, enumValues));
        });
        datagrid.datagrid('enableFilter', specialFilteringColumnDefinition);


        $("#btnSave").click(function () {
            if ($('#modifyForm').form('validate')) {
                $.post(ctx + "/rms/" + moduleContext + "/save", $("#modifyForm").serialize(), function (data) {
                    if (data.code === 1) datagrid.datagrid("reload");
                    $.messager.show({title: "提示", msg: data.message, timeout: 5000, showType: 'slide'});
                });
            }
        });
    });

    /***
     * 通用的Easyui字典值转换方法
     * @param value 值
     * @param row 行
     * @param index 索引
     * @param field 字段名
     */
    function globalFormat(value, row, index, field) {
        return _.propertyOf(getAllDictValue(field))(value);
    }

    /**
     * 拿到可用的字典值
     * @param fieldCode 字段名
     */
    function getAllDictValue(field) {
        return _.last(_.values(_.pick(dict, _.last(_.keys(_.pick(mapping, function (value, key, object) {
            return _.contains(value, field);
        }))))));
    }

</script>



<%--<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>--%>
</body>