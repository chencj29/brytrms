/**
 * Created by xiaopo on 15/12/14.
 */
//--------------------**sysDictUtils**------------------------

var sysDictUtils = function (tName, cName) {
    this.datas = new Object();
    this.init = function (tableName, columnName) {
        var _this = this;
        $.ajax({
            url: ctx + "/sysdict/getLabels",
            async: false,
            data: {"tableName": tableName, "columnName": columnName},
            success: function (resp) {
                _this.datas = resp;
            }
        });

    };
    this.getLabel = function (value) {
        if (typeof(value) == 'undefined' || value == null || !value) {
            return this.datas[0];
        }
        for (var data in this.datas) {
            if (this.datas.hasOwnProperty(data)) {
                if (typeof(data) != 'undefined' && data == value) {
                    return this.datas[data];
                }
            }
        }
    };
    this.init(tName, cName);
};

var dictMap = function (array) {
    var datas = {};
    $.ajax({
        dataType: "json", type: "post",
        contentType: "application/json",
        url: ctx + "/sysdict/getLabelss",
        async: false, data: JSON.stringify(array),
        success: function (resp) {
            datas = resp;
        }
    });
    return datas;
};

var $uuid = (function () {
    return (function () {
        var d = new Date().getTime();
        if (window.performance && typeof window.performance.now === "function") {
            d += performance.now();
        }
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            var r = (d + Math.random() * 16) % 16 | 0;
            d = Math.floor(d / 16);
            return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
        });
    })
}());

function strNull2Blank(str) {
    var result = "";
    if (str) {
        if (str != null) {
            result = str;
        }
    }
    return result;
}