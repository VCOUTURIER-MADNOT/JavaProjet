/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import Boutique.Classes.Commande;
import Boutique.Classes.Produit;
import JavaRMI.Classes.Boutique;
import JavaRMI.Gestionnaires.GestionnaireUtilisateurs;
import JavaRMI.Interfaces.IGestionnaireBoutiques;
import JavaRMI.Interfaces.IGestionnaireUtilisateurs;
import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.SocketAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author valentin
 */
public class Gestionnaire {
    
    private static IGestionnaireUtilisateurs GU;
    private static IGestionnaireBoutiques GB;
    
    public Gestionnaire()
    {
        try {
            Registry registry = LocateRegistry.getRegistry(12345);
            GU = (IGestionnaireUtilisateurs) registry.lookup("rmi://127.0.0.1:12345/JavaRMI/GestionnaireUtilisateurs");
            GB = (IGestionnaireBoutiques) registry.lookup("rmi://127.0.0.1:12345/JavaRMI/GestionnaireBoutiques");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void inscrire(String _login, String _mdp, String _name)
    {
        try {
            GU.inscrire(_login, _mdp, _name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Seuls le client et l'admin général devrait pouvoir désinscrire le client
    public void desinscrire(String _login, String _loginExecuteur)
    {
        try {
            GU.desinscrire(_login, _loginExecuteur);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean isUtilisateur(String _login)
    {
        return GestionnaireUtilisateurs.getUtilisateur(_login) != null;
    }
    
    public boolean isValidAuthentication(String _login, String _password)
    {
        try {
            return GU.isValidAuthentication(_login, _password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }
    
    // Seul un client enregistré devrait pouvoir créer une boutique
    public void creerBoutique(String _nomBoutique, String _loginAdmin, int _tcpPort, int _udpPort)
    {
        try {
            GB.creerBoutique(_nomBoutique, _loginAdmin, _tcpPort, _udpPort);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Seuls l'admin de la boutique et l'admin général devraient pouvoir supprimer sa boutique
    public void supprimerBoutique(String _ipPortBoutique, String _loginAdmin)
    {
        try {
            GB.supprimerBoutique(_ipPortBoutique, _loginAdmin);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public ArrayList<Boutique> getBoutiques()
    {
        try {
            return GB.getBoutiques();
        } catch (RemoteException ex) {
            return null;
        }
    }
    
    public ArrayList<Produit> getProduits(String _loginAdminBoutique)
    {
        try {
            
            ArrayList<Produit> listeProduits = new ArrayList<Produit>();
            
            Boutique b = GB.getBoutique(_loginAdminBoutique);
            Socket so = new Socket(b.getIp(), b.getTcpPort());
            
            Document d = new Document();
            Element e = new Element("Request");
            d.setRootElement(e);
            e.setAttribute("action", "afficherProduits");
            PrintWriter pw = new PrintWriter(so.getOutputStream());
            pw.println(new XMLOutputter().outputString(d));
            pw.println("\n");
            pw.flush();
            so.shutdownOutput();
            //System.out.println("Requete afficher produit envoyée ! \n" + new XMLOutputter().outputString(d));
            
            SAXBuilder sb = new SAXBuilder();
            d = (Document) sb.build(new ByteArrayInputStream(Util.StringUtil.XMLInputStreamToStr(so.getInputStream()).toString().getBytes()));
            
            
            Element rootElement = d.getRootElement();
            if(rootElement.getName().equals("Response"))
            {
                for(Element el : rootElement.getChildren("Produit"))
                {
                    System.out.println(el.getChildText("Nom"));
                    listeProduits.add(new Produit(el.getChildText("Nom"), el.getChildText("Description"), Float.parseFloat(el.getChildText("Prix"))));
                }
            }
            so.close();
            return listeProduits;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    // Seul l'admin de la boutique devrait pouvoir ajouter un produit
    public void ajoutProduit(String _loginAdminBoutique, Produit _p)
    {
        try {
            
            Boutique b = GB.getBoutique(_loginAdminBoutique);
            Socket so = new Socket(b.getIp(), b.getTcpPort());
            
            Document d = new Document();
            Element e = new Element("Request");
            d.setRootElement(e);
            e.setAttribute("action", "ajoutProduit");
            e.addContent(Produit.getElementFromObject(_p));
            PrintWriter pw = new PrintWriter(so.getOutputStream());
            pw.println(new XMLOutputter().outputString(d));
            pw.println("\n");
            pw.flush();
            so.shutdownOutput();
            //System.out.println("Requete afficher produit envoyée ! \n" + new XMLOutputter().outputString(d));
            
            SAXBuilder sb = new SAXBuilder();
            d = (Document) sb.build(new ByteArrayInputStream(Util.StringUtil.XMLInputStreamToStr(so.getInputStream()).toString().getBytes()));
            
            System.out.println(new XMLOutputter().outputString(d));
            
            so.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Seul l'admin de la boutique devrait pouvoir supprimer un produit
    public void supprimerProduit(String _loginAdminBoutique, String _nomProduit)
    {
        try {
            
            Boutique b = GB.getBoutique(_loginAdminBoutique);
            Socket so = new Socket(b.getIp(), b.getTcpPort());
            
            Document d = new Document();
            Element e = new Element("Request");
            d.setRootElement(e);
            e.setAttribute("action", "supprimerProduit");
            Element nomProduit = new Element("NomProduit");
            nomProduit.setText(_nomProduit);
            e.addContent(nomProduit);
            PrintWriter pw = new PrintWriter(so.getOutputStream());
            pw.println(new XMLOutputter().outputString(d));
            pw.println("\n");
            pw.flush();
            so.shutdownOutput();
            //System.out.println("Requete afficher produit envoyée ! \n" + new XMLOutputter().outputString(d));
            
            SAXBuilder sb = new SAXBuilder();
            d = (Document) sb.build(new ByteArrayInputStream(Util.StringUtil.XMLInputStreamToStr(so.getInputStream()).toString().getBytes()));
            
            System.out.println(new XMLOutputter().outputString(d));
            
            so.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Seul un client enregistré devrait pouvoir ajouter une commande
    public void ajouterCommande(ArrayList<String> _nomProduits, String _loginClient, String _loginAdminBoutique)
    {
        try {
            Boutique b = GB.getBoutique(_loginAdminBoutique);
            
            Document d = new Document();
            Element e = new Element("Request");
            d.setRootElement(e);
            e.setAttribute("action", "ajoutCommande");
            
            Element eCommande = new Element("Commande");
            Element eList = new Element("ListeProduit");
            for(String nomProduit : _nomProduits)
            {
                // On récupère un élément à partir d'un objet de telle boutique
                eList.addContent(Produit.getElementFromObject(this.getProduit(nomProduit, _loginAdminBoutique)));
            }
            
            Element eId = new Element("Id");
            eId.setText(java.util.UUID.randomUUID().toString());
            
            Element eLogin = new Element("Login");
            eLogin.setText(_loginClient);
            
            Element eValide = new Element("Valide");
            eValide.setText("non");
            
            eCommande.addContent(eList);
            eCommande.addContent(eId);
            eCommande.addContent(eLogin);
            eCommande.addContent(eValide);
            
            e.addContent(eCommande);
            
            DatagramSocket ds = new DatagramSocket();
            
            String xml = new XMLOutputter().outputString(d);
            byte[] buffer = xml.getBytes();
            
            // Le datagramPacket ne peut pas envoyer au "0.0.0.0" donc on lui passe le getLocalHost().
            // Ceci ne devrait se passer seulement pour les tests car logiquement, le client ne commande pas du meme serveur de la boutique.
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length, Inet4Address.getLocalHost(), b.getUdpPort());
                    
            ds.send(dp);
            System.out.println(xml + "envoyé");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Seul l'admin de la boutique devrait pouvoir supprimer une commande
    public void supprimerCommande(String _idCommande, String _loginAdminBoutique)
    {
        try {
            Boutique b = GB.getBoutique(_loginAdminBoutique);
            
            Document d = new Document();
            Element e = new Element("Request");
            d.setRootElement(e);
            e.setAttribute("action", "supprimerCommande");
            
            Element eId = new Element("IdCommande");
            eId.setText(_idCommande);
            
            e.addContent(eId);
            
            DatagramSocket ds = new DatagramSocket();
            
            String xml = new XMLOutputter().outputString(d);
            
            byte[] buffer = xml.getBytes();
            
            // Le datagramPacket ne peut pas envoyer au "0.0.0.0" donc on lui passe le getLocalHost().
            // Ceci ne devrait se passer seulement pour les tests car logiquement, le client ne commande pas du meme serveur de la boutique.
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length, Inet4Address.getLocalHost(), b.getUdpPort());
                    
            ds.send(dp);
            System.out.println(xml + "envoyé");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Seul l'admin de la boutique devrait pouvoir valider une commande
    public void validerCommande(String _idCommande, boolean valide, String _loginAdminBoutique)
    {
        try {
            Boutique b = GB.getBoutique(_loginAdminBoutique);
            
            Document d = new Document();
            Element e = new Element("Request");
            d.setRootElement(e);
            e.setAttribute("action", "validerCommande");
            
            Element eId = new Element("IdCommande");
            eId.setText(_idCommande);
            
            Element eValide = new Element("Valide");
            eValide.setText(valide?"oui":"non");
            
            e.addContent(eId);
            e.addContent(eValide);
            
            DatagramSocket ds = new DatagramSocket();
            
            String xml = new XMLOutputter().outputString(d);
            
            byte[] buffer = xml.getBytes();
            
            // Le datagramPacket ne peut pas envoyer au "0.0.0.0" donc on lui passe le getLocalHost().
            // Ceci ne devrait se passer seulement pour les tests car logiquement, le client ne commande pas du meme serveur de la boutique.
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length, Inet4Address.getLocalHost(), b.getUdpPort());
                    
            ds.send(dp);
            System.out.println(xml + "envoyé");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Seul l'admin de la boutique devrait pouvoir afficher les commandes
    public ArrayList<Commande> afficherCommandes(String _loginAdminBoutique)
    {
        ArrayList<Commande> commandes = null;
        try {
            Boutique b = GB.getBoutique(_loginAdminBoutique);
            
            Document d = new Document();
            Element e = new Element("Request");
            d.setRootElement(e);
            e.setAttribute("action", "afficherCommandes");
                       
            DatagramSocket ds = new DatagramSocket();
            SocketAddress sa = ds.getLocalSocketAddress();
            
            String xml = new XMLOutputter().outputString(d);
            
            byte[] buffer = xml.getBytes();
            
            // Le datagramPacket ne peut pas envoyer au "0.0.0.0" donc on lui passe le getLocalHost().
            // Ceci ne devrait se passer seulement pour les tests car logiquement, le client ne commande pas du meme serveur de la boutique.
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length, Inet4Address.getLocalHost(), b.getUdpPort());        
            ds.send(dp);
            System.out.println(xml + "envoyé");
            
            Document docIn = this.getDocumentFromResponse(ds);
            if(docIn != null)
            {
                commandes = new ArrayList<Commande>();
                Element rootElement = docIn.getRootElement();
                for(Element eCommande : rootElement.getChildren("Commande"))
                {
                    Commande c = (Commande) Commande.getObjectFromElement(eCommande);
                    if(c != null)
                    {
                        commandes.add(c);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return commandes;
    }
    
    private Document getDocumentFromResponse(DatagramSocket _ds)
    {
        try {
            // Attente d'une réponse
            byte[] byteToPacket = new byte[1024];
            DatagramPacket dpIn = new DatagramPacket(byteToPacket, byteToPacket.length);
            //DatagramSocket dsIn = new DatagramSocket(sa);
            _ds.receive(dpIn);
            
            byte[] recv = new byte[2048];
            recv = dpIn.getData();
            String xmlIn = Util.StringUtil.ByteArrayToXMLString(recv);
            
            SAXBuilder builder = new SAXBuilder();
            return builder.build(new StringReader(xmlIn));
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private Produit getProduit(String _nomProduit, String _nomBoutique)
    {
        for(Produit p : this.getProduits(_nomBoutique))
        {
            if(p.getNom().equals(_nomProduit))
            {
                return p;
            }
        }
        
        return null;
    }
}
