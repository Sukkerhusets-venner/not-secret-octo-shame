<%@include file="../../includes/head.jspf" %>
        <title>Gehm!</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <c:url var="ngpUrl" value="/resources/css/niceGamepage.css" />
        <link href="${ngpUrl}" rel="stylesheet" type="text/css"/>
       
       
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
                
                var chat = $("#chatRamme");
                var showChatB = $("#chatWrap");

                showChatB.click(function(){
                   var s = chat.css("display");
                   chat.css ("visibility", "visible");
                   if(s == "none"){
                        showChatB.animate({width:"32.17rem"}, 300);
                        chat.fadeIn("slow");
                   }else{
                        showChatB.animate({width:"8rem"}, 300);
                        chat.fadeOut("slow");
                   }
                });
            });
            
            
            function setRenderedResult(frame, html, css) {
                var html2 = removeScript(html);
                var css2 = removeScript(css);
                if((html2 === "script") || (css2 === "script")){
                    css2 = "h4{color: white;} h6{ color: white;} body{ background-image: url('http://weknowmemes.com/generator/uploads/generated/g1334693036781255327.jpg'); background-size: 250px 200px;}"
                    html2 = "<h4>ONE DOES NOT SIMPLY</h4><br/><p><br/><br/><br/><h6>CROSS SITE SCRIPT INTO MORDOR</h6>";
                }
                frame.contents().find("html").html(html2);
                var $head = frame.contents().find("head");                
                $head.append("<style>" + css2 + "</style>") 
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
                                document.forms["nesteOppgave"].elements["assignment.randomNumber"].value = Math.random();
                                document.forms["nesteOppgave"].elements["assignment.score"].value = poengsum;
                                document.forms["nesteOppgave"].submit();
                            }
                        });
                    });            
                }
            }
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
            function setUp(){
                timescore = "${loginform.getAssignment().getTimescore()}";
                setInterval(function(){
                    if(timescore > 0){
                        timescore--;
                    }
                },30000);
                oppgNr = "${loginform.getAssignment().getCurrentTaskNr()}";
                oppgTekst = "${loginform.getAssignment().getCurrentTask().getText()}"
                var solutionHtml = fixFromDb("${loginform.getAssignment().getCurrentTask().getAnswerHtml()}");
                var solutionCss = fixFromDb("${loginform.getAssignment().getCurrentTask().getAnswerCss()}");
                var type = "${loginform.getAssignment().getCurrentTask().getType()}";
                var sHtml = "";
                var sCss = "";
                if(type === "Css"){
                    sHtml = solutionHtml;
                    sCss = fixFromDb("${loginform.getAssignment().getCurrentTask().getTaskCss()}");
                    editorHtml.setOption("readOnly", true);
                    editorCss.setOption("readOnly", false);
                } else if(type === "Html"){
                    sHtml = fixFromDb("${loginform.getAssignment().getCurrentTask().getTaskHtml()}");
                    sCss = solutionCss;
                    editorHtml.setOption("readOnly", false);
                    editorCss.setOption("readOnly", true);
                } else if(type === "CssHtml"){
                    sHtml = fixFromDb("${loginform.getAssignment().getCurrentTask().getTaskHtml()}");
                    sCss = fixFromDb("${loginform.getAssignment().getCurrentTask().getTaskCss()}");
                    editorHtml.setOption("readOnly", false);
                    editorCss.setOption("readOnly", false);
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
            function fixFromDb(text){
                var s = text.split("¤")
                var retur = s[0];
                for(var i=1; i<s.length; i++){
                    retur += "'"+s[i]
                }
                return retur;
            }
            function removeScript(text){
                var editText1 = text.toLowerCase();
                var editText2 = editText1.split('script').join('');
                var scr = "<" + "%";
                editText2 = editText2.split(scr).join('');
                if(editText1 === editText2){
                    return text;
                } else {
                    return "script";
                }
            }
        </script>
    </head>
    
    <body>
        
        <form:form method="POST" modelAttribute="loginform" action ="nesteOppgave" id="nesteOppgave" name="nesteOppgave">
            <input type="hidden" name="assignment.score" value=''>
            <input type="hidden" name="assignment.randomNumber" value=''>
        </form:form>
         <div id="wrapper"> 
              <div class="header">
                <div class="mptitle">
                    <div id="smiley"><object type="image/svg+xml" data="resources/img/grin.svg"></object></div>
                    <c:if test="${loginform.getMessages()>0}">
                        <div id="circle">${loginform.getMessages()}</div>
                    </c:if>
                    <h1>C. H. I. L.</h1>
                </div>
                <div id="buttons">
                    <!-- Ikke formater disse divene! -->
                    <div><a href="chat">Spillet</a>
                    </div><div><a href="javascript:tilHovedmeny()">Resultater</a>
                    </div><div><a href="javascript:tilHovedmeny()">Profil</a>
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
