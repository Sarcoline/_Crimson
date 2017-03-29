var markWatchedEpisodes = function (rateThis, watched) {
    rateThis.each(function () {
        if ($.inArray($(this).data('id'), watched) != -1) $(this).find('i').toggleClass('fa-square-o fa-check-square-o');
    });
};

var rateTvShow = function (label, rateValue, id, title) {
    label.on('click', function () {
        var modal = UIkit.modal(".uk-modal");
        var i = $('input#' + $(this).attr('for')).val();
        rateValue.html(" " + i);
        modal.hide();
        $.ajax({
            type: "post",
            url: "/api/rate",
            data: {id: id, value: i},
            success: function (data) {
                UIkit.notify({
                    message: "You rated " + title + " on " + i,
                    status: 'info',
                    timeout: 3000,
                    pos: 'bottom-right'
                });
            }
        });
    });
};

var followTvShow = function (follow, id, title) {
    'use strict';
    follow.on('click', function () {
        $(this).find('i').toggleClass('fa-heart-o fa-heart');
        $.ajax({
            type: "post",
            url: "/api/follow",
            data: {
                id: id
            },
            success: function (data) {
                var text = data === "follow" ? "You are following " + title : "You unfollowed " + title;
                UIkit.notify({
                    message: text,
                    status: 'info',
                    timeout: 3000,
                    pos: 'bottom-right'
                });
            }
        });
    });
};

var markAsWatched = function (watchedThis) {
    watchedThis.on('click', function () {
        $(this).find('i').toggleClass('fa-square-o fa-check-square-o');
        $.ajax({
            type: "post",
            url: "/api/watched",
            data: {id: $(this).data('id')}
        });
    });
};