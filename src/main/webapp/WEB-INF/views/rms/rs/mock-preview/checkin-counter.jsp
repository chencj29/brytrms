<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>机位分配</title>
    <link rel="stylesheet" href="${ctxAssets}/scripts/gantt/gantt.css"/>
    <link rel="stylesheet" href="${ctxAssets}/scripts/select2/dist/css/select2.min.css">
    <link rel="stylesheet" href="${ctxAssets}/scripts/spectrum-1.8.0/spectrum.css"/>
    <script src="${ctxAssets}/scripts/spectrum-1.8.0/spectrum.js"></script>
    <style type="text/css">
        #table-status tr {
            cursor: pointer;
        }

        .table-bordered {
            border-collapse: collapse;
        }

        .table-bordered > tbody > tr > td {
            border: 1px dotted #aaa;
            padding: 2px;
            font-size: 10px;
        }
    </style>
</head>
<body>
<div class="easyui-layout" id="main-layout" fit="true">
    <input type="hidden" name="infoId" id="infoId" type="text" value="${infoId}"/>
    <input type="hidden" name="resourceType" id="resourceType" value="${resourceType}" type="text"/>
    <input type="hidden" name="path" id="path" value="${path}" type="text"/>
    <div data-options="region: 'south', height: 300, hideCollapsedContent: false, collapsible: false, maximizable: false" title="图形展示" id="graphic-panel">
        <div class="gantt" style="height: 265px; width:100%; overflow: hidden; position: absolute;"></div>
    </div>
    <div data-options="region:'center'" id="dgContainer">
        <table id="dataGrid4Preview" class="easyui-datagrid">
            <thead>
            <tr>
                <th field="id" width="66" hidden="true">主键</th>
                <th field="flightDynamicCode" hidden="true" width="80" align="center">航班号</th>
                <th field="flightDynamicId" width="100" hidden="true" order="false">航班动态ID</th>
                <th field="detailId" width="10" align="center" hidden="true">模拟分配详情ID</th>
                <th field="planDate" width="80" align="center" formatter="formatDate">计划日期</th>
                <th field="expectedStartTime" formatter="dateFmt" hidden="true" width="100" align="center">预计开始</th>
                <th field="expectedOverTime" formatter="dateFmt" hidden="true" width="100" align="center">预计结束</th>
                <th field="vitrualFlightNumber" formatter="vitrualFlightNumberFmt" width="100" align="center">航班号</th>
                <th field="aircraftNum" width="50" align="center">机号</th>
                <th field="aircraftTypeCode" width="50" align="center">机型</th>
                <th field="flightDynamicNature" width="80" formatter="natureFmt" align="center">属性</th>
                <th field="inteCheckingCounterCode" width="100" align="center">国内航段</th>
                <th field="intlCheckingCounterCode" width="100" align="center">国际航段</th>
                <th field="info" width="200" align="center">备注</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
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
<script src="${ctxAssets}/scripts/metadata-dev/resource-dist/mock-preview/checkin_counter_dist.js"></script>
</body>