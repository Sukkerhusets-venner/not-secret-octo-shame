<%@include file="../../includes/head.jspf" %>
        <title>Game</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="resources/js/alertify.min.js"></script>
        <link rel="stylesheet" href="resources/css/alertify.core.css" />
	<link rel="stylesheet" href="resources/css/alertify.default.css" id="toggleCSS" />
        <c:url var="nmpUrl" value="/resources/css/niceMultichoice.css" />
        <link href="${nmpUrl}" rel="stylesheet" type="text/css"/>
 
        
        <script>
            var oppgNr = 0;
            var riktigSvar = 0;
            var oppgTekst = "";
            var timescore = 0;
            var svarOppg = "";
            var tekst = "";
            var svar1 = "";
            var svar2 = "";
            var svar3 = "";
            var svar4 = "";
            
            $(document).ready(function() {
                timescore = "${assignment.getTimescore()}";
                setInterval(function(){
                    if(timescore > 0){
                        timescore--;
                    }
                },20000);
                
                svar1 = "${assignment.getCurrentTask().getTaskHtml()}";
                svar2 = "${assignment.getCurrentTask().getAnswerHtml()}";
                svar3 = "${assignment.getCurrentTask().getTaskCss()}";
                svar4 = "${assignment.getCurrentTask().getAnswerCss()}";
                tekst = "${assignment.getCurrentTask().getText()}";
                var split = tekst.split("$");
                riktigSvar = parseInt(split[0],10);
                if(riktigSvar === 1){
                    svarOppg = svar1;
                } else if(riktigSvar === 2){
                    svarOppg = svar2;
                } else if(riktigSvar === 3){
                    svarOppg = svar3;
                } else {
                    svarOppg = svar4; 
                }
                oppgTekst = split[1];
                document.getElementById("svar1").value = svar1;
                document.getElementById("svar2").value = svar2;
                document.getElementById("svar3").value = svar3;
                document.getElementById("svar4").value = svar4;
                document.getElementById("oppgTekst").innerHTML = oppgTekst;
            });
            function toInt(n){ return Math.round(Number(n)); };
            function tilHovedmeny(){
                reset();
                alertify.set({ labels: { ok: "Fortsett å spille", cancel: "Gå til hovedmeny" } });
                alertify.confirm("<b>Er du sikker på at du vil returnere til hovedmenyen?<br/>Du vil kunne fortsette spillet, men timescoren din vil bli satt til 0.</b><br/><br/>", function (e) {
                    if (!e) {
                        window.location.href = "hovedside";
                    }
                });
            }
            function pressed(nr){
                var svar = "";
                var poengsum = 0;
                if(nr === riktigSvar){
                    poengsum = toInt(45)+toInt(timescore);
                    svar = "Veldig bra! Du fikk riktig. <br/><br/>Poeng for riktig: 45/45.<br/>Poeng for tid: "+
                    timescore+"/5<br/><br/>Din poengsum ble: "+poengsum+"/50. <br/><br/>Gratulerer!!<br/><br/>";
                } else {
                    poengsum = toInt(timescore);
                    svar = "Synd! Du svarte feil. <br/><br/>Poeng for riktig: 0/45.<br/>Poeng for tid: "+timescore+
                    "/5<br/><br/>Din poengsum ble: "+poengsum+"/50. <br/><br/>Riktig svar skulle vært:<br/><br/>"+escapeHtml(svarOppg)+"<br/><br/>";
                }
                reset();
                alertify.alert(svar,function (e) {
                    if(e){
                        document.forms["nesteOppgave"].elements["randomNumber"].value = Math.random();
                        document.forms["nesteOppgave"].elements["score"].value = poengsum;
                        document.forms["nesteOppgave"].submit();
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
            function escapeHtml(text) {
                var map = {
                  '&': '&amp;',
                  '<': '&lt;',
                  '>': '&gt;',
                  '"': '&quot;',
                  "'": '&#039;'
                };

                return text.replace(/[&<>"']/g, function(m) { return map[m]; 
                });
            }
        </script>
    </head>
    <body>
        <form:form method="POST" modelAttribute="assignment" action ="nesteOppgave" id="nesteOppgave" name="nesteOppgave">
            <input type="hidden" name="score" value=''>
            <input type="hidden" name="randomNumber" value=''>
        </form:form>
            <div id="wrapper"> 
              <div class="header">
                <div class="mptitle">
                    <div id="smiley"><object type="image/svg+xml" data="resources/img/grin.svg"></object></div>
                    <c:if test="${loginform.getMessages()>0}">
                        <div id="circle">${loginform.getMessages()}</div>
                    </c:if>
                    <h1>Spillet</h1>
                </div>
                <div id="buttons">
                    <!-- Ikke formater disse divene! -->
                    <div><a href="chat">Chat</a>
                    </div><div><a href="javascript:tilHovedmeny()">Resultater</a>
                    </div><div><a href="javascript:tilHovedmeny()">Profil</a>
                    </div>
                    <!-- ---------------------------- -->
                </div>
            </div>
        <section id="content">
            <section class="block"> 
                <h1>Oppgave ${assignment.getCurrentTaskNr()}</h3><br/>
                <h3 id="oppgTekst"></h3><br/><br/>
                <input type="button" value="" id="svar1" class="knapp" onclick="pressed(1)">
                <input type="button" value="$" id="svar2" class="knapp" onclick="pressed(2)">
                <br/><br/>
                <input type="button" value="" id="svar3" class="knapp" onclick="pressed(3)">
                <input type="button" value="" id="svar4" class="knapp" onclick="pressed(4)">
            </section>
        </section>
            </div>
    </body>
</html>
