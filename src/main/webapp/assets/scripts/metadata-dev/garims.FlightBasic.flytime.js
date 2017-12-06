/**
 * Created by xiaopo on 15/12/14.
 */
var modifyFormHtml;
modelName = (typeof(modelName) != "undefined") ? modelName : "";
width = (typeof(width) != "undefined") ? width : "500px";
$(function () {
    // validation
    modifyFormHtml = $("#modifyForm").clone().html();
    $("#modifyForm").remove();

    // init datatables
    modifyDataTable = $("#modifyDataTable").DataTable({
        select: 'single',
        scrollCollapse: true,
        scrollX: true,
        buttons: [{
            text: '<i class="fa fa-plus"></i>&nbsp;添加' + modelName,
            action: function (evt, dt, node, config) {
                saveOrUpdateForMain();
            }
        }, extraButtons.collections, extraButtons.colvis],
        columnDefs: [{
            targets: columns.length,
            render: function (data, type, row, dt) {
                return "<a href='javascript:void(0);' data-row-index='" + dt.row
                    + "' name='modifyBtn'>编辑</a> &nbsp;&nbsp;"
                    + "<a href='javascript:void(0);' data-row-index='" + dt.row
                    + "' name='deleteBtn'>删除</a>";
            }
        }, {
            "targets": 0,
            "render": function (data, type, row) {
                try {
                    return strNull2Blank(row.startSiteCode) + "&nbsp;" + strNull2Blank(row.startSite);
                } catch (e) {
                }
            }
        }, {
            "targets": 1,
            "render": function (data, type, row) {
                try {
                    return strNull2Blank(row.endSiteCode) + "&nbsp;" + strNull2Blank(row.endSite);
                } catch (e) {
                }
            }
        }],
        "processing": true,
        "serverSide": true,
        "ajax": "list",
        "columns": columns
    });


    // modify & delete
    $('body').on('click', 'a[name=modifyBtn]', function (e) {
        var data = modifyDataTable.data()[$(this).data()['rowIndex']];
        saveOrUpdateForMain(data);
    }).on('click', 'a[name=deleteBtn]', function (e) {
        var data = modifyDataTable.data()[$(this).data()['rowIndex']];
        layer.confirm("确定删除该条数据?", {
            btn: ["确定", "取消"]
        }, function () {
            $.post("delete", {"id": data.id}, function (resp) {
                if (resp && resp.code == 1) {
                    layer.msg("删除成功");
                    modifyDataTable.ajax.reload();
                } else {
                    layer.alert(resp.message);
                }
            })
        });
    });

    // FIXME 显示查询框
    var searchElIndex = 0;
    $('#modifyDataTable thead:eq(1) td').each(function () {
        var name = $(this).text();
        $(this).html('<input type="text" class="datatable_search_input" name="' + name + '" _index="' + searchElIndex++ + '" style="width: 100%;"/>');
    });

    // FIXME 绑定回车查询事件
    $("#modifyDataTable thead:eq(1) td input[type=text]").bind('keypress', function (event) {
        if (event.keyCode == "13") {
            modifyDataTable.columns($(this).attr("_index")).search($(this).val()).draw();
        }
    });

    //refresh
    $("#refresh_datatbles").click(function () {
    	modifyDataTable.ajax.reload();
    });
});

/**
 * 新增或修改航空公司信息
 * @param data
 */
function saveOrUpdateForMain(data) {
    var title = "新增";
    if (data && $(data).length > 0)
        title = "修改";
    title = title + modelName;
    var index = layer.open({
        title: title,
        type: 1,
        closeBtn: 1,
        shift: 2,
        area: width,
        shadeClose: false,
        content: modifyFormHtml,
        success: function () {
            $("form[name='saveModifyForm']").validationEngine({
                showOneMessage: true,
                promptPosition: 'bottomLeft'
            });
            //修改
            if (data && $(data).length > 0) {
                $("form[name='saveModifyForm']").children("input[name='id']").val(data.id);
                //
                $utils.initFormHTML("form[name='saveModifyForm']", data);
            }
            // extend callback
            try{
                layerShowSuccessCallBack(data);
            }catch(e){}
            // init sysdict name
            try{
                setSelectName();
            }catch(e){}
        },
        btn: ['保存', '取消'],
        yes: function () {
            var valid = $("form[name='saveModifyForm']").validationEngine("validate");
            if (valid) {
                var form = $("form[name='saveModifyForm']");
                $.post(form.attr("action"), form.serialize(), function (resp) {
                    if (resp && resp.code == 1) {
                        layer.close(index);
                        modifyDataTable.ajax.reload();
                    } else {
                        layer.alert(resp.message);
                        return false;
                    }
                });
            }
        }
    });


    initDetailWidget();
}


// 适用于select 冗余例如:powerTypeCode powerTypeName select
$(function () {
    $("body").on("change","select.dict", function () {
        setSelectName();
    });
    setSelectName();
});

//
function setSelectName() {
    $("select.dict").each(function () {
        try{
            var text = $(this).find("option:selected").text();
            var code = $(this).attr("name");
            var name = code.substring(0, code.lastIndexOf("Code")) + "Name";
            $("input[name='"+name+"']").val(text);
        }catch(e){}
    });
}

function initDetailWidget() {
    $("#startSiteCode, #endSiteCode").select2({
        allowClear: true,
        language: "zh-CN",
        placeholder: "请选择"
    }).change(function () {
        $("input[name='" + $(this).attr("hiddenInputName") + "']").val($(this).find("option:selected").attr("hiddenInputValue"));
    });
}