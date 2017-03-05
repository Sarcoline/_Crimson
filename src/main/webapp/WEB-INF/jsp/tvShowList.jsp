<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <div class="genreList uk-margin-large-top" id="filter"></div>
    <ul class="uk-pagination" data-uk-pagination="{items:${tvSize}, itemsOnPage:5}">
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

        var getJson = function (page) {
            $.getJSON('/api/tvshows/' + page, function (data) {
                var result = [];
                $.each(data, function (key, val) {
                    var found = createFound(val.slug, val.title);
                    result.push(found);
                });
                $('#filter').html(result);
            });
        };


        $('[data-uk-pagination]').on('select.uk.pagination', function (e, pageIndex) {
            var page = pageIndex + 1;
            getJson(page);
        });

        getJson(1);
    })
</script>
</body>
</html>
