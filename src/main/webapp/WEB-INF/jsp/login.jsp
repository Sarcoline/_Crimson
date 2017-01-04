<%--
  Created by IntelliJ IDEA.
  User: Meow
  Date: 30.12.2016
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <div class=" uk-text-center">

        <div class="uk-panel uk-panel-box uk-vertical-align-middle uk-margin-top login" style="width: 300px;">
            <h1>Log In!</h1>
            <c:if test="${not empty error}">
                <div class="uk-alert uk-alert-danger" data-uk-alert="">
                    <a href="" class="uk-alert-close uk-close"></a>
                    <p>${error}</p>
                </div>
            </c:if>
            <c:if test="${not empty msg}">
                <div class="uk-alert uk-alert-success" data-uk-alert="">
                    <a href="" class="uk-alert-close uk-close"></a>
                    <p>${msg}</p>
                </div>
            </c:if>
            <form name='loginForm' action="<c:url value='/login' />" method='POST' class="uk-form uk-form-stacked">
                <div class="uk-form-row">
                    <input class="uk-width-1-1 uk-form-large" type="text" name='username' placeholder="Username" autofocus></div>
                <div class="uk-form-row">
                    <input class="uk-width-1-1 uk-form-large" type="password" name='password' placeholder="Password">
                </div>
                <div class="uk-form-row">
                    <input class="uk-width-1-1 uk-button uk-button-primary uk-button-large" name="submit" type="submit"
                           value="Login"/>
                </div>
                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>
