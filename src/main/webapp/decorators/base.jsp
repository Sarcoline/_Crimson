<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="name" property="name"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link href="https://fonts.googleapis.com/css?family=Raleway|Roboto:900|Montserrat:700" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/uikit/css/uikit.min.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/font-awesome-4.7.0/css/font-awesome.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/uikit/css/components/slidenav.css' />">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"
            type="application/javascript"></script>
    <script src="<c:url value='/static/uikit/js/uikit.min.js' />" type="application/javascript"></script>
    <script src="<c:url value='/static/uikit/js/components/lightbox.min.js' />" type="application/javascript"></script>
    <script src="<c:url value='/static/uikit/js/components/slider.min.js' />" type="application/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/uikit/css/components/slider.min.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/style.css' />">
    <title><decorator:title/></title>
</head>
<body>
<nav class="uk-navbar">
    <ul class="uk-navbar-nav"><a href="/" class="uk-navbar-brand">_Crimson</a>
        <sec:authorize access="isAuthenticated()">
            <li><a href="<c:url value="/tv/user/${name}" />">Dashboard</a></li>
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
            <form class="uk-form uk-margin-remove uk-display-inline-block" action="<c:url value="/tv/search"/> " method="post">
                <input name="search" type="text" placeholder="Search"> <a class="search"><i class="fa fa-search fa-lg"></i></a>
                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
            </form>
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
<%--TODO zrobić ładniejszy logout--%>
<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>
</body>
</html>