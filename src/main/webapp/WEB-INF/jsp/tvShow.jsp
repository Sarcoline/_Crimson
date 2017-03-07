<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="name" property="name"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${tv.title}</title>
</head>
<body>
<header class="head" style="background: url(<c:url value="/images/tv/${tv.slug}/back"/>) center center;">
    <figure style="background: url(<c:url value="/images/tv/${tv.slug}/poster"/>) center center"></figure>
</header>

<h1 class="title">${tv.title}
    <sec:authorize access="isAuthenticated()">
        <small class="follow"><a id="follow"><i class="fa fa-heart-o" aria-hidden="true" onclick="onClick()"
                                                style="cursor: pointer"></i></a></small>
    </sec:authorize>
    <div id="message" style="color:darkblue; font-size: large; text-align: center"></div>

</h1>
<h3 class="subtitle uk-text-muted">
    ${tv.genre}, ${tv.releaseYear} - <c:if test="${tv.finishYear != 0}">${tv.finishYear}</c:if>

</h3>
<div class="uk-grid">
    <div class="uk-width-large-1-6  uk-width-medium-1-1" data-uk-grid-margin=" ">
        <div class="gallery">
            <div class="uk-grid-small uk-grid-width-1-1" data-uk-grid-margin=" ">

                <a href="<c:url value="/images/tv/${tv.slug}/1"/>" data-lightbox-type="image"
                   data-uk-lightbox="{group:'group1'}"> <img src="<c:url value="/images/tv/${tv.slug}/1"/>"
                                                             width="200" height="200"> </a>
                <a href="<c:url value="/images/tv/${tv.slug}/2"/>" data-lightbox-type="image"
                   data-uk-lightbox="{group:'group1'}"> <img src="<c:url value="/images/tv/${tv.slug}/2"/>"
                                                             width="200" height="200"> </a>
                <a href="<c:url value="/images/tv/${tv.slug}/3"/>" data-lightbox-type="image"
                   data-uk-lightbox="{group:'group1'}"> <img src="<c:url value="/images/tv/${tv.slug}/3"/>"
                                                             width="200" height="200"> </a>
            </div>
        </div>
    </div>
    <div class="uk-width-large-4-6 uk-width-medium-1-1">
        <article class="uk-article summary">
            <h2 class="uk-article-title">Summary of <strong>${tv.title}</strong>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')">
                    <small><a href="/tv/${tv.slug}/edit"><i class="fa fa-cog uk-text-primary" aria-hidden="true"
                                                            style="cursor: pointer; font-size: 2rem"></i></a></small>
                    <small><a href="/tv/${tv.slug}/delete"><i class="fa fa-times uk-text-danger" aria-hidden="true"
                                                              style="cursor: pointer; font-size: 2rem"></i></a></small>
                </sec:authorize>
            </h2>
            <p>${tv.description}</p>

        </article>
        <h2>Episode list:</h2>
        <div class="uk-grid episodes">
            <div class="uk-width-large-1-6 uk-width-small-2-6">
                <ul class="uk-tab uk-tab-left" data-uk-tab="{connect:'#tab-content', animation: 'fade'}">
                    <c:forEach begin="1" end="${seasons}" varStatus="count">
                        <li aria-expanded="false" class="menu <c:if test="${count.last}">uk-active</c:if>"><a href="#">Season ${count.count}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="uk-width-large-3-6 uk-width-small-4-6">
                <ul id="tab-content" class="uk-switcher ">
                    <c:forEach begin="1" end="${seasons}" varStatus="count">
                        <li aria-hidden="false" class="">
                            <h3>Season ${count.count}</h3>
                            <ul class="uk-list uk-list-line">
                                <c:forEach items="${episodes}" var="episode">
                                    <c:if test="${episode.season == count.count}">
                                        <li>
                                            <p><strong> ${episode.number}. </strong>
                                                <sec:authorize access="isAuthenticated()">
                                                    <a class="watched-this" data-id="${episode.id}"><i
                                                            class="fa fa-square-o"
                                                            aria-hidden="true"></i></a>

                                                </sec:authorize>
                                                    ${episode.title}
                                                <small class="episode-date uk-text-muted">${episode.releaseDate}</small>
                                            </p>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <h2 class="uk-margin-large-top">Reviews (${reviews.size()}): </h2>
        <a class="uk-button uk-button-success"
           href="/tv/${tv.slug}/reviews/write">Write
        </a>
        <div class="reviews">
            <ul class="uk-list uk-list-line">
                <c:forEach items="${tv.reviews}" var="review">
                    <li>
                        <article class="uk-article uk-margin-top">

                            <p class="uk-article-lead">${review.title}</p>
                            <p class="uk-article-meta">By <a href="<c:url value="/user/${review.user.name}"/> ">
                            ${review.user.name}</a> on ${review.publicationDate}</p>
                                ${review.introduction} <a href="<c:url value="/tv/${tv.slug}/reviews/${review.id}"/> " style="color: #00a8e6;">Read more</a>
                        </article>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <h2 class="uk-margin-large-top">Comments (${comments.size()}): </h2>
        <button class="uk-button uk-button-success"
                data-uk-toggle="{target:'#add-comment'}">Add
        </button>
        <form class="uk-form uk-margin-top uk-margin-large-bottom uk-hidden" id="add-comment">
            <div class="uk-form-row">
                <textarea id="commenttext" class="uk-width-1-2 uk-form-large" rows="5"
                          placeholder="Add new comment" name="commenttext"></textarea>
            </div>
            <button class="uk-width-1-2 uk-button uk-button-primary uk-button-large" id="send-comment" disabled>Send
            </button>
        </form>
        <div class="uk-margin-large-bottom comments">
            <ul class="uk-comment-list uk-list-lined">
                <li id="comments">
                    <c:forEach items="${comments}" var="comment">
                        <article class="uk-comment uk-margin-top">
                            <header class="uk-comment-header">
                                <img class="uk-comment-avatar" src="<c:url value="/images/user/${comment.user.name}"/> "
                                     width="50"
                                     height="50" alt="">
                                <h4 class="uk-comment-title">${comment.user.name}</h4>
                                <div class="uk-comment-meta">${comment.date} | <a
                                        href="<c:url value="/user/${comment.user.name}"/>">Profile</a></div>
                            </header>
                            <div class="uk-comment-body">
                                <p>${comment.text}</p>
                            </div>
                        </article>
                    </c:forEach>
                </li>
            </ul>
        </div>
    </div>
    <div class="uk-width-large-1-6 uk-width-medium-1-1">
        <div class="uk-grid details" data-uk-grid-margin=" ">
            <div class="uk-width-large-1-1 uk-width-small-1-2">
                <div class="ratebox">
                    <p class="overall-rating">
                        ${tv.overallRating}
                        <small class="uk-text-muted" style="font-size: 2rem;">/10</small>
                    </p>
                    <p class="uk-text-muted">${tv.ratings.size()} ratings</p>
                    <p class="uk-text-muted">${tv.users.size()} follows</p>
                    <sec:authorize access="isAuthenticated()">
                        <div class="userRating">
                            <img src="<c:url value="/images/user/${name}"/>" class="userPicture">
                            <i class="fa fa-star fa-lg rate" data-uk-modal="{target:'#my-id'}" title="Rate!"
                               aria-hidden="true"></i><span
                                class="rateValue"></span>
                        </div>
                    </sec:authorize>

                </div>
            </div>
            <div class="uk-width-large-1-1 uk-width-small-1-2">
                <div class="info">
                    <p>Network:
                        <br> <strong>${tv.network}</strong></p>
                    <p>Country:
                        <br><strong>${tv.country}</strong></p>
                    <p>Episode Length:
                        <br><strong>60 minutes</strong></p> <a class="uk-button uk-button-primary"
                                                               href=${tv.trailerUrl}
                                                                       data-uk-lightbox="{group:'group2'}">Watch
                    trailer</a>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="my-id" class="uk-modal">
    <div class="uk-modal-dialog">
        <a class="uk-modal-close uk-close"></a>
        <div class="uk-modal-header">
            <h2 class="uk-text-center">Rate ${tv.title}</h2>
        </div>
        <div class="uk-grid">
            <div class="uk-width-1-2 uk-align-center">
                <sec:authorize access="isAuthenticated()">
                    <img src="<c:url value="/images/user/${name}"/>" class="userPictureRate">
                </sec:authorize>
            </div>
            <div class="uk-width-1-2 center-rating">
                <fieldset class="rating" data-uk-modal="{target:'#rating-confirmation'}">
                    <input type="radio" id="star5" name="rating" value="10"/>
                    <label class="full" for="star5" title="10"></label>
                    <input type="radio" id="star4half" name="rating" value="9"/>
                    <label class="half" for="star4half" title="9"></label>
                    <input type="radio" id="star4" name="rating" value="8"/>
                    <label class="full" for="star4" title="8"></label>
                    <input type="radio" id="star3half" name="rating" value="7"/>
                    <label class="half" for="star3half" title="7"></label>
                    <input type="radio" id="star3" name="rating" value="6"/>
                    <label class="full" for="star3" title="6"></label>
                    <input type="radio" id="star2half" name="rating" value="5"/>
                    <label class="half" for="star2half" title="5"></label>
                    <input type="radio" id="star2" name="rating" value="4"/>
                    <label class="full" for="star2" title="4"></label>
                    <input type="radio" id="star1half" name="rating" value="3"/>
                    <label class="half" for="star1half" title="3"></label>
                    <input type="radio" id="star1" name="rating" value="2"/>
                    <label class="full" for="star1" title="2"></label>
                    <input type="radio" id="starhalf" name="rating" value="1"/>
                    <label class="half" for="starhalf" title="1"></label>
                </fieldset>
            </div>
        </div>
    </div>
</div>

<div id="rating-confirmation" class="uk-modal">
    <div class="uk-modal-dialog">
        <a class="uk-modal-close uk-close"></a>
        <div class="uk-modal-header">
            <h2 class="uk-text-center" style="color: darkblue">Your rating is saved!</h2>
            <div class="uk-grid">
                <div class="uk-container-center">


                    <a class="uk-button uk-button-default uk-modal-close">OK</a>
                </div>
            </div>


        </div>

    </div>
</div>
<div id="myyy-id" class="uk-modal">
    <div class="uk-modal-dialog">
        <a class="uk-modal-close uk-close"></a>
        <div class="uk-modal-header">
            <h2 class="uk-text-center">You follow it !</h2>
            <div class="uk-grid">
                <div class="uk-container-center">


                    <a class="uk-button uk-button-default uk-modal-close">OK</a>
                </div>
            </div>


        </div>

    </div>
</div>
<script src="<c:url value="/static/js/userActions.js"/>"></script>
<script src="<c:url value="/static/js/comment.js" />"></script>
<script>
    $(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
        <sec:authorize access="isAuthenticated()">
        var rating = parseFloat(${rating});
        var rateValue = $('.rateValue');
        var watchedThis = $('.watched-this');
        var sendButton = $('#send-comment');
        var commentInput = $('#commenttext');
        var watched = ${watchedEpisodesId};
        var label = $('label');
        var follow = $('#follow');
        var id = ${tv.id};
        if (rating != 0) rateValue.html(" " + rating);
        <c:if test="${follow == true}">
        $('i.fa-heart-o').addClass('fa-heart').removeClass('fa-heart-o');
        </c:if>
        //mark watched episodes
        markWatchedEpisodes(watchedThis, watched);
        //ajax request to rate tvShow
        rateTvShow(label, rateValue, id);
        //ajax request to follow tvShow
        followTvShow(follow, id);
        //ajax request to rate tvShow
        markAsWatched(watchedThis);
        //add comment
        commentInput.on('input', function () {
            var len = $(this).val().length;
            if (len >= 5 && len <= 200) sendButton.prop('disabled', false);
            else sendButton.prop('disabled', true);
        });
        sendButton.on('click', function (event) {
            event.preventDefault();
            var comment = {
                text: commentInput.val(),
                idTvShow: ${tv.id}
            };
            if (comment.text.length >= 5) {
                $.ajax({
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    type: "post",
                    url: "/api/addComment",
                    data: JSON.stringify(comment),
                    success: function () {
                        console.log('ok');
                        $('#comments').prepend(createComment(comment.text, '${name}'));
                        $('#add-comment').addClass('uk-hidden');
                        commentInput.val(' ');
                    },
                    error: function () {
                        console.log('not ok')
                    }
                });
            }
        });
        </sec:authorize>
    });
    //follow confirmation
    var clicks = 0;
    function onClick() {
        clicks += 1;
        var message = "";
        if((clicks%2)==1)
        { message = "You follow it!";}
        else {message = "You do not follow it!";}
        document.getElementById("message").innerHTML = message;
    };
</script>
</body>
</html>