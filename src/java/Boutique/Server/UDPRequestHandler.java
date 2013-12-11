package Boutique.Server;

import Boutique.Classes.Commande;
import Util.NotifyLists.CommandeNotifyList;
import java.io.IOException;
import java.net.DatagramPacket;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class UDPRequestHandler extends Thread {
    private 		Document 	document;
    private 		Element 	racine;
    private             CommandeNotifyList listeCommande;           
    
    private             DatagramPacket          clientPacket;
    
    public UDPRequestHandler(DatagramPacket _dp)
    {
        this.clientPacket = _dp;
        this.listeCommande = new CommandeNotifyList();
    }
    
    public void run()
    {

        SAXBuilder sxb = new SAXBuilder();

        try
        {
            byte[] recv = new byte[2048];
            
            recv = clientPacket.getData();
            
            String xml = Util.StringUtil.ByteArrayToXMLString(recv);
            
            this.document = sxb.build(xml);
            this.racine = this.document.getRootElement();
        
           Document d = new Document();
            
            switch(this.racine.getAttributeValue("action"))
            {
                case "ajoutCommande":
                    d = ajoutRemplaceCommande();
                    break;
                case "supprimerCommande":
                    d = supprimerCommandeSiExistant();
                    break;
                case "validerCommande":
                    d = validerCommande();
                    break;
                case "afficherCommandes":
                    d = afficherCommandes();
                    break;
            }
            
            // Envoi
        }
        catch(IOException | JDOMException ex)
        {
            ex.printStackTrace();
        }
       
    }
    
    private Document ajoutRemplaceCommande()
    {
        Document d = new Document();
        Element msg = new Element("Message");
        Element response = new Element("Response");
        d.setRootElement(response);
        
 
        msg.addContent("Produit ajoute");
        response.addContent(msg);
        return d;
    }
    
    private Document supprimerCommandeSiExistant()
    {
        Document d = new Document();
        Element msg = new Element("Message");
        Element response = new Element("Response");
        d.setRootElement(response);
        

        
        response.addContent(msg);
        return d;
    }
    
        
    private Document validerCommande()
    {
        Document d = new Document();
        Element msg = new Element("Message");
        Element response = new Element("Response");
        d.setRootElement(response);
        

        
        response.addContent(msg);
        return d;
    }
    
    private Document afficherCommandes()
    {
        Document d = new Document();
        Element response = new Element("Response");
        d.setRootElement(response);
        
        for (Commande p: this.listeCommande)
        {
            response.addContent(this.listeCommande.getElementFromObject(p));
        }
        
        return d;
    }
}

