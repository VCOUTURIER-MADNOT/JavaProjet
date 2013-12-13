package Boutique.Server;

import Boutique.Classes.Commande;
import Boutique.Classes.Produit;
import Util.NotifyLists.CommandeNotifyList;
import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

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
            System.out.println(xml);
            this.document = sxb.build(new StringReader(xml));
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
            String response = new XMLOutputter().outputString(d);
            byte[] buffer = response.getBytes();
            
            DatagramPacket dpOut = new DatagramPacket(buffer, buffer.length, clientPacket.getSocketAddress());
            DatagramSocket dsOut = new DatagramSocket();
            dsOut.send(dpOut);
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
        
        Commande c = (Commande)this.listeCommande.getObjectFromElement(this.racine.getChild("Commande"));
        
        Element elementEquivoque =  this.listeCommande.getElementFromId(String.valueOf(c.getId()));
        if ( elementEquivoque != null)
        {
            this.listeCommande.remove((Commande)this.listeCommande.getObjectFromElement(elementEquivoque));
        }
        this.listeCommande.add(c, true);
 
        msg.addContent("Commande ajoute");
        response.addContent(msg);
        return d;
    }
    
    private Document supprimerCommandeSiExistant()
    {
        Document d = new Document();
        Element msg = new Element("Message");
        Element response = new Element("Response");
        d.setRootElement(response);
        
        Element element =  this.listeCommande.getElementFromId(this.racine.getChildText("IdCommande"));
        if ( element != null)
        {
            this.listeCommande.remove((Commande)this.listeCommande.getObjectFromElement(element));
            
            msg.addContent("Commande supprimee");
        }
        else
            msg.addContent("Commande inconnue");
        
        response.addContent(msg);
        return d;
    }
    
        
    private Document validerCommande()
    {
        Document d = new Document();
        Element msg = new Element("Message");
        Element response = new Element("Response");
        d.setRootElement(response);
        
        Element element =  this.listeCommande.getElementFromId(this.racine.getChildText("IdCommande"));
        if ( element != null)
        {
            Commande c = (Commande)this.listeCommande.getObjectFromElement(element);
            this.listeCommande.remove(c);
            c.setValide(this.racine.getChildText("Valide").equals("oui"));
            this.listeCommande.add(c, true);
            msg.addContent("Commande modifiee");
        }
        else 
            msg.addContent("Commande introuvable");
        
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

