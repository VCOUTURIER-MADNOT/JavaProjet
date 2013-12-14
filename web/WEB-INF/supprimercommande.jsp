<%-- 
    Document   : supprimercommande
    Created on : 14 déc. 2013, 00:42:03
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
            // requete : http://localhost:8080/JavaProjet/supprimercommande?idCommande=6ac96109-797f-43dd-9465-0f6af079749e
            out.print(request.getAttribute("msg"));
        %>
    </body>
</html>
