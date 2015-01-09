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
                            <h1>Registrer deg</h1>
                            <span>Venligst fyll in skjema og registrer deg som bruker.</span>
                        </div>

                        <div class="content">
                            <form:input path="user.username" class="input username" placeholder="Brukernavn" />
                            <div class="user-icon"><img src="resources/img/user.png" /></div>
                            <form:input path="user.email" class="input password" placeholder="Email" />
                            <div class="pass-icon"><img src="resources/img/mail.png" /></div>	
                        </div>

                        <div class="footer">
                            <input id="registrer" type="submit" name="submit" value="Registrer" class="button" />
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
</html>
