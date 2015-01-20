<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url var="cssUrl" value="/resources/css/godkjentListe.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
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
        <c:if test = "${not empty godkjentListe}">
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
                                <c:if test="${UserScoreOverview.passed}"><p style="color:#70c469;">&check;</p></c:if>
                                <c:if test="${not UserScoreOverview.passed}"><p style="color:red;"></p>&Cross;</c:if>
                                </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </c:if><c:if test= "${empty godkjentListe}">
            <p>Fant ingen resultater</p>
        </c:if>
    </body>
</html>
