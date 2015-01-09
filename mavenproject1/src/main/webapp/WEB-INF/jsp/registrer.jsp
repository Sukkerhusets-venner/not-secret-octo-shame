<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
    <%@include file="../../includes/head.jspf" %>
    <link href="resources/css/niceLogin.css" rel="stylesheet" type="text/css"/>
    <title>Registrer</title>
</head>
    <body>
<center>
    <div class="outer"><!-- top høyre hjørne -->
        <div class="middle"><!-- 50% | 50% // midtstiller -->
            <div class="inner"><!-- 50%-x | x | 50%-x // setter inn elementer -->
                <div id="wrapper">
                    <form:form method="POST" modelAttribute="registreringform" action="send" name="login-form" class="login-form">
                        <div class="header">
                            <h1>Registrer</h1>
                            <span>Venligst fyll in skjema og registrer deg som bruker</span>
                        </div>

                        <div class="content">
                            <form:input path="user.username" class="input username" placeholder="Brukernavn" />
                            <div class="user-icon"><img src="resources/img/user.png" /></div>
                            <form:input path="user.email" class="input password" placeholder="Email" />
                            <div class="pass-icon"><img src="resources/img/lock.png" /></div>	
                        </div>

                        <div class="footer">
                            <a href="<c:url value="registrer"/>" class="register" >Registrer</a>
                            
                            <input type="submit" name="submit" value="Login" class="button" />
                            <i class="errorMessage"><form:errors path="user.username" /></i>
                            <i class="errorMessage"><form:errors path="user.email" /></i>
                            <i class="errorMessage"><c:if test = "${not empty emailError}"> <c:out value = "${emailError}"/> </c:if></i>
                            <i class="errorMessage"><c:if test = "${not empty registerError}"> <c:out value = "${registerError}"/> </c:if></i>
                        </div>

                    </form:form>

                </div>
                <div class="gradient"></div>
                
                <div class="clear"></div> <!-- Avslutter delingen -->
            </div><!-- inner -->
        </div><!-- mid -->
    </div><!-- outer -->  
</center>
</body>
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
