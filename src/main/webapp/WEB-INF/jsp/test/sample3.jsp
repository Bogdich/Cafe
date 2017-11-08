<%--
  Created by IntelliJ IDEA.
  User: Adrienne
  Date: 16.08.17
  Time: 03:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <title>Sample 3</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta login="viewport" content="width=device-width, initial-scale=1"/>
    <!--Import Google Icon Font-->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="../../static/materialize/css/materialize.min.css" media="screen,projection"/>
</head>
<body>

<div class="container center-align">
    <hr/>Время в милисекундах: ${calendar.timeInMillis}<hr/>
</div>

<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="../../static/materialize/js/materialize.min.js"></script>
</body>
</html>
