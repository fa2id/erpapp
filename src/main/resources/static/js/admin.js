$(document).ready(function () {

    getAllUsers();
    $("#myForm").submit(function (e) {
        const result = $("#result");
        const form = $(this);
        const url = form.attr('action');
        const method = form.attr('method').toUpperCase();
        result.hide();
        $.ajax({
            type: method,
            url: url,
            data: form.serialize(),
            success: function (data) {
                getAllUsers();
                alert(data.message);
            }
        });
        form[0].reset();
        e.preventDefault();
    });

    const userTable = $("#userTable");
    const tableHeaders = userTable.html();

    function getAllUsers() {
        $.ajax({
            type: 'GET',
            url: '/admin/users/v1/get/all',
            success: function (data) {
                console.log(data);
                const users = data.result;
                let tableData = "";
                users.forEach(function (user) {
                    tableData = tableData + '<tr><td>' + user.username + '</td><td>' + user.role + '</td></tr>';
                });
                console.log(tableHeaders);
                console.log(tableData);
                userTable.html(tableHeaders + tableData);
            }
        });
    }
});