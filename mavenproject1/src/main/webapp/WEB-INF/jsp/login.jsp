<%@page contentType="text/html" session="true" pageEncoding="UTF-8"%>
<%@include file="../../includes/head.jspf" %>
<c:url var="niceLogUrl" value="/resources/css/niceLogin.css" />
<link href="${niceLogUrl}" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
    function usinp(elem){
        var s = $(elem).val();
        var Re = new RegExp("\\s", "g");
        $(elem).val(s.replace(Re,""));
    }
    
    
</script>
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
                <form:input path="user.email" oninput="usinp(this)" type="email" class="input username"  placeholder="Email" required="true" />
                <div class="user-icon"><img src="resources/img/user.png" /></div>
                <form:input type="password" oninput="usinp(this)" path ="user.password" class="input password" required="true" placeholder="Passord" />
                <div class="pass-icon"><img src="resources/img/lock.png" /></div>	
            </div>

            <div class="footer">
                <a href="<c:url value="registrer"/>" class="register" >Registrer</a>
                <input type="submit" name="submit" value="Login" class="button" />
                <a href="<c:url value="glemtPassord"/>" class="register" id ="glemtPassord" >Glemt passord</a>
                <i class="errorMessage" id="loginError">
                    <c:out value = "${loginError}"/>
                    <form:errors path="user.email" />
                    <form:errors path="user.password" />
                </i>
            </div>
        </form:form>
    </div>
    <!-- Husk å fjerne! -->
    <a href="logincheat" style="position:fixed; left:3rem; bottom:2rem;">Logincheat</a>
    <a href="testError" style="position:fixed; left:3rem; bottom:1rem;">Generer en feil</a>
</body>
</html>
