<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<head>
    <title>生产保障历史数据查询和导出</title>
    <style>
        .r {
            padding: 7px 0px 9px;
        }
    </style>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div name="searchColums" style="height: 130px" data-options="region: 'north'" id="safeguardHistoryListtb">
        <input id="_sqlbuilder" name="sqlbuilder" type="hidden"/>
        <input id="dType" name="dType" type="hidden"/>
        <fieldset style="width: 210px; height: 105px;float: left;">
            <legend>查询数据类型</legend>
            <input type="radio" name="dataType" id="homeManifest" value="homeManifest"/>
            <label for="homeManifest" class="r" style="margin-right: 25px;">本站仓单</label>

            <input type="radio" name="dataType" id="preManifest" value="preManifest"/>
            <label for="preManifest" class="r">上站仓单</label>
            <br><br>
            <input type="radio" name="dataType" id="securityService" value="securityService"/>
            <label for="securityService" class="r">安检航班信息</label>
            <input type="radio" name="dataType" id="flightSecurity" value="flightSecurity"/>
            <label for="flightSecurity" class="r">安检旅客信息</label>
            <br><br>
            <input type="radio" name="dataType" id="flightDuty" value="flightDuty" checked/>
            <label for="flightDuty" class="r" style="margin-right: 25px;">值机数据</label>
            <input type="radio" name="dataType" id="overManifest" value="overManifest"/>
            <label for="overManifest" class="r">过站旅客</label>
        </fieldset>
        <fieldset style="width: 160px; height: 105px;float: left;">
            <legend>查询时间</legend>
            起始日期：<input class="inuptxt" type="text" id="planDate_begin" name="planDate_begin"
                        onkeypress="EnterPress(event)" onkeydown="EnterPress()"><br>
            <br><br>
            终止日期：<input class="inuptxt" type="text" id="planDate_end" name="planDate_end"
                        onkeypress="EnterPress(event)" onkeydown="EnterPress()">
        </fieldset>
        <fieldset style="width: 515px; height: 105px;float: left;">
            <legend>查询条件</legend>
            <div style="height: 31px;">
                <span style="text-align:right;" title="航空公司">航空公司 </span>
                <input class="easyui-combobox" type="text" id="flightCompanyName" name="flightCompanyCode"
                       style="width:200px" onkeypress="EnterPress(event)" onkeydown="EnterPress()">&nbsp;&nbsp;
                <span style="text-align:right;" title="航班号">航班号：</span>
                <input class="inuptxt" type="text" name="flightNum" onkeypress="EnterPress(event)"
                       onkeydown="EnterPress()">
            </div>
            <div style="height: 31px;">
                <span style="text-align:right;" title="航班性质">航班性质</span>
                <input class="easyui-combobox" type="text" id="flightNatureCode" name="flightNatureCode"
                       style="width:200px" onkeypress="EnterPress(event)" onkeydown="EnterPress()">&nbsp;&nbsp;
                <span style="text-align:right;" title="航班性质">航班属性</span>
                <input class="easyui-combobox" type="text" id="flightAttrCode" name="flightAttrCode"
                       style="width:200px" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
            </div>
            <div style="height: 31px;">
                <span style="text-align:right;" title="地名">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</span>
                <input class="easyui-combobox" type="text" id="addr" name="addr" style="width:200px"
                       onkeypress="EnterPress(event)" onkeydown="EnterPress()">
            </div>
        </fieldset>
        <div style="height:30px;margin: 45px 0px;float: left;" class="datagrid-toolbar">
                <span style="float:right">
                    <a href="#" class="easyui-linkbutton" iconcls="icon-search" id="search"
                       onclick="safeguardHistoryListsearch()">查询</a>
                    <a href="#" class="easyui-linkbutton" iconcls="icon-reload"
                       onclick="searchReset('safeguardHistoryList')">重置</a>
                    <a href="#" class="easyui-linkbutton" iconcls="icon-20130406125519344_easyicon_net_16"
                       onclick="downloadExcel()">下载</a>
                </span>
        </div>
    </div>
    <div data-options="region:'center'" id="dgContainer" style="overflow-y:hidden;"></div>
</div>

<script type="text/html" id="flightDutyTpl">
    <table id="datagrid" class="easyui-datagrid" data-options="remoteSort:false">
        <thead>
        <tr>
            <th field="id" width="100" hidden="true" sortable="false" order="false">id</th>
            <th field="planDate" width="100" align="center" sortable="true" formatter="dateFmt">航班日期</th>
            <th field="flightCompanyCode" width="50" align="center" sortable="true">航空公司</th>
            <th field="flightCompanySubInfoCode" width="50" align="center" sortable="true">子公司<br>代码</th>
            <th field="flightCompanySubInfoName" width="60" align="center" sortable="true">子公司<br>公司名称</th>
            <th field="flightNum" width="50" align="center" sortable="true">航班号</th>
            <th field="aircraftNum" width="50" align="center" sortable="true">飞机号</th>
            <th field="travellersAllCount" width="100" align="center" sortable="true">本站出港<br>旅客总数</th>
            <th field="personCount" width="100" align="center" sortable="true">本站出港<br>成人数</th>
            <th field="childCount" width="100" align="center" sortable="true">本站出港<br>儿童数</th>
            <th field="babyCount" width="100" align="center" sortable="true">本站出港<br>婴儿数</th>
            <th field="vipPersonCount" width="100" align="center" sortable="true">本站出港<br>vip人数</th>
            <th field="babyAloneCount" width="100" align="center" sortable="true">本站出港<br>无陪伴儿童人数</th>
            <th field="nonstopLuggageCount" width="100" align="center" sortable="true">本站出港<br>直达行李数量</th>
            <th field="nonstopLuggageWeight" width="100" align="center" sortable="true">本站出港<br>直达行李重量</th>
            <th field="transferLuggageCount" width="100" align="center" sortable="true">本站出港<br>转运行李数量</th>
            <th field="transferLuggageWeight" width="100" align="center" sortable="true">本站出港<br>转运行李重量</th>
        </tr>
        </thead>
    </table>
</script>

<script type="text/html" id="homeManifestTpl">
    <table class="easyui-datagrid" id="datagrid" data-options="fitColumns:false,fit:true,remoteSort:false">
        <thead>
        <tr>
            <th field="id" width="100" hidden="true">id</th>
            <th field="flightDynamicId" width="100" hidden="true">动态id</th>
            <th field="lastAllCount" width="60" align="center" hidden="true" sortable="true">载结人数</th>
            <th field="planDate" width="80" align="center" sortable="true" formatter="dateFmt">航班日期</th>
            <th field="flightCompanyCode" width="40" align="center" sortable="true">航空公司</th>
            <th field="flightNum" width="40" align="center" sortable="true">航班号码</th>
            <th field="aircraftNum" width="40" align="center" sortable="true">飞机号</th>
            <th field="flightCompanySubInfoCode" width="40" align="center" sortable="true">子公司<br/>代码</th>
            <th field="flightCompanySubInfoName" width="60" align="center" sortable="true">子公司<br/>公司名称</th>
            <th field="travellerAllCount" width="60" align="center" sortable="true">本站出港<br/>旅客总数</th>
            <th field="personCount" width="100" align="center" sortable="true">本站出港<br/>实际过站成人数</th>
            <th field="childCount" width="100" align="center" sortable="true">本站出港<br/>实际过站儿童数</th>
            <th field="babyCount" width="100" align="center" sortable="true">本站出港<br/>实际过站婴儿数</th>
            <th field="flightCrewCount" width="80" align="center" sortable="true">机组人数</th>
            <th field="goodsCount" width="80" align="center" sortable="true">出港货物<br/>数量</th>
            <th field="goodsWeight" width="80" align="center" sortable="true">出港货物<br/>重量</th>
            <th field="mailCount" width="80" align="center" sortable="true">出港邮件<br/>件数</th>
            <th field="mailWeight" width="80" align="center" sortable="true">出港邮件<br/>重量</th>
            <th field="luggageCount" width="100" align="center" sortable="true">出港行李数量<br/>件数</th>
            <th field="luggageWeight" width="100" align="center" sortable="true">出港行李数量<br/>重量</th>
            <th field="vipCount" width="80" align="center" sortable="true">出港旅客<br/>出港VIP人数</th>
            <th field="firstCabinCount" width="100" align="center" sortable="true">出港旅客<br/>头等舱旅客人数</th>
            <th field="businessCabinCount" width="100" align="center" sortable="true">出港旅客<br/>公务舱旅客人数</th>
            <th field="touristCabinCount" width="100" align="center" sortable="true">出港旅客<br/>经济舱旅客人数</th>
            <th field="oilWeight" width="80" align="center" sortable="true">油料重量</th>
            <th field="personActualCountP" width="80" align="center" sortable="true">实际过站<br/>旅客总数</th>
            <th field="personCountP" width="80" align="center" sortable="true">实际过站<br/>成人数</th>
            <th field="childCoutP" width="80" align="center" sortable="true">实际过站<br/>儿童数</th>
            <th field="babyCountP" width="80" align="center" sortable="true">实际过站<br/>婴儿数</th>
        </tr>
        </thead>
    </table>
</script>

<script type="text/html" id="overManifestTpl">
    <table class="easyui-datagrid" id="datagrid" data-options="remoteSort:false">
        <thead>
        <tr>
            <th field="id" width="100" hidden="true">id</th>
            <th field="flightDynamicId" width="100" hidden="true">动态id</th>
            <th field="planDate" width="80" align="center" sortable="true" formatter="dateFmt">计划日期</th>
            <th field="flightCompanyCode" width="60" align="center" sortable="true">航空公司</th>
            <th field="flightCompanySubInfoCode" width="40" align="center" sortable="true">子公司<br>代码</th>
            <th field="flightCompanySubInfoName" width="60" align="center" sortable="true">子公司<br>公司名称</th>
            <th field="flightNum" width="40" align="center" sortable="true">航班号码</th>
            <th field="aircraftNum" width="40" align="center" sortable="true">飞机号</th>
            <th field="goodsCount" width="60" align="center" sortable="true">过站货物<br>数量</th>
            <th field="goodsWeight" width="60" align="center" sortable="true">过站货物<br>重量</th>
            <th field="mailCount" width="60" align="center" sortable="true">过站邮件<br>件数</th>
            <th field="mailWeight" width="60" align="center" sortable="true">过站邮件<br>重量</th>
            <th field="luggageCount" width="60" align="center" sortable="true">过站行李<br>件数</th>
            <th field="luggageWeight" width="60" align="center" sortable="true">过站行李<br>重量</th>
            <th field="personPlanCount" width="60" align="center" sortable="true">过站旅客<br>计划总人数</th>
            <th field="personTrueCount" width="60" align="center" sortable="true">过站旅客<br>实际总人数</th>
            <th field="vipCount" width="60" align="center" sortable="true">过站旅客<br>VIP人数</th>
            <th field="personCount" width="60" align="center" sortable="true">过站旅客<br>成人数</th>
            <th field="childCount" width="60" align="center" sortable="true">过站旅客<br>儿童数</th>
            <th field="babyCount" width="60" align="center" sortable="true">过站旅客<br>婴儿数</th>
        </tr>
        </thead>
    </table>
</script>

<script type="text/html" id="preManifestTpl">
    <table class="easyui-datagrid" id="datagrid" data-options="remoteSort:false">
        <thead>
        <tr>
            <th field="id" width="100" hidden="true">id</th>
            <th field="flightDynamicId" width="100" hidden="true">动态id</th>
            <th field="planDate" width="80" align="center" sortable="true" formatter="dateFmt">计划日期</th>
            <th field="flightCompanyCode" width="40" align="center" sortable="true">航空公司</th>
            <th field="flightCompanySubInfoCode" width="40" align="center" sortable="true">子公司<br>代码</th>
            <th field="flightCompanySubInfoName" width="60" align="center" sortable="true">子公司<br>公司名称</th>
            <th field="flightNum" width="40" align="center" sortable="true">航班号码</th>
            <th field="aircraftNum" width="40" align="center" sortable="true">飞机号</th>
            <th field="departureAirportName" width="80" align="center" sortable="true">起飞地</th>
            <th field="passAirport1Name" width="80" align="center" sortable="true">经停地1</th>
            <th field="passAirport2Name" width="80" align="center" sortable="true">经停地2</th>
            <th field="touristCountJ" width="60" align="center" sortable="true">进港旅客<br>总数</th>
            <th field="babyCountJ" width="60" align="center" sortable="true">进港旅客<br>婴儿数</th>
            <th field="personCountJ" width="60" align="center" sortable="true">进港旅客<br>成人数</th>
            <th field="childCountJ" width="60" align="center" sortable="true">进港旅客<br>儿童数</th>
            <th field="touristCountP" width="60" align="center" sortable="true">过站旅客<br>总数</th>
            <th field="babyCountP" width="60" align="center" sortable="true">过站旅客<br>婴儿数</th>
            <th field="personCountP" width="60" align="center" sortable="true">过站旅客<br>成人数</th>
            <th field="childCountP" width="60" align="center" sortable="true">过站旅客<br>儿童数</th>
            <th field="goodsCount" width="60" align="center" sortable="true">过站货物<br>数量</th>
            <th field="goodsWeight" width="60" align="center" sortable="true">过站货物<br>重量</th>
            <th field="mailCount" width="60" align="center" sortable="true">过站邮件<br>件数</th>
            <th field="mailWeight" width="60" align="center" sortable="true">过站邮件<br>重量</th>
            <th field="luggageCount" width="60" align="center" sortable="true">过站行李<br>件数</th>
            <th field="luggageWeight" width="60" align="center" sortable="true">过站行李<br>重量</th>
        </tr>
        </thead>
    </table>
</script>

<script type="text/html" id="securityServiceTpl">
    <table class="easyui-datagrid" id="datagrid" data-options="remoteSort:false">
        <thead>
        <tr>
            <th field="id" width="66" hidden="true">主键</th>
            <%--<th field="planDate" width="80" align="center" formatter="formatDate" sortable="true">计划日期</th>--%>

            <!--综合航班号 -->
            <th field="mergeFlightNum" width="80" align="center" formatter="formatFlightNum" parent="vFlightNum"
                sortable="true">
                进港<br/>航班号
            </th>
            <th field="arrivalPlanTime" width="70" align="center" formatter="dateFmt" parent="in" sortable="true">进港<br/>计达
            </th>
            <th field="eta" width="80" align="center" formatter="dateFmt" parent="in" sortable="true">进港<br/>预达</th>
            <th field="departureAirportCode" width="80" align="center" formatter="globalPlaceFmt" parent="in"
                sortable="true">
                进港<br/>起飞地
            </th>
            <th field="ata" width="80" align="center" formatter="dateFmt" parent="in" sortable="true">进港<br/>实达</th>
            <th field="statusName" width="60" align="center" parent="in" sortable="true">进港<br/>航班状态</th>

            <th field="mergeFlightNum2" width="70" align="center" formatter="formatFlightNum2" parent="vFlightNum"
                sortable="true">
                出港<br/>航班号
            </th>
            <th field="departurePlanTime2" width="80" align="center" formatter="dateFmt" parent="out"
                sortable="true">出港<br/>计起
            </th>
            <th field="atd2" width="80" align="center" formatter="dateFmt" parent="out" sortable="true">出港<br/>实起
            </th>
            <th field="arrivalAirportCode2" width="80" align="center" formatter="globalPlaceFmt" parent="out"
                sortable="true">
                出港<br/>到达地
            </th>
            <th field="statusName2" width="60" align="center" parent="out" sortable="true">出港<br/>航班状态</th>

            <th field="aircraftNum" width="50" align="center" parent="airplane" sortable="true">飞机号</th>
            <th field="inteBoardingGateCode" width="50" align="center" parent="airplane" sortable="true">登机口</th>
            <th field="intlBoardingGateCode" width="50" align="center" parent="airplane" hidden="true"
                sortable="true">登机口
            </th>
            <%--<th field="inteDepartureHallCode" width="50" align="center" parent="airplane" sortable="true">候机厅</th>--%>
            <th field="placeNum" width="50" align="center" parent="airplane" sortable="true">机位</th>
            <th field="serviceInfo" width="150" align="center" parent="airplane" sortable="true">安检和勤务信息</th>


            <th field="flightNatureCode" width="50" hidden="true" align="center" parent="in" sortable="true">进港性质</th>
            <th field="flightNatureCode2" width="50" hidden="true" align="center" parent="out" sortable="true">出港性质</th>
            <th field="status" width="60" align="center" hidden="true" parent="in" sortable="true">进港航班状态码</th>
            <th field="status2" width="60" align="center" hidden="true" parent="out" sortable="true">出港航班状态码</th>
        </tr>
        </thead>
    </table>
</script>

<script type="text/html" id="flightSecurityTpl">
    <table class="easyui-datagrid" id="datagrid" data-options="remoteSort:false">
        <thead>
        <tr>
            <th field="id" width="100" hidden="true" rowspan="2">ID</th>
            <th field="checkDate" width="80" align="center" sortable="true" formatter="dateFmt" rowspan="2">航班日期
            </th>
            <th field="flightCompanyCode" width="40" align="center" sortable="true" rowspan="2">航空公司</th>
            <th field="flightNum" width="30" align="center" sortable="true" rowspan="2">航班号</th>
            <th field="arrival" width="100" colspan="2">目的地</th>
            <th field="arrival" width="100" colspan="4">旅客</th>
            <th field="arrival" width="100" colspan="2">不能通过原因</th>
            <th field="arrival" width="100" colspan="2">不能登机原因</th>
        </tr>
        <tr>
            <th field="arrivalAirportCode" width="50" align="center" sortable="true">代码</th>
            <th field="arrivalAirportName" width="60" align="center" sortable="true">地名</th>
            <th field="name" width="50" align="center" sortable="true">姓名</th>
            <th field="sex" width="40" align="center" sortable="true" formatter="sexFmt">性别</th>
            <th field="idCode" width="80" align="center" sortable="true">身份证号</th>
            <th field="nationality" width="60" align="center" sortable="true">国籍</th>
            <th field="noPassReason" width="100" align="center" sortable="true">不通过原因</th>
            <th field="screeners" width="60" align="center" sortable="true">安检员</th>
            <th field="noBoardReason" width="100" align="center" sortable="true">不能登机原因</th>
            <th field="publicSecurityOfficers" width="60" align="center" sortable="true">公安人员</th>
        </tr>
        </thead>
    </table>
</script>

<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/statistics-dist/safeguardHistory.js"></script>
<script>
    $(function () {
        loadData($("#safeguardHistoryListtb [name=dataType]:checked").attr("id"));

        $("#safeguardHistoryListtb [name=dataType]").click(function () {
            loadData($(this).attr("id"));
        });
    })

    function loadData(dataType) {
        if (!dataType) return;
        $("#dgContainer").html($("#" + dataType + "Tpl").html());
        $("#dType").val(dataType);
        $('#datagrid').datagrid({
            idField: 'id',
            url: ctx + '/rms/statistics/safeguardHistoryList',
            fit: true, loadMsg: '数据加载中...', pageSize: 100,
            pagination: false, pageList: [100, 500, 1000], sortOrder: 'asc', rownumbers: true,
            singleSelect: true, fitColumns: false, striped: true, showFooter: true, frozenColumns: [[]],
            onLoadSuccess: function (data) {
                $("#datagrid").datagrid("clearSelections");
            }
        });
    }

</script>
</body>