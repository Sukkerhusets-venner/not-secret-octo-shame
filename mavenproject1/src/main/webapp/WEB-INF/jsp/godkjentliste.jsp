<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url var="cssUrl" value="/resources/css/godkjentListe.css" />
<!--< c:url var="sokIkonUrl" value="/resources/img/search.svg" />
 <object type="image/svg+xml" data="${sokIkonUrl}" class="sokIkon"></object>
-->
<link href="${cssUrl}" rel="stylesheet" type="text/css"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h3>Godkjenning</h3>
        
            <form action="godkjentliste" method="post">
                <input type="text" placeholder="søk" name="soket" />
                <input type="submit">
            </form><c:if test = "${not empty godkjentListe}">
            <table class="finTabell">
                <tr>
                    <th>Bruker</th>
                    <th>Godkjent</th>
                </tr>
                <c:forEach var="UserScoreOverview" items="${godkjentListe}">
                    <c:if test="${not empty UserScoreOverview.user.username}">
                        <tr>
                            <td><c:out value="${UserScoreOverview.user.username}"/></td>
                            <td>
                                <c:if test="${UserScoreOverview.passed}">Ja</c:if>
                                <c:if test="${not UserScoreOverview.passed}">Nei</c:if>
                                </td>
                            </tr>
                    </c:if>
                </c:forEach>
            </table>
        </c:if><c:if test= "${empty godkjentListe}">
            <p>Fant ingen resultater på deg</p>
        </c:if>
    </body>
</html>
