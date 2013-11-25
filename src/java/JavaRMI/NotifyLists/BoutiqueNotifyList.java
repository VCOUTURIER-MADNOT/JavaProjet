/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaRMI.NotifyLists;

import JavaRMI.Classes.Boutique;
import JavaRMI.Classes.Utilisateur;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author valentin
 */
public class BoutiqueNotifyList extends NotifyList<Boutique>{

    public BoutiqueNotifyList(){
        SAXBuilder sxb = new SAXBuilder();
        this.xmlUrl = "Boutiques.xml";
        try
        {
            this.document = sxb.build(new File(this.xmlUrl));
        }
        catch(IOException | JDOMException e)
        {
            e.printStackTrace();
        }

        this.racine = this.document.getRootElement();
        
        for (Element eBoutique : this.racine.getChildren("Boutique"))
        {
            this.add((Boutique) this.getObjectFromElement(eBoutique), false);
        } 
    }

    @Override
    public void addToXML(Boutique _o) {
        Element eBoutique = this.getElementFromObject(_o);
        if(eBoutique != null)
        {
            this.racine.addContent(eBoutique);
        }
    }

    @Override
    public void removeFromXML(Object _o) {
        if(_o instanceof Boutique)
        {
            Boutique b = (Boutique) _o;
            Element e = this.getElementFromId(b.ipPortToString());
            this.racine.removeContent(e);
        }
    }

    @Override
    protected Element getElementFromId(String _id) {
        List<Element> boutiques = this.racine.getChildren("Boutique");
        Iterator<Element> iterator = boutiques.iterator();
        while(iterator.hasNext())
        {
            Element eBoutique = iterator.next();
            if ((eBoutique.getChildText("Ip")+':'+eBoutique.getChildText("Port")).equals(_id))
            {	
                return eBoutique;
            }
        }

        return null;
    }

    @Override
    protected Element getElementFromObject(Object _o) {
        Element eBoutique = null;
        if(_o instanceof Boutique)
        {
            Boutique b = (Boutique) _o;
            eBoutique = new Element("Boutique");
            
            Element eNom = new Element("Nom");
            eNom.setText(b.getNom());
            
            Element eIp = new Element("Ip");
            eIp.setText(b.getIp().getHostAddress());
            
            Element ePort = new Element("Port");
            ePort.setText(String.valueOf(b.getPort()));
            
            Element eAdmin = new Element ("Admin");
            eAdmin.setText(b.getAdmin().getLogin());
        
            eBoutique.addContent(eNom);
            eBoutique.addContent(eIp);
            eBoutique.addContent(ePort);
            eBoutique.addContent(eAdmin);
        }
        
        return eBoutique;
        
    }

    @Override
    protected Object getObjectFromElement(Element _e) {
        Boutique b = new Boutique(_e.getChildText("Nom"), _e.getChildText("Admin"));
        b.setIpPortFromString(_e.getChildText("Port")+":"+_e.getChildText("Ip"));
        
        return b;
    }
    
   
}
