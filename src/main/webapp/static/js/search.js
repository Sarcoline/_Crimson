var searchFunction = function (search, found) {

    function createFound(slug, title) {
        var a = document.createElement('a');
        a.href = '/tv/' + slug;
        var span = document.createElement('span');
        var link = '/images/tv/' + slug + '/poster';
        span.style.backgroundImage = "url(" + link + ")";
        span.className = 'item-search';
        var spanOverlay = document.createElement('span');
        spanOverlay.className = 'overlay';
        var spanItem = document.createElement('span');
        spanItem.className = 'item-header';
        spanItem.innerText = title;
        spanOverlay.appendChild(spanItem);
        span.appendChild(spanOverlay);
        a.appendChild(span);
        return a;
    }

    search.on('input', function () {
        var input = $(this).val();
        if (input.length > 1) {
            var result = [];
            $.getJSON('/api/search/' + input, function (data) {
                $.each(data, function (key, val) {
                    var found = createFound(val.slug, val.title);
                    result.push(found);

                });
                found.html(result);
            });
        }
        else {
            result = [];
            found.html(" ");
        }
        $(this).focusin(function () {
            found.html(result);
        });
    }).focusout(function () {
        setTimeout(function () {
            found.html(" ");
        }, 500);
    });
};