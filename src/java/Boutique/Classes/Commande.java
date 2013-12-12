package Boutique.Classes;

import java.util.ArrayList;
import java.util.Objects;
import org.jdom2.Element;

public class Commande {
    
    private int          id;
    private String       login;
    private boolean      valide;
    private ArrayList<Produit> listeProduit;
    
    public Commande(int _id, String _login, boolean _valide, ArrayList<Produit> _listeProduit)
    {
        this.id = _id;
        this.login = _login;
        this.valide = _valide;
        this.listeProduit = _listeProduit;
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int _id)
    {
        this.id = _id;
    }

    public String getLogin()
    {
        return new String(this.login);
    }

    public void setLogin(String _login)
    {
        this.login = _login;
    }
    
    public boolean getValide()
    {
        return this.valide;
    }

    public void setValide(boolean _valide)
    {
        this.valide = _valide;
    }
    
    public ArrayList<Produit> getListeProduit()
    {
        return (ArrayList<Produit>)this.listeProduit.clone();
    }
    
    public void setListeProduit(ArrayList<Produit> _listeProduit)
    {
        this.listeProduit = _listeProduit;
    }
            
    @Override
    public String toString() {
        return "Commande{" + "id=" + id + ", login=" + login + ", valide =" + (valide ? "oui" : "non") + ", " + listeProduit.toString() + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Commande other = (Commande) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public static Commande getObjectFromElement(Element _e) {
        ArrayList<Produit> p = new ArrayList();
        Element listeProduit = _e.getChild("ListeProduit");
        for (Element eProduit : listeProduit.getChildren("Produit"))
        {
<<<<<<< HEAD
            p.add((Produit)Produit.getObjectFromElement(eProduit));
=======
            p.add((Produit) Produit.getObjectFromElement(eProduit));
>>>>>>> Commit commit
        }
        return new Commande(Integer.parseInt(_e.getChildText("Id")), _e.getChildText("Login"), _e.getChildText("Valide") == "oui" , p );
    }
    
    public static Element getElementFromObject(Object _o) {
        Element eCommande = null;
        if(_o instanceof Commande)
        {
            Commande p = (Commande) _o;
            eCommande = new Element("Commande");
            
            Element eId = new Element("Id");
            eId.setText(Integer.toString(p.getId()));
            
            Element eLogin = new Element("Login");
            eLogin.setText(p.getLogin());
            
            Element eValide = new Element("Valide");
            eValide.setText(p.getValide()? "oui" : "non");
            
            Element eListeProduit = new Element("ListeProduit");
            for(Produit eProduit : p.getListeProduit())
            {
                eListeProduit.addContent(Produit.getElementFromObject(p));
            }
        
            eCommande.addContent(eId);
            eCommande.addContent(eLogin);
            eCommande.addContent(eValide);
            eCommande.addContent(eListeProduit);
        }
        
        return eCommande;  
    }
    
    
}
