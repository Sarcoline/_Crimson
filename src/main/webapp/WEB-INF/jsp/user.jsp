<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="name" property="name"/>
<%--
  Created by IntelliJ IDEA.
  User: adm
  Date: 2017-01-16
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="uk-grid">
    <div class="uk-width-2-6 uk-margin-large-top" data-uk-grid-margin=" ">
        <div class="uk-container uk-container-center">
            <div class="login" style="padding: 10px; margin-bottom: 40px;">
                <c:if test="${user.name == name}">
                    <a href="<c:url value="/tv/user/edit" /> "><i class="fa fa-cog fa-2x" style="color: #999; margin-bottom: -20px;" aria-hidden="true"></i></a>
                </c:if>
                <div>
                    <img src="http://www.iconsfind.com/wp-content/uploads/2015/08/20150831_55e46ad551392.png"
                         width="200" height="200" class="center" style="margin-bottom: 20px;">
                </div>
                <ul class="uk-list uk-text-center " style="font-size: 1.8rem">
                    <li style="font-size: 2.5rem; margin-bottom: 5px;"><strong>${user.name}</strong></li>
                    <li class="uk-text-muted"><strong>${user.email}</strong></li>
                </ul>
            </div>
            <h2 style="text-align: center">Favorites</h2>
            <div class="uk-slidenav-position" data-uk-slider="{infinite: true}">
                <div class="uk-slider-container">
                    <ul class="uk-slider uk-grid-width-medium-1-4">
                        <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item1" style="background-image: url('<c:url value="/images/tv/game-of-thrones/poster"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                        </a></li>
                        <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item1" style="background-image: url('<c:url value="/images/tv/game-of-thrones/poster"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                        </a></li>
                        <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item1" style="background-image: url('<c:url value="/images/tv/game-of-thrones/poster"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                        </a></li>
                        <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item1" style="background-image: url('<c:url value="/images/tv/game-of-thrones/poster"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                        </a></li>
                        <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item1" style="background-image: url('<c:url value="/images/tv/game-of-thrones/poster"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                        </a></li>
                        <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item1" style="background-image: url('<c:url value="/images/tv/game-of-thrones/poster"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                        </a></li>

                    </ul>
                </div>
                <a href="/" class="uk-slidenav uk-slidenav-contrast uk-slidenav-previous"
                   data-uk-slider-item="previous"></a>
                <a href="/" class="uk-slidenav uk-slidenav-contrast uk-slidenav-next" data-uk-slider-item="next"></a>
            </div>
        </div>
    </div>
    <div style="border-left: 2px solid #00a8e6;" class="uk-width-4-6 uk-margin-large-top">
        <div class="uk-container uk-container-center">
            <h2 class="uk-article-title" style="text-align:center">${user.name} TvShows</h2>
            <div class="genreList uk-margin-large-top">
                <c:forEach items="${tvshows}" var="tv">
                    <a href="<c:url value="/tv/${tv.slug}"/>"> <span class="item1" style="background-image: url('<c:url
                            value="/images/tv/${tv.slug}/poster"/>')">
                    <span class="overlay">
                        <span class="item-header">${tv.title}</span> </span>
            </span>
                    </a>
                </c:forEach>
            </div>
        </div>

    </div>
</div>


</body>
</html>
