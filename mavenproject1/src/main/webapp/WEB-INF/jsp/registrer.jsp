<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
    <%@include file="../../includes/head.jspf" %>
    <title>Registrer</title>
</head>
    <body>
        <form:form method="POST" modelAttribute="registreringform" action="send" >
            <table>
                <tr>
                    <td>Brukernavn : </td>
                    <td><form:input path="user.username" /></td>
                    <td><form:errors path="user.username" /></td>      
                    <td> <c:if test = "${not empty usernameEmptyError}"> <c:out value = "${usernameEmptyError}"/> </c:if> </td>
                </tr>
                <tr>
                    <td>Epost : </td>
                    <td><form:input path="user.email" /></td>
                    <td><form:errors path="user.email" /></td>
                    <td> <c:if test = "${not empty emailError}"> <c:out value = "${emailError}"/> </c:if> </td>
                </tr>
                <tr>
                    <td><input type="submit" value="SEND"></td>
                    <td> <c:if test = "${not empty registerError}"> <c:out value = "${registerError}"/> </c:if> </td>
                </tr>
            </table>
        </form:form>
        
        <br> <br> <a href ="<c:url value = "registrertest"/>" > registertest </a>
    </body>
</html>
