<%@page import="java.util.Enumeration"%>
<%@page contentType="text/html" session="true" pageEncoding="UTF-8"%>
<% /**
     * Fallback
     */
    boolean tilgang = false;

    try {
        //session.getAttribute("Username")
        // hvis validert! -- metode???
        if (session.getAttribute("Username") != null || !session.getAttribute("Username").equals("null")) {
            tilgang = true;
        }
        Enumeration<String> s = session.getAttributeNames();
        while (s.hasMoreElements()) {
            String st = s.nextElement();
            if (st.equals("Username")) {
                tilgang = true;
            }
        }
    } catch (Exception e) {
        // ???
        response.sendRedirect("error");
    }
    if (tilgang) {
%>
<%@include file="../../includes/head.jspf" %>
<link href="resources/css/niceMainpage.css" rel="stylesheet" type="text/css"/>
<link href="resources/css/loader.css" rel="stylesheet" type="text/css"/>
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
            <div id="loadBkgr">
                <div class="container">
                    <div class="part"></div>
                    <div class="part"></div>
                    <div class="part"></div>
                    <div class="part"></div>
                    <div class="part"></div>
                </div>
                <!--<figure></figure>-->
            </div>
            <div id="selectMenu">
                <span>&#9312;</span>
                <span>&#9313;</span>
                <span>&#9314;</span>
            </div>
        </div>
    </body>
    <% } else { %>
    <body>
        <p> Hmm.. fant ikke brukeren din? </p>
    </body>
    <% }%>
</html>
