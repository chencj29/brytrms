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
    <div style="height: 50%;" data-options="region: 'south',
        hideCollapsedContent: false,
        collapsed: true,
        maximizable: true,
        onMaximize: maximizeOrRestoreHandler,
        onRestore: maximizeOrRestoreHandler,
        onExpand: _panelExpandHandler,
        onResize:ganttPanelResizeHandler,
        onCollapse: _panelCollapseHandler,split:true" border="false" title="图形展示" id="graphic-panel">
        <div class="gantt" style="height: 100%; width:100%; overflow: hidden; position: absolute;"></div>
    </div>
    <div data-options="region:'center'" id="dgContainer" style="overflow-y:hidden;"></div>
</div>
<div id="view-setting-dialog" title="显示设置" class="easyui-dialog" data-options="closed:true, modal:true"
     style="width: 265px; height:500px;"></div>
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
<script src="${ctxAssets}/scripts/metadata-dev/resource-dist/aircraft_dist.js"></script>
<script>
    <shiro:hasPermission name="rms:aircraft_dist:auto_allo">flagAutoAllocation = true, flagMockDist = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:aircraft_dist:manu_allo">flagManualAllocation = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:aircraft_dist:gant">flagFlightGant = true;
    </shiro:hasPermission>
    <%--<shiro:hasPermission name="rms:aircraft_dist:settime_oci">flagEndFlightOccupation = true;--%>
    <%--</shiro:hasPermission>--%>
    <shiro:hasPermission name="rms:aircraft_dist:cancel_allo">flagCancelAllocation = true;
    </shiro:hasPermission>
</script>
<script type="text/html" id="gateLeaveTpl">
    <form id="gateLeaveForm" method="post" style="padding-top:30px;">
        <input type="hidden" name="flightDynamicId" value="{{flightDynamicId}}">
        <table cellpadding="5" width="100%">
            <tr>
                <td align="right"><label>离开机位时间：</label></td>
                <td>
                    <input type="text" name="leaveTime" id="iptLeaveTime" class="Wdate"
                           onClick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'})"
                           style="width: 200px;height: 25px; line-height: 25px;"/>
                </td>
            </tr>
        </table>
    </form>
</script>
<script type="text/html" id="datagridTpl">
    <table id="datagrid" group="false" adjust="true" data-options="fitColumns:false,fit:true, remoteSort:false">
        <thead>
        <tr>
            <th field="id" width="66" hidden="true" sortable="true">主键</th>
            <th field="flightNum" hidden="true" width="80" align="center" sortable="true">进港航班号</th>
            <th field="flightNum2" hidden="true" width="80" align="center" sortable="true">出港航班号</th>
            <th field="flightDynimicId" width="50" hidden="true" align="center" sortable="true">进港航班ID</th>
            <th field="expectedGateNum" width="66" align="center" hidden="true" sortable="true">预计占用机位数</th>
            <th field="status" width="60" align="center" hidden="true" parent="in" sortable="true">进港航班状态码</th>
            <th field="flightDynimicId2" width="50" hidden="true" align="center" sortable="true">出港航班ID</th>
            <th field="status2" width="60" align="center" hidden="true" parent="out" sortable="true">出港航班状态码</th>
            <th field="planDate" width="80" align="center" formatter="formatDate" sortable="true">计划日期</th>
            <th field="occupiedStart" width="80" align="center" formatter="dateFmt" hidden="true" sortable="true">计划开始</th>
            <th field="occupiedEnd" width="80" align="center" formatter="dateFmt" hidden="true" sortable="true">计划结束</th>
            <th field="flightNatureCode" width="50" hidden="true" align="center" parent="in" sortable="true">进港性质</th>
            <th field="flightNatureCode2" width="50" hidden="true" align="center" parent="out" sortable="true">出港性质</th>

            <th field="agentCode" width="30" align="center" parent="proxy" sortable="true">代理<br/>ID</th>
            <th field="agentName" width="80" align="center" parent="proxy" sortable="true">代理<br/>名称</th>

            <!-- 飞机 -->
            <th field="aircraftNum" width="50" align="center" parent="airplane" sortable="true">飞机<br/>机号</th>
            <th field="aircraftTypeCode" width="50" align="center" parent="airplane" sortable="true">飞机<br/>机型</th>
            <th field="placeNum" width="50" align="center" parent="airplane" formatter="colorFn" sortable="true">飞机<br/>机位</th>

            <th field="safecuardTypeCode" width="50" align="center" parent="safeguard" sortable="true">保障<br/>编码</th>
            <th field="safecuardTypeName" width="80" align="center" parent="safeguard" sortable="true">保障<br/>名称</th>

            <th field="travelLine" width="150" align="center" sortable="true">航线</th>

            <!--综合航班号 -->
            <th field="mergeFlightNum" width="80" align="center" formatter="formatFlightNum" parent="vFlightNum" sortable="true">
                航班号<br/>进港
            </th>
            <th field="mergeFlightNum2" width="80" align="center" formatter="formatFlightNum2" parent="vFlightNum" sortable="true">
                航班号<br/>出港
            </th>

            <!-- 进港长条开始 -->
            <th field="shareFlightNum" width="60" align="center" parent="in" sortable="true">进港<br/>共享航班</th>
            <th field="flightNatureName" width="50" align="center" parent="in" sortable="true">进港<br/>性质</th>
            <th field="statusName" width="120" align="center" parent="in" sortable="true">进港<br/>航班状态</th>

            <th field="departureAirportCode" width="100" align="center" formatter="globalPlaceFmt" parent="in" sortable="true">进港<br/>起飞
            </th>
            <th field="passAirport1Code" width="100" align="center" formatter="globalPlaceFmt" parent="in" sortable="true">进港<br/>经停1
            </th>
            <th field="passAirport2Code" width="100" align="center" formatter="globalPlaceFmt" parent="in" sortable="true">进港<br/>经停2
            </th>
            <th field="ext10" width="100" align="center" formatter="globalPlaceFmt" parent="in" sortable="true">进港<br/>经停3</th>
            <th field="ext12" width="100" align="center" formatter="globalPlaceFmt" parent="in" sortable="true">进港<br/>经停4</th>
            <th field="ext14" width="100" align="center" formatter="globalPlaceFmt" parent="in" sortable="true">进港<br/>经停5</th>
            <th field="ext16" width="100" align="center" formatter="globalPlaceFmt" parent="in" sortable="true">进港<br/>经停6</th>
            <th field="departurePlanTime" width="80" align="center" formatter="dateFmt" parent="in" sortable="true">进港<br/>计飞</th>
            <th field="arrivalPlanTime" width="80" align="center" formatter="dateFmt" parent="in" sortable="true">进港<br/>计达</th>
            <th field="etd" width="80" align="center" formatter="dateFmt" parent="in" sortable="true">进港<br/>预飞</th>
            <th field="eta" width="80" align="center" formatter="dateFmt" parent="in" sortable="true">进港<br/>预达</th>
            <th field="atd" width="80" align="center" formatter="dateFmt" parent="in" sortable="true">进港<br/>实飞</th>
            <th field="ata" width="80" align="center" formatter="dateFmt" parent="in" sortable="true">进港<br/>实达</th>
            <th field="vip" width="80" align="center" formatter="vipFmt" styler="css" parent="in" sortable="true">进港<br/>VIP</th>
            <th field="delayResonsName" width="150" align="center" parent="in" sortable="true">进港<br/>延误原因</th>
            <th field="delayResonsRemark" width="150" align="center" parent="in" sortable="true">进港<br/>备注</th>
            <th field="alternateAreaName" width="150" align="center" parent="in" sortable="true">进港<br/>备降</th>

            <!-- 出港长条开始 -->
            <th field="shareFlightNum2" width="60" align="center" parent="out" sortable="true">出港<br/>共享航班</th>
            <th field="flightNatureName2" width="50" align="center" parent="out" sortable="true">出港<br/>性质</th>
            <th field="statusName2" width="120" align="center" parent="out" sortable="true">出港<br/>航班状态</th>
            <th field="passAirport1Code2" width="100" align="center" formatter="globalPlaceFmt" parent="out" sortable="true">出港<br/>经停1
            </th>
            <th field="passAirport2Code2" width="100" align="center" formatter="globalPlaceFmt" parent="out" sortable="true">出港<br/>经停2
            </th>
            <th field="ext18" width="100" align="center" formatter="globalPlaceFmt" parent="out" sortable="true">出港<br/>经停3</th>
            <th field="ext20" width="100" align="center" formatter="globalPlaceFmt" parent="out" sortable="true">出港<br/>经停4</th>
            <th field="ext22" width="100" align="center" formatter="globalPlaceFmt" parent="out" sortable="true">出港<br/>经停5</th>
            <th field="ext24" width="100" align="center" formatter="globalPlaceFmt" parent="out" sortable="true">出港<br/>经停6</th>
            <th field="arrivalAirportCode2" width="100" align="center" formatter="globalPlaceFmt" parent="out" sortable="true">出港<br/>到达
            </th>
            <th field="departurePlanTime2" width="80" align="center" formatter="dateFmt" parent="out" sortable="true">出港<br/>计飞</th>
            <th field="arrivalPlanTime2" width="80" align="center" formatter="dateFmt" parent="out" sortable="true">出港<br/>计达</th>
            <th field="etd2" width="80" align="center" formatter="dateFmt" parent="out" sortable="true">出港<br/>预飞</th>
            <th field="eta2" width="80" align="center" formatter="dateFmt" parent="out" sortable="true">出港<br/>预达</th>
            <th field="atd2" width="80" align="center" formatter="dateFmt" parent="out" sortable="true">出港<br/>实飞</th>
            <th field="ata2" width="80" align="center" formatter="dateFmt" parent="out" sortable="true">出港<br/>实达</th>
            <th field="vip2" width="80" align="center" formatter="vipFmt" styler="css" parent="out" sortable="true">出港<br/>VIP</th>
            <th field="delayResonsName2" width="150" align="center" parent="out" sortable="true">出港<br/>延误原因</th>
            <th field="delayResonsRemark2" width="150" align="center" parent="out" sortable="true">出港<br/>备注</th>
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