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
    $(document).ready(function(){
        //fjernes
        $("#spill").css("visibility", "hidden");
        $("#hiscore").css("visibility", "visible");
        
    });
    function checkGame(){
        if("${loginform.isInGame()}"){
            reset();
            alertify.set({ labels: { ok: "Fortsett", cancel: "Start et nytt" } });
            alertify.confirm("Vil du fortsette det gamle spillet eller starte på et nytt et?", function (e) {
                if (e) {
                    window.location.href = "game";
                } else {
                    document.forms["game"].elements["inGame"].value = false;
                    document.forms["game"].submit();
                }
            });
        } else {
            window.location.href = "game";
        }
    }
    function reset () {
        $("#toggleCSS").attr("href", "../themes/alertify.default.css");
        alertify.set({
            labels : {
                ok     : "OK",
                cancel : "Cancel"
            },
            delay : 5000,
            buttonReverse : false,
            buttonFocus   : "ok"
        });
    }
</script>
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
                    <h2> Resultater </h2>
                    <div id="drWrap">
                        <h3>Dine Resultater</h3>
                            <p>Fant ingen resultater på deg</p>
                    </div>
                    <div id="godkjWrap">
                        <h3>Godkjenning</h3>
                         <c:if test = "${not empty godkjentListe}">
                            <table class="finTabell">
                                <tr>
                                    <th>Bruker</th>
                                    <th>Godkjent</th>
                                </tr>
                            <c:forEach var="UserScoreOverview" items="${godkjentListe}">
                                <c:if test="${not empty UserScoreOverview.user.username}">
                                <tr>
                                    <td><c:out value="${UserScoreOverview.user.username}"/></td>
                                    <td>
                                        <c:if test="${UserScoreOverview.passed}">Ja</c:if>
                                        <c:if test="${not UserScoreOverview.passed}">Nei</c:if>
                                    </td>
                                </tr>
                                </c:if>
                            </c:forEach>
                            </table>
                        </c:if><c:if test= "${empty godkjentListe}">
                            <p>Fant ingen resultater på deg</p>
                        </c:if>
                    </div>
                    <div id="highWrap">
                        <h3>HiScores</h3>
                        <c:if test = "${not empty loginform.hiScore}">
                            <table class="finTabell">
                                <tr>
                                    <th>Bruker</th>
                                    <th>Score</th>
                                </tr>
                                <c:forEach var="UserScore" items="${loginform.hiScore}">

                                    <tr><td><c:out value="${UserScore.username}"/></td>
                                        <td><c:out value="${UserScore.highScore}"/></td></tr>


                                </c:forEach></table>
                            </c:if><c:if test="${empty loginform.hiScore}">
                            <p> Fant ingen Hiscores</p>
                            </c:if>
                    </div>
                    
                </div>
                <div id="profil">
                    <p>Brukernavn: ${loginform.user.username}</p>
                    <p>Email: <c:out value = "${loginform.user.email}"/></p>
                    <br> 
                    <button type = "button" id ="byttBrukernavn" > Bytt brukernavn </button>
                    <button type = "button" id ="byttPassord" > Bytt passord </button>
                    <div id ="brukernavnform" >
                        <table>
                        <form:form method="POST" modelAttribute="editform" action ="byttBrukernavn"> 
                        <tr> <td> <form:input path = "userNew.username" placeholder = "Nytt brukernavn"/> </td>
                        <td> <c:if test = "${not empty InputfeilBrukernavn}"> <c:out value = "${InputfeilBrukernavn}"/> </c:if> </td> </tr>
                        <tr> <td> <form:input path = "userOld.password" placeholder = "Bekreft passord" /> </td>
                        <td> <c:if test = "${not empty InputfeilPassord}"> <c:out value = "${InputfeilPassord}"/> </c:if> </td> </tr>
                        <tr> <td> <input id = "button" type="submit" name="Send" value="byttBrukernavn"/> </td> </tr>
                        <tr> <td> <c:if test = "${not empty feilpassord}"> <c:out value = "${feilpassord}"/> </c:if> </td> </tr>
                        </form:form>
                        </table>

                    </div>
                    <div id ="passordform" >
                        <form:form method="POST" modelAttribute="editform" action ="byttPassord">
                        <form:input path="userOld.password" placeholder = "Gammelt passord"/>
                        <form:input path = "userNew.password" placeholder = "Nytt passord"/>
                        <input id = "button" type="submit" name="Send" value="byttpassord"/>
                        </form:form>

                    </div>
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
    </body>
</html>
