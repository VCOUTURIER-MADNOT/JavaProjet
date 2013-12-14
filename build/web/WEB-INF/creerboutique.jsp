<%-- 
    Document   : creerboutique
    Created on : 14 déc. 2013, 02:10:46
    Author     : Valentin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <%
            out.print(request.getAttribute("msg"));
        %>
    </body>
</html>
