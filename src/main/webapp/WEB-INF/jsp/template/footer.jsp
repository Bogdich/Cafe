<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="/localization/lang" var="lang"/>

<footer class="page-footer blue-grey darken-3">
    <div class="footer-copyright">
        <div class="container">
            <fmt:message bundle="${lang}" key="footer.copyrigh"/>
        </div>
    </div>
</footer>