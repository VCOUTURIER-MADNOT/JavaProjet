package JavaRMI;

import JavaRMI.Gestionnaires.GestionnaireBoutiques;
import JavaRMI.Gestionnaires.GestionnaireUtilisateurs;
import java.net.Inet4Address;
import java.rmi.AlreadyBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

 
/**
 * Serveur qui cree une personne et la met a disposition des clients.
 * @author Cyril Rabat
 * @version 08/10/2013
 */
public class ServeurRMI {
 
	public static Registry registry;
    /**
     * Methode principale.
     * @param args inutilise
     */
    public static void main(String[] args) {
     
    	try {
            registry = LocateRegistry.createRegistry(12345);
            GestionnaireBoutiques GB = new GestionnaireBoutiques();
            GestionnaireUtilisateurs GU = new GestionnaireUtilisateurs();
            // En pratique, on devrait remplacer le localhost par l'adresse IP externe de la boutique mais pour des soucis de test, nous utilisons localhost
            registry.bind("rmi://localhost/JavaRMI/GestionnaireBoutiques", GB);
            registry.bind("rmi://localhost/JavaRMI/GestionnaireUtilisateurs", GU);
            System.out.println("Serveur démarré ! ");
	} catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
 
}