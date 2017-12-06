<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>安检航班信息</title>
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

        .datagrid-btable > tbody > tr > td {font-weight:bold;}
    </style>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
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

                <%--<th field="aircraftNum" width="50" align="center" parent="airplane" sortable="true">飞机号</th>--%>
                <%--<th field="placeNum" width="50" align="center" parent="airplane" sortable="true">机位</th>--%>

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
                <%--<th field="aircraftTypeCode" width="50" align="center" parent="airplane" hidden="true" sortable="true">飞机<br/>机型</th>
                <th field="flightNum" hidden="true" width="80" align="center" sortable="true">进港<br/>航班号</th>
                <th field="flightNum2" hidden="true" width="80" align="center" sortable="true">出港<br/>航班号</th>

                <th field="flightDynimicId" width="50" hidden="true" align="center" sortable="true">进港航班ID</th>
                <th field="expectedGateNum" width="66" align="center" hidden="true" sortable="true">预计占用机位数</th>
                <th field="status" width="60" align="center" hidden="true" parent="in" sortable="true">进港航班状态码</th>
                <th field="flightDynimicId2" width="50" hidden="true" align="center" sortable="true">出港航班ID</th>
                <th field="status2" width="60" align="center" hidden="true" parent="out" sortable="true">出港航班状态码</th>

                <th field="occupiedStart" width="80" align="center" formatter="dateFmt" hidden="true" sortable="true">计划开始</th>
                <th field="occupiedEnd" width="80" align="center" formatter="dateFmt" hidden="true" sortable="true">计划结束</th>
                <th field="flightNatureCode" width="50" hidden="true" align="center" parent="in" sortable="true">进港性质</th>
                <th field="flightNatureCode2" width="50" hidden="true" align="center" parent="out" sortable="true">出港性质</th>

                <th field="agentCode" width="30" align="center" parent="proxy" hidden="true" sortable="true">代理<br/>ID</th>
                <th field="agentName" width="80" align="center" parent="proxy" hidden="true" sortable="true">代理<br/>名称</th>

                <!-- 飞机 -->
                <th field="safecuardTypeCode" width="50" align="center" parent="safeguard" hidden="true" sortable="true">保障<br/>编码</th>
                <th field="safecuardTypeName" width="80" align="center" parent="safeguard" hidden="true" sortable="true">保障<br/>名称</th>

                <th field="travelLine" width="150" align="center" hidden="true" sortable="true">航线</th>

                <!-- 进港长条开始 -->
                <th field="shareFlightNum" width="60" align="center" parent="in" hidden="true" sortable="true">进港<br/>共享航班</th>
                <th field="flightNatureName" width="50" align="center" parent="in" hidden="true" sortable="true">进港<br/>性质</th>

                <th field="passAirport1Code" width="100" align="center" formatter="globalPlaceFmt" parent="in"
                    hidden="true" sortable="true">进港<br/>经停1
                </th>
                <th field="passAirport2Code" width="100" align="center" formatter="globalPlaceFmt" parent="in"
                    hidden="true" sortable="true">进港<br/>经停2
                </th>
                <th field="ext10" width="100" align="center" formatter="globalPlaceFmt" parent="in" hidden="true" sortable="true">
                    进港<br/>经停3
                </th>
                <th field="ext12" width="100" align="center" formatter="globalPlaceFmt" parent="in" hidden="true" sortable="true">
                    进港<br/>经停4
                </th>
                <th field="ext14" width="100" align="center" formatter="globalPlaceFmt" parent="in" hidden="true" sortable="true">
                    进港<br/>经停5
                </th>
                <th field="ext16" width="100" align="center" formatter="globalPlaceFmt" parent="in" hidden="true" sortable="true">
                    进港<br/>经停6
                </th>
                <th field="departurePlanTime" width="80" align="center" formatter="dateFmt" parent="in" hidden="true" sortable="true">进港<br/>计飞
                </th>
                <th field="etd" width="80" align="center" formatter="dateFmt" parent="in" hidden="true" sortable="true">进港<br/>预飞</th>
                <th field="atd" width="80" align="center" formatter="dateFmt" parent="in" hidden="true" sortable="true">进港<br/>实飞</th>
                <th field="delayResonsName" width="150" align="center" parent="in" hidden="true" sortable="true">进港<br/>延误原因</th>
                <th field="delayResonsRemark" width="150" align="center" parent="in" hidden="true" sortable="true">进港<br/>备注</th>
                <th field="alternateAreaName" width="150" align="center" parent="in" hidden="true" sortable="true">进港<br/>备降</th>

                <!-- 出港长条开始 -->
                <th field="shareFlightNum2" width="60" align="center" parent="out" hidden="true" sortable="true">出港<br/>共享航班</th>
                <th field="flightNatureName2" width="50" align="center" parent="out" hidden="true" sortable="true">出港<br/>性质</th>
                <th field="passAirport1Code2" width="100" align="center" formatter="globalPlaceFmt" parent="out"
                    hidden="true" sortable="true">出港<br/>经停1
                </th>
                <th field="passAirport2Code2" width="100" align="center" formatter="globalPlaceFmt" parent="out"
                    hidden="true" sortable="true">出港<br/>经停2
                </th>
                <th field="ext18" width="100" align="center" formatter="globalPlaceFmt" parent="out" hidden="true" sortable="true">
                    出港<br/>经停3
                </th>
                <th field="ext20" width="100" align="center" formatter="globalPlaceFmt" parent="out" hidden="true" sortable="true">
                    出港<br/>经停4
                </th>
                <th field="ext22" width="100" align="center" formatter="globalPlaceFmt" parent="out" hidden="true" sortable="true">
                    出港<br/>经停5
                </th>
                <th field="ext24" width="100" align="center" formatter="globalPlaceFmt" parent="out" hidden="true" sortable="true">
                    出港<br/>经停6
                </th>
                <th field="arrivalPlanTime2" width="80" align="center" formatter="dateFmt" parent="out" hidden="true" sortable="true">出港<br/>计达
                </th>
                <th field="etd2" width="80" align="center" formatter="dateFmt" parent="out" hidden="true" sortable="true">出港<br/>预飞</th>
                <th field="eta2" width="80" align="center" formatter="dateFmt" parent="out" hidden="true" sortable="true">出港<br/>预达</th>
                <th field="ata2" width="80" align="center" formatter="dateFmt" parent="out" hidden="true" sortable="true">出港<br/>实达</th>
                <th field="delayResonsName2" width="150" align="center" parent="out" hidden="true" sortable="true">出港<br/>延误原因</th>
                <th field="delayResonsRemark2" width="150" align="center" parent="out" hidden="true" sortable="true">出港<br/>备注</th>--%>
            </tr>
            </thead>
        </table>
    </div>

    <div data-options="region:'south'" style="height:80px;">
        <form action="${ctx}/rms/securityServiceInfo/save" id="modifyForm">
            <input type="hidden" name="id">
            <input type="hidden" name="random">
            <input type="hidden" name="flightDynimicId">
            <input type="hidden" name="flightDynimicId2">
            <table width="100%" class="formtable">
                <tr>
                    <td>安检和勤务信息：</td>
                    <td>
                        <input type="text" name="serviceInfo" class="easyui-textbox easyui-validatebox" id="serviceInfo"
                               style="width: 100%;height:60px;"
                               data-options="multiline:true"
                        >
                    </td>
                    <td>
                        <div style="text-align: center; margin-top:15px; margin-right:15px;">
                            <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
                        </div>
                    </td>
                    <td width=220></td>
                </tr>
            </table>
        </form>

    </div>
</div>
<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script src="${ctxAssets}/scripts/aui-artTemplate/dist/template.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.resource.dist.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/securityServiceInfo.js"></script>
<script>
    var moduleContext = "securityServiceInfo";
    flagAutoHight = true, isFitColumns = false;

    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:securityServiceInfo:edit">flagDgEdit = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:securityServiceInfo:insert">flagDgInsert = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:securityServiceInfo:del">flagDgDel = true;
    </shiro:hasPermission>
    flagRefresh = true;

    function formatDate(v, r, i, f) {
        return !v ? "" : moment(v).format("YYYY-MM-DD");
    }

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

    function dateFmt(v, r, i, f) {
        return !v ? "" : moment(v).format("DD日 HH:mm");
    }

    function globalPlaceFmt(val, row, index, field) {
        return field.indexOf("ext") !== -1 ? !val ? "" : val + " " + row["ext" + (parseInt(field.replace("ext", '')) + 1)] : !val ? "" : val + " " + row[field.replace("Code", "Name")]
        //return field.indexOf("ext") !== -1 ? !val ? "" :row["ext" + (parseInt(field.replace("ext", '')) + 1)] : !val ? "" : row[field.replace("Code", "Name")]

    }

    //选中时回填
    function preSelectFn(index, row) {
        $("#modifyForm [name=flightDynimicId]").val(row.flightDynimicId);
        $("#modifyForm [name=flightDynimicId2]").val(row.flightDynimicId2);
        if (!row.id) {
            $("#modifyForm [name=id]").val("");
            $("#serviceInfo").textbox('setText', "");
            $("#modifyForm [name=serviceInfo]").val("");
        }
    }

    //datagrid加载成功时调用  隐藏数据显示
    function loadsuccessFn(data) {
        datagrid.datagrid('doCellTip', {
            onlyShowInterrupt: true, position: 'bottom', maxWidth: '200px',
            tipStyler: {'backgroundColor': '#fff000', borderColor: '#ff0000', boxShadow: '1px 1px 3px #292929'}
        });
    }

    //保存前调用
    function preSaveFn() {
        var selected = datagrid.datagrid("getSelected");
        if (selected) {//重新保存时回填
            $("#modifyForm [name=id]").val(selected.id);
            $("#modifyForm [name=flightDynimicId]").val(selected.flightDynimicId);
            $("#modifyForm [name=flightDynimicId2]").val(selected.flightDynimicId2);
            if (_socket_uuid) $("#modifyForm [name=random]").val(_socket_uuid);
        } else {
            $.messager.show({title: "提示", msg: "请选择后再操作！", timeout: 3000, showType: 'slide'});
            return true;
        }
    }

    //保存后调用
    function afterSaveFn(data) {
        var selected = datagrid.datagrid('getSelected');
        if (selected) {
            var rowIndex = datagrid.datagrid('getRowIndex', selected);
            datagrid.datagrid('updateRow', {index: rowIndex, row: _.extend(selected, data.result.data)});
        }
    }


    //socket数据刷新

    var reloadData = _.debounce(() => {
        var selected = datagrid.datagrid('getSelected');
        datagrid.datagrid("reload");
        if (selected) {
            datagrid.datagrid("clearSelections");
            var rowIndex = datagrid.datagrid('getRowIndex', selected);
            loadsuccessFn = function () {
                datagrid.datagrid('selectRow', rowIndex)
            };
        }
    }, 5000);

    var _socket_uuid = $uuid(), extraData = {random: _socket_uuid},
        socket = io.connect(message_socket_server_url, {query: "ns=/global/dynamics&uuid=" + _socket_uuid}),
        socketEntity = io.connect(message_socket_server_url, {query: "ns=/rms/securityServiceInfo&uuid=" + _socket_uuid});

    socket.on("modification", function (data) {
        if (data.uuid === "" || data.uuid === "null" || !data.uuid) return false;
        _.defer(function (data) {
            let _identify = data.identify, monitorType = data.monitorType;
            if ("BoardingGateOccupyingInfo" === _identify) {
                //reloadData();
                let _entity = $.parseJSON(data.data), _changeList = $.parseJSON(data.changeList);
                if (_.some(_changeList, (o) => o.fieldName == "inteBoardingGateCode")) reloadData();
            } else if ("DepartureHallOccupyingInfo" === data.identify) {
                //reloadData();
            }

            if (data.monitorType === 'INSERT' && "FlightPairWrapper" === _identify) {
                reloadData();
            } else if (_identify === "FlightPairWrapper" && monitorType === "DELETE") {
                reloadData();
            }
        }, data);
    }).on('flightDynamicChange2History', reloadData)

    socketEntity.on("modification", function (data) {
        if (data.uuid === "" || data.uuid === "null" || !data.uuid) return false;
        _.defer(function (data) {
            if ("SecurityServiceInfo" === data.identify) {
                if (extraData.random != data.uuid) reloadData();
            }
        }, data);
    })
</script>
</body>