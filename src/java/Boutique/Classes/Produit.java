package Boutique.Classes;

import java.util.Objects;

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
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (Float.floatToIntBits(this.prix) != Float.floatToIntBits(other.prix)) {
            return false;
        }
        return true;
    }    
}
