<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sec:authentication var="name" property="name"/>

<html>
<head>
    <title>Crimson</title>
</head>
<body>
<div class="index">

    //Welcome for user if is authenticated or is anonymous

    <div class="uk-container uk-container-center">
        <sec:authorize access="isAuthenticated()">
            <h1 class="welcome uk-margin-large-top">Hello ${name}!</h1>
        </sec:authorize>
        <sec:authorize access="isAnonymous()">
            <h1 class="welcome uk-margin-large-top">Welcome in <strong class="crimson"> Crimson </strong></h1>
        </sec:authorize>


        //Slider with TvShows

        <div class="uk-grid">
            <div class="top-10 uk-margin-large-top uk-width-1-1">
                <h2>Top 10</h2>
                <div class="uk-slidenav-position" data-uk-slider="{infinite: true}">
                    <div class="uk-slider-container">
                        <ul class="uk-slider uk-grid-width-small-1-2 uk-grid-width-medium-1-4 uk-grid-width-large-1-5">
                            <c:forEach items="${tvShows}" var="tvShow" varStatus="status">
                                <li><a href="<c:url value="/tv/${tvShow.slug}"/>">
                <span class="item" style="background-image: url('<c:url value="/images/tv/${tvShow.slug}/poster"/>')">
                    <span class="overlay"><span class="item-header">${status.count}. ${tvShow.title}</span> </span>
            </span>
                                </a></li>
                            </c:forEach>
                        </ul>
                    </div>
                    <a href="<c:url value="/"/>" class="uk-slidenav uk-slidenav-contrast uk-slidenav-previous"
                       data-uk-slider-item="previous"></a>
                    <a href="<c:url value="/"/>" class="uk-slidenav uk-slidenav-contrast uk-slidenav-next"
                       data-uk-slider-item="next"></a>
                </div>
            </div>

            //Here we have got latest reviews

            <div class="new-reviews uk-margin-top uk-width-1-2 ">
                <h2>Latest reviews</h2>
                <div class="">
                    <ul class="uk-list uk-list-line">
                        <c:forEach items="${reviews}" var="review">
                            <li>
                                <article class="uk-article uk-margin-top">

                                    <p class="uk-article-lead"><a
                                            href="<c:url value="/tv/${review.tvShow.slug}"/> ">${review.tvShow.title}</a>
                                        - ${review.title}</p>
                                    <p class="uk-article-meta">By <a href="<c:url value="/user/${review.user.name}"/> ">
                                            ${review.user.name}</a> on ${review.publicationDate}</p>
                                        ${review.introduction} <a
                                        href="<c:url value="/tv/${review.tvShow.slug}/reviews/${review.id}"/> "
                                        style="color: #00a8e6;">Read more</a>
                                </article>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>

    </div>
</div>
</body>
</html>
