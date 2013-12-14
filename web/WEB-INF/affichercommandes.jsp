<%-- 
    Document   : affichercommandes
    Created on : 14 déc. 2013, 00:51:35
    Author     : Valentin
--%>

<%@page import="Boutique.Classes.Commande"%>
<%@page import="java.util.ArrayList"%>
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
            // requete : http://localhost:8080/JavaProjet/affichercommande
            ArrayList<Commande> commandes = (ArrayList<Commande>) request.getAttribute("commandes");
            
            if (commandes != null)
            {
                for (Commande c : commandes)
                {
                    out.println("<p>" + c.getId() + "</p>");
                }
            }
        %>
    </body>
</html>
