<%--
  Created by IntelliJ IDEA.
  User: Adrienne
  Date: 14.08.17
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Samples</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta login="viewport" content="width=device-width, initial-scale=1"/>
    <!--Import Google Icon Font-->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="../../../static/materialize/css/materialize.min.css" media="screen,projection"/>
</head>
<body>

<div class="collection">
    <div class="collection-item active"><h4>Samples</h4></div>
    <a id="sample-1" class="collection-item" onclick="window.location.replace('/sample1')" onmouseover="$('#sample-1').css('cursor','pointer')">Sample 1</a>
    <a id="sample-2" class="collection-item" onclick="window.location.replace('/sample2')" onmouseover="$('#sample-2').css('cursor','pointer')">Sample 2</a>
    <a id="sample-3" class="collection-item" onclick="window.location.replace('/sample3')" onmouseover="$('#sample-3').css('cursor','pointer')">Sample 3</a>
    <a id="sample-4" class="collection-item" onclick="window.location.replace('/sample4')" onmouseover="$('#sample-4').css('cursor','pointer')">Sample 4</a>
</div>
<br>
<div class="container">
    <form action="/test" method="get">
        <div class="input-field">
            <i class="material-icons prefix">mode_edit</i>
            <label for="sample-url">Enter url</label>
            <input id="sample-url" login="command" type="text" class="validate">
            <input type="submit" class="btn">
        </div>
    </form>
</div>

<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="../../../static/materialize/js/materialize.min.js"></script>

</body>
</html>
