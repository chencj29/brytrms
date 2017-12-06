/**
 * JSON数据结构说明:
 * startDate: 开始日期-格式:yyyy-MM-dd 示例:2015-12-25
 * startTime: 开始日间-格式:HH:mm 示例:15:55
 * endDate: 结束日期,同startDate
 * endTime: 结束时间,同startTime
 * status: 状态,明文,枚举值
 * code: 编号
 * expectedGate: 分配的资源个数, 示例: 2  || 1
 * gate: 目前占用资源, 数组格式, 例如: [5,6] || [5]
 *
 * 其余参数由业务需求而定,所有json数据以HTML5自定义data属性方式附属在元素(div)中,在jQuery中可以通过$("#id").data('o')获取
 *
 *
 * 插件主要参数说明:
 * draggableAxis: 元素的拖拽方向, 可靠值为: ['x', 'y'] 默认值为 false
 * holdCount: 甘特图预留区个数,默认值为1, 更改此属于值会直接影响元素的计算结果
 * collisionThresholds: 碰撞检测元素间隔阀值, 单位为分钟, 插件会根据此值和cellFactor计算实际元素间的碰撞像素阀值
 * stepHeight: 每单元格的高度
 * stepWidth: 每单元格宽度
 * showLabelWidth: 元素显示text的最底宽度
 * unitFactor: 每个单元所占用的时间, 此属性值与cellFactor组合会影响Gantt渲染表现
 * unitWidth: 每单元格所占用的宽度
 * cellFactor: 小时单元格所占用的数值
 * yAxis: 纵向轴线(停机位) xAxis: 横向顶层轴线(日期) subXAxis: 横向子轴线(小时)
 * multi: 是否一行显示多个元素，默认为false
 * auto: 初始化时是否自行滚动显示当前时间的节点，默认为false
 *
 * 插件依赖:
 *  1) jQuery
 *  2) jQuery UI
 *  3) collision-detection-plugin location: assets/scripts/gantt/collision.detection.plugin.js
 *  4) underscore
 *  5) garims.jQuery.utilities  location: assets/scripts/metadata-dev/garims.jQuery.utilities.js
 *
 * 样式:
 *  gantt.css location: assets/scripts/gantt/gantt.css
 *
 *
 *  -------------------------changelogs-------------------------
 *  2016年2月18日 ~ 2016年2月24日 - add features by xiaowu
 *  * 添加一行显示多个元素特性
 *  * 代码重构
 *
 * 2016年3月2日 ~ 2016年3月3日
 * * 界面渲染代码重构，采用纯jQuery创建对象
 * * JSON结构添加transient属性，当属性为true时不再创建长方形，而是根据起始时间绘制两个圆点
 *
 */
;((function ($) {
    "use strict";

    var methods = {
        init: function (options) {
            var settings = $.extend({}, $.fn.gantt.defaults, options || {});
            return this.each(function () {
                var $this = $(this);
                $this.data('options', settings);

                //绘制界面
                methods.render.call($this);
                var yAxisHtmlArray = [];
                $.map($(".gantt-left-panel > div", $this), function (item) {
                    yAxisHtmlArray.push($.trim($(item).text()).gblen() * 6);  // 根据牛逼地计算,一个Gantt图中的字体占12个像素
                });

                $(".gantt-content", $this).css("left", _.max(yAxisHtmlArray) + 22 + "px");
                $(".airplane-wrapper", $this).html("");

                if (settings.data) settings.data.forEach(function (entity) {
                    methods.append.apply($this, [entity, settings]);
                });

                if (settings.timeline && settings.xAxis.includes(moment().format("YYYY-MM-DD"))) {
                    // 求出content高度 // 算出当前时间的left
                    var contentHeight = $(".outerDiv:first", $this).height(), left = methods.calcLeftPosVal(moment().format("YYYY-MM-DD HH:mm"), settings, $this);
                    //var contentHeight = $(".outerDiv").height(), left = methods.calcLeftPosVa(moment().format("2016-04-01 16:16"), settings);

                    $("<div class='timeline'/>", $this).css({
                        height: contentHeight, lineHeight: contentHeight, left: left
                    }).appendTo($(".timeline-wrapper", $this));

                    $("div.timeline", $this).css({
                        height: $(".outerDiv:first", $this).height(), lineHeight: $(".outerDiv:first", $this).height()
                    });


                    (function timeline_looper () {
                        $("div.timeline").each(function () {
                            $(this).css({
                                left: methods.calcLeftPosVal(moment().format("YYYY-MM-DD HH:mm"), settings, $(this).parents(".gantt"))
                            })
                        })

                        setTimeout(timeline_looper, 1000 * 6);
                    })();
                }
            });
        }, getBaseEl: function ($el) {
            return $(".innerDetail", $el);
        }, getDivTotalWidth: function (opts) {
            return this.getDayWidth(opts) * opts.xAxis.length;
        }, scroll2Now: function (opts, now) {
            var date = now || new Date(),
                month = (date.getMonth() + 1) < 10 ? ("0" + (date.getMonth() + 1)) : (date.getMonth() + 1),
                day = date.getDate() < 10 ? ("0" + date.getDate()) : date.getDate(), today = date.getFullYear() + "-" + month + "-" + day,
                hours = date.getHours(), minutes = date.getMinutes(), index = _.indexOf(opts.xAxis, today);
            if (index === 0 && hours < 10) return;

            var baseWidth = index * methods.getDayWidth(opts), additionalWidth = 0;
            if (hours < 4) baseWidth += (hours - 4) * methods.getHourWidth(opts);
            else additionalWidth = ((hours - 4) + (minutes / 60)) * methods.getHourWidth(opts);

            $(".gantt-content").scrollLeft(baseWidth + additionalWidth);
        }, getLeftHtml: function () {
            var opts = this.data('options'), $ganttLeftPanel = $("<div/>").attr("class", "gantt-left-panel"), _height = opts.stepHeight + "px",
                css = { "height": _height, "line-height": _height };

            _.times(3, function () {
                $ganttLeftPanel.append($("<div/>").text("").css(css));
            });

            _.times(opts.holdCount, function () {
                $ganttLeftPanel.append($("<div/>").text(opts.holdLabel).css(css).css({ 'background-color': '#efefef', 'cursor': 'pointer' })
                    .attr({ "_code": "预留区", "_available": "1" }).on('click', opts.move2HoldAreaCallback));
            });

            _.times(opts.yAxis.length, function (yIndex) {
                var $axis = $("<div/>", {
                    "_idx": yIndex, "_value": opts.yAxis[yIndex].value,
                    "_code": opts.yAxis[yIndex].text, "_available": opts.yAxis[yIndex].available
                }).css(css).text(opts.yAxis[yIndex].text).data('axis', opts.yAxis[yIndex]);
                if (opts.yAxis[yIndex].available == '0') $axis.css({ 'background-color': 'red', 'color': '#FFF' });
                if (opts.leftAxisClickHandler) $axis.css("cursor", "pointer").on('click', opts.leftAxisClickHandler);
                $ganttLeftPanel.append($axis);
            });

            //if (opts.leftAxisClickHandler) $ganttLeftPanel.on('click', 'div', opts, opts.leftAxisClickHandler);
            //$(".airplane-wrapper").on('click', "div", settings, settings.clickHandler);

            return $ganttLeftPanel;
        }, getHorizontalAxisHtmlCode: function () {
            // var opts = this.data('options'), minutsAxisLen = parseInt(opts.cellFactor / opts.unitFactor), hourAxisLen = opts.subXAxis.length,
            //     _height = opts.stepHeight + "px", $date = $("<div class='gantt-date'/>").css({height: _height});
            //
            // _.times(opts.xAxis.length, function (n) {
            //     $date.append($("<div/>").attr({_value: opts.xAxis[n]}).css({
            //         "line-height": _height,
            //         "height": _height,
            //         "width": minutsAxisLen * opts.unitWidth * hourAxisLen + "px"
            //     }).text(opts.xAxis[n]));
            // });
            //
            // return $date;

            var opts = this.data('options'), _height = opts.stepHeight,
                _width = parseInt(opts.cellFactor / opts.unitFactor) * opts.unitWidth * opts.subXAxis.length,
                htmlCodeArray = ["<div class='gantt-date' style='height:", opts.stepHeight, "px;'>"],
                stylestr = "style='line-height:" + _height + "px;height:" + _height + "px; width:" + _width + "px;'",
                htmlCodeStr = "";
            opts.xAxis.forEach((axis) => htmlCodeArray.$push("<div _value='").$push(axis).$push("' ").$push(stylestr).$push(">").$push(axis).$push("</div>"));
            htmlCodeArray.push("</div>");

            htmlCodeStr = htmlCodeArray.join("");
            htmlCodeArray.$clear();

            return htmlCodeStr;

        }, getSubHorizontalAxisHtmlCode: function () {
            // var opts = this.data('options'), axisLen = parseInt(opts.cellFactor / opts.unitFactor),
            //     _width = (axisLen * opts.unitWidth) + "px", _height = opts.stepHeight + "px", $hours = $("<div  class='gantt-hours-wrapper'/>");
            //
            // _.times(opts.xAxis.length, function (o) {
            //     _.times(opts.subXAxis.length, function (s) {
            //         $("<div class='gantt-hours'/>").css({height: _height}).append($("<div/>", {_value: opts.subXAxis[s]}).text(opts.subXAxis[s]).css({
            //             width: _width, height: _height, lineHeight: _height
            //         })).appendTo($hours);
            //     });
            // });
            // return $hours;

            var opts = this.data('options'), looperCount = opts.xAxis.length * opts.subXAxis.length, _width = (parseInt(opts.cellFactor / opts.unitFactor) * opts.unitWidth),
                htmlCodeArray = ["<div class='gantt-hours-wrapper' style='height:", opts.stepHeight, "px;width:", _width * looperCount, "px;'>"], htmlCodeStr = "",
                stylestr = "style='width:" + _width + "px;height:" + opts.stepHeight + "px;line-height:" + opts.stepHeight + "px;' ";

            _.times(opts.xAxis.length, () => opts.subXAxis.forEach((axis) =>
                htmlCodeArray.$push("<div class='gantt-hours' ").$push(stylestr).$push(" _value='").$push(axis).$push("'>").$push(axis).$push("</div>"))
            );

            htmlCodeArray.push("</div>");
            htmlCodeStr = htmlCodeArray.join("");
            htmlCodeArray.$clear();


            return htmlCodeStr;
        }, getMinutesAxisHtmlCode: function () {
            // var opts = this.data('options'), axisLen = parseInt(opts.cellFactor / opts.unitFactor), axisValue, _height = opts.stepHeight + "px",
            //     _width = opts.unitWidth + "px", $minutes = $("<div class='gantt-minutes-wrapper'/>");
            // _.times(opts.xAxis.length, function (j) {
            //     _.times(opts.subXAxis.length, function (k) {
            //         _.times(axisLen, function (i) {
            //             axisValue = i * opts.unitFactor;
            //             $("<div class='gantt-minutes'/>").css({height: _height}).append($("<div/>").text(axisValue).css({
            //                 width: _width, height: _height, lineHeight: _height
            //             })).appendTo($minutes);
            //         });
            //     });
            // });
            //
            // return $minutes;

            var opts = this.data('options'), _width = opts.unitWidth + "px", htmlCodeArray = [], htmlCodeStr = "", subHtmlCodeArray = [], subHtmlCodeStr = "",
                stylestr = "style='width:" + opts.unitWidth + "px; height:" + opts.stepHeight + "px; line-height:" + opts.stepHeight + "px;'",
                $minutes = $("<div class='gantt-minutes-wrapper'/>");

            _.times(parseInt(opts.cellFactor / opts.unitFactor), (n) => subHtmlCodeArray.$push("<div class='gantt-minutes' ").$push(stylestr).$push(">").$push(n).$push("</div>"));
            subHtmlCodeStr = subHtmlCodeArray.join("");
            subHtmlCodeArray.$clear();

            htmlCodeArray.$push("<div class='gantt-minutes-wrapper' style='height:").$push(opts.stepHeight).$push("px;'>");

            _.times(opts.xAxis.length * opts.subXAxis.length, () => htmlCodeArray.push(subHtmlCodeStr));
            htmlCodeArray.push("</div>");
            htmlCodeStr = htmlCodeArray.join("");
            htmlCodeArray.$clear();

            return htmlCodeStr;
        }, getDayWidth: function (opts) {
            return parseInt(opts.cellFactor / opts.unitFactor) * opts.unitWidth * opts.subXAxis.length;
        }, getHourWidth: function (opts) {
            return parseInt(opts.cellFactor / opts.unitFactor) * opts.unitWidth;
        }, getGridHtmlCode: function () {
            var opts = this.data('options'), totalWidth = methods.getDivTotalWidth(opts) + "px",
                dayWidth = methods.getDayWidth(opts) + "px", unitWidth = opts.unitWidth + "px",
                _height = opts.stepHeight + "px", $detail = $("<div class='innerDetail'/>").css("width", totalWidth);

            $detail.append($("<div class='airplane-wrapper'/>"));
            $detail.append($("<div class='timeline-wrapper'/>"));

            // _.times(opts.xAxis.length, function (i) {
            //     var $outerDiv = $("<div class='outerDiv'/>").attr({"_xAxisIndex": i, "_xAxisVal": opts.xAxis[i]});
            //
            //     _.times(opts.holdCount, function (h) {
            //         var $rowDiv = $("<div class='rowDiv'/>").css({
            //             width: dayWidth,
            //             height: _height,
            //             backgroundColor: '#efefef'
            //         });
            //         _.times(opts.subXAxis.length, function (k) {
            //             _.times(parseInt(opts.cellFactor / opts.unitFactor), function (l) {
            //                 $("<div/>").attr({"_rowIndex": h, "_columnIndex": k, "_minutesVal": (l + 1) * opts.unitFactor}).css({
            //                     width: unitWidth, height: _height
            //                 }).appendTo($rowDiv);
            //             });
            //         });
            //         $rowDiv.appendTo($outerDiv);
            //     });
            //
            //     _.times(opts.yAxis.length, function (j) {
            //         var $rowDiv = $("<div class='rowDiv'/>").attr({
            //             "_rowIndex": j, "_rowVal": _.propertyOf(opts.yAxis[j])('text')
            //         }).data({"rowIndex": j, "rowVal": opts.yAxis[j]}).css({width: dayWidth, height: _height});
            //
            //         _.times(opts.subXAxis.length, function (k) {
            //             _.times(parseInt(opts.cellFactor / opts.unitFactor), function (l) {
            //                 $("<div/>").attr({"_rowIndex": j, "_columnIndex": k, "_minutesVal": (l + 1) * opts.unitFactor}).data({
            //                     "rowIndex": j, "columnIndex": k, "minutesVal": (l + 1) * opts.unitFactor
            //                 }).css({width: unitWidth, height: _height}).appendTo($rowDiv);
            //             });
            //         });
            //         $rowDiv.appendTo($outerDiv);
            //     });
            //     $outerDiv.appendTo($detail);
            // });
            // return $detail;

            _.times(opts.xAxis.length, function (i) {
                var $outerDiv = $("<div class='outerDiv'/>").attr({ "_xAxisIndex": i, "_xAxisVal": opts.xAxis[i] });

                _.times(opts.holdCount, () => $("<div class='rowDiv'/>").css({ width: dayWidth, height: _height, backgroundColor: '#efefef' }).appendTo($outerDiv));

                _.times(opts.yAxis.length, (j) =>
                    $("<div class='rowDiv'/>").attr({
                        "_rowIndex": j, "_rowVal": _.propertyOf(opts.yAxis[j])('text')
                    }).data({ "rowIndex": j, "rowVal": opts.yAxis[j] }).css({ width: dayWidth, height: _height }).appendTo($outerDiv)
                );
                $outerDiv.appendTo($detail);
            });
            return $detail;
        }, render: function () {
            // clear first
            this.html("");
            this.html(methods.getLeftHtml.call(this));
            var opts = this.data('options'), totalWidth = methods.getDivTotalWidth(opts), //total width
                $content = $("<div/>").attr("class", "gantt-content"),
                $header = $("<div/>").attr("class", "ganttHeader").css({ width: totalWidth + "px", height: "90px" });

            // render dates
            $header.append(methods.getHorizontalAxisHtmlCode.call(this));
            // render hours
            $header.append(methods.getSubHorizontalAxisHtmlCode.call(this));
            // render minutes
            $header.append(methods.getMinutesAxisHtmlCode.call(this));
            var w = $(".gantt").width() - $(".gantt-left-panel").width();
            $content.append($header).css({ width: w - 50 });
            // render cells
            $content.append(methods.getGridHtmlCode.call(this));
            $content.appendTo(this);
        }, reload: function (url, params) {
            var settings = this.data('options');
            if (url && !_.isEmpty(url)) settings.ajaxUrl = url;
            if (!_.isNull(params)) settings.ajaxParams = params;
            settings.data = [];
            methods.init.call(this, settings);
        }, reloadData: function (datas) {
            var settings = this.data('options');
            settings.data = datas;
            datas.forEach(function (data) {
                methods.append.bind(this)(data, settings);
            }.bind(this));
        }, getCollisionElements: function () {
            return $(".airplane-wrapper > div.gantt-collision");
        }, getModifiedElements: function () {
            return $(".airplane-wrapper > div:data(modified)");
        }, getGate: function (oData, _top, opts) {
            var newGate = [], baseTop = methods.getBaseEl($(".gantt#" + oData.flightDynamicId)).offset().top;
            if (!opts.multi) {
                oData.gate = opts.yAxis[(_top - baseTop) / opts.stepHeight - opts.holdCount];
                //if (oData.gate !== null && oData.gate !== undefined) {
                if (!_.isEmpty(oData.gate)) {
                    newGate.push(oData.gate);
                    for (var startIndex = 1, len = oData.expectedGate; startIndex < len; startIndex++) {
                        var index = _.indexOf(opts.yAxis, oData.gate) + 1;
                        newGate.push(opts.yAxis[index]);
                        oData.gate = opts.yAxis[index];
                    }
                }
            } else {
                var step = (_top - baseTop) / opts.stepHeight;
                if (step >= opts.holdCount) {
                    //遍历yaxis，根据sub data-attr来计算当前是在哪个axis下
                    for (var i = 0, len = opts.yAxis.length; i < len; i++) {
                        var _yAxis = opts.yAxis[i], _value = _.propertyOf(_yAxis)('text'),
                            sub = $("div.gantt-left-panel > div[_code=" + _value + "]", $("#" + oData.flightDynamicId)).data('sub');
                        if (!sub) sub = 1;
                        step -= sub;
                        if ((opts.holdCount == 0 && step < 0) || (opts.holdCount != 0 && step <= 0)) {
                            newGate = [_yAxis];
                            break;
                        }
                    }
                } else newGate = undefined; // 放入Hold区
            }
            return newGate;
        }, adjustPos: function (el, start, over, opts) {
            var dayWidth = methods.getDayWidth(opts), hourWidth = methods.getHourWidth(opts),
                startMt = moment(start), overMt = moment(over),
                startDate = startMt.format("YYYY-MM-DD"), startTime = startMt.format("HH:mm"),
                endDate = overMt.format("YYYY-MM-DD"), endTime = overMt.format("HH:mm"),
                startTimeArray = startTime.split(":"), startTimeIntPart = startTimeArray[0],
                startTimeDecimalPart = (startTimeArray[1] / opts.cellFactor).toFixed(2),
                endTimeArray = endTime.split(":"), endTimeIntPart = endTimeArray[0],
                endTimeDecimalPart = (endTimeArray[1] / opts.cellFactor).toFixed(2),
                _$startOuterDiv = $(".outerDiv[_xaxisval=" + startDate + "]", $(".gantt[id=" + el.data('o').flightDynamicId + "]")),
                _$endOuterDiv = $(".outerDiv[_xaxisval=" + endDate + "]"),
                diff = (parseInt(_$endOuterDiv.attr("_xaxisindex")) - parseInt(_$startOuterDiv.attr("_xaxisindex"))), _width = $(this).width();

            if (diff === 0)  //在同一天
                _width = ((parseFloat(endTimeIntPart) + parseFloat(endTimeDecimalPart)) - ((parseFloat(startTimeIntPart) + parseFloat(startTimeDecimalPart)))) * hourWidth;
            else if (diff === 1)  //隔天
                _width = ((opts.subXAxis.length - (parseFloat(startTimeIntPart) + parseFloat(startTimeDecimalPart))) + ((parseFloat(endTimeIntPart) + parseFloat(endTimeDecimalPart)))) * hourWidth;
            else
                _width = ((parseFloat(endTimeIntPart) + parseFloat(endTimeDecimalPart)) - ((parseFloat(startTimeIntPart) + parseFloat(startTimeDecimalPart)))) * opts.stepWidth + diff * dayWidth;

            el.css("width", _width);
        }, calcLeftPosVal: function (now, opts, target) {
            var startTimeArray = moment(now).format("HH:mm").split(":");
            return parseInt($(".outerDiv[_xaxisval=" + moment(now).format("YYYY-MM-DD") + "]", target).attr("_xaxisindex"))
                * methods.getDayWidth(opts) + (parseFloat(startTimeArray[0]) + parseFloat((startTimeArray[1] / opts.cellFactor).toFixed(2)))
                * methods.getHourWidth(opts);
        }, append: function (data, settings) {
            var hourWidth = parseInt(settings.cellFactor / settings.unitFactor) * settings.unitWidth,
                dayWidth = methods.getDayWidth(settings), textAxis = _.pluck(settings.yAxis, "text"), now = new Date();

            data = $.extend({}, data, settings.dataExt);
            if (_.isEmpty(_.without(data.gate, ""))) return;

            data.endDate = data.endDate ? data.endDate : data.startDate;
            data.endTime = data.endTime ? data.endTime : moment().format('HH:mm');

            if (!data.startDate || !data.endDate) return;
            let startDate = data.startDate, startTime = data.startTime, endDate = data.endDate, endTime = data.endTime;

            if (!_.contains(settings.xAxis, startDate)) {
                startDate = _.first(settings.xAxis);
                startTime = "00:00";
            } else if (!_.contains(settings.xAxis, endDate)) {
                endDate = _.last(settings.xAxis);
                endTime = "23:59"
            }

            var _top, startTimeArray = startTime.split(":"), startTimeIntPart = startTimeArray[0],
                startTimeDecimalPart = (startTimeArray[1] / settings.cellFactor).toFixed(2),
                endTimeArray = endTime.split(":"), endTimeIntPart = endTimeArray[0],
                endTimeDecimalPart = (endTimeArray[1] / settings.cellFactor).toFixed(2),
                _$startOuterDiv = $(".outerDiv[_xaxisval=" + startDate + "]", this),
                _$endOuterDiv = $(".outerDiv[_xaxisval=" + endDate + "]", this),
                _left = parseInt(_$startOuterDiv.attr("_xaxisindex")) * dayWidth + (parseFloat(startTimeIntPart) + parseFloat(startTimeDecimalPart)) * hourWidth,
                diff = (parseInt(_$endOuterDiv.attr("_xaxisindex")) - parseInt(_$startOuterDiv.attr("_xaxisindex"))),
                _width = 0, height;

            if (!_.isEmpty(data.gate)) {
                height = data.gate.length * settings.stepHeight;
                if (!settings.multi) {
                    _top = (_.indexOf(textAxis, data.gate[0]) + settings.holdCount) * settings.stepHeight;
                } else {
                    // 如果是多子元素模式，初始化高度时需要计算该gate前的sub情况
                    var multiModeHeight = 0;
                    $("div.gantt-left-panel > div[_value]", this).filter(function () {
                        return $(this).attr('_idx') < _.indexOf(textAxis, data.gate[0]);
                    }).each(function () {
                        var current_sub = $(this).data('sub') === undefined ? 1 : $(this).data('sub');
                        multiModeHeight += current_sub * settings.stepHeight;
                    });
                    _top = multiModeHeight + (settings.holdCount) * settings.stepHeight;
                }
            } else {
                _top = methods.getBaseEl(this).offset().top;
                height = settings.stepHeight;
            }

            if (diff === 0)  //在同一天
                _width = ((parseFloat(endTimeIntPart) + parseFloat(endTimeDecimalPart)) - ((parseFloat(startTimeIntPart) + parseFloat(startTimeDecimalPart)))) * hourWidth;
            else if (diff === 1)  //隔天
                _width = ((settings.subXAxis.length - (parseFloat(startTimeIntPart) + parseFloat(startTimeDecimalPart))) + ((parseFloat(endTimeIntPart) + parseFloat(endTimeDecimalPart)))) * hourWidth;
            else
                _width = ((parseFloat(endTimeIntPart) + parseFloat(endTimeDecimalPart)) - ((parseFloat(startTimeIntPart) + parseFloat(startTimeDecimalPart)))) * settings.stepWidth + diff * dayWidth;

            var $div, uid = $uuid(), gateWrapperArray = [];


            // 将编号转换为标准格式
            _.each(data.gate, function (item) {
                gateWrapperArray.push(_.findWhere(settings.yAxis, { text: item }))
            });

            data.gate = gateWrapperArray;
            data.uid = uid;  //将生成的uuid记录到实体中，方便后续调用
            if (data.moment) {
                $div = $("<div/>").css({
                    /*width: 20,*/ top: _top, left: _left, boxShadow: 'none', zIndex: data.zIndex, minWidth: 20, /* height: height + "px", lineHeight: height + "px",*/
                    backgroundColor: "transparent", color: data.extra.bgColor ? data.extra.bgColor : "#3eb7f7", borderRadius: 0, fontSize: "15px"
                }).html("&#x25CF;").attr({ draggable: data.draggable, _id_: data.id, _uid_: uid, _text: data.text }).data({
                    'uid': uid, 'text': data.text, 'o': data
                }).appendTo($(".airplane-wrapper", this));
            } else {
                $div = $("<div/>").css({
                    width: _width, top: _top, left: _left, zIndex: data.zIndex, /*height: height + "px", lineHeight: height + "px", fontSize: "10px",*/
                    backgroundColor: data.extra.bgColor ? data.extra.bgColor : '#3EB7F7', minWidth: settings.minWidth
                }).attr({
                    draggable: data.draggable, _id_: data.id, _uid_: uid, _dynamic_: data.extra.dynamic, _actual_: data.extra.actual
                }).data({ 'uid': uid, 'text': data.text, 'o': data, 'dynamic': data.extra.dynamic, actual: data.extra.actual });
                if (settings.showLabel && _width > data.text.length * 15) $div.text(data.text);
                else $div.text("...");
                $div.appendTo($(".airplane-wrapper", this));
            }


            // 如果为实际节点，初始化的时候默认将其放置到第二行
            if (data.extra.actual) {
                // 找到gate
                var gateText = data.gate[0].text, yAxisCell = $(".gantt-left-panel > div[_code=" + gateText + "]", this),
                    outerRow = $("div[_rowval=" + (gateText) + "]", this),
                    innerRow = $(".rowDiv[_rowindex=" + _.indexOf(_.pluck(settings.yAxis, 'text'), (gateText)) + "] > div", this),
                    yAxisCellHeight = parseInt(yAxisCell.css("height").replace('px', '')),
                    finalHeightStr = yAxisCellHeight + settings.stepHeight + "px", currentOffset = $(this).offset(),
                    sub = yAxisCell.data('sub') === undefined ? 1 : yAxisCell.data('sub');

                yAxisCell.css({ 'height': finalHeightStr, 'line-height': finalHeightStr }).data('sub', ++sub);
                outerRow.css({ 'height': finalHeightStr, 'line-height': finalHeightStr });
                innerRow.css({ 'height': finalHeightStr, 'line-height': finalHeightStr });

                $div.offset({ top: $div.offset().top + settings.stepHeight });
            }

            if (settings.dblClick) $div.on("dblclick", "", data, settings.dblClick);
            if (settings.collisionDetectionHandler) {
                var ui = {};
                ui.helper = $div;
                settings.collisionDetectionHandler(null, ui);
            }

            if (settings.draggableHandler) $div.draggable(settings.draggableHandler());
            if (settings.clickHandler) $div.on("click", "", settings, settings.clickHandler);
            if (settings.resizableHandler) $div.resizable(settings.resizableHandler);
            if (settings.auto) methods.scroll2Now(settings);
            if (settings.afterRenderHandler) settings.afterRenderHandler(settings);
        }
    }

    $.fn.gantt = function (method) {
        // Method calling logic
        if (methods[method]) return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        else if (typeof method === 'object' || !method) return methods.init.apply(this, arguments);
        else $.error('Method ' + method + ' does not exist on jQuery.Gantt');
    };

    $.fn.gantt.defaults = {
        ajaxUrl: "", ajaxParams: {}, ajaxMethod: 'get', draggableAxis: false, holdCount: 1, holdLabel: '预留区', collisionThresholds: 15, defaultLabel: '...',
        stepHeight: 30, stepWidth: 30, showLabelWidth: 50.5, showLabel: true, unitFactor: 10, cellFactor: 60.0, unitWidth: 20, yAxis: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
        xAxis: ['2015-12-07', '2015-12-08', '2015-12-09'], subXAxis: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23],
        multi: false, auto: false, saveCallbackFn: false, leftAxisClickHandler: false, draggableStartCallback: false, promptHandler: false, timeline: false,
        getGate: false, move2HoldAreaCallback: false, dblClick: false, afterRenderHandler: false, draggable: true, resizableHandler: {
            containment: ".innerDetail", handles: 'e', stop: function (event, ui) {
                if (this.collisionDetectionHandler) this.collisionDetectionHandler(event, ui, this);
                if (methods.getTime) methods.getTime(event, ui, this);
            }
        },
        clickHandler: function (event) {
            if (event.data.promptHandler) event.data.promptHandler($(this).data('o'), event);
        },
        collisionDetectionHandler: function (event, ui) {
            var _this = this;
            if (overlaps) {
                //移动一下元素首先需要检测与已碰撞的元素是否消除碰撞,其次需检测与没有碰撞的元素是否产生碰撞
                $(".airplane-wrapper > div").filter(function () {
                    return $(this).data('uid') != ui.helper.data('uid');
                }).each(function () {
                    if ($(this).data('uid') !== ui.helper.data('uid')) {
                        var result = overlaps(ui.helper, $(this), _this.collisionThresholds);
                        if (result === true) {
                            ui.helper.addClass('gantt-collision');
                            $(this).addClass('gantt-collision');
                            //发现有冲突的航班,双向建立引用
                            var currentCollisionRefs = ui.helper.data('collisionRefs') || [], collisionRefs = $(this).data('collisionRefs') || [];
                            if (!_.contains(currentCollisionRefs, $(this).data('uid'))) currentCollisionRefs.push($(this).data('uid'));
                            if (!_.contains(collisionRefs, ui.helper.data('uid'))) collisionRefs.push(ui.helper.data('uid'));
                            ui.helper.data('collisionRefs', currentCollisionRefs);
                            $(this).data('collisionRefs', collisionRefs);
                            ui.helper.addClass("blink");
                            $(this).addClass("blink");
                        } else {
                            if (event) {
                                if (_.contains(ui.helper.data('collisionRefs'), $(this).data('uid'))) {
                                    var collisionRefs = _.without(ui.helper.data('collisionRefs'), $(this).data('uid')),
                                        currentCollisionRefs = _.without($(this).data('collisionRefs'), ui.helper.data('uid'));
                                    if (_.isEmpty(collisionRefs)) {
                                        ui.helper.removeClass('gantt-collision');
                                        ui.helper.removeClass("blink");
                                    }
                                    if (_.isEmpty(currentCollisionRefs)) {
                                        $(this).removeClass('gantt-collision').removeClass("blink");
                                    }
                                    ui.helper.data('collisionRefs', collisionRefs);
                                    $(this).data('collisionRefs', currentCollisionRefs);
                                }
                            }
                        }
                    }
                });
            }
        },
        draggableHandler: function () {
            var _this = this;
            return {
                containment: ".innerDetail", axis: this.draggableAxis, start: function (event, ui) {
                    ui.helper.removeClass("blink");
                    if (_this.draggableStartCallback) _this.draggableStartCallback(event, ui);
                }, drag: function () {
                    if (layer) layer.closeAll('page');
                }, stop: function (event, ui) {
                    var offset = $(this).offset(), yPos = offset.top, oData = ui.helper.data('o'), expectedGate = oData.expectedGate,
                        step = Math.floor((yPos - methods.getBaseEl($("#" + oData.flightDynamicId)).offset().top) / _this.stepHeight);
                    $(this).animate({
                        'top': "-=" + (yPos - ((step * _this.stepHeight) + methods.getBaseEl($("#" + oData.flightDynamicId)).offset().top)),
                        'height': _this.stepHeight * expectedGate, 'line-height': _this.stepHeight * expectedGate
                    }, 300);


                    _.delay(function () {
                        var gateArray = methods.getGate(oData, ui.helper.offset().top, _this), value, pass = true;
                        if (_.isEmpty(gateArray)) value = ["预留区"];
                        else if (oData.expectedGate === 1) value = [gateArray[0].text];
                        else value = _.pluck(gateArray, "text");

                        // 检测是否可用
                        pass = _.every(value, function (v) {
                            return $("div[_code=" + v + "]").attr("_available") === '1';
                        });

                        //根据value在leftPanel中查找，判断相应资源是否可用
                        if (pass) {
                            ui.helper.removeClass('gantt-collision').data('available', true);
                            if (_this.resizableHandler && _this.getTime) _this.getTime(event, ui, _this);
                            else _this.changeYAxisValue(event, ui);
                            if (_this.collisionDetectionHandler) _this.collisionDetectionHandler(event, ui);
                        } else {
                            layer.msg("当前机位不可用");
                            ui.helper.addClass('blink gantt-collision').data('available', false);
                        }
                    }, 500);
                }
            }
        },
        changeYAxisValue: function (event, ui) {
            var oData = ui.helper.data('o'), _top = ui.helper.offset().top;
            oData.gate = methods.getGate(oData, _top, this);

            //如果是移入Hold区,则将多机位的元素Height收起,同理将元素移出Hold区时,也根据机位还原其Height属性
            //从Hold区移出或正常移动
            if (!_.isEmpty(oData.gate))
                ui.helper.animate({ height: this.stepHeight * parseInt(oData.expectedGate), lineHeight: this.stepHeight * parseInt(oData.expectedGate) }, 100);
            else  //移入
                ui.helper.animate({ height: this.stepHeight, lineHeight: this.stepHeight }, 100);

            if (!_.isEmpty(oData.gate)) {
                ui.helper.data({ 'o': oData, 'modified': true });
                if (this.promptHandler) this.promptHandler(oData, event);
                if (this.leftAxisClickHandler) $("div[_code=" + oData.gate[0].text + "]").trigger('click');
            } else {
                //移入hold区，清除机位号
                if (this.move2HoldAreaCallback) this.move2HoldAreaCallback(oData);
            }
        },
        getTime: function (event, ui, opts) {
            var oData = ui.helper.data('o'), hourWidth = methods.getHourWidth(opts), dayWidth = methods.getDayWidth(opts);

            //判断节点的Width以显示航班号
            ui.helper.contents().filter(function () {
                return this.nodeType == 3;
            }).each(function () {
                this.data = ui.helper.width() >= opts.showLabelWidth ? oData.text : opts.defaultLabel;
                return false;
            });

            var _scrollLeft = $(".gantt-content").scrollLeft(), _staticOffsetLeft = _scrollLeft + $(".innerDetail").offset().left,
                _left = ui.helper.offset().left + _scrollLeft - _staticOffsetLeft, _width = ui.helper.width(), _top = ui.helper.offset().top;
            var _startDayIndex = Math.floor(_left / dayWidth), _startLeft = _left - (dayWidth * _startDayIndex),
                _startHourVal = ((_startLeft) / hourWidth).toFixed(2), _startHourArray = (_startHourVal + "").split("."),
                _startHours = _startHourArray[0], _minutes = (parseFloat("0." + _startHourArray[1]) * opts.cellFactor).toFixed(0),
                _dayStart = opts.xAxis[_startDayIndex];


            var _endDayIndex = Math.floor((_left + _width) / dayWidth), _endLeft = (_left + _width) - (dayWidth * _endDayIndex),
                _endHourVal = ((_endLeft) / hourWidth).toFixed(2), _endHourArray = (_endHourVal + "").split("."),
                _endHours = _endHourArray[0], _endMinutes = (parseFloat("0." + _endHourArray[1]) * opts.cellFactor).toFixed(0),
                _dayEnd = opts.xAxis[_endDayIndex];

            if (_endDayIndex === opts.xAxis.length)
                _dayEnd = opts.xAxis[_endDayIndex - 1];


            if ((_left + _width) >= (dayWidth * opts.xAxis.length)) {
                _endHours = 24;
                _endMinutes = 0;
            }

            oData.startDate = _dayStart;
            oData.startTime = _startHours + ":" + _minutes;
            oData.endDate = _dayEnd;
            oData.endTime = _endHours + ":" + _endMinutes;
            oData.gate = opts.yAxis[(_top - methods.getBaseEl($("#" + oData.flightDynamicId)).offset().top) / opts.stepHeight - opts.holdCount];

            ui.helper.data('o', oData);
            ui.helper.data('modified', true);


            if (opts.promptHandler) opts.promptHandler(oData, event);
        },
        data: [],
        renderCss: ".airplane-wrapper",
        dataExt: { minWidth: '30px', zIndex: 91, draggable: true }
    };
}))
(jQuery);
