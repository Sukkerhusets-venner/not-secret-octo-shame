<%@page contentType="text/html" session="true" pageEncoding="UTF-8"%>
<%@include file="../../includes/head.jspf" %>
<link href="resources/css/niceMainpage.css" rel="stylesheet" type="text/css"/>
<title>Hovedside</title>
<script type="text/javascript">

    var REFRESH_TIMER = 5000;
    $(document).ready(function () {
        var a = $("#spill");
        var b = $("#hiscore");
        var c = $("#profil");
        a.css("visibility", "visible");

        var divVist = 0;
        var timer = setInterval(function () {
            divVist++;
            if (divVist >= 3) {
                divVist = 0;
            }
            switch (divVist) {
                case 0:
                    c.fadeTo(500, 0, function () {
                        c.css("visibility", "hidden");
                        a.css("visibility", "visible");
                        a.fadeTo(500, 1);
                    });
                    break;
                case 1:
                    a.fadeTo(500, 0, function () {
                        a.css("visibility", "hidden");
                        b.css("visibility", "visible");
                        b.fadeTo(500, 1);
                    });
                    break;
                case 2:
                    b.fadeTo(500, 0, function () {
                        b.css("visibility", "hidden");
                        c.css("visibility", "visible");
                        c.fadeTo(500, 1);
                    });
                    break;
            }
        }, REFRESH_TIMER);
        $("#buttons div").hover(function (event) {
            var elem = $(event.target);
            clearInterval(timer);
            if (elem.html().equals("Spillet") && a.css("visiblity").equals("hidden")) {
                
            }
        });
        /*function() showSpill(){
            if(c.css("visiblity").equals("visible")){
                c.fadeTo(500, 0, function () {
                        c.css("visibility", "hidden");
                        a.css("visibility", "visible");
                        a.fadeTo(500, 1);
                });
            }else if(b.css("visiblity").equals("visible")){
                b.fadeTo(500, 0, function () {
                        b.css("visibility", "hidden");
                        a.css("visibility", "visible");
                        a.fadeTo(500, 1);
                });
            }
        }
        function() showHiScore(){
            if(a.css("visiblity").equals("visible")){
                a.fadeTo(500, 0, function () {
                        a.css("visibility", "hidden");
                        b.css("visibility", "visible");
                        b.fadeTo(500, 1);
                });
            }else if(c.css("visiblity").equals("visible")){
                c.fadeTo(500, 0, function () {
                        c.css("visibility", "hidden");
                        b.css("visibility", "visible");
                        b.fadeTo(500, 1);
                });
            }
        }
        function() showProfil(){
            if(a.css("visiblity").equals("visible")){
                a.fadeTo(500, 0, function () {
                        a.css("visibility", "hidden");
                        c.css("visibility", "visible");
                        c.fadeTo(500, 1);
                });
            }else if(b.css("visiblity").equals("visible")){
                b.fadeTo(500, 0, function () {
                        b.css("visibility", "hidden");
                        c.css("visibility", "visible");
                        c.fadeTo(500, 1);
                });
            }
        }*/

    });
</script>
</head>
<html>
    <body>
        <div class="headspace"></div>
        <div id="wrapper">
            <div class="header">
                <div class="mptitle">
                    <div id="smiley"><object type="image/svg+xml" data="resources/img/grin.svg"></object></div>
                    <h1>Velkommen</h1>
                </div>
                <div id="buttons">
                    <!-- Ikke formater disse divene! -->
                    <div><a href="game">Spillet</a>
                    </div><div><a href="#">HiScore</a>
                    </div><div><a href="#">Profil</a>
                    </div>
                    <!-- ---------------------------- -->
                </div>
            </div>
            <!--  Eksempel: admin::
            <sec:authorize access="hasRole('admin')"></sec:authorize> -->
            <div id="stuff">
                <div id="spill">
                    <h3>Spill</h3>
                </div>
                <div id="hiscore">
                    <h3> HiScore </h3>
                    <c:if test = "${not empty loginform.hiScore}">
                        <table>
                            <tr>
                                <th>Bruker</th>
                                <th>Score</th>
                            </tr>
                            <c:forEach var="UserScore" items="${loginform.hiScore}">

                                <tr><td><c:out value="${UserScore.username}"/></td>
                                    <td><c:out value="${UserScore.highScore}"/></td></tr>

                            </c:forEach></table>
                        </c:if><c:if test="${empty loginform.hiScore}">
                        <p> Fant ingen Hiscores (hvis du har brukt <b>logincheat</b> vil du ikke finne noen!)</p>
                    </c:if>
                </div>
                <div id="profil">
                    <h3> Profil </h3>
                    <p>Brukernavn: <%=session.getAttribute("Username")%></p>
                    <p>Email: <c:out value = "${loginform.user.email}"/></p>
                    <p><a href="snake">Snakes!</a></p>
                    <p><a href="taskTester">Test</a></p>
                </div>
            </div>
            <div id="selectMenu">
                <span id="s1">&#9312;</span>
                <span id="s2">&#9313;</span>
                <span id="s3">&#9314;</span>
            </div>
        </div>
    </body>
</html>
