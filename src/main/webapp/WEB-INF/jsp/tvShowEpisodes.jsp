<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${name} episodes</title>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <h1>Edit ${name} episodes</h1>
    <div class="uk-grid uk-grid-large uk-margin-large-top">
        <%--action="<c:url value="/tv/${episode.episodeFromTvShow.slug}/edit/episodes"/>"--%>
        <c:forEach begin="1" end="${seasons}" varStatus="count">
            <ul class="uk-list uk-list-line uk-width-1-1 uk-margin-top">
                <h3>Season ${count.count}</h3>
                <c:forEach items="${episodes}" var="episode">
                    <c:if test="${episode.season == count.count}">
                        <li>
                            <strong>S${episode.season}E${episode.number} -</strong>
                            <span>${episode.title} -</span>
                            <span class="uk-text-muted ">${episode.releaseDate}</span>
                            <span style="float: right;">
                            <a href="/tv/${episode.tvShow.slug}/edit/episodes/${episode.id}"
                               class="uk-button uk-button-primary uk-button-small">Edit</a>
                            <a href="/tv/${episode.tvShow.slug}/edit/episodes/${episode.id}/delete"
                               class="uk-button uk-button-danger uk-button-small">Delete</a>
                                </span>
                            <span>${episode.episodeSummary}</span>

                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </c:forEach>
        <a href="<c:url value="/tv/${name}/edit/"/>"
           class="uk-button uk-margin-top">Back</a>
        <a href="<c:url value="/tv/${name}/edit/episodes/add/"/>"
           class="uk-button uk-button-success uk-margin-top" style="float: right">Add</a>
        <a href="<c:url value="/tv/${name}/edit/episodes/addSearch/"/>"
           class="uk-button uk-button-primary uk-margin-top">Try to add from external api</a>
    </div>
</div>
</body>
</html>
