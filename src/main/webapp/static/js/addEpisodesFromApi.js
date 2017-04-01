var addEpisodes = function (tvshow, idTv) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var getJson = function () {
        var im;
        $.getJSON('http://api.tvmaze.com/singlesearch/shows?q=' + tvshow, function (data) {
            im = data.id;
            $.getJSON('http://api.tvmaze.com/shows/' + im + '/episodes', function (data) {
                $.each(data, function (i) {
                    var li = document.createElement('li');
                    var a = document.createElement('a');
                    a.setAttribute("data-season", data[i].season);
                    a.setAttribute("data-episode", data[i].number);
                    a.setAttribute("data-title", data[i].name);
                    a.setAttribute("data-date", data[i].airdate);
                    a.setAttribute("data-summary", data[i].summary);
                    a.setAttribute("class", "uk-button uk-button-success");
                    a.setAttribute("style", "float:right;");
                    a.innerHTML = 'Save!';
                    li.innerHTML = 'S' + data[i].season + 'E' + data[i].number + ' ' + data[i].name + ' ' + data[i].airdate;
                    li.appendChild(a);
                    $('#test').append(li);
                })
            });
        });
    };
    getJson();
    var postJson = function (episodes) {
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: '/api/' + tvshow + '/add',
            type: 'post',
            data: JSON.stringify(episodes),
            success: function () {
                UIkit.notify({
                    message: 'Successfully added ' + episodes.length + ' episodes!',
                    status: 'info',
                    timeout: 5000,
                    pos: 'bottom-right'
                });
            },
            error: function () {
                UIkit.notify({
                    message: 'Error with adding: ' + episodes.title + '!',
                    status: 'danger',
                    timeout: 5000,
                    pos: 'top-center'
                });
            }
        });
    };
    $('#saveAll').on('click', function () {
        var episodes = [];
        $("#test").find("li").each(function (index, li) {
            var a = li.firstElementChild;
            var episode = {
                title: a.dataset.title,
                releaseDate: a.dataset.date,
                season: a.dataset.season,
                episode: a.dataset.episode,
                summary: a.dataset.summary,
                idTvShow: idTv
            };
            episodes.push(episode);
        });
        postJson(episodes);
    });
    $('#test').on("click", "a", function (e) {
        var episodes = [];
        e.preventDefault();
        var episode = {
            title: $(this).data('title'),
            releaseDate: $(this).data('date'),
            season: $(this).data('season'),
            episode: $(this).data('episode'),
            summary: $(this).data('summary'),
            idTvShow: idTv
        };
        episodes.push(episode);
        postJson(episodes);
    });
};