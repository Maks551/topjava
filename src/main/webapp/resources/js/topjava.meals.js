const ajaxUrl = "ajax/profile/meals/";
let datatableApi;
let lastDataCount = 0;
let maxDate, minDate;
let startDateStr, endDateStr, startTimeStr, endTimeStr;

function updateTable() {
    updateFilterDatesIfEmpty();
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

// http://api.jquery.com/jQuery.ajax/#using-converters
$.ajaxSetup({
    converters: {
        "text json": function (stringData) {
            const json = JSON.parse(stringData);
            $(json).each(function () {
                this.dateTime = this.dateTime.replace('T', ' ').substr(0, 16);
            });
            return json;
        }
    }
});

$(function () {
    datatableApi = $('#datatable').DataTable(extendsOpts({
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "render": renderEditBtn,
                "defaultContent": "",
                "orderable": false
            },
            {
                "render": renderDeleteBtn,
                "defaultContent": "",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            $(row).attr("data-mealExceed", data.exceed);
        },
        "language": {
            "info": i18n["common.showingInfo"],
            "emptyTable": i18n["common.emptyTable"],
            "infoEmpty": i18n["common.infoEmpty"],
            "lengthMenu": i18n["common.menuInfo"],
            "paginate": {
                "next": i18n["common.next"],
                "previous": i18n["common.previous"]
            }
        },
        "lengthMenu": [ 10, 25, 50, 100 ]
    }));

    $.datetimepicker.setLocale(localeCode);

//  http://xdsoft.net/jqplugins/datetimepicker/
    const startDate = $('#startDate');
    const endDate = $('#endDate');
    startDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        onShow: function (ct) {
            this.setOptions({
                maxDate: endDate.val() ? endDate.val() : false
            })
        }
    });
    endDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        onShow: function (ct) {
            this.setOptions({
                minDate: startDate.val() ? startDate.val() : false
            })
        }
    });

    const startTime = $('#startTime');
    const endTime = $('#endTime');
    startTime.datetimepicker({
        datepicker: false,
        format: 'H:i',
        onShow: function (ct) {
            this.setOptions({
                maxTime: endTime.val() ? endTime.val() : false
            })
        }
    });
    endTime.datetimepicker({
        datepicker: false,
        format: 'H:i',
        onShow: function (ct) {
            this.setOptions({
                minTime: startTime.val() ? startTime.val() : false
            })
        }
    });

    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });
});

function updateFilterDatesIfEmpty(){
    if (lastDataCount != datatableApi.context[0].json.length && datatableApi.context[0].json.length > 0){
        lastDataCount = datatableApi.context[0].json.length;
        maxDate = datatableApi.context[0].json.map(x => new Date(x.dateTime)).reduce((a,b) => a > b ? a : b);
        minDate = datatableApi.context[0].json.map(x => new Date(x.dateTime)).reduce((a,b) => a < b ? a : b);
        startDateStr = minDate.getFullYear() + "-" + 
            ((minDate.getMonth() + 1) > 9 ? (minDate.getMonth() + 1) : "0" + (minDate.getMonth() + 1)) + "-" + 
            (minDate.getDate() > 9 ? minDate.getDate() : "0" + minDate.getDate());
        endDateStr = maxDate.getFullYear() + "-" + 
            ((maxDate.getMonth() + 1) > 9 ? (maxDate.getMonth() + 1) : "0" + (maxDate.getMonth() + 1)) + "-" + 
            (maxDate.getDate() > 9 ? maxDate.getDate() : "0" + maxDate.getDate());
        startTimeStr = (minDate.getHours() > 9 ? minDate.getHours() : "0" + minDate.getHours()) + ":" + 
            (minDate.getMinutes() > 9 ? minDate.getMinutes() : "0" + minDate.getMinutes());
        endTimeStr = (maxDate.getHours() > 9 ? maxDate.getHours() : "0" + maxDate.getHours()) + ":" + 
            (maxDate.getMinutes() > 9 ? maxDate.getMinutes() : "0" + maxDate.getMinutes());
    }

    $("#filter")[0].startDate.value = $("#filter")[0].startDate.value == "" ? startDateStr : $("#filter")[0].startDate.value;
    $("#filter")[0].endDate.value = $("#filter")[0].endDate.value == "" ? endDateStr : $("#filter")[0].endDate.value;
    $("#filter")[0].startTime.value = $("#filter")[0].startTime.value == "" ? startTimeStr : $("#filter")[0].startTime.value;
    $("#filter")[0].endTime.value = $("#filter")[0].endTime.value == "" ? endTimeStr : $("#filter")[0].endTime.value;

}