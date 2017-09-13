<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<div class="navbar-fixed">
    <nav class="blue-grey darken-3">
        <div class="nav-wrapper container">
            <a href="#" class="brand-logo">${sessionScope.role}</a>
            <ul class="right hide-on-med-and-down">
                <li><a href="${pageContext.request.contextPath}/controller?command=main-page">
                    <fmt:message bundle="${lang}" key="navbar.main"/>
                </a></li>
                <li><a href="">
                    <fmt:message bundle="${lang}" key="navbar.menu"/>
                </a></li>
                <li><a href="">
                    <fmt:message bundle="${lang}" key="navbar.contact"/>
                </a></li>
                <li><a id="shopping-cart" class="" href="" data-activates="slide-out"><i class="white-text material-icons">add_shopping_cart</i></a></li>
                <li><a class="dropdown-button" data-activates="dropdown-lang"><i class="material-icons">language</i></a></li>
                <li>
                    <ul id="dropdown-lang" class="dropdown-content">
                        <li><a href="${pageContext.request.contextPath}/controller?command=change-language&lang=en_EN" class="">EN</a></li>
                        <li><a href="${pageContext.request.contextPath}/controller?command=change-language&lang=ru_RU" class="">RU</a></li>
                    </ul>
                </li>
                <li><a class="waves-effect waves-light btn" href="#login-form">
                    <fmt:message bundle="${lang}" key="navbar.signin"/>
                </a></li>
            </ul>
            <%--<ul id="nav-mobile" class="side-nav">--%>
                <%--<li><a href="">About</a></li>--%>
                <%--<li><a href="">Menu</a></li>--%>
                <%--<li><a href="">Contact</a></li>--%>
                <%--<li><a class="" href="" ><i class="material-icons">add_shopping_cart</i></a></li>--%>
                <%--<li><a class=""><i class="material-icons">language</i></a></li>--%>
                <%--<li><a class="waves-effect waves-light btn" href="#form">Sign in</a></li>--%>
            <%--</ul>--%>
            <a href="#" data-activates="nav-mobile" class="button-collapse right"><i class="material-icons">menu</i></a>
        </div>
    </nav>
</div>

<!-- Order products -->
<ul id="slide-out" class="side-nav">
    <li><a href="#!">First Sidebar Link</a></li>
    <li><a href="#!">Second Sidebar Link</a></li>
</ul>

<!-- Modal Structure -->
<div id="login-form" class="modal" style="width: 50vh;">
    <form id="sign-in" style="overflow-x: hidden;" name="loginForm" action="controller" method="post">
        <input type="hidden" name="command" value="login"/>
        <div class="modal-content">
            <div class="input-field">
                <i class="material-icons prefix">account_circle</i>
                <input id="login" name="login" type="text" pattern="^[A-Za-zА-Яа-я0-9_]+$" minlength="2" maxlength="45" required class="validate">
                <label for="login" data-error="Invalid input">
                    <fmt:message bundle="${lang}" key="form.login"/>
                </label>
            </div>
            <div class="input-field">
                <i class="material-icons prefix">lock</i>
                <input id="password" name="password" type="password" pattern="^.*$" minlength="4" maxlength="45" required class="validate">
                <label for="password">
                    <fmt:message bundle="${lang}" key="form.password"/>
                </label>
            </div>
            <div class="input-field">
                <input id="checkbox" name="remember" type="checkbox"/>
                <label for="checkbox">
                    <fmt:message bundle="${lang}" key="form.remember"/>
                </label>
            </div>
            <div class="">
                <br>
                <article>${errorLoginPassMessage}</article>
                <article>${wrongAction}</article>
                <article>${nullPage}</article>
            </div>
        </div>
        <div class="modal-footer">
            <button type="submit" class="modal-action waves-effect waves-green btn">
                <fmt:message bundle="${lang}" key="navbar.signin"/>
            </button>
        </div>
    </form>
</div>