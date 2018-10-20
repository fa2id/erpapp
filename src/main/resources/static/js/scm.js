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

        e.preventDefault();
    });


    getCategories();

    function getCategories() {
        $.ajax({
            url: '/scm/categories/v1/get/all',
            type: 'GET',
            success: function (data) {
                let options = "";
                for (let i = 0; i < data.length; i++) {
                    const opObj = data[i].categoryName;
                    options += "<option value='"+opObj+"'>"
                        + (opObj === "uncategorized"?"":opObj) + "</option>";
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
                console.log(data.result);
                let options = "<option value='' selected></option>";
                for (let i = 0; i < data.result.length; i++) {
                    const opObj = data.result[i].itemId;
                    console.log(opObj);
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
