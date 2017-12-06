/**
 * Created by xiaowu on 15/12/2.
 */
var $utils = $.extend({}, {
    superTrim: function (value, defaultVal) {
        if (value === undefined || value === null || value === 'null') {
            if (defaultVal !== undefined && defaultVal !== null) return defaultVal;
            return "";
        } else return $.trim(value);

    },

    getRandomArbitrary: function (min, max) {
        return Math.random() * (max - min) + min;
    },

    ajaxLoadFragments: function (url, callbackFn) {
        return this._loadFragments({url: url, callbackFn: callbackFn});
    },

    //利用ajax加载Html静态页面
    //调用方式
    //1、异步处理: $utils.ajaxLoadFragment({ url: 'fragments/flightPlan/addPlanDetailForm.html', callbackFn: showHtmlCode })
    //2、同步取值: var result = $utils.ajaxLoadFragment({ url: 'fragments/flightPlan/addPlanDetailForm.html' });
    _loadFragments: function (opts) {
        var returnData;
        if (!opts.url) throw new Error("the url arguments can not be empty");
        $.ajax({
            method: opts.method || 'get', url: opts.url,
            async: opts.async || opts.callbackFn == undefined ? false : true,
            dataType: opts.dataType | 'html',
            success: function (data) {
                if (!opts.callbackFn) {
                    returnData = data;
                } else {
                    opts.callbackFn(data);
                }
            },
            error: function (d) {
                $.messager.alert("提示", "获取数据失败！", "question");
            }
        });
        return returnData;
    },
    /**
     * 表单修改回显,注意：要求表单name和json的key值必须对应，推荐使用数据库字段名
     * @param selector 表单组件父级组件ID，如form或是table的id
     * @param json 结果集 update by xiaopo
     */
    initFormHTML: function (selector, json) {
        var data = $(selector + " *[name]");
        data.each(function () {
            var value = json[$(this).attr("name")];
            if (value != null) {
                var type = $(this).attr("type");
                if (type == "checkbox" || type == "radio") {
                    if ($(this).val() == value)
                        $(this).attr("checked", "checked");
                }
                else {
                    $(this).val(value);
                }
            } else {
                var type = $(this).attr("type");
                if (type == "checkbox" || type == "radio") {
                    $(this).removeAttr("checked");
                }
                else {
                    $(this).val(null);
                }
            }
        });
    }
});

String.prototype.gblen = function () {
    var len = 0;
    for (var i = 0; i < this.length; i++) {
        if (this.charCodeAt(i) > 127 || this.charCodeAt(i) == 94)
            len += 2;
        else
            len++;

    }
    return len;
}

if (!Array.prototype.$push) {
    Array.prototype.$push = function (str) {
        this.push(str);
        return this;
    };

    if (Object.defineProperty) Object.defineProperty(Object.prototype, '$push', {enumerable: false});
}

if (!Array.prototype.$clear) {
    Array.prototype.$clear = function () {
        this.splice(0, this.length);
    };
    if (Object.defineProperty) Object.defineProperty(Object.prototype, '$clear', {enumerable: false});
}


//jQuery extend
(function ($) {
    $.fn.extend({
        serializeObject: function () {
            // console.log(this); this = $("#form")
            var obj = new Object();
            $.each(this.serializeArray(), function (index, param) {
                if (!(param.name in obj)) {
                    obj[param.name] = param.value;
                }
            });
            return obj;
        }, valuesExists: function (field, rules, i, options) {
            var $form = field.closest('form'), args = {
                tn: $form.attr("t"), fc: field.attr("f"), fv: field.val(), id: $form.serializeObject().id
            }, _data;

            $.ajax({
                url: '/common/exists', async: false, data: args, dataType: "JSON", method: "post", success: function (data) {
                    _data = data;
                }
            });

            if (_data) {
                field.select();
                return "「" + field.val() + "」已经存在";
            }
        }
    })
})(jQuery);