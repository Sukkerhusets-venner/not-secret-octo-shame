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
                    <h1>C. H. I. L.</h1>
                </div>
                <div id="buttons">
                    <!-- Ikke formater disse divene! -->
                    <div><a>Spillet</a>
                    </div><div><a href="hovedside">Resultater</a>
                    </div><div><a href="hovedside">Profil</a>
                    </div>
                    <!-- ---------------------------- -->
                </div>
            </div>
            <div id="plassering"> <h3>Oppsummering</h3>
        <c:forEach var="i" begin="1" end="${loginform.getAssignment().getTaskNr()}" >
            <p>Oppgave ${i}:  ${loginform.getAssignment().getDelscore(i-1)} / ${loginform.getAssignment().getMaxPoeng(i-1)}</p>
        </c:forEach>
            <br/>
            <p><b>Sum: ${loginform.getAssignment().sumUp()} / ${loginform.getAssignment().getMaxScore()}</b></p>
            <br/><br/>
            <form:form action ="hovedside" id="hovedside" name="hovedside"> 
                <input type="submit" value="Tilbake til hovedsiden" id="tilbake">
            </form:form>
            </div>
        </div>
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
