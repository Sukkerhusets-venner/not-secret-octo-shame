<%@page contentType="text/html" session="true" pageEncoding="UTF-8"%>
<%@include file="../../includes/head.jspf" %>
<c:url var="nmpUrl" value="/resources/css/niceMainpage.css" />
<c:url var="loaderUrl" value="/resources/css/loader.css" />
<c:url var="mkjsUrl" value="/resources/js/menyKontroll.js" />
<link href="${nmpUrl}" rel="stylesheet" type="text/css"/>
<link href="${loaderUrl}" rel="stylesheet" type="text/css" />
<title>Hovedside</title>
<script src="${mkjsUrl}"></script>
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
                    <div><a>Spillet</a>
                    </div><div><a>Resultater</a>
                    </div><div><a>Profil</a>
                    </div>
                    <!-- ---------------------------- -->
                </div>
            </div>
            <!--  Eksempel: admin::
            <sec:authorize access="hasRole('admin')"></sec:authorize> -->
            <div id="stuff">
                <div id="spill">
                    <h3>Spill</h3>
                    <a href="game">Press this link</a>
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
                        <p> Fant ingen Hiscores</p>
                    </c:if>
                </div>
                <div id="profil">
                        <p>Brukernavn: ${loginform.user.username}</p>
                        <p>Email: <c:out value = "${loginform.user.email}"/></p>
                </div>
            </div>
            <!-- Loader; styres av script -->
            <div class="container">
                <div class="part"></div>
                <div class="part"></div>
                <div class="part"></div>
                <div class="part"></div>
                <div class="part"></div>
            </div>
            <noscript><!-- Hvis javascript er slått av -->
            <style>
                .container{visibility:hidden;}
                #stuff{visibility:hidden;}
            </style>
            <h2 style="padding: 1rem 1rem 1rem 1rem">Du må slå på javascript for å spille spillet.</h2>
            </noscript> 
            <!-- ------------------------ -->
        </div>
    </body>
</html>
