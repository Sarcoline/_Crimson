<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit ${userDTO.name}</title>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <div class=" uk-text-center">
        <div class="uk-grid">
            <div class="uk-width-1-3">
                <h2 class="uk-margin-large-top">Edit settings </h2>
                <form class="uk-form " method="post" action="<c:url value="/updateSettings" />">
                    <input type="number" name="days" placeholder="Days of upcoming episodes"
                           value="${settings.daysOfUpcomingEpisodes}"/>
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>
                    <input class="uk-button uk-button-primary" type="submit" value="Save"/>
                </form>
            </div>
            <div class="uk-width-1-3">
                <h2 class="">Change profile picture</h2>
                <img src="<c:url value="/images/user/${userDTO.name}"/> " alt="" width="300" height="300">
                <form class="uk-form uk-margin-top" method="post" action="<c:url value="/updatePicture" />"
                      enctype="multipart/form-data">
                    <input type="file" name="fileUpload" size="50" onchange="previewFile()"/>
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>
                    <input class="uk-button uk-button-primary" type="submit" value="Save"/>
                </form>
            </div>
            </div>
            <div class="uk-width-1-3">
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
                            <form:input path="name" value="${userDTO.name}" class="uk-width-1-1 uk-form-large"
                                        type="text"
                                        name='name'
                                        placeholder="Username" readOnly="true"/>
                        </div>
                        <div class="uk-form-row">
                            <form:errors path="email" cssClass="uk-alert uk-alert-danger" element="div"/>
                            <form:input path="email" value="${userDTO.email}" class="uk-width-1-1 uk-form-large"
                                        type="text"
                                        name='email'
                                        placeholder="Email"/>
                        </div>
                        <form:input path="profilePic" value="${userDTO.profilePic}" type="hidden"/>
                        <form:input path="matchingPassword" value="${userDTO.password}" type="hidden"/>
                        <form:input path="id" value="${userDTO.id}" type="hidden"/>
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>
                        <div class="uk-form-row">
                            <form:input path="password" value="${userDTO.password}" type="hidden"/>
                        </div>
                        <div class="uk-form-row">
                            <input class="uk-width-1-1 uk-button uk-button-primary uk-button-large" name="submit"
                                   type="submit"
                                   value="Save"/>
                        </div>
                    </form:form>
                    <button class="uk-width-1-1 uk-button uk-button-danger uk-button-large" type="button"  data-uk-modal="{target:'#myy-id'}">
                        Delete


                    </button>

                    <a class="uk-float-left uk-text-small uk-margin-top"
                       href="<c:url value="/user/updatePassword"/> ">Change
                        Password</a>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="myy-id" class="uk-modal">
    <div class="uk-modal-dialog">
        <a class="uk-modal-close uk-close"></a>
        <div class="uk-modal-header">
            <h2 class="uk-text-center">This action will delete your account.</br>Are you sure?</h2>
            <div class="uk-grid">
                <div class="uk-container-center">

                    <a class="uk-button uk-button-danger" href="<c:url value="/user/delete/"/>">Delete</a>
                    <a class="uk-button uk-button-default uk-modal-close">Cancel</a>
                </div>
            </div>


        </div>

    </div>
</div>
<script>
    var previewFile = function () {
        var preview = document.querySelector('img');
        var file = document.querySelector('input[type=file]').files[0];
        var reader = new FileReader();

        reader.onloadend = function () {
            preview.src = reader.result;
        };
        if (file) {
            reader.readAsDataURL(file);
        } else {
            preview.src = "<c:url value="/images/user/${userDTO.name}"/> ";
        }
    };
</script>
</body>
</html>
