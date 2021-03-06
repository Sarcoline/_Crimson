<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="name" property="name"/>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link href="https://fonts.googleapis.com/css?family=Raleway|Roboto:900|Montserrat:400" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/uikit/css/uikit.min.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/font-awesome-4.7.0/css/font-awesome.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/uikit/css/components/slidenav.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/uikit/css/components/slider.min.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/uikit/css/components/notify.min.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/uikit/css/components/datepicker.min.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/uikit/css/components/form-file.min.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/codemirror/lib/codemirror.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/uikit/css/components/htmleditor.min.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/simplePagination/simplePagination.css' />">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/style.css' />">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"
            type="application/javascript"></script>
    <script src="<c:url value='/static/simplePagination/jquery.simplePagination.js' />"
            type="application/javascript"></script>
    <script src="<c:url value='/static/uikit/js/uikit.min.js' />" type="application/javascript"></script>
    <script src="<c:url value='/static/uikit/js/components/lightbox.min.js' />" type="application/javascript"></script>
    <script src="<c:url value='/static/uikit/js/components/slider.min.js' />" type="application/javascript"></script>
    <script src="<c:url value='/static/uikit/js/components/notify.min.js' />" type="application/javascript"></script>
    <decorator:getProperty property="page.local_script"/>
    <title><decorator:title/></title>
    <sec:csrfMetaTags/>
</head>
<body>
<%-- Navigation bar --%>
<nav class="uk-navbar">
    <ul class="uk-navbar-nav uk-"><a href="/" class="uk-navbar-brand">_Crimson</a>
        <sec:authorize access="isAuthenticated()">
            <li><a href="<c:url value="/user/${name}" />">Dashboard</a></li>
        </sec:authorize>
        <li class="uk-parent"><a href="<c:url value="/tv/list"/> ">TvShows</a></li>
        <div class="uk-navbar-content uk-hidden-small">
            <div class="searchForm uk-form uk-margin-remove uk-display-inline-block">
                <input id="search" name="search" type="text" placeholder="Search" autocomplete="off" size="40">
            </div>
            <div class="genreList" id="found" style="z-index: 9999; position: absolute;">
            </div>
        </div>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN')">
            <li class="uk-parent" data-uk-dropdown="{mode:'click'}" aria-haspopup="true" aria-expanded="false">
                <a href="">Manage</a>
                <div class="uk-dropdown uk-dropdown-navbar uk-dropdown-bottom navDropdown">
                    <ul class="uk-nav uk-nav-navbar">
                        <li><a href="<c:url value="/tv/add"/> ">Add TvShow</a></li>
                    </ul>
                </div>
            </li>
        </sec:authorize>

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

<%-- Body --%>

<div id="wrapper">
    <decorator:body/>
</div>

<%-- Footer --%>
<div class="footer"><strong>@Crimson</strong>.</div>

<script src="<c:url value="/static/js/search.js"/>"></script>
<script>
    function formSubmit() {
        document.getElementById('logoutForm').submit();
    }
    $(function () {
        var search = $('#search');
        var found = $('#found');
        searchFunction(search, found);
    });

</script>
</body>
</html>