<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit ${episode.title}</title>
    <content tag="local_script">
        <script src="<c:url value='/static/uikit/js/components/datepicker.min.js' />"
                type="application/javascript"></script>
    </content>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <h1>Edit ${episode.title} of ${episode.tvShow.title}</h1>
    <div class="uk-grid uk-margin-large-top">
        <div class="uk-width-1-1">
            <c:if test="${not empty error}">
                <div class="uk-alert uk-alert-danger" data-uk-alert="">
                    <a href="" class="uk-alert-close uk-close"></a>
                    <p>${error}</p>
                </div>
            </c:if>
            <form:form modelAttribute="episode" method="POST" enctype="utf8" class="uk-form uk-form-stacked">
                <form:errors cssClass="uk-alert uk-alert-danger" element="div"/>
                <div class="uk-form-row">
                    <form:errors path="title" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:label path="title" for="title" class="uk-form-label">Title</form:label>
                    <form:input path="title" value="${episode.title}" class="uk-width-1-2 uk-form-large"
                                type="text"
                                name='title'
                                placeholder="Title"/>

                </div>
                <div class="uk-form-row">
                    <form:errors path="episodeSummary" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:label path="episodeSummary" for="episodeSummary"
                                class="uk-form-label">Episode Summary</form:label>
                    <form:textarea path="episodeSummary" value="${episode.episodeSummary}"
                                   class="uk-width-1-2 uk-form-large"
                                   name='episodeSummary'
                                   placeholder="Episode Summary"/>
                </div>
                <div class="uk-form-row">
                    <form:errors path="releaseDate" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:label path="releaseDate" for="releaseDate"
                                class="uk-form-label">Episode releaseDate</form:label>
                    <form:input path="releaseDate" value="${episode.releaseDate}" class="uk-form-large"
                                name='releaseDate' data-uk-datepicker="{format:'YYYY-MM-DD'}" type="text"
                                placeholder="Episode releaseDate"/>

                </div>
                <div class="uk-form-row">
                    <form:errors path="number" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:label path="number" for="number" class="uk-form-label">Episode number</form:label>
                    <form:input path="number" value="${episode.number}" class="uk-form-large"
                                name='number' type="number" style="width: 50px;"
                                placeholder="Episode number"/>

                </div>
                <div class="uk-form-row">
                    <form:errors path="season" cssClass="uk-alert uk-alert-danger" element="div"/>
                    <form:label path="season" for="season" class="uk-form-label">Episode season</form:label>
                    <form:input path="season" value="${episode.season}" class="uk-form-large"
                                name='season' type="number" style="width: 50px;"
                                placeholder="Episode season"/>
                </div>
                <form:input path="id" value="${episode.id}" type="hidden"/>
                <form:input path="idTvShow" value="${episode.idTvShow}" type="hidden"/>
                <a href="<c:url value="/tv/${episode.tvShow.slug}/edit/episodes"/>"
                   class="uk-button uk-margin-top">Back</a>
                <input class=" uk-button uk-button-success uk-margin-top" name="submit"
                       type="submit"
                       value="Save"/>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>
