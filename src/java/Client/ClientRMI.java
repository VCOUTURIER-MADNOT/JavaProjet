package Client;

import Servlet.Gestionnaire;
 
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
        g.getProduits("Valcou");
    }
 
}