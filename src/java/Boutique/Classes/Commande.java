package Boutique.Classes;

import java.util.ArrayList;
import java.util.Objects;

public class Commande {
    
    private int          id;
    private String       login;
    private ArrayList<Produit> listeProduit;
    
    public Commande(int _id, String _login, ArrayList<Produit> _listeProduit)
    {
        this.id = _id;
        this.login = _login;
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
        return "Commande{" + "id=" + id + ", login=" + login + ", " + listeProduit.toString() + '}';
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

    
    
}
