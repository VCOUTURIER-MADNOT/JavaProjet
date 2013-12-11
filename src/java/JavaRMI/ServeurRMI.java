package JavaRMI;

import JavaRMI.Gestionnaires.GestionnaireBoutiques;
import JavaRMI.Gestionnaires.GestionnaireUtilisateurs;
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
            registry.bind("rmi://127.0.0.1:12345/JavaRMI/GestionnaireBoutiques", GB);
            registry.bind("rmi://127.0.0.1:12345/JavaRMI/GestionnaireUtilisateurs", GU);
            System.out.println("Serveur démarré ! ");
	} catch (RemoteException e) {
            e.printStackTrace();
	}   catch (AlreadyBoundException ex) {
            Logger.getLogger(ServeurRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
}