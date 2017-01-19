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
            <small><a href="<c:url value="follow/${tv.id}"/> "><i class="fa fa-heart-o" aria-hidden="true" style="cursor: pointer"></i></a></small>
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
                <%--<a href="<c:url value="/images/${tv.slug}/4"/>" data-lightbox-type="image"--%>
                <%--data-uk-lightbox="{group:'group1'}"> <img src="<c:url value="/images/${tv.slug}/4"/>"--%>
                <%--width="200" height="200"> </a>--%>
                <%--<a href="<c:url value="/images/${tv.slug}/5"/>" data-lightbox-type="image"--%>
                <%--data-uk-lightbox="{group:'group1'}"> <img src="<c:url value="/images/${tv.slug}/5"/>"--%>
                <%--width="200" height="200"> </a>--%>
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
                    <li aria-expanded="false" class="menu"><a href="#">Season 4</a></li>
                    <li aria-expanded="false" class="menu uk-active"><a href="#">Season 5</a></li>
                </ul>
            </div>
            <div class="uk-width-large-3-6 uk-width-small-4-6">
                <ul id="tab-content" class="uk-switcher ">
                    <li aria-hidden="false" class="">
                        <h3>Season 4</h3>
                        <p>Episode title</p>
                        <p>Episode title</p>
                        <p>Episode title</p>
                    </li>
                    <li aria-hidden="false" class="">
                        <h3>Season 5</h3>
                        <ul class="uk-list uk-list-line">
                            <li>
                                <p><strong> 1. </strong><a><i class="fa fa-check-square-o" aria-hidden="true"></i></a>
                                    The Red Woman
                                    <small class="episodeDate uk-text-muted">24.04.2016</small>
                                </p>
                            </li>
                            <li>
                                <p><strong> 2. </strong><a><i class="fa fa-check-square-o" aria-hidden="true"></i></a>
                                    Home
                                    <small class="episodeDate uk-text-muted">24.04.2016</small>
                                </p>
                            </li>
                            <li>
                                <p><strong> 3. </strong><a><i class="fa fa-check-square-o" aria-hidden="true"></i></a>
                                    Oathbreaker
                                    <small class="episodeDate uk-text-muted">24.04.2016</small>
                                </p>
                            </li>
                            <li>
                                <p><strong> 4. </strong><a><i class="fa fa-check-square-o" aria-hidden="true"></i></a>
                                    Book of the Stranger
                                    <small class="episodeDate uk-text-muted">24.04.2016</small>
                                </p>
                            </li>
                            <li>
                                <p><strong> 5. </strong><a><i class="fa fa-check-square-o" aria-hidden="true"></i></a>
                                    The Door
                                    <small class="episodeDate uk-text-muted">24.04.2016</small>
                                </p>
                            </li>
                            <li>
                                <p><strong> 6. </strong><a><i class="fa fa-check-square-o" aria-hidden="true"></i></a>
                                    Blood of My Blood
                                    <small class="episodeDate uk-text-muted">24.04.2016</small>
                                </p>
                            </li>
                            <li>
                                <p><strong> 7. </strong><a><i class="fa fa-square-o" aria-hidden="true"></i></a> The
                                    Broken Man
                                    <small class="episodeDate uk-text-muted">24.04.2016</small>
                                </p>
                            </li>
                            <li>
                                <p><strong> 8. </strong><a><i class="fa fa-square-o" aria-hidden="true"></i></a> No One
                                    <small class="episodeDate uk-text-muted">24.04.2016</small>
                                </p>
                            </li>
                            <li>
                                <p><strong> 9. </strong><a><i class="fa fa-square-o" aria-hidden="true"></i></a> Battle
                                    of Bastards
                                    <small class="episodeDate uk-text-muted">24.04.2016</small>
                                </p>
                            </li>
                        </ul>
                    </li>
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
                    <p></p>
                    <sec:authorize access="isAuthenticated()">
                        <div class="userRating">
                            <img src="<c:url value="/images/user/${name}"/>" class="userPicture">
                            <a href="/tv/rate/${tv.id}"><i class="fa fa-star fa-lg rate" title="Rate!" aria-hidden="true"></i></a><span
                                class="rateValue"></span>
                        </div>
                        <fieldset class="rating uk-hidden">
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
<script>
    $(function () {
    <c:if test="${follow == true}">
        $('i.fa-heart-o').addClass('fa-heart').removeClass('fa-heart-o');
    </c:if>
        <sec:authorize access="isAuthenticated()">
            $('a').click(function () {
                $(this).find('i').toggleClass('fa-square-o fa-check-square-o');
            });
        </sec:authorize>

        $('small').click(function () {
            $(this).find('i').toggleClass('fa-heart-o fa-heart');
        });
        $('i.rate').on('click', function () {
            $('fieldset.rating').toggleClass('uk-hidden')
        });
        $('label').on('click', function () {
            $('.rateValue').html(" " + $('input#' + $(this).attr('for')).val());
            $('fieldset.rating').addClass('uk-hidden')
        })
    });
</script>
</body>
</html>
