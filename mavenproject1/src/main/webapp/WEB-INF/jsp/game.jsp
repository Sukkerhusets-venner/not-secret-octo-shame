<!DOCTYPE html>
<html>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <%@include file="../../includes/html2canvas.jspf" %>
        <%@include file="../../includes/resemble.jspf" %>
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
                var solutionCss = "body {background-color: white;} h1 { color: blue; text-align: center; }";
                
                var startingHtml = "<!DOCTYPE html><html><body><h1>Hei</h1> Her skal du finne CSS feilen </body></html>";
                var startingCss = "body {background-color: black;} h1 { color: grey; text-align: left; }";
               
                
                setRenderedResult($("#solutionFrame"), solutionHtml, solutionCss);
                setRenderedResult($("#resultFrame"), startingHtml, startingCss);

                $("#htmlView").val(startingHtml); 
                $("#cssView").val(startingCss); 
                               
                $("#viewResult").click(function() {
                    setRenderedResult($("#resultFrame"), $("#htmlView").val(), $("#cssView").val());
                });
                
               
                
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
            <input type="button" value="Se resultat" id="viewResult">
            <input type="button" value="Sammenlign" id="compare">
            <p>Løsning | Din kode</p>
            <div id="solutionDiv">
                <iframe class="renderedFrame" id="solutionFrame" src="about:blank"></iframe>
                <iframe class="renderedFrame" id="resultFrame" src="about:blank"></iframe>
            </div>
        </section>

        <section class="block">        
            <p>CSS</p>
            <textarea class="codeBox" id="cssView"></textarea>
        </section>

        <section class="block">        
            <p>HTML</p>
            <textarea class="codeBox" id="htmlView" disabled=""></textarea>
        </section>
    </section>
</html>

    </body>
</html>
