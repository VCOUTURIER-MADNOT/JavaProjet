<%-- 
    Document   : index
    Created on : 13 déc. 2013, 14:31:49
    Author     : valentin
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
    if(session.getAttribute("login") != null)
    {
        out.println("Bienvenue " + session.getAttribute("nom"));
%>
        <form name='deco' method='GET' action='/JavaProjet/deconnexion'>
            <input type='submit' value='Déconnexion'>
        </form>
        <a href="/JavaProjet/afficherBoutique"> afficher Boutiques </a>
<%
    }
    else
    {
        out.println("Veuillez vous connecter");
%>
        <div>
            <form name="connexionForm" action="/JavaProjet/connexion" method="POST">
                <input type="text" name="login">
                <input type="password" name="mdp">
                <input type="submit" value="Envoyer">
            </form>
        </div>
<%
    }
    if(request.getAttribute("msg")!= null)
    {
        out.println(request.getAttribute("msg"));
    }
%>
        
    </body>
</html>
