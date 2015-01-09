<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@include file="../../includes/head.jspf" %>
    <title>Hovedside</title>
    </head>
<html>

    <body>
        <h1>DETTE ER VÅR HOVEDSIDE</h1>
        <a href="<c:url value="game"/>" >spillet!</a>
        <br> <p> Hallo <%= session.getAttribute("Username") %> </p>
      
    </body>
</html>
