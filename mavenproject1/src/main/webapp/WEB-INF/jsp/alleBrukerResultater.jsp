<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url var="cssUrl" value="/resources/css/godkjentListe.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<link href="${cssUrl}" rel="stylesheet" type="text/css"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2> Resultater </h2>
        <div id="drWrap">
            <h3>Dine Resultater</h3>
            <c:if test="${not empty brukerScore}">
                <table class="finTabell" style="width:16rem;">
                    <tr>
                        <th>Dato</th>
                        <th>Resultat</th>
                        <th>Godkjent</th>
                    </tr>

                    <c:forEach var="ScoreProfile" items="${brukerScore}">
                        <tr>
                            <td>${ScoreProfile.date}</td>
                            <td>${ScoreProfile.points}</td>
                            <c:if test="${ScoreProfile.passed}"><td style="color:#70c469;">&check;</td></c:if>
                            <c:if test="${not ScoreProfile.passed}"><td style="color:#ff0000;">&cross;</td></c:if>
                            </tr>
                    </c:forEach>
                </table>
                <c:if test="${fulListe}">
                    <div id="tebakst"><a href="hovedside">Tilbake</a></div>
                    <style>
                        .finTabell{
                            margin: 0 auto !important;
                        }
                        #tebakst{
                            margin: 0 auto !important;
                        }
                    </style>
                </c:if>
            </c:if>
            <c:if test="${empty brukerScore}">
                <p>Fant ingen resultater på deg</p> 
            </c:if>
        </div>
    </body>
</html>
