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
<script>var sjekk = "${loginform.isInGame()}";</script>
</head>
<html>
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
                    <div><a href="javascript:checkGame()">Spillet</a>
                       <form:form method="POST" modelAttribute="loginform" action ="game" id="game" name="game">
                            <input type="hidden" name="inGame" value=''>
                       </form:form>
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
            <a href="logUt" style="position:fixed; left:3rem; bottom:2rem;">Log ut</a>
            <!-- ------------------------ -->
        </div>
        <c:if test="${loggedIn}">
            <div style="position:fixed; top:2rem; right:2rem;">
                <a href="Hovedside">Du er logget inn! <%=session.getAttribute("currentUser")%></a>
            </div>
        </c:if>
    </body>
</html>
