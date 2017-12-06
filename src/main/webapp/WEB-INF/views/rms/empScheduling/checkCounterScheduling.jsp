<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>值机柜台分配</title>
    <link rel="stylesheet" href="${ctxAssets}/scripts/gantt/gantt.css">
    <link rel="stylesheet" href="${ctxAssets}/scripts/select2/dist/css/select2.min.css">
    <link rel="stylesheet" href="${ctxAssets}/scripts/spectrum-1.8.0/spectrum.css"/>
    <script src="${ctxAssets}/scripts/metadata-dev/garims.CookieUtils.js"></script>
    <script src="${ctxAssets}/scripts/spectrum-1.8.0/spectrum.js"></script>
</head>
<body>
<div class="easyui-layout" id="main-layout" fit="true">
    <div data-options="region:'center',title:'值机柜台分配'" fit="false" style="width:100%;">
        <table class="easyui-datagrid" id="datagrid" sortName="flightDynamicCode" sortOrder="asc"
               data-options="fitColumns:false,fit:true">
            <!-- 出港航班动态的表头 -->
            <thead>
            <tr>
                <th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>

                <th field="planDate" width="100" sortable="false" align="center" order="false" formatter="formatDate">
                    计划日期
                </th>
                <th field="flightDynamicCode" width="50" hidden="true" align="center" sortable="true">航班号</th>
                <th field="vitrualFlightNumber" formatter="vitrualFlightNumberFmt" width="100" align="center"
                    sortable="true">航班号
                </th>
                <th field="aircraftNum" width="80" align="center" sortable="true">机号</th>
                <th field="aircraftTypeCode" width="80" align="center" sortable="true">机型</th>
                <th field="placeNum" width="80" align="center" sortable="true">机位</th>
                <th field="status" width="70" align="center" hidden="true" sortable="true">状态编码</th>
                <th field="statusName" width="70" align="center" sortable="true">状态</th>
                <th field="flightNatureName" width="80" align="center" sortable="true">性质</th>
                <th field="flightDynamicNature" width="80" formatter="natureFmt" align="center" sortable="true">属性</th>
                <th field="inoutTypeName" width="70" align="center" sortable="true">进出港</th>

                <th field="flightDynamicId" width="100" hidden="true" sortable="false" order="false">航班动态编号</th>
                <th field="flightCompanyCode" width="50" hidden="true" align="center" sortable="false" order="false">
                    航空公司
                </th>
                <th field="departureAirportName" width="60" align="center" sortable="true">起飞</th>
                <th field="passAirport1Name" width="60" align="center" sortable="true">经停1</th>
                <th field="passAirport2Name" width="60" align="center" sortable="true">经停2</th>
                <th field="ext12" width="60" align="center" sortable="true">经停3</th>
                <th field="ext14" width="60" align="center" sortable="true">经停4</th>
                <th field="ext16" width="60" align="center" sortable="true">经停5</th>
                <th field="ext18" width="60" align="center" sortable="true">经停6</th>
                <th field="arrivalAirportName" width="60" align="center" sortable="true">到达</th>

                <th field="departurePlanTime" formatter="dateFmt" width="70" align="center" sortable="true">计飞</th>
                <th field="etd" formatter="dateFmt" width="70" align="center" sortable="true">预飞</th>
                <th field="atd" formatter="dateFmt" width="70" align="center" sortable="true">实飞</th>

                <th hidden="true" field="expectedCheckingCounterNum" width="50" align="center" sortable="true">预计占用数量
                </th>
                <th field="expectedStartTime" formatter="dateFmt" width="120" align="center" sortable="true">预计开始时间</th>
                <th field="expectedOverTime" formatter="dateFmt" width="120" align="center" sortable="true">预计结束时间</th>
                <th field="inteCheckingCounterCode" width="150" align="center" sortable="true">国内航段</th>
                <th field="inteActualStartTime" formatter="dateFmt" width="120" align="center" sortable="true">实际开始时间
                </th>
                <th field="inteActualOverTime" width="120" align="center" sortable="true">实际结束时间</th>
                <th field="intlCheckingCounterCode" width="150" align="center" sortable="true">国际航段</th>
                <th field="intlActualStartTime" width="120" align="center" sortable="true">实际开始时间</th>
                <th field="intlActualOverTime" width="120" align="center" sortable="true">实际结束时间</th>

                <th field="agentCode" width="100" align="center" sortable="true">代理ID</th>
                <th field="agentName" width="100" align="center" sortable="true">代理名称</th>
                <th field="delayResonName" width="120" align="center" sortable="true">延误原因</th>
            </tr>
            </thead>
        </table>
    </div>
    <div data-options="region:'south',split:true,border:true" style="height:180px;">
        <form action="${ctx}/rms/rmsEmp/save" id="modifyForm">
            <table width="100%" class="formtable">
                <tr>
                    <td style="width: 10%;">岗位：</td>
                    <td style="width: 20%;">
                        <select class="easyui-validatebox" id="postId" name="postId" style="width:130px;"
                                data-options="">
                        </select>
                    </td>
                    <td style="width: 10%;">值机人员：</td>
                    <td style="width: 60%;">
                        <select class="easyui-validatebox" id="empId" name="empId" style="width:80%;" data-options="">
                        </select>
                    </td>

                </tr>

            </table>
            <div style="text-align: center; margin-top:15px; margin-right:15px;">
                <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
            </div>
        </form>
    </div>

    <div id="checkin_time_input_dialog" class="easyui-dialog" title="值机时间录入"
         data-options="iconCls:'icon-save',closed:true" style="width:520px;height:200px;padding:10px">
        <form id="update_checkin_time_form" method="post" action="${ctx}/rms/checkCounterScheduling/updateScheduling">
            <table cellpadding="5">
                <tr>
                    <td>值机人员选择:</td>
                    <td colspan="3">
                        <input class="easyui-combobox" type="text" id="rmsEmp_id" name="rmsEmp.id"/>
                    </td>
                </tr>
                <tr>
                    <td>开始时间:</td>
                    <td><input class="easyui-datetimebox" type="text" id="checkinStartTime" name="checkinStartTime"
                               data-options="required:true"/></td>
                    <td>结束时间:</td>
                    <td><input class="easyui-datetimebox" type="text" id="checkinEndTime" name="checkinEndTime"
                               data-options="required:true"/></td>
                </tr>
            </table>
            <div style="text-align: center;margin-top: 30px;">
                <a href="javascript:saveCheckinTime();" class="easyui-linkbutton">保存</a>
                <a href="javascript:$('#checkin_time_input_dialog').dialog('close');" class="easyui-linkbutton"
                   style="margin-left: 15px;">取消</a>
            </div>
        </form>
    </div>
</div>

<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script>
    var flightNatures = {"1": "国内航班", "2": "国际航班", "3": "混合航班"};

    var falgCheckin, flagDgEdit, toolbars = [];

    <shiro:hasPermission name="rms:checkCounterScheduling:edit">falgCheckin = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:checkCounterScheduling:scleduling">flagDgEdit = true;
    </shiro:hasPermission>

    $(function () {

        if (falgCheckin) {
            toolbars.push({
                text: '值机时间录入',
                iconCls: 'icon-add',
                handler: function (resp) {
                    checkinTimeInputDialog();
                }
            })
        }

        datagrid = $("#datagrid").datagrid({
            url: ctx + "/ams/flightDynamic/listData", method: 'get',
            idField: "id",
            rownumbers: true,
            singleSelect: true,
            showFooter: true,
            multiSort: false,
            collapsible: true,
            //过滤掉进港航班
            loadFilter: function (data) {
                var value = {
                    total: data.total,
                    rows: []
                };
                for (var i = 0; i < data.rows.length; i++) {
                    var o = data.rows[i];
                    if (null != o && 'C' == o['inoutTypeCode']) {
                        value.rows.push(o);
                    }
                }
                return value;
            },
            toolbar: toolbars
        });

        $("#btnSave").linkbutton('disable');
        //添加点击编辑事件；
        if (flagDgEdit) {
            $("#datagrid").datagrid({
                onSelect: function (inx, row) {
                    fillEmp(inx, row);
                }
            });
            $("#btnSave").linkbutton('enable');

            //拿到岗位数据
            $("#postId").combotree({
                url: ctx + '/sys/office/treeNode?type=2',
                required: true,
                //选择树节点触发事件
                onSelect: function (node) {
                    //返回树对象
                    var tree = $(this).tree;
                    //选中的节点是否为叶子节点,如果不是叶子节点,清除选中
                    var isLeaf = tree('isLeaf', node.target);
                    if (!isLeaf) {
                        //清除选中
                        $('#postId').combotree('clear');
                    }
                    var selectPost = $('#postId').combotree('getValue');       // 获取原选择的节点

                    if (selectPost != node.id)
                        $("#empId").combobox('clear');

                    $("#empId").combobox('reload', ctx + '/rms/rmsEmp/jsonData?postId=' + node.id);
                }
            });

            //拿到员工数据
            $("#empId").combobox({
                panelHeight: 'auto',
                panelMaxHeight: '200',
                multiple: true,
                url: ctx + '/rms/rmsEmp/jsonData',
                valueField: 'id',
                textField: 'empName',
                editable: false
            });

            // 保存
            $('#btnSave').click(function () {
                var selected = datagrid.datagrid('getSelected');
                if (typeof(selected) == 'undefined' || !selected) {
                    $.messager.slide('请选择要分配的航班!');
                    return;
                }
                var empId = $("#empId").combobox('getValues');
                if (!empId || empId == null) {
                    $.messager.slide('请选在要分配的员工!');
                    return;
                }
                // Scheduling
                $.post(ctx + '/rms/checkCounterScheduling/scheduling', {
                    dynamicId: selected.id,
                    empId: empId
                }, function (resp) {
                    if (resp.code == 1) {
                        $.messager.slide('分配值班员工成功');
                        datagrid.datagrid('reload');
                    } else {
                        $.messager.slide(resp.message);
                    }
                });
            });
        }

        $('#rmsEmp_id').combobox({
            valueField: '_empId',
            textField: 'empName',
            onSelect: function (data) {
                var dynamicId = datagrid.datagrid("getSelected").id;
                $.get(ctx + '/rms/flightDynamicCheckin/getByDynamicIdAndEmpId', {
                    'flightDynamic.id': dynamicId,
                    'rmsEmp.id': data._empId
                }, function (data) {
                    $('#checkinStartTime').datetimebox('setValue', data.checkinStartTime);
                    $('#checkinEndTime').datetimebox('setValue', data.checkinEndTime);
                });
            }
        });
    });

    // 回填下方选择框
    function fillEmp(inx, row) {
        $.get(ctx + '/rms/flightDynamicCheckin/listData', {'flightDynamic.id': row.id}, function (resp) {
            if (typeof(resp) != 'undefined' && resp.length > 0) {
                var _ids = _.pluck(_.pluck(resp, 'rmsEmp'), 'id');
                // fillValues
                $("#empId").combobox('setValues', _ids);
                $.get(ctx + '/rms/rmsEmp/getPostId?empId=' + _ids[0], function (resp) {
                    if (!!resp) {
                        $("#postId").combotree('setValue', resp);
                        $("#empId").combobox('reload', ctx + '/rms/rmsEmp/jsonData?postId=' + resp);
                    }
                });
            } else {
                $("#empId").combobox('clear');
                $("#postId").combotree('clear');
            }
        });
    }

    function checkinTimeInputDialog() {
        var selected = datagrid.datagrid('getSelected');
        if (!selected) {
            $.messager.slide('请选择航班!');
            return;
        }
        $('#rmsEmp_id').combobox('clear');
        $('#checkinStartTime').datetimebox('setValue', '');
        $('#checkinEndTime').datetimebox('setValue', '');

        $('#checkin_time_input_dialog').dialog('open');
        $('#rmsEmp_id').combobox('reload', ctx + '/rms/flightDynamicCheckin/listData?flightDynamic.id=' + selected.id);
    }


    function saveCheckinTime() {
        var data = $('#update_checkin_time_form').serializeArray();
        data.push({'name': 'flightDynamic.id', 'value': datagrid.datagrid('getSelected').id});
        $.post(ctx + '/rms/checkCounterScheduling/updateScheduling', data, function (data) {
            if (typeof(data) != 'undefined' && data.code == 1) {
                $.messager.slide('保存成功!');
                $('#checkin_time_input_dialog').dialog('close');
            } else {
                $.messager.slide('保存失败,请稍后重试!');
            }
        });
    }

    function dateFmt(v, r, i, f) {
        return !v ? "" : moment(v).format("DD HH:mm");
    }

    function formatDate(v, r) {
        return v ? moment(v).format('YYYY-MM-DD') : "";
    }

    function natureFmt(v, r, i, f) {
        return _.propertyOf(flightNatures)(v);
    }

    function vitrualFlightNumberFmt(v, r, i, f) {
        return r.flightCompanyCode + r.flightNum;
    }

</script>
</body>
