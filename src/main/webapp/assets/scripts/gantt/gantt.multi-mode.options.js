var options = {
    ajaxUrl: './assets/datas/T_DynamicFlightData_4_TEST.json', ajaxParams: {},
    resizableHandler: false, draggableAxis: 'y',
    multi: true, collisionThresholds: 0, collisionDetectionHandler: function (event, ui) {
        var _this = this, gate = $.fn.gantt('getGate', ui.helper.data('o'), ui.helper.offset().top, _this);

        if (overlaps) {
            // 找到当前y-axis下的所有元素
            $(".airplane-wrapper > div").filter(function () {
                return /*$(this).data('o').gate == gate &&*/ $(this).data('uid') != ui.helper.data('uid');
            }).each(function () {
                if (overlaps(ui.helper, $(this))) {
                    // 利用元素的uid进行关联引用
                    var currentCollisionRefs = ui.helper.data('collisionRefs') || [], collisionRefs = $(this).data('collisionRefs') || [];
                    if (!_.contains(collisionRefs, ui.helper.data('uid') + "(" + ui.helper.data('code') + ")")) collisionRefs.push(ui.helper.data('uid') + "(" + ui.helper.data('code') + ")");
                    var yAxisCell = $(".gantt-left-panel > div[_value=" + gate[0] + "]"),
                        outerRow = $("div[_rowval=" + (gate[0] + 1) + "]"), innerRow = $("div[_rowindex=" + (gate[0] + 1) + "]"),
                        yAxisCellHeight = parseInt(yAxisCell.css("height").replace('px', '')),
                        finalHeightStr = yAxisCellHeight + _this.stepHeight + "px", currentOffset = $(this).offset(),
                        sub = yAxisCell.data('sub') === undefined ? 1 : yAxisCell.data('sub');

                    if (!_.contains(currentCollisionRefs, $(this).data('uid') + "(" + $(this).data('code') + ")")) {
                        currentCollisionRefs.push($(this).data('uid') + "(" + $(this).data('code') + ")");

                        //拿到其height及line-height值
                        yAxisCell.css({'height': finalHeightStr, 'line-height': finalHeightStr}).data('sub', ++sub);
                        outerRow.css({'height': finalHeightStr, 'line-height': finalHeightStr});
                        innerRow.css({'height': finalHeightStr, 'line-height': finalHeightStr});


                        // 得到offset.top大于当前的元素增加opts.stepHeight
                        $(".airplane-wrapper > div").filter(function () {
                            return $(this).offset().top > ui.helper.offset().top
                        }).each(function () {
                            $(this).offset({top: $(this).offset().top += _this.stepHeight});
                        });
                        ui.helper.offset({top: currentOffset.top + _this.stepHeight});
                    } else {
                        ui.helper.offset({
                            top: ui.helper.data('o-offset').top
                        });
                    }
                    yAxisCell.data('sub', parseInt(yAxisCell.css('height').replace('px', '')) / _this.stepHeight);
                    ui.helper.data('collisionRefs', currentCollisionRefs);
                    $(this).data('collisionRefs', collisionRefs);
                } else {
                    var currentCollisionRefs = ui.helper.data('collisionRefs') || [], collisionRefs = $(this).data('collisionRefs') || [];
                    if (_.contains(currentCollisionRefs, $(this).data('uid') + "(" + $(this).data('code') + ")")) {
                        ui.helper.data('collisionRefs', _.without(currentCollisionRefs, $(this).data('uid') + "(" + $(this).data('code') + ")"));
                        $(this).data('collisionRefs', _.without(collisionRefs, ui.helper.data('uid') + "(" + ui.helper.data('code') + ")"));
                    }
                }
            });

        }

    },
    draggableHandler: function () {
        var opts = this;
        return {
            containment: ".innerDetail", axis: opts.draggableAxis,
            start: function (event, ui) {
                ui.helper.data({
                    'o-gate': ui.helper.data('o').gate,
                    'o-offset': ui.helper.offset()
                });
            }, drag: function () {
                if (layer) layer.closeAll('page');
            }, stop: function (event, ui) {
                var offset = $(this).offset(), yPos = offset.top, oData = ui.helper.data('o'), expectedGate = oData.expectedGate,
                    step = Math.floor((yPos - $.fn.gantt('getBaseEl').offset().top) / opts.stepHeight);

                $(this).animate({
                    'top': "-=" + (yPos - ((step * opts.stepHeight) + $.fn.gantt('getBaseEl').offset().top)),
                    'height': opts.stepHeight * expectedGate, 'line-height': opts.stepHeight * expectedGate
                }, 300);

                setTimeout(function () {
                    //如果可以拉伸则计算时间
                    if (opts.resizableHandler && opts.getTime)
                        opts.getTime(event, ui);
                    else //否则只改变yAxis值
                        opts.changeYAxisValue(event, ui);

                    if (opts.collisionDetectionHandler)
                        opts.collisionDetectionHandler(event, ui);

                    var originGate = _.isArray(ui.helper.data('o-gate')) ? ui.helper.data('o-gate')[0] : ui.helper.data('o-gate'),
                        originOffset = ui.helper.data('o-offset'),
                        originYAxisCell = $("div.gantt-left-panel > div[_value=" + originGate + "]"),
                        originSub = originYAxisCell.data('sub') === undefined ? 1 : originYAxisCell.data('sub'),
                        otherEls = $(".airplane-wrapper > div").filter(function () {
                            return $(this).data('o').gate && $(this).data('o').gate[0] === originGate && $(this).offset().top === originOffset.top;
                        });


                    if (originSub > 1 && otherEls.length === 0) {
                        var outerRow = $("div[_rowval=" + (originGate + 1) + "]"), innerRow = $("div[_rowindex=" + (originGate + 1) + "]"),
                            finalHeightStr = (originSub - 1) * opts.stepHeight + 'px';
                        originYAxisCell.css({'height': finalHeightStr, 'line-height': finalHeightStr}).data({sub: originSub - 1});
                        outerRow.css({'height': finalHeightStr, 'line-height': finalHeightStr});
                        innerRow.css({'height': finalHeightStr, 'line-height': finalHeightStr});
                        $(".airplane-wrapper > div").filter(function () {
                            return $(this).offset().top > originOffset.top
                        }).each(function () {
                            $(this).offset({top: $(this).offset().top -= opts.stepHeight});
                        });

                        console.log('gate:', originGate, ', sub:', originYAxisCell.data('sub'));
                    }

                }, 500);
            }
        }
    },
    changeYAxisValue: function (event, ui) {
        var oData = ui.helper.data('o'), opts = this;
        oData.gate = $.fn.gantt('getGate', oData, ui.helper.offset().top, opts);
        ui.helper.data({'o': oData, 'modified': true}).animate({'height': opts.stepHeight, 'line-height': opts.stepHeight}, 100);
        if (layer) {
            var tipHtmlArray = [];
            tipHtmlArray.$push("航&nbsp;&nbsp;班&nbsp;&nbsp;号：").$push(oData.code).$push("<br>机　　位&nbsp;：")
                .$push($utils.superTrim(oData.gate, '未分配'))
                .$push("<br/>起始时间&nbsp;：").$push(oData.startDate).$push(" ").$push(oData.startTime).$push("<br/>结束时间&nbsp;：")
                .$push(oData.endDate).$push(" ").$push(oData.endTime)
                .$push("<br/>起&nbsp;&nbsp;飞&nbsp;&nbsp;地：").$push("WUH").$push("<br/>到&nbsp;&nbsp;达&nbsp;&nbsp;地：").$push("BEI");

            layer.tips(tipHtmlArray.join(""), ui.helper, {
                tips: [1]
            });

            tipHtmlArray.$clear();
        }
    }
};