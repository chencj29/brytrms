// =================== 通用的一些变量 ================================//
var isExpand = false, // Gantt图面板是否展开
    flightNatures = {"1": "国内航班", "2": "国际航班", "3": "混合航班"}, // 航班属性
    resourceNatures = {"1": "国内航段", "2": "国际航段", "3": "国内航段/国际航段"},
    threshold = getThresholdValue(), cellTooltipCommonProps = {
        onlyShowInterrupt: true, position: 'bottom', maxWidth: '200px',
        tipStyler: {'backgroundColor': '#fff000', borderColor: '#ff0000', boxShadow: '1px 1px 3px #292929'}
    }, asyncRenderTaskCompleted = true, ganttRenderTaskCompleted = true;

var currentUserPrivileges = fetchUserCompanyPrivileges(), currentServicePrivileges = fetchServicePrivileges();
const ALL_PRIVILEGE = 'all-privileges';

var localcolors = localStorage.getItem("colors");
if(!localcolors || localcolors =="{}"){
    localStorage.setItem('colors', JSON.stringify({"DEP":"#a79d9d","COM":"#89eee0","CNL":"#eee193","ARR0":"#e84cdb"}));
}

//===================formatter 通用方法================================//

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

/**
 * 处理航班日期
 * @param v 值
 * @param r 行
 * @param i 索引
 * @param f 字段名
 * @returns {string} DD日 HH:mm
 */
function dateFmt(v, r, i, f) {
    return !v ? "" : moment(v).format("DD日 HH:mm");
}

/**
 * 一般的日期转换方法
 * @param v 值
 * @param r 行
 * @returns {string} yyyy-MM-dd
 */
function formatDate(v, r) {
    return v ? moment(v).format('YYYY-MM-DD') : "";
}

/**
 * 航班性质的转换方法
 * @param v 值
 * @param r 行
 * @param i 号
 * @param f 名
 * @returns {*} 国内、国际、混合
 */
function natureFmt(v, r, i, f) {
    return _.propertyOf(flightNatures)(v);
}

function resourceTipFmt(v, r, i, f) {
    return _.propertyOf(resourceNatures)(v);
}

/**
 * 综合航班号转换方法
 * @param v 值
 * @param r 行
 * @param i 号
 * @param f 名
 * @returns {*} 综合航班号
 */
function vitrualFlightNumberFmt(v, r, i, f) {
    return r.flightCompanyCode + r.flightDynamicCode;
}

/**
 * 是否转换
 * @param v 值
 * @param r 行
 * @param i 号
 * @param f 名
 * @returns {string} 0 否 1 是
 */
function yesNoFmt(v, r, i, f) {
    return v === "0" ? "否" : "是";
}

//===================Gantt图 通用方法================================//

/**
 * 多行模式Gantt图的碰撞检测方法
 * @param event 事件对象
 * @param ui 源目标DOM对象(jQuery)
 */
function multiModeCollisionHandler(event, ui) {
    let _this = this,
        o = ui.helper.data('o'),
        gate = $.fn.gantt('getGate', o, ui.helper.offset().top, _this);
    if (!o || !gate || !overlaps) return;
    let gate_text = gate[0].text;
    if (gate_text !== "预留区") {
        // 获取当前yAxis的所有subs
        let $yAxis = $(".gantt-left-panel > div[_code=" + gate_text + "]"),
            yAxisTop = $yAxis.offset().top,
            $outerRow = $("div[_rowval=" + gate_text + "]"),
            subs = !$yAxis.data('sub') ? 1 : $yAxis.data('sub');

        let done = false;

        for (var i = !ui.helper.data('base_step') ? 1 : ui.helper.data('base_step'); i <= subs; i++) {
            var $elements = $(".airplane-wrapper > div").filter(function () {
                return $(this).data('uid') !== ui.helper.data('uid') && $(this).data('o').gate[0].text === gate_text && $(this).data('sub') === i;
            })

            var collisionResultsArray = $elements.map(function () {
                return overlaps(ui.helper, $(this))
            }).get();

            if (!_(collisionResultsArray).contains(true)) { // 如果没有发现冲突，根据subs计算高度
                // ui.helper.offset({ top: ui.helper.offset().top + (i - 1) * _this.stepHeight });
                ui.helper.data('sub', i);
                done = true;
                break;
            } else {
                if (i !== subs) ui.helper.offset({top: ui.helper.offset().top + _this.stepHeight});
            }
        }

        if (!done) { // 如果都遍历完未发现适当的位置，则将当前的yAxis新增一个
            var nsub = subs + 1;
            var final_height = ($yAxis.height() + 1 + _this.stepHeight) + 'px';
            // 增加高度
            $yAxis.css({'height': final_height, 'line-height': final_height}).data('sub', nsub);
            $outerRow.css({'height': final_height, 'line-height': final_height}).data('step', nsub);

            ui.helper.data('sub', i);

            // 将后面的elements全部下移
            $(".airplane-wrapper > div").filter(function () {
                return $(this).offset().top > ui.helper.offset().top
            }).each(function () {
                $(this).offset({top: $(this).offset().top += _this.stepHeight});
            });

            // 移动当前element
            ui.helper.offset({top: ui.helper.offset().top + _this.stepHeight});
        }
    }
}

/**
 * 运输调试计划 - 保障过程图 - 碰撞检测
 * @param event
 * @param ui
 */
function progressCollisionDetectionHandler(event, ui) {
    var _this = this,
        o = ui.helper.data('o');
    if (!o) return false;
    var _identify = o.flightDynamicId,
        $el = $('#' + _identify),
        gate = $.fn.gantt('getGate', o, ui.helper.offset().top, _this);
    var gateText = gate[0].text;
    if (overlaps) {
        // 找到当前y-axis下的所有元素
        $(".airplane-wrapper > div", $el).filter(function () {
            return $(this).data('uid') != ui.helper.data('uid');
        }).each(function () {
            if (overlaps(ui.helper, $(this))) {
                // 利用元素的uid进行关联引用
                var currentCollisionRefs = ui.helper.data('collisionRefs') || [],
                    collisionRefs = $(this).data('collisionRefs') || [];
                if (!_.contains(collisionRefs, ui.helper.data('uid') + "(" + ui.helper.data('text') + ")"))
                    collisionRefs.push(ui.helper.data('uid') + "(" + ui.helper.data('text') + ")");

                var yAxisCell = $(".gantt-left-panel > div[_code=" + gateText + "]", $el),
                    outerRow = $("div[_rowval=" + (gateText) + "]", $el),
                    yAxisCellHeight = parseInt(yAxisCell.css("height").replace('px', '')),
                    finalHeightStr = yAxisCellHeight + _this.stepHeight + "px",
                    currentOffset = $(this).offset(),
                    sub = yAxisCell.data('sub') === undefined ? 1 : yAxisCell.data('sub');

                if (!_.contains(currentCollisionRefs, $(this).data('uid') + "(" + $(this).data('text') + ")")) {
                    currentCollisionRefs.push($(this).data('uid') + "(" + $(this).data('text') + ")");

                    //拿到其height及line-height值
                    yAxisCell.css({'height': finalHeightStr, 'line-height': finalHeightStr}).data('sub', ++sub);
                    outerRow.css({'height': finalHeightStr, 'line-height': finalHeightStr});

                    // 得到offset.top大于当前的元素增加opts.stepHeight
                    $(".airplane-wrapper > div").filter(function () {
                        return $(this).offset().top > ui.helper.offset().top
                    }).each(function () {
                        $(this).offset({top: $(this).offset().top += _this.stepHeight});
                    });
                    ui.helper.offset({top: currentOffset.top + _this.stepHeight});
                } else ui.helper.offset({top: ui.helper.data('o-offset').top});

                yAxisCell.data('sub', parseInt(yAxisCell.css('height').replace('px', '')) / _this.stepHeight);
                ui.helper.data('collisionRefs', currentCollisionRefs);
                $(this).data('collisionRefs', collisionRefs);
            } else {
                var currentCollisionRefs = ui.helper.data('collisionRefs') || [],
                    collisionRefs = $(this).data('collisionRefs') || [];
                if (_.contains(currentCollisionRefs, $(this).data('uid') + "(" + $(this).data('text') + ")")) {
                    ui.helper.data('collisionRefs', _.without(currentCollisionRefs, $(this).data('uid') + "(" + $(this).data('text') + ")"));
                    $(this).data('collisionRefs', _.without(collisionRefs, ui.helper.data('uid') + "(" + ui.helper.data('text') + ")"));
                }
            }
        });
    }
}

function commonAfterRenderCallback(settings, data) {
    var rowIndex = $("td[field=flightDynamicId]").filter(function () {
            return $(this).text() === data.flightDynamicId;
        }).parent().attr('datagrid-row-index'),
        rowData = _.findWhere(datagrid.datagrid('getData').rows, {flightDynamicId: data.flightDynamicId});

    if (!rowData.id || rowData.id !== data.id) {
        datagrid.datagrid('updateRow', {index: rowIndex, row: {id: data.id}});
    }
}

/**
 * 多行模式Gantt图拖拽处理方法
 * @returns {{containment: string, axis: (string|boolean), start: start, drag: drag, stop: stop}}
 */
function multiModeGanttDraggableHandler() {
    var opts = this;
    return {
        containment: ".innerDetail",
        axis: opts.draggableAxis,
        start: function (event, ui) {
            ui.helper.data({'o-gate': ui.helper.data('o').gate, 'o-offset': ui.helper.offset()});
            ui.helper.removeClass("blink");
            if (opts.draggableStartCallback) return opts.draggableStartCallback(event, ui);
        },
        drag: function () {
            if (layer) layer.closeAll('page');
        },
        stop: function (event, ui) {

            // console.log("previous position => ", ui.helper.data('originPos').top, "current position => ", ui.helper.position().top)

            let position = ui.helper.position(), sourcePosition = ui.helper.data('originPos'), _basePositionTop = 0,
                oData = ui.helper.data('o'),
                offset = ui.helper.offset(), sourceOffset = ui.helper.data('o-offset'),
                _baseElementTop = $.fn.gantt("getBaseEl").offset().top,
                targetGate = _.first($.fn.gantt('getGateByPos', oData, position.top, opts)),
                sourceGate = _.first($.fn.gantt('getGateByPos', oData, sourcePosition.top, opts)),
                $targetYAxis = $(".gantt-left-panel > div[_code=" + targetGate.text + "]"),
                $sourceYAxis = $(".gantt-left-panel > div[_code=" + sourceGate.text + "]"),
                step4base = Math.floor((offset.top - _baseElementTop) / opts.stepHeight),
                nstep = Math.floor((offset.top - $targetYAxis.offset().top) / opts.stepHeight) + 1,
                ostep = Math.floor((sourceOffset.top - $sourceYAxis.offset().top) / opts.stepHeight) + 1;
            //
            //
            // console.log("原offset：", sourceOffset, "原位置：", sourceGate.text, "新offset", offset, "新位置：", targetGate.text);
            // console.log("ostep", ostep, "nstep", nstep);
            //
            $(this).animate({'top': "-=" + (offset.top - ((step4base * opts.stepHeight) + _baseElementTop + 2.2))});
            //
            // 拖拽分为冲突及不冲突两种情况
            // 冲突情况：
            // 1. 同一资源下的航班拖拽，造成冲突则认为操作无效，将拖拽的element归置原位。如果未造成冲突，更换element的sub值，并检查其原sub下有无其它的元素，
            // 如果没有，则清除该sub-row, 并将该sub-row offset top 下的航班整体上移。
            // 2. 不同资源下的航班拖拽，首先提醒用户是否进行分配（走既定的分配流程），点击确定后，首先更换资源号，并判断新位置是否冲突，如果冲突，没感觉进行移位计算。
            // 然后判断拖拽的element原位置的sub-row是否还有其它元素，如果没有，则清除原sub-row（sub > 1的情况）。
            // 不冲突情况：
            // 1. 同一资源下的航班拖拽，换位置，如果原sub-row不存在任何元素，则清除原sub-row
            // 2. 不同资源下的航班拖拽，弹出确认框，用户确认后更改航班资源占用信息并移动位置，如果原sub-row不存在任何元素，则清除原sub-row
            //
            //
            _.delay((oData, offset, sourceOffset, nstep, ostep, targetGate, sourceGate, $targetYAxis, $sourceYAxis) => {

                // 检测该位置是否可用
                if ($targetYAxis.attr("_available") != 1) {
                    layer.msg('当前资源不可用！');
                    ui.helper.offset(sourceOffset);
                    multiModeDragStopFn(origin_drag_elem);
                    clearManuallyGanttRefs();
                    return false;
                }

                // 拖动没有改变资源及subs情况，视为无效操作
                if (targetGate.text === sourceGate.text) {
                    if (nstep === ostep) return false;
                    ui.helper.data('base_step', nstep);
                    opts.collisionDetectionHandler(event, ui);
                    // 判断ostep'sub-row有没有其它elements，如果没有则删除
                    var $elements = $(".airplane-wrapper > div").filter(function () {
                        return _.first($(this).data('o').gate).text === targetGate.text && $(this).data('sub') === ostep;
                    });

                    if ($elements.size() === 0) {
                        var sub = $sourceYAxis.data('sub');
                        if (!sub || sub == 1) return false; // 只有一行，无须删除
                        var final_height = (sub - 1) * opts.stepHeight + 'px';
                        $sourceYAxis.css({'height': final_height, 'line-height': final_height}).data('sub', sub - 1);
                        $("div[_rowval=" + targetGate.text + "]").css({
                            'height': final_height,
                            'line-height': final_height
                        });
                        $(".airplane-wrapper > div").filter(function () {
                            return _.first($(this).data('o').gate).text === targetGate.text && $(this).data('sub') > ostep;
                        }).each(function () {
                            $(this).data('sub', $(this).data('sub') - 1);
                        });

                        $(".airplane-wrapper > div").filter(function () {
                            return $(this).offset().top > sourceOffset.top
                        }).each(function () {
                            $(this).offset({top: $(this).offset().top - opts.stepHeight});
                        });
                    }
                } else opts.changeYAxisValue(event, ui);
            }, 500, oData, offset, sourceOffset, nstep, ostep, targetGate, sourceGate, $targetYAxis, $sourceYAxis);
        }
    }
}

/**
 * 通用的多行模式Gantt图开始拖动前处理函数
 */
function multiModeGanttDraggableBeforeHandler(event, ui) {
    origin_drag_target = ui.helper.data('o-gate');
    return normalModeGanttDraggableBeforeHandler(event, ui);

}

/**
 * 正常模式Gantt开始拖动前处理函数
 * @param event
 * @param ui
 */
function normalModeGanttDraggableBeforeHandler(event, ui) {
    if (!checkOperationPermission(ui.helper.data('o').flightCompanyCode, ui.helper.data('o').agentCode)) {
        layer.msg('您没有权限操作此航班！');
        clearManuallyGanttRefs()
        return false;
    }


    // var currentRows = datagrid.datagrid('getRows');
    // if (isFiltered() && !currentRows.find(entity => entity.id === ui.helper.data('o').id)) // 如果用户过滤了数据，且拖动的数据不在当前页面中，则清除数据过滤，还原所有数据。
    //     datagrid.datagrid('removeFilterRule').datagrid('doFilter');
    var index = datagrid.datagrid('getRowIndex', ui.helper.data('o').id);
    if (index !== -1) datagrid.datagrid('selectRecord', ui.helper.data('o').id);
    else datagrid.datagrid('clearSelections');
    draggable_holder = ui.helper.data('o');
    ui.helper.data('originPos', ui.helper.position());
    origin_drag_elem = ui.helper;
    origin_drag_target = ui.helper.data('o').gate;
    return true;
}

/**
 * 通用的Gantt元素点击提示方法
 * @param TPL_NAME 模板名称
 * @param templateArgs 模板参数
 */
function commonGanttTips(TPL_NAME, templateArgs, event) {
    layer.tips(fetchReourceTipTpl(TPL_NAME, templateArgs), $("div[_uid_=" + templateArgs.uid + "]"), {
        tips: [1],
        tipsPosition: true,
        mouse: {left: event.pageX}
    });
}

/**
 * 通用的多行Gantt模板参数生成器
 * @param oData 模板参数
 */
function multiModeGanttTipsArgsGenerator(oData) {
    return {
        uid: oData.uid,
        planDate: oData.planDate,
        flightCompanyCode: oData.flightCompanyCode,
        flightDynamicCode: oData.flightDynamicCode,
        flightDynamicNature: natureFmt(oData.flightDynamicNature),
        codeNatureName: resourceTipFmt(oData.extra.codeNature),
        carouselCode: _.pluck(oData.gate, "text").join(","),
        startDate: oData.startDate + " " + oData.startTime,
        overDate: oData.endDate + " " + oData.endTime
    }
}

/**
 * 通用实际开始时间设置方法
 * @param TPL_NAME 模板名称
 * @param templateArgs 模板参数
 * @param title 标题
 * @param url 更新地址
 */
function commonSetActualTimeHandler(TPL_NAME, templateArgs, title, url, area) {
    layer.open({
        title: title, btn: ['确定', '取消'], area: area || ['400px', '280px'], type: 1, scrollbar: false, move: false,
        content: fetchTimeSettingTpl(TPL_NAME, templateArgs),
        yes: function (index, layero) {
            var inteActualStartTime = $("#actualTimeForm [name=inteActualStartTime]").val();
            var inteActualOverTime = $("#actualTimeForm [name=inteActualOverTime]").val();
            if (!inteActualStartTime && !inteActualOverTime) {
                layer.alert("请输入值！");
                return false;
            }
            $.post(ctx + url, $.extend({}, $("#actualTimeForm").serializeObject(), extraData), function (data) {
                layer.msg(data.message);
                if (data.code == -3) {

                } else if (data.code) {
                    var rowIndex = $("td[field=flightDynamicId]").filter(function () {
                        return $(this).text() === templateArgs.flightDynamicId;
                    }).parent().attr("datagrid-row-index");
                    datagrid.datagrid('updateRow', {
                        index: rowIndex,
                        row: {inteActualStartTime: data.result.start, inteActualOverTime: data.result.over}
                    });
                    // if (isExpand) { //刷新Gantt图
                    //     $(".gantt").data('options').auto = false;
                    //     ganttReload();
                    // }
                }
                layer.close(index);
            });
            return false;
        }
    });
}

/**
 * 多行Gantt图的资源分配检测
 */
function multiModeGanttDistributedAvailableChecking(resourceTypeName, fetchOccupyingTimeUrl, selectedRow) {
    if (selectedRow) {
        if (!selectedRow.expectedStartTime || !selectedRow.expectedOverTime) {
            occupiedTimeMap = $utils.ajaxLoadFragments(ctx + fetchOccupyingTimeUrl);
            if (occupiedTimeMap && occupiedTimeMap.start && occupiedTimeMap.over) {
                selectedRow.expectedStartTime = occupiedTimeMap.start;
                selectedRow.expectedOverTime = occupiedTimeMap.over;
                selectedRow.expectedCheckingCounterNum = occupiedTimeMap.expectedNum;
            }
            if (datagrid.datagrid('getRowIndex', selectedRow) !== -1)
                datagrid.datagrid('updateRow', {
                    index: datagrid.datagrid('getRowIndex', selectedRow),
                    row: selectedRow
                });
        }
        manual_holder = selectedRow;
    } else {
        layer.msg("请选中一条记录再进行操作！");
        return false;
    }

    // 此处，求出时间后（或已经存在时间），判断该航班的性质，如果是国内或国际航班，则直接让用户点击甘特图中的值机柜台编号分配，检查是否占用
    // 如果是混合航班，则弹出对话框，让用户选择国内及国际的航班编号，点击确定之后检测是否占用。
    // 如果没有占用，直接分配，如果已经占用，弹出提示咨询用户是否强制分配。
    // 如果拿不到正确的航班性质数据，弹出对话框无法分配
    if (!selectedRow.flightDynamicNature) {
        layer.msg("无法确定当前航班性质，无法分配！");
        manual_holder = draggable_holder = origin_drag_target = oldCarouselCode = null;
        return false;
    }

    return true;
}

/**
 * 通用的数据刷新方法
 */
function commonDataRefreshHandler() {
    if (!asyncRenderTaskCompleted) {
        layer.msg('数据渲染中，请等待！')
        return;
    }
    var opt = datagrid.datagrid('options');
    if(opt && opt.sortName) opt.sortName = "";
    datagrid.datagrid('reload');
    if (isExpand) ganttReload();
}


/**
 * 资源分配间隔值
 * @returns {*}
 */
function getThresholdValue() {
    var thresholdValueDict = new sysDictUtils('sys', 'threshold_value');
    if (thresholdValueDict && thresholdValueDict.datas) {
        var keys = _.keys(thresholdValueDict.datas);
        if (!_.isEmpty(keys) && !_.isEmpty(keys[0])) {
            return parseInt(keys[0]);
        }
    }
    return 15;
}

/**
 * 显示Gantt图面板, 要求实际页面拥有ganttReload方法, 且此方法不适用于「运输调度计划」
 */
function showGraphicPanel() {
    if (!ganttRenderTaskCompleted) {
        layer.msg('数据渲染中，请等待！')
        return;
    }
    ganttRenderTaskCompleted = false;
    if (!isExpand) $("#main-layout").layout("expand", "south");
    if ($(".gantt").html() == "") $(".gantt").width($(document).width()).gantt(gateGanttOptions);
    if ($(".airplane-wrapper > div").length === 0) $(".gantt").data('options').auto = true;
    ganttReload();
}

//===================资源分配DataGrid 通用方法================================//

/**
 * 通用DataGrid行样式渲染方法
 * @param index 索引
 * @param row 行
 * @returns {string} 返回rgba颜色值
 */
function commonRowStyler(index, row) {
    if (index != -1) {
        var colorsMap = JSON.parse(localStorage.getItem("colors")) || {};
        //颜色优先渲染规则：出港状态，出港返航性质，进港状态，进港性质
        var rtn2 = "RTN" == row.hasOwnProperty("flightNatureCode2") && row["flightNatureCode2"] ? "RTN" : "";
        var rtn = "RTN" == row.hasOwnProperty("flightNatureCode") && row["flightNatureCode"] ? "RTN" : "";
        var status = row.status2 || rtn2 || row.status || rtn;
        if(row.status2 != "DEP" &&  row.status) status = row.status;
        if (status == "DLY" && row.status) status = row.status;
        return !status ? "" : "background-color:" + colorsMap[status];
    }
}

/**
 *  单元格样式渲染方法
 * @param val
 * @param row
 * @returns {*} 返回html或val
 */
var colorsMap = JSON.parse(localStorage.getItem("colors")) || {};

function colorFn(val, row) {
    //colorsMap = JSON.parse(localStorage.getItem("colors")) || {};
    var atdIsBlack = !!(row["atd"] || row["atd2"]);
    if (!val) {
        return val;
    } else {
        if (atdIsBlack && colorsMap["adHoc_placeNum_after"]) {
            return '<span style="color:' + colorsMap["adHoc_placeNum_after"] + ';">' + val + '</span>';
        } else if (colorsMap["adHoc_placeNum_before"]) {
            return '<span style="color:' + colorsMap["adHoc_placeNum_before"] + ';">' + val + '</span>';
        } else {
            return val;
        }
    }
}

//添加jQuery颜色转为Hex
$.fn.getBackgroundColor = function (colorFlag) {
    var rgb = $(this).css('background-color');
    if (colorFlag) rgb = $(this).css('color');
    try {
        if (rgb >= 0) {
            return rgb;//如果是一个hex值则直接返回
        } else {
            rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);

            function hex(x) {
                return ("0" + parseInt(x).toString(16)).slice(-2);
            }

            rgb = "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
        }
    } catch (e) {
    }
    return rgb;
}

//特定业务条件下后通过配置文件指定字段的高亮显示
var adHocList = {
    adHoc_placeNum_before: "机位占用",
    adHoc_placeNum_after: "机位释放后"
};

/**
 * 颜色设置通用处理方法
 */
function colorSettingHandler() {
    $utils.ajaxLoadFragments(ctx + "/ams/flightParameters/listJsons4Type?type=航班状态", function (data) {
        var html = template.compile($utils.ajaxLoadFragments(ctxAssets + '/fragments/sys/gantt/ColorSettingTpl.html'))({
            statusList: data,
            adHocList: adHocList
        });
        layer.open({
            type: 1,
            title: "配置颜色",
            area: ["520px", "520px"],
            content: html,
            btn: ["确定", "取消"],
            success: function () {
                $("#flatClearable").spectrum({
                    flat: true, preferredFormat: "hex", showInput: true,
                    allowEmpty: true, showButtons: false, move: function (color) {
                        if (color) {
                            var col = color.toHexString();
                            var $tr = $("#table-status").find("tr.trSelected");
                            if ($tr.attr("data-status-code") && $tr.attr("data-status-code").indexOf("adHoc_") >= 0) {
                                $tr.css({"color": col});
                            } else {
                                $tr.css({"background-color": col});
                            }
                        }
                    }
                });
                _.each(JSON.parse(localStorage.getItem("colors")), function (val, key) {
                    if (key.indexOf("adHoc_") >= 0) {
                        $("#table-status tr[data-status-code='" + key + "']").css("color", val);
                    } else {
                        $("#table-status tr[data-status-code='" + key + "']").css("background-color", val);
                    }
                });

                // click
                $("#table-status tr").click(function () {
                    if ($('th', this).length > 0) return;
                    $("#table-status tr").removeClass("trSelected");
                    $(this).removeAttr("style").addClass("trSelected");
                });
            },
            yes: function () {
                var obj = {};
                $("#table-status tr").each(function (inx, rec) {
                    if ($(rec).data() && $(rec).data().statusCode) {
                        var color = $(rec).getBackgroundColor();
                        //#dfdfdf 为默认选中时的背景色 #333333 默认字体颜色
                        if ($(rec).data() && $(rec).data().statusCode.indexOf("adHoc_") >= 0) {
                            var getColor = $(rec).getBackgroundColor(true);
                            if (getColor != '#333333' && getColor != '#000000') color = getColor;
                            else color = "";
                        }
                        if (color && color != "#dfdfdf") obj[$(rec).data().statusCode] = color;
                    }
                });

                localStorage.setItem('colors', JSON.stringify(obj));
                // colorsObj = obj;
                colorsMap = obj;//更新颜色缓存
                layer.closeAll();

                // 得到所有的tr, 根据状态或性质进行对比
                $(".datagrid-btable:last tr").each(function () {
                        var rtn2 = "RTN" == $("td[field=flightNatureCode2]", $(this)).text() ? "RTN" : "";
                        var rtn = "RTN" == $("td[field=flightNatureCode]", $(this)).text() ? "RTN" : "";
                        var status = $.trim($("td[field=status2]", $(this)).text() || rtn2 || $("td[field=status ]", $(this)).text() || rtn);
                        if($.trim($("td[field=status2]", $(this)).text()) != "DEP" && $.trim($("td[field=status]", $(this)).text())){
                            status = $.trim($("td[field=status ]", $(this)).text() || rtn)
                        }
                        if (status && obj[status] && $(this).getBackgroundColor() !== obj[status]) {
                            $(this).css("backgroundColor", obj[status]);
                        } else if (!obj[status]) {
                            $(this).css("backgroundColor", "");
                        }

                        //colorsMap = colorsObj;
                        var numDiv = $("td[field=placeNum]>div", $(this));
                        var atdIsBlack = !!($.trim($("td[field=atd]", $(this)).text() || $("td[field=atd2]", $(this)).text()));
                        var numText = $.trim(numDiv.text());
                        if (numText) {
                            var numSpan = numDiv.find("span");
                            if (numSpan.length != 1) {
                                numSpan = numDiv.html('<span style="color:#f03030;">' + numText + '</span>').find("span");
                            }

                            if (atdIsBlack && colorsMap["adHoc_placeNum_after"]) numSpan.css("color", colorsMap["adHoc_placeNum_after"]);
                            else if (colorsMap["adHoc_placeNum_before"]) numSpan.css("color", colorsMap["adHoc_placeNum_before"]);
                            else numSpan.removeAttr("style");
                        }
                    }
                );

                if (isExpand) {
                    $(".airplane-wrapper > div").filter(function () {
                        return $(this).data('o').status && obj[$(this).data('o').status] && obj[$(this).data('o').status] !== $(this).getBackgroundColor() && obj[$(this).data('o').status] !== "rgba(0, 0, 0, 0)";
                    }).each(function () {
                        $(this).css('backgroundColor', obj[$(this).data('o').status]);
                    });
                }
            }
        });
    });
}

/**
 * 通用的资源自动分配处理方法
 * @param url 分配地址
 * @param resourceTypeName 资源标识
 */
function commonDistributedResourceAuto(url, resourceTypeName, typeFlag) {
    // 查询可用的资源分配知识包
    $.post('./fetch-rule-packages', {projectCode: resourceTypeName}).done(function (data) {
        layer.open({
            title: '选择分配时间',
            btn: ['确定', '取消'],
            area: ['487px', !typeFlag ? '166px' : '210px'],
            type: 1,
            scrollbar: false,
            move: false,
            content: template.compile($utils.ajaxLoadFragments(ctxAssets + '/fragments/sys/gantt/ResourceDistTimeTpl.html'))({
                list: data.result.data,
                typeFlag: typeFlag
            }),
            success: () => {
                $("#selPackage").select2($.extend({}, {placeholder: '请选择资源分配知识包'}))
            },
            yes: function (index, layero) {
                if (_.isEmpty($("#iptStartTime").val())) {
                    layer.tips('请选择开始时间！', "#iptStartTime", {tips: 1});
                    return false;
                }

                if (_.isEmpty($("#iptOverTime").val())) {
                    layer.tips('请选结束择时间！', "#iptOverTime", {tips: 1});
                    return false;
                }

                $.ajax({
                    method: 'post',
                    url: ctx + url,
                    data: $.extend({}, {
                        startTime: $("#iptStartTime").val(),
                        endTime: $("#iptOverTime").val(),
                        packageId: $("#selPackage").val(),
                        allotType: !typeFlag ? '' : $("#selPackage2").val()
                    }, extraData,{random: $uuid()})
                }).done(function (data) {
                    layer.close(index);
                    if (data.code !== 0) layer.msg(data.message);
                    else layer.alert("提交任务失败：" + data.message);
                }).fail(function (data) {
                    layer.alert("提交任务失败：" + data.message);
                });
            }
        });
    }).fail(() => {
        layer.alert('向服务器请求可用的资源分配知识包时出现异常！')
    })
}

function filterValues(array, value) {
    return array.filter(looperValue => looperValue !== value);
}

function refreshRowBGColor(changeWrapper, rowIndex) {
    if (rowIndex !== -1) {
        if (changeWrapper.hasOwnProperty("status2") || changeWrapper.hasOwnProperty("turnBackTime") || changeWrapper.hasOwnProperty("status")) {
            var rtn = changeWrapper["turnBackTime"] ? "RTN" : "";
            var status = changeWrapper["status2"] || rtn || changeWrapper["status"];
            var colorsMap = JSON.parse(localStorage.getItem("colors")) || {};
            if (status) $("tr[datagrid-row-index=" + rowIndex + "]:last").css("backgroundColor", colorsMap[status]);
            else $("tr[datagrid-row-index=" + rowIndex + "]:last").css("backgroundColor", "");
        }
    }
}

function refreshGanttElementBGColor(changeWrapper, $ganttEl) {
    if ((_.has(changeWrapper, "status") || _.has(changeWrapper, "status2"))) {
        var _colorsObj = JSON.parse(localStorage.getItem("colors"));

        var status = changeWrapper["status2"] || changeWrapper["status"];
        if ($ganttEl.data('o')) $ganttEl.data('o').status = status || "";

        if (status && _colorsObj[status] && _colorsObj[status] !== 'rgba(0, 0, 0, 0)') $ganttEl.css("backgroundColor", _colorsObj[status]);
        else $ganttEl.css("backgroundColor", "#3EB7F7");
    }
}

function getGanttElemementType(draggable_holder, yAxis, _aircraft_num) {
    if (!draggable_holder) return getAxisNature(yAxis, _aircraft_num) === "1" ? "inte" : "intl";
    else {
        if (!draggable_holder.extra && !draggable_holder.extra.codeNature) {
            return getAxisNature(yAxis, _aircraft_num) === "1" ? "inte" : "intl";
        } else {
            if (draggable_holder.extra.codeNature === "3") return getAxisNature(yAxis, _aircraft_num) === "1" ? "inte" : "intl";
            else return draggable_holder.extra.codeNature === '1' ? 'inte' : 'intl'
        }
    }
}

function getAxisNature(yAxis, _aircraft_num) {
    return yAxis.filter(axis => axis.text === _aircraft_num)[0].nature;
}

function normalDragStopFn(origin_drag_elem) {
    if (origin_drag_elem && origin_drag_elem.data('o')) {
        origin_drag_elem.removeClass("gantt-collision").removeClass("blink");
        var oData = origin_drag_elem.data('o'),
            opts = $(".gantt").data('options');
        origin_drag_elem.css('top', origin_drag_elem.data('originPos').top);

        oData.gate = $.fn.gantt('getGate', oData, origin_drag_elem.offset().top, opts);
        if (opts.collisionDetectionHandler) opts.collisionDetectionHandler(event, {helper: origin_drag_elem});
        clearManuallyGanttRefs();
    }
}

function multiModeDragStopFn(origin_drag_elem) {
    if (!origin_drag_elem || !origin_drag_elem.data('o')) return;
    origin_drag_elem.css({top: origin_drag_elem.data('originPos').top + "px"});

    var offset = origin_drag_elem.offset(),
        yPos = offset.top,
        oData = origin_drag_elem.data('o'),
        opts = $(".gantt").data('options'),
        step = Math.floor((yPos - $.fn.gantt('getBaseEl').offset().top) / opts.stepHeight),
        gateArray = $.fn.gantt('getGate', origin_drag_elem.data('o'), origin_drag_elem.offset().top, opts),
        value = gateArray[0];
    oData.gate = $.fn.gantt('getGate', oData, yPos, opts);
    var ui = {helper: origin_drag_elem};
    origin_drag_elem.removeClass('gantt-collision').data('available', true);

    if (opts.collisionDetectionHandler) opts.collisionDetectionHandler(null, ui);
    clearManuallyGanttRefs();
}

function isFiltered() {
    return $('.datagrid-filter-c > input:text').filter(function () {
        return $(this).val() !== ""
    }).length !== 0;
}

// 清除没有任何元素的空白sub-row
function removeEmptySubRows(settings) {
    var multiSubRowsAxis = $(".gantt-left-panel > div[_idx]").filter(function () {
        return $(this).data('sub') !== undefined && $(this).data('sub') !== 1
    });
    // 遍历所有多于的行，找到没有元素的sub-row清除
    multiSubRowsAxis.each(function () {
        var current_sub = $(this).data('sub'),
            current_resource_code = $(this).text().trim();
        var $elements = $(".airplane-wrapper > div").filter(function () {
                return _.first($(this).data('o').gate).text === current_resource_code
            }),
            $currentAxis = $(this);

        var union_subs = _.union(_.without($elements.map(function () {
            return $(this).data('sub')
        }).get(), undefined));


        var actually_subrows_count = union_subs.length == 0 ? 1 : union_subs.length,
            empty_rows_count = current_sub - actually_subrows_count;

        if (empty_rows_count !== 0) {

            var final_height = actually_subrows_count * settings.stepHeight + 'px';
            $(this).css({'height': final_height, 'line-height': final_height}).data('sub', actually_subrows_count);
            $("div[_rowval=" + current_resource_code + "]").css({
                'height': final_height,
                'line-height': final_height
            }).data('step', actually_subrows_count);

            // 根据empty_rows_count将element整体上移 empty_rows_count * settings.stepHeight
            // 找为为空的subrows.offset.top， 将所有大于这个top值的gantt elements将上移settings.stepHeight
            var startEmptyRowIndex = _.difference(_.range(1, current_sub + 1), union_subs);
            var calcFinalHeight = $currentAxis.offset().top + settings.stepHeight * startEmptyRowIndex; // 大于此数值的element全部上移
            $(".airplane-wrapper > div").filter(function () {
                return $(this).offset().top > calcFinalHeight
            }).each(function () {
                $(this).offset({top: $(this).offset().top - settings.stepHeight})
            });

            // 设置sub
            if (actually_subrows_count === 1) {
                $elements.data('sub', 1);
            } else {
                $elements.each(function () { // 重新计算sub值
                    $(this).data('sub', Math.ceil(($(this).offset().top - $currentAxis.offset().top) / settings.stepHeight));
                })
            }
        }
    })
}

// ==================== 模板方法 ================================//
/**
 * 获取资源分配通用方法
 * @param name html文件名称, 无需.html
 * @param args 模板使用的数据
 */
function fetchResourceDistTpl(name, args) {
    return template.compile($utils.ajaxLoadFragments(ctxAssets + '/fragments/sys/gantt/resource-dist/' + name + '.html'))(args);
}

/**
 * 获取时间设置通用方法
 * @param name html文件名称, 无需.html
 * @param args 模板使用的数据
 */
function fetchTimeSettingTpl(name, args) {
    return template.compile($utils.ajaxLoadFragments(ctxAssets + '/fragments/sys/gantt/time-setting/' + name + '.html'))(args);
}

/**
 * 获取元素提示通用方法
 * @param name html文件名称, 无需.html
 * @param args 模板使用的数据
 */
function fetchReourceTipTpl(name, args) {
    return template.compile($utils.ajaxLoadFragments(ctxAssets + '/fragments/sys/gantt/resource-tip/' + name + '.html'))(args);
}


// ================================== Convert Ztree to EasyUI Tree =========================//
/**
 * 将开发框架中的zTree数据结构转换为EasyUI数据结构
 * @param zTreeData
 */
function convertZTree2EasyUITree(zTreeData) {
    var root = _(zTreeData).find((data) => {
            return data.pId === '0';
        }),
        result = {id: root.id, text: root.name, children: [], attributes: {type: 'root'}, state: 'open'};

    _recursiveTreeData(zTreeData.filter(entity => entity.pId === result.id), zTreeData, result);
    return result;
}

function _recursiveTreeData(cols, zTreeData, self) {
    for (var item of cols) {
        var subItems = _(zTreeData).filter((entity) => entity.pId === item.id);
        var result = {
            id: item.id,
            text: item.name,
            children: [],
            attributes: {type: $utils.superTrim(item.type)},
            state: 'open'
        };
        self.children.push(result);
        if (subItems.length !== 0) _recursiveTreeData(subItems, zTreeData, result);

    }
}

//========================数据实时刷新特殊处理方法=========================
/**
 * 得到数据变动信息
 * @param _changeList 修改列表
 * @param entityMapping 字然映射
 * @returns 变动Object
 */
function getChangeWrapper(_changeList, entityMapping) {
    return _changeList.reduce(function (result, item) {
        var key = Object.keys(item)[0], resultKey = item[key];
        if (entityMapping && _.has(entityMapping, resultKey)) resultKey = _.propertyOf(entityMapping)(item[key]);
        result[resultKey] = $utils.superTrim(item.newValue);
        return result;
    }, {});
}

/**
 * 构建新的Gantt Element数据结构
 */
function newTempalteWrapper(_entity, rowData) {
    return {
        startDate: formatDate(_entity.expectedStartTime), startTime: moment(_entity.expectedStartTime).format("HH:mm"),
        endDate: formatDate(_entity.expectedOverTime), endTime: moment(_entity.expectedOverTime).format("HH:mm"),
        expectedGate: _entity.expectedNum || 1,
        flightDynamicId: _entity.flightDynamicId,
        flightDynamicNature: _entity.flightDynamicNature,
        flightDynamicCode: _entity.flightDynamicCode,
        extra: {codeNature: $utils.superTrim(_entity.flightDynamicNature, "1")},
        inteActualOverTime: _entity.inteActualOverTime,
        inteActualStartTime: _entity.inteActualStartTime,
        intlActualOverTime: _entity.intlActualOverTime,
        intlActualStartTime: _entity.intlActualStartTime,
        moment: false, inteCode: [], intlCode: [], status: _entity.status, id: _entity.id, gate: [],
        text: _entity.flightCompanyCode + (rowData.flightDynamicCode || _entity.flightNum) + " " + $utils.superTrim(_entity.aircraftTypeCode, ''),
        planDate: formatDate(_entity.planDate), flightCompanyCode: _entity.flightCompanyCode
    };
}

function generateGanttWrapper(pairEntity, isOCIUpdateTime) { //isOCIUpdateTime参数 控制是否使用占用信息更新
    let inoutType = !_.isEmpty(pairEntity.flightNum) && !_.isEmpty(pairEntity.flightNum2) ? "RELATE" : _.isEmpty(pairEntity.flightNum) ? "SO" : "SI";
    var aircraftStartDate, aircraftOverDate;
    if (inoutType === "RELATE") {
        aircraftStartDate = pairEntity.ata || pairEntity.eta || pairEntity.arrivalPlanTime;
        aircraftOverDate = pairEntity.atd2 || pairEntity.etd2 || pairEntity.departurePlanTime2;
    } else if (inoutType === "SI") {
        aircraftStartDate = pairEntity.ata || pairEntity.eta || pairEntity.arrivalPlanTime;
        aircraftOverDate = moment(pairEntity.planDate).add(1, "day").format("YYYY-MM-DD 06:00");
    } else {
        aircraftStartDate = moment().format("YYYY-MM-DD HH:mm");
        aircraftOverDate = pairEntity.atd2 || pairEntity.etd2 || pairEntity.departurePlanTime2;
    }

    if (isOCIUpdateTime) {
        aircraftStartDate = pairEntity.expectedStartTime;
        aircraftOverDate = pairEntity.expectedOverTime;
    }

    if (moment(aircraftStartDate).isAfter(moment(aircraftOverDate))) { //如果结束时间早于开始时间，调个儿
        var temp = aircraftStartDate;
        aircraftStartDate = aircraftOverDate;
        aircraftOverDate = temp;
    }

    return {
        aircraftStartDate: formatDate(aircraftStartDate), aircraftOverDate: formatDate(aircraftOverDate),
        occupiedStart: aircraftStartDate, occupiedEnd: aircraftOverDate, arrivalPlace: '', expectedGate: 1, extra: {},
        planDate: formatDate(pairEntity.planDate),
        inCode: $utils.superTrim(pairEntity.flightCompanyCode) + $utils.superTrim(pairEntity.flightNum),
        outCode: $utils.superTrim(pairEntity.flightCompanyCode2) + $utils.superTrim(pairEntity.flightNum2),
        flightDynamicId: pairEntity.id,
        gate: $utils.superTrim(pairEntity.placeNum, "").split(",") || [],
        id: $utils.superTrim(pairEntity.id, ''), leave: "0", leavePlace: '', leaveTime: '', moment: false,
        status: (pairEntity.status2 != "DEP" && pairEntity.status) ? pairEntity.status : (pairEntity.status2 || pairEntity.status),
        text: $utils.superTrim(pairEntity.virtualTipField),
        typeCode: $utils.superTrim(pairEntity.aircraftTypeCode, ''),
        startDate: formatDate(aircraftStartDate), startTime: moment(aircraftStartDate).format("HH:mm"),
        endDate: formatDate(aircraftOverDate), endTime: moment(aircraftOverDate).format("HH:mm"),
        agentCode:$utils.superTrim(pairEntity.agentCode),flightCompanyCode:$utils.superTrim(pairEntity.flightCompanyCode)
    }
}

function enhanceXAxis(xAxis) {
    if (!xAxis || xAxis.length === 0) {
        var maxPlanDate = $utils.ajaxLoadFragments(ctx + "/rms/rs/getMaxPlanDate");
        return [moment(maxPlanDate).add(-1, "day").format("YYYY-MM-DD"), moment(maxPlanDate).format("YYYY-MM-DD"), moment(maxPlanDate).add(1, "day").format("YYYY-MM-DD")];
        // return [moment().add(-1, "day").format("YYYY-MM-DD"), moment().format("YYYY-MM-DD"), moment().add(1, "day").format("YYYY-MM-DD")];
    } else return xAxis.sort();
}

function enhanceXAxis4Mock(xAxis) {
    if (!xAxis || xAxis.length === 0) {
        var maxPlanDate = $utils.ajaxLoadFragments(ctx + "/rms/rs/getMaxPlanDate4Mock?infoId=" + $("#infoId").val() + "&resourceType=" + $("#resourceType").val());
        return [moment(maxPlanDate).add(-1, "day").format("YYYY-MM-DD"), moment(maxPlanDate).format("YYYY-MM-DD"), moment(maxPlanDate).add(1, "day").format("YYYY-MM-DD")];
    } else return xAxis.sort();
}

function enhanceUpdateDGRow(fieldName, matchValue, definition) {
    var [filtered, filteredRowIndex, filteredRowData, rowIndex, rowData] = getRowIndex(fieldName, matchValue);
    if (!filtered || (rowIndex !== undefined && rowIndex !== -1)) datagrid.datagrid("updateRow", {
        index: rowIndex,
        row: definition
    });
    else datagrid.data('datagrid').filterSource.rows[filteredRowIndex] = $.extend(datagrid.data('datagrid').filterSource.rows[filteredRowIndex], definition);
}

// 由于Gantt图拖动需要选中数据列表中相应的行，但数据列表有过滤功能，过滤后会导致拖动Gantt图时无法选中数据行的情况，特作此处理
// 数据被过滤：
// 1. 移动的Gantt图数据存在列表中，直接选中
// 2. 移动的Gantt图数据不存在列表中，利用隐藏数据源filter获取
// 数据未被过滤：直接选中。
function enhanceGetDGSelectedRow(_entityId, _matchField = "id") {
    var allRows = [];

    if (isFiltered()) allRows = getAllRows()
    else allRows = datagrid.datagrid('getData').rows

    return allRows.find((item) => item[_matchField] === _entityId)
}


function _formatDateTime(date) {
    return moment(date).format("YYYY-MM-DD HH:mm:ss");
}


function clearManuallyGanttRefs() {
    origin_drag_elem = origin_drag_target = manual_holder = draggable_holder = origin_drag_target = oldCarouselCode = null;
}

function getRowIndex(fieldName, comparedValue) {
    var filtered = isFiltered(), filteredRowIndex = -1, filteredRowData, rowIndex = -1, rowData;
    if (!comparedValue) return [filtered, filteredRowIndex, filteredRowData, rowIndex, rowData];

    if (filtered) {
        filteredRowIndex = datagrid.data('datagrid').filterSource.rows.findIndex(row => row[fieldName] === comparedValue);
        filteredRowData = datagrid.data('datagrid').filterSource.rows[filteredRowIndex];
    }

    rowIndex = datagrid.datagrid('getData').rows.findIndex(row => row[fieldName] === comparedValue);
    rowData = datagrid.datagrid('getData').rows[rowIndex];

    return [filtered, filteredRowIndex, filteredRowData, rowIndex, rowData];
}

function distributedWaitingDialog(tipMessage = "正在校验规则，请稍候……", icon = 16) {
    return layer.open({
        title: false,
        closeBtn: 0,
        scrollbar: false,
        move: false,
        btn: false,
        content: tipMessage,
        icon: icon
    });
}

/**
 * 数据表格高度适配
 */
function layoutAutomaticallyAdapter() {
    if (dataGridMeta) {
        // get height diff values
        var _diffvalue = $(".datagrid-view").height() - $(".datagrid-body").height(),
            _actual_height = $(document).height(),
            _toolbar = parseInt($(".datagrid-toolbar").css('height')),
            _dHeader = parseInt($(".datagrid-header").css('height')),
            _sPanel = parseInt($(".layout-expand-south").css('height'));
        if (isExpand) _sPanel = 300;

        if ($(".datagrid-view").height() < _actual_height) return;
        _actual_height = _actual_height - _toolbar - _dHeader - _sPanel;

        $(".datagrid-view2 > .datagrid-body").height(_actual_height);
        $(".datagrid-view1 > .datagrid-body").height(_actual_height);
        $(".datagrid-view").height(_actual_height + _diffvalue);
    }
}

function _panelExpandHandler() {
    isExpand = true;
    layoutAutomaticallyAdapter();
}

function _panelCollapseHandler() {
    isExpand = false;
    layoutAutomaticallyAdapter();
}

/**
 * 最大化最小化处理函数, 此方法不适用于「运输调度计划」
 */
function maximizeOrRestoreHandler() {
    // $(".gantt").css('height', $("#main-layout").layout('panel', 'south').panel('options').height - 35);
    // $(".gantt-content-scroller-wrapper").css('height', $(".gantt").height());
    // $(".gantt-left-panel").css("height", $(".gantt").height() - 18);
    // if ($("#graphic-panel").height() > 300) $(".layout-button-down").hide();
    // else {
    //     $(".layout-button-down").show();
    //     var gantHeight = $(".gantt").parent().parent().height() - 30;
    //     $(".gantt-content-scroller-wrapper").height(gantHeight);
    //     $(".gantt-left-panel").height(gantHeight - 18);
    // }
    $(".gantt").css('height', $("#main-layout").layout('panel', 'south').panel('options').height - 33);
    $(".gantt-content-scroller-wrapper").css('height', $(".gantt").height() - 90);
    $(".gantt-left-panel").css("height", $(".gantt").height() - 18);
    if ($("#graphic-panel").height() > ($("#main-layout").height() * 0.9)) $(".layout-button-down").hide();
    else {
        $(".layout-button-down").show();
        var gantHeight = $(".gantt").parent().parent().height() - 30;
        $(".gantt-content-scroller-wrapper").height(gantHeight - 90);
        $(".gantt-left-panel").height(gantHeight - 18);
    }
}

/**
 * 在删除时解除其它单个冲突元素的闪烁状态
 */
function blinkRemoveOpHandler4Collision(collisionRefs, currentCollisionUid) {
    if (collisionRefs || !currentCollisionUid) return;

    for (var collision of collisionRefs) {
        var targetElement = $("div[_uid_=" + collision + "]"),
            targetCollisionRefs = targetElement.data('collisionRefs');
        if (!targetCollisionRefs) continue;
        if (targetCollisionRefs.include(currentCollisionUid)) {
            targetCollisionRefs = _.without(targetCollisionRefs, currentCollisionUid);
            if (!targetCollisionRefs || !targetCollisionRefs.length) // 清除当前元素的闪烁及冲突效果
                targetElement.removeClass('gantt-collision').removeClass('blink');
            targetElement.data('collisionRefs', targetCollisionRefs);
        }
    }
}

function commonGanttElementRemoveFn() {
    var preparedRemoveGanttUID = $(this).data("uid");
    $(".airplane-wrapper > div.gantt-collision.blink").filter(function () {
        var collisionRefs = $(this).data('collisionRefs') || [];
        return collisionRefs.includes(preparedRemoveGanttUID);
    }).each(function () {
        var collisionRefs = _($(this).data('collisionRefs')).without(preparedRemoveGanttUID);
        if (_.isEmpty(collisionRefs)) $(this).removeClass("gantt-collision").removeClass("blink");
        $(this).data('collisionRefs', collisionRefs);
    });

    $(this).remove();
}

function checkGanttElementExistsByUID(uid) {
    return $("div[_uid_=" + uid + "]").length !== 0;
}

function getAllRows() {
    var rowAll = [...datagrid.datagrid('getData').rows, ...datagrid.datagrid('getData').unMatchedRows];
    return rowAll;
}

function clearNonExistsGanttCollisionCode(collisionRefs) {
    if (!collisionRefs || collisionRefs.length === 0) return [];
    var nonExistsRefs = [];
    for (var collision of collisionRefs) {
        if (!checkGanttElementExistsByUID(collision)) nonExistsRefs.push(collision);
    }
    return _(collisionRefs).difference(nonExistsRefs);
}

//vip信息显示
function vipFmt(val, row, index) {
    if (!val) return "";
    else return "有";
}

/**
 * vip数据处理
 * @param vipData
 * @returns {*}
 */
function vipToStr(vipData) {
    if (!vipData) return "";
    if (vipData.vipsex == 1) vipData.vipsex = "男";
    else vipData.vipsex = "女";

    if (vipData.needcheck == 1) vipData.needcheck = "是";
    else vipData.needcheck = "否";

    if (vipData.isvip == 1) vipData.isvip = "是";
    else vipData.isvip = "否";

    if (vipData.inout == "J") vipData.inout = "进港";
    else if (vipData.inout == "C") vipData.inout = "出港";
    return vipData;
}

//设鼠标指针样式
function css(value, row, index) {
    if (value) return "cursor:pointer";
}

//easyui datagrid本地排序方法 datagrid 对象 inOutType 动态表类型（J，C，R）
// var tmpTime = {timing: new Date(), count: 0, second: 0},
//     timeId, timeCount = 0;
//
// function sortDate(datagrid, inOutType) {
//     if (!datagrid || !inOutType) return;
//
//     //tabs激活时排序
//     try {
//         if ($('#maintabs', window.parent.document).find('.tabs-selected').text() != document.title) return;
//     } catch (e) {
//     }
//     //console.log("排序：" + document.title);
//
//     var diffSeconds = moment().diff(moment(tmpTime.timing), 'seconds'); //调用的时长
//     tmpTime.timing = new Date();
//     tmpTime.second = diffSeconds;
//     tmpTime.count += 1;
//
//     //console.log("开始计时器",tmpTime);
//     if (timeId) clearInterval(timeId);
//     timeId = setInterval(() => toTimeSort(timeId, tmpTime, datagrid, inOutType), 2000);
// }
//
// function toTimeSort(timeId, tmpTime, datagrid, inOutType) {
//     //console.log("计时器",tmpTime);
//     if (timeId && (tmpTime.second != 0 || timeCount == tmpTime.count)) {
//         //跳出计时器
//         //清除计录
//         tmpTime.timing = new Date();
//         tmpTime.second = 0;
//         tmpTime.count = 0;
//         clearInterval(timeId);
//         //console.log("跳出计时器",tmpTime);
//         immediateSortDate(datagrid, inOutType); //排序
//     } else if (timeId) {
//         if (timeCount != tmpTime.count) timeCount = tmpTime.count;
//     }
// }

var sortDateJ = _.debounce((datagrid) => immediateSortDate(datagrid, "J"), 2000);
var sortDateC = _.debounce((datagrid) => immediateSortDate(datagrid, "C"), 2000);
var sortDateR = _.debounce((datagrid) => immediateSortDate(datagrid, "R"), 10000);

//即时排序easyui datagrid 对象 inOutType 动态表类型（J，C，R）
function immediateSortDate(datagrid, inOutType) {
    if (!datagrid || !inOutType) return;

    //tabs激活时排序
    var selectId = "";
    try {
        var selecttdRow = $('#datagrid').datagrid('getSelected');
        if(selecttdRow) selectId = selecttdRow.id;
        if ($('#maintabs', window.parent.document).find('.tabs-selected').text() != document.title) return;
    } catch (e) {
    }
    //更新行并且获取数据行集合，注意getData返回的是{ total=4, rows:[{}....]}类似的数据

    //排序处理函数
    //_loopCounter = 0, _inOutType = inOutType;
    var sortedDatas = mysort(getAllRows(), inOutType);

    datagrid.datagrid('loadData', sortedDatas); //归并排序 重载数据
    setTimeout(function () {
        datagrid.datagrid('doFilter');
        if (selectId) $('#datagrid').datagrid('selectRecord', selectId);
    }, 1000);
    //datagrid.datagrid('doFilter');
}

function ganttPanelResizeHandler(width, height) {
    // $(".gantt").width(width);
    // $(".gantt-content-scroller-wrapper").width(width - $(".gantt-left-panel").width());

    $(".gantt").width(width).height(height - 32);
    //$(".gantt-content-scroller-wrapper").width(width - $(".gantt-left-panel").width()).height(height-32);
    $(".gantt-content-scroller-wrapper").width(width - $(".gantt-left-panel").width()).height(height - 121);
    $(".gantt-left-panel").height(height - 48);
}

function fetchVipInfos(rowIndex, field, value) {
    if ((field == "vip" || field == "vip2") && !!value) {
        $.getJSON(ctx + "/rms/vipplan/vipinfo?id=" + value, (msg) => {
            if (msg.code == 1) {
                var vipcount = msg.result.vip.length > 1 ? 1.6 : 1;
                layer.open({
                    title: "vip信息", type: 1, area: ["800px", (260 * vipcount) + "px"],
                    content: template('vip_info', {vipList: _.map(msg.result.vip, (o) => vipToStr(o))})
                });
            }
        });
    }
}

function emptyFirstAndLastCommas(str = "") {
    return str.replace(/^,?|,?$/g, '');
}


//---------------归并排序-begin -----------------------wjp_2016年10月11日17时08分
/**
 * 排序规则
 * 进港：航班日期，实达，预达，计达
 * 出港：航班日期，实起，预起，计起
 * 配对的：按航班日期、按出港实飞、按进港实达、按进港实飞、按出港预飞、按出港计飞、按进港预达、按进港计达、按进港计飞
 *   wjp_2017年8月30日需求改 配对：出港实飞、进港实达、进港预达、进港计达
 *   wjp_2017年10月13日18时17分需求改 配对排序修改
 */
var sortRules = {
    "J": ['planDate', 'ata', 'eta', 'arrivalPlanTime'],
    "C": ['planDate', 'atd', 'etd', 'departurePlanTime'],
    'R': ['planDate', 'atd2', 'ata', 'atd', 'etd2', 'departurePlanTime2', 'eta', 'arrivalPlanTime', 'departurePlanTime'],
    'R1': ['planDate', 'atd2', 'departurePlanTime2', 'ata', 'eta', 'arrivalPlanTime'],
    'R2': ['planDate', 'departurePlanTime2', 'ata', 'eta', 'arrivalPlanTime'],
    'R3': ['planDate', 'ata', 'arrivalPlanTime'],
    'R4': ['planDate', 'departurePlanTime2', 'arrivalPlanTime'],
    'R5': ['planDate'],
};

function mysort(allData, _inOutType) {

    //排序处理函数
    var _loopCounter = 0;

    function sortCallbackHandler(compareA, compareB, loopCounter, type) {
        var sortFieldName = sortRules[type][loopCounter];
        var milliseconds = toByTime(compareA[sortFieldName]).diff(toByTime(compareB[sortFieldName]));
        if (milliseconds > 0) return 1;
        else if (milliseconds < 0) return -1;
        else {
            if (loopCounter === sortRules[type].length - 1) return 0;
            return sortCallbackHandler(compareA, compareB, ++loopCounter, type);
        }
    }

    //字符串时间转为moment类型
    function toByTime(strTime) {
        if (!strTime) return moment(moment().format("YYYY-MM-DD")).add(5, 'year'); //后推5年当最大值
        else return moment(strTime);
    }

    //var _loopCounter = 0, _inOutType;

    function sortCall(a, b) {
        _loopCounter = 0;
        return sortCallbackHandler(a, b, _loopCounter, _inOutType);
    }

    function mergeSort(arr) { //采用自上而下的递归方法
        var len = arr.length;
        if (len < 2) return arr;
        var middle = Math.floor(len / 2), left = arr.slice(0, middle), right = arr.slice(middle);
        return merge(mergeSort(left), mergeSort(right));
    }

    function merge(left, right) {
        var result = [];
        while (left.length && right.length) {
            if (sortCall(left[0], right[0]) <= 0) result.push(left.shift());
            else result.push(right.shift());
        }
        while (left.length) result.push(left.shift());
        while (right.length) result.push(right.shift());
        return result;
    }

    if (_inOutType == "R") {
        var data1 = [], data2 = [], data3 = [], data4 = [];
        for (var i = 0; i < allData.length; i++) {
            var o = allData[i];
            if (o.statusName2 && o.statusName2.indexOf("已起飞") > -1) data1.push(o);
            else if (o.statusName && o.statusName.indexOf("到达") > -1) data2.push(o);
            else if (o.statusName && o.statusName.indexOf("前方起飞") > -1) data3.push(o);
            else data4.push(o);
        }
        data1 = mysort(data1, "R1");
        data2 = mysort(data2, "R2");
        data3 = mysort(data3, "R3");
        data4 = mysort(data4, "R4");
        allData = [...data1, ...data2, ...data3, ...data4];
        _inOutType = "R5";
    }

    return mergeSort(allData);
}

//---------------归并排序-end -----------------------

// --------------异步渲染处理函数---------------------
function asyncGanttElementRenderTaskFn(current, total) {
    asyncRenderTaskCompleted = false;
    var $panel = $(".layout-panel-south .panel-title");
    $panel.text("图形展示 - 正在渲染：" + (current + 1) + "/" + total + "(" + ((current + 1) / total * 100.0).toFixed(2) + "%)");
    if (current == total - 1) {
        $panel.text("图形展示 - 渲染完成");
        asyncRenderTaskCompleted = true;
        ganttRenderTaskCompleted = true;
        setTimeout(() => {
            $panel.text("图形展示");
        }, 1.5 * 1000);
    }
}

//--------------计算航班属性-----------------------
function getNature(flightAttrCode, originNature) {
    if (!flightAttrCode) return originNature;
    if (flightAttrCode == "11") return "2";
    if (flightAttrCode == "12") return "2";
    if (flightAttrCode == "21") return "1";
    if (flightAttrCode == "22") return "1";
    return "3";
}

function fetchUserCompanyPrivileges() {
    var privilegesData = [];
    $.ajax({
        url: '/a/sys/user/user-company-privileges', dataType: 'json', async: false,
        success: function (data) {
            privilegesData = data;
        }
    });

    return privilegesData;
}

function fetchServicePrivileges() {
    var privilegesData = [];
    $.ajax({
        url: '/a/sys/user/user-service-privileges', dataType: 'json', async: false,
        success: function (data) {
            privilegesData = data;
        }
    });

    return privilegesData;
}

// 权限判断
function checkOperationPermission(_op_company_code_, _op_service_code) {
    try {
        var _user_privileges_val_ = fetchUserCompanyPrivileges(), _service_privileges_val_ = fetchServicePrivileges();
        if (_.isEmpty(_user_privileges_val_) && _.isEmpty(_service_privileges_val_)) return false;
        if ((_user_privileges_val_.length === 1 && _user_privileges_val_[0] === 'all-privileges') ||
            ((_service_privileges_val_.length === 1 && _service_privileges_val_[0] === 'all-privileges'))) return true;

        if (_op_service_code && _service_privileges_val_.includes(_op_service_code)) return true;

        var first_cc, last_cc, is_same = true;

        if (_.contains(_op_company_code_, ",")) {
            var codeArray = _op_company_code_.split(","), first_cc = codeArray[0], last_cc = codeArray[1];
            is_same = first_cc === last_cc;
            _op_company_code_ = first_cc;
        }


        return is_same ? _user_privileges_val_.includes(_op_company_code_) : _user_privileges_val_.include(first_cc) || _user_privileges_val_.include(last_cc);
    } catch (e) {
        return false;
    }
}

//到港门、滑槽、登机口 实际占用时间设置 中的title设置
function getFormatTitle(row) {
    return {
        flightDynamicId: row.flightDynamicId, nature: row.flightDynamicNature,
        inteStart: row.inteActualStartTime, inteOver: row.inteActualOverTime,
        planDate: moment(row.planDate).format("YYYY-MM-DD"),
        flightCode: row.flightCompanyCode + row.flightDynamicCode + " " + (row.aircraftTypeCode || ''),
        aircraftNum: row.aircraftNum,
        departurePlanTime: moment(row.expectedStartTime).format("YYYY-MM-DD HH:mm"),
        arrivalPlanTime: moment(row.expectedOverTime).format("YYYY-MM-DD HH:mm")
    }
}

function resourceInitCallbackFn(result) {
    if (result.success) console.log(result.type, '处理完毕')
    if (_.isEmpty(result.restResourceArray)) return;
    var currentSelectedTab = result.restResourceArray.pop();

    var currentIframe = $(window.parent.document).find('#maintabs iframe').filter(function () {
        return $(this)[0].contentDocument.title === currentSelectedTab
    })[0];

    if (currentIframe && currentIframe.contentWindow.fnAfterInited) currentIframe.contentWindow.fnAfterInited({
        data: result.data, type: currentSelectedTab.replace('分配', ''), restResourceArray: result.restResourceArray
    })
}

function mockDistFn() {
    $.post('./fetch-rule-packages', {projectCode: moduleName}).done((data) => {
        layer.open({
            title: '模拟分配', btn: false, area: ['780px', '430px'], type: 1, scrollbar: false, move: false,
            content: template.compile($utils.ajaxLoadFragments(ctxAssets + '/fragments/sys/gantt/mock-dist/mock-dist.html'))({list: data.result.data}),
            success: () => {
                $("#selPackage").select2($.extend({}, {placeholder: '请选择资源包'}));
                $("#addMockBtn").linkbutton({
                    onClick: () => {
                        if (_.isEmpty($("#iptMockStartDate").val())) {
                            layer.tips('请选择开始时间！', "#iptMockStartDate", {tips: 1});
                            return false;
                        }

                        if (_.isEmpty($("#iptMockOverDate").val())) {
                            layer.tips('请选择结束时间！', "#iptMockOverDate", {tips: 1});
                            return false;
                        }

                        $("#addMockBtn").linkbutton('disable');

                        var waittingLayer = distributedWaitingDialog("正在执行模拟分配，请稍候……");

                        $.post(ctx + '/rms/rs/' + TPL_NAME + '-mock-dist', {
                            mockStartDate: $("#iptMockStartDate").val(),
                            mockOverDate: $("#iptMockOverDate").val(),
                            packageName: $("#selPackage").text(),
                            packageCode: $("#selPackage").val(),
                            filterDistedRes: $("#ckFilterDistedRes").prop('checked'),
                            omitOciDatas: $("#ckOmitOciDatas").prop('checked'),
                            resourceType: moduleName
                        }).done(function (data) {
                            layer.close(waittingLayer);
                            if (data.code !== 0) {
                                layer.msg('新增成功！')
                                mockGrid.datagrid('reload').datagrid('clearSelections');
                            } else layer.msg('新增失败：' + data.message);
                            $("#addMockBtn").linkbutton('enable');
                        }).fail(function (code, text) {
                            layer.close(waittingLayer);
                            layer.alert('新增失败，请求服务时出现未知错误！');
                            $("#addMockBtn").linkbutton('enable');
                        })
                    }
                });

                var mockGrid = $("#mockgrid").datagrid({
                    url: ctx + "/rms/resourceMockDistInfo/listByUser", queryParams: {resourceType: moduleName},
                    rownumbers: true, method: 'get', fitColumns: true, idField: "id",
                    singleSelect: true, remoteSort: false, multiSort: false, fit: true, toolbar: [{
                        text: '预览', iconCls: 'icon-application_view_detail', handler: () => {
                            var srow = mockGrid.datagrid('getSelected');
                            if (!srow) {
                                layer.msg('请选择一条数据再进行操作！');
                                return false;
                            }

                            $.get(ctx + '/rms/resourceMockDistDetail/checkData', {
                                infoId: srow.id,
                                resourceType: moduleName
                            }).done(function (data) {
                                if (data.code !== 0) {
                                    // 根据mockInfoID获取详情数据
                                    var previewIndex = layer.open({
                                        title: '预览模拟分配数据', btn: false, type: 2, scrollbar: false, move: false,
                                        content: [ctx + '/rms/rs/preview?infoId=' + srow.id + "&path=" + TPL_NAME + "&resourceType=" + moduleName, 'no']
                                    })

                                    layer.full(previewIndex)
                                } else layer.alert(data.message)
                            }).fail(function (code, text) {
                                layer.msg('预览模拟分配数据失败：与服务器通讯出现异常！')
                            })
                        }
                    }, {
                        text: '发布', iconCls: 'icon-web', handler: () => {
                            var srow = mockGrid.datagrid('getSelected');
                            if (!srow) {
                                layer.msg('请选择一条数据再进行操作！');
                                return false;
                            }

                            layer.confirm('您确定要发布当前选中的模拟分配数据吗？', function () {
                                $.post(ctx + '/rms/resourceMockDistInfo/publish', {
                                    infoId: srow.id,
                                    resourceType: moduleName,
                                    random: _socket_uuid
                                }).done(function (data) {
                                    if (data.code !== 0) {
                                        layer.closeAll();
                                        layer.msg(data.message);
                                    } else layer.alert(data.message);
                                }).fail(function (code, text) {
                                    layer.msg('发布模拟分配数据失败：与服务器通讯出现异常！')
                                })
                            });
                        }
                    }, {
                        text: '删除', iconCls: 'icon-cross', handler: () => {
                            var srow = mockGrid.datagrid('getSelected');
                            if (!srow) {
                                layer.msg('请选择一条数据再进行操作！');
                                return false;
                            }

                            layer.confirm('您确定要删除当前选中的模拟分配数据吗？', function () {
                                $.get(ctx + '/rms/resourceMockDistInfo/delete', {id: srow.id}).done(function (data) {
                                    if (data.code !== 0) {
                                        layer.msg('删除成功！');
                                        mockGrid.datagrid('reload').datagrid('clearSelections');
                                    } else layer.alert('操作失败：' + data.message);
                                })
                            })
                        }
                    }]
                }).datagrid('enableFilter', []).datagrid('doCellTip', cellTooltipCommonProps);
            }
        })
    }).fail((fail) => {
        layer.alert('向服务器请求可用的资源分配知识包时出现异常！')
    });
}

function removeDataByPlanDate(planDate) {
    var withoutPlanDateDatas = getAllRows().filter(entity => entity.planDate != planDate);
    datagrid.datagrid('loadData', withoutPlanDateDatas);
    $(".airplane-wrapper > div").filter(function () {
        return $(this).data('o').planDate == planDate
    }).each(commonGanttElementRemoveFn);
    setTimeout("datagrid.datagrid('doFilter')", 1000);
}