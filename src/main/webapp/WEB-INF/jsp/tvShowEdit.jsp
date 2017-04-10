<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit ${tv.title}</title>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <h1>Edit ${tv.title}</h1>
    <div class="uk-grid uk-margin-large-top">
        <div class="uk-width-1-2">
            <c:if test="${not empty error}">
                <div class="uk-alert uk-alert-danger" data-uk-alert="">
                    <a href="" class="uk-alert-close uk-close"></a>
                    <p>${error}</p>
                </div>
            </c:if>

            <%-- Form to edit TvShow --%>

            <form:form modelAttribute="tv" method="POST" enctype="utf8" class="uk-form uk-form-stacked">
                <form:errors cssClass="uk-alert uk-alert-danger" element="div"/>
                <div class="uk-form-row">
                    <form:errors path="title" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:input path="title" value="${tv.title}" class="uk-width-1-1 uk-form-large"
                                type="text"
                                name='title'
                                placeholder="Title"/>
                </div>
                <div class="uk-form-row">
                    <form:errors path="genre" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:input path="genre" value="${tv.genre}" class="uk-width-1-1 uk-form-large"
                                type="text"
                                name='genre'
                                placeholder="Genre"/>
                </div>
                <div class="uk-form-row">
                    <form:errors path="network" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:input path="network" value="${tv.network}" class="uk-width-1-1 uk-form-large"
                                type="text"
                                name='network'
                                placeholder="Network"/>
                </div>
                <div class="uk-form-row">
                    <form:errors path="country" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:input path="country" value="${tv.country}" class="uk-width-1-1 uk-form-large"
                                type="text"
                                name='country'
                                placeholder="Country"/>
                </div>
                <div class="uk-form-row">
                    <form:errors path="releaseYear" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:input path="releaseYear" value="${tv.releaseYear}" class="uk-width-1-1 uk-form-large"
                                type="number"
                                name='releaseYear'
                                placeholder="Release Year"/>
                </div>
                <div class="uk-form-row">
                    <form:errors path="description" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:textarea path="description" value="${tv.description}" class="uk-width-1-1 uk-form-large"
                                   type="text"
                                   style="height: 200px;"
                                   name='description'
                                   placeholder="Description"/>
                </div>
                <div class="uk-form-row">
                    <form:errors path="trailerUrl" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:input path="trailerUrl" value="${tv.trailerUrl}" class="uk-width-1-1 uk-form-large"
                                type="url"
                                name='trailerUrl'
                                placeholder="Trailer URL"/>
                </div>
                <form:input path="slug" value="${tv.slug}" type="hidden"/>
                <form:input path="overallRating" value="${tv.overallRating}" type="hidden"/>
                <form:input path="id" value="${tv.id}" type="hidden"/>
                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
                <div class="uk-form-row uk-margin-top">
                    <input class="uk-width-1-1 uk-button uk-button-success uk-button-large" name="submit"
                           type="submit"
                           value="Save"/>
                    <a href="<c:url value="/tv/${tv.slug}/edit/episodes"/>" class="uk-width-1-1 uk-button
                    uk-button-primary uk-button-large">Episodes</a>
                    <a href="<c:url value="/tv/${tv.slug}"/>" class="uk-width-1-1 uk-button uk-button-large">Back</a>
                </div>
            </form:form>
        </div>
        <div class="uk-width-1-2">
            <c:forEach items="${tv.pictures}" var="pictures" varStatus="status">
                <form class="uk-form uk-margin-bottom" method="post"
                      action="<c:url value="/tv/${tv.slug}/updatePicture" />" enctype="multipart/form-data">
                    <img src="<c:url value="/images/tv/${tv.slug}/${pictures.key}"/>" width="200" height="200">
                    <br>
                    <input type="file" name="pic1" required/>
                    <input type="hidden" name="key" value="${pictures.key}"/>
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>
                    <input class="uk-button uk-button-primary" type="submit" value="Save"/>
                </form>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
