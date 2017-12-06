<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>资源分配查询和导出</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div region="center" style="padding:1px;">
        <table width="100%" id="${statisticsName}List" toolbar="#${statisticsName}Listtb">
            <thead>
            <tr>
                <th field="id" width="100" hidden="true" sortable="false" order="false" rowspan="2">主键</th>
                <th field="planDate" width="66" align="center" rowspan="2">计划日期</th>
                <th width="50" align="center" order="false" colspan="3">飞机</th>
                <th field="inoutTypeCode" width="50" align="center" rowspan="2" formatter="formatFn">进出港</th>
                <th width="50" align="center">进港</th>
                <th width="50" align="center">出港</th>
                <th colspan="8">进港</th>
                <th colspan="17">出港</th>
            </tr>
            <tr>

                <!-- 飞机 -->
                <th field="aircraftNum" width="50" align="center">机号</th>
                <th field="aircraftTypeCode" width="50" align="center">机型</th>
                <th field="placeNum" width="50" align="center">机位</th>

                <th field="flightNum" hidden="true" width="80" align="center">进港航班ID</th>
                <th field="flightNum2" hidden="true" width="80" align="center">出港航班ID</th>

                <!-- 进港 -->
                <th field="mergeFlightNum" width="80" align="center" formatter="formatFn">综合航班号</th>
                <!-- 出港 -->
                <th field="mergeFlightNum2" width="80" align="center" formatter="formatFn">综合航班号</th>


                <!-- 进港长条开始 -->
                <th field="flightDynimicId" width="50" hidden="true" align="center" sortable="false">进港</th>

                <th field="departurePlanTime" width="70" align="center" formatter="formatDateTime">计飞</th>
                <th field="arrivalPlanTime" width="70" align="center" formatter="formatDateTime">计达</th>
                <th field="atd" width="70" align="center" formatter="formatDateTime">实飞</th>
                <th field="ata" width="70" align="center" formatter="formatDateTime">实达</th>
                <th field="inteArrivalGateCode" width="70" align="center">国内到港门</th>
                <th field="intlArrivalGateCode" width="70" align="center">国际到港门</th>
                <th field="inteCarouselCode" width="80" align="center">国内行李转盘</th>
                <th field="intlCarouselCode" width="80" align="center">国际行李转盘</th>

                <!-- 出港长条开始 -->
                <th field="flightDynimicId2" width="50" hidden="true" align="center">出港</th>

                <th field="departurePlanTime2" width="70" align="center" formatter="formatDateTime">计飞</th>
                <th field="etd2" width="70" align="center" formatter="formatDateTime">预飞</th>
                <th field="atd2" width="70" align="center" formatter="formatDateTime">实飞</th>
                <th field="inteCheckingCounterCode" width="90" align="center">国内值机柜台</th>
                <th field="intlCheckingCounterCode" width="90" align="center">国际值机柜台</th>
                <th field="inteActualStartTime" width="90" align="center" formatter="formatDateTime">值机开始时间</th>
                <th field="inteActualEndTime" width="90" align="center" formatter="formatDateTime">值机结束时间</th>
                <th field="inteSlideCoastCode" width="70" align="center">国内滑槽</th>
                <th field="intlSlideCoastCode" width="70" align="center">国际滑槽</th>
                <th field="inteSecurityCheckinCode" width="80" align="center">国内安检口</th>
                <th field="intlSecurityCheckinCode" width="80" align="center">国际安检口</th>
                <th field="inteDepartureHallCode" width="80" align="center">国内候机厅</th>
                <th field="intlDepartureHallCode" width="80" align="center">国际候机厅</th>

            </tr>
            </thead>
        </table>
        <div id="${statisticsName}Listtb" style="padding:3px; height: auto">
            <div name="searchColums" style="display:inline">
                <input id="_sqlbuilder" name="sqlbuilder" type="hidden"/>
                <span style="display:-moz-inline-box;display:inline-block;">
                    <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; " title="计划日期">计划日期：</span>
                    <input class="inuptxt" type="text" id="planDate_begin" name="planDate_begin" onkeypress="EnterPress(event)" onkeydown="EnterPress()"> 至
                         <input class="inuptxt" type="text" id="planDate_end" name="planDate_end" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                    <span style="text-align:right;" title="航班号">进出港：</span>
                    <input class="easyui-combobox" type="text" id="inoutTypeCode" style="width:60px" name="inoutTypeCode" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                    <span style="text-align:right;" title="航班号">航空公司：</span>
                    <input class="easyui-combobox" type="text" id="flightCompanyName" name="flightCompanyCode" style="width:200px" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                    <span style="text-align:right;" title="航班号">航班号：</span>
                    <input class="inuptxt" type="text" name="flightNum" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                    <span style="text-align:right;" title="航班号">飞机号：</span>
                    <input class="inuptxt" type="text" name="aircraftNum" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                 </span>
            </div>
            <div style="height:30px;" class="datagrid-toolbar">
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
    var fnMap = {
        inoutTypeCode:function (val,row,i) {
            if (!!row.inoutTypeCode && !!row.inoutTypeCode2) {
                return "连班";
            }else if(!!row.inoutTypeCode){
                return "进港";
            }else if(!!row.inoutTypeCode2){
                return "出港";
            } else {
                return "";
            }
        },
        mergeFlightNum:function (val,row,i) {
            if (row.flightNum != null && row.flightNum != '') {
                return row.flightCompanyCode + row.flightNum;
            } else {
                return "";
            }
        },
        mergeFlightNum2:function (val,row,i) {
            if (row.flightNum2 != null && row.flightNum2 != '') {
                return row.flightCompanyCode2 + row.flightNum2;
            } else {
                return "";
            }
        }
    };

    function formatFn(val,row,index) {
        return fnMap[this.field]?fnMap[this.field](val,row,index):val;
    }

    function formatDateTime (val, row, index) {
        if (val != null && val != '') {
            return moment(val).format("DD HH:mm");
        } else {
            return "";
        }
    }

    var flightCompanyCodeDates;
    if(!flightCompanyCodeDates) {
        $.ajax({
            url:ctx + '/ams/flightCompanyInfo/jsonData',
            dataType: "json",
            async: false,
            success: function (data) {
                flightCompanyCodeDates = _.map(data,function(o){
                    var result = {};
                    result.twoCode = o.twoCode;
                    result.tCodechName = o.twoCode+o.chineseName;
                    return result;
                });
            }
        });
    }

    $.fn.combobox.defaults.filter = function(q, row){
        var opts = $(this).combobox('options');
        return row[opts.textField].toUpperCase().indexOf(q.toUpperCase()) >= 0;
    }

    $(function () {
        //给时间控件加上样式
        $("#${statisticsName}Listtb").find("input[name='planDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {WdatePicker({ dateFmt: 'yyyy-MM-dd' });});
        $("#${statisticsName}Listtb").find("input[name='planDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {WdatePicker({ dateFmt: 'yyyy-MM-dd' });});

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

        $("#inoutTypeCode").combobox({
            panelHeight: 'auto',
            panelMaxHeight: '200',
            data:[{k:"J",v:"进港"},{k:"C",v:"出港"}],
            valueField: 'k',
            textField: 'v'
        });
        //拿到航空公司数据
        $("#flightCompanyName").combobox({
            panelHeight: 'auto',
            panelMaxHeight: '200',
            data:flightCompanyCodeDates,
            valueField: 'twoCode',
            textField: 'tCodechName'
        });
    });

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