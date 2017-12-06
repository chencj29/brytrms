const TPL_NAME = 'departure-hall', moduleContext = 'departureHall', moduleName = '候机厅', inoutTypeCode = 'C';
var xAxis = enhanceXAxis(), yAxis = $utils.ajaxLoadFragments(ctx + '/rms/departureHall/listJson');

var gateGanttOptions = {
        auto: true,
        xAxis: xAxis,
        yAxis: yAxis,
        multi: true,
        draggableAxis: 'y',
        collisionThresholds: 0,
        timeline: true,
        ajaxUrl: ctx + '/rms/rs/disted_departure_hall_Json',
        ajaxParams: {startDate: _.first(xAxis), overDate: _.last(xAxis)},
        collisionDetectionHandler: multiModeCollisionHandler,
        draggableHandler: multiModeGanttDraggableHandler,
        resizableHandler: false,
        asyncRenderCallback: asyncGanttElementRenderTaskFn,
        move2HoldAreaCallback: function (oData) {
            if (oData.flightDynamicId) { // 用户手动拖动到「预留区」
                // 根据origin_drag_target找到其原先的位置
                var originAxis = origin_drag_target[0], selectedRow = enhanceGetDGSelectedRow(oData.id),
                    intes = _.without($utils.superTrim(selectedRow.inteDepartureHallCode).split(","), ""),
                    intls = _.without($utils.superTrim(selectedRow.intlDepartureHallCode).split(","), ""), tipMessage;

                tipMessage = `您确定要清除「${selectedRow.flightCompanyCode + selectedRow.flightDynamicCode}」航班的「${originAxis.text}」号候机厅吗?`;

                layer.confirm(tipMessage, function (index) {
                    checkingIndex = distributedWaitingDialog();

                    if (intes.includes(originAxis.text)) {
                        selectedRow.type = 'inte'
                        selectedRow.opCode = filterValues(intes, originAxis.text)
                    } else {
                        selectedRow.type = 'intl'
                        selectedRow.opCode = filterValues(intls, originAxis.text)
                    }

                    if (selectedRow.opCode.indexOf(",") === 0) selectedRow.opCode = selectedRow.opCode.slice(1);

                    $.post(ctx + '/rms/rs/departureHall_manual_checking', $.extend({}, selectedRow, extraData), function (data) {
                        layer.close(checkingIndex);
                        if (data.code == 1) {
                            layer.msg(data.message);
                            updateResourcePosition(selectedRow.id, selectedRow.type = 'inte' ? {inteDepartureHallCode: selectedRow.opCode} : {intlDepartureHallCode: selectedRow.opCode});
                        } else if (data.code == -3) {
                            layer.close(index);
                            layer.alert(data.message);
                            updateResourcePosition(selectedRow.id, selectedRow.type = 'inte' ? {inteDepartureHallCode: selectedRow.inteDepartureHallCode} : {intlDepartureHallCode: selectedRow.intlDepartureHallCode});
                        } else layer.alert(data.message);
                    });
                }, () => multiModeDragStopFn(origin_drag_elem));
            } else clearCallbackFn();
        },
        draggableStartCallback: multiModeGanttDraggableBeforeHandler,
        promptHandler: function (oData, event) {
            commonGanttTips(TPL_NAME, multiModeGanttTipsArgsGenerator(oData), event);
        },
        dblClick: function (event) {
            commonSetActualTimeHandler(TPL_NAME, getFormatTitle(enhanceGetDGSelectedRow(event.data.flightDynamicId, "flightDynamicId")), '候机厅 - 实际占用时间设置', '/rms/rs/setDepartureHallActualTime');
        },
        leftAxisClickHandler: function () {
            if (!datagrid.datagrid('getSelected') && !draggable_holder) {
                layer.msg('请选择一条记录再进行操作！');
                return;
            }

            if ($(this).attr("_available") === '0') {
                layer.msg('当前候机厅「' + $(this).text() + '」不可用，无法分配！');
                return false;
            }
            dist_departure_hall_manually($(this).text());
        }
    }, datagrid, draggable_holder, origin_drag_target, origin_drag_elem, dataGridMeta,
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
            handler: () => commonDistributedResourceAuto('/rms/rs/departure_hall_dist_auto', moduleName)
        });

    if (flagManualAllocation) {
        toolbars.push({
            text: '手动分配', iconCls: 'icon-bullet_wrench', handler: function () {
                var selectedRow = datagrid.datagrid('getSelected');
                if (selectedRow) {
                    if (multiModeGanttDistributedAvailableChecking(moduleName, '/rms/rs/getDepartureHallOccupyingTime?flightDynamicId=' + selectedRow.flightDynamicId, selectedRow)) {
                        if (selectedRow.flightDynamicNature !== '3') {

                            var availableInte = $utils.ajaxLoadFragments(ctx + '/rms/departureHall/list_json_by_condition?nature=1'),
                                availableIntl = $utils.ajaxLoadFragments(ctx + '/rms/departureHall/list_json_by_condition?nature=2'),
                                inte = !selectedRow.inteDepartureHallCode ? [] : _.without($utils.superTrim(selectedRow.inteDepartureHallCode).split(","), ""),
                                intl = !selectedRow.intlDepartureHallCode ? [] : _.without($utils.superTrim(selectedRow.intlDepartureHallCode).split(","), "");

                            availableInte = _.union(availableInte, inte);
                            availableIntl = _.union(availableIntl, intl);


                            layer.open({
                                title: '手动分配',
                                btn: ['确定', '取消'],
                                area: ['500px', '150px'],
                                type: 1,
                                scrollbar: false,
                                move: false,
                                content: fetchResourceDistTpl(TPL_NAME, {
                                    inte, intl, availableInte, availableIntl,
                                    fightDynamicId: selectedRow.flightDynamicId, nature: selectedRow.flightDynamicNature
                                }),
                                success: function (layero, index) {
                                    var select2Opts = {allowClear: true, closeOnSelect: false};
                                    $("#cbInteCarouselCode").select2($.extend({}, {placeholder: '请选择国内航段候机厅'}, select2Opts));
                                    $("#cbIntlCarouselCode").select2($.extend({}, {placeholder: '请选择国际航段候机厅'}, select2Opts));

                                    $("#cbInteCarouselCode").val(inte).trigger('change');
                                    $("#cbIntlCarouselCode").val(intl).trigger('change');
                                },
                                yes: function (index, layero) {
                                    var intes = $("#cbInteCarouselCode").val(), intls = $("#cbIntlCarouselCode").val();
                                    if (!intes && !intls) { //如果两者皆为空
                                        if (!selectedRow.inteDepartureHallCode && !selectedRow.intlDepartureHallCode) return;
                                        else clearCallbackFn();
                                    } else {
                                        var checkingIndex = distributedWaitingDialog();
                                        selectedRow.type = _.isEmpty(intls) ? "inte" : "intl";
                                        selectedRow.opCode = _.isEmpty(intls) ? intes.join(",") : intls.join(",");

                                        if (selectedRow.opCode.indexOf(",") === 0) selectedRow.opCode = selectedRow.opCode.slice(1);

                                        $.post(ctx + '/rms/rs/departureHall_manual_checking', $.extend({}, selectedRow, extraData), function (data) {
                                            layer.close(checkingIndex);
                                            if (data.code == 1) {
                                                layer.msg(data.message);
                                                updateResourcePosition(selectedRow.id, _.isEmpty(intls) ? {inteDepartureHallCode: selectedRow.opCode} : {intlDepartureHallCode: selectedRow.opCode});
                                            } else if (data.code == -3) {
                                                layer.close(index);
                                                layer.alert(data.message);
                                                updateResourcePosition(selectedRow.id, _.isEmpty(intls) ? {inteDepartureHallCode: selectedRow.inteDepartureHallCode} : {intlDepartureHallCode: selectedRow.intlDepartureHallCode});
                                            } else layer.alert(data.message);
                                        });
                                    }
                                    layer.close(index);
                                }
                            });
                        } else dist_departure_hall_manually();
                    }
                } else layer.msg('请选择一条记录再进行操作!');
            }
        });
    }

    //添加机位赶甘特图铵钮；
    if (flagFlightGant) toolbars.push({text: '甘特图', iconCls: 'icon-zoom', handler: showGraphicPanel});

    //添加实际占用时间录入铵钮；
    if (flagEndFlightOccupation) {
        toolbars.push({
            text: '实际占用时间录入', iconCls: 'icon-stop', handler: function () {
                var selectedRow = datagrid.datagrid('getSelected');
                if (selectedRow) commonSetActualTimeHandler(TPL_NAME, getFormatTitle(selectedRow), '候机厅 - 实际占用时间设置', '/rms/rs/setDepartureHallActualTime')
                else layer.msg("请选中一条记录再进行操作！")
            }
        })
    }


    //添加取消机位分配铵钮；
    if (flagCancelAllocation) toolbars.push({
        text: '取消分配', iconCls: 'icon-cross', handler: clearCallbackFn
    });

    //添加双击事件；显示甘特图  单击vip格中的“有”显示vip详情
    if (flagFlightGant) $("#datagrid").datagrid({onDblClickRow: scroll2CallbackFn});

    toolbars.push({text: '颜色设置', iconCls: 'icon-color', handler: colorSettingHandler}, dataGridViewSettingToolbar, {
        text: '刷新数据', iconCls: 'icon-arrow_refresh_small', handler: commonDataRefreshHandler
    });

    datagrid = $("#datagrid").datagrid({
        rownumbers: true,
        idField: "id",
        singleSelect: true,
        showFooter: true,
        multiSort: false,
        url: ctx + "/rms/rs/departure-hall-list",
        queryParams: {inoutTypeCode: inoutTypeCode},
        method: 'get',
        rowStyler: commonRowStyler,
        toolbar: toolbars,
        onLoadSuccess: layoutAutomaticallyAdapter,
        onClickCell: fetchVipInfos,
        remoteSort: false   //本地排序
    });

    datagrid.datagrid('enableFilter');
    datagrid.datagrid('doCellTip', cellTooltipCommonProps);
});

//移入hold区回调函数
function clearCallbackFn() {
    var selectedRow = datagrid.datagrid('getSelected');
    if (selectedRow) {
        if (selectedRow.inteDepartureHallCode || selectedRow.intlDepartureHallCode) {
            layer.confirm('是否要清除当前航班分配的候机厅？', {icon: 3, title: '提示'}, function (index) {
                $.post(ctx + '/rms/rs/clearDepartureHall', $.extend({}, {flightDynamicId: selectedRow.flightDynamicId}, extraData), function (data) {
                    if (data.code == 1) {
                        layer.msg(data.message);
                        updateResourcePosition(selectedRow.id, {
                            inteDepartureHallCode: "",
                            intlDepartureHallCode: "",
                            inteActualStartTime: "",
                            inteActualOverTime: ""
                        });
                    } else if (data.code == -3) {
                        layer.alert(data.message);
                        layer.close(index);
                        updateResourcePosition(selectedRow.id, {
                            inteDepartureHallCode: selectedRow.inteDepartureHallCode,
                            intlDepartureHallCode: selectedRow.intlDepartureHallCode
                        })
                    } else layer.alert(data.message);
                });
            });
        }
    } else layer.msg("请选中一条记录再进行操作！");
}

function scroll2CallbackFn() {
    var oData = datagrid.datagrid("getSelected");
    if (oData) {
        if (_.isEmpty(oData.expectedOverTime) && _.isEmpty(oData.expectedStartTime)) return;
        // 如果没有展开面板就选展开
        if (!isExpand) showGraphicPanel();

        $(".gantt-content-scroller-wrapper").gantt("scroll2Now", $(".gantt").data('options'), moment(oData.expectedStartTime).toDate());

        try {
            var top = $(".airplane-wrapper > div").filter(function () {
                return $(this).data('o').flightDynamicId === oData.flightDynamicId;
            }).position().top;
        } catch (e) {
        }


        var carouselCode = oData.flightDynamicNature === "1" ? oData.inteDepartureHallCode : oData.flightDynamicNature === "2" ? oData.intlDepartureHallCode :
            _.isEmpty(oData.inteDepartureHallCode) ? oData.intlDepartureHallCode : oData.inteDepartureHallCode;
        if (carouselCode) $(".gantt-content-scroller-wrapper").scrollTop(top);
        else $(".gantt-content-scroller-wrapper").scrollTop(0);

        $(".airplane-wrapper > div").filter(function () {
            return $(this).data('o').id === oData.id;
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
    $(".gantt").gantt('reload', ctx + '/rms/rs/disted_departure_hall_Json', {
        startDate: _.first(xAxis),
        overDate: _.last(xAxis)
    });
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
        let _entity = $.parseJSON(data.data),
            _changeList = $.parseJSON(data.changeList),
            _identify = data.identify,
            _compareId = _entity.id,
            rowIndex = -1, rowData, monitorType = data.monitorType, ganttOpts = $(".gantt").data("options"),
            filtered,           // 是否过滤(标识)
            filteredRowIndex,   // 过滤后的下标
            filteredRowData;    // 过滤后的数据


        //过滤COBT消息，不用更新
        if (!_.isEmpty(_changeList) && _changeList[0]["fieldDesc"] == "COBT") {
            return;
        }

        // 权限判断
        if (!_.contains(currentUserPrivileges, ALL_PRIVILEGE) && !_.contains(currentUserPrivileges, _entity.flightCompanyCode) &&
            !_.contains(currentServicePrivileges, ALL_PRIVILEGE) && !_.contains(currentServicePrivileges, _entity.agentCode) &&
            _identify !== 'DepartureHallOccupyingInfo') return;

        if (_identify === 'DepartureHallOccupyingInfo') _compareId = _entity.flightDynamicId;
        [filtered, filteredRowIndex, filteredRowData, rowIndex, rowData] = getRowIndex("flightDynamicId", _compareId);

        if (_identify === "FlightDynamic" && monitorType === "INSERT") {
            if (_entity.inoutTypeCode === inoutTypeCode) {
                _entity.flightPlan = "";
                $.post(ctx + "/rms/rs/getDepartureHallOccupyingTimeByDynamic", _entity).then(function (data) {
                    var oci = {
                        flightDynamicId: _entity.id, flightDynamicNature: data.nature,
                        expectedStartTime: _formatDateTime(data.start), expectedOverTime: _formatDateTime(data.over),
                        flightDynamicCode: _entity.flightNum, expectedDepartureHallNum: data.expectedNum
                    };

                    _entity = $.extend(_entity, oci);
                    var filterData = $.fn.datagrid.defaults.filterMatcher.call(datagrid[0], {
                        total: 1,
                        rows: [_entity]
                    });
                    // 将命中的数据添加到datagrid中
                    for (var matchData of filterData.rows) datagrid.datagrid("appendRow", matchData);
                    // 将未命中的数据添加到filterSource中
                    if (filterData.unMatchedRows.length !== 0) for (var unMatchData of filterData.unMatchedRows) datagrid.datagrid('getData').unMatchedRows.push(unMatchData);

                    if (isExpand) $.fn.gantt('append', newTempalteWrapper(_entity, {}), ganttOpts);
                    sortDateC(datagrid);
                    layoutAutomaticallyAdapter();
                });
            }
        } else if (_identify === "FlightDynamic" && monitorType === "DELETE") {
            if (_entity.inoutTypeCode === inoutTypeCode && ((!filtered && rowIndex !== undefined && rowIndex != -1) || (filtered && filteredRowIndex != -1))) {
                if (!filtered) datagrid.datagrid('deleteRow', rowIndex);
                else {
                    if (rowIndex !== undefined && rowIndex !== -1) datagrid.datagrid('deleteRow', rowIndex);
                    datagrid.data('datagrid').filterSource.rows.splice(filteredRowIndex, 1);
                    if(filteredRowIndex>-1) datagrid.datagrid('doFilter');
                }

                if (isExpand) {
                    $(".airplane-wrapper > div").filter(function () {
                        return $(this).data('o').flightDynamicId === _compareId
                    }).remove();

                    ganttOpts.data = _(ganttOpts.data).reject((data) => data.flightDynamicId === _compareId);
                }
                sortDateC(datagrid);
                layoutAutomaticallyAdapter();
            }
        } else if (monitorType === "UPDATE") {

            if ((!filtered && rowIndex !== undefined && rowIndex != -1) || (filtered && filteredRowIndex != -1)) {
                var changeWrapper = getChangeWrapper(_changeList);
                //计算航航属性
                changeWrapper.flightDynamicNature = getNature(changeWrapper.flightAttrCode, _entity.flightDynamicNature);

                // 更新表格中的数据
                if (!filtered) datagrid.datagrid("updateRow", {index: rowIndex, row: changeWrapper});
                else {
                    if (rowIndex !== undefined && rowIndex !== -1) datagrid.datagrid('updateRow', {
                        index: rowIndex,
                        row: changeWrapper
                    });
                    datagrid.data('datagrid').filterSource.rows[filteredRowIndex] = $.extend(datagrid.data('datagrid').filterSource.rows[filteredRowIndex], changeWrapper);
                }

                refreshRowBGColor(changeWrapper, rowIndex);

                if (isExpand) {
                    var $ganttEl = $(".airplane-wrapper > div").filter(function () {
                        return $(this).data('o').flightDynamicId === _compareId;
                    });

                    // 清除原来的资源
                    $ganttEl.remove();
                    // 清除原来的Gantt Data
                    ganttOpts.data = ganttOpts.data.filter(_filterData => _filterData.flightDynamicId !== _compareId);

                    var templateData = _.findWhere(getAllRows(), {flightDynamicId: _compareId});
                    templateData.expectedNum = templateData.expectedDepartureHallNum;
                    var mockGanttData = newTempalteWrapper(templateData, templateData);

                    var inteCodes = $utils.superTrim(templateData.inteDepartureHallCode, ""),
                        intlCodes = $utils.superTrim(templateData.intlDepartureHallCode, "");

                    if (_.isEmpty(inteCodes) && _.isEmpty(intlCodes)) { // 清除
                        $.fn.gantt('append', $.extend(mockGanttData, {
                            extra: {codeNature: rowData.flightDynamicNature},
                            gate: [],
                            inteCode: [],
                            intlCode: []
                        }), ganttOpts);
                    } else {
                        for (var inteCode of _.without(inteCodes.split(","), "")) {
                            mockGanttData.extra = {codeNature: "1"};
                            mockGanttData.gate = [inteCode];
                            mockGanttData.inteCode = inteCodes;
                            $.fn.gantt('append', mockGanttData, ganttOpts);
                        }

                        for (var intlCode of _.without(intlCodes.split(","), "")) {
                            mockGanttData.extra = {codeNature: "2"};
                            mockGanttData.gate = [intlCode];
                            mockGanttData.intlCode = intlCodes;
                            $.fn.gantt('append', mockGanttData, ganttOpts);
                        }
                    }
                }
                //判断包含有的字段才排序
                if (_.intersection(_.keys(changeWrapper), _.without(sortRules.C,"planDate")).length > 0) sortDateC(datagrid);
                layoutAutomaticallyAdapter();
            }
        }
    }, data);
}).on('flightDynamicChange2History', function (data) {
    try {
        if ($('#maintabs', window.parent.document).find('.tabs-selected').text() != document.title) return;
    } catch (e) {
    }

    xAxis = enhanceXAxis();
    if (!data || _.isEmpty(data)) return;
    let rowIndex = -1,      // 未过滤的下标
        rowData,            // 未过滤的数据
        filtered,           // 是否过滤(标识)
        filteredRowIndex,   // 过滤后的下标
        filteredRowData;    // 过滤后的数据

    _.defer(function (data) {
        try {
            for (var _key of _(data).keys()) {
                if (_key.indexOf("flightDynamic") != -1) {
                    let valuesArray = _key.split("#"), mType = valuesArray[0], dynamicId = valuesArray[1],
                        dynamicObject = JSON.parse(_.propertyOf(data)(_key));
                    [filtered, filteredRowIndex, filteredRowData, rowIndex, rowData] = getRowIndex("flightDynamicId", dynamicId);
                    if ((!filtered && rowIndex !== undefined && rowIndex != -1) || (filtered && filteredRowIndex !== -1)) {
                        // 这里要分过滤及非过滤两种情况的处理  此处是非过滤的情况
                        if (!filtered)
                            datagrid.datagrid('deleteRow', rowIndex);
                        else {
                            if (rowIndex !== undefined && rowIndex !== -1) datagrid.datagrid('deleteRow', rowIndex);
                            datagrid.data('datagrid').filterSource.rows.splice(filteredRowIndex, 1);
                        }

                        $(".airplane-wrapper > div").filter(function () {
                            return $(this).data('o').flightDynamicId === dynamicId;
                        }).remove();
                        if ($(".gantt").data('options')) $(".gantt").data('options').data = _($(".gantt").data('options').data).reject(data => data.flightDynamicId === dynamicId);
                    }
                }
            }
            sortDateC(datagrid);
            layoutAutomaticallyAdapter();
        } catch (e) {
            console.log(e);
        }
    }, data);
}).on('flightDynamicTime2Change', function (data) {
    try {
        if ($('#maintabs', window.parent.document).find('.tabs-selected').text() != document.title) return;
    } catch (e) {
    }

    if (!data) return;
    if (isExpand) {
        _.defer(function (data) {
            var data = $.parseJSON(data);
            var dynamic = data.dynamic;
            if (dynamic.inoutTypeCode === inoutTypeCode) {
                dynamic.flightPlan = '';
                $.ajax({
                    url: ctx + '/rms/departureHallOccupyingInfo/fetchByFlightDynamicId',
                    data: {flightDynamicId: dynamic.id},
                    async: false,
                    dataType: 'json'
                }).then(function (oci) {
                    dynamic = $.extend({}, dynamic, {
                        flightDynamicId: dynamic.id,
                        flightDynamicNature: oci.flightDynamicNature,
                        id: oci.id,
                        expectedStartTime: _formatDateTime(oci.expectedStartTime),
                        expectedOverTime: _formatDateTime(oci.expectedOverTime),
                        flightDynamicCode: dynamic.flightNum,
                        expectedDepartureHallNum: oci.expectedDepartureHallNum,
                        inteDepartureHallCode: oci.inteDepartureHallCode,
                        intlDepartureHallCode: oci.intlDepartureHallCode
                    });
                });
                updateResourcePosition(dynamic.id, dynamic);
            }
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
    if (data.dynamics.length === 0) return resourceInitCallbackFn({
        restResourceArray: resp.restResourceArray,
        data: data,
        type: moduleName,
        success: true
    });
    var dynamics = data.dynamics.filter(dynamic => dynamic.inoutTypeCode === inoutTypeCode),
        ociDatas = data.oci[moduleName];
    if (_.isEmpty(ociDatas)) return resourceInitCallbackFn({
        restResourceArray: resp.restResourceArray,
        data: data,
        type: moduleName,
        success: true
    });

    let wrappers = [];

    _.each(dynamics, (dynamic) => {
        var oci = _(ociDatas).find(condition => condition.flightDynamicId === dynamic.id);
        wrappers.push(_.extend({}, dynamic, {
            flightDynamicId: dynamic.id, flightDynamicNature: oci.flightDynamicNature, id: oci.id,
            expectedStartTime: oci.expectedStartTime, expectedOverTime: oci.expectedOverTime,
            flightDynamicCode: dynamic.flightNum, expectedDepartureHallNum: oci.expectedDepartureHallNum,
            inteDepartureHallCode: oci.inteDepartureHallCode, intlDepartureHallCode: oci.intlDepartureHallCode
        }))

    });

    if (filtered) datagrid.data('datagrid').filterSource.rows = datagrid.data('datagrid').filterSource.rows.filter(row => formatDate(row.planDate) != planDate);
    // 找出当前数据源中不等于planDate的数据
    let unMatchedDatas = datagrid.datagrid('getData').rows.filter(row => formatDate(row.planDate) != planDate),
        // 判断用户权限进行渲染
        privilegesDynamics = (_.contains(currentUserPrivileges, ALL_PRIVILEGE) || _.contains(currentServicePrivileges, ALL_PRIVILEGE)) ? wrappers :
            wrappers.filter((pairWrapper) => _.contains(currentUserPrivileges, pairWrapper.flightCompanyCode) || _.contains(currentServicePrivileges, _entity.agentCode)),
        //privilegesDynamics = _.contains(currentUserPrivileges, ALL_PRIVILEGE) ? wrappers : wrappers.filter((dynamic) => _.contains(currentUserPrivileges, dynamic.flightCompanyCode)),
        finalRows = _.union(unMatchedDatas, privilegesDynamics);
    if (filtered) finalRows = _.union(finalRows, datagrid.data('datagrid').filterSource.rows);

    _inOutType = inoutTypeCode;
    _loopCounter = 0;
    finalRows = mysort(finalRows,inoutTypeCode);
    datagrid.datagrid('loadData', {total: finalRows.length, rows: finalRows});

    if (isExpand) {
        var planDateMt = moment(planDate), currentMaxPlanDateMt = moment(currentMaxPlanDate),
            isAfter = planDateMt.isAfter(currentMaxPlanDate);
        if (isAfter) xAxis = [planDateMt.add(-1, "day").format("YYYY-MM-DD"), planDateMt.format("YYYY-MM-DD"), planDateMt.add(1, "day").format("YYYY-MM-DD")];
        if (_.contains(xAxis, planDate) || isAfter) ganttReload();
    }

    if (resourceInitCallbackFn) resourceInitCallbackFn({
        restResourceArray: resp.restResourceArray,
        data: data,
        type: moduleName,
        success: true
    });
}

function dist_departure_hall_manually(_aircraft_num) {
    var selectedRow = datagrid.datagrid('getSelected');
    if (!selectedRow) {
        if (draggable_holder) selectedRow = enhanceGetDGSelectedRow(draggable_holder.id)
        else return false;
    }

    var settings = $(".gantt").data("options"), oldCarouselCode, checkingIndex;
    if (!selectedRow) return false;
    if (!multiModeGanttDistributedAvailableChecking(moduleName, '/rms/rs/getDepartureHallOccupyingTime?flightDynamicId=' + selectedRow.flightDynamicId, selectedRow)) return;

    if (!_aircraft_num) { // 混合航班，弹出界面分配

        var availableInte = $utils.ajaxLoadFragments(ctx + '/rms/departureHall/list_json_by_condition?nature=1'),
            availableIntl = $utils.ajaxLoadFragments(ctx + '/rms/departureHall/list_json_by_condition?nature=2'),
            inte = !selectedRow.inteDepartureHallCode ? [] : _.without($utils.superTrim(selectedRow.inteDepartureHallCode).split(","), ""),
            intl = !selectedRow.intlDepartureHallCode ? [] : _.without($utils.superTrim(selectedRow.intlDepartureHallCode).split(","), "");

        availableInte = _.union(availableInte, inte);
        availableIntl = _.union(availableIntl, intl);

        var confirmLayerIndex = layer.open({
            title: '候机厅手动分配',
            btn: ['确定', '取消'],
            area: ['500px', selectedRow.flightDynamicNature === '3' ? '199px' : '150px'],
            type: 1,
            scrollbar: false,
            move: false,
            content: fetchResourceDistTpl(TPL_NAME, {
                flightDynamicId: selectedRow.flightDynamicId,
                id: selectedRow.id,
                nature: selectedRow.flightDynamicNature,
                inte,
                intl,
                availableInte,
                availableIntl
            }),
            success: function (layero, index) {
                var select2Opts = {allowClear: true};
                $("#cbInteCarouselCode").select2($.extend({}, {placeholder: '请选择国内航段候机厅'}, select2Opts));
                $("#cbIntlCarouselCode").select2($.extend({}, {placeholder: '请选择国际航段候机厅'}, select2Opts));

                $("#cbInteCarouselCode").val(inte).trigger('change');
                $("#cbIntlCarouselCode").val(intl).trigger('change');

            },
            yes: function (index, layero) {
                oldCarouselCode = {inte: selectedRow.inteDepartureHallCode, intl: selectedRow.intlDepartureHallCode};
                var cintec = $("#cbInteCarouselCode").val(), cintlc = $("#cbIntlCarouselCode").val();
                if (cintec === oldCarouselCode.inte && cintlc === oldCarouselCode.intl) return false;
                selectedRow.actualDepartureHallNum = (cintec ? cintec.length : 0) + (cintlc ? cintlc.length : 0);
                selectedRow.inteDepartureHallCode = cintec ? cintec.join(",") : "";
                selectedRow.intlDepartureHallCode = cintlc ? cintlc.join(",") : "";

                //format date
                selectedRow.expectedStartTime = _formatDateTime(selectedRow.expectedStartTime);
                selectedRow.expectedOverTime = _formatDateTime(selectedRow.expectedOverTime);
                checkingIndex = distributedWaitingDialog();
                $.post(ctx + '/rms/rs/departureHall_manual_checking_4_mixing', $.extend({}, selectedRow, extraData), function (data) {
                    layer.close(checkingIndex);
                    layer.close(confirmLayerIndex);
                    if (data.code == 1) {
                        layer.msg(data.message);
                        updateResourcePosition(selectedRow.id, {
                            inteDepartureHallCode: selectedRow.inteDepartureHallCode,
                            intlDepartureHallCode: selectedRow.intlDepartureHallCode,
                        });
                    } else if (data.code == -3) {
                        layer.close(index);
                        layer.alert(data.message);
                        updateResourcePosition(selectedRow.id, {
                            inteDepartureHallCode: oldCarouselCode.inte,
                            intlDepartureHallCode: oldCarouselCode.intl
                        });
                    } else layer.alert(data.message);
                });
                return true;
            }
        });
    } else {

        // 如果draggable_holder没有值, 表示用户是直接选中表格中的内容, 再点击左侧的资源编号面板进行分配
        // 得到当前航班的「国内航段」及「国际航段」的资源, 根据当前点击的资源判断其类型, 得到编号, 如果编号存在, 则将当前操作视为「清除当前资源」。反之视为「新增当前资源」
        // draggable没有值的情况,用户选中的航班点击「预留区」则清除所有分配的资源


        // 如果draggable_holder有值, 表示用户是直接拖动Gantt Element。 直接拖动的处理方式为: 将拖动前的值替换为拖动后的值,
        // 假设当前 「国内航段 = 1, 3, 5」, 「国际航段 = 2, 4」, 用户将编号为5的资源拖动到第「6 - 国际航段」, 此时将5清除, 国际航段变为 「2,4,6」

        // 拖动到「预留区」,清除当前拖动的资源编号
        // 拖动到已有的资源上暂时不作处理

        // 得到当前航班的inte及intl值
        var intes = _.without($utils.superTrim(selectedRow.inteDepartureHallCode).split(","), ""),  //国内航段
            intls = _.without($utils.superTrim(selectedRow.intlDepartureHallCode).split(","), ""),  //国际航段
            ganttOpts = $(".gantt").data('options'), yAxis = ganttOpts.yAxis,
            flightNum = selectedRow.flightCompanyCode + selectedRow.flightDynamicCode;


        if (draggable_holder) {  // 用户拖动Gantt上的元素
            var originAxis = origin_drag_target[0], newAxis = draggable_holder.gate[0], newAxisExists = false;
            if (selectedRow.flightDynamicNature !== "3")
                newAxisExists = selectedRow.flightDynamicNature === "1" ? intes.includes(newAxis.text) : intls.includes(newAxis.text);
            else {
                if (intes.includes(originAxis.text)) newAxisExists = intls.includes(newAxis.text)
                else newAxisExists = intes.includes(newAxis.text);
            }

            if (originAxis.text === newAxis.text) {
                clearManuallyGanttRefs();
                return;
            }

            if (!newAxisExists) {  // 移动到inte及intl都不存在的数值上

                let layerPromptMsgTpl = `您确定将「${newAxis.text}」号候机厅分配给「${flightNum}」航班吗?`;
                if (selectedRow.flightDynamicNature != '3' && selectedRow.flightDynamicNature !== newAxis.nature)
                    layerPromptMsgTpl = `当前航班「${flightNum}」属性为${flightNatures[selectedRow.flightDynamicNature]}，${newAxis.text}为${flightNatures[newAxis.nature]}资源，是否强制分配？`;


                layer.confirm(layerPromptMsgTpl, function (index) {
                    checkingIndex = distributedWaitingDialog();

                    // --- 保持用户拖动axis的性质来进行分配 -- changed by xiaowu 2017.1.24
                    selectedRow.inteDepartureHallCode = $utils.superTrim(selectedRow.inteDepartureHallCode);
                    selectedRow.intlDepartureHallCode = $utils.superTrim(selectedRow.intlDepartureHallCode);
                    var inte_array = _.without(selectedRow.inteDepartureHallCode.split(","), ""),
                        intl_array = _.without(selectedRow.intlDepartureHallCode.split(","), "");

                    if (_.contains(inte_array, originAxis.text)) {
                        selectedRow.inteDepartureHallCode = [..._.without(inte_array, originAxis.text), ...[newAxis.text]].join(",")
                    } else if (_.contains(intl_array, originAxis.text)) {
                        selectedRow.intlDepartureHallCode = [..._.without(intl_array, originAxis.text), ...[newAxis.text]].join(",")
                    } else {
                        if (selectedRow.flightDynamicNature === '1' || (selectedRow.flightDynamicNature === '3' && newAxis.nature === '1')) {
                            selectedRow.inteDepartureHallCode = $utils.superTrim(filterValues(intes, originAxis.text)) + "," + newAxis.text;
                            selectedRow.intlDepartureHallCode = $utils.superTrim(selectedRow.intlDepartureHallCode);
                        } else if (selectedRow.flightDynamicNature === '2' || (selectedRow.flightDynamicNature === '3' && newAxis.nature === '2')) {
                            selectedRow.inteDepartureHallCode = $utils.superTrim(selectedRow.inteDepartureHallCode);
                            selectedRow.intlDepartureHallCode = $utils.superTrim(filterValues(intls, originAxis.text)) + "," + newAxis.text;
                        }
                    }

                    //清除首尾多余的逗号
                    selectedRow.inteDepartureHallCode = emptyFirstAndLastCommas(selectedRow.inteDepartureHallCode);
                    selectedRow.intlDepartureHallCode = emptyFirstAndLastCommas(selectedRow.intlDepartureHallCode);

                    $.post(ctx + '/rms/rs/departureHall_manual_checking_4_mixing', $.extend({}, selectedRow, extraData), function (data) {
                        layer.close(checkingIndex);
                        if (data.code == 1) {
                            layer.msg(data.message);
                            updateResourcePosition(selectedRow.id, {
                                inteDepartureHallCode: selectedRow.inteDepartureHallCode,
                                intlDepartureHallCode: selectedRow.intlDepartureHallCode
                            });
                        } else if (data.code == -3) {
                            layer.close(index);
                            layer.alert(data.message);
                            updateResourcePosition(selectedRow.id, {
                                inteDepartureHallCode: inte_array.join(","),
                                intlDepartureHallCode: intl_array.join(",")
                            });
                        } else layer.alert(data.message);
                    })
                }, () => multiModeDragStopFn(origin_drag_elem));
            } else multiModeDragStopFn(origin_drag_elem);
        } else { // 用户点击左侧的资源面板
            let clickedAxis = yAxis.filter(filterAxis => filterAxis.text === _aircraft_num)[0];
            let nature = selectedRow.flightDynamicNature === '3' ? clickedAxis.nature : selectedRow.flightDynamicNature;
            let values, tipMessage;
            if (intes.includes(_aircraft_num) || intls.includes(_aircraft_num)) { // 清除操作
                values = nature === "1" ? filterValues(intes, _aircraft_num) : filterValues(intls, _aircraft_num);
                tipMessage = `您确定要清除「${flightNum}」航班的「${_aircraft_num}」号候机厅吗?`;
            } else { // 追加操作
                if (nature === "1") {
                    intes.push(_aircraft_num);
                    values = intes;
                } else {
                    intls.push(_aircraft_num);
                    values = intls;
                }
                tipMessage = `您确定要将编号为「${_aircraft_num}」候机厅分配给航班「${flightNum}」吗?`
                if (selectedRow.flightDynamicNature !== 3 && (selectedRow.flightDynamicNature !== clickedAxis.nature))
                    tipMessage = `当前航班「${flightNum}」属性为${flightNatures[selectedRow.flightDynamicNature]}，${clickedAxis.text}为${flightNatures[clickedAxis.nature]}资源，是否强制分配？`;
            }

            layer.confirm(tipMessage, function (index) {
                checkingIndex = distributedWaitingDialog();
                selectedRow.type = nature === "1" ? "inte" : "intl";
                selectedRow.opCode = values.join(",");
                if (selectedRow.opCode.indexOf(",") === 0) selectedRow.opCode = selectedRow.opCode.slice(1);

                $.post(ctx + '/rms/rs/departureHall_manual_checking', $.extend({}, selectedRow, extraData), function (data) {
                    layer.close(checkingIndex);
                    if (data.code == 1) {
                        layer.msg(data.message);
                        updateResourcePosition(selectedRow.id, nature === "1" ? {inteDepartureHallCode: selectedRow.opCode} : {intlDepartureHallCode: selectedRow.opCode});
                    } else if (data.code == -3) {
                        layer.alert(data.message);
                        layer.close(index);
                        updateResourcePosition(selectedRow.id, nature === "1" ? {inteDepartureHallCode: selectedRow.inteDepartureHallCode} : {intlDepartureHallCode: selectedRow.intlDepartureHallCode});
                    } else layer.alert(data.message);
                });
            })
        }
    }
}

function updateResourcePosition(resourceId, definition) {
    enhanceUpdateDGRow("id", resourceId, definition);
    var templateData = _.findWhere(getAllRows(), {id: resourceId}),
        resourceNum = $utils.superTrim(templateData.inteDepartureHallCode) + "," + $utils.superTrim(templateData.intlDepartureHallCode);
    if (isExpand) {
        var ganttOpts = $(".gantt").data('options'), yAxis = ganttOpts.yAxis;
        $(".airplane-wrapper > div").filter(function () {
            return $(this).data('o').flightDynamicId === templateData.flightDynamicId;
        }).remove();

        ganttOpts.data = ganttOpts.data.filter(filterData => filterData.flightDynamicId !== templateData.flightDynamicId);
        var generateGanttData = newTempalteWrapper(templateData, templateData);
        if (resourceNum === ",") {
            //清除资源
            $.fn.gantt('append', $.extend(generateGanttData, {
                extra: {codeNature: templateData.flightDynamicNature}, gate: [], inteCode: [], intlCode: []
            }), ganttOpts);
        } else {
            // 根据resourceNum来重新绘制Gantt Element
            for (var resourceCode of resourceNum.split(",")) {
                if (_.isEmpty(resourceCode)) continue;
                generateGanttData.extra = {codeNature: yAxis.filter(axis => axis.text === resourceCode)[0].nature};
                generateGanttData.gate = [resourceCode];
                $.fn.gantt('append', generateGanttData, ganttOpts);
            }
        }
        removeEmptySubRows(ganttOpts); // 清空empty sub rows
    }
    clearManuallyGanttRefs();
    sortDateC(datagrid);
    layoutAutomaticallyAdapter();
}