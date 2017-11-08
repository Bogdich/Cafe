<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="/localization/lang" var="lang"/>

<html>
<head>
    <title>Cafe</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/materialize/css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/materialize/css/style.css" media="screen,projection"/>
</head>
<body>

<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/template/header.jsp"/>

<main>
    <div class="carousel carousel-slider center" style="min-height: 90vh" data-indicators="true">
        <div class="carousel-fixed-item center">
            <a class="btn waves-effect white grey-text darken-text-2">
                <fmt:message bundle="${lang}" key="navbar.menu"/>
            </a>
        </div>
        <div class="carousel-item red white-text" href="#one!">
            <h2>First Panel</h2>
            <p class="white-text">This is your first panel</p>
        </div>
        <div class="carousel-item amber white-text" href="#two!">
            <h2>Second Panel</h2>
            <p class="white-text">This is your second panel</p>
        </div>
        <div class="carousel-item green white-text" href="#three!">
            <h2>Third Panel</h2>
            <p class="white-text">This is your third panel</p>
        </div>
        <div class="carousel-item blue white-text" href="#four!">
            <h2>Fourth Panel</h2>
            <p class="white-text">This is your fourth panel</p>
        </div>
    </div>
</main>

<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/template/footer.jsp"/>

<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/additional-methods.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}materialize/js/materialize.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}materialize/js/main.js"></script>
</body>
</html>
