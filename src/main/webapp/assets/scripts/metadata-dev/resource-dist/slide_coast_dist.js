const TPL_NAME = 'slide-coast', moduleContext = 'slideCoast', inoutTypeCode = 'C', moduleName = '滑槽';
var xAxis = enhanceXAxis(), yAxis = $utils.ajaxLoadFragments(ctx + '/rms/slideCoast/listJson');

var gateGanttOptions = {
        auto: true,
        xAxis: xAxis,
        yAxis: yAxis,
        draggableAxis: 'y',
        collisionThresholds: getThresholdValue(),
        timeline: true,
        resizableHandler: false,
        ajaxUrl: ctx + '/rms/rs/disted_slide_coast_Json',
        ajaxParams: {startDate: _.first(xAxis), overDate: _.last(xAxis)},
        asyncRenderCallback: asyncGanttElementRenderTaskFn,
        move2HoldAreaCallback: function (oData) {
            if (oData.flightDynamicId) { // 用户手动拖动到「预留区」
                if (origin_drag_target[0].text === '预留区') return;
                // 根据origin_drag_target找到其原先的位置
                var originAxis = origin_drag_target[0], selectedRow = enhanceGetDGSelectedRow(oData.id), tipMessage;
                tipMessage = `您确定要清除「${selectedRow.flightCompanyCode + selectedRow.flightDynamicCode}」航班的「${originAxis.text}」号行李转盘吗?`;

                layer.confirm(tipMessage, function (index) {
                    checkingIndex = distributedWaitingDialog();
                    var params = {};
                    params.type = selectedRow.inteCarouselCode === originAxis.text ? "1" : "2"
                    params.flightDynamicId = selectedRow.flightDynamicId;

                    $.post(ctx + '/rms/rs/clearSlideCoastByNature', $.extend({}, params, extraData), function (data) {
                        layer.close(checkingIndex);
                        if (data.code == 1) {
                            layer.msg(data.message);
                            updateResourcePosition(selectedRow.id, params.type === "1" ? {inteCarouselCode: ""} : {intlCarouselCode: ""});
                        } else if (data.code == -3) {
                            layer.alert(data.message);
                            layer.close(index);
                            updateResourcePosition(selectedRow.id, {
                                inteCarouselCode: selectedRow.inteCarouselCode,
                                intlCarouselCode: selectedRow.intlCarouselCode
                            })
                        }
                    });
                }, () => normalDragStopFn(origin_drag_elem));
            } else clearCallbackFn();
        },
        draggableStartCallback: normalModeGanttDraggableBeforeHandler,
        promptHandler: function (oData, event) {  // 弹出提示层
            commonGanttTips(TPL_NAME, multiModeGanttTipsArgsGenerator(oData), event);
        },
        dblClick: function (event) {
            commonSetActualTimeHandler(TPL_NAME, getFormatTitle(enhanceGetDGSelectedRow(event.data.flightDynamicId, "flightDynamicId")), '滑槽 - 实际占用时间设置', '/rms/rs/setSlideCoastActualTime');
        },
        leftAxisClickHandler: function () {
            if (!datagrid.datagrid('getSelected') && !draggable_holder) {
                layer.msg('请选择一条记录再进行操作！');
                return;
            }

            if ($(this).attr("_available") === '0') {
                layer.msg('当前滑槽「' + $(this).text() + '」不可用，无法分配！');
                return false;
            }
            dist_slide_coast_manually($(this).text());
        }
    }, datagrid, draggable_holder, dataGridMeta, origin_drag_target, origin_drag_elem,
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
            handler: () => commonDistributedResourceAuto('/rms/rs/slide_coast_dist_auto', moduleName)
        });

    if (flagManualAllocation) {
        toolbars.push({
            text: '手动分配', iconCls: 'icon-bullet_wrench', handler: function () {
                var selectedRow = datagrid.datagrid('getSelected');
                if (selectedRow) {
                    if (multiModeGanttDistributedAvailableChecking(moduleName, '/rms/rs/getSlideCoastOccupyingTime?flightDynamicId=' + selectedRow.flightDynamicId, selectedRow)) {
                        if (selectedRow.flightDynamicNature !== '3') {

                            var availableInte = $utils.ajaxLoadFragments(ctx + '/rms/slideCoast/list_json_by_condition?nature=1'),
                                availableIntl = $utils.ajaxLoadFragments(ctx + '/rms/slideCoast/list_json_by_condition?nature=2'),
                                inte = !selectedRow.inteCarouselCode ? [] : _.without($utils.superTrim(selectedRow.inteCarouselCode).split(","), ""),
                                intl = !selectedRow.intlCarouselCode ? [] : _.without($utils.superTrim(selectedRow.intlCarouselCode).split(","), "");

                            availableInte = _.union(availableInte, inte);
                            availableIntl = _.union(availableIntl, intl);

                            layer.open({
                                title: '手动分配',
                                btn: ['确定', '取消'],
                                area: ['370px', '150px'],
                                type: 1,
                                scrollbar: false,
                                move: false,
                                content: fetchResourceDistTpl(TPL_NAME, {
                                    inte, intl, availableInte, availableIntl,
                                    fightDynamicId: selectedRow.flightDynamicId, nature: selectedRow.flightDynamicNature
                                }),
                                success: function (layero, index) {
                                    var select2Opts = {allowClear: true};
                                    $("#cbInteCarouselCode").select2($.extend({}, {placeholder: '请选择国内航段滑槽'}, select2Opts));
                                    $("#cbIntlCarouselCode").select2($.extend({}, {placeholder: '请选择国际航段滑槽'}, select2Opts));

                                    $("#cbInteCarouselCode").val(inte).trigger('change');
                                    $("#cbIntlCarouselCode").val(intl).trigger('change');
                                },
                                yes: function (index, layero) {
                                    var oldCarouselCode = {
                                        inte: selectedRow.inteCarouselCode,
                                        intl: selectedRow.intlCarouselCode
                                    };
                                    var cintec = $("#cbInteCarouselCode").val(),
                                        cintlc = $("#cbIntlCarouselCode").val();

                                    if (_.isEmpty(cintec) && _.isEmpty(cintlc)) {
                                        if (!selectedRow.inteCarouselCode && !selectedRow.intlCarouselCode) return;
                                        else clearCallbackFn();
                                        return;
                                    }

                                    if (cintec === oldCarouselCode.inte && cintlc === oldCarouselCode.intl) return;
                                    checkingIndex = distributedWaitingDialog();

                                    var pArgs = {
                                        flightDynamicId: selectedRow.flightDynamicId,
                                        inte: cintec,
                                        intl: cintlc,
                                        nature: !_.isEmpty(cintec) ? "1" : "2",
                                        force: false
                                    };
                                    var gArgs = {
                                        selectedRowId: selectedRow.id,
                                        nInte: cintec,
                                        nIntl: cintlc,
                                        oInte: oldCarouselCode.inte,
                                        oIntl: oldCarouselCode.intl,
                                        nature: !_.isEmpty(cintec) ? "1" : "2",
                                        force: true
                                    };
                                    commonManuallyDistributionFn(ctx + '/rms/rs/slide_coast_manual_dist', ctx + '/rms/rs/slide_coast_manual_dist', pArgs, gArgs, checkingIndex)
                                    return true;
                                }
                            });
                        } else dist_slide_coast_manually();
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
                if (selectedRow) commonSetActualTimeHandler(TPL_NAME, getFormatTitle(selectedRow), '滑槽 - 实际占用时间设置', '/rms/rs/setSlideCoastActualTime')
                else layer.msg("请选中一条记录再进行操作！");
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
        url: ctx + "/rms/rs/slide-coast-list",
        queryParams: {inoutTypeCode: inoutTypeCode},
        method: 'get',
        idField: "id",
        singleSelect: true,
        showFooter: true,
        multiSort: false,
        toolbar: toolbars,
        rowStyler: commonRowStyler,
        onLoadSuccess: layoutAutomaticallyAdapter,
        onClickCell: fetchVipInfos
    });

    datagrid.datagrid('enableFilter');
    datagrid.datagrid('doCellTip', cellTooltipCommonProps);
});


//移入hold区回调函数
function clearCallbackFn() {
    var selectedRow = datagrid.datagrid('getSelected');
    if (selectedRow) {
        if (selectedRow.inteCarouselCode || selectedRow.intlCarouselCode) {
            layer.confirm(`是否要清除当前航班「${selectedRow.flightCompanyCode + selectedRow.flightDynamicCode}」分配的滑槽？`, {
                icon: 3,
                title: '提示'
            }, function (index) {
                $.post(ctx + '/rms/rs/clearSlideCoastCode', $.extend({}, {flightDynamicId: selectedRow.flightDynamicId}, extraData), function (data) {
                    if (data.code == 1) {
                        layer.msg(data.message);
                        updateResourcePosition(selectedRow.id, {inteCarouselCode: "", intlCarouselCode: ""});
                    } else if (data.code == -3) {
                        layer.alert(data.message);
                        layer.close(index);
                        updateResourcePosition(selectedRow.id, {
                            inteCarouselCode: selectedRow.inteCarouselCode,
                            intlCarouselCode: selectedRow.intlCarouselCode
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

        var carouselCode = oData.flightDynamicNature === "1" ? oData.inteCarouselCode : oData.flightDynamicNature === "2" ? oData.intlCarouselCode :
            _.isEmpty(oData.inteCarouselCode) ? oData.intlCarouselCode : oData.inteCarouselCode;
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


var _socket_uuid = $uuid(), extraData = {random: _socket_uuid}, socket = io.connect(message_socket_server_url, {
    query: "ns=/global/dynamics&uuid=" + _socket_uuid
}), entityMapping = {inteSlideCoastCode: "inteCarouselCode", intlSlideCoastCode: "intlCarouselCode"};

socket.on("modification", function (data) {
    try {
        if ($('#maintabs', window.parent.document).find('.tabs-selected').text() != document.title) return;
    } catch (e) {
    }

    if (data.uuid === "" || data.uuid === "null" || !data.uuid) return false;
    _.defer(function (data) {
        let _entity = $.parseJSON(data.data), _changeList = $.parseJSON(data.changeList), _identify = data.identify,
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
            _identify !== 'SlideCoastOccupyingInfo') return;

        if (_identify === 'SlideCoastOccupyingInfo') _compareId = _entity.flightDynamicId;
        [filtered, filteredRowIndex, filteredRowData, rowIndex, rowData] = getRowIndex("flightDynamicId", _compareId);

        if (_identify === "FlightDynamic" && monitorType === "INSERT") {
            if (_entity.inoutTypeCode === inoutTypeCode) {
                _entity.flightPlan = "";
                $.post(ctx + "/rms/rs/getSlideCoastOccupyingTimeByDynamic", _entity).then(function (data) {
                    var oci = {
                        flightDynamicId: _entity.id, flightDynamicNature: data.nature,
                        expectedStartTime: _formatDateTime(data.start), expectedOverTime: _formatDateTime(data.over),
                        flightDynamicCode: _entity.flightNum
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
                _entity.flightPlan = "";
                if (!filtered)
                    datagrid.datagrid('deleteRow', rowIndex);
                else {
                    if (rowIndex !== undefined && rowIndex !== -1) datagrid.datagrid('deleteRow', rowIndex);
                    datagrid.data('datagrid').filterSource.rows.splice(filteredRowIndex, 1);
                    if(filteredRowIndex>-1) datagrid.datagrid('doFilter');
                }

                if (isExpand) {
                    $(".airplane-wrapper > div").filter(function () {
                        return $(this).data('o').flightDynamicId === _compareId
                    }).each(commonGanttElementRemoveFn).remove();

                    ganttOpts.data = _(ganttOpts.data).reject((data) => data.flightDynamicId === _compareId);
                }
                sortDateC(datagrid);
                layoutAutomaticallyAdapter();
            }
        } else if (monitorType === "UPDATE") {
            if ((!filtered && rowIndex !== undefined && rowIndex != -1) || (filtered && filteredRowIndex != -1)) {
                var changeWrapper = getChangeWrapper(_changeList, entityMapping);
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

                var $ganttEl = $(".airplane-wrapper > div").filter(function () {
                    return $(this).data('o').flightDynamicId === _compareId;
                });

                if (isExpand) {
                    // 清除原来的资源
                    $ganttEl.each(commonGanttElementRemoveFn).remove();
                    // 清除原来的Gantt Data
                    ganttOpts.data = ganttOpts.data.filter(_filterData => _filterData.flightDynamicId !== _compareId);

                    var templateData = _.findWhere(getAllRows(), {flightDynamicId: _compareId});
                    templateData.expectedNum = templateData.expectedCarouselNum;
                    var mockGanttData = newTempalteWrapper(templateData, templateData);

                    var inteCodes = $utils.superTrim(templateData.inteCarouselCode, ""),
                        intlCodes = $utils.superTrim(templateData.intlCarouselCode, "");

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
                        if (!filtered) datagrid.datagrid('deleteRow', rowIndex);
                        else {
                            if (rowIndex !== undefined && rowIndex !== -1) datagrid.datagrid('deleteRow', rowIndex);
                            datagrid.data('datagrid').filterSource.rows.splice(filteredRowIndex, 1);
                        }
                        $(".airplane-wrapper > div").filter(function () {
                            return $(this).data('o').flightDynamicId === dynamicId;
                        }).each(commonGanttElementRemoveFn).remove();
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
                    url: ctx + '/rms/slideCoastOccupyingInfo/fetchByFlightDynamicId',
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
                        expectedCarouselNum: oci.expectedSlideCoastNum,
                        inteCarouselCode: oci.inteSlideCoastCode,
                        intlCarouselCode: oci.intlSlideCoastCode
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
            flightDynamicCode: dynamic.flightNum, expectedCarouselNum: oci.expectedSlideCoastNum,
            inteCarouselCode: oci.inteSlideCoastCode, intlCarouselCode: oci.intlSlideCoastCode
        }));
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

    //_inOutType = inoutTypeCode;
    //_loopCounter = 0;
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


function ganttReload() {
    $(".gantt").gantt('reload', ctx + '/rms/rs/disted_slide_coast_Json', {
        startDate: _.first(xAxis),
        overDate: _.last(xAxis)
    });
}

function dist_slide_coast_manually(_aircraft_num) {
    var selectedRow = datagrid.datagrid('getSelected');
    if (!selectedRow) {
        if (draggable_holder) selectedRow = enhanceGetDGSelectedRow(draggable_holder.id)
        else return false;
    }

    var settings = $(".gantt").data("options"), oldCarouselCode, checkingIndex;
    if (!selectedRow) return false;

    if (!multiModeGanttDistributedAvailableChecking(moduleName, '/rms/rs/getSlideCoastOccupyingTime?flightDynamicId=' + selectedRow.flightDynamicId, selectedRow)) return;

    if (!_aircraft_num) {

        var availableInte = $utils.ajaxLoadFragments(ctx + '/rms/slideCoast/list_json_by_condition?nature=1'),
            availableIntl = $utils.ajaxLoadFragments(ctx + '/rms/slideCoast/list_json_by_condition?nature=2'),
            inte = !selectedRow.inteCarouselCode ? [] : _.without($utils.superTrim(selectedRow.inteCarouselCode).split(","), ""),
            intl = !selectedRow.intlCarouselCode ? [] : _.without($utils.superTrim(selectedRow.intlCarouselCode).split(","), "");

        availableInte = _.union(availableInte, inte);
        availableIntl = _.union(availableIntl, intl);

        var confirmLayerIndex = layer.open({
            title: '滑槽手动分配',
            btn: ['确定', '取消'],
            area: ['370px', selectedRow.flightDynamicNature === '3' ? '199px' : '150px'],
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
                $("#cbInteCarouselCode").select2($.extend({}, {placeholder: '请选择国内航段滑槽'}, select2Opts));
                $("#cbIntlCarouselCode").select2($.extend({}, {placeholder: '请选择国际航段滑槽'}, select2Opts));

                $("#cbInteCarouselCode").val(inte).trigger('change');
                $("#cbIntlCarouselCode").val(intl).trigger('change');
            },
            yes: function (index, layero) {
                oldCarouselCode = {inte: selectedRow.inteCarouselCode, intl: selectedRow.intlCarouselCode};
                var cintec = $("#cbInteCarouselCode").val(), cintlc = $("#cbIntlCarouselCode").val();
                if (cintec === oldCarouselCode.inte && cintlc === oldCarouselCode.intl) return false;
                checkingIndex = distributedWaitingDialog();

                var pArgs = {
                    flightDynamicId: selectedRow.flightDynamicId,
                    inteSlideCoastCode: cintec,
                    intlSlideCoastCode: cintlc,
                    nature: selectedRow.flightDynamicNature,
                    force: false
                };
                var gArgs = {
                    selectedRowId: selectedRow.id,
                    nInte: cintec,
                    nIntl: cintlc,
                    oInte: oldCarouselCode.inte,
                    oIntl: oldCarouselCode.intl,
                    nature: selectedRow.flightDynamicNature,
                    force: true
                };
                commonManuallyDistributionFn(ctx + '/rms/rs/slide_coast_manual_dist', ctx + '/rms/rs/slide_coast_manual_dist', pArgs, gArgs, checkingIndex, confirmLayerIndex)
                return true;
            }
        });
    } else {
        var intes = $utils.superTrim(selectedRow.inteCarouselCode),  //国内航段
            intls = $utils.superTrim(selectedRow.intlCarouselCode),  //国际航段
            ganttOpts = $(".gantt").data('options'), yAxis = ganttOpts.yAxis,
            flightNum = selectedRow.flightCompanyCode + selectedRow.flightDynamicCode;

        if (draggable_holder) {  // 用户拖动Gantt上的元素
            var originAxis = origin_drag_target[0], newAxis = draggable_holder.gate[0],
                newAxisExists = newAxis.nature === "1" ? intes === newAxis.text : intls === newAxis.text, params = {};

            if (originAxis.text === newAxis.text) {
                clearManuallyGanttRefs();
                return;
            }

            if (!newAxisExists) {  // 移动到inte及intl都不存在的数值上
                let layerPromptMsgTpl = `您确定将「${newAxis.text}」号滑槽分配给「${flightNum}」航班吗?`;
                if (selectedRow.flightDynamicNature != '3' && selectedRow.flightDynamicNature !== newAxis.nature)
                    layerPromptMsgTpl = `当前航班「${flightNum}」属性为${flightNatures[selectedRow.flightDynamicNature]}，${newAxis.text}为${newAxis.nature === '2' ? '国际航段' : '国内航段'}资源，是否强制分配？`;

                layer.confirm(layerPromptMsgTpl, function (index) {
                    params.flightDynamicId = selectedRow.flightDynamicId;
                    checkingIndex = distributedWaitingDialog();

                    // --- 保持用户拖动axis的性质来进行分配 -- changed by xiaowu 2017.1.24
                    params.inte = $utils.superTrim(selectedRow.inteCarouselCode);
                    params.intl = $utils.superTrim(selectedRow.intlCarouselCode);
                    var inte_array = _.without(params.inte.split(","), ""),
                        intl_array = _.without(params.intl.split(","), "")

                    if (_.contains(inte_array, originAxis.text)) {
                        params.inte = [..._.without(inte_array, originAxis.text), ...[newAxis.text]].join(",")
                    } else if (_.contains(intl_array, originAxis.text)) {
                        params.intl = [..._.without(intl_array, originAxis.text), ...[newAxis.text]].join(",")
                    } else {
                        if (selectedRow.flightDynamicNature === '1' || (selectedRow.flightDynamicNature === '3' && newAxis.nature === '1')) {
                            params.inte = newAxis.text;
                            params.intl = $utils.superTrim(selectedRow.intlCarouselCode);
                        } else if (selectedRow.flightDynamicNature === '2' || (selectedRow.flightDynamicNature === '3' && newAxis.nature === '2')) {
                            params.inte = $utils.superTrim(selectedRow.inteCarouselCode);
                            params.intl = newAxis.text;
                        }
                    }

                    params.nature = selectedRow.flightDynamicNature;
                    params.force = false;

                    var gArgs = {
                        selectedRowId: selectedRow.id,
                        nInte: params.inte,
                        nIntl: params.intl,
                        oInte: selectedRow.inteCarouselCode,
                        oIntl: selectedRow.intlCarouselCode,
                        nature: selectedRow.flightDynamicNature,
                        force: true
                    };
                    commonManuallyDistributionFn(ctx + '/rms/rs/slide_coast_manual_dist', ctx + '/rms/rs/slide_coast_manual_dist', params, gArgs, checkingIndex);

                }, () => normalDragStopFn(origin_drag_elem));
            } else normalDragStopFn(origin_drag_elem);
        } else {
            let clickedAxis = yAxis.filter(filterAxis => filterAxis.text === _aircraft_num)[0];
            let nature = selectedRow.flightDynamicNature === '3' ? clickedAxis.nature : selectedRow.flightDynamicNature;
            let tipMessage;
            if (intes === _aircraft_num || intls === _aircraft_num) { // 清除操作
                tipMessage = `您确定要清除「${flightNum}」航班的「${_aircraft_num}」号滑槽吗?`;

                layer.confirm(tipMessage, function (index) {
                    checkingIndex = distributedWaitingDialog();

                    $.post(ctx + '/rms/rs/clearSlideCoastByNature', $.extend({}, {
                        flightDynamicId: selectedRow.flightDynamicId,
                        type: nature
                    }, extraData), function (data) {
                        layer.close(checkingIndex);
                        if (data.code == 1) {
                            layer.msg(data.message);
                            updateResourcePosition(selectedRow.id, nature === "1" ? {inteCarouselCode: _aircraft_num} : {intlCarouselCode: _aircraft_num});
                        } else if (data.code == -3) {
                            layer.alert(data.message);
                            layer.close(index);
                            updateResourcePosition(selectedRow.id, {
                                inteCarouselCode: selectedRow.inteCarouselCode,
                                intlCarouselCode: selectedRow.intlCarouselCode
                            })
                        }
                    });
                }, clearManuallyGanttRefs);
            } else { // 追加操作
                tipMessage = `您确定要将编号为「${_aircraft_num}」滑槽分配给航班「${flightNum}」吗?`;
                if (selectedRow.flightDynamicNature != '3' && selectedRow.flightDynamicNature !== clickedAxis.nature)
                    tipMessage = `当前航班「${flightNum}」属性为${flightNatures[selectedRow.flightDynamicNature]}，${clickedAxis.text}为${flightNatures[clickedAxis.nature]}资源，是否强制分配？`;
                layer.confirm(tipMessage, function (index) {
                    var oldCarouselCode = {inte: intes, intl: intls}, expectSlideCoastCode = _aircraft_num,
                        cintec = nature === "1" ? _aircraft_num : intes,
                        cintlc = nature === "2" ? _aircraft_num : intls;
                    checkingIndex = distributedWaitingDialog();

                    var pArgs = {
                        flightDynamicId: selectedRow.flightDynamicId,
                        nature: nature,
                        inte: cintec,
                        intl: cintlc,
                        force: false
                    }
                    var gArgs = {
                        selectedRowId: selectedRow.id,
                        nInte: cintec,
                        nIntl: cintlc,
                        oInte: oldCarouselCode.inte,
                        oIntl: oldCarouselCode.intl,
                        nature: nature,
                        force: true
                    };
                    commonManuallyDistributionFn(ctx + '/rms/rs/slide_coast_manual_dist', ctx + '/rms/rs/slide_coast_manual_dist', pArgs, gArgs, checkingIndex)
                }, clearManuallyGanttRefs);
            }
        }
    }
}

function updateResourcePosition(resourceId, definition) {
    // datagrid.datagrid('updateRow', { index: datagrid.datagrid('getRowIndex', resourceId), row: definition });
    enhanceUpdateDGRow("id", resourceId, definition);
    var templateData = _.findWhere(getAllRows(), {id: resourceId}),
        resourceNum = $utils.superTrim(templateData.inteCarouselCode) + "," + $utils.superTrim(templateData.intlCarouselCode);
    if (isExpand) {
        var ganttOpts = $(".gantt").data('options'), yAxis = ganttOpts.yAxis;
        $(".airplane-wrapper > div").filter(function () {
            return $(this).data('o').flightDynamicId === templateData.flightDynamicId;
        }).each(commonGanttElementRemoveFn).remove();

        ganttOpts.data = ganttOpts.data.filter(filterData => filterData.flightDynamicId !== templateData.flightDynamicId);
        var generateGanttData = newTempalteWrapper(templateData, templateData);
        if (resourceNum === ",") {
            //清除资源
            $.fn.gantt('append', $.extend(generateGanttData, {
                extra: {codeNature: templateData.flightDynamicNature},
                gate: [],
                inteCode: [],
                intlCode: []
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
    }
    clearManuallyGanttRefs();
    sortDateC(datagrid);
    layoutAutomaticallyAdapter();
}

function commonManuallyDistributionFn(url, forceDistUrl, pArgs, gArgs, checkingIndex, confirmIndex) {
    $.post(url, $.extend({}, pArgs, extraData), function (data) {
        layer.close(checkingIndex);
        if (data.code == 1) {
            layer.closeAll();
            layer.msg(data.message);
            updateResourcePosition(gArgs.selectedRowId, {inteCarouselCode: gArgs.nInte, intlCarouselCode: gArgs.nIntl});
            clearManuallyGanttRefs();
            if (confirmIndex) layer.close(confirmIndex);
        } else {
            if (data.code == -2) {
                layer.alert(data.message)
                return;
            }
            if (data.code == -3) {
                updateResourcePosition(gArgs.selectedRowId, {
                    inteCarouselCode: gArgs.oInte,
                    intlCarouselCode: gArgs.oIntl
                })
                layer.close(layer.index - 1);
                layer.alert(data.message)
                return;
            }
            layer.confirm(data.message + "<br/>是否要强制分配？", function (index) {
                $.post(forceDistUrl, $.extend({}, {
                    flightDynamicId: pArgs.flightDynamicId,
                    inte: gArgs.nInte,
                    intl: gArgs.nIntl,
                    nature: gArgs.nature,
                    force: gArgs.force
                }, extraData), function (data) {
                    if (data.code == 1) {
                        layer.closeAll();
                        updateResourcePosition(gArgs.selectedRowId, {
                            inteCarouselCode: gArgs.nInte,
                            intlCarouselCode: gArgs.nIntl
                        });
                        clearManuallyGanttRefs();
                    } else if (data.code == -3) {
                        layer.close(index);
                        layer.alert(data.message);
                        updateResourcePosition(gArgs.selectedRowId, {
                            inteCarouselCode: gArgs.oInte,
                            intlCarouselCode: gArgs.oIntl
                        }) // 提交审核后还原
                        return;
                    } else {
                        layer.close(index);
                        layer.alert('未知错误！' + data.message);
                        return;
                    }
                });
            }, function () {
                layer.closeAll();
                updateResourcePosition(gArgs.selectedRowId, {
                    inteCarouselCode: gArgs.oInte,
                    intlCarouselCode: gArgs.oIntl
                });
                clearManuallyGanttRefs();
            });
        }
    });
}