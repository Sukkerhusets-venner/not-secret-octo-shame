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
                    <div><a href="#">Spillet</a>
                    </div><div><a href="#">HiScore</a>
                    </div><div><a href="#">Profil</a>
                    </div>
                    <!-- ---------------------------- -->
                </div>
            </div>
            <div id="stuff">
                <p><c:out value = "${User.username}"/> Model</p>
                <p><%=session.getAttribute("Username")%> Session</p>
            </div>
            <div id="selectMenu">
                <span>&#9312;</span>
                <span>&#9313;</span>
                <span>&#9314;</span>
            </div>
        </div>
        
    </body>
</html>
