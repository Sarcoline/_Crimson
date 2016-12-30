<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="name" property="name"/>

<html>
<body>
<div class="uk-container uk-container-center uk-margin-large-top">
    <sec:authorize access="isAuthenticated()">
        <h1>Hello ${name}!</h1>
    </sec:authorize>
    <sec:authorize access="isAnonymous()">
        <h1>Hello World!</h1>
    </sec:authorize>
</div>
</body>
</html>
