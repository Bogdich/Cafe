$(document).ready(function () {

    var $categoryDivList = $("div[class~='category']");
    var $tabID = $('div[class~="category"]:first').attr("id");

    $categoryDivList.each(function (index) {
        observer.observe($categoryDivList[index], {
            attributes: true //listen to attribute changing
        });
    });

    $('ul.tabs').tabs('select_tab', $tabID);

    $(".button-collapse").sideNav();

    $("#shopping-cart").sideNav({
        menuWidth: 400, // Default is 300
        edge: 'right', // Choose the horizontal origin
        closeOnClick: true, // Closes side-nav on <a> clicks, useful for Angular/Meteor
        draggable: true, // Choose whether you can drag to open on touch screens,
        onOpen: function() {
            var table = $('#shopping-cart-table');
            loadShoppingCart(table)
        },
        onClose: function() {
            $('#shopping-cart-table').empty();
        }
    });

    $('.modal').modal();

    $('.dropdown-button').dropdown({
            inDuration: 300,
            outDuration: 225,
            constrainWidth: false, // Does not change width of dropdown to that of the activator
            hover: true, // Activate on hover
            gutter: 0, // Spacing from edge
            belowOrigin: true, // Displays dropdown below the button
            alignment: 'left', // Displays dropdown with edge aligned to the left of button
            stopPropagation: false // Stops event propagation
        }
    );

    $('.scrollspy').scrollSpy();

    $(document).on('click', "button[class~='add-dish']", function () {
        $dishID = $(this).attr('id');
        addDishToShopingCart($dishID);
    });

    $(document).on('focusin', "input[id|='input']", function(){
        $(this).data('val', $(this).val());
    }).on('change',"input[id|='input']", function(){
        var prev = $(this).data('val');
        var current = $(this).val();
        console.log("Prev value " + prev);
        console.log("New value " + current);
        changeDishQuantityInShoppingCart($(this), prev, current)
    });

    $(document).on('click', "button[id|='delete']", function () {
       var id = $(this).attr('id').substring('delete-'.length);
       var tr = "tr[id|='tr-" + id + "']";
       console.log($(tr));
       deleteDishFromShoppingCart(tr, id);
    });

    $(document).on('click', '#checkout', function () {
        checkShoppingCartSize(function (size) {
            if (size>0) {
                fillOrderFormWithUserInfo();
                fillOrderFormWithTotalCost();
                $('#modal1').modal('open');
            }
        });
    });
    $(document).on('click', '#order-create-button', function () {
        checkShoppingCartSize(function (size) {
            if (size>0) {
                createOrder();
            }
        });
    })
});

/**
 * Apllying for listening to objects object changing.
 * @type {MutationObserver}
 */
var observer = new MutationObserver (function (mutations) {
    mutations.forEach(function (mutation) {
        if (mutation.attributeName === "class") {

            var $item = $(mutation.target);

            if ($item.hasClass("active")) {
                $('#preloader').show();
                console.log($item.attr("id")+": data has been received:");
                loadDishes($item);
            } else {
                $item.empty();
                console.log($item.attr("id")+": item has been cleaned.");
            }
        }
    });
});

/**
 * Load dishes for active category tab.
 * @param $item is an active div.
 */
function loadDishes($item) {
    var request = $.ajax({
        url: "controller",
        type: 'GET',
        dataType: 'json',
        data: {command: "get-dishes", category: $item.attr("id")},
        contentType: 'application/json',
        mimeType: 'application/json',
    });
    request.always(function () {
        $('#preloader').hide();
    });
    request.done(function (data) {
        var $row = $("<div/>");
        $row.addClass("row");
        $.each(data, function (index, dish) {
            console.log(dish);
            $div = $("<div/>");
            $div.html(function () {
                return"" +
                    "<section class=\"col s0\" style=\"width:5.5555555556%;margin-left:auto;left:auto;right:auto\"></section>" +
                    "<div class=\"col s12 m5\">" +
                    "   <div class=\"card\">" +
                    "       <div class=\"card-image\">" +
                    "           <div class=\"waves-effect waves-block waves-light\">" +
                    "               <img class=\"activator\" src=\"/img/" + dish.picture + "\" onerror=\"this.src='/img/Чикенбургер.png'\">" +
                    "           </div>" +
                    "           <button id='" + dish.id + "' class=\"btn-floating halfway-fab waves-effect waves-light red add-dish\"><i class=\"material-icons\">add</i></button>" +
                    "       </div>" +
                    "       <div class=\"card-content\">" +
                    "           <span class=\"card-title activator grey-text text-darken-4\"><strong>"+ dish.name +"</strong></span>" +
                    "           <span>" + dish.price + " BYN</span>" +
                    "           <button class=\"activator btn-flat right\">Описание</button>" +
                    "       </div>" +
                    "       <div class=\"card-reveal\">" +
                    "           <span class=\"card-title grey-text text-darken-4\"><strong>" + dish.name + "</strong><i class=\"material-icons right\">close</i></span>" +
                    "           <p>" + dish.description + "</p>" +
                    "           <p><span><strong>" + dish.weight + "</strong> грамм</span></p>" +
                    "       </div>" +
                    "   </div>" +
                    "</div>";
            });
            $row.append($div);
        });
        $item.append($row);
    });
}

/**
 * Add dish to the shopping cart
 * @param $id of a dish to be sent to the server side.
 */
function addDishToShopingCart($id) {
    var request = $.ajax({
        url: "controller",
        type: 'GET',
        dataType: 'json',
        data: {command: "add-dish", dishID: $id},
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    request.done(function (data) {
        Materialize.toast(data.message, 4000);
    });
}

/**
 * Load shopping cart content on slide out
 * @param $shoppingCart
 */
function loadShoppingCart($shoppingCart) {
    var request = $.ajax({
        url: "controller",
        type: 'GET',
        dataType: 'json',
        data: {command: "get-shopping-cart"},
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    request.done(function (data) {
        var $table = $('#shopping-cart-table');
        var $tbody = $("<tbody></tbody>");


        $.each(data, function (index, map) {
            console.log(map[0], ": ", map[1]);
            var row = "<tr id=\"tr-" + map[0].id + "\"></tr>";
            var $tr= $(row);
            $tr.html(function () {
                return"" +
                    "<td style=\"width: 35%\">" +
                    "   <div class=\"center\">" +
                    "       <img src=\"" + map[0].picture + "\" onerror=\"this.src='/img/Чикенбургер.png'\" alt=\"\" class=\"circle responsive-img\">" +
                    "   </div>" +
                    "   <div class=\"center\">" +
                    "       <p><span><strong>" + map[0].name + "</strong></span></p>" +
                    "</div>" +
                    "</td>" +
                    "<td style=\"width: 30%\">" +
                    "   <div class=\"center\">" +
                    "       <p>" +
                    "           <span>" + map[0].price + " BYR</span>" +
                    "       </p> " +
                    "   </div> " +
                    "</td> " +
                    "<td style=\"width: 25%\">" +
                    "   <div class=\"center\">" +
                    "       <label for=\"sc\">Количество</label>" +
                    "       <input value=\"" + map[1] + "\" name=\"" + map[0].name + "\" id=\"input-" + map[0].id + "\" type=\"number\" class=\"validate\" min=\"1\" required/>" +
                    "   </div>" +
                    "</td>" +
                    "<td style=\"width: 10%\"> " +
                    "   <div class=\"center\"> " +
                    "       <button id=\"delete-" + map[0].id + "\" class=\"btn-flat waves-effect black-text\"><i class=\"material-icons\">close</i></button> " +
                    "   </div>" +
                    "</td>";
            });
            $tbody.append($tr);
        });
        $table.append($tbody);
    });
}

/**
 * Change item quantity
 * @param input
 * @param prev
 * @param current
 */
function changeDishQuantityInShoppingCart(input, prev, current) {
    var id = $(input).attr('id').substring('input-'.length);
    var request = $.ajax({
        url: "controller",
        type: 'POST',
        data: {
            command: "change-quantity",
            quantity: current,
            dishID: id
        },
        contentType: 'application/x-www-form-urlencoded'
    });
    request
        .done(function (data) {
        })
        .error(function () {
            $(input).val(prev);
        });
}

/**
 * Delete item from shopping cart
 * @param trow
 * @param id
 */
function deleteDishFromShoppingCart(trow, id) {
    var request = $.ajax({
        url: "controller",
        type: 'POST',
        data: {
            command: "delete-dish-from-shopping-cart",
            dishID: id
        },
        contentType: 'application/x-www-form-urlencoded'
    });
    request
        .done(function () {
            $(trow).remove();
        })
        .error(function () {
        });
}

/**
 * Check size of shopping cart
 */
function checkShoppingCartSize(returnSize) {
    var request = $.ajax({
        url: 'controller',
        type: 'GET',
        data: {
            command: 'check-shopping-cart-size'
        },
        dataType: 'json'
    });
    request
        .done(function (data) {
            returnSize(data.answer);
        })
        .error(function () {

        });
}

function fillOrderFormWithUserInfo() {
    var request = $.ajax({
        url: 'controller',
        type: 'GET',
        data: {
            command: 'fill-order-form-with-user-info'
        },
        dataType: 'json'
    });
    request
        .done(function (userInfo) {
            $('#order-street').val(userInfo.street);
            $('#order-house').val(userInfo.house);
            $('#order-flat').val(userInfo.flat);
            $('#order-phone-number').val(userInfo.number);
            Materialize.updateTextFields();
            console.log(userInfo);
        })
        .error(function () {

        });
}

function fillOrderFormWithTotalCost() {
    var request = $.ajax({
        url: 'controller',
        type: 'GET',
        data: {
            command: 'fill-order-form-with-total-cost'
        },
        dataType: 'json'
    });
    request
        .done(function (answer) {
            $('#order-total-cost').html(answer.answer);

        })
        .error(function () {

        });
}

/**
 * Create order
 */
function createOrder() {
    var request = $.ajax({
        url: "controller",
        type: 'POST',
        data: {
            command: "create-order",
            street: $('#order-street').val(),
            house: $('#order-house').val(),
            flat: $('#order-flat').val(),
            number: $('#order-phone-number').val()
        },
        contentType: 'application/x-www-form-urlencoded',
        dataType: 'json'
    });
    request
        .done(function (data) {
            console.log(data);
            $('.button-collapse').sideNav('hide');
            Materialize.toast(data, 4000);

        })
        .error(function () {
        });
}
