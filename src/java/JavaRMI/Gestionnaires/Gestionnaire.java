/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaRMI.Gestionnaires;

import JavaRMI.Interfaces.IGestionnaireBoutiques;
import JavaRMI.Interfaces.IGestionnaireUtilisateurs;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author valentin
 */
public class Gestionnaire {
    
    private static IGestionnaireUtilisateurs GU;
    private static IGestionnaireBoutiques GB;
    
    public Gestionnaire()
    {
        try {
            Registry registry = LocateRegistry.getRegistry(12345);
            GU = (IGestionnaireUtilisateurs) registry.lookup("/JavaRMI/GestionnaireUtilisateurs");
            GB = (IGestionnaireBoutiques) registry.lookup("/JavaRMI/GestionnaireBoutiques");
        } catch (RemoteException ex) {
            Logger.getLogger(Gestionnaire.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Gestionnaire.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void inscrire(String _login, String _mdp, String _name)
    {
        try {
            GU.inscrire(_login, _mdp, _name);
        } catch (RemoteException ex) {
            Logger.getLogger(Gestionnaire.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void desinscrire(String _login, String _loginExecuteur)
    {
        try {
            GU.desinscrire(_login, _loginExecuteur);
        } catch (RemoteException ex) {
            Logger.getLogger(Gestionnaire.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void creerBoutique(String _nomBoutique, String _loginAdmin, int _tcpPort, int _udpPort)
    {
        try {
            GB.creerBoutique(_nomBoutique, _loginAdmin, _tcpPort, _udpPort);
        } catch (RemoteException ex) {
            Logger.getLogger(Gestionnaire.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void supprimerBoutique(String _ipPortBoutique, String _loginAdmin)
    {
        try {
            GB.supprimerBoutique(_ipPortBoutique, _loginAdmin);
        } catch (RemoteException ex) {
            Logger.getLogger(Gestionnaire.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
