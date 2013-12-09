package Util.NotifyLists;

import Boutique.Classes.Produit;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ProduitNotifyList extends NotifyList<Produit>{

    public void ProduitNotifyList() {
        SAXBuilder sxb = new SAXBuilder();
        this.xmlUrl = "src/java/Produits.xml";
        try
        {
            this.document = sxb.build(new File(this.xmlUrl));
            this.racine = this.document.getRootElement();
        
            for (Element eProduit : this.racine.getChildren("Produit"))
            {
                this.add((Produit) this.getObjectFromElement(eProduit), false);
            } 
        }
        catch(IOException | JDOMException ex)
        {
            ex.printStackTrace();
        }
    }
    
    
    @Override
    public void addToXML(Produit _o) {
        Element eProduit = this.getElementFromObject(_o);
        if(eProduit != null)
        {
            this.racine.addContent(eProduit);
        }
    }

    @Override
    public void removeFromXML(Object _o) {
        if(_o instanceof Produit)
        {
            Produit p = (Produit) _o;
            Element e = this.getElementFromId(p.getNom());
            this.racine.removeContent(e);
        }
    }

    @Override
    public Element getElementFromId(String _id) {
        List<Element> produits = this.racine.getChildren("Produit");
        Iterator<Element> iterator = produits.iterator();
        while(iterator.hasNext())
        {
            Element eProduit = iterator.next();
            if ((eProduit.getChildText("Nom")).equals(_id))
            {	
                return eProduit;
            }
        }

        return null;
    }

    @Override
    public Element getElementFromObject(Object _o) {
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
        return new Produit(_e.getChildText("Nom"), _e.getChildText("Description"), Float.parseFloat(_e.getChildText("Prix")));
    }
}
