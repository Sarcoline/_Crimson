var filter = function (size) {

    var pagination = $('.pagination');

    pagination.pagination({
        items: size,
        itemsOnPage: 20,
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
        pagination.pagination('drawPage', 1);
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

        var rStart = document.getElementById("ratingStart");
        var ratingStart = rStart.options[rStart.selectedIndex].value;
        console.log(ratingStart);
        parameters.minimalRating = parseFloat(ratingStart);

        var rEnd = document.getElementById("ratingEnd");
        var ratingEnd = rEnd.options[rEnd.selectedIndex].value;
        parameters.maximumRating = parseFloat(ratingEnd);
        console.log(parameters);
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
                var filter = $('#filter');
                pagination.pagination('updateItems', data.size);
                data.size === 0 ? filter.html("<h1>There's no tvshows with given parameters</h1>") : filter.html(result);
                $('.loader').addClass('uk-hidden');
            }
        });
    };

    pagination.on('click', function () {
        postJson($(this).pagination('getCurrentPage'));
    });
    postJson(1);

};
