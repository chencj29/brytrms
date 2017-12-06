/**
 * 返回表格描述数据
 */
function getDataGridDraggableAndVisibleMetaInfo ($element) {
    let firstRow = $element.find("thead > tr:first"),
        secondRow = $element.find("thead > tr:last");

    let descriptor = {
        id: $element.attr("id"),
        title: $element.attr("title"),
        complex: $element.attr("group") === 'true',
        columns: []
    };

    firstRow.find("th").each(function (index) {
        let row = $.extend({}, _buildRowData($(this)), { index: index, columns: [], uid: $uuid() });

        if (descriptor.complex) { // find sub columns
            var subColumns = secondRow.find("th[parent=" + row.field + "]");
            if (subColumns.length !== 0) {
                subColumns.each(function (index) {
                    row.columns.push($.extend({}, _buildRowData($(this)), { index: index, columns: [], uid: $uuid(), pid: row.uid }));
                });
            }
        }
        descriptor.columns.push(row);
    });
    return descriptor;
}

function _buildRowData ($element) {
    return {
        field: $element.attr("field"),
        text: $element.text(),
        visible: !$element.prop("hidden"),
        html: $element[0].outerHTML
    };
}

function _hiddenContentReplacer (result) {
    var _replaceHtmlCode = result.html;
    if (!result.visible) {
        if (result.html.includes("hidden=")) _replaceHtmlCode = result.html.replace(/hidden=".*?"/, "hidden=\"true\"");
        else _replaceHtmlCode = result.html.replace(/<th /, "<th hidden=\"true\" ");
    } else {
        if (result.html.includes("hidden=")) _replaceHtmlCode = result.html.replace(/hidden=".*?"/, "");
    }
    result.html = _replaceHtmlCode;
}

function loadDataGridContent () {
    dataGridMeta = JSON.parse(localStorage.getItem(currentUserId + "@" + moduleContext));
    let tableHtmlCode;
    if (!dataGridMeta) tableHtmlCode = template('datagridTpl')();
    else {
        let _tableHtml = [], firstRowHtml = [], lastRowHtml = [], lastRowHiddenHtml = [];
        _tableHtml.$push("<table id='").$push(dataGridMeta.id).$push("' title='").$push(dataGridMeta.title).$push("' adjust='true'  data-options='fitColumns:false, fit:true'");
        if (dataGridMeta.complex) _tableHtml.$push(" group='true' ");
        else _tableHtml.$push(" group='false' ");
        _tableHtml.$push("><thead>");
        firstRowHtml.$push("<tr>");
        lastRowHtml.$push("<tr>");

        dataGridMeta.columns.sort((a, b) => a.index - b.index);

        for (var column of dataGridMeta.columns) {
            firstRowHtml.$push(column.html);
            if (dataGridMeta.complex) {
                column.columns.sort((a, b) => a.index - b.index);
                for (var subColumn of column.columns) {
                    if (!subColumn.visible) lastRowHiddenHtml.push(subColumn.html);
                    else lastRowHtml.$push(subColumn.html);
                }
            }
        }
        firstRowHtml.$push("</tr>");

        // 将hidden的row放至最后
        for (var hiddenHtml of lastRowHiddenHtml) lastRowHtml.$push(hiddenHtml);

        lastRowHtml.$push("</tr>");
        _tableHtml.$push(firstRowHtml.join(""));
        if (dataGridMeta.complex) _tableHtml.$push(lastRowHtml.join(""));
        _tableHtml.$push("</thead></table>");

        tableHtmlCode = _tableHtml.join("");
    }
    $("#dgContainer").append(tableHtmlCode);
}

$(function () {
    // setTimeout(() => {
    // 加载「显示设置」弹出框
    $("#view-setting-dialog").dialog({
        href: ctxAssets + '/fragments/sys/viewSetting/view-setting.html', closed: true,
        onLoad: function () {
            if (!dataGridMeta) dataGridMeta = getDataGridDraggableAndVisibleMetaInfo($("#datagrid"));
            // 加载一级字段列表
            $("ul#firstLevelFieldList").html(template.compile($utils.ajaxLoadFragments(ctxAssets + '/fragments/sys/viewSetting/item-list.html'))({ columns: dataGridMeta.columns }));
            indicator = $(".indicator");
            initDrop();

            $("#btnSaveView").linkbutton({
                iconCls: 'icon-add', onClick: function () {
                    localStorage.setItem(currentUserId + "@" + moduleContext, JSON.stringify(dataGridMeta));
                    // 重载操作
                    $("#view-setting-dialog").dialog('close');
                    window.location.reload();
                }
            });

            $("#btnCancelView").linkbutton({
                iconCls: 'icon-cross', onClick: function () {
                    $("#view-setting-dialog").dialog('close');
                }
            });
        }
    });
    // }, 0);


    $(document).on('click', 'li.drag-item > input:checkbox', function () {
        var currentId = $(this).attr('value'), result = _(dataGridMeta.columns).find({ uid: currentId }),
            $parentEl = $(this).parents('ul');
        if (!result && dataGridMeta.complex)
            result = _(dataGridMeta.columns).chain().pluck('columns').reject(_.isEmpty).flatten().find(x => x.uid === currentId).value();

        if (!result) return false;

        result.visible = $(this).prop('checked');
        _hiddenContentReplacer(result);

        if (dataGridMeta.complex) { // 复杂表头, 特殊处理
            if (!result.visible) { // 取消勾选处理
                if ($parentEl.attr('id') === 'secondLevelFieldList') { // 当二级表头全部取消勾选时, 相应地取消其一级表头的勾选
                    var parent = _(dataGridMeta.columns).find(x => x.uid === result.pid),
                        checkedCount = $parentEl.find('li.drag-item > input:checked').length;
                    if (checkedCount === 0) {
                        parent.visible = false;
                        _hiddenContentReplacer(parent);
                        $("input[value=" + result.pid + "]:checkbox").prop('checked', false);
                    } else {
                        parent.html = $(parent.html).attr("colspan", checkedCount)[0].outerHTML;
                    }
                } else { // 当一级表头取消勾选的时候, 相应的取消所有二级表头的勾选
                    result.columns.forEach(x => {
                        x.visible = false;
                        _hiddenContentReplacer(x);
                        $("input[value=" + x.uid + "]:checkbox").prop('checked', false);
                    });
                }
            } else {  // 勾选处理
                if ($parentEl.attr('id') === 'secondLevelFieldList') {
                    var parent = _(dataGridMeta.columns).find(x => x.uid === result.pid),
                        checkedCount = $parentEl.find('li.drag-item > input:checked').length;
                    parent.visible = true;
                    _hiddenContentReplacer(parent);
                    $("input[value=" + result.pid + "]:checkbox").prop('checked', true);
                    parent.html = $(parent.html).attr("colspan", checkedCount)[0].outerHTML;
                } else {
                    result.columns.forEach(x => {
                        x.visible = true;
                        _hiddenContentReplacer(result);
                        $("input[value=" + x.uid + "]:checkbox").prop('checked', true);
                    });
                }
            }
        }
    });
});

function initDrop () {
    $('.drag-item').draggable({ revert: true, deltaX: 0, deltaY: 0, axis: 'v' }).droppable(droppableProperties).click(function () {
        $(this).addClass('ct-selected').siblings().removeClass('ct-selected');

        var currentMetaInfo = _(dataGridMeta.columns).find(item => item.uid === $(this).attr('value'));
        if (currentMetaInfo && currentMetaInfo.columns.length !== 0) {
            $("ul#secondLevelFieldList").html(template.compile($utils.ajaxLoadFragments(ctxAssets + '/fragments/sys/viewSetting/item-list.html'))({ columns: currentMetaInfo.columns }));
            $("ul#secondLevelFieldList").find("li.drag-item").edraggable(draggableProperties).edroppable($.extend({}, droppableProperties, {
                onDragOver: function (e, source) {
                    indicator.css({ left: $(this).position().left + 204, top: $(this).position().top + $(this).outerHeight(), zIndex: 99999 }).show();
                }
            }));
        }
        else $("ul#secondLevelFieldList").html("");
    });
}

var draggableProperties = { revert: true, deltaX: 0, deltaY: 0, axis: 'v' },
    droppableProperties = {
        onDragOver: function (e, source) {
            indicator.css({ left: $(this).position().left, top: $(this).position().top + $(this).outerHeight(), zIndex: 99999 }).show();
        },
        onDragLeave: function (e, source) {
            indicator.hide();
        },
        onDrop: function (e, source) {
            $(source).insertAfter(this);
            indicator.hide();

            var columns;
            if ($(source).parents('ul').attr("id") === 'firstLevelFieldList') {
                columns = dataGridMeta.columns;
            } else {
                columns = _(dataGridMeta.columns).find({
                    uid: _(dataGridMeta.columns).chain()
                        .pluck('columns').reject(_.isEmpty).flatten().find(x => x.uid === $(this).attr("value")).value().pid
                }).columns;
            }

            $(source).parents('ul').find('li.drag-item').each(function (idx) {
                _(columns).find({ uid: $(this).attr('value') }).index = idx;
            });
        }
    },
    dataGridViewSettingToolbar = {
        text: '显示设置', iconCls: 'icon-2012080404391', handler: function () {
            $("#view-setting-dialog").dialog('open');
        }
    }, indicator;