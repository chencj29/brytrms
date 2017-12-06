$(function() {
	
	initWidget();

});

/**
 * 初始化所有控件
 */
function initWidget() {
	
	var id = $("#record_id").val();
	var editData = null;
	
	// 机型
	initAircraftTypeCodeWidget();
	
	// 计划类型
	initTypeWidget();
	
	// 进出港类型
	initInoutType();
	
	// 航空公司
	initFlightCompanyCodeWidget();
	
	// 班期
	initPeriodWidget();
	
	// 航站，默认初始化4个待选的航站控件
	initStopPoint(4);
	
	// init getUp
	initGetUp();
	
}

/**
 * 初始化进出港类型
 */
function initInoutType() {
	$("#inoutTypeCode").select2({
		placeholder : "请选择进出港类型"
	});
}

/**
 * 初始化机型选择控件
 * 显示结果如A380
 * 选择后的结果是机型ID
 */
function initAircraftTypeCodeWidget() {
	$("#aircraftTypeCode").select2({
		placeholder : "请选择机型"
	});
}

/**
 * 初始化航空公司控件
 * 显示结果，航空公司名称
 * 选择后的结果是航空公司ID
 */
function initFlightCompanyCodeWidget() {
	$("#flightCompanyCode").select2({
		allowClear: true,
		placeholder : "请选择航空公司"
	}).change(function() {
		// flightCompanyName
		$("input[name='flightCompanyName']").val($(this).find("option:selected").attr("hiddenInputValue"));
	});
}

/**
 * 初始化计划类型控件
 * 显示结果 进港;出港
 * 选择后的结果是 J;C
 */
function initTypeWidget() {
	$("#type").select2({
		allowClear: true,
		placeholder : "请选择计划类型"
	});
}

/**
 * 初始化班期控件
 * Checkbox
 */
function initPeriodWidget() {
	$("#checkAllBtn").click(function() {
		if ($(this).is(":checked")) {
			$(".periodBox input[name='period']").prop("checked", true);
		} else {
			$(".periodBox input[name='period']").prop("checked", false);
		}
	});
}

/**
 * 初始化经停站
 * @param count
 */
function initStopPoint(count) {
	if ($("#stopPointBox").children().length == 0) {
		for (var i = 0; i < count; i++) {
			addSite(i);
		}
	} else {
		var index = $("#stopPointBox").children().length;
		for (var i = 0 ; i < index ; i++) {
			var prefix_name = "longPlanStopList["+i+"].";
			var stopPoint_t = $("#stopPointBox").children()[i];
			$(stopPoint_t).find("select[name='stopPointCode']").attr("name", prefix_name + "stopPointCode");
			$(stopPoint_t).find("input[name='stopPointSTime']").attr("name", prefix_name + "stopPointSTime");
			$(stopPoint_t).find("input[name='stopPointETime']").attr("name", prefix_name + "stopPointETime");
			
			$(stopPoint_t).find("select[name*='stopPointCode']").select2({
				allowClear: true,
				placeholder : "请选择航站"
			});
			
			$(stopPoint_t).find(".addLong-delete").show();
			
			$("#stopPointBox .index").each(function() {
				$(this).text($("#stopPointBox .index").index($(this)) + 1);
			});
		}
	}
}

function initGetUp() {
	$("#getUp").click(function() {
		$("#modifyDataTable_wrapper .row:first").next().remove();
	});
}

function addSiteDom() {
	var index = $("#stopPointBox").children().length;
	addSite(index);
}


/**
 * 添加航站
 */
function addSite(index) {
	var stopPoint_t = $("#stopPoint_templete").clone();
	$(stopPoint_t).removeAttr("id");
	$("#stopPointBox").append(stopPoint_t);
	$(stopPoint_t).show();
	// longPlanStopList[0].
	var prefix_name = "longPlanStopList["+index+"].";
	$(stopPoint_t).find("select[name='stopPointCode']").attr("name", prefix_name + "stopPointCode");
	$(stopPoint_t).find("input[name='stopPointName']").attr("name", prefix_name + "stopPointName");
	$(stopPoint_t).find("input[name='stopPointSTime']").attr("name", prefix_name + "stopPointSTime");
	$(stopPoint_t).find("input[name='stopPointETime']").attr("name", prefix_name + "stopPointETime");
	
	$(stopPoint_t).find("select[name*='stopPointCode']").select2({
		allowClear: true,
		placeholder : "请选择航站"
	}).change(function() {
		$("input[name='" + prefix_name + "stopPointName'").val($(this).find("option:selected").attr("hiddenInputValue"));
	});
	
	$(stopPoint_t).find(".addLong-delete").show();
	
	$("#stopPointBox .index").each(function() {
		$(this).text($("#stopPointBox .index").index($(this)) + 1);
	});
}

/**
 * 删除航站
 * @param obj
 */
function delSiteRow(obj) {
	$(obj).parent().parent().parent().remove();
	$("#stopPointBox .index").each(function() {
		$(this).text($("#stopPointBox .index").index($(this)) + 1);
	});
}

function formSubmit() {
	$("#longPlanForm").ajaxSubmit({    
		success: function() {
			layer.msg("保存成功");
			modifyDataTable.ajax.reload();
		}    
	});
}

function formSubmitAndContinue() {
	$("#longPlanForm").ajaxSubmit({
		success: function() {
			layer.msg("保存成功");
			modifyDataTable.ajax.reload();
			editLongPlan(null);
		}    
	});
}
