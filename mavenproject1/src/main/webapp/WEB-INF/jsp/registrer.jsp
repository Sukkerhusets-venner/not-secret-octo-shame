<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
    <%@include file="../../includes/head.jspf" %>
    <body>
        <form:form method="POST" modelAttribute="registreringform" action="send" >
            <table>
                <tr>
                    <td>Brukernavn : </td>
                    <td><form:input path="user.username" /></td>
                    <td><form:errors path="user.username" /></td>
                </tr>
                <tr>
                    <td>Epost : </td>
                    <td><form:input path="user.email" /></td>
                    <td><form:errors path="user.email" /></td>
                </tr>
                <tr>
                    <td><input type="submit" value="SEND"></td>
                </tr>
            </table>
        </form:form>
    </body>
</html>