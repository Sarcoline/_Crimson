<%--suppress JSUnresolvedFunction --%>
<%--suppress JSUnresolvedFunction --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="name" property="name"/>
<%--
  Created by IntelliJ IDEA.
  User: Meow
  Date: 30.12.2016
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${tv.title}</title>
</head>
<body>
<header style="background: url(<c:url value="/images/tv/${tv.slug}/back"/>) center center;">
    <figure style="background: url(<c:url value="/images/tv/${tv.slug}/poster"/>) center center"></figure>
</header>

<h1 class="title">${tv.title}
    <sec:authorize access="isAuthenticated()">
        <small class="follow"><a id="follow"><i class="fa fa-heart-o" aria-hidden="true"
                                                                             style="cursor: pointer"></i></a></small>
    </sec:authorize>
</h1>
<h3 class="subtitle uk-text-muted">${tv.genre} ${tv.releaseYear}</h3>
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
        <article class="uk-article">
            <h2 class="uk-article-title">Summary of <strong>${tv.title}</strong></h2>
            <p>${tv.description}</p>

        </article>
        <h2>Episode list:</h2>
        <div class="uk-grid episodes">
            <div class="uk-width-large-1-6 uk-width-small-2-6">
                <ul class="uk-tab uk-tab-left" data-uk-tab="{connect:'#tab-content', animation: 'fade'}">
                    <c:forEach begin="1" end="${seasons}" varStatus="count">
                        <li aria-expanded="false" class="menu <c:if test="${count.last}">uk-active</c:if>"><a href="#">Season ${count.count}</a>
                        </li>
                        <%--<li aria-expanded="false" class="menu uk-active"><a href="#">Season 5</a></li>--%>
                    </c:forEach>
                </ul>
            </div>
            <div class="uk-width-large-3-6 uk-width-small-4-6">
                <%--Na razie wszystkie episody obserwowanych--%>
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
                                                    <a class="rateThis" data-id="${episode.id}"><i class="fa fa-square-o"
                                                                           aria-hidden="true"></i></a>

                                                </sec:authorize>
                                                    ${episode.title}
                                                <small class="episodeDate uk-text-muted">${episode.releaseDate}</small>
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
    </div>
    <div class="uk-width-large-1-6 uk-width-medium-1-1">
        <div class="uk-grid details" data-uk-grid-margin=" ">
            <div class="uk-width-large-1-1 uk-width-small-1-2">
                <div class="ratebox">
                    <p class="overallrating">
                        ${tv.overallRating}<small class="uk-text-muted" style="font-size: 2rem;">/10</small>
                    </p>
                    <p class="uk-text-muted">253 rated</p>
                    <p class="uk-text-muted">1453 follows</p>
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
                    trailer</a></div>
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
            <div class="uk-width-1-2 centerH">
                <fieldset class="rating">
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
<script>
    $(function () {

        var rating = ${rating};
        if (rating != 0) $('.rateValue').html(" " + rating);

        var watched = ${watchedEpisodesId}
            $('.rateThis').each(function () {
                if ($.inArray($(this).data('id'),watched) != -1)  $(this).find('i').toggleClass('fa-square-o fa-check-square-o');
            });

        <c:if test="${follow == true}">
        $('i.fa-heart-o').addClass('fa-heart').removeClass('fa-heart-o');
        </c:if>

        <sec:authorize access="isAuthenticated()">
        $('a.rateThis').click(function () {
            $(this).find('i').toggleClass('fa-square-o fa-check-square-o');
        });
        </sec:authorize>

        $('small.follow').click(function () {
            $(this).find('i').toggleClass('fa-heart-o fa-heart');
        });
//        $('i.rate').on('click', function () {
//            $('fieldset.rating').toggleClass('uk-hidden')
//        });
        var modal = UIkit.modal(".uk-modal");
        $('label').on('click', function () {
            var i = $('input#' + $(this).attr('for')).val();
            $('.rateValue').html(" " + i);
            modal.hide();
            $.ajax({
                type: "get",
                url: "rate",
                data: {id: ${tv.id}, value: i}
            });
        });

        $('#follow').on('click',function () {
           $.ajax({
               type: "get",
               url: "follow",
               data: {id: ${tv.id}}
           })
        });

        $('.rateThis').on('click', function () {
            $.ajax({
                type: "get",
                url: "watched",
                data: {id: $(this).data('id')}
            });
        });
    });
</script>
</body>
</html>
