<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <h1>Add episodes</h1>
    <div class="uk-grid uk-grid-large uk-margin-large-top">
        <div id="test">

        </div>
    </div>
</div>
<script>
    $(function () {
        'use strict';
        var tvshow = '${name}';
        var episodes = [];
        var createForm = function (number,season,date,title) {
            var f = document.createElement("form");
            f.setAttribute('method',"post");
            f.setAttribute('action'," ");
            f.setAttribute('class', "uk-form uk-margin-top");

            var titleInput = document.createElement("input"); //input element, text
            titleInput.setAttribute('type',"text");
            titleInput.setAttribute('name',"Title");
            titleInput.setAttribute('value', title);

            var i = document.createElement("input"); //input element, text
            i.setAttribute('type',"text");
            i.setAttribute('name',"releaseDate");
            i.setAttribute('value', date);
            i.setAttribute('data-uk-datepicker', "{format:'YYYY-MM-DD'}");

            var seasonInput = document.createElement("input"); //input element, text
            seasonInput.setAttribute('type',"number");
            seasonInput.setAttribute('name',"Season");
            seasonInput.setAttribute('value', season);

            var numberInput = document.createElement("input"); //input element, text
            numberInput.setAttribute('type',"number");
            numberInput.setAttribute('name',"Number");
            numberInput.setAttribute('value', number);

            var csrf = document.createElement("input");
            csrf.setAttribute("type", "hidden");
            csrf.setAttribute("name", "${_csrf.parameterName}");
            csrf.setAttribute("value", "${_csrf.token}");
            var submit = document.createElement("input");
            submit.setAttribute('type', "submit");
            submit.setAttribute('class', "uk-button uk-button-success");
            submit.setAttribute('value', "Save!");
            f.appendChild(seasonInput);
            f.appendChild(numberInput);
            f.appendChild(titleInput);
            f.appendChild(i);
            f.appendChild(csrf);
            f.appendChild(submit);
            return f;
        };
        console.log(createForm(1,1, "test","test"));
        var getJson = function () {
            $.getJSON('http://www.omdbapi.com/?t=' + tvshow, function (data) {
                console.log(data.Genre, data.Title);
                for (var i = 1; i <= data.totalSeasons; i += 1) {
                    (function (i) {
                        $.getJSON('http://www.omdbapi.com/?t=' + tvshow + '&season=' + i, function (data) {
                            $.each(data.Episodes, function (val, key) {
                                $('#test').append(createForm(key.Episode, i, key.Released, key.Title));
                            });
                        });
                    })(i);
                }
            });
        };
        getJson();
    });
</script>
</body>
</html>
