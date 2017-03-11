<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <article class="uk-article uk-margin-large-top review">
        <h1 class="uk-article-title review-title">${review.title}</h1>
        <p class="uk-article-meta">Written by
            <a href="<c:url value="/user/${review.user.name}"/> ">${review.user.name}</a>
            on ${review.publicationDate}</p>
        <p class="uk-article-lead">${review.introduction} </p>
        ${review.content}
        <hr class="uk-article-divider">
        <a class="uk-button" href="<c:url value="/tv/${review.tvShow.slug}"/> ">Back</a>
    </article>

</div>
</body>
</html>
