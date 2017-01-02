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
        <h1>Hello World!</h1>
    </sec:authorize>
</div>
<div class="uk-grid">
    <div class="uk-width-1-5  uk-width-small-1-6">
        <div class="gallery">
            <div class="uk-grid-small uk-grid-width-1-1" data-uk-grid-margin="">
                <div>
                    <a href="<c:url value="/images/news/1"/>" data-lightbox-type="image"
                       data-uk-lightbox="{group:'group1'}"> <img src="<c:url value="/images/news/1"/>"
                                                                 width="200" height="200"> </a>
                </div>
                <div>
                    <a href="<c:url value="/images/news/2"/>" data-lightbox-type="image"
                       data-uk-lightbox="{group:'group1'}"> <img src="<c:url value="/images/news/2"/>"
                                                                 width="200" height="200"> </a>
                </div>
                <div>
                    <a href="<c:url value="/images/news/3"/>" data-lightbox-type="image"
                       data-uk-lightbox="{group:'group1'}"> <img src="<c:url value="/images/news/3"/>"
                                                                 width="200" height="200"> </a>
                </div>
                <div>
                    <a href="<c:url value="/images/gameofthrones/4"/>" data-lightbox-type="image"
                       data-uk-lightbox="{group:'group1'}"> <img src="<c:url value="/images/gameofthrones/4"/>"
                                                                 width="200" height="200"> </a>
                </div>
                <div>
                    <a href="<c:url value="/images/gameofthrones/5"/>" data-lightbox-type="image"
                       data-uk-lightbox="{group:'group1'}"> <img src="<c:url value="/images/gameofthrones/5"/>"
                                                                 width="200" height="200"> </a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
