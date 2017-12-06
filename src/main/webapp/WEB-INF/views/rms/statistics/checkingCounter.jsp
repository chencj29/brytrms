<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>值机柜台信息查询</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div region="center" style="padding:1px;">
        <table width="100%" id="${statisticsName}List" toolbar="#${statisticsName}Listtb">
            <!-- 出港航班动态的表头 -->
            <thead>
            <tr>
                <th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>

                <th field="planDate" width="100" sortable="false" align="center" order="false" formatter="formatDate">计划日期</th>
                <th field="flightDynamicCode" width="50" hidden="true" align="center" sortable="true">航班号</th>
                <th field="vitrualFlightNumber" formatter="vitrualFlightNumberFmt" width="100" align="center" sortable="true">航班号</th>
                <th field="aircraftNum" width="80" align="center" sortable="true">机号</th>
                <th field="aircraftTypeCode" width="80" align="center" sortable="true">机型</th>
                <th field="placeNum" width="80" align="center" sortable="true">机位</th>
                <th field="statusName" width="70" align="center" sortable="true">状态</th>
                <th field="flightNatureName" width="80" align="center" sortable="true">性质</th>
                <th field="flightDynamicNature" width="80" formatter="natureFmt" align="center" sortable="true">属性</th>
                <th field="inoutTypeName" width="70" align="center" sortable="true">进出港</th>

                <th field="flightDynamicId" width="100" hidden="true" sortable="false" order="false">航班动态编号</th>
                <th field="flightCompanyCode" width="50" hidden="true" align="center" sortable="false" order="false">航空公司</th>
                <th field="inteCheckingCounterCode" width="150" align="center" sortable="true">国内航段</th>
                <th field="inteActualStartTime" formatter="dateFmt" width="120" align="center" sortable="true">实际开始时间</th>
                <th field="inteActualOverTime" width="120" align="center" sortable="true">实际结束时间</th>
                <th field="intlCheckingCounterCode" width="150" align="center" sortable="true">国际航段</th>
                <th field="intlActualStartTime" width="120" align="center" sortable="true">实际开始时间</th>
                <th field="intlActualOverTime" width="120" align="center" sortable="true">实际结束时间</th>

                <th field="departureAirportName" width="60" align="center" sortable="true" formatter="globalPlaceFmt">起飞</th>
                <th field="passAirport1Name" width="60" align="center" sortable="true" formatter="globalPlaceFmt">经停1</th>
                <th field="passAirport2Name" width="60" align="center" sortable="true" formatter="globalPlaceFmt">经停2</th>
                <th field="ext11" width="60" align="center" sortable="true" formatter="globalPlaceFmt">经停3</th>
                <th field="ext13" width="60" align="center" sortable="true" formatter="globalPlaceFmt">经停4</th>
                <th field="ext15" width="60" align="center" sortable="true" formatter="globalPlaceFmt">经停5</th>
                <th field="ext17" width="60" align="center" sortable="true" formatter="globalPlaceFmt">经停6</th>
                <th field="arrivalAirportName" width="60" align="center" sortable="true">到达</th>

                <th field="departurePlanTime" formatter="dateFmt" width="70" align="center" sortable="true">计飞</th>
                <th field="etd" formatter="dateFmt" width="70" align="center" sortable="true">预飞</th>
                <th field="atd" formatter="dateFmt" width="70" align="center" sortable="true">实飞</th>

                <th hidden="true" field="expectedCheckingCounterNum" width="50" align="center" sortable="true">预计占用数量</th>
                <th field="expectedStartTime" formatter="dateFmt" width="120" align="center" sortable="true">预计开始时间</th>
                <th field="expectedOverTime" formatter="dateFmt" width="120" align="center" sortable="true">预计结束时间</th>

                <th field="agentCode" width="100" align="center" sortable="true">代理ID</th>
                <th field="agentName" width="100" align="center" sortable="true">代理名称</th>
                <th field="delayResonsName" width="120" align="center" sortable="true">延误原因</th>
            </tr>
            </thead>
        </table>
        <div id="${statisticsName}Listtb" style="padding:3px; height: auto">
            <div name="searchColums" style="display:inline">
                <input id="_sqlbuilder" name="sqlbuilder" type="hidden"/>
                <span style="display:-moz-inline-box;display:inline-block;">
                        <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "
                              title="计划日期">计划日期：</span>
                        <input class="inuptxt" type="text" name="planDate" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                        <span style="text-align:right;" title="航班号">航班号：</span>
                        <input class="inuptxt" type="text" name="flightDynamicCode" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                        <span style="text-align:right;" title="值机柜台编号">值机柜台编号：</span>
                        <input class="inuptxt" type="text" name="inteCheckingCounterCode" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                     </span>
            </div>
            <div style="height:30px;display:inline" class="datagrid-toolbar">
                <span style="float:right">
                    <a href="#" class="easyui-linkbutton" iconcls="icon-search" id="search" onclick="${statisticsName}Listsearch()">查询</a>
                    <a href="#" class="easyui-linkbutton" iconcls="icon-reload" onclick="searchReset('${statisticsName}List')">重置</a>
                    <a href="#" class="easyui-linkbutton" iconcls="icon-20130406125519344_easyicon_net_16" onclick="downloadExcel()">下载</a>
                </span>
            </div>
        </div>
    </div>
</div>

<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script>
    var flightNatures = { "1": "国内航班", "2": "国际航班", "3": "混合航班" };
    function dateFmt (v, r, i, f) {
        return !v ? "" : moment(v).format("DD HH:mm");
    }

    function formatDate (v, r) {
        return v ? moment(v).format('YYYY-MM-DD') : "";
    }

    function natureFmt (v, r, i, f) {
        return _.propertyOf(flightNatures)(v);
    }

    function vitrualFlightNumberFmt (v, r, i, f) {
        return r.flightCompanyCode + r.flightDynamicCode;
    }


    $(function () {
        //给时间控件加上样式
        $("#${statisticsName}Listtb").find("input[name='planDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {WdatePicker({ dateFmt: 'yyyy-MM-dd' });});

        storage = $.localStorage;
        if (!storage) storage = $.cookieStorage;
        $('#${statisticsName}List').datagrid({
            idField: 'id',
            url: ctx + '/rms/statistics/${statisticsName}List',
            fit: true,
            loadMsg: '数据加载中...',
            pageSize: 100,
            pagination: false,
            pageList: [100, 500, 1000],
            sortOrder: 'asc',
            rownumbers: true,
            singleSelect: true,
            fitColumns: false,
            striped: true,
            showFooter: true,
            frozenColumns: [[]],
            onLoadSuccess: function (data) {
                $("#${statisticsName}List").datagrid("clearSelections");
            },
            onClickRow: function (rowIndex, rowData) {
                rowid = rowData.id;
                var selections = $('#${statisticsName}List').datagrid('getSelections');
                gridname = '${statisticsName}List';
            }
        });
    });
    function reloadTable () {
        try {
            $('#' + gridname).datagrid('reload');
            $('#' + gridname).treegrid('reload');
        } catch (ex) {
        }
    }
    function reload${statisticsName}List () {
        $('#${statisticsName}List').datagrid('reload');
    }

    function ${statisticsName}Listsearch () {
        var queryParams = $('#${statisticsName}List').datagrid('options').queryParams;
        $('#${statisticsName}Listtb').find('*').each(function () {
            queryParams[$(this).attr('name')] = $(this).val();
        });
        $('#${statisticsName}List').datagrid({
            url: ctx + '/rms/statistics/${statisticsName}List',
            queryParams: queryParams
        });
    }
    function dosearch (params) {
        var jsonparams = $.parseJSON(params);
        $('#${statisticsName}List').datagrid({
            url: ctx + '/rms/statistics/${statisticsName}List',
            queryParams: jsonparams
        });
    }
    function ${statisticsName}Listsearchbox (value, name) {
        var queryParams = $('#${statisticsName}List').datagrid('options').queryParams;
        queryParams[name] = value;
        queryParams.searchfield = name;
        $('#${statisticsName}List').datagrid('reload');
    }
    $('#${statisticsName}Listsearchbox').searchbox({
        searcher: function (value, name) {
            ${statisticsName}Listsearchbox(value, name);
        },
        menu: '#${statisticsName}Listmm',
        prompt: '请输入查询关键字'
    });
    function EnterPress (e) {
        var e = e || window.event;
        if (e.keyCode == 13) {
            ${statisticsName}Listsearch();
        }
    }
    function searchReset (name) {
        $("#" + name + "tb").find(":input").val("");
        ${statisticsName}Listsearch();
    }

    function downloadExcel () {
        location.href = ctx + '/rms/statistics/${statisticsName}ExpExcel';
    }

    /**
     * 地名的通用转码器
     * @param val 值
     * @param row 行
     * @param index 索引
     * @param field 字段名, 请使用三字码, 即包含Code的字段
     * @returns {string} 返回 三字码 + 空格 + 地名
     */
    function globalPlaceFmt (val, row, index, field) {
        return field.indexOf("ext") !== -1 ? !val ? "" : val + " " + row["ext" + (parseInt(field.replace("ext", '')) + 1)] : !val ? "" : val + " " + row[field.replace("Code", "Name")]

    }

</script>
</body>