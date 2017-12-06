<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>应急救援单位信息</title>
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
    <div data-options="region:'west',split:true" title="应急救援单位信息" style="width:260px;">
        <div class="easyui-panel" style="padding:5px;position: fixed;display: block;left: 0px;top: 28px;" id="pan_m">
            <a href="#" class="easyui-linkbutton" id="btnDel" data-options="plain:true,iconCls:'icon-remove'">删除</a>
            <a href="#" class="easyui-linkbutton" id="btnU" data-options="plain:true,iconCls:'icon-add'">添加单位</a>
            <a href="#" class="easyui-linkbutton" id="btnP" data-options="plain:true,iconCls:'icon-add'">添加人员</a>
        </div>
        <ul class="easyui-tree" id="tree" style="margin-top: 39px;"></ul>
    </div>

    <div data-options="region:'center',border:false">

        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'">
                <table class="easyui-datagrid" title="编缉单位/人员信息" id="datagrid" sortName="unitName" sortOrder="asc">
                    <thead>
                    <tr>
                        <th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
                        <th field="pid" width="100" hidden="true" align="center" sortable="true">父机构ID</th>
                        <th field="unitName" width="100" align="center" sortable="true">单位名称</th>
                        <th field="contactName" width="100" align="center" sortable="true">联系人姓名</th>
                        <th field="contactTel" width="100" align="center" sortable="true">联系人电话</th>
                        <th field="ntype" formatter="globalFormat" width="100" align="center" sortable="true">类型</th>
                    </tr>
                    </thead>
                </table>
            </div>

            <div data-options="region:'south'" style="height:180px;">
                <form action="${ctx}/rms/emergencyUnit/save" id="modifyForm">
                    <input type="hidden" name="id">
                    <input type="hidden" id="pid" name="pid">
                    <table width="100%" class="formtable">
                        <tr>
                            <td>单位名称：</td>
                            <td>
                                <input type="text" id="unitName" name="unitName" class="easyui-textbox easyui-validatebox"
                                     data-options=""
                                >
                            </td>
                            <td>联系人姓名：</td>
                            <td>
                                <input type="text" name="contactName" class="easyui-textbox easyui-validatebox"
                                     data-options=""
                                >
                            </td>
                            <td>联系人电话：</td>
                            <td>
                                <input type="text" name="contactTel" class="easyui-textbox easyui-validatebox"
                                     data-options=""
                                >
                            </td>
                        </tr>
                        <tr>
                            <%--<td>父机构ID：</td>
                            <td>
                                <input type="text" name="pid" class="easyui-textbox easyui-validatebox"
                                       data-options=""
                                >
                            </td>--%>
                            <td>类型：</td>
                            <td>
                                <select class="easyui-validatebox" id="ntype" name="ntype" style="width:130px;"
                                        data-options="">
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
    </div>
</div>
<script>
    var moduleContext = "emergencyUnit";

    // 拿到字典Map, key = tableName, value = columnName
    var dict = dictMap([{key: "", value: "nodeType"}]),
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
                "@nodeType": ["ntype"]
            };
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    //禁用全局验证控件
    flagValidate = true ;


     $(function () {

         //默认机构时，单位名称不能为空
         $("#modifyForm input:visible").eq(0).validatebox({
             required: true
         });

         //将字典数据格式转为下拉数据格式
         var d = _.map(_.keys(dict['@nodeType']),function(n){return {"id":n,"text":dict['@nodeType'][n]}});
         d[0]["selected"]=true;//设置第一个为默认选项
         //拿到类型数据
         $("#ntype").combobox({panelHeight: 'auto',panelMaxHeight: '200',
             data:d,
             valueField: 'id',
             textField: 'text',
             onChange:function(n){
                 var o = $("#modifyForm input:visible");
                 if(n==1){
                     o.validatebox({required: false});
                     o.eq(0).validatebox({ required: true});
                 }else{
                     o.validatebox({required: true});
                 }
             }
         });

         $("#btnSave").linkbutton('disable');
         $('#btnDel').linkbutton('disable');
         <shiro:hasPermission name="rms:emergencyUnit:del">
         $("#btnDel").linkbutton('enable');
         $('#btnDel').click(function(){
             var optree = $('#tree').tree('getSelected');
             if (!optree) {
                 $.messager.alert("提示", "请选择一条数据再进行操作！");
                 return false;
             }

             if (optree.children.length!=0) {
                 $.messager.alert("提示", "请删除下层的数据再进行操作！");
                 return false;
             }

             $.messager.confirm("提示", "确定要删除选中的记录吗？", function (r) {
                 if (r) {
                     $.post(ctx + "/rms/" + moduleContext + "/delete", {id:optree.id}, function (data) {
                         if (data.code === 1) {
                             datagrid.datagrid("reload").datagrid("clearSelections");
                             form.form("reset");
                             $("input[name=id]").val("");
                         }
                         $.messager.show({title: "提示", msg: data.message, timeout: 5000, showType: 'slide'});
                         datagrid.datagrid("reload");
                         $('#tree').tree("reload");
                     });
                 }
             });
         })
         </shiro:hasPermission>

         $('#btnU').linkbutton('disable');
         $('#btnP').linkbutton('disable');
         <shiro:hasPermission name="rms:emergencyUnit:insert">
         $("#btnSave").linkbutton('enable');
         $("#btnU").linkbutton('enable');
         $('#btnU').click(function(){
             if(!$(this).linkbutton('options').disabled){
                 var optree = $('#tree').tree('getSelected');
                 if(!optree) {
                     $.messager.show({title: "提示", msg: "请选择单位后，再添加！", timeout: 5000, showType: 'slide'});
                     return false;
                 }

                 /*if(!!optree.attributes && optree.attributes.ntype==2){
                     $.messager.show({title: "提示", msg: "此为员工结点，禁止添加！", timeout: 3000, showType: 'slide'});
                     return;
                 }*/
                 datagrid.datagrid("reload").datagrid("clearSelections");
                 form.form("reset");
                 $("#pid").val(optree.id);
                 $("input[name=id]").val("");
                 $("#ntype").combobox('setValue',1);
             }
         })

         $("#btnP").linkbutton('enable');
         $('#btnP').click(function(){
             if(!$(this).linkbutton('options').disabled){
                 var optree = $('#tree').tree('getSelected');
                 if(!optree) {
                     $.messager.show({title: "提示", msg: "请选择单位后，再添加！", timeout: 3000, showType: 'slide'});
                     return;
                 }

                 /*if(!!optree.attributes && optree.attributes.ntype==2){
                     $.messager.show({title: "提示", msg: "此为员工结点，禁止添加！", timeout: 5000, showType: 'slide'});
                     return;
                 }*/
                 datagrid.datagrid("reload").datagrid("clearSelections");
                 form.form("reset");
                 $("#pid").val(optree.id);
                 $("#unitName").textbox('setValue',optree.text);
                 $("input[name=id]").eq(1).val("");
                 $("#ntype").combobox('setValue',2);
             }
         })
         </shiro:hasPermission>

    });

    /**
     * form: crud表单对象
     * datagrid: 数据表格对象
     * dictColumns: 字典字段
     * comboboxFilterDefinetion: 字典类型字段过滤定义 （需传入字段名及JSON枚举值）
     *      例如：[{value: '', text: '全部'}, {value: '0', text: '不可用'}, {value: '1', text: '可用'}]
     */
    var form,selectedId, datagrid, dictColumns, comboboxFilterDefinetion = function (fieldCode, enumValues) {
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
    },flagAutoHight,flagValidate;//编辑框自动适应高度开关，所有非空验证开关
    $(function () {
        $('#tree').tree({
            url: '${ctx}/rms/emergencyUnit/getMenuList?ntype=2',
            lines:true,
            onClick: function (node) {
                //form.form("reset");
                //$("input[name=id]").val("");

                $("#pid").val(node.id);
                selectedId = node.id;
                datagrid.datagrid("reload", {"pid": selectedId});
            },
            onLoadSuccess:function(){
                $("#menu_left").tree('collapseAll');
            }
        });

        form = $("#modifyForm");

        //增加非空验证 wjp 2016-4-5
        if(!flagValidate){
            $("#modifyForm input:visible").validatebox({
                required: true
            });
        }
        //设置下拉框的最大高度及自适应 wjp 2016-4-5
        $('.easyui-combobox').combobox({panelHeight:'auto',panelMaxHeight:'200'});
        //编辑框自动适应高度
        if(!flagAutoHight){
            //var c = $('body>div').eq(0);
            var c = $('#datagrid').parents('.easyui-layout').eq(0);
            c.layout('panel','south').outerHeight();
            var h = (c.layout('panel','south').outerHeight()-($(window).height()-$('#btnSave').offset().top)+38);
            c.layout('panel','south').panel('resize', {height:h});
            c.layout('resize', {height:'auto'});
        }

        <shiro:hasPermission name="rms:emergencyUnit:edit">
        $("#datagrid").datagrid({
            onSelect: function (index, row) {
                form.form("load", $utils.ajaxLoadFragments(ctx + "/rms/" + moduleContext + "/get?id=" + row.id));
            }
        })
        $("#btnSave").linkbutton('enable');
        </shiro:hasPermission>


        dictColumns = $("#datagrid th[formatter=globalFormat]").map(function () {
            return $(this).attr("field");
        });

        datagrid = $("#datagrid").datagrid({
            rownumbers: true, url: "./list", method: 'get',
            queryParams:{"pid":selectedId},
            fit: true, fitColumns: true, idField: "id", singleSelect: true,
            showFooter: true, remoteSort: false, multiSort: false,
            toolbar: [
                <shiro:hasPermission name="rms:emergencyUnit:insert">
                {
                text: "新增主单位", iconCls: "icon-add",
                handler: function () {
                    datagrid.datagrid("reload").datagrid("clearSelections");
                    form.form("reset");
                    $("#pid").val("0");
                    $("#ntype").combobox('setValue',1);
                    $("input[name=id]").val("");
                }
            }</shiro:hasPermission>]
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
            /*try{
                if(!!$('#tree').tree('getSelected') && $('#tree').tree('getSelected').attributes.ntype==2){
                    $.messager.show({title: "提示", msg: "此为员工节/点，禁止添加！", timeout: 5000, showType: 'slide'});
                    return;
                }
            }catch (a){}*/
            if($(this).linkbutton('options').disabled == false && $('#modifyForm').form('validate')){
                $.post(ctx + "/rms/" + moduleContext + "/save", $("#modifyForm").serialize(), function (data) {
                    if (data.code === 1) {
                        datagrid.datagrid("reload");
                        $('#tree').tree("reload");
                    }
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