<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>机位使用量统计</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div region="center" style="padding:1px;">
        <table width="100%" id="flightGateList" toolbar="#flightGateListtb">
        </table>
        <div id="flightGateListtb" style="padding:3px; height: auto">
            <div name="searchColums" style="display:inline">
                <input id="_sqlbuilder" name="sqlbuilder" type="hidden" />
                     <span style="display:-moz-inline-box;display:inline-block;">
                        <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; " title="使用日期">使用日期：</span>
                        <input class="inuptxt" type="text" id="startTime_begin" name="startTime_begin" onkeypress="EnterPress(event)" onkeydown="EnterPress()"> 至
                         <input class="inuptxt" type="text" id="overTime_end" name="overTime_end" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                    </span>
            </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <div style="height:30px;display:inline" class="datagrid-toolbar">
                <span style="float:right">
                    <a href="#" class="easyui-linkbutton" iconcls="icon-search" id="search" onclick="flightGateListsearch()">查询</a>
                    <a href="#" class="easyui-linkbutton" iconcls="icon-reload" onclick="searchReset('flightGateList')">重置</a>
                    <a href="#" class="easyui-linkbutton" iconcls="icon-20130406125519344_easyicon_net_16" onclick="downloadExcel()">下载</a>
                </span>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function(){
        $("#flightGateListtb").find("input[name='startTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'overTime_end\')}'});});
        $("#flightGateListtb").find("input[name='overTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime_begin\')}'});});
        //给时间控件加上样式
    });

    $(function() {
        storage = $.localStorage;
        if (!storage) storage = $.cookieStorage;
        $('#flightGateList').datagrid({
            idField: 'id',
            url: ctx + '/rms/statistics/flightGateUsedList',
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
            columns: [[{
                    field: 'gateNum',
                    title: '机位',
                    width: 120,
                    align: 'center',
                    sortable: true
                },
                {
                    field: 'num',
                    title: '使用次数',
                    width: 120,
                    align: 'center',
                    sortable: true
                }]],
            onLoadSuccess: function(data) {
                $("#flightGateList").datagrid("clearSelections");
            },
            onClickRow: function(rowIndex, rowData) {
                rowid = rowData.id;
                var selections = $('#flightGateList').datagrid('getSelections');
                gridname = 'flightGateList';
            }
        });
    });
    function reloadTable() {
        try {
            $('#' + gridname).datagrid('reload');
            $('#' + gridname).treegrid('reload');
        } catch(ex) {}
    }
    function reloadflightGateList() {
        $('#flightGateList').datagrid('reload');
    }

    function flightGateListsearch() {
        var queryParams = $('#flightGateList').datagrid('options').queryParams;
        $('#flightGateListtb').find('*').each(function() {
            queryParams[$(this).attr('name')] = $(this).val();
        });
        $('#flightGateList').datagrid({
            url: ctx + '/rms/statistics/flightGateUsedList',
            queryParams:queryParams
        });
    }
    function dosearch(params) {
        var jsonparams = $.parseJSON(params);
        $('#flightGateList').datagrid({
            url: ctx + '/rms/statistics/flightGateUsedList',
            queryParams: jsonparams
        });
    }
    function flightGateListsearchbox(value, name) {
        var queryParams = $('#flightGateList').datagrid('options').queryParams;
        queryParams[name] = value;
        queryParams.searchfield = name;
        $('#flightGateList').datagrid('reload');
    }
    $('#flightGateListsearchbox').searchbox({
        searcher: function(value, name) {
            flightGateListsearchbox(value, name);
        },
        menu: '#flightGateListmm',
        prompt: '请输入查询关键字'
    });
    function EnterPress(e) {
        var e = e || window.event;
        if (e.keyCode == 13) {
            flightGateListsearch();
        }
    }
    function searchReset(name) {
        $("#" + name + "tb").find(":input").val("");
        flightGateListsearch();
    }

    function downloadExcel(){
        location.href= ctx + '/rms/statistics/flightGateUsedExpExcel';
    }
</script>
<%--<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>--%>
</body>