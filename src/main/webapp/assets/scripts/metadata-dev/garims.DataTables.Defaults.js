$.extend($.fn.dataTable.defaults, {
    dom: "R<'row'<'col-md-2'l><'col-md-10 text-left sm-left'B>r>" +
    "t" +
    "<'row'<'col-md-4 sm-center'i><'col-md-8 text-right sm-center'p>>",
    language: {
        "url": "/assets/scripts/datatables-plugins/i18n/Chinese.lang"
    },
    pagingType: "full_numbers",
    processing: true, select: true,
    buttons: []
});

var extraButtons = $.extend({}, {
    collections: {
        extend: 'collection',
        text: '<i class="fa fa-ellipsis-h"></i>&nbsp; <span class="caret">',
        buttons: [
            {extend: 'copyHtml5', text: '数据复制'},
            {extend: 'excelHtml5', text: '导出为Excel'},
            {extend: 'csvHtml5', text: '导出为CSV'},
            {extend: 'pdfHtml5', text: '导出为PDF'},
            {extend: 'print', text: '打印'}
        ]
    }, colvis: {
        extend: 'colvis',
        text: '<i class="fa fa-eye"></i>&nbsp; <span class="caret">'
    }
});