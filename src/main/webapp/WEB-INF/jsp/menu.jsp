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
    <link href="https://fonts.googleapis.com/css?family=Jura|Russo+One" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/materialize/css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/materialize/css/style.css" media="screen,projection"/>

</head>
<body>

<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/template/header.jsp"/>

<main>
    <div id="menu" class="row">
        <div class="col s12">
            <ul class="tabs tabs-transparent tabs-fixed-width">
                <c:if test="${requestScope.categories != null}">
                    <c:forEach var="entry" items="${requestScope.categories}">
                        <li class="tab col m3 s12">
                            <a class="blue-grey-text darken-3" href="#${entry.key.getKey()}">
                                ${entry.value}
                            </a>
                        </li>
                    </c:forEach>
                </c:if>
                <div class="indicator blue-grey" style="z-index:1"></div>
            </ul>
        </div>
        <div id="preloader" class="valign-wrapper center">
            <div class="preloader-wrapper big active">
                <div class="spinner-layer spinner-blue-only">
                    <div class="circle-clipper left">
                        <div class="circle"></div>
                    </div><div class="gap-patch">
                    <div class="circle"></div>
                </div><div class="circle-clipper right">
                    <div class="circle"></div>
                </div>
                </div>
            </div>
        </div>
        <c:if test="${requestScope.categories != null}">
            <c:forEach var="entry" items="${requestScope.categories}">
                <div id="${entry.key.getKey()}" class="col s12 category"></div>
            </c:forEach>
        </c:if>
    </div>
</main>


<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/template/footer.jsp"/>

<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/additional-methods.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/materialize/js/materialize.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/materialize/js/menu.js"></script>

</body>
</html>
