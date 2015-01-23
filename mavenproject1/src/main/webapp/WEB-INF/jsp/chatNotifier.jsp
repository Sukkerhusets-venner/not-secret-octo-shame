<%@include file="../../includes/head.jspf" %>
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
