<%@include file="../../includes/head.jspf" %>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="resources/js/resemble.js"></script>
	<script src="resources/js/html2canvas.js"></script>
	<link rel="stylesheet" href="resources/css/codemirror.css">
	<script src="resources/js/codemirror.js"></script>
	<script src="resources/js/xml.js"></script>
	<script src="resources/js/css.js"></script>
	<script src="resources/js/beautify-css.js"></script>
	<script src="resources/js/beautify-html.js"></script>
        <script src="resources/js/alertify.min.js"></script>
        <link rel="stylesheet" href="resources/css/alertify.core.css" />
	<link rel="stylesheet" href="resources/css/alertify.default.css" id="toggleCSS" />
        <c:url var="nmpUrl" value="/resources/css/niceGamepage.css" />
        <link href="${nmpUrl}" rel="stylesheet" type="text/css"/>
       
       
        <script>
        //"Read only" variabler.
        var oppgNr = 0;
        var oppgTekst = "";
        var timescore = 0;
        $(document).ready(function() {
               
                setUp();                 
                
                $("#compare").click(function() { 
                    
                var resultUrl = null;
                    var solutionUrl = null;
                    html2canvas($("#resultFrame").contents().find("body"), {
                        onrendered: function (canvas) {
                            resultUrl = canvas.toDataURL('image/png'); 
                            resembleIfBothLoaded(resultUrl, solutionUrl);
                        }
                    });
                    html2canvas($("#solutionFrame").contents().find("body"), {
                        onrendered: function (canvas) {
                            solutionUrl = canvas.toDataURL('image/png'); 
                            resembleIfBothLoaded(resultUrl, solutionUrl);
                        }
                    });
                });            
            });
            
            function setRenderedResult(frame, html, css) {
                frame.contents().find("html").html(html);
                var $head = frame.contents().find("head");                
                $head.append("<style>" + css + "</style>") 
            }

            function resembleIfBothLoaded(resultUrl, solutionUrl) { 
                if(resultUrl && solutionUrl) {
                    resemble(resultUrl).compareTo(solutionUrl).onComplete(function(data){
                        var poengsum = 0;
                        var skillscore = 0;
                        if(data.misMatchPercentage <= 4){
                            skillscore = toInt((4-data.misMatchPercentage)*22.5);
                        }
                        poengsum = toInt(timescore) + toInt(skillscore);
                        reset();
			alertify.alert("Veldig bra, du har nå kommet til neste oppgave.<br/><br/> Din skillscore ble: "+skillscore+
                        "/90.<br/>Din tidscore ble "+timescore+"/10<br/><br/>Din poengsum ble: "+poengsum+"/100. <br/><br/>Gratulerer!!<br/><br/>"
                        ,function (e) {
                            if(e){
                                document.forms["nesteOppgave"].elements["randomNumber"].value = Math.random();
                                document.forms["nesteOppgave"].elements["score"].value = poengsum;
                                document.forms["nesteOppgave"].submit();
                            }
                        });
                    });            
                }
            }
            function toInt(n){ return Math.round(Number(n)); };
            function setUp(){
                timescore = "${assignment.getTimescore()}";
                setInterval(function(){
                    if(timescore > 0){
                        timescore--;
                    }
                },30000);
                oppgNr = "${assignment.getCurrentTaskNr()}";
                var solutionHtml = "${assignment.getCurrentTask().getAnswerHtml()}";
                var solutionCss = "${assignment.getCurrentTask().getAnswerCss()}";
                var type = "${assignment.getCurrentTask().getType()}";
                var sHtml = "";
                var sCss = "";
                if(type === "Css"){
                    sHtml = solutionHtml;
                    sCss = "${assignment.getCurrentTask().getTaskCss()}";
                    editorHtml.setOption("readOnly", true);
                    editorCss.setOption("readOnly", false);
                    oppgTekst = "Her må du skrive Css-kode slik at bildene under blir like";
                } else if(type === "Html"){
                    sHtml = "${assignment.getCurrentTask().getTaskHtml()}";
                    sCss = solutionCss;
                    editorHtml.setOption("readOnly", false);
                    editorCss.setOption("readOnly", true);
                    oppgTekst = "Her må du skrive Html-kode slik at bildene under blir like";
                } else if(type === "CssHtml"){
                    sHtml = "${assignment.getCurrentTask().getTaskHtml()}";
                    sCss = "${assignment.getCurrentTask().getTaskCss()}";
                    editorHtml.setOption("readOnly", false);
                    editorCss.setOption("readOnly", false);
                    oppgTekst = "Her må du skrive Html-kode og Css-kode slik at begge bildene blir like";
                }
                
               	var startingHtml = style_html(sHtml);
		var startingCss = css_beautify(sCss);
                
                setRenderedResult($("#solutionFrame"), solutionHtml, solutionCss);
                setRenderedResult($("#resultFrame"), startingHtml, startingCss);
                editorHtml.setSize(380,300);
                editorCss.setSize(380,300);
                editorHtml.getDoc().setValue(startingHtml );
                editorCss.getDoc().setValue(startingCss);
                
                document.getElementById("oppgnummer").innerHTML = "Oppgave "+oppgNr;
                document.getElementById("oppgtekst").innerHTML = oppgTekst;
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
                    <div><a>Chat</a>
                    </div><div><a href="hovedside">Resultater</a>
                    </div><div><a href="hovedside">Profil</a>
                    </div>
                    <!-- ---------------------------- -->
                </div>
            </div>
             <section id="content">
        <section class="block"> 
            
    
            <h3 id="oppgnummer"></h3>
            <p id="oppgtekst"></p>
            <p>Løsning | Din kode</p>
            <div id="solutionDiv">
                <iframe class="renderedFrame" id="solutionFrame" src="about:blank"></iframe>
                <iframe class="renderedFrame" id="resultFrame" src="about:blank"></iframe>
            </div>
            
        </section>

        <section class="block">        
            <p>CSS</p>
            <form><textarea id="cssView" name="css"></textarea></form>
	    	<script>
	    		var editorCss = CodeMirror.fromTextArea(document.getElementById("cssView"), {
	    						        extraKeys: {"Ctrl-Space": "autocomplete"},
	    						        lineNumbers: true,
	    						      	mode: "text/css",
                                                                readOnly: false
	    		});
                        editorCss.on('change', function(e){
                            setRenderedResult($("#resultFrame"), editorHtml.getDoc().getValue(), editorCss.getDoc().getValue());
                        });
    		</script>
        </section>

        <section class="block">        
            <p>HTML</p>
            <form><textarea id="htmlView" name="html"></textarea></form>
	    	<script>
	                var editorHtml = CodeMirror.fromTextArea(document.getElementById("htmlView"), {
	    								        mode: "text/html",
	    								        lineNumbers: true,
                                                                                readOnly: false
	    		});
                        editorHtml.on('change', function(e){
                            setRenderedResult($("#resultFrame"), editorHtml.getDoc().getValue(), editorCss.getDoc().getValue());
                        });
		</script>
        </section>
    </section>
             <input type="button" value="SVAR" id="compare">
             <div id="space">  
             
             
             
             </div>
                  
  </div>  
</html>

    </body>
</html>
