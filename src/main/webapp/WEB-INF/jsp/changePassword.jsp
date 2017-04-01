<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<html>
<body>
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
            <form:form modelAttribute="passwordDTO" action="/user/updatePassword" method="POST" enctype="utf8"
                       class="uk-form uk-form-stacked">
                <form:errors cssClass="uk-alert uk-alert-danger" element="div"/>
                <div class="uk-form-row">
                    <form:errors path="oldPassword" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:input path="oldPassword" value="" class="uk-width-1-1 uk-form-large" type="password"
                                name='oldPassword'
                                placeholder="Old password"/>
                </div>
                <div class="uk-form-row">
                    <form:errors path="password" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:input path="password" value="" class="uk-width-1-1 uk-form-large" type="password"
                                name='password'
                                placeholder="New password"/>
                </div>
                <div class="uk-form-row">
                    <form:errors path="matchingPassword" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:input path="matchingPassword" value="" class="uk-width-1-1 uk-form-large" type="password"
                                name='matchingPassword'
                                placeholder="Confirm new password"/>
                </div>
                <div class="uk-form-row">
                    <input class="uk-width-1-1 uk-button uk-button-primary uk-button-large" name="submit" type="submit"
                           value="Change password"/>
                    <a href="<c:url value="/user/edit"/>" class="uk-width-1-1 uk-button uk-button-large">Back</a>
                </div>
                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>
