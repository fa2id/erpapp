$(document).ready(function () {

    $("a[href^='#']").click(function (e) {
        getOrderIds();
        e.preventDefault();
        $("#main").hide();
        $(".my-section").hide();
        $($(this).attr("href")).fadeIn();
        $($(this).attr("href")).fadeIn("slow");
        $($(this).attr("href")).fadeIn(3000);
    });

    $(".my-section").hide();
    $("#main").fadeIn();

    $("form").submit(function (e) {

        const form = $(this);
        const url = form.attr('action');
        const id = "#" + form.attr('id') + "Result";
        const method = form.attr('method').toUpperCase();

        $.ajax({
            type: method,
            url: url,
            data: form.serialize(),
            success: function (data) {
                $(id).text(JSON.stringify(data, null, '\t'));
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
                console.log(data.result);
                let options = "<option value='' selected></option>";
                for (let i = 0; i < data.result.length; i++) {
                    const opObj = data.result[i].orderId;
                    console.log(opObj);
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
                console.log(data.result);
                let options = "<option value='' selected></option>";
                for (let i = 0; i < data.result.length; i++) {
                    const opObj = data.result[i].itemId;
                    console.log(opObj);
                    options += "<option>" + opObj + "</option>";
                }
                $("#itemIdEditSelect").html(options);
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
                        const result = data.result;
                        $("#placeAvailableQuantity").html(result.itemQuantity);
                        $("#placeItemName").html(result.itemName);
                        $("#placeItemPrice").html(result.itemPrice);
                        $("#placeItemCategory").html(result.itemCategory);
                    },
                    error: function (error) {
                        console.log(error);
                    }
                });
            }
        }
    );
});