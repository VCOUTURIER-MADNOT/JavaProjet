/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaRMI.Gestionnaires;

import JavaRMI.Interfaces.IGestionnaireBoutiques;
import JavaRMI.Interfaces.IGestionnaireUtilisateurs;
import java.net.Inet4Address;
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
    public IGestionnaireBoutiques GB;
    public IGestionnaireUtilisateurs GU;
    
    public Gestionnaire()
    {
        try {
            GU = new GestionnaireUtilisateurs();
            GB = new GestionnaireBoutiques();
        } catch (RemoteException ex) {
            Logger.getLogger(Gestionnaire.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
