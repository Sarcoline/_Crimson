<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="name" property="name"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard - ${user.name} </title>
</head>
<body>


<%-- Basic data of user : name, email, picture, number of TvShows which user follow --%>
<%-- Option that redirects us to the edit page --%>


<div class="uk-grid">
    <div class="uk-width-4-6 uk-margin-large-top" data-uk-grid-margin=" ">
        <div class="uk-container uk-container-center">
            <div class="login" style="padding: 10px; ">
                <div class="uk-grid">
                    <div class="uk-width-1-3">
                        <c:if test="${user.name == name}">
                            <a href="<c:url value="/user/edit" /> "><i class="fa fa-cog fa-2x"
                                                                       style="color: #999;"
                                                                       aria-hidden="true"></i></a>
                        </c:if>
                        <div>
                            <img src="<c:url value="/images/user/${user.name}"/>" class="center"
                                 style="border-radius: 50%;
                                 <c:if test="${user.name != name}"> margin-top: 0; </c:if> ">
                        </div>
                    </div>
                    <div class="uk-width-1-3 center-user-shows">
                        <ul class="uk-list " style="font-size: 1.8rem">
                            <li style="font-size: 3rem;"><strong>${user.name}</strong></li>
                            <li class="uk-text-muted " style="margin-top: -100px;"><strong>${user.email}</strong></li>
                        </ul>
                    </div>
                    <div class="uk-width-1-3 center-user-info">
                        <ul class="uk-list">
                            <li class=""><strong
                                    style="font-size: 5rem;">${user.tvShows.size()}</strong></li>

                        </ul>
                    </div>
                </div>
            </div>


            <%-- Information when we don't follow none TvShows --%>

            <c:if test="${user.name == name}">
                <div class="uk-width-4-5 ">
                    <div class="" style="padding: 20px;">
                        <c:if test="${tvshows.size() == 0}">
                            <h2 style="text-align: center">${user.name} is not following any tvshows :(</h2>
                        </c:if>
                        <div class="genreList ">
                            <c:forEach items="${tvshows}" var="tv">
                                <a href="<c:url value="/tv/${tv.slug}"/>"> <span class="item-smaller"
                                                                                 style="background-image: url('<c:url
                                                                                         value="/images/tv/${tv.slug}/poster"/>')">
                    <span class="overlay">
                        <span class="item-header">${tv.title}</span> </span>
            </span>
                                </a>
                            </c:forEach>
                        </div>
                    </div>


                    <%-- User can see upcoming episodes his TvShows which he follow --%>

                    <h2>Upcoming episodes
                        <small class="uk-text-muted">${user.setting.daysOfUpcomingEpisodes} days</small>
                    </h2>
                    <c:if test="${upcomimgEpisodes.size() <= 0}">
                        <h3 class="uk-text-muted">There's no upcoming episodes</h3>
                    </c:if>
                    <ul class="uk-list uk-list-line" style="font-size: 1.1rem">
                        <c:forEach items="${upcomimgEpisodes}" var="episode">
                            <li>
                                <p><strong><a href="<c:url value="/tv/${episode.slug}" /> ">
                                        ${episode.title} </a> - S${episode.season}E${episode.number} -
                                </strong>
                                    <a class="watched-this" data-id="${episode.id}"><i class="fa fa-square-o"
                                                                                       aria-hidden="true"></i></a>
                                        ${episode.title}
                                    <small class="episode-date uk-text-muted">${episode.releaseDate}</small>
                                </p>
                            </li>
                        </c:forEach>
                    </ul>

                </div>
            </c:if>
        </div>
    </div>
    <div class="uk-width-2-6">
        <div class="uk-container">


            <%-- Favorite TvShows of user --%>

            <div class="uk-grid">
                <div class="uk-width-1-1 uk-margin-large-top ">
                    <c:if test="${favorites.size() > 0}">
                        <h2 style="text-align: center">Favorites</h2>
                        <c:forEach items="${favorites}" var="tv" begin="0" end="4">
                            <a href="<c:url value="/tv/${tv.slug}"/>"> <span class="item-wide"
                                                                             style="background-image: url('<c:url
                                                                                     value="/images/tv/${tv.slug}/back"/>'); ">
                    <span class="overlay">
                        <span class="item-header">${tv.title}</span> </span>
            </span>
                            </a>
                        </c:forEach>
                    </c:if>
                </div>


                <%-- User can see what he recently watched --%>

                <c:if test="${user.name == name}">
                    <div class="uk-width-1-1 uk-margin-large-top">
                        <c:if test="${watchedEpisodesId.size() > 0}">
                            <h2 class="uk-margin-large-bottom uk-text-center">Recently watched</h2>
                            <ul class="uk-list uk-margin-large-left">
                                <c:forEach items="${watchedEpisodes}" var="episode" begin="0" end="9">

                                    <li>
                                        <p><strong><a href="<c:url value="/tv/${episode.slug}" /> ">
                                                ${episode.tvTitle}</a> </strong> -
                                            S${episode.season}E${episode.number} - ${episode.title}
                                        </p>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </div>
                </c:if>
            </div>

        </div>

    </div>
</div>
<script src="<c:url value="/static/js/userActions.js"/> "></script>
<script>
    $(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
        <sec:authorize access="isAuthenticated()">
        var watched = ${watchedEpisodesId};
        var rateThis = $('.watched-this');
        markWatchedEpisodes(rateThis, watched);
        markAsWatched(rateThis);
        </sec:authorize>
    });
</script>
</body>
</html>
