<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Meow
  Date: 20.02.2017
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <h1>Edit ${episodes.get(0).episodeFromTvShow.title} episodes</h1>
    <div class="uk-grid uk-grid-large uk-margin-large-top">
        <%--action="<c:url value="/tv/${episode.episodeFromTvShow.slug}/edit/episodes"/>"--%>
        <c:forEach begin="1" end="${seasons}" varStatus="count">
            <ul class="uk-list uk-list-striped uk-width-1-1 uk-margin-top">
                <h3>Season ${count.count}</h3>
                <c:forEach items="${episodes}" var="episode">
                    <c:if test="${episode.season == count.count}">
                        <li>
                            <strong>S${episode.season}E${episode.number} -</strong>
                            <span>${episode.title} -</span>
                            <span>${episode.episodeSummary}</span>
                            <span class="uk-text-muted uk-margin-left">${episode.releaseDate}</span>
                            <a href="/tv/${episode.episodeFromTvShow.slug}/edit/episodes/${episode.id}"
                               style="float: right;" class="uk-button uk-button-primary  uk-button-small">Edit</a>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </c:forEach>
            <a href="<c:url value="/tv/${episodes.get(0).episodeFromTvShow.slug}/edit/"/>"
               class="uk-button uk-margin-top uk-button-large">Back</a>
    </div>
</div>
</body>
</html>
