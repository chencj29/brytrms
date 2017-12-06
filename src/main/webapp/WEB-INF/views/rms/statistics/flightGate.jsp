<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>机位信息查询</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div region="center" style="padding:1px;">
        <table width="100%" id="${statisticsName}List" toolbar="#${statisticsName}Listtb">
            <thead>
            <tr>
                <th field="id" width="100" hidden="true" sortable="false" order="false" rowspan="2">主键</th>
                <th field="planDate" width="75" align="center" sortable="true" order="false" rowspan="2"
                    formatter="formatDate">计划日期
                </th>
                <th width="66" align="center" sortable="true" order="false" colspan="2">代理</th>
                <th width="50" align="center" sortable="true" colspan="3">飞机</th>
                <th field="travelLine" width="150" align="center" sortable="false" rowspan="2">航线</th>
                <th width="50" align="center" sortable="true">进港</th>
                <th width="50" align="center" sortable="true">出港</th>
                <th colspan="18">进港</th>
                <th colspan="18">出港</th>
            </tr>
            <tr>
                <th field="agentCode" width="60" align="center" sortable="false" order="false">代理人ID</th>
                <th field="agentName" width="60" align="center" sortable="false" order="false">代理人名称</th>

                <!-- 飞机 -->
                <th field="aircraftNum" width="50" align="center" sortable="true">机号</th>
                <th field="aircraftTypeCode" width="50" align="center" sortable="true">机型</th>
                <th field="placeNum" width="50" align="center" sortable="true">机位</th>

                <th field="flightNum" hidden="true" width="80" align="center">进港航班ID</th>
                <th field="flightNum2" hidden="true" width="80" align="center">出港航班ID</th>

                <!-- 进港 -->
                <th field="mergeFlightNum" width="80" align="center" formatter="formatFlightNum">综合航班号</th>
                <!-- 出港 -->
                <th field="mergeFlightNum2" width="80" align="center" formatter="formatFlightNum2">综合航班号</th>


                <!-- 进港长条开始 -->
                <th field="flightDynimicId" width="50" hidden="true" align="center" sortable="false">进港</th>

                <th field="shareFlightNum" width="60" align="center" sortable="false">共享航班</th>
                <th field="flightNatureName" width="50" align="center" sortable="false">性质</th>
                <th field="inoutStatusName" width="60" align="center" sortable="false">进港状态</th>

                <th field="departureAirportCode" width="60" align="center" sortable="false"
                    formatter="formatDepartureAirportCode">起飞
                </th>
                <th field="passAirport1Code" width="60" align="center" sortable="false" formatter="globalPlaceFmt">经停1
                </th>
                <th field="passAirport2Code" width="60" align="center" sortable="false" formatter="globalPlaceFmt">经停2
                </th>
                <th field="ext10" width="60" align="center" sortable="false" formatter="globalPlaceFmt">经停3</th>
                <th field="ext12" width="60" align="center" sortable="false" formatter="globalPlaceFmt">经停4</th>
                <th field="ext14" width="60" align="center" sortable="false" formatter="globalPlaceFmt">经停5</th>
                <th field="ext16" width="60" align="center" sortable="false" formatter="globalPlaceFmt">经停6</th>

                <th field="departurePlanTime" width="70" align="center" sortable="true" formatter="formatDateTime">计飞
                </th>
                <th field="arrivalPlanTime" width="70" align="center" sortable="true" formatter="formatDateTime">计达</th>
                <th field="etd" width="70" align="center" sortable="true" formatter="formatDateTime">预飞</th>
                <th field="eta" width="70" align="center" sortable="true" formatter="formatDateTime">预达</th>
                <th field="atd" width="70" align="center" sortable="true" formatter="formatDateTime">实飞</th>
                <th field="ata" width="70" align="center" sortable="true" formatter="formatDateTime">实达</th>

                <th field="delayResonsName" width="120" align="center" sortable="false">延误原因</th>
                <th field="alterNateAreaName" width="120" align="center" sortable="false">备降</th>

                <!-- 出港长条开始 -->
                <th field="flightDynimicId2" width="50" hidden="true" align="center" sortable="true">出港</th>

                <th field="shareFlightNum2" width="60" align="center" sortable="false">共享航班</th>
                <th field="flightNatureName2" width="50" align="center" sortable="false">性质</th>
                <th field="inoutStatusName2" width="60" align="center" sortable="false">出港状态</th>

                <th field="arrivalAirportCode2" width="50" align="center" sortable="false" formatter="globalPlaceFmt">
                    到达
                </th>
                <th field="passAirport1Code2" width="50" align="center" sortable="false" formatter="globalPlaceFmt">
                    经停1
                </th>
                <th field="passAirport2Code2" width="50" align="center" sortable="false" formatter="globalPlaceFmt">
                    经停2
                </th>
                <th field="ext18" width="60" align="center" sortable="false" formatter="globalPlaceFmt">经停3</th>
                <th field="ext20" width="60" align="center" sortable="false" formatter="globalPlaceFmt">经停4</th>
                <th field="ext22" width="60" align="center" sortable="false" formatter="globalPlaceFmt">经停5</th>
                <th field="ext24" width="60" align="center" sortable="false" formatter="globalPlaceFmt">经停6</th>
                <th field="departurePlanTime2" width="70" align="center" sortable="true" formatter="formatDateTime">计飞
                </th>
                <th field="etd2" width="70" align="center" sortable="true" formatter="formatDateTime">预飞</th>
                <th field="atd2" width="70" align="center" sortable="true" formatter="formatDateTime">实飞</th>

                <th field="delayResonsName2" width="120" align="center" sortable="false">延误原因</th>
            </tr>
            </thead>
        </table>
        <div id="${statisticsName}Listtb" style="padding:3px; height: auto">
            <div name="searchColums" style="display:inline">
                <input id="_sqlbuilder" name="sqlbuilder" type="hidden"/>
                <span style="display:-moz-inline-box;display:inline-block;">
                    <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "
                          title="计划日期">计划日期：</span>
                    <input class="inuptxt" type="text" name="planDate" onkeypress="EnterPress(event)"
                           onkeydown="EnterPress()">
                    <span style="text-align:right;" title="航班号">航班号：</span>
                    <input class="inuptxt" type="text" name="flightNum" onkeypress="EnterPress(event)"
                           onkeydown="EnterPress()">
                    <span style="text-align:right;" title="机位号">机位号：</span>
                    <input class="inuptxt" type="text" name="placeNum" onkeypress="EnterPress(event)"
                           onkeydown="EnterPress()">
                 </span>
            </div>
            <div style="height:30px;display:inline" class="datagrid-toolbar">
                <span style="float:right">
                    <a href="#" class="easyui-linkbutton" iconcls="icon-search" id="search"
                       onclick="${statisticsName}Listsearch()">查询</a>
                    <a href="#" class="easyui-linkbutton" iconcls="icon-reload"
                       onclick="searchReset('${statisticsName}List')">重置</a>
                    <a href="#" class="easyui-linkbutton" iconcls="icon-20130406125519344_easyicon_net_16"
                       onclick="downloadExcel()">下载</a>
                </span>
            </div>
        </div>
    </div>
</div>

<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script>
    function formatFlightNum(val, row, index) {
        if (row.flightNum != null && row.flightNum != '') {
            return row.flightCompanyCode + row.flightNum;
        } else {
            return "";
        }
    }

    function formatFlightNum2(val, row, index) {
        if (row.flightNum2 != null && row.flightNum2 != '') {
            return row.flightCompanyCode2 + row.flightNum2;
        } else {
            return "";
        }
    }

    function formatDepartureAirportCode(val, row, index) {
        if (row.departureAirportCode != null && row.departureAirportCode != '') {
            return row.departureAirportCode + " " + row.departureAirportName;
        } else {
            return "";
        }
    }

    function globalPlaceFmt(val, row, index) {
        if (row.passAirport1Code != null && row.passAirport1Code != '') {
            return row.passAirport1Code + " " + row.passAirport1Name;
        } else {
            return "";
        }
    }

    function globalPlaceFmt(val, row, index) {
        if (row.passAirport2Code != null && row.passAirport2Code != '') {
            return row.passAirport2Code + " " + row.passAirport2Name;
        } else {
            return "";
        }
    }

    function formatDateTime(val, row, index) {
        if (val != null && val != '') {
            return moment(val).format("DD HH:mm");
        } else {
            return "";
        }
    }

    function formatDate(val, row, index) {
        if (val) return moment(val).format("YYYY-MM-DD");
        else return "";
    }

    $(function () {
        //给时间控件加上样式
        $("#${statisticsName}Listtb").find("input[name='planDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });

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

    function reloadTable() {
        try {
            $('#' + gridname).datagrid('reload');
            $('#' + gridname).treegrid('reload');
        } catch (ex) {
        }
    }

    function reload${statisticsName}List() {
        $('#${statisticsName}List').datagrid('reload');
    }

    function ${statisticsName}Listsearch() {
        var queryParams = $('#${statisticsName}List').datagrid('options').queryParams;
        $('#${statisticsName}Listtb').find('*').each(function () {
            queryParams[$(this).attr('name')] = $(this).val();
        });
        $('#${statisticsName}List').datagrid({
            url: ctx + '/rms/statistics/${statisticsName}List',
            queryParams: queryParams
        });
    }

    function dosearch(params) {
        var jsonparams = $.parseJSON(params);
        $('#${statisticsName}List').datagrid({
            url: ctx + '/rms/statistics/${statisticsName}List',
            queryParams: jsonparams
        });
    }

    function ${statisticsName}Listsearchbox(value, name) {
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

    function EnterPress(e) {
        var e = e || window.event;
        if (e.keyCode == 13) {
            ${statisticsName}Listsearch();
        }
    }

    function searchReset(name) {
        $("#" + name + "tb").find(":input").val("");
        ${statisticsName}Listsearch();
    }

    function downloadExcel() {
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
    function globalPlaceFmt(val, row, index, field) {
        return field.indexOf("ext") !== -1 ? !val ? "" : val + " " + row["ext" + (parseInt(field.replace("ext", '')) + 1)] : !val ? "" : val + " " + row[field.replace("Code", "Name")]

    }
</script>
</body>