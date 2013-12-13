<%-- 
    Document   : afficherBoutiques
    Created on : 13 déc. 2013, 16:21:38
    Author     : valentin
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="JavaRMI.Classes.Boutique"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Boutiques!</h1>
        <%
            ArrayList<Boutique> boutiques = (ArrayList<Boutique>) request.getAttribute("boutiques");
            for(Boutique b : boutiques)
            {
                out.println("<a href='/afficherproduits?admin="+ b.getAdmin().getLogin() + ">" + b.getNom() + "</a>");
            }
        %>
        
    </body>
</html>
