<%@include file="../../includes/head.jspf" %>
        <title>Game</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="resources/js/alertify.min.js"></script>
        <link rel="stylesheet" href="resources/css/alertify.core.css" />
	<link rel="stylesheet" href="resources/css/alertify.default.css" id="toggleCSS" />
        <c:url var="nmpUrl" value="/resources/css/niceChat.css" />
        <link href="${nmpUrl}" rel="stylesheet" type="text/css"/>  
        <script>
            function setClass(Id){
                document.getElementById(Id).className = "messaged";
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
        <section id="content">
            <section class="block">
                <c:choose>
                    <c:when test="${chatform.isInChat() == false}">
                        <form:form method="POST" modelAttribute="chatform" action ="velgChat" id="velgChat" name="velgChat">
                            <h3 class="listHeader">Dine aktive samtaler:</h3>
                          <!--  <h3 class="listHeader">Lærere:</h3> -->
                            <h3 class="listHeader">Elever:</h3>
                            <br/>
                            <table id="scrollable2">
                            <c:forEach var="user" items="${chatform.getChatUserlist()}">
                                <tr><td><input type="submit" id="${user.getEmail()}" class="knapp" name="chosen" value="${user.getEmail()}" ><br/></td></tr>
                                <c:if test="${user.isMessaged()}">
                                    <script>setClass("${user.getEmail()}");</script>
                                </c:if>
                            </c:forEach>
                            </table>
                            <!--<table id="scrollable2">
                            <c:forEach var="user" items="${chatform.getAdminlist()}">
                                <tr><td><input type="submit" class="knapp" name="chosen" value="${user.getEmail()}" ><br/></td></tr>
                            </c:forEach>
                            </table>-->
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
                            <form:input path="melding" required="true" class="input" placeholder="skriv din melding her.." />
                            <br/><br/>
                            <input type="submit" id="sndMld" class="knapp" value='Send melding' >
                            <a id="tebaks" href="chat">Tilbake</a>
                        </form:form>
                    </c:otherwise>
                </c:choose>
                <br/>
            </section>
    </section>
            </div>
    </body>
</html>

