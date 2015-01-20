<%@include file="../../includes/head.jspf" %>
 <c:url var="nmpUrl" value="/resources/css/niceScorepage.css" />
 <link href="${nmpUrl}" rel="stylesheet" type="text/css"/>
        <title>Score</title>
    </head>
    <body>
        <div id="wrapper"> 
              <div class="header">
                <div class="mptitle">
                    <div id="smiley"><object type="image/svg+xml" data="resources/img/grin.svg"></object></div>
                    <h1>Spillet</h1>
                </div>
                <div id="buttons">
                    <!-- Ikke formater disse divene! -->
                    <div><a>Chat</a>
                    </div><div><a href="meny">Resultater</a>
                    </div><div><a href="meny">Profil</a>
                    </div>
                    <!-- ---------------------------- -->
                </div>
            </div>
            <div id="plassering"> <h3>Oppsummering</h3>
        <c:forEach var="i" begin="1" end="${assignment.getTaskNr()}" >
            <p>Oppgave ${i}:  ${assignment.getDelscore(i-1)} / ${assignment.getMaxPoeng(i-1)}</p>
        </c:forEach>
            <br/>
            <p><b>Sum: ${assignment.sumUp()} / ${assignment.getMaxScore()}</b></p>
            <br/><br/>
            <form:form method="POST" action ="meny" id="meny" name="meny"> 
                <input type="submit" value="Tilbake til hovedsiden" id="tilbake">
            </form:form>
            </div>
        </div>
    </body>
</html>
