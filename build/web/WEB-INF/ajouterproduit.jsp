<%-- 
    Document   : ajouterproduit
    Created on : 13 déc. 2013, 23:10:26
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
            
            // requete = http://localhost:8080/JavaProjet/ajouterproduit?nom=bebe&description=bebe&prix=50&nomBoutique=Valcou%27s+Shop
            out.print(request.getAttribute("msg"));
        %>
    </body>
</html>
