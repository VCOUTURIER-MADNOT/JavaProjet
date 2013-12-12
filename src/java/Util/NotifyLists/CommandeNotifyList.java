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
    
    public CommandeNotifyList() {
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
        return Commande.getElementFromObject(_o);  
    }
    
    @Override
    public Object getObjectFromElement(Element _e) {
        return Commande.getCommandeFromElement(_e);
    }
}
