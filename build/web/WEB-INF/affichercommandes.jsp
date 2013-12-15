<%@page import="Boutique.Classes.Commande"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="JavaRMI.Classes.Boutique"%>
<%@page import="Boutique.Classes.Produit"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
		<!-- This code is only meant for previewing your Reflow design. -->
		<head>
                    <link rel="stylesheet" href="/JavaProjet//boilerplate.css" />
                    <link rel="stylesheet" href="/JavaProjet//style.css" />
                    <meta charset="utf-8">
                    <meta name="viewport" content="initial-scale = 1.0,maximum-scale = 1.0" />
		</head>
		<body>

	<div id="body" class="clearfix">


			 <!-- Image Ville + Ordinateur -->
			 <img id='backgroundImg' src='/JavaProjet/img/java_0001s_0000s_0003_wallpaper-2878536.png' class='image' />
			 <img id='computerImg' src='/JavaProjet/img/java_0001s_0000s_0002_computer.png' class='image' />

			<!-- Header -->
			 <div id='header' class='clearfix'>
					 <p id='boutiqueTxt'>Boutique</p>
			 </div>

			 <!-- Boutons connexion / panier -->
			 <div id='loginBtn' />
                                        <%
					    if(session.getAttribute("login") != null)
					    {
					        out.println("<p style='text-align:center;'>Bienvenue " + session.getAttribute("nom") + "</p>");
					%>
                                                <a href="/JavaProjet/" style="margin-left: 27px">Accueil</a>
					        <form name='deco' method='GET' action='/JavaProjet/deconnexion' style="margin: auto; width: 100px">
					            <input type='submit' value='Déconnexion'>
					        </form>
                                                
                                        <%
                                             if(session.getAttribute("aBoutique") !=null)
                                             {
                                                if (session.getAttribute("aBoutique").equals("non"))
                                                {
                                                    out.println("<a style='margin-left: 27px' href='/JavaProjet/creerboutique'>Ajout. Boutique</a>");
                                                }
                                                else
                                                {
                                                    out.println("<a style='margin-left: 27px' href='/JavaProjet/affichercommande'>Commandes</a>");
                                                    out.println("<a style='margin-left: 27px' href='/JavaProjet/afficherproduits?boutique=" + URLEncoder.encode((String)session.getAttribute("boutiqueDefaut"),"UTF-8") + "'>Produits</a>");
                                                    out.println("<a style='margin-left: 27px' href='/JavaProjet/supprimerboutique'>Suppr. Boutique</a>");
                                                    out.println("<a style='margin-left: 27px' href='/JavaProjet/ajouterproduit'>Ajout. Produit</a>");
                                                }
                                             }
                                        %>
                                                <a style="margin-left: 27px" href="/JavaProjet/desinscrire">Se désinscrire</a>
                                                
					<%
					    }
					    else
					    {
					        out.println("<p style='text-align:center;'>Connexion</p>");
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
					        out.println("<br /><p style='text-align:center;'>"+request.getAttribute("msg")+"</p>");
					    }
                                            
                                        %>
                         
                         </div>

			 <!-- Section Commande -->
			 <div id='produits' class='clearfix'>
					<p class='text'>Commandes</p>
                                        <div id="produitList">
                                        <%

                                            if (request.getAttribute("commandes") != null){
                                                ArrayList<Commande> commandes = (ArrayList<Commande>) request.getAttribute("commandes");
				        
                                                for(Commande c : commandes)
                                                {
                                                    out.print("<p>" + URLEncoder.encode(c.getId(),"UTF-8")+ " " + c.getLogin() + " " + c.getListeProduit().get(0).getNom() + " " + (c.getValide() ? "validée" : "non validée") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                                    out.print("<a href='/JavaProjet/validercommande?idCommande=" + URLEncoder.encode(c.getId(), "UTF-8") + "'>Valider</a> ");
                                                    out.print("<a href='/JavaProjet/supprimercommande?idCommande=" + URLEncoder.encode(c.getId(), "UTF-8") + "'>Supprimer</a>");
                                                    out.print("</p>");
                                                }
                                            }
                                        %>
                                        </div>
			 </div>
                                        
			 <!-- Footer -->
			 <div id='footer' class='clearfix'>
					 <p id='footerTxt'>2013 - Maxence ADNOT - Coutcout</p>
			 </div>

	</div>
		</body>
</html>
