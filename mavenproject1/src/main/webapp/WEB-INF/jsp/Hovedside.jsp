<%@page contentType="text/html" session="true" pageEncoding="UTF-8"%>
<%@include file="../../includes/head.jspf" %>
<link href="resources/css/niceMainpage.css" rel="stylesheet" type="text/css"/>
<title>Hovedside</title>
<script src="resources/js/menyKontroll.js"></script>
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
                    <p><a href="testprofile">Test profile</a></p>
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
