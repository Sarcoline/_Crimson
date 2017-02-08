<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sec:authentication var="name" property="name"/>

<html>
<head>
    <title>Title</title>
</head>
<body>

<div class="uk-container uk-container-center uk-margin-large-top">
    <sec:authorize access="isAuthenticated()">
        <h1>Hello ${name}!</h1>
    </sec:authorize>
    <sec:authorize access="isAnonymous()">
        <h1 id="hej">Hello World!</h1>
    </sec:authorize>
    <h2 class="uk-margin-large-top">Top 10</h2>
    <div class="uk-slidenav-position" data-uk-slider="{infinite: true}">
        <div class="uk-slider-container">
            <ul class="uk-slider uk-grid-width-medium-1-5">
                <c:forEach items="${tvShows}" var="tvShow" varStatus="status">
                <li><a href="<c:url value="/tv/${tvShow.slug}"/>">
                <span class="item" style="background-image: url('<c:url value="/images/tv/${tvShow.slug}/poster"/>')">
                    <span class="overlay"><span class="item-header">${status.count}. ${tvShow.title}</span> </span>
            </span>
                </a></li>
                </c:forEach>
            </ul>
        </div>
        <a href="/" class="uk-slidenav uk-slidenav-contrast uk-slidenav-previous" data-uk-slider-item="previous"></a>
        <a href="/" class="uk-slidenav uk-slidenav-contrast uk-slidenav-next" data-uk-slider-item="next"></a>
    </div>


</div>
</body>
</html>
