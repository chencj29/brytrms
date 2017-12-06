var fnMap = {
    inoutTypeCode: function (val, row, i) {
        if (!!row.inoutTypeCode && !!row.inoutTypeCode2) {
            return "连班";
        } else if (!!row.inoutTypeCode) {
            return "进港";
        } else if (!!row.inoutTypeCode2) {
            return "出港";
        } else {
            return "";
        }
    },
    mergeFlightNum: function (val, row, i) {
        if (row.flightNum) {
            return row.flightCompanyCode + row.flightNum;
        } else {
            return "";
        }
    },
    mergeFlightNum2: function (val, row, i) {
        if (row.flightNum2) {
            return row.flightCompanyCode2 + row.flightNum2;
        } else {
            return "";
        }
    }
};

function dateFmt(v, r, i, f) {
    return !v ? "" : moment(v).format("YYYY-MM-DD");
}

function formatFlightNum(val, row, index) {
    if (row.flightNum != null && row.flightNum != '') {
        return row.flightCompanyCode + row.flightNum;
    } else {
        return "";
    }
}

function formatFlightNum2(val, row, index) {
    if (row.flightNum2 != null && row.flightNum2 != '') {
        return row.flightCompanyCode2 + row.flightNum2;
    } else {
        return "";
    }
}

function formatFn(val, row, index) {
    return fnMap[this.field] ? fnMap[this.field](val, row, index) : val;
}

function formatDateTime(val, row, index) {
    if (val != null && val != '') {
        return moment(val).format("YYYY-MM-DD HH:mm");
    } else {
        return "";
    }
}

function globalPlaceFmt(val, row, index) {
    var codeName = this.field;
    if (codeName.indexOf("Code") > 0) {
        if (row[codeName]) {
            return row[codeName] + " " + row[codeName.replace("Code", "Name")];
        }
    } else {
        if (codeName.indexOf("ext") > 0) {
            var num = parseInt(codeName.replace("ext", ""));
            return row[codeName] + " " + row[codeName.replace(num, num + 1)];
        }
    }
    return val;
}


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

function formatDate(v, r) {
    return v ? moment(v).format('YYYY-MM-DD') : "";
}

var flightCompanyCodeDates, addressDatas, flightNatureDatas, flightAttrDatas;
if (!flightCompanyCodeDates) {
    $.ajax({
        url: ctx + '/ams/flightCompanyInfo/jsonData',
        dataType: "json",
        async: false,
        success: function (data) {
            flightCompanyCodeDates = _.map(data, function (o) {
                var result = {};
                result.twoCode = o.twoCode;
                result.tCodechName = o.twoCode + o.chineseName;
                return result;
            });
        }
    });
}

if (!addressDatas) {
    $.ajax({
        url: ctx + '/ams/amsAddress/jsonData',
        dataType: "json",
        async: false,
        success: function (data) {
            addressDatas = _.map(data, function (o) {
                var result = {};
                result.threeCode = o.threeCode;
                result.chName = o.chName;
                result.tCodechName = o.chName + o.threeCode;
                return result;
            });
        }
    });
}

if (!flightNatureDatas) {
    $.ajax({
        url: ctx + '/ams/flightNature/jsonData',
        dataType: "json",
        async: false,
        success: function (data) {
            flightNatureDatas = _.map(data, function (o) {
                var result = {};
                result.natureNum = o.natureNum;
                result.natureName = o.natureName;
                result.tNumName = o.natureName + o.natureNum;
                return result;
            });
        }
    });
}

if (!flightAttrDatas) {
    $.ajax({
        url: ctx + '/ams/flightProperties/jsonData',
        dataType: "json",
        async: false,
        success: function (data) {
            flightAttrDatas = _.map(data, function (o) {
                var result = {};
                result.propertiesNo = o.propertiesNo;
                result.propertiesName = o.propertiesName;
                result.tNoName = o.propertiesName + o.propertiesNo;
                return result;
            });
        }
    });
}

$.fn.combobox.defaults.filter = function (q, row) {
    var opts = $(this).combobox('options');
    return row[opts.textField].toUpperCase().indexOf(q.toUpperCase()) >= 0;
}

var nameMap = {
    机位: "placeNum",
    到港门: "arrivalGate",
    行李转盘: "carousel",
    值机柜台: "checkingCounter",
    滑槽: "slideCoast",
    登机口: "boardingGate",
    候机厅: "departureHall",
    安检口: "securityCheckin"
};


$(function () {
    //给时间控件加上样式
    $("#fdHistoryListtb #planDate_begin").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
        WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '#F{$dp.$D(\'planDate_end\')}'});
    });
    $("#fdHistoryListtb #planDate_end").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
        WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: '#F{$dp.$D(\'planDate_begin\')}'});
    });

    $("#inoutTypeCode").combobox({
        panelHeight: 'auto',
        panelMaxHeight: '200',
        data: [{k: "J", v: "进港"}, {k: "C", v: "出港"}],
        valueField: 'k',
        textField: 'v'
    });
    //拿到航空公司数据
    $("#flightCompanyName").combobox({
        panelHeight: 'auto',
        panelMaxHeight: '200',
        data: flightCompanyCodeDates,
        valueField: 'twoCode',
        textField: 'tCodechName'
    });

    //拿到地名数据
    $("#addr").combobox({
        data: addressDatas,
        valueField: 'threeCode',
        textField: 'tCodechName',
        panelHeight: 'auto',
        panelMaxHeight: '200'
    });

    $("#flightNatureCode").combobox({
        data: flightNatureDatas,
        valueField: 'natureNum',
        textField: 'tNumName',
        panelHeight: 'auto',
        panelMaxHeight: '200'
    });

    $("#flightAttrCode").combobox({
        data: flightAttrDatas,
        valueField: 'propertiesNo',
        textField: 'tNoName',
        panelHeight: 'auto',
        panelMaxHeight: '200'
    });
});

function reloadfdHistoryList() {
    $('#datagrid').datagrid('reload');
}

function fdHistoryListsearch() {
    var planDate_begin = $("#planDate_begin").val(),planDate_end = $("#planDate_end").val();
    if(!planDate_begin && !planDate_begin){
        $.messager.show({title: "提示", msg: "请输入查询时间!", timeout: 5000, showType: 'slide'});
        return;
    }

    var queryParams = $('#datagrid').datagrid('options').queryParams;
    //$("#sourceType").combobox()
    $('#fdHistoryListtb').find('*').each(function () {
        queryParams[$(this).attr('name')] = $(this).val();
    });

    $('#datagrid').datagrid({
        url: ctx + '/rms/statistics/fdHistoryList',
        queryParams: queryParams
    });
}

function EnterPress(e) {
    var e = e || window.event;
    if (e.keyCode == 13) {
        fdHistoryListsearch();
    }
}

function searchReset(name) {
    var dType = $("#dType").val();
    $("#" + name + "tb").find(":input").val("");
    $("#dType").val(dType);
    fdHistoryListsearch();
}

function downloadExcel() {
    var planDate_begin = $("#planDate_begin").val(),planDate_end = $("#planDate_end").val();
    if(!planDate_begin && !planDate_begin){
        $.messager.show({title: "提示", msg: "请输入查询时间!", timeout: 5000, showType: 'slide'});
        return;
    }

    var queryParams = {};
    $('#fdHistoryListtb').find('*').each(function () {
        queryParams[$(this).attr('name')] = $(this).val();
    });
    location.href = ctx + '/rms/statistics/fdHistoryExpExcel?' + parseParam(queryParams);
}

var parseParam = function (param, key) {
    var paramStr = "";
    if (param instanceof String || param instanceof Number || param instanceof Boolean) {
        paramStr += "&" + key + "=" + encodeURIComponent(param);
    } else {
        $.each(param, function (i) {
            var k = key == null ? i : key + (param instanceof Array ? "[" + i + "]" : "." + i);
            paramStr += '&' + parseParam(this, k);
        });
    }
    return paramStr.substr(1);
};