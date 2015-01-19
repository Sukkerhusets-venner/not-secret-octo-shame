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
            
            $(document).ready(function() {
                timescore = 10;
                setInterval(function(){
                    if(timescore > 0){
                        timescore--;
                    }
                },20000);
                
                var svar1 = "${assignment.getCurrentTask().getTaskHtml()}";
                var svar2 = "${assignment.getCurrentTask().getAnswerHtml()}";
                var svar3 = "${assignment.getCurrentTask().getTaskCss()}";
                var svar4 = "${assignment.getCurrentTask().getAnswerCss()}";
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
                
                document.getElementById("oppgTekst").innerHTML = oppgTekst;
            });
            
            function pressed(nr){
                var svar = "";
                var poengsum = 0;
                if(nr === riktigSvar){
                    poengsum = 40+timescore;
                    svar = "Veldig bra! Du fikk riktig. <br/><br/>Poeng for riktig: 40/40.<br/>Poeng for tid: "+
                    timescore+"/10<br/><br/>Din poengsum ble: "+poengsum+"/50. <br/><br/>Gratulerer!!<br/><br/>";
                } else {
                    poengsum = timescore;
                    svar = "Synd! Du svarte feil. <br/><br/>Poeng for riktig: 0/40.<br/>Poeng for tid: "+timescore+
                    "/10<br/><br/>Din poengsum ble: "+poengsum+"/50. <br/><br/>Riktig svar skulle v�rt:<br/><br/><br/>"+escapeHtml(svarOppg);
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
                    <h1>Spillet</h1>
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
        <section id="content">
            <section class="block"> 
                <h1>Oppgave ${assignment.getCurrentTaskNr()}</h3><br/>
                <h3 id="oppgTekst"></h3><br/><br/>
                <input type="button" value="${assignment.getCurrentTask().getTaskHtml()}" id="svar1" class="knapp" onclick="pressed(1)">
                <input type="button" value="${assignment.getCurrentTask().getAnswerHtml()}" id="svar2" class="knapp" onclick="pressed(2)">
                <br/><br/>
                <input type="button" value="${assignment.getCurrentTask().getTaskCss()}" id="svar3" class="knapp" onclick="pressed(3)">
                <input type="button" value="${assignment.getCurrentTask().getAnswerCss()}" id="svar4" class="knapp" onclick="pressed(4)">
            </section>
        </section>
            </div>
    </body>
</html>
