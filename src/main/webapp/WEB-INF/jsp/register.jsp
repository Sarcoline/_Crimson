<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Meow
  Date: 06.01.2017
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <div class=" uk-text-center">
        <div class="uk-panel uk-panel-box uk-vertical-align-middle uk-margin-top login" style="width: 300px;">
            <h1>Register!</h1>
            <form:form modelAttribute="userDTO" method="POST" enctype="utf8" class="uk-form uk-form-stacked">
                <form:errors cssClass="uk-alert uk-alert-danger" element="div"/>
                <div class="uk-form-row">
                    <form:input path="name" value="" class="uk-width-1-1 uk-form-large" type="text" name='username' placeholder="Username" />
                    <form:errors path="name" cssClass="uk-alert uk-alert-danger" element="div"/>
                </div>
                <div class="uk-form-row">
                    <form:input path="password" value="" class="uk-width-1-1 uk-form-large" type="password" name='password' placeholder="Password" />
                    <form:errors path="password" cssClass="uk-alert uk-alert-danger" element="div"/>
                </div>
                <div class="uk-form-row">
                    <form:input path="email" value="" class="uk-width-1-1 uk-form-large" type="text" name='email' placeholder="Email" />
                        <%--TODO zmienic wyświetlanie błędów--%>
                    <form:errors path="email" cssClass="uk-alert uk-alert-danger" element="div"/>
                </div>
                <div class="uk-form-row">
                    <input class="uk-width-1-1 uk-button uk-button-primary uk-button-large" name="submit" type="submit"
                           value="Register"/>
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>
