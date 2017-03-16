<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <div class=" uk-text-center">
        <div class="uk-panel uk-panel-box uk-vertical-align-middle uk-margin-top login" style="width: 400px;">
            <h1>Register!</h1>

            <form:form modelAttribute="userDTO" method="POST" enctype="multipart/form-data" class="uk-form uk-form-stacked" >
                <form:errors cssClass="uk-alert uk-alert-danger" element="div"/>
                <div class="uk-form-row">
                    <form:errors path="name" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:input path="name" value="" class="uk-width-1-1 uk-form-large" type="text" name='username'
                                placeholder="Username"/>
                </div>
                <div class="uk-form-row">
                    <form:errors path="password" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:input path="password" value="" class="uk-width-1-1 uk-form-large" type="password"
                                name='password' placeholder="Password"/>
                </div>
                <div class="uk-form-row">
                    <form:errors path="matchingPassword" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:input path="matchingPassword" value="" class="uk-width-1-1 uk-form-large" type="password"
                                name='matchingPassword' placeholder="Confirm password"/>
                </div>
                <div class="uk-form-row">
                    <form:errors path="email" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:input path="email" value="" class="uk-width-1-1 uk-form-large" type="text" name='email'
                                placeholder="Email"/>
                </div>
                <div class="uk-form-row uk-form-file" >
                    <form:errors path="uploadedPic" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <button class="uk-button">Select profile picture</button>
                    <form:input path="uploadedPic" value="" class="uk-width-1-1 uk-form-large" type="file" name="profilePic"/>
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
