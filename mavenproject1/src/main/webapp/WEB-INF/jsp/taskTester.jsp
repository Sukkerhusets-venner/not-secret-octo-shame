
<%@page import="prosjekt.Domene.Task"%>
<%@page import="prosjekt.Ui.Assignment"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tasktester</title>
    </head>
    <body>
        <%
            Assignment a = new Assignment(0);
            a.addTask("dD", "Html");
            Task t = a.getCurrentTask();
            out.println(t.getTasknr());
            out.println(t.getStrTask());
            out.println(t.getActualTask());
        %>
    </body>
</html>
