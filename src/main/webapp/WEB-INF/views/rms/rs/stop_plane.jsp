<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>停场飞机</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="停场飞机(只显示停场飞机及10天内处理过的飞机)" id="datagrid">
            <thead>
            <tr>
                <th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
                <th field="planDate" width="100" sortable="true" order="false" formatter="dateFmt">日期</th>
                <th field="aircraftNum" width="100" align="center" sortable="true">飞机号</th>
                <th field="placeNum" width="100" align="center" sortable="true">机位号</th>
                <th field="placeStatus" width="100" align="center" sortable="true" formatter="statusFmt">机位占用状态</th>


                <th field="ext1" width="100" align="center" sortable="true">登机口(冲突时间计录)</th>
                <th field="ext2" width="100" align="center" sortable="true">非次日航班使用时间计录</th>
                <th field="ext3" width="100" hidden="true" align="center" sortable="true">扩展3</th>
            </tr>
            </thead>
        </table>
    </div>

    <div data-options="region:'south'" style="height:100px;">
        <form action="${ctx}/rms/aircraftBoarding/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>机位占用状态：</td>
                    <td>
                        <input type="radio" name="placeStatus" value="1" checked>&nbsp;未占用
                        <input type="radio" name="placeStatus" value="0">&nbsp;占用
                    </td>
                    <td>
                        <div style="text-align: center; margin-top:15px; margin-right:15px;">
                            <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
                        </div>
                    </td>
                    <td width=220></td>
                </tr>
            </table>

        </form>

    </div>
</div>

<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script>
    var moduleContext = "stopPlane";

    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:stopPlane:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:stopPlane:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:stopPlane:del">flagDgDel=true;</shiro:hasPermission>

    flagValidate = true; //关闭全部必填

    $(function () {

    });

    function statusFmt(v, r, i, f) {
        return (v == 1) ? '未占用' : '占用';
    }

    function dateFmt(v, r, i, f) {
        return !v ? "" : moment(v).format("YYYY-MM-DD");
    }
</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>
</body>
</html>