/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import Boutique.Classes.Produit;
import JavaRMI.Classes.Boutique;
import JavaRMI.Gestionnaires.GestionnaireUtilisateurs;
import JavaRMI.Interfaces.IGestionnaireBoutiques;
import JavaRMI.Interfaces.IGestionnaireUtilisateurs;
import Util.NotifyLists.ProduitNotifyList;
import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public void creerBoutique(String _nomBoutique, String _loginAdmin, int _tcpPort, int _udpPort)
    {
        try {
            GB.creerBoutique(_nomBoutique, _loginAdmin, _tcpPort, _udpPort);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
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
    
    public void ajouterCommande(ArrayList<String> _nomProduits, String _loginAdminBoutique)
    {
        try {
            Boutique b = GB.getBoutique(_loginAdminBoutique);
            Socket so = new Socket(b.getIp(), b.getTcpPort());
            
            Document d = new Document();
            Element e = new Element("Request");
            d.setRootElement(e);
            e.setAttribute("action", "ajouterCommande");
            for(String nomProduit : _nomProduits)
            {
                e.addContent(Produit.getElementFromObject(this.getProduit(nomProduit, _loginAdminBoutique)));
            }
            //eid
            //elogin
            //java.util.UUID.randomUUID();
        } catch (Exception ex) {
            ex.printStackTrace();
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
