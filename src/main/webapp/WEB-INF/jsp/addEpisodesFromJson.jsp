<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <h1>Add episodes</h1>
    <a class="uk-button uk-button-primary" id="saveAll">Add all (risky) </a>
    <div class="uk-grid uk-grid-large uk-margin-large-top">
        <div>
            <ul id="test" class="uk-list uk-list-line">

            </ul>
        </div>
    </div>
</div>
<script>
    $(function () {
        var tvshow = '${name}';

        var getJson = function () {
            $.getJSON('http://www.omdbapi.com/?t=' + tvshow, function (data) {
                for (var i = 1; i <= data.totalSeasons; i += 1) {
                    (function (i) {
                        $.getJSON('http://www.omdbapi.com/?t=' + tvshow + '&season=' + i, function (data) {
                            $.each(data.Episodes, function (val, key) {
                                var li = document.createElement('li');
                                var a = document.createElement('a');
                                a.setAttribute("data-season", i);
                                a.setAttribute("data-episode", key.Episode);
                                a.setAttribute("data-title", key.Title);
                                a.setAttribute("data-date", key.Released);
                                a.setAttribute("class", "uk-button uk-button-success");
                                a.setAttribute("style", "float:right;");
                                a.innerHTML = 'Save!';
                                li.innerHTML = 'S' + i + 'E' + key.Episode + ' ' + key.Title + ' ' + key.Released;
                                li.appendChild(a);
                                $('#test').append(li);
                            });
                        });
                    })(i);
                }
            });
        };
        getJson();

        var postJson = function(episode) {
            $.ajax({
                url: 'add',
                type: 'get',
                data: episode,
                success: function () {
                    UIkit.notify({
                        message: 'Successfully added: ' + episode.title + '!',
                        status: 'success',
                        timeout: 5000,
                        pos: 'top-center'
                    });
                },
                error: function () {
                    UIkit.notify({
                        message: 'Error with adding: ' + episode.title + '!',
                        status: 'danger',
                        timeout: 5000,
                        pos: 'top-center'
                    });
                }
            });
        };

        $('#saveAll').on('click', function () {
            $( "#test").find("li").each(function( index, li ) {
                var a = li.firstElementChild;
                var episode = {
                    title: a.dataset.title,
                    releaseDate: a.dataset.date,
                    season: a.dataset.season,
                    episode: a.dataset.episode
                };
                postJson(episode);
            });
        });

        $('#test').on("click", "a", function (e) {
            e.preventDefault();
            var episode = {
                title: $(this).data('title'),
                releaseDate: $(this).data('date'),
                season: $(this).data('season'),
                episode: $(this).data('episode')
            };
            postJson(episode);
        });
    });
</script>
</body>
</html>
