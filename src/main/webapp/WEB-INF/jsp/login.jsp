<%--
  Created by IntelliJ IDEA.
  User: Adrienne
  Date: 31.08.17
  Time: 03:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign in page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <!--Import Google Icon Font-->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="../../static/materialize/css/materialize.min.css" media="screen,projection"/>
</head>
<body>

<div id="materialbox-overlay" class="indigo darken-1 valign-wrapper">
    <div class="container">
        <div class="row">
            <form class="white-text col s6 offset-s3 m6 offset-m3 l6 offset-l3" name="loginForm" action="/controller" method="post">
                <input type="hidden" name="command" value="login"/>
                <div class="container">
                    <div class="input-field">
                        <input id="login" name="login" type="text"  pattern="^[A-Za-zА-Яа-я_\d]+$" minlength="4" maxlength="45" required class="validate">
                        <label class="amber-text" for="login" data-error="invalid input">Login</label>
                    </div>

                    <div class="input-field">
                        <input id="password" name="password" type="password" pattern="^[A-Za-zА-Яа-я_\d]+$" minlength="4" maxlength="45" required class="validate">
                        <label class="amber-text" for="password" data-wrong="invalid characters">Password</label>
                    </div>
                    <div class="input-field">
                        <input id="checkbox" name="remember" type="checkbox"/>
                        <label class="amber-text" for="checkbox">Remember me</label>
                    </div>

                    <div class="card-content">
                        <br>
                        <article>${errorLoginPassMessage}</article>
                        <article>${wrongAction}</article>
                        <article>${nullPage}</article>
                    </div>
                    <div class="input-field">
                        <input type="submit" class="amber waves-effect waves-light btn-flat">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="../../static/materialize/js/materialize.min.js"></script>
</body>
</html>
