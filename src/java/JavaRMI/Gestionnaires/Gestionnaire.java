/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaRMI.Gestionnaires;

import JavaRMI.Interfaces.IGestionnaireBoutiques;
import JavaRMI.Interfaces.IGestionnaireUtilisateurs;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
            Registry registry = LocateRegistry.getRegistry(12345);
            GU = (IGestionnaireUtilisateurs) registry.lookup("rmi://127.0.0.1:12345/JavaRMI/GestionnaireUtilisateurs");
            GB = (IGestionnaireBoutiques) registry.lookup("rmi://127.0.0.1:12345/JavaRMI/GestionnaireBoutiques");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
}
