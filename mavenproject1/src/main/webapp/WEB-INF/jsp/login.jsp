<%@page contentType="text/html" session="true" pageEncoding="UTF-8"%>
    <%@include file="../../includes/head.jspf" %>
    <title>Log inn</title>
    </head>
    <body>
            <div class="midForm">
            <form:form method="POST" modelAttribute="loginform" action ="Log inn" >
                <table>
                    <tr>
                         <td> Email : </td>
                         <td><form:input path="user.email" /></td>
                        <td> <c:if test = "${not empty usernameEmptyError}"> <c:out value = "${usernameEmptyError}"/> </c:if> </td>
                    </tr>
                    <tr>
                        <td> Passord: </td>
                        <td><form:input type="password" path ="user.password" /></td>
                        <td> <c:if test = "${not empty passwordEmptyError}"> <c:out value = "${passwordEmptyError}"/> </c:if> </td>
                    </tr>
                    <tr>
                        <td><input type='submit' value="Log inn"> </td>
                        <td> <c:if test = "${not empty loginError}"> <c:out value = "${loginError}"/> </c:if> </td>
                    </tr>
    
                </table>
            </form:form>
            </div>
        <br>
        <a href="<c:url value="registrer"/>" >Har du ikke bruker? Registrer deg her da vel!</a>
        
        <br> <br> <a href ="<c:url value = "logincheat"/>" > login cheat for de som ikke gidder/kan logge inn :) </a>

        <br> <br> <a href ="<c:url value="snake" />" >Snakes! </a>
         
    </body>
</html>
