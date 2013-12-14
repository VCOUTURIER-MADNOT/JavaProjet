<%-- 
    Document   : afficherproduits
    Created on : 13 déc. 2013, 22:17:19
    Author     : Valentin
--%>

<%@page import="Boutique.Classes.Produit"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Produits!</h1>
        <%
            ArrayList<Produit> produits = (ArrayList<Produit>) request.getAttribute("produits");
            
            for(Produit p : produits)
            {
                out.println("<p>"+ p.getNom() + "</p>");
            }
        %>
    </body>
</html>
