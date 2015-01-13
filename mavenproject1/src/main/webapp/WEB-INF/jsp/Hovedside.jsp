<%@page contentType="text/html" session="true" pageEncoding="UTF-8"%>
<%@include file="../../includes/head.jspf" %>
<link href="resources/css/niceMainpage.css" rel="stylesheet" type="text/css"/>
<title>Hovedside</title>
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
                    
                </div>
                <div id="hiscore">
                    
                </div>
                <div id="profil">
                    <p>Brukernavn: <%=session.getAttribute("Username")%></p>
                    <p>Email: <c:out value = "${loginform.user.email}"/></p>
                    <p><a href="snake">Snakes!</a></p>
                    <p><a href="taskTester">Test</a></p>
                    <p><a href="testprofile">Test profile</a></p>
                </div>
            </div>
            <div id="selectMenu">
                <span>&#9312;</span>
                <span>&#9313;</span>
                <span>&#9314;</span>
            </div>
        </div>
    </body>
</html>
