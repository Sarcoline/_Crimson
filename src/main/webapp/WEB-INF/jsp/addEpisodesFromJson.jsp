<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <h1>Add episodes </h1>
    <p id="loading"></p>
    <a class="uk-button uk-button-primary" id="saveAll">Add all (risky) </a>
    <div class="uk-grid uk-grid-large uk-margin-large-top">
        <div>
            <ul id="test" class="uk-list uk-list-line">

            </ul>
        </div>
    </div>
</div>
<script src="<c:url value="/static/js/addEpisodesFromApi.js"/> ">
</script>
<script>
    addEpisodes('${name}','${id}');
</script>
</body>
</html>
