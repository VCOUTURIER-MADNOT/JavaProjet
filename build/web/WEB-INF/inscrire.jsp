<%-- 
    Document   : inscrire
    Created on : 14 déc. 2013, 01:44:48
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
            out.println(request.getAttribute("msg"));
        %>
    </body>
</html>
