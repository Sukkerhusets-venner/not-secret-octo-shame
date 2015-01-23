<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!--[if lt IE 7]>
            <script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE7.js"></script>
        <![endif]-->
        <!--[if lt IE 8]>
            <script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE8.js"></script>
        <![endif]-->
        <!--[if lt IE 9]>
            <script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js"></script>
        <![endif]-->
        <style>
            #circle {
                width: 1.5rem;
                height: 1.5rem;
                border-radius: 0.75rem;
                font-size: 0.75rem;
                color: black;
                line-height: 1.5rem;
                text-align: center;
                background: red;
                float: right;
                position: relative;
                bottom: 0.5rem; 
            }
        </style>
        <script>
            setTimeout(function(){
                window.location.reload(1);
            }, 5000);
        </script>
    </head>
    <body background="#d2dcdd">
        <c:if test="${loginform.getMessages()>0}">
            <div id="circle">${loginform.getMessages()}</div>
        </c:if>
    </body>
</html>
