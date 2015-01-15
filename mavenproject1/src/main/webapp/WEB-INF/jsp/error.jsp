<%@page contentType="text/html" session="true" pageEncoding="UTF-8"%>
<%@include file="../../includes/head.jspf" %>
<c:url var="reUrl" value="/resources/css/error.css" />
<link href="${reUrl}" rel="stylesheet" type="text/css"/>
<title>Hovedside</title>
</head>
<html>
    <body>
        <div class="headspace"></div>
        <div id="wrapper">
            <h1>404</h1>
            <h3>Der gikk det galt!</h3>
            <c:url var="loginUrl" value="/login" />
            <center><div id="ftknapp"><a href="${loginUrl}">Tilbake</a></div></center>
        </div>
        <p><spring:message code="feilside.unntak" /> ${unntak}</p>
</body>
</html>
