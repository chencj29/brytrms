var columns = [{data: "configName"}, {data: "configType"}, {data: "monitorClass"}, {data: "actionService"}, {data: "messageTpl"}],
    itemColumns = [
        {data: 'fieldName'}, {data: 'fieldCode'}, {data: 'conditionName'}, {data: 'conditionCode'}, {data: 'thresholdValue'}
    ], columnDefs = [{
        targets: 1, render: function (data, type, row, dt) {
            return dictType.getLabel(data);
        }
    }, {
        targets: 2, render: function (data, type, row, dt) {
            return _.last(data.split("."));
        }
    }, {
        targets: columns.length, render: function (data, type, row, dt) {
            return "<a href='javascript:void(0);' data-row-index='" + dt.row + "' name='modifyBtn'>编辑</a> &nbsp;&nbsp;"
                + "<a href='javascript:void(0);' data-row-index='" + dt.row + "' name='deleteBtn'>删除</a>";
        }
    }], itemColumnDefs = [], configTable, itemTable, dictType = new sysDictUtils("warn_remind_config", "config_type"), warnOperations = {
        "@=": "前置时间", "=@": "后置时间", ">=": "大于等于", "<=": "小于等于", "=": "等于", "!=": "不等于"
    }, previousSelectId = null;

$(function () {
    var monitorClasses = $utils._loadFragments({url: './monitor-list', method: 'post', dataType: 'json'}),
        actionServices = $utils._loadFragments({url: './service-list', method: 'post', dataType: 'json'});

    configTable = $('#configTable').DataTable({
        select: "single", scrollCollapse: true, scrollX: true, processing: true, serverSide: true, ajax: {url: "./list"},
        columns: columns, columnDefs: columnDefs, buttons: [{
            text: '<i class="fa fa-plus-circle" ></i>&nbsp;增加',
            action: function (evt, dt, node, config) {
                var selector = "div[name=configFormContainer]";
                if ($(selector).length != 0) $(selector).remove();
                $utils.ajaxLoadFragments('/assets/fragments/sys/monitor/configForm.html', function (data) {
                    var _data = $(data).data();
                    $(_data["selector"]).after(template.compile(data)({
                        config: {}, monitorClasses: monitorClasses, actionServices: actionServices,
                        monitorClassName: "", actionServiceName: "", type: 'new'
                    }));
                });
            }
        }]
    }).on('select', function (evt, dt, type, indexes) {

        var selectedData = dt.row(indexes).data(), path = './item-list?config_id=' + selectedData.id;
        if (previousSelectId !== selectedData.id) {
            itemTable.settings({serverSide: true}).ajax.url(path).load().draw();
            $($("div[name=itemFormContainer]").data('selector')).next().remove();
            previousSelectId = selectedData.id;
        }

    });

    itemTable = $("#itemTable").DataTable({
        select: 'single', scrollCollapse: true, scrollX: true, processing: true, columns: itemColumns, columnDefs: itemColumnDefs,
        buttons: [{
            text: '<i class="fa fa-plus-circle" ></i>&nbsp;增加规则',
            action: function (evt, dt, node, config) {
                var configTableSelectedRecord = configTable.row({selected: true});
                if (configTableSelectedRecord.count() === 1) {
                    var selectData = configTableSelectedRecord.data(), templateObject = {
                        config: {id: selectData.id, type: selectData.configType, class: selectData.monitorClass}, datas: [],
                        fields: monitorClasses[selectData.monitorClass].fields, operations: warnOperations
                    }, selector = "div[name=itemFormContainer]";
                    if (selectData.configType === 'INSERT' || selectData.configType === 'DELETE') {
                        layer.alert('当前选中的记录无需添加规则！');
                        return false;
                    }

                    if (selectData.configType === 'TIMER') {
                        //构造模板数据
                        _.each(itemTable.data(), function (item) {
                            templateObject.datas.push(item)
                        });
                    }

                    if ($(selector).length != 0) $(selector).remove();
                    $utils.ajaxLoadFragments('/assets/fragments/sys/monitor/itemForm4' + selectData.configType + '.html', function (data) {
                        var _data = $(data).data();
                        $(_data["selector"]).after(template.compile(data)({item: templateObject}));
                    });

                } else layer.msg("请先选择一条配置信息再进行操作！", {icon: 2});
            }
        }, {
            text: '<i class="fa fa-times" ></i>&nbsp;清除规则',
            action: function (evt, dt, node, config) {
                var selectedData = configTable.row({selected: true}).data();
                if (selectedData && itemTable.data().length !== 0) {
                    layer.confirm("你确定清空经配置项下的所有规则列表吗？", {
                        icon: 3, title: "清除规则"
                    }, function () {
                        $utils._loadFragments({
                            url: './delete?id=' + selectedData.id + "&type=d",
                            dataType: 'JSON', callbackFn: function (data) {
                                if (data.code === 1) {
                                    layer.msg('清除成功！');
                                    $($("div[name=itemFormContainer]").data('selector')).next().remove();
                                    itemTable.ajax.reload();
                                } else {
                                    layer.alert('清除失败：' + data.message);
                                }
                            }
                        })
                    });
                }
            }
        }]
    });

    $('body').on('click', 'a[name=modifyBtn]', function (e) {
        var tdata = configTable.data()[$(this).data()['rowIndex']], selector = "div[name=configFormContainer]";
        if ($(selector).length != 0) $(selector).remove();

        //根据ID查询数据
        $utils.ajaxLoadFragments('/assets/fragments/sys/monitor/configForm.html', function (data) {
            var _data = $(data).data(), config = $utils._loadFragments({
                url: './get?id=' + tdata.id, method: 'post', dataType: 'json'
            }), templateObject = {
                config: config, monitorClasses: monitorClasses, actionServices: actionServices,
                monitorClassName: monitorClasses[config.monitorClass].desc,
                actionServiceName: actionServices[config.actionService][0], type: 'modify'
            };

            $(_data["selector"]).after(template.compile(data)(templateObject));
        });

    }).on('click', 'a[name=deleteBtn]', function (e) {
        var data = configTable.data()[$(this).data()['rowIndex']];
        layer.confirm("确定删除该条数据（该配置下的所有规则将一并删除）？", {
            btn: ["确定", "取消"]
        }, function () {
            $.post("./delete", {"id": data.id, "type": "c"}, function (resp) {
                if (resp && resp.code == 1) {
                    layer.msg("删除成功");
                    configTable.ajax.reload();
                    $($("div[name=itemFormContainer]").data('selector')).next().remove();
                    $($("div[name=configFormContainer]").data('selector')).next().remove();
                    itemTable.clear().draw();
                    previousSelectId = null;
                } else {
                    layer.alert(resp.message);
                }
            })
        });
    });
});