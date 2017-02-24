<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Meow
  Date: 14.01.2017
  Time: 19:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of Tv Shows</title>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <c:if test="${genre != null}">
        <h1>${genre}:</h1>
    </c:if>
    <div class="genreList uk-margin-large-top" id="filter">
        <%--<c:forEach items="${tvshows}" var="tv">--%>
        <%--<a href="<c:url value="/tv/${tv.slug}"/>"> <span class="item" style="background-image: url('<c:url--%>
        <%--value="/images/tv/${tv.slug}/poster"/>')">--%>
        <%--<span class="overlay">--%>
        <%--<span class="item-header">${tv.title}</span> </span>--%>
        <%--</span>--%>
        <%--</a>--%>
        <%--</c:forEach>--%>
    </div>
    <ul class="uk-pagination" data-uk-pagination="{items:100, itemsOnPage:25}">
    </ul>
</div>
<script>
    $(function () {
        var createFound = function (slug, title) {
            var a = document.createElement('a');
            a.href = '/tv/' + slug;
            var span = document.createElement('span');
            var link = '/images/tv/' + slug + '/poster';
            span.style.backgroundImage = "url(" + link + ")";
            span.className = 'item';
            var spanOverlay = document.createElement('span');
            spanOverlay.className = 'overlay';
            var spanItem = document.createElement('span');
            spanItem.className = 'item-header';
            spanItem.innerText = title;
            spanOverlay.appendChild(spanItem);
            span.appendChild(spanOverlay);
            a.appendChild(span);
            return a;
        };


        $('[data-uk-pagination]').on('select.uk.pagination', function (e, pageIndex) {
            if (pageIndex + 1 == 1) {
                var result = [];
                $.getJSON('/api/search/game', function (data) {
                    $.each(data, function (key, val) {
                        var found = createFound(val.slug, val.title);
                        console.log(found);
                        result.push(found);
                    })
                    $('#filter').html(result);
                })
            }
            if (pageIndex + 1 == 2) {
                $.getJSON('/api/search/shame', function (data) {
                    var result = [];
                    $.each(data, function (key, val) {
                        var found = createFound(val.slug, val.title);
                        console.log(found);
                        result.push(found);
                    })
                    $('#filter').html(result);
                })
            }
        });

    })
</script>
</body>
</html>
