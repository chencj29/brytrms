/**
 * Created by xiaopo on 15/12/14.
 */
var modifyDataTable, copyLongPlanHtml, modelName = (typeof(modelName) != "undefined") ? modelName : "", width = (typeof(width) != "undefined") ? width : "500px";
$(function () {
	
	//copyLongPlanHtml = $("#copyLongPlan").clone().html();
	
    // init datatables
    modifyDataTable = $("#modifyDataTable").DataTable({
        select: 'single',
        scrollCollapse: true,
        scrollX: true,
        buttons: [{
            text: '<i class="fa fa-plus"></i>&nbsp;添加',
            action: function (evt, dt, node, config) {
            	editLongPlan();
            }
        }, {
            text: '<i class="fa fa-check-circle"></i>&nbsp;应用',
            action: function (evt, dt, node, config) {
            	divideLongPlan();
            }
        }, {
        	text: '<i class="fa fa-files-o"></i>&nbsp;复制',
        	action: function (evt, dt, node, config) {
        		// 点击复制后将前台的查询条件传给后台，如果后台能够查询到数据，那么弹出框填写开始时间、结束时间、年份、类型
        		//var params = {};
        		//$(".datatable_search_input").each(function() {
        		//	var name = $(this).attr("name");
        		//	var value = $(this).val();
        		//	if (name != "" && value != "") {
        		//		params[name] = value;
        		//	}
        		//});
        		//console.log(params);
        		//$.post("/a/ams/longplan/preCopy", params, function(resp) {
        		//	if (resp.code == 1) {
        		//		openLongPlanCopyDialog(params);
        		//		console.log("存在记录，可以复制");
        		//	} else {
        		//		console.log("不存在记录，不能复制");
        		//	}
        		//});
				openLongPlanCopyDialog();
        	}
        }, {
         	text: '<i class="fa fa-search"></i>&nbsp;查询', 
         	action: function (evt, dt, node, config) {
         		$(".datatable_search_input").each(function() {
         			modifyDataTable.columns($(this).attr("_index")).search($(this).val());
         		});
         		modifyDataTable.draw();
         	}
        },  extraButtons.collections, extraButtons.colvis],
        columnDefs: [{
            targets: columns.length,
            render: function (data, type, row, dt) {
                return "<a href='javascript:void(0);' data-row-index='" + dt.row
                    + "' name='modifyBtn'>编辑</a> &nbsp;&nbsp;"
                    + "<a href='javascript:void(0);' data-row-index='" + dt.row
                    + "' name='deleteBtn'>删除</a>";
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
        editLongPlan(data);
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

    //refresh
    $("#refresh_datatbles").click(function () {
        modifyDataTable.ajax.reload();
    });
    
    // FIXME 显示查询框
    var searchElIndex = 0;
    $('#modifyDataTable thead:eq(1) td').each(function () {
        var name = $(this).attr("name");
        $(this).html('<input type="text" class="datatable_search_input" name="'+name+'" _index="' + searchElIndex++ + '" style="width: 100%;"/>');
    });
    
    // FIXME 绑定回车查询事件
    $("#modifyDataTable thead:eq(1) td input[type=text]").bind('keypress',function(event){
        if (event.keyCode == "13") {
        	modifyDataTable.columns($(this).attr("_index")).search($(this).val()).draw();
        }
    });
});

function editLongPlan(data) {
	var url = "";
	if (data != null) {
		url = '/a/ams/longplan/preEdit?id='+data.id;
	} else {
		url = '/a/ams/longplan/preEdit';
	}
	var formStr = $("#longPlanForm").html();
	if (formStr && formStr.length > 0) {
		$utils.ajaxLoadFragments(url, function (data) {
			$("#modifyDataTable_wrapper .row:first").next().remove();
			$("#modifyDataTable_wrapper .row:first").after(data);
		});
	} else {
		$utils.ajaxLoadFragments(url, function (data) {
			$("#modifyDataTable_wrapper .row:first").after(data);
		});
	}
}

function openLongPlanCopyDialog() {
	var index = layer.open({
		title : "长期计划复制",
		type : 1, closeBtn : 1, shift : 2,
		area : [ "410px", "400px" ],
		shadeClose : false,
		content : template('copyLongPlanTpl')({}),
		btn : [ '保存', '取消' ],
		yes : function() {
			var form = $("form[name='saveModifyForm']");
			var copyParams = form.serializeObject();
			var omg = [];
			// omg.$push(copyParams);

            for (var i = 0; i < copyParams.year.length; i++) {
                omg.push({
                    year: copyParams.year[i],
                    type: copyParams.type[i]
                });
            }

			$.ajax({
				url: form.attr("action"), type: "post", data: JSON.stringify(omg), dataType: "json",
				contentType: 'application/json;charset=utf-8', success: function (resp) {
					if (resp && resp.code == 1) {
						layer.msg("复制成功");
						layer.close(index);
						modifyDataTable.ajax.reload();
					} else {
						layer.alert(resp.message);
						layer.close(index);
					}
				}
			})
		}
	});
}

/**
 * 执行长期计划拆分操作
 * 应用是需要指定具体日期，否则全部拆分会出现系统卡死问题
 */
function divideLongPlan() {

    var index = layer.open({
        title : "长期计划应用",
        type : 1, closeBtn : 1, shift : 2,
        area : [ "410px", "170px" ],
        shadeClose : false,
        content : template('divideLongPlanTpl')({}),
        btn : [ '保存', '取消' ],
        yes : function() {
            var form = $("form[name='divideModifyForm']");
            var divideParams = form.serializeObject();
            $.ajax({
                url: form.attr("action"), type: "post", data: divideParams, dataType: "json",
                success: function (resp) {
                    if (resp && resp.code == 1) {
                        layer.close(index);
                        layer.msg("应用成功！");
                        modifyDataTable.ajax.reload();
                    } else {
                        layer.close(index);
                        layer.alert(resp.message);
                    }
                }
            });
        }
    });


	/*$.ajax({
		type: "post",
		url: "/a/ams/longplan/divide",
		success: function(data) {
			layer.msg(data.message);
		}
	});*/
}

/**
 * 新增或修改航空公司信息
 * 
 * @param data
 */

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