package Util.NotifyLists;

import JavaRMI.Classes.Boutique;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class BoutiqueNotifyList extends NotifyList<Boutique>{

    public BoutiqueNotifyList(){
        SAXBuilder sxb = new SAXBuilder();
        this.xmlUrl = "src/java/Boutiques.xml";
        try
        {
            this.document = sxb.build(new File(this.xmlUrl));
                    this.racine = this.document.getRootElement();
        
            for (Element eBoutique : this.racine.getChildren("Boutique"))
            {
                this.add((Boutique) this.getObjectFromElement(eBoutique), false);
            } 
        }
        catch(IOException | JDOMException e)
        {
            e.printStackTrace();
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
            Element e = this.getElementFromId(b.getAdmin().getLogin());
            this.racine.removeContent(e);
        }
    }

    @Override
    public Element getElementFromId(String _id) {
        List<Element> boutiques = this.racine.getChildren("Boutique");
        Iterator<Element> iterator = boutiques.iterator();
        while(iterator.hasNext())
        {
            Element eBoutique = iterator.next();
            if ((eBoutique.getChildText("Admin")).equals(_id))
            {	
                return eBoutique;
            }
        }

        return null;
    }

    @Override
    public Element getElementFromObject(Object _o) {
        Element eBoutique = null;
        if(_o instanceof Boutique)
        {
            Boutique b = (Boutique) _o;
            eBoutique = new Element("Boutique");
            
            Element eNom = new Element("Nom");
            eNom.setText(b.getNom());
            
            Element eIp = new Element("Ip");
            eIp.setText(b.getIp().getHostAddress());
            
            Element eTcpPort = new Element("TcpPort");
            eTcpPort.setText(String.valueOf(b.getTcpPort()));
            
            Element eUdpPort = new Element("UdpPort");
            eUdpPort.setText(String.valueOf(b.getUdpPort()));
            
            Element eAdmin = new Element ("Admin");
            eAdmin.setText(b.getAdmin().getLogin());
        
            eBoutique.addContent(eNom);
            eBoutique.addContent(eIp);
            eBoutique.addContent(eTcpPort);
            eBoutique.addContent(eUdpPort);
            eBoutique.addContent(eAdmin);
        }
        
        return eBoutique;
        
    }

    @Override
    public Object getObjectFromElement(Element _e) {
        Boutique b = new Boutique(_e.getChildText("Admin"), Integer.parseInt(_e.getChildText("TcpPort")), Integer.parseInt(_e.getChildText("UdpPort")));
        b.setIpFromString(_e.getChildText("Ip"));
        return b;
    }
    
   
}
