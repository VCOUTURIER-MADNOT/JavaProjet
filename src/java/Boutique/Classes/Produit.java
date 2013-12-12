package Boutique.Classes;

import java.util.Objects;
import org.jdom2.Element;

public class Produit {
    private String          nom;
    private String          description;
    private float           prix;
    
    public Produit(String _nom, String _description, float _prix)
    {
        this.nom = _nom;
        this.description = _description;
        this.prix = _prix;
    }

    public String getNom()
    {
        return new String(this.nom);
    }
    
    public void setNom(String _nom)
    {
        this.nom = _nom;
    }
    
    public String getDescription()
    {
        return new String(this.description);
    }
    
    public void setDescription(String _description)
    {
        this.description = _description;
    }
    
    public float getPrix()
    {
        return this.prix;
    }
    
    public void setPrix(float _prix)
    {
        this.prix = _prix;
    }
            
    @Override
    public String toString() {
        return "Produit{" + "nom=" + nom + ", description=" + description + ", prix=" + prix + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produit other = (Produit) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        return true;
    }    
    
    public static Element getElementFromObject(Object _o) {
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

    public static Object getObjectFromElement(Element _e) {
        return new Produit(_e.getChildText("Nom"), _e.getChildText("Description"), Float.parseFloat(_e.getChildText("Prix")));
    }
}
