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
            <h1>Edit your account!</h1>
            <c:if test="${not empty error}">
                <div class="uk-alert uk-alert-danger" data-uk-alert="">
                    <a href="" class="uk-alert-close uk-close"></a>
                    <p>${error}</p>
                </div>
            </c:if>
            <form:form modelAttribute="userDTO" method="POST" enctype="utf8" class="uk-form uk-form-stacked">
                <form:errors cssClass="uk-alert uk-alert-danger" element="div"/>
                <div class="uk-form-row">
                    <form:errors path="name" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:input path="name" value="${userDTO.name}" class="uk-width-1-1 uk-form-large" type="text" name='name'
                                placeholder="Username" readOnly="true"/>
                </div>
                <div class="uk-form-row">
                    <form:errors path="email" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:input path="email" value="${userDTO.email}" class="uk-width-1-1 uk-form-large" type="text" name='email'
                                placeholder="Email"/>
                        <%--TODO zmienic wyświetlanie błędów--%>
                </div>
                <div class="uk-form-row">
                <form:input path="id" value="${userDTO.id}" type="hidden"/>
                <form:input path="profilePic" value="${userDTO.profilePic}" type="hidden" />
                <form:input path="password" value="${userDTO.password}" type="hidden"/>
                </div>
                <div class="uk-form-row">
                    <input class="uk-width-1-1 uk-button uk-button-primary uk-button-large" name="submit" type="submit"
                           value="Save"/>
                    <a href="<c:url value="/tv/user/delete" /> "><i class="fa fa-cog fa-2x"
                                                                  style="color: #999; margin-bottom: -30px;"
                                                                  aria-hidden="true"></i></a>
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>
