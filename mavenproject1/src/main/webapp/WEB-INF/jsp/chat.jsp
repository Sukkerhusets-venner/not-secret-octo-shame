<%@include file="../../includes/head.jspf" %>
        <title>Game</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="resources/js/alertify.min.js"></script>
        <link rel="stylesheet" href="resources/css/alertify.core.css" />
	<link rel="stylesheet" href="resources/css/alertify.default.css" id="toggleCSS" />
        <c:url var="nmpUrl" value="/resources/css/niceChat.css" />
        <link href="${nmpUrl}" rel="stylesheet" type="text/css"/>  
        <style>
            .melding { display: inline };
            #name { color: gray};
        </style>
        <script>
            function getBrukernavn(text){
                var split = text.split(":");
                return split[0];
            } 
            function getMelding(text){
                var split = text.split(":");
                var ret = "";
                for(var i=1; i<split.length; i++){
                    ret+=":"+split[i];
                }
                return ret;
            } 
            
            function tilHovedmeny(){
                reset();
                alertify.set({ labels: { ok: "Fortsett å spille", cancel: "Gå til hovedmeny" } });
                alertify.confirm("<b>Er du sikker på at du vil returnere til hovedmenyen?<br/>Du vil kunne fortsette spillet, men timescoren din vil bli satt til 0.</b><br/><br/>", function (e) {
                    if (!e) {
                        window.location.href = "hovedside";
                    }
                });
            }
            function reset () {
                $("#toggleCSS").attr("href", "resources/css/alertify.default.css");
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
    <body>
            <div id="wrapper"> 
              <div class="header">
                <div class="mptitle">
                    <div id="smiley"><object type="image/svg+xml" data="resources/img/grin.svg"></object></div>
                    <h1>Spillet</h1>
                </div>
                <div id="buttons">
                    <!-- Ikke formater disse divene! -->
                    <div><a href="game">Game</a>
                    </div><div><a href="javascript:tilHovedmeny()">Resultater</a>
                    </div><div><a href="javascript:tilHovedmeny()">Profil</a>
                    </div>
                    <!-- ---------------------------- -->
                </div>
            </div>
        <section id="content">
            <section class="block"> 
                <c:choose>
                    <c:when test="${chatform.isInChat() == false}">
                        <form:form method="POST" modelAttribute="chatform" action ="velgChat" id="velgChat" name="velgChat">
                            <h1>Chat</h3><br/>
                            <h3>Aktive samtaler:</h3> 
                            <c:forEach var="user" items="${chatform.getChatUserlist()}">
                                <input type="submit" class="knapp" name="chosen" value="${user.getEmail()}" ><br/>
                            </c:forEach>
                                <br/><h3>Andre brukere:</h3>
                            <c:forEach var="user" items="${chatform.getUserlist()}">
                                <input type="submit" class="knapp" name="chosen" value="${user.getEmail()}" ><br/>
                            </c:forEach>
                        </form:form>
                    </c:when>
                    <c:otherwise>
                        <form:form method="POST" modelAttribute="chatform" action ="sendMeldning" id="sendMeldning" name="sendMeldning">
                            <h1>Chat</h3><br/>
                            <h3>Din samtale med ${chatform.getChosen()}:</h3> 
                            <c:forEach var="msg" items="${chatform.getMessages()}">
                                <p id="name" class="melding"><c:out value="${getBrukernavn(msg)}"/></p>
                                <p id="meld" class="melding"><c:out value="${getMelding(msg)}"/></p><br/>
                            </c:forEach>
                            <form:input path="melding" required="true" class="input melding" placeholder="skriv din melding her.." />
                            <br/><br/>
                            <input type="submit" class="knapp" value='Send melding' ><br/>
                        </form:form>
                    </c:otherwise>
                </c:choose>
                <br/>
                <a href="chat">Tilbake</a>
            </section>
        </section>
            </div>
    </body>
</html>

