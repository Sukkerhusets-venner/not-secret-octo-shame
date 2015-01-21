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
    
    function glemtPassord() {
        var s = $("#GlemtPassordForm").css("display");
        $("#GlemtPassordForm").css ("visibility", "visible");
        if(s == "none"){
            $("#GlemtPassordForm").fadeIn("slow");
        }else{
            $("#GlemtPassordForm").fadeOut("slow");
        }
    }
    
    $(document).ready(function () {
        if(${not empty GlemtPassordError}) {
            $("#GlemtPassordForm").css ("visibility", "visible");
            $("#GlemtPassordForm").css ("display", "initial");
        }
    });
</script>
<title>Log inn</title>
</head>
<body>
    <div id="wrapper">
        <c:if test="${notConnected}">
        <div id="notConnected">
            <p>Du er ikke tilkoblet databasen</p>
            <a href="login">Prøv på nytt</a>
        </div>
        </c:if>
        <c:if test="${loggedIn}">
        <div id="notConnected">
            <p>Du er allerede logget inn ${currentUser}</p>
            <a href="hovedside">Til hovedsiden</a>
        </div>
        </c:if>
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
                <i class="errorMessage" id="loginError">
                    <c:out value = "${loginError}"/>
                    <form:errors path="user.email" />
                    <form:errors path="user.password" />
                </i>
            </div>
        </form:form>
    </div>
    <div id ="GlemtPassordForm">
        <form:form method ="POST" modelAttribute = "loginform" action = "glemtPassord">
                <form:input id="inpGlemt" path ="user.email" type ="email" placeholder = "Email" />
                <input type ="submit" value ="Send nytt passord" id = "NyttPassordButton">
                <c:if test="${newPassSuccess}">
                    <p>Sendt!</p>
                    <style>#GlemtPassordForm{visibility: visible;}</style>
                </c:if>
        </form:form>
        <p id = "errormessageGP" style="color:red;"> <c:if test="${not empty GlemtPassordError}"> <c:out value ="${GlemtPassordError}"/> </c:if></p>
    </div>     
    <a href="javascript:glemtPassord()" style="position:fixed; right:2rem; bottom:2rem;"> Glemt passord</a>
</body>
</html>
