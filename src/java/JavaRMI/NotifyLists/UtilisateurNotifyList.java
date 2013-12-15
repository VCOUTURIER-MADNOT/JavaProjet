package JavaRMI.NotifyLists;

import JavaRMI.Classes.Utilisateur;
import JavaRMI.NotifyLists.NotifyList;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;


public class UtilisateurNotifyList extends NotifyList<Utilisateur>{

    public UtilisateurNotifyList() {
        SAXBuilder sxb = new SAXBuilder();
        this.xmlUrl = "src/java/JavaRMI/Utilisateurs.xml";
        try
        {
            this.document = sxb.build(new File(this.xmlUrl));
            this.racine = this.document.getRootElement();

            for (Element eUtilisateur : this.racine.getChildren("Utilisateur"))
            {
                this.add((Utilisateur) this.getObjectFromElement(eUtilisateur), false);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void addToXML(Utilisateur _o) {
        Element eUtilisateur = this.getElementFromObject(_o);
        if(eUtilisateur != null)
        {
            this.racine.addContent(eUtilisateur);
        }
    }

    @Override
    public void removeFromXML(Object _o) {
        if(_o instanceof Utilisateur)
        {
            Utilisateur u = (Utilisateur) _o;
            Element e = this.getElementFromId(String.valueOf(u.getLogin()));
            this.racine.removeContent(e);
        }   
    }

    @Override
    public Element getElementFromId(String _id) {
        List<Element> utilisateurs = this.racine.getChildren("Utilisateur");
        Iterator<Element> iterator = utilisateurs.iterator();
        Element eUtilisateur = null;
        while(iterator.hasNext())
        {
            eUtilisateur = iterator.next();
            if (eUtilisateur.getChildText("Login").equals(_id))
            {	
                return eUtilisateur;
            }
        }

        return null;
    }

    @Override
    public Element getElementFromObject(Object _o) {
        Element eUtilisateur = null;
        if (_o instanceof Utilisateur)
        {
            Utilisateur u = (Utilisateur) _o;
            eUtilisateur = u.toElement();
        }
        
        return eUtilisateur;
    }

    @Override
    public Object getObjectFromElement(Element _e) {
         return new Utilisateur(_e.getChildText("Login"), _e.getChildText("Password"), _e.getChildText("Nom"), Integer.valueOf(_e.getChildText("UserLevel")));
    }
    
}
