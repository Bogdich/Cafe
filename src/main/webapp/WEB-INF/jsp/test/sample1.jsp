<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  UserOld: Adrienne
  Date: 09.08.17
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sample 1</title>
</head>
<body>
<jsp:useBean id="calendar" class="java.util.GregorianCalendar" scope="page"/>
<div class="picker__container__wrapper">
    <h4>Directive</h4>
    <%@include file="/WEB-INF/jsp/test/sample3.jsp3.jsp" %>
</div>
<div class="picker__container__wrapper">
    <h4>Action-tag</h4>
    <jsp:include page="/WEB-INF/jsp/test/sample3.jsp3.jsp"/>
</div>
<div class="picker__container__wrapper">
    <h4>Java Standart Tag Library</h4>
    <c:import url="/WEB-INF/jsp/test/sample3.jsp3.jsp"/>
</div>
</body>
</html>
