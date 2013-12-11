package Util.NotifyLists;

import Boutique.Classes.Commande;
import Boutique.Classes.Produit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class CommandeNotifyList extends NotifyList<Commande> {
    
    public void CommandeNotifyList() {
        SAXBuilder sxb = new SAXBuilder();
        this.xmlUrl = "src/java/Commandes.xml";
        try
        {
            this.document = sxb.build(new File(this.xmlUrl));
            this.racine = this.document.getRootElement();
        
            for (Element eCommande : this.racine.getChildren("Commande"))
            {
                this.add((Commande) this.getObjectFromElement(eCommande), false);
            } 
        }
        catch(IOException | JDOMException ex)
        {
            ex.printStackTrace();
        }
    }
    
    
    @Override
    public void addToXML(Commande _o) {
        Element eCommande = this.getElementFromObject(_o);
        if(eCommande != null)
        {
            this.racine.addContent(eCommande);
        }
    }

    @Override
    public void removeFromXML(Object _o) {
        if(_o instanceof Commande)
        {
            Commande c = (Commande) _o;
            Element e = this.getElementFromId(Integer.toString(c.getId()));
            this.racine.removeContent(e);
        }
    }

    @Override
    public Element getElementFromId(String _id) {
        List<Element> commandes = this.racine.getChildren("Commande");
        Iterator<Element> iterator = commandes.iterator();
        while(iterator.hasNext())
        {
            Element eCommande = iterator.next();
            if ((eCommande.getChildText("Id")).equals(_id))
            {	
                return eCommande;
            }
        }

        return null;
    }

    @Override
    public Element getElementFromObject(Object _o) {
        Element eCommande = null;
        if(_o instanceof Commande)
        {
            Commande p = (Commande) _o;
            eCommande = new Element("Commande");
            
            Element eId = new Element("Id");
            eId.setText(Integer.toString(p.getId()));
            
            Element eLogin = new Element("Login");
            eLogin.setText(p.getLogin());
            
            Element eListeProduit = new Element("ListeProduit");
            for(Produit eProduit : p.getListeProduit())
            {
                eListeProduit.addContent(getProduitElementFromObject(eProduit));
            }
        
            eCommande.addContent(eId);
            eCommande.addContent(eLogin);
            eCommande.addContent(eListeProduit);
        }
        
        return eCommande;  
    }
    
    public Element getProduitElementFromObject(Object _o) {
        Element eProduit = null;
        if(_o instanceof Produit)
        {
            Produit p = (Produit) _o;
            eProduit = new Element("Produit");
            
            Element eNom = new Element("Nom");
            eNom.setText(p.getNom());
            
            Element eDescription = new Element("Description");
            eDescription.setText(p.getDescription());
            
            Element ePrix = new Element("Prix");
            ePrix.setText(String.valueOf(p.getPrix()));
        
            eProduit.addContent(eNom);
            eProduit.addContent(eDescription);
            eProduit.addContent(ePrix);
        }
        
        return eProduit;  
    }

    @Override
    public Object getObjectFromElement(Element _e) {
        ArrayList<Produit> p = new ArrayList();
        Element listeProduit = _e.getChild("ListeProduit");
        for (Element eProduit : listeProduit.getChildren("Produit"))
        {
            p.add((Produit)getProduitFromElement(eProduit));
        }
        return new Commande(Integer.parseInt(_e.getChildText("Id")), _e.getChildText("Login"), p );
    }
    
    public Object getProduitFromElement(Element _e) {
        return new Produit(_e.getChildText("Nom"), _e.getChildText("Description"), Float.parseFloat(_e.getChildText("Prix")));
    }
}
