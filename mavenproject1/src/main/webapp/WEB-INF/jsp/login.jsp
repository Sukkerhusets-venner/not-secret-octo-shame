<%@page contentType="text/html" session="true" pageEncoding="UTF-8"%>
<%@include file="../../includes/head.jspf" %>
<link href="resources/css/niceLogin.css" rel="stylesheet" type="text/css"/>
<title>Log inn</title>
</head>
<body>
    <div id="wrapper">
        <form:form method="POST" modelAttribute="loginform" action ="Log inn" name="login-form" class="login-form">
            <div class="header">
                <h1>Login</h1>
                <span>Venligst log inn eller registrer deg som bruker.</span>
            </div>
            <div class="content">
                <form:input path="user.email" class="input username"  placeholder="Email" required="true" />
                <div class="user-icon"><img src="resources/img/user.png" /></div>
                <form:input type="password" path ="user.password" class="input password" required="true" placeholder="Passord" />
                <div class="pass-icon"><img src="resources/img/lock.png" /></div>	
            </div>

            <div class="footer">
                <a href="<c:url value="registrer"/>" class="register" >Registrer</a>
                <input type="submit" name="submit" value="Login" class="button" />
                <i class="errorMessage" id="loginError">
                    <c:out value = "${loginError}"/>
                    <form:errors path="user.email" />
                    <form:errors path="user.password" />
                </i>
            </div>
        </form:form>
    </div>
    <% session.setAttribute("Username", "Logincheat"); %>
    <a href="logincheat" style="position:fixed; left:3rem; bottom:2rem;">Logincheat</a>
</body>
</html>
