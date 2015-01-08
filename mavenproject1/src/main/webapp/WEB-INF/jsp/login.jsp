<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@include file="../../includes/head.jspf" %>
    <body>
            <form:form method="POST" modelAttribute="loginform" action ="Log inn" >
                <table>
                    <tr>
                         <td> Brukernavn : </td>
                         <td><form:input path="user.email" /></td>
                    </tr>
                    <tr>
                        <td> Passord: </td>
                        <td><form:input type="password" path ="user.password" /></td>
                    </tr>
                    <tr>
                    <td><input type='submit' value="Log inn"></td>
                    </tr>
    
                </table>
            </form:form>
        <br>
        <a href="<c:url value="registrer"/>" >Har du ikke bruker? Registrer deg her da vel!</a>
        
        <br> <br> <a href ="<c:url value = "logincheat"/>" > login cheat for de som ikke gidder/kan logge inn :) </a>

        <br> <br> <a href ="<c:url value="jSpill" />" >Meeep </a>
    </body>
</html>
