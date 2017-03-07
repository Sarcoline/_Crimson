<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of Tv Shows</title>
</head>
<body>
<div class="uk-margin-large-top">
    <div class="uk-grid">
        <div class="uk-width-medium-1-6 uk-width-small-1-1 uk-margin-large-top">
            <form class="uk-form uk-form-stacked uk-margin-large-left" id="filterForm">
                <div class="uk-form-row">
                    <label class="uk-form-label" for="releaseYearStart">Year start</label>
                    <input type="number" name="releaseYearStart"
                           id="releaseYearStart" value="0"/>
                </div>
                <div class="uk-form-row">
                    <label class="uk-form-label" for="releaseYearEnd">Year end</label>
                    <input type="number" name="releaseYearEnd"
                           id="releaseYearEnd" value="0"/>
                </div>
                <div class="uk-form-row">
                    <label class="uk-form-label" for="genre">Genre</label>
                    <input type="text" name="genre"
                           id="genre"/>
                </div>
                <div class="uk-form-row">
                    <label class="uk-form-label" for="genre">Network</label>
                    <select id="network">
                        <option selected value="0">Network</option>
                        <option value="HBO">HBO</option>
                        <option value="Showtime">Showtime</option>
                        <option value="NBC">NBC</option>
                        <option value="Canal+">Canal+</option>
                        <option value="Fox">Fox</option>
                        <option value="BBC">BBC</option>
                    </select>
                </div>
                <div class="uk-form-row">
                    <label class="uk-form-label" for="genre">Country</label>
                    <select id="country">
                        <option selected value="0">Country</option>
                        <option value="USA">USA</option>
                        <option value="UK">UK</option>
                        <option value="Poland">Poland</option>
                    </select>
                </div>
                <br>
                <button class="uk-button uk-button-primary " id="filterButton">Filter</button>
                <button class="uk-button " id="filterReset">Reset</button>
            </form>
        </div>
        <div class="uk-width-medium-4-6  uk-width-small-1-1 uk-margin-large-left">
            <div class="genreList uk-margin-large-top " id="filter">
                <div class="loader loader--style2 uk-text-center uk-hidden" title="1">
                   <c:import url="loading.jsp"/>
                </div>
            </div>
            <div class="pagination uk-margin-large-top">
            </div>
        </div>

    </div>
</div>
<script>
    $(function () {
        var pagination = $('.pagination');

        pagination.pagination({
            items: ${tvSize},
            itemsOnPage: 5,
            cssStyle: 'light-theme'
        });
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

        var parameters = {
            genre: null,
            releaseYearStart: null,
            releaseYearEnd: null,
            country: null,
            network: null,
            minimalRating: null,
            maximumRating: null
        };
        $('#filterReset').on('click', function (event) {
            event.preventDefault();
            parameters = {
                genre: null,
                releaseYearStart: null,
                releaseYearEnd: null,
                country: null,
                network: null,
                minimalRating: null,
                maximumRating: null
            };
            $('#filterForm')[0].reset();
            postJson(1);
        });
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

        $("#filterButton").on('click', function (event) {
            event.preventDefault();
            var startYear = $('#releaseYearStart').val();
            parameters.releaseYearStart = startYear != 0 ? startYear : null;
            var endYear = $('#releaseYearEnd').val();
            parameters.releaseYearEnd = endYear != 0 ? endYear : null;
            var genre = $('#genre').val();
            parameters.genre = genre != 0 ? genre : null;
            var e = document.getElementById("country");
            var country = e.options[e.selectedIndex].value;
            parameters.country = country != 0 ? country : null;
            var n = document.getElementById("network");
            var network = n.options[n.selectedIndex].value;
            parameters.network = network != 0 ? network : null;
            postJson(1);
        });


        var postJson = function (page) {
            $('.loader').removeClass('uk-hidden');
            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                url: '/api/filter/' + page,
                type: 'post',
                data: JSON.stringify(parameters),
                success: function (data) {
                    var result = [];
                    $.each(data.tvShows, function (key, val) {
                        var found = createFound(val.slug, val.title);
                        result.push(found);
                    });

                    pagination.pagination('updateItems', data.size);
                    data.size == 0 ? $('#filter').html("<h1>There's no tvshows with given parameters</h1>") : $('#filter').html(result);
                    $('.loader').addClass('uk-hidden');
                }
            });
        };

        pagination.on('click', function () {
            postJson($(this).pagination('getCurrentPage'));
        });
        postJson(1);
    });
</script>
</body>
</html>
