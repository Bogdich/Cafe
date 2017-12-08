<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<header>
<div class="navbar-fixed">
    <nav class="blue-grey darken-3">
        <div class="nav-wrapper container">
            <a href="#" class="brand-logo"><i class="large material-icons">account_box</i></a>
            <ul class="right hide-on-med-and-down">
                <li><a href="${pageContext.request.contextPath}/controller?command=main-page">
                    <fmt:message bundle="${lang}" key="navbar.main"/>
                </a></li>
                <li><a href="${pageContext.request.contextPath}/controller?command=menu-page">
                    <fmt:message bundle="${lang}" key="navbar.menu"/>
                </a></li>
                <li><a href="">
                    <fmt:message bundle="${lang}" key="navbar.contact"/>
                </a></li>
                <li><a id="shopping-cart" class="" href="" data-activates="shopping-cart-div"><i class="white-text material-icons">add_shopping_cart</i></a></li>
                <li><a class="dropdown-button" data-activates="dropdown-lang"><i class="material-icons">language</i></a></li>
                <li>
                    <ul id="dropdown-lang" class="dropdown-content">
                        <li><a href="${pageContext.request.contextPath}/controller?command=change-language&lang=en-EN" class="">EN</a></li>
                        <li><a href="${pageContext.request.contextPath}/controller?command=change-language&lang=ru-RU" class="">RU</a></li>
                    </ul>
                </li>
                <li>
                    <%--<form action="controller">--%>
                    <%--<input type="hidden" name="command" value="logout">--%>
                    <%--<a href="${pageContext.request.contextPath}/controller?command=logout" class="waves-effect waves-light btn">Logout</a>--%>
                    <%--</form>--%>
                    <a href="${pageContext.request.contextPath}/controller?command=logout" class="waves-effect waves-light btn">
                        <fmt:message bundle="${lang}" key="navbar.logout"/>
                    </a>
                </li>
            </ul>
            <%--<ul id="nav-mobile" class="side-nav">--%>
            <%--<li><a href="">--%>
            <%--<fmt:message key="navbar.about"/>--%>
            <%--</a></li>--%>
            <%--<li><a href="">--%>
            <%--<fmt:message key="navbar.menu"/>--%>
            <%--</a></li>--%>
            <%--<li><a href="">--%>
            <%--<fmt:message key="navbar.contact"/>--%>
            <%--</a></li>--%>
            <%--<li><a class="" href="" ><i class="material-icons">add_shopping_cart</i></a></li>--%>
            <%--<li><a class=""><i class="material-icons">language</i></a></li>--%>
            <%--<li>--%>
            <%--<form action="controller">--%>
            <%--<input type="hidden" name="command" value="logout">--%>
            <%--<button type="submit" class="waves-effect waves-light btn">Logout</button>--%>
            <%--</form>--%>
            <%--</li>--%>
            <%--</ul>--%>
            <a href="#" data-activates="nav-mobile" class="button-collapse right"><i class="material-icons">menu</i></a>
        </div>
    </nav>
</div>
</header>
