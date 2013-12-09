package Boutique.Server;

import Boutique.Classes.Produit;
import Util.NotifyLists.ProduitNotifyList;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class TCPRequestHandler extends Thread {
    
    private 		Document 	document;
    private 		Element 	racine;
    private             ProduitNotifyList listeProduit;           
    
    private             Socket          clientSocket;
    
    public TCPRequestHandler(Socket _s)
    {
        this.clientSocket = _s;
        this.listeProduit = new ProduitNotifyList();
    }
    
    public void run()
    {

        SAXBuilder sxb = new SAXBuilder();
        

        try
        {
            this.document = sxb.build(clientSocket.getInputStream());
            this.racine = this.document.getRootElement();
        
           Document d = new Document();
            
            switch(this.racine.getAttributeValue("action"))
            {
                case "ajoutProduit":
                    d = ajoutRemplaceProduit();
                    break;
                case "supprimerProduit":
                    d = supprimerProduitSiExistant();
                    break;
                case "afficherProduits":
                    d = afficherProduits();
                    break;
            }         
            
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
            pw.print(new XMLOutputter().outputString(d));
        }
        catch(IOException | JDOMException ex)
        {
            ex.printStackTrace();
        }
       
    }
    
    private Document ajoutRemplaceProduit()
    {
        Document d = new Document();
        Element msg = new Element("Message");
        Element response = new Element("Response");
        d.setRootElement(response);
        
        Produit p = (Produit)this.listeProduit.getObjectFromElement(this.racine.getChild("Produit"));
        
        Element elementEquivoque =  this.listeProduit.getElementFromId(p.getNom());
        if ( elementEquivoque != null)
        {
            this.listeProduit.removeFromXML((Produit)this.listeProduit.getObjectFromElement(elementEquivoque));
        }
        this.listeProduit.add(p, true);
        
        msg.addContent("Produit ajoute");
        response.addContent(msg);
        return d;
    }
    
    private Document supprimerProduitSiExistant()
    {
        Document d = new Document();
        Element msg = new Element("Message");
        Element response = new Element("Response");
        d.setRootElement(response);
        
        Element element =  this.listeProduit.getElementFromId(this.racine.getChildText("NomProduit"));
        if ( element != null)
        {
            this.listeProduit.removeFromXML((Produit)this.listeProduit.getObjectFromElement(element));
            
            msg.addContent("Produit supprime");
        }
        else
            msg.addContent("Produit inconnu");
        
        response.addContent(msg);
        return d;
    }
    
    private Document afficherProduits()
    {
        Document d = new Document();
        Element response = new Element("Response");
        d.setRootElement(response);
        
        for (Produit p: this.listeProduit)
        {
            response.addContent(this.listeProduit.getElementFromObject(p));
        }
        
        return d;
    }
}
