<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Write review for ${tv.title}</title>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <h1>Write review for ${tv.title}</h1>
    <form:form modelAttribute="review" method="POST" enctype="utf8" class="uk-form uk-form-stacked">
        <form:errors cssClass="uk-alert uk-alert-danger" element="div"/>
        <div class="uk-form-row">
            <form:errors path="title" cssClass="uk-alert uk-alert-danger" element="div"/>
            <form:label path="title" for="title" class="uk-form-label">Title</form:label>
            <form:input path="title"
                           class="uk-width-1-2 uk-form-large"
                           name='title'
                           placeholder="Title"/>
        </div>
        <div class="uk-form-row">
            <form:errors path="introduction" cssClass="uk-alert uk-alert-danger" element="div"/>

            <form:textarea path="introduction"
                           class="uk-width-1-2 uk-form-large"
                           name='introduction'
                           placeholder="Introduction"/>
        </div>
        <div class="uk-form-row">
            <form:errors path="content" cssClass="uk-alert uk-alert-danger" element="div"/>
            <form:label path="content" for="content" class="uk-form-label">Review</form:label>
            <form:textarea path="content"
                           class="uk-width-1-2 uk-form-large"
                           name='content'
                           placeholder="content" data-uk-htmleditor="{markdown:true}"/>
        </div>
        <a href="<c:url value="/tv/${tv.slug}/"/>"
           class="uk-button uk-margin-top">Back</a>
        <input class=" uk-button uk-button-success uk-margin-top" name="submit"
               type="submit"
               value="Save"/>
    </form:form>
</div>
</body>
</html>
