
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <h1>Add new TvShow</h1>
    <div class="uk-grid uk-margin-large-top">
        <div class="uk-width-1-2">
            <c:if test="${not empty error}">
                <div class="uk-alert uk-alert-danger" data-uk-alert="">
                    <a href="" class="uk-alert-close uk-close"></a>
                    <p>${error}</p>
                </div>
            </c:if>
            <form:form modelAttribute="tv" method="POST" enctype="multipart/form-data" class="uk-form uk-form-stacked">
            <form:errors cssClass="uk-alert uk-alert-danger" element="div"/>
            <div class="uk-form-row">
                <form:errors path="title" cssClass="uk-alert uk-alert-danger" element="div"/>
                <form:input path="title" class="uk-width-1-1 uk-form-large"
                            type="text"
                            name='title'
                            placeholder="Title"/>
            </div>
            <div class="uk-form-row">
                <form:errors path="genre" cssClass="uk-alert uk-alert-danger" element="div"/>
                <form:input path="genre" class="uk-width-1-1 uk-form-large"
                            type="text"
                            name='genre'
                            placeholder="Genre"/>
            </div>
            <div class="uk-form-row">
                <form:errors path="network" cssClass="uk-alert uk-alert-danger" element="div"/>
                <form:input path="network" class="uk-width-1-1 uk-form-large"
                            type="text"
                            name='network'
                            placeholder="Network"/>
            </div>
            <div class="uk-form-row">
                <form:errors path="country" cssClass="uk-alert uk-alert-danger" element="div"/>
                <form:input path="country" class="uk-width-1-1 uk-form-large"
                            type="text"
                            name='country'
                            placeholder="Country"/>
            </div>
            <div class="uk-form-row">
                <form:errors path="releaseYear" cssClass="uk-alert uk-alert-danger" element="div"/>
                <form:input path="releaseYear" class="uk-width-1-1 uk-form-large"
                            type="number"
                            name='releaseYear'
                            placeholder="Release Year"/>
            </div>
            <div class="uk-form-row">
                <form:errors path="description" cssClass="uk-alert uk-alert-danger" element="div"/>
                <form:textarea path="description" class="uk-width-1-1 uk-form-large"
                               type="text"
                               style="height: 200px;"
                               name='description'
                               placeholder="Description"/>
            </div>
            <div class="uk-form-row">
                <form:errors path="trailerUrl" cssClass="uk-alert uk-alert-danger" element="div"/>
                <form:input path="trailerUrl" class="uk-width-1-1 uk-form-large"
                            type="url"
                            name='trailerUrl'
                            placeholder="Trailer URL"/>
            </div>
            <form:input path="slug" value=" " type="hidden"/>
            <form:input path="overallRating" value="5" type="hidden"/>
            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
            <div class="uk-form-row uk-margin-top">
                <input class="uk-width-1-1 uk-button uk-button-primary uk-button-large" name="submit"
                       type="submit"
                       value="Save"/>
                <a href="<c:url value="/"/>" class="uk-width-1-1 uk-button uk-button-large">Back</a>
            </div>
        </div>
        <div class="uk-width-1-2">
            <div class="uk-form-row">
                <form:errors path="back" cssClass="uk-alert uk-alert-danger" element="div"/>
                <form:label path="back" for="back" class="uk-form-label">Back</form:label>
                <form:input path="back" value="" class="uk-width-1-1 uk-form-large" type="file" name="back"/>
            </div>
            <div class="uk-form-row">
                <form:errors path="poster" cssClass="uk-alert uk-alert-danger" element="div"/>
                <form:label path="poster" for="poster" class="uk-form-label">Poster</form:label>
                <form:input path="poster" value="" class="uk-width-1-1 uk-form-large" type="file" name="poster"/>
            </div>
            <div class="uk-form-row">
                <form:errors path="pic1" cssClass="uk-alert uk-alert-danger" element="div"/>
                <form:label path="pic1" for="pic1" class="uk-form-label">Picture 1</form:label>
                <form:input path="pic1" value="" class="uk-width-1-1 uk-form-large" type="file" name="pic1"/>
            </div>
            <div class="uk-form-row">
                <form:errors path="pic2" cssClass="uk-alert uk-alert-danger" element="div"/>
                <form:label path="pic2" for="pic2" class="uk-form-label">Picture 2</form:label>
                <form:input path="pic2" value="" class="uk-width-1-1 uk-form-large" type="file" name="pic2"/>
            </div>
            <div class="uk-form-row">
                <form:errors path="pic3" cssClass="uk-alert uk-alert-danger" element="div"/>
                <form:label path="pic3" for="poster" class="uk-form-label">Picture 3</form:label>
                <form:input path="pic3" value="" class="uk-width-1-1 uk-form-large" type="file" name="pic3"/>
            </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>
