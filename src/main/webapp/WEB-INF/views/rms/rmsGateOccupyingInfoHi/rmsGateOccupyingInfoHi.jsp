<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>机位使用量统计</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="机位使用量统计" id="datagrid" sortName="flightDynamicId" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="flightDynamicId" width="100" align="center" sortable="true">航班ID</th>
				<th field="flightDynamicCode" width="100" align="center" sortable="true">航班号</th>
				<th field="expectedGateNum" width="100" align="center" sortable="true">预计使用机位个数</th>
				<th field="actualGateNum" width="100" align="center" sortable="true">实际占用使用编号</th>
				<th field="startTime" width="100" align="center" sortable="true">开始占用时间</th>
				<th field="overTime" width="100" align="center" sortable="true">结束占用时间</th>
				<th field="leave" width="100" align="center" sortable="true">中途是否离开</th>
				<th field="leaveTime" width="100" align="center" sortable="true">中途离开开始时间</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<script>
    var moduleContext = "rmsGateOccupyingInfoHi";
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:rmsGateOccupyingInfoHi:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:rmsGateOccupyingInfoHi:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:rmsGateOccupyingInfoHi:del">flagDgDel=true;</shiro:hasPermission>
    flagDgExp=true;

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
                    if (value == '') datagrid.datagrid('removeFilterRule', fieldCode);
                    else datagrid.datagrid('addFilterRule', {field: fieldCode, op: ['equal'], value: value});
                    datagrid.datagrid('doFilter');
                }
            }
        }
    }, toolbarDate = [];//按钮数据

    var flagAutoHight, flagValidate;//编辑框自动适应高度开关，所有非空验证开关
    var flagDgImp,flagDgExp;  //导入、导出按钮开关

    $(function () {

        //添加导入按钮；
        if (flagDgImp) {
            toolbarDate.push('-');
            toolbarDate.push({
                text: "导入",
                iconCls: "icon-tip",
                handler: function () {

                }
            });
        }
        //添加导出按钮；
        if (flagDgExp) {
            toolbarDate.push('-');
            toolbarDate.push({
                text: "导出",
                iconCls: "icon-tip",
                handler: function () {

                }
            });
        }

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

        datagrid = $("#datagrid").datagrid({
            rownumbers: true, url: ctx + "/rms/rs/flightDynamics", method: 'get', fitColumns: false, idField: "id", singleSelect: true,
            showFooter: true, remoteSort: false, multiSort: false, toolbar: toolbarDate
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