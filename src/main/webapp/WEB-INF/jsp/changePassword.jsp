<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<html>
<body>
<div id="errormsg" style="display:none"></div>
<div class="uk-container uk-container-center uk-margin-large-top">
    <div class=" uk-text-center">
        <div class="uk-panel uk-panel-box uk-vertical-align-middle uk-margin-top login" style="width: 300px;">
            <c:if test="${not empty error}">
                <div class="uk-alert uk-alert-danger" data-uk-alert="">
                    <a href="" class="uk-alert-close uk-close"></a>
                    <p>${error}</p>
                </div>
            </c:if>
            <form class="uk-form" id="form" action="<c:url value="/tv/user/updatePassword"/>" method="post">
                <div class="uk-form-row">
                    <input class="uk-width-1-1 uk-form-large" id="oldPassword" name="oldPassword" type="password" placeholder="Old password" required/>
                </div>
                <div class="uk-form-row">
                    <input class="uk-width-1-1 uk-form-large" id="pass" name="password" type="password" placeholder="New password" required/>
                </div>
                <div class="uk-form-row">
                    <input class="uk-width-1-1 uk-form-large" name="passwordConfirm" id="passConfirm" type="password" placeholder="New password confirmation" required/>
                </div>
                <div class="uk-form-row">
                    <input class="uk-width-1-1 uk-button uk-button-primary uk-button-large" name="submit" type="submit"
                           value="Change password"/>
                </div>
                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>
