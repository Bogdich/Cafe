<%--
  Created by IntelliJ IDEA.
  User: Adrienne
  Date: 18.08.17
  Time: 01:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>500</title>
</head>
<body>
500
<br>
Request from ${pageContext.errorData.requestURI} is failed
<br/>
Servlet login or type: ${pageContext.errorData.servletName}
<br/>
Status code: ${pageContext.errorData.statusCode}
<br/>
Exception: ${pageContext.errorData.throwable}
</body>
</html>
