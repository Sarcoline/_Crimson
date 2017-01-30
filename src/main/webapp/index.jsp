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
                <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item" style="background-image: url('<c:url value="/images/tv/game-of-thrones/poster"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                </a></li>
                <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item" style="background-image: url('<c:url value="/images/tv/prison-break/poster"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                </a></li>
                <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item" style="background-image: url('<c:url value="/images/tv/shameless/poster"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                </a></li>

                <li>
                    <a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item" style="background-image: url('<c:url value="/images/tv/house/poster"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
                </span>
                </a>
               </li>
                <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item" style="background-image: url('<c:url value="/images/news/2"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                </a></li>
                <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item" style="background-image: url('<c:url value="/images/news/3"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                </a></li>
                <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item" style="background-image: url('<c:url value="/images/tv/game-of-thrones/poster"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                </a></li>
            </ul>
        </div>
        <a href="/" class="uk-slidenav uk-slidenav-contrast uk-slidenav-previous" data-uk-slider-item="previous"></a>
        <a href="/" class="uk-slidenav uk-slidenav-contrast uk-slidenav-next" data-uk-slider-item="next"></a>
    </div>
    <h2 class="uk-margin-large-top">Recently added</h2>
    <div class="uk-slidenav-position" data-uk-slider="{infinite: true}">
        <div class="uk-slider-container">
            <ul class="uk-slider uk-grid-width-medium-1-5">
                <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item" style="background-image: url('<c:url value="/images/tv/game-of-thrones/poster"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                </a></li>
                <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item" style="background-image: url('<c:url value="/images/tv/prison-break/poster"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                </a></li>
                <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item" style="background-image: url('<c:url value="/images/tv/shameless/poster"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                </a></li>
                <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item" style="background-image: url('<c:url value="/images/news/2"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                </a></li>
                <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item" style="background-image: url('<c:url value="/images/news/3"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                </a></li>
                <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item" style="background-image: url('<c:url value="/images/tv/house/poster"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                </a></li>
                <li><a href="<c:url value="/tv/game-of-thrones"/>">
                <span class="item" style="background-image: url('<c:url value="/images/tv/game-of-thrones/poster"/>')">
                    <span class="overlay"><span class="item-header">Title</span> </span>
            </span>
                </a></li>
            </ul>
        </div>
        <a href="/" class="uk-slidenav uk-slidenav-contrast uk-slidenav-previous" data-uk-slider-item="previous"></a>
        <a href="/" class="uk-slidenav uk-slidenav-contrast uk-slidenav-next" data-uk-slider-item="next"></a>
    </div>

</div>
</body>
</html>
