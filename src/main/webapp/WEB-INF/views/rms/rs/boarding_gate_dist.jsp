<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>登机口分配</title>
    <link rel="stylesheet" href="${ctxAssets}/scripts/gantt/gantt.css">
    <link rel="stylesheet" href="${ctxAssets}/scripts/select2/dist/css/select2.min.css">
    <link rel="stylesheet" href="${ctxAssets}/scripts/spectrum-1.8.0/spectrum.css"/>
    <script src="${ctxAssets}/scripts/spectrum-1.8.0/spectrum.js"></script>
    <style type="text/css">
        .table-bordered{
            border-collapse: collapse;
        }
        .table-bordered > tbody > tr > td {
            border: 1px solid #aaa;
            border-style: dotted;
            padding: 2px;
            font-size: 10px;
        }
    </style>
</head>
<body>
<div class="easyui-layout" id="main-layout" fit="true">
    <div data-options="region: 'south',
        height: 300,
        hideCollapsedContent: false,
        collapsed: true,
        maximizable: true,
        onResize: ganttPanelResizeHandler,
        onMaximize: maximizeOrRestoreHandler,
        onRestore: maximizeOrRestoreHandler,
        onResize:ganttPanelResizeHandler,
        onExpand: _panelExpandHandler,
        onCollapse: _panelCollapseHandler,split:true" border="false" title="图形展示" id="graphic-panel">
        <div class="gantt" style="height: 265px; overflow: hidden; position: absolute;"></div>
    </div>
    <div data-options="region:'center'" id="dgContainer" style="overflow-y:hidden;"></div>
    <div data-options="region:'east',collapsed: true" title="手动分配" id="dgRight" style="width:230px;padding:10px"></div>
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
<script src="${ctxAssets}/scripts/metadata-dev/resource-dist/boarding_gate_dist.js"></script>
<script>
    <shiro:hasPermission name="rms:boarding_gate_dist:auto_allo">flagAutoAllocation = true, flagMockDist = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:boarding_gate_dist:manu_allo">flagManualAllocation = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:boarding_gate_dist:gant">flagFlightGant = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:boarding_gate_dist:settime_oci">flagEndFlightOccupation = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:boarding_gate_dist:cancel_allo">flagCancelAllocation = true;
    </shiro:hasPermission>
</script>
<script type="text/html" id="datagridTpl">
    <table class="easyui-datagrid" id="datagrid" group="false" adjust="true" data-options="fitColumns:false,fit:true, remoteSort:false">
        <thead>
        <tr>
            <th field="id" width="100" align="center" hidden="true" sortable="true">主键</th>
            <th field="flightDynamicCode" width="50" align="center" hidden="true" sortable="true">航班号</th>
            <th field="status" width="70" align="center" hidden="true" sortable="true">状态码</th>
            <th field="flightNatureCode" width="50" hidden="true" align="center" sortable="true">出港性质</th>
            <th field="flightDynamicId" width="100" hidden="true" sortable="true">航班动态编号</th>
            <th field="flightCompanyCode" width="50" hidden="true" align="center" sortable="true">航空公司</th>
            <th hidden="true" field="expectedCarouselNum" width="50" align="center" sortable="true">预计占用数量</th>
            <th field="agentCode" width="60" align="center" sortable="true">代理ID</th>
            <th field="agentName" width="80" align="center" sortable="true">代理名称</th>
            <th field="planDate" width="80" align="center" formatter="formatDate" sortable="true">计划日期</th>

            <th field="vitrualFlightNumber" formatter="vitrualFlightNumberFmt" width="50" align="center" sortable="true">航班号</th>

            <th field="aircraftNum" width="60" align="center" sortable="true">机号</th>
            <th field="aircraftTypeCode" width="60" align="center" sortable="true">机型</th>
            <th field="placeNum" width="60" align="center" formatter="colorFn" sortable="true">机位</th>
            <th field="statusName" width="70" align="center" sortable="true">状态</th>
            <th field="flightNatureName" width="60" align="center" sortable="true">性质</th>
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

            <th field="inteCarouselCode" width="150" align="center" sortable="true">国内航段</th>
            <th field="intlCarouselCode" width="150" align="center" sortable="true">国际航段</th>
            <th field="expectedStartTime" formatter="dateFmt" width="100" align="center" sortable="true">预计开始</th>
            <th field="expectedOverTime" formatter="dateFmt" width="100" align="center" sortable="true">预计结束</th>
            <th field="inteActualStartTime" formatter="dateFmt" width="100" align="center" sortable="true">实际开始</th>
            <th field="inteActualOverTime" formatter="dateFmt" width="100" align="center" sortable="true">实际结束</th>

            <th field="vip" width="70" formatter="vipFmt" styler="css" align="center" sortable="true">VIP</th>
            <th field="delayResonsName" width="120" align="center" sortable="true">延误原因</th>
        </tr>
        </thead>
    </table>
</script>

<script id="vip_info" type="text/html">
    <section style="margin-bottom: 0px;">
        {{ each vipList as value i}}
        <table class="table-bordered">
            <tr>
                <td style="width:8%;">进出港</td>
                <td style="width:12%;">{{value.inout}}</td>
                <td style="width:8%;">日期:</td>
                <td style="width:12%;">{{value.plandate}}</td>
                <td style="width:8%;">航空公司</td>
                <td style="width:12%;">{{value.aircorp}}</td>
                <td style="width:8%;">航班号</td>
                <td style="width:12%;">{{value.aircorp}}{{value.fltno}}</td>
                <td style="width:8%;">地名</td>
                <td style="width:12%;">{{value.arrp}}</td>
            </tr>
            <tr>
                <td>团队编号</td>
                <td>{{value.groupid}}</td>
                <td>VIP姓名</td>
                <td>{{value.vipname}}</td>
                <td>VIP单位</td>
                <td>{{value.vipcorp}}</td>
                <td>VIP职务</td>
                <td>{{value.viprank}}</td>
                <td>证件号码</td>
                <td>{{value.vipid}}</td>
            </tr>
            <tr>
                <td>籍贯</td>
                <td>{{value.orin}}</td>
                <td>车牌号</td>
                <td>{{value.carid}}</td>
                <td>VIP性别</td>
                <td>{{value.vipsex}}</td>
                <td>地址</td>
                <td>{{value.addr}}</td>
                <td>护照</td>
                <td>{{value.passport}}</td>
            </tr>
            <tr>
                <td>家庭住址</td>
                <td>{{value.familyaddr}}</td>
                <td>VIP外出事由</td>
                <td>{{value.rsntosz}}</td>
                <td>人数</td>
                <td>{{value.togethers}}</td>
                <td>是否免检</td>
                <td>{{value.needcheck}}</td>
                <td>是否礼遇</td>
                <td>{{value.isvip}}</td>
            </tr>
            <tr>
                <td>接待位置</td>
                <td>{{value.receplace}}</td>
                <td>厅房安排</td>
                <td>{{value.hallno}}</td>
                <td>迎送领导</td>
                <td>{{value.meetlead}}</td>
                <td>信息来源</td>
                <td>{{value.infofrom}}</td>
                <td>值班人</td>
                <td>{{value.workman}}</td>
            </tr>
            <tr>
                <td>负责人</td>
                <td>{{value.principal}}</td>
                <td>审核人</td>
                <td>{{value.auditer}}</td>
                <td>传真</td>
                <td>{{value.fax}}</td>
                <td>电话1</td>
                <td>{{value.tele1}}</td>
                <td>电话2</td>
                <td>{{value.tele2}}</td>
            </tr>
            <tr>
                <td>接待单位</td>
                <td>{{value.meetcorp}}</td>
                <td>特殊要求</td>
                <td>{{value.esprequire}}</td>
                <td>备注</td>
                <td>{{value.note}}</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        </table>
        <br>
        {{ /each }}
    </section>
</script>
</body>
