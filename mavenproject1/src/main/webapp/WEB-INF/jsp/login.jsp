<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@include file="../../includes/head.jspf" %>
    <body>
            <form:form method="POST" modelAttribute="loginform" >
                <table>
                    <tr>
                         <td> Brukernavn : </td>
                         <td><form:input path="bruker.brukernavn" /></td>
                    </tr>
                    <tr>
                        <td> Passord: </td>
                        <td><form:input type="password" path ="bruker.passord" /></td>
                    </tr>
                    <tr>
                    <td><input type='submit' value="SEND"></td>
                    </tr>
    
                </table>
            </form:form>
    </body>
</html>
