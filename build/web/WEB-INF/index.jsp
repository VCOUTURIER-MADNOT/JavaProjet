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
					        <form name='deco' method='GET' action='/JavaProjet/deconnexion' style="margin: auto; width: 100px">
					            <input type='submit' value='Déconnexion'>
					        </form>
                                                <a style="margin-left: 27px" href="/JavaProjet/affichercommande">Commandes</a>
                                                <a style="margin-left: 27px" href="/JavaProjet/afficherproduits?boutique=">Produits</a>
                                                <a style="margin-left: 27px" href="/JavaProjet/creerboutique">Ajout. Boutique</a>
                                                <a style="margin-left: 27px" href="/JavaProjet/supprimerboutique">Suppr. Boutique</a>
                                                <a style="margin-left: 27px" href="/JavaProjet/desinscrire">Desincrire</a>
                                                
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
					    if(request.getAttribute("msg")!= null && !request.getAttribute("msg").equals("Réussi"))
					    {
					        out.println("<p style='text-align:center;'>"+request.getAttribute("msg")+"</p>");
					    }
                                        %>
                         
                         </div>
			 <img id='panierBtn' src='/JavaProjet/img/java_0001s_0000s_0001_panier-button.png' class='image' onclick="document.write('panier')" style="cursor: pointer;"/>


			 <!-- Section produits -->
			 <div id='produits' class='clearfix'>
					<p class='text'>Produits</p>
                                        <div id="produitList">
                                        <%
                                            
                                            if (request.getAttribute("produits") != null){
                                                ArrayList<Produit> produits = (ArrayList<Produit>) request.getAttribute("produits");
				        
                                                for(Produit p : produits)
                                                {
                                                    out.println("<p>"+ p.getNom() + "</p>");
                                                }
                                            }
                                        %>
                                        
                                        <p>Test</p>
                                        </div>
			 </div>

			 <!-- Section boutiques -->
			 <div id='boutiques' class='clearfix'>
					 <p class="text">Boutiques</p>
                                         <div id="boutiqueList">
					 <%            
                                            ArrayList boutiques = (ArrayList) request.getAttribute("boutiques");
                                            if(boutiques != null)
                                            {
                                                for(Object o : boutiques)
                                                {
                                                    Boutique b = (Boutique) o;
                                                    out.println("<a class='text' href='/JavaProjet/afficherproduits?boutique=" + URLEncoder.encode(b.getNom(),"UTF-8") + "'>" + b.getNom() + "</a>");
                                                }
                                            }
                                            else
                                            {
                                                out.print("null");
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