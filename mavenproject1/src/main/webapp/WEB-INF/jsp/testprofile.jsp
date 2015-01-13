<%-- 
    Document   : testprofile
    Created on : 13.jan.2015, 10:47:56
    Author     : Trond
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="resources/js/profile.js"></script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Din Profil:</h1>
        <div id="usernameChange">
        <div id="popupUsername">
        <form:form action="byttBrukernavn" id="form" method="post" name="form" modelAttribute = "byttbrukernavnform">
        <img id="close" src="resources/img/button-cross.png" onclick ="passord_hide()">
        <h2> Bytt Brukernavn </h2>
        <hr>
        <form:input id="oldName" name="nameOld" placeholder="Ditt gamle navn" path = "oldUser.username">
        <form:input id="newName" name="nameNew" placeholder="Ditt nye navn" path =" newUser.username " >
        <input type="submit" name="submit" value="byttBrukernavn" />
        </form:form>
        </div>
        </div>
        
        <button id="popup" onclick="passord_show()">Nytt brukernavn</button>

    </body>
</html>
