$(document).ready(function () {


    $("a[href^='#']").click(function (e) {
        getCategories();
        getItemIds();
        e.preventDefault();
        $("#main").hide();
        $(".my-section").hide();
        $($(this).attr("href")).fadeIn();
        $($(this).attr("href")).fadeIn("slow");
        $($(this).attr("href")).fadeIn(3000);
    });


    //$(".my-section").hide();
    $("#main").fadeIn();


    $("form").submit(function (e) {

        const form = $(this);
        const url = form.attr('action');
        const id = "#" + form.attr('id') + "Result";
        const status = $(id + "Status");
        const message = $(id + "Message");
        const resultData = $(id + "Data");
        const method = form.attr('method').toUpperCase();
        status.fadeOut();
        message.fadeOut();
        resultData.fadeOut();
        $.ajax({
            type: method,
            url: url,
            data: form.serialize(),
            success: function (data) {
                console.log(data);
                status.html("<h4>STATUS</h4><p>" + data.status + "</p>");
                message.html("<h4>MESSAGE</h4><p>" + data.message + "</p>");
                let inResultDataHtml = "";
                if (Array.isArray(data.result)) {
                    data.result.forEach(function(obj) {
                        let inResultDataHtmlTemp = '<div ' +
                            'class="w3-panel w3-card-4 w3-opacity-min w3-pale-blue w3-round-xlarge">';
                        Object.keys(obj).forEach(function (key) {
                            inResultDataHtmlTemp += "<p>" + key + ": " + obj[key] + "</p>";
                        });
                        inResultDataHtmlTemp += "</div>";
                        inResultDataHtml += inResultDataHtmlTemp;
                    });
                    resultData.addClass("resultDataArray");
                } else {
                    let inResultDataHtmlTemp = '<div ' +
                        'class="w3-panel w3-card-4 w3-opacity-min w3-pale-blue w3-round-xlarge">';
                    Object.keys(data.result).forEach(function (key) {
                        inResultDataHtmlTemp += "<p>" + key + ": " + data.result[key] + "</p>";
                    });
                    inResultDataHtmlTemp += "</div>";
                    inResultDataHtml += inResultDataHtmlTemp;
                    resultData.removeClass("resultDataArray");
                }
                resultData.html("<h4>RESULT</h4>" + inResultDataHtml);
                status.fadeIn(1000);
                message.fadeIn(2000);
                resultData.fadeIn(3000);
            }
        });

        e.preventDefault();
    });


    getCategories();

    function getCategories() {
        $.ajax({
            url: '/scm/categories/v1/get/all',
            type: 'GET',
            success: function (data) {
                console.log(data);
                let options = "";
                for (let i = 0; i < data.length; i++) {
                    const opObj = data[i].categoryName;
                    options += "<option value='" + opObj + "'>"
                        + (opObj === "uncategorized" ? "" : opObj) + "</option>";
                }
                $("#addSelect").html(options);
                $("#editSelect").html(options);
            },
            error: function (error) {
                console.log(error);
            }
        });
    }


    getItemIds();

    function getItemIds() {
        $.ajax({
            url: '/scm/items/v1/get/all',
            type: 'GET',
            success: function (data) {
                console.log(data);
                let options = "<option value='' selected></option>";
                for (let i = 0; i < data.result.length; i++) {
                    const opObj = data.result[i].itemId;
                    options += "<option>" + opObj + "</option>";
                }
                $("#itemIdRemoveSelect").html(options);
                $("#itemIdEditSelect").html(options);
            },
            error: function (error) {
                console.log(error);
            }
        });
    }

    let itemIdEditSelect = $("#itemIdEditSelect");
    itemIdEditSelect.change(
        function () {
            const selectedOptionText = itemIdEditSelect.find(":selected").text();
            $("#editForm")[0].reset();
            itemIdEditSelect.val(selectedOptionText);
            if (itemIdEditSelect.find(":selected").text() !== "") {
                $.ajax({
                    url: '/scm/items/v1/get?itemId=' + itemIdEditSelect.find(":selected").text(),
                    type: 'GET',
                    success: function (data) {
                        console.log(data);
                        const result = data.result;
                        $("#editItemName").val(result.itemName);
                        $("#editItemPrice").val(result.itemPrice);
                        $("#editItemQuantity").val(result.itemQuantity);
                        $("#editSelect").val(result.itemCategory);
                    },
                    error: function (error) {
                        console.log(error);
                    }
                });
            }
        }
    );

});
