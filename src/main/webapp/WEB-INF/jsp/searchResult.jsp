<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Meow
  Date: 06.01.2017
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <h1>${search}</h1>

    <div class="genreList uk-margin-large-top">
        <c:forEach items="${tvshows}" var="tv">
            <a href="<c:url value="/tv/${tv.slug}"/>"> <span class="item" style="background-image: url('<c:url
                    value="/images/tv/${tv.slug}/poster"/>')">
                    <span class="overlay">
                        <span class="item-header">${tv.title}</span> </span>
            </span>
            </a>
        </c:forEach>
    </div>
</div>
</body>
</html>
