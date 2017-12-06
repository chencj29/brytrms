<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<head>
    <title>航班动态历史数据查询和导出</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div name="searchColums" style="height: 105px" data-options="region: 'north'" id="fdHistoryListtb">
        <input id="_sqlbuilder" name="sqlbuilder" type="hidden"/>
        <input id="dType" name="dType" type="hidden"/>
        <fieldset style="width: 90px; height: 83px;float: left;">
            <legend>航班类别</legend>
            <input type="radio" name="inoutType" id="J" value="J"/>
            <label for="J" style="margin-right: 25px;padding: 7px 0px 9px;">进港</label>
            <br><br>
            <input type="radio" name="inoutType" id="C" value="C" checked/>
            <label for="C" style="padding: 7px 0px 9px;">出港</label>
        </fieldset>
        <fieldset style="width: 180px; height: 83px;float: left;">
            <legend>查询时间</legend>
            统计起始日期：<input class="inuptxt" type="text" id="planDate_begin" name="planDate_begin"
                        onkeypress="EnterPress(event)" onkeydown="EnterPress()">
            <br><br>
            统计终止日期：<input class="inuptxt" type="text" id="planDate_end" name="planDate_end"
                        onkeypress="EnterPress(event)" onkeydown="EnterPress()">
        </fieldset>
        <fieldset style="width: 515px; height: 83px;float: left;">
            <legend>查询条件</legend>
            <div style="height: 31px;">
                <span style="text-align:right;" title="航空公司">航空公司 </span>
                <input class="easyui-combobox" type="text" id="flightCompanyName" name="flightCompanyCode"
                       style="width:200px" onkeypress="EnterPress(event)" onkeydown="EnterPress()">&nbsp;&nbsp;
                <span style="text-align:right;" title="航班号">航班号</span>
                <input class="inuptxt" type="text" name="flightNum" onkeypress="EnterPress(event)"
                       onkeydown="EnterPress()">
            </div>
            <div style="height: 31px;">
                <span style="text-align:right;" title="航班性质">航班性质</span>
                <input class="easyui-combobox" type="text" id="flightNatureCode" name="flightNatureCode"
                       style="width:200px" onkeypress="EnterPress(event)" onkeydown="EnterPress()">&nbsp;&nbsp;
                <span style="text-align:right;" title="目的地">目的地</span>
                <input class="easyui-combobox" type="text" id="addr" name="addr"
                       style="width:200px" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
            </div>
        </fieldset>
        <div style="height:30px;margin: 34px 0px;float: left;" class="datagrid-toolbar">
                <span style="float:right">
                    <a href="#" class="easyui-linkbutton" iconcls="icon-search" id="search"
                       onclick="fdHistoryListsearch()">查询</a>
                    <a href="#" class="easyui-linkbutton" iconcls="icon-reload"
                       onclick="searchReset('fdHistoryList')">重置</a>
                    <a href="#" class="easyui-linkbutton" iconcls="icon-20130406125519344_easyicon_net_16"
                       onclick="downloadExcel()">下载</a>
                </span>
        </div>
    </div>
    <div data-options="region:'center'" id="dgContainer" style="overflow-y:hidden;"></div>
</div>

<script type="text/html" id="tpl">
    <table class="easyui-datagrid" id="datagrid" data-options="fitColumns:false,fit:true,remoteSort:false">
        <thead>
        <tr>
            <th field="id" width="100" hidden="true">id</th>
            <th field="flightDynamicId" width="100" hidden="true">动态id</th>
            <th field="planDate" width="80" align="center" sortable="true" formatter="dateFmt">航班日期</th>
            <th field="flightCompanyCode" width="40" align="center" sortable="true">航空公司</th>
            <th field="flightNum" width="40" align="center" sortable="true">航班号码</th>
            <th field="flightNatureName" width="40" align="center" sortable="true">航班性质</th>
            <th field="addressCode" width="70" align="center" sortable="true">航线</th>
            <th field="aircraftNum" width="60" align="center" sortable="true">机号</th>
            <th field="aircraftTypeCode" width="80" align="center" sortable="true">机型</th>
            {{ if dType == "C" }}
            <th field="atd" width="120" align="center" sortable="true" formatter="formatDateTime">实际起飞时间</th>
            <th field="travellerAllCount" width="80" align="center" sortable="true">总人数</th>
            <th field="personCount" width="80" align="center" sortable="true">成人数</th>
            <th field="childCount" width="80" align="center" sortable="true">儿童数</th>
            <th field="babyCount" width="80" align="center" sortable="true">婴儿数</th>
            <th field="luggageCount" width="80" align="center" sortable="true">行李</th>
            <th field="mailCount" width="80" align="center" sortable="true">邮件</th>
            <th field="goodsCount" width="80" align="center" sortable="true">货物</th>
            {{ else if dType == "J" }}
            <th field="ata" width="120" align="center" sortable="true" formatter="formatDateTime">实际到达时间</th>
            <th field="touristCountJ" width="80" align="center" sortable="true">总人数</th>
            <th field="personCountJ" width="80" align="center" sortable="true">成人数</th>
            <th field="childCountJ" width="80" align="center" sortable="true">儿童数</th>
            <th field="babyCountJ" width="80" align="center" sortable="true">婴儿数</th>
            <th field="luggageCount" width="80" align="center" sortable="true">行李</th>
            <th field="mailCount" width="80" align="center" sortable="true">邮件</th>
            <th field="goodsCount" width="80" align="center" sortable="true">货物</th>
            {{ /if }}
        </tr>
        </thead>
    </table>
</script>

<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script src="${ctxAssets}/scripts/aui-artTemplate/dist/template.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/statistics-dist/fdHistory.js"></script>
<script>
    $(function () {
        loadData($("#fdHistoryListtb [name=inoutType]:checked").attr("id"));

        $("#fdHistoryListtb [name=inoutType]").click(function () {
            loadData($(this).attr("id"));
        });
    })

    function loadData(dataType) {
        if (!dataType) return;
        $("#dgContainer").html(template('tpl')({ dType: dataType }));
        $("#dType").val(dataType);
        $('#datagrid').datagrid({
            idField: 'id',
            url: ctx + '/rms/statistics/fdHistoryList',
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