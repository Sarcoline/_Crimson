<%--suppress JSUnresolvedFunction --%>
<%--suppress ALL --%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="name" property="name"/>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link href="https://fonts.googleapis.com/css?family=Raleway|Roboto:900|Montserrat:700" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/uikit/css/uikit.min.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/font-awesome-4.7.0/css/font-awesome.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/uikit/css/components/slidenav.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/uikit/css/components/slider.min.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/uikit/css/components/notify.min.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/uikit/css/components/sortable.min.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/style.css' />">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"
            type="application/javascript"></script>
    <script src="<c:url value='/static/uikit/js/uikit.min.js' />" type="application/javascript"></script>
    <script src="<c:url value='/static/uikit/js/components/lightbox.min.js' />" type="application/javascript"></script>
    <script src="<c:url value='/static/uikit/js/components/slider.min.js' />" type="application/javascript"></script>
    <script src="<c:url value='/static/uikit/js/components/sortable.min.js' />" type="application/javascript"></script>
    <script src="<c:url value='/static/uikit/js/components/notify.min.js' />" type="application/javascript"></script>

    <title><decorator:title/></title>
</head>
<body>
<nav class="uk-navbar">
    <ul class="uk-navbar-nav uk-"><a href="/" class="uk-navbar-brand">_Crimson</a>
        <sec:authorize access="isAuthenticated()">
            <li><a href="<c:url value="/user/${name}" />">Dashboard</a></li>
        </sec:authorize>
        <li class="uk-parent"><a href="<c:url value="/tv/list"/> ">TvShows</a></li>
        <li class="uk-parent" data-uk-dropdown="{mode:'click'}" aria-haspopup="true" aria-expanded="false">
            <a href="">Genre</a>
            <div class="uk-dropdown uk-dropdown-navbar uk-dropdown-bottom">
                <ul class="uk-nav uk-nav-navbar">
                    <li><a href="<c:url value="/tv/genre/drama"/> ">Drama</a></li>
                    <li><a href="<c:url value="/tv/genre/fantasy"/> ">Fantasy</a></li>
                    <li><a href="<c:url value="/tv/genre/comedy"/> ">Comedy</a></li>
                </ul>
            </div>
        </li>
        <div class="uk-navbar-content uk-hidden-small">
            <div class="searchForm uk-form uk-margin-remove uk-display-inline-block">
                <input id="search" name="search" type="text" placeholder="Search" autocomplete="off" size="40">
            </div>
            <div class="genreList" id="found" style="z-index: 9999; position: absolute;">
            </div>
        </div>
    </ul>
    <div class="uk-navbar-flip">
        <ul class="uk-navbar-nav">
            <sec:authorize access="isAnonymous()">
                <li><a href="<c:url value="/register" />">Register</a></li>
                <li class="uk-parent"><a href="<c:url value="/login"/> ">Login</a></li>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <form action="<c:url value="/logout?${_csrf.parameterName}=${_csrf.token}"/>" method="post"
                      id="logoutForm">
                </form>
                <li class="uk-parent"><a href="javascript:formSubmit()">Logout</a></li>
            </sec:authorize>
        </ul>
    </div>
</nav>

<decorator:body/>
<script>
    function formSubmit() {
        document.getElementById('logoutForm').submit();
    }
    $('.search').on('click', function () {
        $('.searchForm').submit();
    });


    function createFound(slug, title) {
        var a = document.createElement('a');
        a.href = '/tv/' + slug;
        var span = document.createElement('span');
        var link = '/images/tv/' + slug + '/poster';
        span.style.backgroundImage = "url(" + link + ")";
        span.className = 'itemSearch';
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

    $('#search').on('input', function () {
        var input = $(this).val();
        if (input.length > 2) {
            var result = [];
            $.getJSON('/api/search/' + input, function (data) {
                $.each(data, function (key, val) {
                    var found = createFound(val.slug, val.title);
                    result.push(found);

                });
                $('#found').html(result);
            })
        }
        else {
            result = [];
            $('#found').html(" ");
        }
        $(this).focusin(function () {
            $('#found').html(result);
        })
    }).focusout(function () {
        setTimeout(function () {
            $('#found').html(" ");
        },500)

    })
</script>
</body>
</html>