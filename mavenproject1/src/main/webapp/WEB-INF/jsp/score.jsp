<%@include file="../../includes/head.jspf" %>
        <title>Score</title>
        <style>
            .tilbake{
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
        <script type="text/javascript">
            function openPage(pageURL)
            {
                window.location.href = pageURL;
            }
        </script>
    </head>
    <body>
        <h3>Oppsummering</h3>
        <c:forEach var="i" begin="1" end="${assignment.getTaskNr()}" >
            <p>Oppgave ${i}: ${assignment.getDelscore(i-1)}</p>
        </c:forEach>
            <input type="button" value="Tilbake til hovedsiden" id="tilbake" onclick="openPage(meny)">
    </body>
</html>
