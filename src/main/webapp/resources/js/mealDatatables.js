var ajaxUrl = "ajax/meals/";
var datatableApi;

$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
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
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });

    $("#filter").submit(function () {
        addFilter();
        return false;
    });

    makeEditable();
});

function clearFilter() {
    
}

function addFilter() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(function() {
        updateTableByFilter(data);
    });
    return false;
}

function updateTableByFilter(data) {
    datatableApi.clear().row.add(data).draw();
}