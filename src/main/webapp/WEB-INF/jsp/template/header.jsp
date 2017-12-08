<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.bogdevich.cafe.entity.type.Role" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="/localization/lang" var="lang"/>

<c:if test="${sessionScope.role != null}">
    <c:choose>
        <c:when test="${sessionScope.role eq Role.CUSTOMER}">
            <%@ include file="customer-header.jsp"%>
        </c:when>
        <c:when test="${sessionScope.role eq Role.ADMIN}">
            <%@ include file="admin-header.jsp"%>
        </c:when>
        <c:otherwise>
            <%@ include file="guest-header.jsp"%>
        </c:otherwise>
    </c:choose>
</c:if>

<%@ include file="shopping-cart.jsp"%>
<%@ include file="create-order.jsp"%>