<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of Tv Shows</title>
</head>
<body>


<%-- Option that allow user to search TvShows according to his own liking --%>
<%-- Available filters : year start, year end, genre, network, country, rating between --%>



<div class="uk-grid uk-margin-large-top uk-margin-large-bottom">
    <div class="uk-width-medium-1-6 uk-width-small-1-1 uk-margin-large-top">
        <form class="uk-form uk-form-stacked uk-margin-large-left" id="filterForm">
            <div class="uk-form-row">
                <label class="uk-form-label" for="releaseYearStart">Year start</label>
                <input type="number" name="releaseYearStart"
                       id="releaseYearStart" value="0"/>
            </div>
            <div class="uk-form-row">
                <label class="uk-form-label" for="releaseYearEnd">Year end</label>
                <input type="number" name="releaseYearEnd"
                       id="releaseYearEnd" value="0"/>
            </div>
            <div class="uk-form-row">
                <label class="uk-form-label" for="genre">Genre</label>
                <input type="text" name="genre"
                       id="genre"/>
            </div>
            <div class="uk-form-row">
                <label class="uk-form-label" for="network">Network</label>
                <select id="network" name="network">
                    <option selected value="0">Network</option>
                    <option value="HBO">HBO</option>
                    <option value="Netflix">Netflix</option>
                    <option value="Showtime">Showtime</option>
                    <option value="NBC">NBC</option>
                    <option value="Canal+">Canal+</option>
                    <option value="Fox">Fox</option>
                    <option value="BBC One">BBC One</option>
                </select>
            </div>
            <div class="uk-form-row">
                <label class="uk-form-label" for="country">Country</label>
                <select id="country" name="country">
                    <option selected value="0">Country</option>
                    <option value="USA">USA</option>
                    <option value="UK">UK</option>
                    <option value="Poland">Poland</option>
                </select>
            </div>
            <div class="uk-form-row">
                <label class="uk-form-label" for="genre">Rating between</label>
                <select id="ratingStart">
                    <option value="1" selected>1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                </select>
                <select id="ratingEnd">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10" selected>10</option>
                </select>
            </div>
            <br>
            <button class="uk-button uk-button-primary " id="filterButton">Filter</button>
            <button class="uk-button " id="filterReset">Reset</button>
        </form>
    </div>
    <div class="uk-width-medium-4-6  uk-width-small-1-1 uk-margin-large-left">
        <div class="genreList uk-margin-large-top " id="filter">
            <div class="loader loader--style2 uk-text-center uk-hidden" title="1">
                <c:import url="loading.jsp"/>
            </div>
        </div>
        <div class="pagination uk-margin-large-top">
        </div>
    </div>

</div>
<script src="<c:url value="/static/js/filter.js"/> ">
</script>
<script>
    filter(${tvSize});
</script>
</body>

</html>
