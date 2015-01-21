<%@include file="../../includes/head.jspf" %>
        <title>Game</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="resources/js/alertify.min.js"></script>
        <link rel="stylesheet" href="resources/css/alertify.core.css" />
	<link rel="stylesheet" href="resources/css/alertify.default.css" id="toggleCSS" />
        <c:url var="nmpUrl" value="/resources/css/niceChat.css" />
        <link href="${nmpUrl}" rel="stylesheet" type="text/css"/>  
        <style>
            #scrollable {
                border:4px solid #70c469;
                overflow-y:scroll;
                height:200px;
                width: 600px;
                display:block;
            }
            #scrollable2 {
                padding: 10px;
                float: left;
                border:4px solid #70c469;
                overflow-y:scroll;
                height:260px;
                width:225px;
                display:block;
            }
            .listHeader{
                display: inline;
                float: left;
                width: 250px;
            }
            .input{
                width: 600px;
            }
        </style>
        <script>
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
                <h1>Chat</h1><br/>
                <c:choose>
                    <c:when test="${chatform.isInChat() == false}">
                        <form:form method="POST" modelAttribute="chatform" action ="velgChat" id="velgChat" name="velgChat">
                            <h3 class="listHeader">Dine aktive samtaler:</h3>
                            <h3 class="listHeader">Lærere:</h3>
                            <h3 class="listHeader">Elever:</h3>
                            <br/>
                            <table id="scrollable2">
                            <c:forEach var="user" items="${chatform.getChatUserlist()}">
                                <tr><td><input type="submit" class="knapp" name="chosen" value="${user.getEmail()}" ><br/></td></tr>
                            </c:forEach>
                            </table>
                            <table id="scrollable2">
                            <c:forEach var="user" items="${chatform.getAdminlist()}">
                                <tr><td><input type="submit" class="knapp" name="chosen" value="${user.getEmail()}" ><br/></td></tr>
                            </c:forEach>
                            </table>
                            <table id="scrollable2">
                            <c:forEach var="user" items="${chatform.getUserlist()}">
                                <tr><td><input type="submit" class="knapp" name="chosen" value="${user.getEmail()}" ><br/></td></tr>
                            </c:forEach>
                            </table>
                        </form:form>
                    </c:when>
                    <c:otherwise>
                        <form:form method="POST" modelAttribute="chatform" action ="sendMeldning" id="sendMeldning" name="sendMeldning">
                            <h3>Din samtale med ${chatform.getChosen()}:</h3> 
                            <table id="scrollable">
                                <c:forEach var="msg" items="${chatform.getMessages()}">
                                    <tr><td><p>${msg.getText()}</p></td></tr>
                                </c:forEach>
                            </table>
                            <br/>
                            <form:input path="melding" required="true" class="input" placeholder="skriv din melding her.." />
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

