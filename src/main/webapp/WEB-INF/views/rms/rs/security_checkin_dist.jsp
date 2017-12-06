<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>安检口分配</title>
    <link rel="stylesheet" href="${ctxAssets}/scripts/gantt/gantt.css">
    <link rel="stylesheet" href="${ctxAssets}/scripts/select2/dist/css/select2.min.css">
    <link rel="stylesheet" href="${ctxAssets}/scripts/spectrum-1.8.0/spectrum.css"/>
    <script src="${ctxAssets}/scripts/spectrum-1.8.0/spectrum.js"></script>
</head>
<body>
<div class="easyui-layout" id="main-layout" fit="true">
    <div data-options="region: 'south',
        height: 300,
        hideCollapsedContent: false,
        collapsed: true,
        maximizable: true,
        onMaximize: maximizeOrRestoreHandler,
        onRestore: maximizeOrRestoreHandler,
        onResize:ganttPanelResizeHandler,
        onExpand: _panelExpandHandler,
        onCollapse: _panelCollapseHandler,split:true" border="false" title="图形展示" id="graphic-panel">
        <div class="gantt" style="height: 265px; overflow: hidden; position: absolute;"></div>
    </div>
    <div data-options="region:'center'" id="dgContainer" style="overflow-y:hidden;"></div>
</div>
<div id="view-setting-dialog" title="显示设置" class="easyui-dialog" data-options="closed:true, modal:true" style="width: 265px; height:500px;"></div>
<script src="${ctxAssets}/scripts/metadata-dev/concurrent.thread.js"></script>
<script src="${ctxAssets}/scripts/gantt/collision.detection.plugin.js"></script>
<script src="${ctxAssets}/scripts/gantt/gantt.js"></script>
<script src="${ctxAssets}/scripts/layer/src/layer.js"></script>
<script src="${ctxAssets}/scripts/jquery-ui/jquery-ui.js"></script>
<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script src="${ctxAssets}/scripts/My97DatePicker/WdatePicker.js"></script>
<script src="${ctxAssets}/scripts/aui-artTemplate/dist/template.js"></script>
<script src="${ctxAssets}/scripts/select2/dist/js/select2.min.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.resource.dist.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.Ext.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/resource-dist/security_checkin_dist.js"></script>
<script>
    <shiro:hasPermission name="rms:security_checkin_dist:auto_allo">flagAutoAllocation = true, flagMockDist = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:security_checkin_dist:manu_allo">flagManualAllocation = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:security_checkin_dist:gant">flagFlightGant = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:security_checkin_dist:settime_oci">flagEndFlightOccupation = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:security_checkin_dist:cancel_allo">flagCancelAllocation = true;
    </shiro:hasPermission>
</script>
<script type="text/html" id="datagridTpl">
    <table class="easyui-datagrid" id="datagrid" group="false" adjust="true" data-options="fitColumns:false, fit:true, remoteSort:false">
        <thead>
        <tr>
            <th field="id" width="100" hidden="true" sortable="true">主键</th>
            <th field="flightDynamicCode" width="50" hidden="true" align="center" sortable="true">航班号</th>
            <th field="placeNum" width="80" align="center" hidden="true" sortable="true">机位</th>
            <th field="status" width="70" hidden="true" align="center" sortable="true">状态编码</th>
            <th field="flightNatureCode" width="50" hidden="true" align="center" sortable="true">出港性质</th>
            <th field="flightDynamicId" width="100" hidden="true" sortable="true">航班动态编号</th>
            <th field="flightCompanyCode" width="50" hidden="true" align="center" sortable="true">航空公司</th>
            <th hidden="true" field="expectedSecurityCheckinNum" width="100" align="center" sortable="true">预计占用数量</th>
            <th field="agentCode" width="100" align="center" sortable="true">代理ID</th>
            <th field="agentName" width="100" align="center" sortable="true">代理名称</th>
            <th field="planDate" width="100" align="center" formatter="formatDate" sortable="true">计划日期</th>
            <th field="vitrualFlightNumber" formatter="vitrualFlightNumberFmt" width="100" align="center" sortable="true">航班号</th>
            <th field="aircraftNum" width="80" align="center" sortable="true">机号</th>
            <th field="aircraftTypeCode" width="80" align="center" sortable="true">机型</th>
            <th field="statusName" width="70" align="center" sortable="true">状态</th>
            <th field="flightNatureName" width="80" align="center" sortable="true">性质</th>
            <th field="flightDynamicNature" width="80" formatter="natureFmt" align="center" sortable="true">属性</th>

            <th field="passAirport1Code" width="100" align="center" formatter="globalPlaceFmt" sortable="true">经停1</th>
            <th field="passAirport2Code" width="100" align="center" formatter="globalPlaceFmt" sortable="true">经停2</th>
            <th field="ext11" width="100" align="center" formatter="globalPlaceFmt" sortable="true">经停3</th>
            <th field="ext13" width="100" align="center" formatter="globalPlaceFmt" sortable="true">经停4</th>
            <th field="ext15" width="100" align="center" formatter="globalPlaceFmt" sortable="true">经停5</th>
            <th field="ext17" width="100" align="center" formatter="globalPlaceFmt" sortable="true">经停6</th>
            <th field="arrivalAirportCode" width="100" align="center" formatter="globalPlaceFmt" sortable="true">到达</th>

            <th field="departurePlanTime" formatter="dateFmt" width="70" align="center" sortable="true">计飞</th>
            <th field="etd" formatter="dateFmt" width="70" align="center" sortable="true">预飞</th>
            <th field="atd" formatter="dateFmt" width="70" align="center" sortable="true">实飞</th>

            <th field="inteSecurityCheckinCode" width="150" align="center" sortable="true">国内航段</th>
            <th field="intlSecurityCheckinCode" width="150" align="center" sortable="true">国际航段</th>
            <th field="expectedStartTime" formatter="dateFmt" width="70" align="center" sortable="true">预计开始</th>
            <th field="expectedOverTime" formatter="dateFmt" width="70" align="center" sortable="true">预计结束</th>
            <th field="inteActualStartTime" formatter="dateFmt" width="70" align="center" sortable="true">实际开始</th>
            <th field="inteActualOverTime" formatter="dateFmt" width="70" align="center" sortable="true">实际结束</th>

            <th field="delayResonName" width="120" align="center" sortable="true">延误原因</th>
        </tr>
        </thead>
    </table>
</script>
</body>
