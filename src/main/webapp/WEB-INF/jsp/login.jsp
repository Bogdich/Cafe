<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="/localization/lang" var="lang"/>
<fmt:setBundle basename="/localization/message" var="message"/>

<html>
<head>
    <title>Registration</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/materialize/css/materialize.min.css" media="screen,projection"/>
    <style>
        body {
            display: flex;
            min-height: 100vh;
            flex-direction: column;
        }
        main {
            flex: 1 0 auto;
        }

        /* хром, сафари */
        body::-webkit-scrollbar { width: 0; }

        /* ie 10+ */
        body { -ms-overflow-style: none; }

        /* фф (свойство больше не работает, других способов тоже нет)*/
        body { overflow: -moz-scrollbars-none; }

    </style>
</head>
<body>

<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/template/header.jsp"/>

<!-- Login form -->
<main class="valign-wrapper" style="overflow: hidden">
    <!--<img class="" style="position:absolute;top:0;left:0;right:0;bottom:0;z-index:-1;max-width: 100" src="../../img/img1.JPG">-->
    <div id="registration-form" class="container">
        <div class="row">
            <div class="col s4 offset-s4 card-panel" style="">
                <form id="sign-up" class="" name="registrationForm" action="controller" method="post">
                    <input type="hidden" name="command" value="registration"/>
                    <div class="row margin">
                        <div class="input-field col s12 center">
                            <h4 class="blue-grey-text text-darken-2">
                                <fmt:message bundle="${lang}" key="form.authorization"/>
                            </h4>
                        </div>
                    </div>
                    <div class="row margin">
                        <div class="input-field col s12">
                            <i class="material-icons prefix">account_circle</i>
                            <input id="login" name="login" type="text" pattern="^[A-Za-zА-Яа-я]+$" minlength="4" maxlength="45" required class="validate">
                            <label for="login" data-error="">
                                <fmt:message bundle="${lang}" key="form.login"/>
                            </label>
                            <span class=""></span>
                        </div>
                    </div>
                    <div class="row margin">
                        <div class="input-field col s12">
                            <i class="material-icons prefix">lock</i>
                            <input id="password" name="password" type="password" pattern="^.*$" minlength="4" maxlength="45" required class="validate">
                            <label for="password">
                                <fmt:message bundle="${lang}" key="form.password"/>
                            </label>
                        </div>
                    </div>
                    <div class="row margin">
                        <div class="input-field col s12">
                            <input id="checkbox" name="remember" type="checkbox"/>
                            <label for="checkbox">
                                <fmt:message bundle="${lang}" key="form.remember"/>
                            </label>
                        </div>
                    </div>
                    <c:if test="${requestScope.errorMessage ne null}">
                        <article class="red-text text-lighten-1">
                            <fmt:message bundle="${message}" key="${requestScope.errorMessage}"/>
                        </article>
                    </c:if>
                    <div class="row">
                        <div class="input-field col s12">
                            <button type="submit" class="btn waves-effect waves-green col s12"><fmt:message bundle="${lang}" key="navbar.signin"/></button>
                        </div>
                        <div class="input-field col s12">
                            <p class="margin center">
                                <fmt:message bundle="${lang}" key="form.donothaveaccount"/>
                                <a href="${pageContext.request.contextPath}/controller?command=registration-page">
                                    <fmt:message bundle="${lang}" key="form.registration"/>
                                </a>
                            </p>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>

<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/template/footer.jsp"/>

<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/additional-methods.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}materialize/js/materialize.min.js"></script>

<script type="text/javascript">
    $( document ).ready(function(){
        $(".button-collapse").sideNav();
        $("#shopping-cart").sideNav({
            edge: 'right'
        });
        $('.carousel.carousel-slider').carousel({fullWidth: true});
        $('.modal').modal();
        $('.dropdown-button').dropdown({
                inDuration: 300,
                outDuration: 225,
                constrainWidth: false, // Does not change width of dropdown to that of the activator
                hover: true, // Activate on hover
                gutter: 0, // Spacing from edge
                belowOrigin: true, // Displays dropdown below the button
                alignment: 'left', // Displays dropdown with edge aligned to the left of button
                stopPropagation: false // Stops event propagation
            }
        );

//        $('#sign-in').validate({
//            rules: {
//                number: {
//                    required: true,
//                    minlength: 9,
//                    maxlength: 9,
//                    pattern: /^(29|33|44)[0-9]*$/
//                }
//            },
//            messages: {
//                number: {
//                    required: "error1",
//                    minlength: "error2",
//                    maxlength: "error3",
//                    pattern: "error4"
//                }
//            },
//            errorClass : "invalid",
//            errorPlacement: function (error, element) {
////                if (element.attribute('login') === 'number') {
////                    element.data('wrong', error)
////                }
//                console.log(error[0].outerText);
////                console.log($(element[0]).next()[0]);
//                $($(element[0]).next()[0]).attr('data-error', error[0].outerText);
//
////                $($(element[0]).next()[0]).attr('class', "invalid");
//            }
//        });
    })
</script>
</body>
</html>
