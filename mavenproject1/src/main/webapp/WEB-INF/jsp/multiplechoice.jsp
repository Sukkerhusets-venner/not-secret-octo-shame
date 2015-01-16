<%@include file="../../includes/head.jspf" %>
        <title>Game</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="resources/js/alertify.min.js"></script>
        <link rel="stylesheet" href="resources/css/alertify.core.css" />
	<link rel="stylesheet" href="resources/css/alertify.default.css" id="toggleCSS" />
        
        <style>
            .block{
                float: left;
                margin: 5px;
                padding: 5px;
                background-color: #f3f3f3;
            }
            #svar1{
                float: left;
            }
            #svar2{
                float: right;
            }
            #svar3{
                float: left;
            }
            #svar4{
                float: right;
            }
            .knapp{
                padding: 11px 25px;

                font-family: 'Bree Serif', serif;
                font-weight: 300;
                font-size: 12px;
                width: 30em;  
                height: 8em;
                color: #fff;
                text-shadow: 0px 1px 0 rgba(0,0,0,0.25);

                background: #56c2e1;
                border: 1px solid #46b3d3;
                border-radius: 5px;
                cursor: pointer;

                box-shadow: inset 0 0 2px rgba(256,256,256,0.75);
                -moz-box-shadow: inset 0 0 2px rgba(256,256,256,0.75);
                -webkit-box-shadow: inset 0 0 2px rgba(256,256,256,0.75);
            }
        </style>
        
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
                    "/10<br/><br/>Din poengsum ble: "+poengsum+"/50. <br/><br/>Riktig svar skulle vært:<br/><br/>"+svarOppg;
                }
                reset();
                alertify.alert(svar,function (e) {
                    if(e){
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
        </script>
    </head>
    <body>
        <form:form method="POST" modelAttribute="assignment" action ="nesteOppgave" id="nesteOppgave" name="nesteOppgave">
            <input type="hidden" name="score" value=''>
        </form:form>
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
    </body>
</html>
