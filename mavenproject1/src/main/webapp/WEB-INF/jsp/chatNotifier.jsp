<%@include file="../../includes/head.jspf" %>
        <style>
            #circle {
                width: 20px;
                height: 20px;
                border-radius: 10px;
                font-size: 10px;
                color: black;
                line-height: 20px;
                text-align: center;
                background: red;
                float: right;
            }
        </style>
        <script>
           setTimeout(function(){
            window.location.reload(1);
         }, 5000);
        </script>
    </head>
    <body>
        <c:if test="${loginform.getMessages()>0}">
            <div id="circle">${loginform.getMessages()}</div>
        </c:if>
    </body>
</html>
