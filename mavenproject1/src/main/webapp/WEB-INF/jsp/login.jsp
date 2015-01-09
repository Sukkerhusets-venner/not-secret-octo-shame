<%@page contentType="text/html" session="true" pageEncoding="UTF-8"%>
<%@include file="../../includes/head.jspf" %>
<link href="resources/css/niceLogin.css" rel="stylesheet" type="text/css"/>
<title>Log inn</title>
</head>
<body>
<center>
    <div class="outer"><!-- top høyre hjørne -->
        <div class="middle"><!-- 50% | 50% // midtstiller -->
            <div class="inner"><!-- 50%-x | x | 50%-x // setter inn elementer -->
                <div id="wrapper">
                    <form:form method="POST" modelAttribute="loginform" action ="Log inn" name="login-form" class="login-form"  >
                        <div class="header">
                            <h1>Login</h1>
                            <span>Venligst log inn eller registrer deg som bruker.</span>
                        </div>
                            <div class="content">
                                <form:input path="user.email" class="input username" placeholder="Email" />
                                <div class="user-icon"><img src="resources/img/user.png" /></div>
                                    <form:input type="password" path ="user.password" class="input password" placeholder="Passord" />
                                <div class="pass-icon"><img src="resources/img/lock.png" /></div>	
                            </div>

                            <div class="footer">
                                <a href="<c:url value="registrer"/>" class="register" >Registrer</a>

                                <input type="submit" name="submit" value="Login" class="button" />
                                <i class="errorMessage" id="loginError"><c:if test = "${not empty loginError}"> <c:out value = "${loginError}"/> </c:if></i>
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
