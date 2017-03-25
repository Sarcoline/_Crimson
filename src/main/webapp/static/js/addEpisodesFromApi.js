var addEpisodes = function (tvshow) {

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

    var postJson = function (episode) {
        $.ajax({
            url: '/api/' + tvshow + '/add',
            type: 'get',
            data: episode,
            success: function (data) {
                UIkit.notify({
                    message: 'Successfully added: ' + episode.title + '!',
                    status: 'success',
                    timeout: 200,
                    pos: 'top-center'
                });
                console.log(data.constructor);
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
        $("#test").find("li").each(function (index, li) {
            var a = li.firstElementChild;
            var episode = {
                title: a.dataset.title,
                releaseDate: a.dataset.date,
                season: a.dataset.season,
                episode: a.dataset.episode,
                summary: a.dataset.summary
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
            episode: $(this).data('episode'),
            summary: $(this).data('summary')
        };
        postJson(episode);
    });
};