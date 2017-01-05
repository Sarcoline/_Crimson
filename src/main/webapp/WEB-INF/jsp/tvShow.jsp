<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>${title}</title>
</head>
<body>
<header style="background: url(<c:url value="/images/shameless/back"/>) center center;">
    <figure style="background: url(<c:url value="/images/shameless/poster"/>) center center"></figure>
</header>

<h1 class="title">${title}
    <small><i class="fa fa-heart" aria-hidden="true" style="cursor: pointer"></i></small>
</h1>
<h3 class="subtitle uk-text-muted">Drama 2014</h3>
<div class="uk-grid">
    <div class="uk-width-large-1-6  uk-width-medium-1-1" data-uk-grid-margin=" ">
        <div class="gallery">
            <div class="uk-grid-small uk-grid-width-1-1" data-uk-grid-margin=" ">

                <a href="<c:url value="/images/gameofthrones/1"/>" data-lightbox-type="image"
                   data-uk-lightbox="{group:'group1'}"> <img src="<c:url value="/images/gameofthrones/1"/>"
                                                             width="200" height="200"> </a>
                <a href="<c:url value="/images/gameofthrones/2"/>" data-lightbox-type="image"
                   data-uk-lightbox="{group:'group1'}"> <img src="<c:url value="/images/gameofthrones/2"/>"
                                                             width="200" height="200"> </a>
                <a href="<c:url value="/images/gameofthrones/3"/>" data-lightbox-type="image"
                   data-uk-lightbox="{group:'group1'}"> <img src="<c:url value="/images/gameofthrones/3"/>"
                                                             width="200" height="200"> </a>
                <a href="<c:url value="/images/gameofthrones/4"/>" data-lightbox-type="image"
                   data-uk-lightbox="{group:'group1'}"> <img src="<c:url value="/images/gameofthrones/4"/>"
                                                             width="200" height="200"> </a>
                <a href="<c:url value="/images/gameofthrones/5"/>" data-lightbox-type="image"
                   data-uk-lightbox="{group:'group1'}"> <img src="<c:url value="/images/gameofthrones/5"/>"
                                                             width="200" height="200"> </a>
            </div>
        </div>
    </div>
    <div class="uk-width-large-4-6 uk-width-medium-1-1">
        <article class="uk-article">
            <h1 class="uk-article-title">Summary of <strong>${title}</strong></h1>
            <p class="uk-article-lead">Pellentesque facilisis. Nulla imperdiet sit amet magna. Vestibulum dapibus,
                mauris nec malesuada fames ac turpis velit, rhoncus eu, luctus et interdum adipiscing wisi.</p> Lorem
            ipsum dolor sit amet enim. Etiam ullamcorper. Suspendisse a pellentesque dui, non felis. Maecenas malesuada
            elit lectus felis, malesuada ultricies. Curabitur et ligula. Ut molestie a, ultricies porta urna. Vestibulum
            commodo volutpat a, convallis ac, laoreet enim. Phasellus fermentum in, dolor. Pellentesque facilisis. Nulla
            imperdiet sit amet magna. Vestibulum dapibus, mauris nec malesuada fames ac turpis velit, rhoncus eu, luctus
            et interdum adipiscing wisi. Aliquam erat ac ipsum. Integer aliquam purus. Quisque lorem tortor fringilla
            sed, vestibulum id, eleifend justo vel bibendum sapien massa ac turpis faucibus orci luctus non,
            consectetuer lobortis quis, varius in, purus. Integer ultrices posuere cubilia Curae, Nulla ipsum dolor
            lacus, suscipit adipiscing. Cum sociis natoque penatibus et ultrices volutpat. Nullam wisi ultricies a,
            gravida vitae, dapibus risus ante sodales lectus blandit eu, tempor diam pede cursus vitae, ultricies eu,
            faucibus quis, porttitor eros cursus lectus, pellentesque eget, bibendum a, gravida ullamcorper quam. Nullam
            viverra consectetuer. Quisque cursus et, porttitor risus. Aliquam sem. In hendrerit nulla quam nunc,
            accumsan congue. Lorem ipsum primis in nibh vel risus. Sed vel lectus. Ut sagittis, ipsum dolor quam.
        </article>
        <h1>Episode list:</h1>
        <div class="uk-grid episodes">
            <div class="uk-width-large-1-6 uk-width-small-2-6">
                <ul class="uk-tab uk-tab-left" data-uk-tab="{connect:'#tab-content', animation: 'fade'}">
                    <li class="menu" aria-expanded="false"><a href="#">Season 1</a></li>
                    <li aria-expanded="false" class="menu"><a href="#">Season 2</a></li>
                    <li aria-expanded="false" class="menu"><a href="#">Season 3</a></li>
                    <li aria-expanded="false" class="menu"><a href="#">Season 4</a></li>
                    <li aria-expanded="false" class="menu uk-active"><a href="#">Season 5</a></li>
                </ul>
            </div>
            <div class="uk-width-large-3-6 uk-width-small-4-6">
                <ul id="tab-content" class="uk-switcher ">
                    <li class="" aria-hidden="false">
                        <p><i class="fa fa-check-square-o" aria-hidden="true"></i> Episode title</p>
                        <p><i class="fa fa-check-square-o" aria-hidden="true"></i> Episode title</p>
                        <p>Episode title</p>
                    </li>
                    <li aria-hidden="false" class="">
                        <h3>Season 2</h3>
                        <p>Episode title</p>
                        <p>Episode title</p>
                        <p>Episode title</p>
                    </li>
                    <li aria-hidden="false" class="">
                        <h3>Season 3</h3>
                        <p>Episode title</p>
                        <p>Episode title</p>
                        <p>Episode title</p>
                    </li>
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
                    7.2<small class="uk-text-muted" style="font-size: 2rem;">/10</small>
                </p>
                <p class="uk-text-muted">253 rated</p>
                <p class="uk-text-muted">1453 follows</p>
                <p></p>
                <div class="userRating">
                    <img src="<c:url value="/images/user/meow"/>" class="userPicture">
                    <i class="fa fa-star fa-lg rate" title="Rate!" aria-hidden="true"></i><span
                        class="rateValue"> 5</span>
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
            </div>
            </div>
            <div class="uk-width-large-1-1 uk-width-small-1-2">
            <div class="info">
                <p>Network:
                    <br> <strong>History</strong></p>
                <p>Country:
                    <br><strong>United States</strong></p>
                <p>Episode Length:
                    <br><strong>60 Minutes</strong></p> <a class="uk-button uk-button-primary"
                                                           href="https://www.youtube.com/watch?v=EI0ib1NErqg"
                                                           data-uk-lightbox="{group:'group2'}">Watch trailer</a></div>
        </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $('a').click(function () {
            $(this).find('i').toggleClass('fa-square-o fa-check-square-o');
        });
        $('h1').click(function () {
            $(this).find('i').toggleClass('fa-heart fa-heart-o');
        });
        $('i.rate').on('click', function () {
            $('fieldset.rating').toggleClass('uk-hidden')
        })
        $('label').on('click', function () {
            $('.rateValue').html(" " + $('input#' + $(this).attr('for')).val());
            $('fieldset.rating').addClass('uk-hidden')
        })
    });
</script>
</body>
</html>
