/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import Boutique.Classes.Produit;
import JavaRMI.Classes.Boutique;
import JavaRMI.Gestionnaires.GestionnaireUtilisateurs;
import Servlet.Interfaces.IGestionnaireBoutiques;
import Servlet.Interfaces.IGestionnaireUtilisateurs;
import java.io.PrintWriter;
import java.net.Socket;
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
            GU = (IGestionnaireUtilisateurs) registry.lookup("/JavaRMI/GestionnaireUtilisateurs");
            GB = (IGestionnaireBoutiques) registry.lookup("/JavaRMI/GestionnaireBoutiques");
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
            Boutique b = GB.getBoutique(_loginAdminBoutique);
            Socket so = new Socket(b.getIp(), b.getTcpPort());
            
            Document d = new Document();
            Element e = new Element("Request");
            e.setAttribute("action", "afficherProduits");
            PrintWriter pw = new PrintWriter(so.getOutputStream());
            pw.print(new XMLOutputter().outputString(d));
            
            SAXBuilder sb = new SAXBuilder();
            d = sb.build(so.getInputStream());
            Element rootElement = d.getRootElement();
            if(rootElement.getText().equals("Response"))
            {
                for(Element el : rootElement.getChildren("Produit"))
                {
                    System.out.println(el.getChildText("Nom"));
                }
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
        
    }
}
