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
                                                    out.println("<a style='margin-left: 27px' href='/JavaProjet/afficherproduits?boutique=" + URLEncoder.encode((String)(session.getAttribute("boutiqueDefaut")),"UTF-8") + "'>Produits</a>");
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
                                                    
                                                    <a href="/JavaProjet/inscrire" style="text-align:center;">S'inscrire</a>
					        </div>
					<%
					    }
					    if(request.getAttribute("msg")!= null && !request.getAttribute("msg").equals("Réussi"))
					    {
					        out.println("<br /><br /><p style='text-align:center;'>"+request.getAttribute("msg")+"</p>");
					    }
                                        %>
                         
                         </div>

			 <!-- Section produits -->
			 <div id='produits' class='clearfix'>
					<p class='text'>Produits</p>
                                        <div id="produitList">
                                        <%
                                            
                                            if (session.getAttribute("produits") != null){
                                                ArrayList<Produit> produits = (ArrayList<Produit>) session.getAttribute("produits");
				        
                                                for(Produit p : produits)
                                                {
                                                    out.println("<a href='/JavaProjet/ajoutercommande?produits="+ URLEncoder.encode(p.getNom(),"UTF-8")+ "'/>" + p.getNom() + "</a><br /><br />");
                                                }
                                            }
                                            else
                                            {
                                                out.println("Veuillez vous connecter");
                                            }
                                        %>
                                        </div>
			 </div>

			 <!-- Section boutiques -->
			 <div id='boutiques' class='clearfix'>
					 <p class="text">Boutiques</p>
                                         <div id="boutiqueList">
					 <%            
                                            ArrayList boutiques = (ArrayList) session.getAttribute("boutiques");
                                            if(boutiques != null)
                                            {
                                                for(Object o : boutiques)
                                                {
                                                    Boutique b = (Boutique) o;
                                                    out.println("<a href='/JavaProjet/afficherproduits?boutique=" + URLEncoder.encode(b.getNom(),"UTF-8") + "'>" + b.getNom() + "</a>");
                                                }
                                            }
                                            else
                                            {
                                                out.print("Veuillez vous connecter");
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