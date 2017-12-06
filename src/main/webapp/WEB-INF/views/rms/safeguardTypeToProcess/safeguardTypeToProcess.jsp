<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>保障类型保障过程关联</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">

    <div data-options="region:'west',split:true" title="保障类型" style="width:200px;">
        <ul class="easyui-tree" id="tree"></ul>
    </div>

    <div data-options="region:'center',border:false">
        <div id="p" class="easyui-panel" title="保障类型与保障过程对应关系" style="width:100%;height:100%;padding:10px;">
            <div class="easyui-layout" data-options="fit:true">
                <div data-options="region:'west',border:false" , style="width: 230px">
                    <div id="p222" class="easyui-panel" title="已选过程(拖动排序)" style="width:99%;height:99%;" fit="true">
                        <ul id="related" title="已选过程" style="width: 200px;height: 100%;padding-left: 0px;">
                        </ul>
                    </div>
                </div>
                <div data-options="region:'center',border:false">
                    <div class="easyui-layout" data-options="fit:true">
                        <div data-options="region:'west',border:false" , style="width: 60px">
                            <table style="height: 100%;width:100%;">
                                <tr>
                                    <td valign="middle" align="center">
                                        <div style="width: 100%;float:left;">
                                            <a id="add" href="#" class="easyui-linkbutton" data-options=""><<</a>
                                        </div>
                                        <div style="width: 100%;float:left;margin-top: 8px;">
                                            <a id="del" href="#" class="easyui-linkbutton" data-options="">>></a>
                                        </div>

                                    </td>
                                </tr>
                            </table>

                        </div>

                        <div data-options="region:'center',border:false">
                            <ul id="waitSelect" class="easyui-datalist" title="待选过程" style="width: 200px;height: 100%;">
                            </ul>
                        </div>
                    </div>


                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script src="${ctxAssets}/scripts/aui-artTemplate/dist/template-debug.js"></script>
<script src="${ctxAssets}/scripts/jQuery.blockUI/jquery.blockUI.min.js"></script>
<script>
    var moduleContext = "safeguardTypeToProcess", indicator, flagMove;//可拖动开关，默认关闭

    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [], selectedTypeTree = null;

    $(function () {
        var relateSelectIndex, waitSelect;

        indicator = $('<div class="indicator">>></div>').appendTo('body');

        <shiro:lacksPermission name="rms:safeguardTypeToProcess:insert">$("#add").linkbutton('disable');
        </shiro:lacksPermission>
        <shiro:lacksPermission name="rms:safeguardTypeToProcess:del">$("#del").linkbutton('disable');
        </shiro:lacksPermission>
        <shiro:hasPermission name="rms:safeguardTypeToProcess:edit">flagMove = true;
        </shiro:hasPermission>

        $('#tree').tree({
            url: '${ctx}/rms/safeguardType/getWholeTree',
            method: 'get',
            onClick: function (node) {
//				$("#related").datalist("reload", {"id": node.params.obj.id});
                $("#waitSelect").datalist("reload", {"id": node.params.obj.id});

                selectedTypeTree = node;

                // 展示已选
                showRelated(node.params.obj.id);
            }
        });


        $("#waitSelect").datalist({
            url: '${ctx}/rms/safeguardProcess/waitSelectedPorcess',
            queryParams: {"id": ""},
            textField: "safeguardProcessName",
            valueField: "id",
            onSelect: function (index, row) {
                waitSelect = index;
            }
        });

        $("#add").click(function () {
            if ($(this).linkbutton('options').disabled == false) {
                var waitRowSelected = $("#waitSelect").datalist("getSelected");
                if (!waitRowSelected) {
                    $.messager.alert('提示', '请选择右侧过程');
                    return;
                }
                $.post("${ctx}/rms/safeguardTypeToProcess/save", {"safeguardTypeId": selectedTypeTree.params.obj.id, "safeguardProcessId": waitRowSelected.id}, function (data) {
                    if (waitRowSelected) {
                        $("#waitSelect").datalist("deleteRow", waitSelect);
                        //
                        showRelated(selectedTypeTree.params.obj.id);
                    }
                });
            }
        });

        $("#del").click(function () {
            if ($(this).linkbutton('options').disabled == false) {
                var id = $(".drag-item").filter(".ct-selected").attr('value');
                if (!id) {
                    $.messager.alert('提示', '请选择左侧过程');
                    return;
                }
                $.post("${ctx}/rms/safeguardTypeToProcess/deleteByRelate", {"safeguardTypeId": selectedTypeTree.params.obj.id, "safeguardProcessId": id}, function (data) {
                    if (id) {
                        $("#waitSelect").datalist("reload");
                        //
                        showRelated(selectedTypeTree.params.obj.id);
                    }
                });
            }
        });
    });

    function showRelated(id) {
        $.getJSON(ctx + '/rms/safeguardProcess/selectedPorcess', {id: id}, function (data) {
            $("#related").html(template('related_template', {array: data}));
            initDrop();
        });
    }

    function initDrop() {
        $('.drag-item').edraggable({revert: true, deltaX: 0, deltaY: 0}).edroppable({
            onDragOver: function (e, source) {
                indicator.css({
                    display: 'block',
                    left: $(this).offset().left - 10,
                    top: $(this).offset().top + $(this).outerHeight() - 5
                });
            },
            onDragLeave: function (e, source) {
                indicator.hide();
            },
            onDrop: function (e, source) {
                $(source).insertAfter(this);
                indicator.hide();
                //获取 li 顺序存入数据库
                var sort = [], ids = [];
                $('.drag-item').each(function (inx, rec) {
                    // 保存排序
                    sort.push(inx);
                    ids.push($(rec).attr("value"));
                });

                // $.messager.progress({title: '请稍后', msg: '正在保存排序...'});
                $.blockUI({
                    css: {
                        border: 'none',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: .6,
                    },
                    message: '<h3>正在保存排序...</h3>'
                });
                //
                $.post(ctx + '/rms/safeguardTypeToProcess/updateSort', {sort: sort, ids: ids, typeId: selectedTypeTree.params.obj.id}, function (resp) {
                    $.unblockUI();
                }, 'json');
            }
        }).click(function () {
            $(this).addClass('ct-selected').siblings().removeClass('ct-selected');
        });

        if (!flagMove) $('.drag-item').edraggable('disable');
    }
</script>
<script id="related_template" type="text/html">
    {{each array as item i}}
    <li class="drag-item" value="{{item.id}}">{{item.safeguardProcessName}}</li>
    {{/each}}
</script>
<%--<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>--%>
</body>
</html>