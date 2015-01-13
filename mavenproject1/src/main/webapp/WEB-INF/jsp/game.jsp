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

        <style>
            .block {
                float: left;
                margin: 5px;
                padding: 5px;
                background-color: #dddddd;
            }
            
            .renderedFrame, .codeBox {
                width: 250px; height: 200px
            }
            #compare {
                float: right;
                padding: 11px 25px;

                font-family: 'Bree Serif', serif;
                font-weight: 300;
                font-size: 18px;
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
        //"Read only" variabler.
        var oppgNr = 0;
        var oppgTekst = "";
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
                        alert("Likhet: " + (100 - data.misMatchPercentage) + "%");
                        document.forms["nesteOppgave"].submit();
                    });            
                }
            }
            function setUp(){
                oppgNr = "${assignment.getCurrentTaskNr()}";
                oppgNr++;
                oppgTekst = "${assignment.getCurrentTask().getDescription()}";
                var solutionHtml = "${assignment.getCurrentTask().getTaskHtml()}";
                var solutionCss = "${assignment.getCurrentTask().getTaskCss()}";
                var type = "${assignment.getCurrentTask().getType()}";
                var sHtml = "";
                var sCss = "";
                
                if(type === "Css"){
                    sHtml = solutionHtml;
                    sCss = "";
                    editorHtml.setOption("readOnly", true);
                    editorCss.setOption("readOnly", false);
                } else if(type === "Html"){
                    sHtml = "";
                    sCss = solutionCss;
                    editorHtml.setOption("readOnly", false);
                    editorCss.setOption("readOnly", true);
                }
                
               	var startingHtml = style_html(sHtml);
		var startingCss = css_beautify(sCss);
                
                setRenderedResult($("#solutionFrame"), solutionHtml, solutionCss);
                setRenderedResult($("#resultFrame"), startingHtml, startingCss);
                editorHtml.getDoc().setValue(startingHtml );
                editorCss.getDoc().setValue(startingCss);
                
                document.getElementById("oppgnummer").innerHTML = "Oppgave "+oppgNr;
            }
        </script>
    </head>
        
    <body>
    <form:form method="POST" modelAttribute="assignment" action ="nesteOppgave" id="nesteOppgave" name="nesteOppgave">
    </form:form>
    <section id="content">
        <section class="block"> 
            <h3 id="oppgnummer">Oppgave <script>document.write(oppgNr)</script></h3>
            <p>${assignment.getCurrentTask().getDescription()}</p>
            <p>L?sning | Din kode</p>
            <div id="solutionDiv">
                <iframe class="renderedFrame" id="solutionFrame" src="about:blank"></iframe>
                <iframe class="renderedFrame" id="resultFrame" src="about:blank"></iframe>
            </div>
            <input type="button" value="SVAR" id="compare">
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
</html>

    </body>
</html>
