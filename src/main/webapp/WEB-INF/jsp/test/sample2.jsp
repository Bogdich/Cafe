<%--
  Created by IntelliJ IDEA.
  User: Adrienne
  Date: 16.08.17
  Time: 03:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sample 2</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta login="viewport" content="width=device-width, initial-scale=1"/>
    <!--Import Google Icon Font-->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="../../../static/materialize/css/materialize.min.css" media="screen,projection"/>
</head>
<body>
<article>Путь к контексту : ${ pageContext.request.contextPath }</article> <br/>
<article>Имя хоста : ${ header["host"] }</article><br/>
<article>Тип и кодировка контента : ${pageContext.response.contentType}</article><br/>
<article>Кодировка ответа : ${pageContext.response.characterEncoding}</article><br/>
<article>ID сессии : ${pageContext.request.session.id}</article><br/>
<article>Время создания сессии в мсек : ${pageContext.request.session.creationTime}</article><br/>
<article>Время последнего доступа к сессии : ${pageContext.request.session.lastAccessedTime}</article><br/>
<article>Имя сервлета : ${pageContext.servletConfig.servletName}</article>
<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="../../../static/materialize/js/materialize.min.js"></script>
</body>
</html>
