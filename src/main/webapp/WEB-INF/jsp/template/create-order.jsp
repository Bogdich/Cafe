<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%--<div id="order-modal" class="modal modal-fixed-footer">
    <div class="modal-content">
            <form id="order-form" action="controller">
                <div class="row">
                    <div class="input-field col s6">
                        <input id="order-street" type="text">
                        <label for="order-street" class="">Street</label>
                    </div>
                    <div class="input-field col s3">
                        <input id="order-house" type="text">
                        <label for="order-house" class="">House</label>
                    </div>
                    <div class="input-field col s3">
                        <input id="order-flat" type="text">
                        <label for="order-flat" class="">Flat</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s6">
                        <input id="order-phone-number" type="text">
                        <label for="order-phone-number" class="">Phone number</label>
                    </div>
                </div>
            </form>
            <div class="row">
                <div class="col s6">
                    <p>
                        <fmt:message bundle="${lang}" key="shoppingcart.total"/>: <span id="order-total-cost"></span>
                    </p>
                </div>
            </div>
    </div>
    <div class="modal-footer">
        <div class="input-field">
            <button class="modal-action btn waves-effect waves-light right">Submit
                <i class="material-icons right">send</i>
            </button>
        </div>
    </div>
</div>--%>

<!-- Modal Structure -->
<div id="modal1" class="modal">
    <div class="modal-content">
        <h4>
            <fmt:message bundle="${lang}" key="order.title"/>
        </h4>
        <div class="row">
            <div class="input-field col s6">
                <input id="order-street" type="text">
                <label for="order-street" class="">
                    <fmt:message bundle="${lang}" key="form.street"/>
                </label>
            </div>
            <div class="input-field col s3">
                <input id="order-house" type="text">
                <label for="order-house" class="">
                    <fmt:message bundle="${lang}" key="form.house"/>
                </label>
            </div>
            <div class="input-field col s3">
                <input id="order-flat" type="text">
                <label for="order-flat" class="">
                    <fmt:message bundle="${lang}" key="form.flat"/>
                </label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <input id="order-phone-number" type="text">
                <label for="order-phone-number" class="">
                    <fmt:message bundle="${lang}" key="form.number"/>
                </label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <p>
                    <fmt:message bundle="${lang}" key="shoppingcart.total"/>: <span id="order-total-cost"></span> BYN
                </p>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button id="order-create-button" class="modal-action modal-close waves-effect waves-green btn">
            <fmt:message bundle="${lang}" key="order.confirm"/>
        </button>
    </div>
</div>