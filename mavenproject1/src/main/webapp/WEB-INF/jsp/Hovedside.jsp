<%@page contentType="text/html" session="true" pageEncoding="UTF-8"%>
<%@include file="../../includes/head.jspf" %>
<link href="resources/css/niceMainpage.css" rel="stylesheet" type="text/css"/>
<link href="resources/css/loader.css" rel="stylesheet" type="text/css" />
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
                    <div><a href="mk1">Spillet</a>
                    </div><div><a href="mk2">HiScore</a>
                    </div><div><a href=mk3>Profil</a>
                    </div>
                    <!-- ---------------------------- -->
                </div>
            </div>
            <!--  Eksempel: admin::
            <sec:authorize access="hasRole('admin')"></sec:authorize> -->
            <div id="stuff">
                <c:if test="${meny == 1 or empty meny}">
                    <div id="spill">
                    <h3>Spill</h3>
                    <a href="game">Press this link</a>
                    </div>
                </c:if>
                <c:if test="${meny == 2}">
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
                        <p> Fant ingen Hiscores</p>
                    </c:if>
                </div>
                </c:if>
                <c:if test="${meny == 3}">
                <div id="profil">
                    <h3> Profil </h3>
                    <p>Brukernavn: <%=session.getAttribute("Username")%></p>
                    <p>Email: <c:out value = "${loginform.user.email}"/></p>
                    <p><a href="snake">Snakes!</a></p>
                    <p><a href="taskTester">Test</a></p>

                </div>
                </c:if>
            </div>
            <div class="container">
                <div class="part"></div>
                <div class="part"></div>
                <div class="part"></div>
                <div class="part"></div>
                <div class="part"></div>
            </div>
        </div>
    </body>
</html>
