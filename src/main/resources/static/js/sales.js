$(document).ready(function () {

    $("a[href^='#']").click(function (e) {
        getOrderIds();
        getItemIds();
        removeItemFieldsInPlaceOrder();
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
                    data.result.forEach(function (obj) {
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
        form[0].reset();
        $("#placeAvailableQuantity").html("");
        $("#placeItemName").html("");
        $("#placeItemPrice").html("");
        $("#placeItemCategory").html("");
        e.preventDefault();
    });

    getOrderIds();

    function getOrderIds() {
        $.ajax({
            url: '/sales/orders/v1/get/all',
            type: 'GET',
            success: function (data) {
                console.log(data);
                let options = "<option value='' selected></option>";
                for (let i = 0; i < data.result.length; i++) {
                    const opObj = data.result[i].orderId;
                    options += "<option>" + opObj + "</option>";
                }
                $("#orderIdCancelSelect").html(options);
                $("#orderIdGetSelect").html(options);
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
                $("#itemIdPlaceSelect").html(options);
            },
            error: function (error) {
                console.log(error);
            }
        });
    }

    let itemIdPlaceSelect = $("#itemIdPlaceSelect");
    itemIdPlaceSelect.change(
        function () {
            $("#placeAvailableQuantity").html("");
            $("#placeItemName").html("");
            $("#placeItemPrice").html("");
            $("#placeItemCategory").html("");
            if (itemIdPlaceSelect.find(":selected").text() !== "") {
                $.ajax({
                    url: '/scm/items/v1/get?itemId=' + itemIdPlaceSelect.find(":selected").text(),
                    type: 'GET',
                    success: function (data) {
                        console.log(data);
                        const result = data.result;
                        $("#placeAvailableQuantity").html(result.itemQuantity);
                        $("#placeItemName").html(result.itemName);
                        $("#placeItemPrice").html(result.itemPrice);
                        $("#placeItemCategory").html(result.itemCategory);
                        $("input[name='itemQuantity']").prop('max', result.itemQuantity);

                    },
                    error: function (error) {
                        console.log(error);
                    }
                });
            }
        }
    );

    function  removeItemFieldsInPlaceOrder(){
        $("#placeAvailableQuantity").html("");
        $("#placeItemName").html("");
        $("#placeItemPrice").html("");
        $("#placeItemCategory").html("");
        $("input[name='itemQuantity']").prop('max', 100000);
    }

    $("#customerEmailInput").change(
        function () {
            const customerFirstNameInput = $("#customerFirstNameInput");
            const customerLastNameInput = $("#customerLastNameInput");
            customerFirstNameInput.val("");
            customerLastNameInput.val("");
            customerFirstNameInput.prop('disabled', true);
            customerLastNameInput.prop('disabled', true);
            $.ajax({
                url: '/sales/customers/v1/get?customerEmail=' + $("#customerEmailInput").val(),
                type: 'GET',
                success: function (data) {
                    console.log(data);
                    const result = data.result;
                    if (result.customerExisted) {
                        customerFirstNameInput.val(result.customerFirstName);
                        customerLastNameInput.val(result.customerLastName);
                    } else {
                        customerFirstNameInput.prop('disabled', false);
                        customerLastNameInput.prop('disabled', false);
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            });
        }
    );
});