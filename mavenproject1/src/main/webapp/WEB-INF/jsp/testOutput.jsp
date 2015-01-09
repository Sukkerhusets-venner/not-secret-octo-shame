<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.springframework.context.support.FileSystemXmlApplicationContext"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
           FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext("resources/css/style.css");; 
           Object f = ctx.getResource("Other Sources/src/main/resources/css/style.css");
           if(f != null){
               out.println(f.toString());
           }else{
               out.println("finnes ikke");
           }
        %>
    </body>
</html>
