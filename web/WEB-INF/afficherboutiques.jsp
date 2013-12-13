<%-- 
    Document   : afficherBoutiques
    Created on : 13 déc. 2013, 16:21:38
    Author     : valentin
--%>

<%@page import="Util.NotifyLists.BoutiqueNotifyList"%>
<%@page import="java.net.URLEncoder"%>
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
            ArrayList boutiques = (ArrayList) request.getAttribute("boutiques");
            if(boutiques != null)
            {
                for(Object o : boutiques)
                {
                    Boutique b = (Boutique) o;
                    out.println("<a href='/afficherproduits?boutique="+b.getNom()+"'>" + b.getNom() + "</a>");
                }
            }
        %>
        
    </body>
</html>
