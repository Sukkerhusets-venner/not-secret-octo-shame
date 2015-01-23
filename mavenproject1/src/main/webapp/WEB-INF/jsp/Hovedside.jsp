<%@page contentType="text/html" session="true" pageEncoding="UTF-8"%>
<%@include file="../../includes/head.jspf" %>
<c:url var="nmpUrl" value="/resources/css/niceMainpage.css" />
<c:url var="loaderUrl" value="/resources/css/loader.css" />
<c:url var="mkjsUrl" value="/resources/js/menyKontroll.js" />
<script src="resources/js/alertify.min.js"></script>
<link rel="stylesheet" href="resources/css/alertify.core.css" />
<link rel="stylesheet" href="resources/css/alertify.default.css" id="toggleCSS" />
<link href="${nmpUrl}" rel="stylesheet" type="text/css"/>
<link href="${loaderUrl}" rel="stylesheet" type="text/css" />
<title>Hovedside</title>
<script src="${mkjsUrl}"></script>
<script>
    var sjekk = "${loginform.isInGame()}";
    $(document).ready(function () {
        var a = $("#spill");
        var b = $("#hiscore");
        var c = $("#profil");
    <c:if test = "${not empty profilpassord}">
        a.css("visibility", "hidden");
        b.css("visibility", "hidden");
        c.css("visibility", "visible");
        $("#passordform").css("visibility", "visible");
        $("#brukernavnform").css("visibility", "hidden");
    </c:if>
    <c:if test="${not empty profilbrukernavn}">
        a.css("visibility", "hidden");
        b.css("visibility", "hidden");
        c.css("visibility", "visible");
        $("#brukernavnform").css("visibility", "visible");
        $("#passordform").css("visibility", "hidden");
    </c:if>
});
</script>
</head>
    <body>
        <div class="headspace"></div>
        <div id="wrapper">
            <div class="header">
                <div class="mptitle">
                    <div id="smiley"><object type="image/svg+xml" data="resources/img/grin.svg"></object></div>
                    <h1>Velkommen</h1>
                </div>
                <div id="buttons">
                    <!-- Ikke formater disse divene! -->
                    <div><a>Spillet</a>
                    </div><div><a>Resultater</a>
                    </div><div><a>Profil</a>
                    </div>
                    <!-- ---------------------------- -->
                </div>
            </div>
            <!--  Eksempel: admin::
            <sec:authorize access="hasRole('admin')"></sec:authorize> -->
            <div id="stuff">
                <div id="spill">
                    <h3>Spill</h3>
                    
                    <form:form method="POST" modelAttribute="loginform" action ="game" id="game" name="game">
                        <input type="hidden" name="inGame" value=''>
                    </form:form>
                    <c:url var="spaceInvaderUrl" value="/resources/css/spaceInvader.css" />
                    <div id="invadeBanner"><h2>Dette er css!</h2><div id="arrow"></div><a id="spilLink" href="javascript:checkGame()"> Spill Spillet!</a></div>
                    <link href="${spaceInvaderUrl}" rel="stylesheet" type="text/css"/>
                    <div class="space">
                        <div class="invader"></div>
                    </div>
                </div>
                <div id="hiscore">
                    <%@include file="../../includes/resultater.jspf"%>
                </div>
                <div id="profil">
                    <%@include file="../../includes/profil.jspf"%>
                </div>
            </div>
            <!-- Loader; styres av script -->
            <div class="container">
                <div class="part"></div>
                <div class="part"></div>
                <div class="part"></div>
                <div class="part"></div>
                <div class="part"></div>
            </div>
            <noscript><!-- Hvis javascript er slått av -->
            <style>
                .container{visibility:hidden;}
                #stuff{visibility:hidden;}
            </style>
            <h2 style="padding: 1rem 1rem 1rem 1rem">Du må slå på javascript for å spille spillet.</h2>
            </noscript> 
            
            <!-- ------------------------ -->
        </div>
        <c:if test="${loggedIn}">
            <div style="position:fixed; top:2rem; right:2rem;">
                <a href="Hovedside">Du er logget inn! <%=session.getAttribute("currentUser")%></a>
            </div>
        </c:if>
        <iframe id="chatRamme" scrolling="no" src="chat"></iframe>
        <div id="chatWrap">
            <iframe id="chatNotifier" scrolling="no" src="chatNotifier"></iframe>
            <c:url var="chatImgUrl" value="/resources/img/bubble.svg" />
            <object type="image/svg+xml" data="${chatImgUrl}" id="chatImg"></object>
            <p>Chat</p>
        </div>
        <div id="logWrap">
            <a href="logUt">Log ut</a>
        </div>
        
    </body>
</html>
