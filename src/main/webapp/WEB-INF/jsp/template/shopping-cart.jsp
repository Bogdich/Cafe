<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<div id="shopping-cart-div" class="side-nav">
    <div class="section center blue-grey darken-3 white-text">
        <h3>
            <fmt:message bundle="${lang}" key="shoppingcart.title"/>
        </h3>
    </div>
    <%--<div class="divider"></div>--%>
    <div class="section center">
        <table id="shopping-cart-table" class="highlight bordered">

        </table>
    </div>
    <%--<div class="divider"></div>--%>
    <div class="section center blue-grey darken-3">
        <button id="checkout" class="btn waves-effect waves-light center-align">
            <fmt:message bundle="${lang}" key="shoppingcart.button"/>
        </button>
    </div>
</div>
