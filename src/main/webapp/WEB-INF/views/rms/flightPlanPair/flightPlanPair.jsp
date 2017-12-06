<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>运输调度计划</title>
    <link rel="stylesheet" href="${ctxAssets}/scripts/gantt/gantt.css">
    <link rel="stylesheet" href="${ctxAssets}/scripts/spectrum-1.8.0/spectrum.css"/>
    <script src="${ctxAssets}/scripts/spectrum-1.8.0/spectrum.js"></script>
    <style type="text/css">
        .gantt-content {
            overflow-y: hidden !important;
        }

        .panelBotoomPageBar {
            position: absolute;
            bottom: 1px;
            background: #FFFFFF;
            height: 30px;
            border-top: 1px solid #93B6EB;
            border-right: 1px solid #93B6EB;
            line-height: 30px;
            z-index: 1314520;
            left: 2px;
            width: 579px;
        }

        .airplane-wrapper > div {
            cursor: pointer !important;
        }

        .forminput {
            line-height: 25px;
            height: 25px;
            border: #999 1px solid;
        }

        .table-bordered {
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
<div class="easyui-layout" id="layout" data-options="fit:true">
    <div data-options="region:'center', border:true" id="dgContainer">

    </div>

    <div title="保障过程图" id="graphic-panel" style="width: 550px;height: 100%;" fit="false" 　
         data-options="region:'east', hideCollapsedContent:false, border:true,
                       onExpand: function() {   isExpand = true; }, onCollapse: function() {isExpand = false; }">
        <div class="ganttArea"></div>
        <div class="panelBotoomPageBar easyui-pagination">pagination bar</div>
    </div>
</div>

<div id="specialServiceDialog" style="width: 800px;margin-bottom:7px;">
    <table class="easyui-datagrid" id="specialServiceDatagrid" data-options="fitColumns:false,fit:true,pageSize:100000,border:false" style="width:100%;height: 100%;">
        <thead>
        <tr>
            <th field="flightCompanyName" width="100">公司名称</th>
            <th field="flightNum" width="100">航班号</th>
            <th field="place" width="100">地点</th>
            <th field="passengerName" width="100">姓名</th>
            <th field="passengerAge" width="100">年龄</th>
            <th field="pickUpName" width="100">接站人</th>
            <th field="pickUpPhone" width="100">接站人电话</th>
            <th field="waiterName" width="100">服务人员</th>
            <th field="operatorName" width="100">值班人员</th>
        </tr>
        </thead>
    </table>
</div>

<div id="dynamic-detail-dialog" title="保障过程明细" class="easyui-dialog" data-options="closed:true,modal: true" style="width: 650px;height: 457px;">
    <div id="dynamic-detail-template"></div>
</div>

<div id="add-progress-dialog" title="添加保障过程" class="easyui-dialog" data-options="closed:true,modal: true" style="width: 510px;height: 457px;">
    <div id="add-progress-template"></div>
</div>

<div id="view-setting-dialog" title="显示设置" class="easyui-dialog" data-options="closed:true, modal:true" style="width: 430px; height:500px;"></div>

<script src="${ctxAssets}/scripts/gantt/collision.detection.plugin.js"></script>
<script src="${ctxAssets}/scripts/gantt/gantt4progress.js"></script>
<script src="${ctxAssets}/scripts/layer/layer.js"></script>
<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script src="${ctxAssets}/scripts/My97DatePicker/WdatePicker.js"></script>
<script src="${ctxAssets}/scripts/aui-artTemplate/dist/template.js"></script>
<script src="${ctxAssets}/scripts/select2/dist/js/select2.min.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.resource.dist.js"></script>
<script src="${ctxAssets}/scripts/easyui144/extensions/datagrid-cell-editing/datagrid-cellediting.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.Pair.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.Ext.js"></script>
<script type="text/html" id="ganttContainer">
    <div class="wrapper" style="height:400px; width: 580px; float: left;">
        <div class="info" style="height: 25px;position: relative; width: 580px;">
            <div style="float: left; height: 25px; line-height: 25px; padding-left: 10px; font-weight: bold;">
                {{ info }}
            </div>
        </div>
        <div class="container" style="height: 375px; width: 580px; position: relative; overflow-y: hidden; overflow-x: hidden; border: 1px solid #94B7E9;">
            <div class="gantt" id="{{id}}"></div>
        </div>
    </div>
</script>
<script type="text/html" id="timeFormContainer">
    <form id="timeForm" style="padding:15px;">
        <input type="hidden" name="flightDynamicId" value="{{flightDynamicId}}">
        <input type="hidden" name="processId" value="{{processId}}">
        <table cellpadding="5" width="100%" class="formtable">
            <tr>
                <td>航班日期:</td>
                <td>{{planDate}}</td>
                <td>保障过程:</td>
                <td>{{text}}</td>
            </tr>
            <tr>
                <td>进港航班号:</td>
                <td>{{inCode}}</td>
                <td>出港航班号:</td>
                <td>{{outCode}}</td>
            </tr>
            <tr>
                <td>机型:</td>
                <td>{{aircraftTypeCode}}</td>
                <td>机位:</td>
                <td>{{placeNum}}</td>
            </tr>
            <tr>
                <td>计划开始:</td>
                <td>{{pst}}</td>
                <td>计划结束:</td>
                <td>{{pot}}</td>
            </tr>
        </table>
        <hr/>
        <table cellpadding="5" width="100%">
            <tr>
                <td>实际开始:</td>
                <td>
                    <input type="text" name="actualStartTime" id="iptActualStartTime"
                           class="Wdate" onClick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm', readOnly:true ,maxDate:'#F{$dp.$D(\'iptActualOverTime\')}'})"
                           style="height: 25px; line-height: 25px;" value="{{ast}}"/>
                </td>
                <td>实际完成:</td>
                <td>
                    <input type="text" name="actualOverTime" id="iptActualOverTime"
                           class="Wdate" onClick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm', readOnly:true,minDate:'#F{$dp.$D(\'iptActualStartTime\')}'})"
                           style="height: 25px; line-height: 25px;" value="{{aot}}"/>
                </td>
            </tr>
            <tr>
                <td>完成人:</td>
                <td colspan="3">
                    <input type="text" name="opName" id="iptOpName" value="{{opName}}" class="forminput" style="width:391px;"/>
                </td>
            </tr>
            <tr>
                <td>特车类型:</td>
                <td><input type="text" name="carType" id="iptCarType" value="{{carType}}" class="forminput"/></td>
                <td>特车牌号:</td>
                <td><input type="text" name="carNumber" id="iptCarNumber" value="{{carCode}}" class="forminput"/></td>
            </tr>
        </table>
    </form>
</script>
<script id="addProgressTpl" type="text/html">
    <form>
        <table class="formtable" style="width:100%">
            <tr>
                <td>计划日期</td>
                <td colspan="3">{{planDate}}</td>
            </tr>
            <tr>
                <td>进港航班号</td>
                <td>{{inFlightNum}}&nbsp;</td>
                <td>出港航班号</td>
                <td>{{outFlightNum}}&nbsp;</td>
            </tr>
            <tr>
                <td>机位</td>
                <td>{{placeNum}}&nbsp;</td>
                <td>飞机号</td>
                <td>{{aircraftNum}}&nbsp;</td>
            </tr>
        </table>
    </form>

    <table id="progress-datagrid" class="easyui-datagrid" title="保障过程列表" style="width:100%; height:272px"
           data-options="fitColumns:true, rownumbers: true, checkbox: true, selectOnCheck: true, checkOnSelect: true, remoteSort: false, remoteFilter: false, multiSort: false">
        <thead>
        <tr>
            <th data-options="field:'id',width:80, align:'center', hidden:true">ID</th>
            <th data-options="field:'ck',checkbox:true"></th>
            <th data-options="field:'safeguardProcessCode',width:80, align:'center'">编码</th>
            <th data-options="field:'safeguardProcessName',width:80,align:'center'">名称</th>
            <th formatter="dateFmt" data-options="field:'redundant1', width:80, align:'center', editor: {type: 'datetimebox'}">计划开始</th>
            <th formatter="dateFmt" data-options="field:'redundant2', width:80, align:'center', editor: {type: 'datetimebox'}">计划结束</th>
        </tr>
        </thead>
    </table>
    <div style="text-align: center; margin-top:11px;">
        <a href="javascript:;" id="btnAddProgress" class="easyui-linkbutton">保存设置</a>
        &nbsp;
        <a href="javascript:;" id="btnCloseWindow" class="easyui-linkbutton">关闭窗口</a>
    </div>
</script>

<script id="showDynamicDetail" type="text/html">
    <form>
        <table class="formtable" style="width: 100%;">
            <tr>
                <td>计划日期</td>
                <td colspan="3">{{pair.planDate}}</td>
            </tr>
            <tr>
                <td>进港航班号</td>
                <td>{{pair.flightNum}}&nbsp;</td>
                <td>出港航班号</td>
                <td>{{pair.flightNum2}}&nbsp;</td>
            </tr>
            <tr>
                <td>机位</td>
                <td>{{pair.placeNum}}&nbsp;</td>
                <td>飞机号</td>
                <td>{{pair.aircraftNum}}&nbsp;</td>
            </tr>
            <tr>
                <td>保障主类型</td>
                <td>
                    <input class="easyui-combobox" id="_safeguardMainType" name="_safeguardMainType" style="width: 60px;">
                </td>
                <td>保障子类型</td>
                <td>
                    <input class="easyui-combobox" id="safeguardTypeCode" value="{{pair.safecuardTypeCode}}" name="safeguardTypeCode" style="width: 100px;">
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    <center>
                        <a href="javascript:;" id="modifySafeguardTypeButton" class="easyui-linkbutton">更改保障类型</a>
                    </center>
                </td>
            </tr>
        </table>
    </form>
    <table id="safeguard_process_grid" class="easyui-datagrid" title="保障过程详情" style="width:100%;height:250px" data-options="singleSelect:true,fitColumns:true,method:'get', rownumbers: true">
        <thead>
        <tr>
            <th data-options="field:'progressName',    width:80, align:'center'">保障过程名称</th>
            <th data-options="field:'planStartTime',   width:80, align:'center'">计划开始时间</th>
            <th data-options="field:'planOverTime',    width:80, align:'center'">计划结束时间</th>
            <th data-options="field:'actualStartTime', width:80, align:'center'">实际开始时间</th>
            <th data-options="field:'actualOverTime',  width:80, align:'center'">实际结束时间</th>
        </tr>
        </thead>
    </table>
</script>
<script type="text/html" id="datagridTpl">
    <table id="datagrid" title="航班配对信息" group="false" adjust="true">
        <thead>
        <tr>
            <th field="id" width="66" hidden="true">主键</th>
            <th field="flightNum" hidden="true" width="80" align="center">进港航班号</th>
            <th field="flightNum2" hidden="true" width="80" align="center">出港航班号</th>
            <th field="flightDynimicId" width="50" hidden="true" align="center">进港航班ID</th>
            <th field="expectedGateNum" width="66" align="center" hidden="true">预计占用机位数</th>
            <th field="status" width="60" align="center" hidden="true" parent="in">进港航班状态码</th>
            <th field="flightDynimicId2" width="50" hidden="true" align="center">出港航班ID</th>
            <th field="status2" width="60" align="center" hidden="true" parent="out">出港航班状态码</th>
            <th field="planDate" width="80" align="center" formatter="formatDate">计划日期</th>
            <th field="occupiedStart" width="80" align="center" formatter="dateFmt" hidden="true">计划开始</th>
            <th field="occupiedEnd" width="80" align="center" formatter="dateFmt" hidden="true">计划结束</th>
            <th field="flightNatureCode" width="50" hidden="true" align="center" parent="in">进港性质</th>
            <th field="flightNatureCode2" width="50" hidden="true" align="center" parent="out">出港性质</th>

            <th field="agentCode" width="30" align="center" parent="proxy">代理<br/>ID</th>
            <th field="agentName" width="80" align="center" parent="proxy">代理<br/>名称</th>

            <!-- 飞机 -->
            <th field="aircraftNum" width="50" align="center" parent="airplane">飞机<br/>机号</th>
            <th field="aircraftTypeCode" width="50" align="center" parent="airplane">飞机<br/>机型</th>
            <th field="placeNum" width="50" align="center" parent="airplane" formatter="colorFn">飞机<br/>机位</th>

            <th field="safecuardTypeCode" width="50" align="center" parent="safeguard">保障<br/>编码</th>
            <th field="safecuardTypeName" width="80" align="center" parent="safeguard">保障<br/>名称</th>

            <th field="travelLine" width="150" align="center">航线</th>

            <!--综合航班号 -->
            <th field="mergeFlightNum" width="80" align="center" formatter="formatFlightNum" parent="vFlightNum">航班号<br/>进港</th>
            <th field="mergeFlightNum2" width="80" align="center" formatter="formatFlightNum2" parent="vFlightNum">航班号<br/>出港</th>

            <!-- 进港长条开始 -->
            <th field="shareFlightNum" width="60" align="center" parent="in">进港<br/>共享航班</th>
            <th field="flightNatureName" width="50" align="center" parent="in">进港<br/>性质</th>
            <th field="statusName" width="120" align="center" parent="in">进港<br/>航班状态</th>

            <th field="departureAirportCode" width="100" align="center" formatter="globalPlaceFmt" parent="in">进港<br/>起飞</th>
            <th field="passAirport1Code" width="100" align="center" formatter="globalPlaceFmt" parent="in">进港<br/>经停1</th>
            <th field="passAirport2Code" width="100" align="center" formatter="globalPlaceFmt" parent="in">进港<br/>经停2</th>
            <th field="ext10" width="100" align="center" formatter="globalPlaceFmt" parent="in">进港<br/>经停3</th>
            <th field="ext12" width="100" align="center" formatter="globalPlaceFmt" parent="in">进港<br/>经停4</th>
            <th field="ext14" width="100" align="center" formatter="globalPlaceFmt" parent="in">进港<br/>经停5</th>
            <th field="ext16" width="100" align="center" formatter="globalPlaceFmt" parent="in">进港<br/>经停6</th>
            <th field="departurePlanTime" width="80" align="center" formatter="dateFmt" parent="in">进港<br/>计飞</th>
            <th field="arrivalPlanTime" width="80" align="center" formatter="dateFmt" parent="in">进港<br/>计达</th>
            <th field="etd" width="80" align="center" formatter="dateFmt" parent="in">进港<br/>预飞</th>
            <th field="eta" width="80" align="center" formatter="dateFmt" parent="in">进港<br/>预达</th>
            <th field="atd" width="80" align="center" formatter="dateFmt" parent="in">进港<br/>实飞</th>
            <th field="ata" width="80" align="center" formatter="dateFmt" parent="in">进港<br/>实达</th>
            <th field="vip" width="80" align="center" formatter="vipFmt" styler="css" parent="in">进港<br/>VIP</th>
            <th field="delayResonsName" width="150" align="center" parent="in">进港<br/>延误原因</th>
            <th field="delayResonsRemark" width="150" align="center" parent="in">进港<br/>备注</th>
            <th field="alternateAreaName" width="150" align="center" parent="in">进港<br/>备降</th>

            <!-- 出港长条开始 -->
            <th field="shareFlightNum2" width="60" align="center" parent="out">出港<br/>共享航班</th>
            <th field="flightNatureName2" width="50" align="center" parent="out">出港<br/>性质</th>
            <th field="statusName2" width="120" align="center" parent="out">出港<br/>航班状态</th>
            <th field="passAirport1Code2" width="100" align="center" formatter="globalPlaceFmt" parent="out">出港<br/>经停1</th>
            <th field="passAirport2Code2" width="100" align="center" formatter="globalPlaceFmt" parent="out">出港<br/>经停2</th>
            <th field="ext18" width="100" align="center" formatter="globalPlaceFmt" parent="out">出港<br/>经停3</th>
            <th field="ext20" width="100" align="center" formatter="globalPlaceFmt" parent="out">出港<br/>经停4</th>
            <th field="ext22" width="100" align="center" formatter="globalPlaceFmt" parent="out">出港<br/>经停5</th>
            <th field="ext24" width="100" align="center" formatter="globalPlaceFmt" parent="out">出港<br/>经停6</th>
            <th field="arrivalAirportCode2" width="100" align="center" formatter="globalPlaceFmt" parent="out">出港<br/>到达</th>
            <th field="departurePlanTime2" width="80" align="center" formatter="dateFmt" parent="out">出港<br/>计飞</th>
            <th field="arrivalPlanTime2" width="80" align="center" formatter="dateFmt" parent="out">出港<br/>计达</th>
            <th field="etd2" width="80" align="center" formatter="dateFmt" parent="out">出港<br/>预飞</th>
            <th field="eta2" width="80" align="center" formatter="dateFmt" parent="out">出港<br/>预达</th>
            <th field="atd2" width="80" align="center" formatter="dateFmt" parent="out">出港<br/>实飞</th>
            <th field="ata2" width="80" align="center" formatter="dateFmt" parent="out">出港<br/>实达</th>
            <th field="vip2" width="80" align="center" formatter="vipFmt" styler="css" parent="out">出港<br/>VIP</th>
            <th field="delayResonsName2" width="150" align="center" parent="out">出港<br/>延误原因</th>
            <th field="delayResonsRemark2" width="150" align="center" parent="out">出港<br/>备注</th>
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