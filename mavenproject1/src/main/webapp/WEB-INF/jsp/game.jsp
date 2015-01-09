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
        </style>
        
        <script>
            $(document).ready(function() {
                
                var solutionHtml = "<!DOCTYPE html><html><body><h1>Hei</h1> Her skal du finne CSS feilen </body></html>";
                var solutionCss = "body {background-color: white; color: black;} h1 { color: blue; text-align: center; }";
                
                var sHtml = "<!DOCTYPE html><html><body><h1>Hei</h1> Her skal du finne CSS feilen </body></html>";
                var sCss = "body {background-color: black; color: white;} h1 { color: grey; text-align: left; }";
               
               	var startingHtml = style_html(sHtml);
		var startingCss = css_beautify(sCss);
                
                setRenderedResult($("#solutionFrame"), solutionHtml, solutionCss);
                setRenderedResult($("#resultFrame"), startingHtml, startingCss);

		editorHtml.getDoc().setValue(startingHtml);
		editorCss.getDoc().setValue(startingCss);                     
                
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
                    });            
                }
            }
        </script>
    </head>
        
    <body>
    <section id="content">
        <section class="block"> 
             <p>Oppgave 1</p>
            <p>Du skal finne feilen i koden så begge rutene blir like.</p>
            <input type="button" value="Sammenlign" id="compare">
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
	    						      	mode: "text/css"
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
