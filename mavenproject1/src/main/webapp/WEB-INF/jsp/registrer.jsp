<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
    <%@include file="../../includes/head.jspf" %>
    <body>
        <form:form method="POST" modelAttribute="registreringform" action="send" >
            <table>
                <tr>
                    <td>Brukernavn : </td>
                    <td><form:input path="bruker.brukernavn" /></td>
                    <td><form:errors path="bruker.brukernavn" /></td>
                </tr>
                <tr>
                    <td>Epost : </td>
                    <td><form:input path="bruker.epost" /></td>
                    <td><form:errors path="bruker.epost" /></td>
                </tr>
                <tr>
                    <td><input type="submit" value="SEND"></td>
                </tr>
            </table>
        </form:form>
    </body>
</html>
