<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sec:authentication var="name" property="name"/>

<html>
<head>
    <title>Title</title>
</head>
<body>

<div class="uk-container uk-container-center uk-margin-large-top">
    <sec:authorize access="isAuthenticated()">
        <h1>Hello ${name}!</h1>
    </sec:authorize>
    <sec:authorize access="isAnonymous()">
        <h1>Hello World!</h1>
    </sec:authorize>
</div>
<div class="uk-grid">

</div>
</body>
</html>
