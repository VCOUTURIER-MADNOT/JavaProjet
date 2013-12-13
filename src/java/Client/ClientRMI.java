package Client;

import Boutique.Classes.Produit;
import Servlet.Gestionnaire;
import java.util.ArrayList;
 
/**
 * Client permettant d'interroger la personne sur le serveur distant.
 * @author Cyril Rabat
 * @version 08/10/2013
 */
public class ClientRMI {
 
    /**
     * Methode principale.
     * @param args inutilise
     */
    public static void main(String[] args){
    	Gestionnaire g = new Gestionnaire();
        
        ArrayList<String> listeProduits = new ArrayList<>();
        listeProduits.add("LEL1");
        listeProduits.add("LEL2");
        
        System.out.println(g.afficherCommandes("Valcou"));
    }
    
    
}