<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Meow
  Date: 24.03.2017
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div sec:authorize="hasAuthority('CHANGE_PASSWORD_PRIVILEGE')">
    <div class="uk-container uk-container-center uk-margin-large-top">
        <div class=" uk-text-center">
            <div class="uk-panel uk-panel-box uk-vertical-align-middle uk-margin-top login" style="width: 400px;">
                <h1>Change password</h1>
                <c:if test="${not empty error}">
                    <div class="uk-alert uk-alert-danger" data-uk-alert="">
                        <a href="" class="uk-alert-close uk-close"></a>
                        <p>${error}</p>
                    </div>
                </c:if>
                <form:form modelAttribute="passwordResetDTO" action="/user/changePassword" method="POST" enctype="utf8"
                           class="uk-form uk-form-stacked">
                    <form:errors cssClass="uk-alert uk-alert-danger" element="div"/>
                    <div class="uk-form-row">
                        <form:errors path="password" cssClass="uk-alert uk-alert-danger" element="div"/>
                        <form:input path="password" value="" class="uk-width-1-1 uk-form-large" type="password"
                                    name='password' id="password"
                                    placeholder="New password"/>
                    </div>
                    <div class="uk-form-row">
                        <form:errors path="matchingPassword" cssClass="uk-alert uk-alert-danger" element="div"/>
                        <form:input path="matchingPassword" value="" class="uk-width-1-1 uk-form-large" type="password"
                                    name='matchingPassword' id="matchingPassword"
                                    placeholder="Confirm new password"/>
                        <form:hidden path="token" value="${param.token}"/>
                    </div>

                    <div class="uk-form-row">
                        <input class="uk-width-1-1 uk-button uk-button-primary uk-button-large" name="submit"
                               type="submit"
                               value="Change password"/>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script>
    $(":password").keyup(function () {
        if ($("#password").val() !== $("#matchPassword").val()) {
            $("#globalError").show().html("o chuj");
        } else {
            $("#globalError").html("").hide();
        }
    });
</script>
</body>
</html>
