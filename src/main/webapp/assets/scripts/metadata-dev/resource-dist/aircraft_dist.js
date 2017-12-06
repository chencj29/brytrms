const TPL_NAME = 'aircraft-stand', moduleContext = 'aircraft', moduleName = '机位', inoutTypeCode = 'R';
var xAxis = enhanceXAxis(), asyncRenderCompleted = false,
    yAxis = $utils.ajaxLoadFragments(ctx + '/rms/aircraftStand/listJson');
var gateGanttOptions = {
        auto: true, blinkFlagOp: true,
        xAxis: xAxis,
        yAxis: yAxis,
        timeline: true,
        draggableAxis: 'y',
        resizableHandler: false,
        ajaxUrl: ctx + '/rms/rs/distedGateListJson',
        collisionThresholds: 15,
        ajaxParams: {startDate: _.first(xAxis), overDate: _.last(xAxis)},
        move2HoldAreaCallback: clearAircraftFn,
        asyncRenderCallback: asyncGanttElementRenderTaskFn,
        leftAxisClickHandler: function () {
            if (!datagrid.datagrid('getSelected') && !draggable_holder) {
                layer.msg('请选择一条记录再进行操作！');
                return false;
            }

            if ($(this).attr("_available") == '0') {
                layer.alert('当前机位「' + $(this).text() + '」不可用，无法分配！');
                return false;
            }
            dist_gate_manually($(this).text(), datagrid.datagrid('getSelected'));
        },
        promptHandler: function (oData, event) { // 弹出提示层
            layer.tips(fetchReourceTipTpl(TPL_NAME, oData), $("div[_uid_=" + oData.uid + "]"), {
                tips: [1],
                tipsPosition: true,
                mouse: {left: event.pageX}
            });
        },
        draggableStartCallback: function (event, ui) {
            if (ui.helper.position() === undefined) return false;

            if (!checkOperationPermission(ui.helper.data('o').flightCompanyCode, ui.helper.data('o').agentCode)) {
                layer.msg('您没有权限操作此航班！');
                return false;
            }

            var index = datagrid.datagrid('getRowIndex', ui.helper.data('o').flightDynamicId);
            if (index !== -1) datagrid.datagrid('selectRecord', ui.helper.data('o').flightDynamicId);
            else datagrid.datagrid('clearSelections');
            draggable_holder = ui.helper.data('o');
            ui.helper.data('originPos', ui.helper.position());
            origin_drag_elem = ui.helper;
            origin_drag_target = ui.helper.data('o').gate;
        },
        dblClick: function (evt) {
            //通过Gantt图中的ID找到DataGrid相应的行
            var rowData = datagrid.datagrid('getRows').filter((x) => x.id === evt.data.flightDynamicId)[0];
            if (rowData) {
                var rowIndex = datagrid.datagrid('getRowIndex', rowData);
                datagrid.datagrid('selectRow', rowIndex);
            }

            $(this).addClass("blink");
            var that = $(this);
            setTimeout(function () {
                that.removeClass('blink');
            }, 6000);
        }
    },
    datagrid, dataGridMeta, draggable_holder, origin_drag_target, origin_drag_elem,
    toolbars = [], // 按钮数组
    flagMockDist, // 模拟分配
    flagAutoAllocation, // 自动分配
    flagManualAllocation, // 手动分配
    flagFlightGant, // 显示甘特图
    flagEndFlightOccupation, // 占用时间录入
    flagCancelAllocation, // 取消分配
    socketUpdateGannt; //控制甘特图的刷新频率

$(function () {
    loadDataGridContent(); //加载表格内容

    if (flagMockDist) toolbars.push({text: '模拟分配', iconCls: 'icon-lightbulb', handler: mockDistFn})

    //添加自动分配按钮；
    if (flagAutoAllocation)
        toolbars.push({
            text: '自动分配',
            iconCls: 'icon-lightning',
            handler: () => commonDistributedResourceAuto('/rms/rs/gateDistAutomatically', moduleName, true)
        });

    if (flagManualAllocation) {
        toolbars.push({
            text: '手动分配', iconCls: 'icon-bullet_wrench', handler: function () {
                var selectedRow = datagrid.datagrid('getSelected');
                if (selectedRow) {
                    var placeNumberArray = !selectedRow.placeNum ? [] : selectedRow.placeNum.split(",");

                    layer.open({
                        title: '手动分配',
                        btn: ['确定', '取消'],
                        area: ['500px', '150px'],
                        type: 1,
                        scrollbar: false,
                        move: false,
                        content: fetchResourceDistTpl(TPL_NAME, {
                            flightPlanPairId: selectedRow.flightDynamicId,
                            list: yAxis,
                            placeNumberArray: placeNumberArray
                        }),
                        success: () => $("#selGate").select2({
                            allowClear: true,
                            placeholder: '请选择机位',
                            maximumSelectionLength: selectedRow.expectedGateNum
                        }),
                        yes: function (index, layero) {
                            var selValue = $("#selGate").val();
                            if (!selValue) {
                                if (!placeNumberArray || placeNumberArray.length === 0) {
                                    layer.close(index);
                                    return false;
                                }

                                clearAircraftFn();
                                layer.close(index);
                            }

                            if (placeNumberArray.sort().join(",") != selValue.sort().join(",")) {
                                dist_gate_manually(selValue.sort().join(","), selectedRow);
                                layer.close(index);
                            } else {
                                layer.close(index);
                                return false;
                            }
                        }
                    });
                } else layer.msg('请选择一条记录再进行操作！');
            }
        });
    }

    //添加机位赶甘特图铵钮；
    if (flagFlightGant) toolbars.push({text: '甘特图', iconCls: 'icon-zoom', handler: showGraphicPanel});

    //添加结束机位占用铵钮；
    if (flagEndFlightOccupation) {
        toolbars.push({
            text: '结束占用', iconCls: 'icon-stop', handler: function () {
                var selectedRow = datagrid.datagrid('getSelected');
                if (selectedRow) {
                    layer.open({
                        title: '机位占用结束设置',
                        btn: ['确定', '取消'],
                        area: ['400px', '174px'],
                        type: 1,
                        scrollbar: false,
                        move: false,
                        content: template('gateLeaveTpl')({flightDynamicId: selectedRow.id}),
                        yes: function (index, layero) {
                            if (_.isEmpty($("#iptLeaveTime").val())) {
                                layer.tips('请选择时间！', "#iptLeaveTime", {tips: 1});
                                return false;
                            }

                            $.post(ctx + "/rms/rs/aircraftLeaveGate", $.extend({}, $("#gateLeaveForm").serializeObject(), extraData), function (data) {
                                layer.msg(data.message);
                                if (data.code) datagrid.datagrid('reload');
                                layer.close(index);
                            });
                            return false;
                        }
                    });
                } else layer.msg("请选中一条记录再进行操作！");
            }
        });
    }

    //添加取消机位分配铵钮；
    if (flagCancelAllocation) toolbars.push({
        text: '取消分配', iconCls: 'icon-cross', handler: function () {
            var selectedRow = datagrid.datagrid('getSelected');
            if (selectedRow) clearAircraftFn(selectedRow);
            else layer.msg('请选择一条记录！');
        }
    });

    //添加双击事件；显示甘特图  单击vip格中的“有”显示vip详情
    if (flagFlightGant) $("#datagrid").datagrid({onDblClickRow: scroll2CallbackFn, onClickCell: fetchVipInfos});

    toolbars.push({text: '颜色设置', iconCls: 'icon-color', handler: colorSettingHandler}, dataGridViewSettingToolbar, {
        text: '刷新数据', iconCls: 'icon-arrow_refresh_small', handler: commonDataRefreshHandler
    });

    datagrid = $("#datagrid").datagrid({
        rownumbers: true,
        url: ctx + "/rms/rs/flightDynamics",
        method: 'get',
        fitColumns: false,
        idField: "id",
        singleSelect: true,
        showFooter: true,
        remoteSort: false,
        multiSort: true,
        toolbar: toolbars,
        rowStyler: commonRowStyler,
        onLoadSuccess: layoutAutomaticallyAdapter
    });
    datagrid.datagrid('enableFilter');
    datagrid.datagrid('doCellTip', cellTooltipCommonProps);

    //调整甘特图的结束占用时间
    //setInterval(pairTimeoutGanttChange, 6000);

    //控制甘特图的更新频率
    socketUpdateGannt = _.throttle((_entity, $ganttEl, ganttOpts) => {
        var gd = generateGanttWrapper(_entity);
        if (!gd.text || gd.text === '') return;
        $ganttEl.each(commonGanttElementRemoveFn);
        ganttOpts.data = _(ganttOpts.data).reject((data) => data.flightDynamicId === _entity.id);
        $.fn.gantt("append", generateGanttWrapper(_entity), ganttOpts);
    }, 3000)
});


function dist_gate_manually(_aircraft_num, selectedRow) {
    //var selectedRow = datagrid.datagrid('getSelected');
    if (!selectedRow) {
        if (draggable_holder) selectedRow = enhanceGetDGSelectedRow(draggable_holder.flightDynamicId)
        else return false;
    }

    var settings = $(".gantt").data("options"), old_place_num;
    if (!selectedRow || _aircraft_num == selectedRow.placeNum) return false;
    selectedRow.old_place_num = old_place_num = selectedRow.placeNum;
    var flightNum = $utils.superTrim(selectedRow.flightNum), flightNum2 = $utils.superTrim(selectedRow.flightNum2),
        finalFn;
    finalFn = $utils.superTrim(selectedRow.flightCompanyCode || selectedRow.flightCompanyCode2) + (!_.isEmpty(flightNum) && !_.isEmpty(flightNum2) ? flightNum + "/" + flightNum2 : flightNum + flightNum2);

    layer.confirm("您确定要将航班「" + finalFn + "」分配到「" + _aircraft_num + "」号机位吗？", function (index) {
        selectedRow.placeNum = _aircraft_num;
        checkingIndex = distributedWaitingDialog();

        $.post(ctx + '/rms/rs/aircraft_num_manual_dist', $.extend({}, {
            id: selectedRow.id,
            expectAircraftNum: _aircraft_num,
            force: false
        }, extraData), function (data) {
            layer.close(checkingIndex);
            if (data.success) {
                layer.msg(data.message);
                updateResourcePosition(selectedRow.id, data.result)
            } else {
                if (data.errorType == "1") { // 数据未同步，无需强制分配
                    layer.alert(data.message);
                    updateResourcePosition(selectedRow.id, selectedRow.old_place_num);
                    return;
                }

                if (data.errorType == "-3") { // 数据被拦截，无需强制分配
                    selectedRow.placeNum = selectedRow.old_place_num;
                    layer.close(index);
                    layer.alert(data.message);
                    updateResourcePosition(selectedRow.id, selectedRow.old_place_num);
                    return;
                }

                layer.confirm(data.message + "<br/>是否要强制分配？", function (index) {
                    if (selectedRow.expectedGateNum && selectedRow.expectedGateNum != 1) { //多机位
                        //找到当前机位的index, 根据expectedGateNum进行遍历，拿到机位
                        var gateArray = _.pluck(settings.yAxis, "text"),
                            currentIndex = _.indexOf(gateArray, _aircraft_num), aircraftStandArray = [_aircraft_num];
                        for (var i = 1, len = selectedRow.expectedGateNum; i < len; i++) aircraftStandArray.push(gateArray[++currentIndex]);
                        _aircraft_num = aircraftStandArray.join(",");
                    }

                    $.post(ctx + '/rms/rs/aircraft_num_manual_dist', $.extend({}, {
                        id: selectedRow.id,
                        expectAircraftNum: _aircraft_num,
                        force: true
                    }, extraData), function (data) {
                        if (data.code === 1) {
                            layer.msg(data.message);
                            updateResourcePosition(selectedRow.id, data.result);
                        } else {
                            layer.alert(data.message);
                            updateResourcePosition(selectedRow.id, selectedRow.old_place_num)
                        }
                    });
                }, () => {
                    layer.closeAll();
                    updateResourcePosition(selectedRow.id, old_place_num);
                });
            }
        });
    }, () => {
        if (origin_drag_elem) normalDragStopFn(origin_drag_elem)
    });
}

function scroll2CallbackFn() {
    var oData = datagrid.datagrid("getSelected");
    if (oData) {
        if (!oData.occupiedStart && !oData.occupiedEnd) return;
        // 如果没有展开面板就选展开
        if (!isExpand) showGraphicPanel();
        $(".gantt-content-scroller-wrapper").gantt("scroll2Now", $(".gantt").data('options'), moment(oData.occupiedStart).toDate());

        try {
            var top = $(".airplane-wrapper > div").filter(function () {
                return $(this).data('o').flightDynamicId === oData.id;
            }).position().top;
        } catch (e) {
        }

        if (oData.placeNum && top) $(".gantt-content-scroller-wrapper").scrollTop(top);
        else $(".gantt-content-scroller-wrapper").scrollTop(0);

        $(".airplane-wrapper > div").filter(function () {
            return $(this).data('o').flightDynamicId === oData.id;
        }).each(function () {
            $(this).addClass("blink");
            var that = $(this);
            setTimeout(function () {
                that.removeClass('blink');
            }, 3600);
        });
    }
}

function ganttReload() {
    $(".gantt").gantt('reload', ctx + '/rms/rs/distedGateListJson', {
        startDate: _.first(xAxis),
        overDate: _.last(xAxis)
    });
    //调整高度 wjp_2017年9月23日15时09分
    var gantHeight = $(".gantt-left-panel").parent().height();
    $(".gantt-content-scroller-wrapper").height(gantHeight - 116);
    $(".gantt-left-panel").height(gantHeight - 42);
    $(".gantt-content-scroller-wrapper").gantt("scroll2Now", $(".gantt").data('options'));
}

function clearAircraftFn() {
    var selectedRow = datagrid.datagrid('getSelected');
    if (!selectedRow) {
        if (draggable_holder) selectedRow = enhanceGetDGSelectedRow(draggable_holder.flightDynamicId)
        else {
            layer.msg('请选择您要操作的记录！')
            return false;
        }
    }

    if (selectedRow) {
        if (selectedRow.placeNum) {
            layer.confirm(`是否要清除航班「${selectedRow.virtualTipField}」分配的「${selectedRow.placeNum}」号机位？`, {
                icon: 3,
                title: '提示'
            }, function (index) {
                $.post(ctx + '/rms/rs/clearAircraftNum', $.extend({}, {flightPlanPairId: selectedRow.id}, extraData), function (data) {
                    layer.msg(data.message);
                    if (data.code == 1) {
                        enhanceUpdateDGRow("id", selectedRow.id, {placeNum: ''});
                        if (isExpand) {
                            var ganttOpts = $(".gantt").data('options');
                            $(".airplane-wrapper > div").filter(function () {
                                return $(this).data('o').flightDynamicId === selectedRow.id;
                            }).each(commonGanttElementRemoveFn);

                            ganttOpts.data = _(ganttOpts.data).reject((data) => data.flightDynamicId === selectedRow.id);
                            selectedRow.placeNum = "";
                            $.fn.gantt("append", generateGanttWrapper(selectedRow), ganttOpts);
                        }
                        layoutAutomaticallyAdapter();
                    } else if (data.code == -3) {
                        layer.close(index);
                        layer.alert(data.message);
                        updateResourcePosition(selectedRow.id, selectedRow.placeNum)
                    }
                });
            }, () => normalDragStopFn(origin_drag_elem));
        }
    } else layer.msg("请选中一条记录再进行操作！");
}

var _socket_uuid = $uuid(), extraData = {random: _socket_uuid},
    socket = io.connect(message_socket_server_url, {query: "ns=/global/dynamics&uuid=" + _socket_uuid});
socket.on("modification", function (data) {
    try {
        if ($('#maintabs', window.parent.document).find('.tabs-selected').text() != document.title) return;
    } catch (e) {
    }

    if (data.uuid === "" || data.uuid === "null" || !data.uuid) return false;
    _.defer(function (data) {
        let _entity = $.parseJSON(data.data), _changeList = $.parseJSON(data.changeList),
            _identify = data.identify, _compareId = _entity.id, rowIndex = -1,
            rowData, monitorType = data.monitorType, ganttOpts = $(".gantt").data("options"),
            filtered, // 是否过滤(标识)
            filteredRowIndex, // 过滤后的下标
            filteredRowData; // 过滤后的数据

        //过滤COBT消息，不用更新
        if (!_.isEmpty(_changeList) && _changeList[0]["fieldDesc"] == "COBT") {
            return;
        }

        // 权限判断
        if (!_.contains(currentUserPrivileges, ALL_PRIVILEGE) &&
            (!_.contains(currentUserPrivileges, _entity.flightCompanyCode) && !_.contains(currentUserPrivileges, _entity.flightCompanyCode2)) &&
            !_.contains(currentServicePrivileges, ALL_PRIVILEGE) && !_.contains(currentServicePrivileges, _entity.agentCode)) return;

        // 如何根据实体类型在DataGrid中找到相当的数据及行索引
        if (_identify === 'FlightPairWrapper' || _identify === 'FlightPlanPair') [filtered, filteredRowIndex, filteredRowData, rowIndex, rowData] = getRowIndex("id", _compareId);
        else {
            _compareId = _entity.flightDynamicId;
            [filtered, filteredRowIndex, filteredRowData, rowIndex, rowData] = getRowIndex("flightDynamicId", _compareId);
        }

        if (_identify === "FlightPairWrapper" && monitorType === "INSERT") {
            _entity.random = extraData.random;
            let mockGanttData = generateGanttWrapper(_entity);
            _entity.occupiedStart = mockGanttData.occupiedStart;
            _entity.occupiedEnd = mockGanttData.occupiedEnd;
            var filterData = $.fn.datagrid.defaults.filterMatcher.call(datagrid[0], {total: 1, rows: [_entity]});
            // 将命中的数据添加到datagrid中
            for (var matchData of filterData.rows) datagrid.datagrid("appendRow", matchData);
            // 将未命中的数据添加到filterSource中
            if (filterData.unMatchedRows.length !== 0)
                for (var unMatchData of filterData.unMatchedRows) datagrid.datagrid('getData').unMatchedRows.push(unMatchData);
            if (isExpand) $.fn.gantt("append", mockGanttData, ganttOpts);
            //sortDate(datagrid, inoutTypeCode);
            sortDateR(datagrid);
            layoutAutomaticallyAdapter();
        } else if (_identify === "FlightPairWrapper" && monitorType === "DELETE") {
            if (!_.isEmpty(_entity.id) && ((!filtered && rowIndex !== undefined && rowIndex != -1) || (filtered && filteredRowIndex != -1))) {
                _entity.random = extraData.random;
                // 删除联班数据步骤 1 - 删除相关资源, 包括联班中dynamic ids 的相关资源 2 - 移除DataGrid中的行 3 - 移除Gant元素 4 - 移除Gantt Options Data
                //datagrid.datagrid("deleteRow", datagrid.datagrid("getRowIndex", _entity.id));
                if (!filtered) datagrid.datagrid('deleteRow', rowIndex);
                else {
                    if (rowIndex !== undefined && rowIndex !== -1) datagrid.datagrid('deleteRow', rowIndex);
                    datagrid.data('datagrid').filterSource.rows.splice(filteredRowIndex, 1);
                    if (filteredRowIndex > -1) datagrid.datagrid('doFilter');
                }

                if (isExpand) {
                    $(".airplane-wrapper > div").filter(function () {
                        return $(this).data('o').flightDynamicId === _entity.id
                    }).each(commonGanttElementRemoveFn);
                    ganttOpts.data = _(ganttOpts.data).reject((data) => data.flightDynamicId === _entity.id);
                }
                //sortDate(datagrid, inoutTypeCode);
                sortDateR(datagrid);
                layoutAutomaticallyAdapter();
            }
        } else if (monitorType === "UPDATE") {
            if (_identify === "FlightPlanPair" && data.uuid == _socket_uuid) return;

            if ((!filtered && rowIndex !== undefined && rowIndex != -1) || (filtered && filteredRowIndex != -1)) {
                var changeWrapper = getChangeWrapper(_changeList);

                //过滤cobt的更新
                if ((_identify === "FlightPlanPair" || _identify === "FlightPairWrapper") && _.contains(_.keys(changeWrapper), "ext8")) return;

                if (!filtered) datagrid.datagrid("updateRow", {index: rowIndex, row: changeWrapper});
                else {
                    if (rowIndex !== undefined && rowIndex !== -1) datagrid.datagrid('updateRow', {
                        index: rowIndex,
                        row: changeWrapper
                    });
                    datagrid.data('datagrid').filterSource.rows[filteredRowIndex] = $.extend(datagrid.data('datagrid').filterSource.rows[filteredRowIndex], changeWrapper);
                    //datagrid.datagrid('doFilter');
                }

                refreshRowBGColor(changeWrapper, rowIndex);

                //如果图形面板展开，更新图形位置
                var $ganttEl = $(".airplane-wrapper > div").filter(function () {
                    return $(this).data('o').flightDynamicId === _compareId;
                });

                if (isExpand) {
                    socketUpdateGannt(_entity, $ganttEl, ganttOpts);
                }
                //判断包含有的字段才排序
                if (_.intersection(_.keys(changeWrapper), sortRules.R).length > 0) sortDateR(datagrid);
                layoutAutomaticallyAdapter();
            }
        }
    }, data);
}).on("flightDynamicChange2History", function (data) {
    try {
        if ($('#maintabs', window.parent.document).find('.tabs-selected').text() != document.title) return;
    } catch (e) {
    }

    xAxis = enhanceXAxis();
    if (!data || _.isEmpty(data)) return;
    let rowIndex = -1, // 未过滤的下标
        rowData, // 未过滤的数据
        filtered, // 是否过滤(标识)
        filteredRowIndex, // 过滤后的下标
        filteredRowData; // 过滤后的数据

    _.defer(function (data) {
        try {
            for (var _key of _(data).keys()) {
                if (_key.indexOf("flightPairWrapper") != -1) { // 元素删除的时候，如果该元素有collisionRefs， 要将这些CollisionRefs检测是否清除冲突状态
                    let valuesArray = _key.split("#"), mType = valuesArray[0], pairId = valuesArray[1],
                        pairObject = JSON.parse(_.propertyOf(data)(_key));
                    [filtered, filteredRowIndex, filteredRowData, rowIndex, rowData] = getRowIndex("id", pairId);
                    if ((!filtered && rowIndex !== undefined && rowIndex != -1) || (filtered && filteredRowIndex !== -1)) {
                        // 这里要分过滤及非过滤两种情况的处理  此处是非过滤的情况
                        if (!filtered) datagrid.datagrid('deleteRow', rowIndex);
                        else {
                            if (rowIndex !== undefined && rowIndex !== -1) datagrid.datagrid('deleteRow', rowIndex);
                            datagrid.data('datagrid').filterSource.rows.splice(filteredRowIndex, 1);
                        }

                        $(".airplane-wrapper > div").filter(function () {
                            return $(this).data('o').flightDynamicId === pairId
                        }).each(commonGanttElementRemoveFn);
                        if ($(".gantt").data('options'))
                            $(".gantt").data('options').data = _($(".gantt").data('options').data).reject(data => data.flightDynamicId === pairId);
                    }
                }
            }
            //sortDate(datagrid, inoutTypeCode);
            sortDateR(datagrid);
            layoutAutomaticallyAdapter();
        } catch (e) {
            console.error(e);
        }
    }, data);
}).on('flightDynamicTime2Change', function (data) {
    if (!data) return;
    if (isExpand) {
        _.defer(function (data) {
            var data = $.parseJSON(data);
            var pair = data.pair;
            $.ajax({
                url: ctx + '/rms/gateOccupyingInfo/fetchByFlightDynamicId',
                data: {flightDynamicId: pair.id},
                async: false,
                dataType: 'json'
            }).then(function (oci) {
                pair = $.extend({}, pair, {
                    flightDynamicNature: oci.flightDynamicNature, id: pair.id,
                    expectedStartTime: _formatDateTime(oci.startTime), expectedOverTime: _formatDateTime(oci.overTime),
                    flightDynamicCode: pair.flightNum, expectedGateNum: oci.expectedGateNum, placeNum: pair.placeNum
                });
            });
            updateResourcePositionNew(pair.id, pair);
        }, data);
    }
});

function fnAfterInited(resp) {
    if (resp.type !== moduleName) return resourceInitCallbackFn({
        restResourceArray: resp.restResourceArray,
        data: data,
        type: moduleName,
        success: true
    });
    var data = resp.data, filtered = isFiltered(), planDate = formatDate(data.planDate), currentMaxPlanDate = xAxis[1];
    if (!_.has(data, 'oci')) { // 初始化失败
        removeDataByPlanDate(planDate);
        return resourceInitCallbackFn({
            restResourceArray: resp.restResourceArray,
            data: data,
            type: moduleName,
            success: true
        });
    }
    if (data.pairWrappers.length === 0) return resourceInitCallbackFn({
        restResourceArray: resp.restResourceArray,
        data: data,
        type: moduleName,
        success: true
    });
    var ociDatas = data.oci[moduleName];
    if (_.isEmpty(ociDatas)) return resourceInitCallbackFn({
        restResourceArray: resp.restResourceArray,
        data: data,
        type: moduleName,
        success: true
    });

    let wrappers = [];

    _.each(data.pairWrappers, (pair) => {
        var oci = _(ociDatas).find(condition => condition.flightDynamicId === pair.id);
        wrappers.push(_.extend({}, pair, {
            flightDynamicNature: oci.flightDynamicNature,
            expectedGateNum: oci.expectedGateNum,
            placeNum: '',
            flightDynamicCode: pair.flightNum,
            expectedStartTime: _formatDateTime(oci.startTime),
            expectedOverTime: _formatDateTime(oci.overTime),
            occupiedStart: _formatDateTime(oci.startTime),
            occupiedEnd: _formatDateTime(oci.overTime)
        }));
    });

    if (filtered) datagrid.data('datagrid').filterSource.rows = datagrid.data('datagrid').filterSource.rows.filter(row => formatDate(row.planDate) != planDate);
    // 找出当前数据源中不等于planDate的数据
    let unMatchedDatas = datagrid.datagrid('getData').rows.filter(row => formatDate(row.planDate) != planDate),
        privilegePairWrappers = (_.contains(currentUserPrivileges, ALL_PRIVILEGE) || _.contains(currentServicePrivileges, ALL_PRIVILEGE)) ? wrappers :
            wrappers.filter((pairWrapper) => _.contains(currentUserPrivileges, pairWrapper.flightCompanyCode) || _.contains(currentUserPrivileges, pairWrapper.flightCompanyCode2) || _.contains(currentServicePrivileges, _entity.agentCode)),
        finalRows = _.union(unMatchedDatas, privilegePairWrappers);
    if (filtered) finalRows = _.union(finalRows, datagrid.data('datagrid').filterSource.rows);

    _inOutType = inoutTypeCode;
    _loopCounter = 0;
    finalRows = mysort(finalRows, inoutTypeCode);

    datagrid.datagrid('loadData', {total: finalRows.length, rows: finalRows});
    if (isExpand) {
        var planDateMt = moment(planDate), currentMaxPlanDateMt = moment(currentMaxPlanDate),
            isAfter = planDateMt.isAfter(currentMaxPlanDate);
        if (isAfter) xAxis = [planDateMt.add(-1, "day").format("YYYY-MM-DD"), planDateMt.format("YYYY-MM-DD"), planDateMt.add(1, "day").format("YYYY-MM-DD")];
        if (_.contains(xAxis, planDate) || isAfter) ganttReload();
    }

    // 加载完后，调用回调函数
    if (resourceInitCallbackFn) resourceInitCallbackFn({
        restResourceArray: resp.restResourceArray,
        data: data,
        type: moduleName,
        success: true
    });
}

function formatFlightNum(val, row, index) {
    if (row.flightNum != null && row.flightNum != '') return row.flightCompanyCode + row.flightNum;
    else return "";
}

function updateResourcePosition(resourceId, resourceNum) {
    enhanceUpdateDGRow("id", resourceId, {placeNum: resourceNum});
    if (isExpand) {
        var $ganttEl = $(".airplane-wrapper > div").filter(function () {
            return $(this).data('o').flightDynamicId === resourceId;
        });

        var ganttData = $ganttEl.data('o'), ganttOpts = $(".gantt").data('options');
        ganttOpts.data = _(ganttOpts.data).reject((data) => data.id === ganttData.id);
        ganttData.gate = !_.isEmpty(resourceNum) ? resourceNum.split(",") : [];
        $ganttEl.each(commonGanttElementRemoveFn);
        $.fn.gantt("append", ganttData, ganttOpts);
    }
    layoutAutomaticallyAdapter();
    clearManuallyGanttRefs();
}

function formatFlightNum2(val, row, index) {
    if (row.flightNum2 != null && row.flightNum2 != '') return row.flightCompanyCode2 + row.flightNum2;
    else return "";
}

function updateResourcePositionNew(resourceId, pair) {
    var templateData = _.findWhere(getAllRows(), {id: resourceId});
    if (isExpand) {
        $(".airplane-wrapper > div").filter(function () {
            return $(this).data('o').flightDynamicId === resourceId;
        }).each(commonGanttElementRemoveFn);

        var ganttData = generateGanttWrapper(pair, 1), ganttOpts = $(".gantt").data('options');
        ganttOpts.data = _(ganttOpts.data).reject((data) => data.id === ganttData.id);
        ganttData.gate = !_.isEmpty(pair.placeNum) ? pair.placeNum.split(",") : [];
        $.fn.gantt("append", ganttData, ganttOpts);
    }
    sortDateR(datagrid);
    layoutAutomaticallyAdapter();
}

//计算配对航班中预飞或计飞时小于当前时间时（即航班延误没有发送延误后的预飞时间），调整甘特图的结束占用时间
function pairTimeoutGanttChange() {
    if (isExpand) {
        _.each(_.filter(getAllRows(), (pairEntity) => pairEntity.flightDynimicId && pairEntity.flightDynimicId2 && !pairEntity.atd2 && pairEntity.status2 != 'CNL' &&
            moment().isAfter(moment(pairEntity.etd2 || pairEntity.departurePlanTime2)) && pairEntity.placeNum),
            (pairEntity) => {
                var aircraftOverDate = pairEntity.atd2 || pairEntity.etd2 || pairEntity.departurePlanTime2;
                if (asyncRenderTaskCompleted && moment().isAfter(moment(aircraftOverDate))) { //如果结束时间小于当前时间，则结束时间设为当前时间
                    aircraftOverDate = moment().format("YYYY-MM-DD HH:mm");
                    $(".airplane-wrapper > div").filter(function () {
                        return $(this).data('o').flightDynamicId === pairEntity.id;
                    }).each(commonGanttElementRemoveFn);

                    //console.log('延误后甘特图的结束占用时间(' + pairEntity.flightCompanyCode2 + pairEntity.flightNum2 + ')');
                    var aircraftStartDate = pairEntity.ata || pairEntity.eta || pairEntity.arrivalPlanTime;
                    var ganttData = generateGanttWrapper(pairEntity, 1), ganttOpts = $(".gantt").data('options');
                    ganttOpts.data = _(ganttOpts.data).reject((data) => data.id === ganttData.id);

                    ganttData['aircraftStartDate'] = formatDate(aircraftStartDate);
                    ganttData['aircraftOverDate'] = formatDate(aircraftOverDate);
                    ganttData['occupiedStart'] = aircraftStartDate;
                    ganttData['occupiedEnd'] = aircraftOverDate;
                    ganttData['startDate'] = formatDate(aircraftStartDate);
                    ganttData['startTime'] = moment(aircraftStartDate).format("HH:mm");
                    ganttData['endDate'] = formatDate(aircraftOverDate);
                    ganttData['endTime'] = moment(aircraftOverDate).format("HH:mm");
                    $.fn.gantt("append", ganttData, ganttOpts);
                    //console.log(pairEntity.placeNum,aircraftStartDate,aircraftOverDate);
                }
            });
        //console.log('------------------------');
    }
}