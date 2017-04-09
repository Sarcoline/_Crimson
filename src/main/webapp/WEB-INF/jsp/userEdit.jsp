<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit ${userDTO.name}</title>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">


    //Edit profile information : name, email, password


    <h2 class="uk-margin-large-top">Edit account information!</h2>
    <form:form modelAttribute="userDTO" method="POST" enctype="utf8" class="uk-form uk-form-stacked">
        <form:errors cssClass="uk-alert uk-alert-danger" element="div"/>
        <div class="uk-form-row">
            <form:errors path="name" cssClass="uk-alert uk-alert-danger" element="div"/>
            <form:input path="name" value="${userDTO.name}" class="uk-width-1-2 uk-form-large"
                        type="text"
                        name='name'
                        placeholder="Username" readOnly="true"/>
        </div>
        <div class="uk-form-row">
            <form:errors path="email" cssClass="uk-alert uk-alert-danger" element="div"/>
            <form:input path="email" value="${userDTO.email}" class="uk-width-1-2 uk-form-large"
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
            <input class="uk-button uk-button-primary " name="submit"
                   type="submit"
                   value="Save"/>
            <a class="uk-button"
               href="<c:url value="/user/updatePassword"/> ">Change
                Password</a>
        </div>

    </form:form>

    //Edit settings about days of upcoming episodes and episode list

    <h2 class="uk-margin-large-top">Edit settings </h2>
    <form class="uk-form uk-form-stacked" >
        <div class="uk-form-row">
        <label class="uk-form-label" for="days">Days of upcoming episodes</label>
        <input type="number" name="days" placeholder="Days of upcoming episodes"
               id="days" value="${settings.daysOfUpcomingEpisodes}"/>
        </div>
        <div class="uk-form-row">
        <label class="uk-form-label" for="days">Send me daily episode list</label>
            <select name="select" id="sendMail">

                <option value="true">Send</option>
                <option value="false" <c:if test="${!userDTO.setting.sendEpisodeList}"> selected
                </c:if>>Turn off</option>
            </select>
        </div>
        <div class="uk-form-row">
        <a class="uk-button uk-button-primary" id="saveSettings">Save</a>
        </div>
    </form>

    //Edit profile picture

    <h2 class="uk-margin-large-top">Change profile picture</h2>
    <img src="<c:url value="/images/user/${userDTO.name}"/> " alt="" width="200" height="200">
    <form class="uk-form uk-margin-top" method="post" action="<c:url value="/updatePicture" />"
          enctype="multipart/form-data">
        <div class="uk-form-row uk-form-file">
            <button class="uk-button">Select profile picture</button>
            <input type="file" name="fileUpload" size="50" onchange="previewFile()"/>
        </div>

        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
        <input class="uk-button uk-button-primary" type="submit" value="Save"/>
    </form>

    //Option to delete our account

    <button class="uk-button uk-button-danger uk-margin-large-top" type="button"
            data-uk-modal="{target:'#myy-id'}">
        Delete account
    </button>

</div>

//Confirmation to delete our account

<div id="myy-id" class="uk-modal">
    <div class="uk-modal-dialog">
        <a class="uk-modal-close uk-close"></a>
        <div class="uk-modal-header">
            <h2 class="uk-text-center">This action will delete your account.<br>Are you sure?</h2>
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

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $('#saveSettings').on('click', function () {
        var e = document.getElementById("sendMail");
        var send = e.options[e.selectedIndex].value;
        var days = $('#days').val();
        console.log(send);
        console.log(days);
        $.ajax({
            type: "post",
            url: "/api/updateSettings",
            data: {days: days, send: send},
            success: function () {
                UIkit.notify({
                    message: 'Settings updated',
                    status: 'info',
                    timeout: 3000,
                    pos: 'bottom-right'
                });
            },
            error: function () {
                console.log('not ok')
            }
        });
    });
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
