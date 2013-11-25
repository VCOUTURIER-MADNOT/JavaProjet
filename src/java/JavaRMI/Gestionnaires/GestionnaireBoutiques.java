/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaRMI.Gestionnaires;

import JavaRMI.Classes.Boutique;
import JavaRMI.Interfaces.IGestionnaireBoutiques;
import JavaRMI.NotifyLists.BoutiqueNotifyList;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author valentin
 */
public class GestionnaireBoutiques extends UnicastRemoteObject implements IGestionnaireBoutiques{

    private static BoutiqueNotifyList boutiques = new BoutiqueNotifyList();
    
    public GestionnaireBoutiques() throws RemoteException
    {
        super();
    }
    
    @Override
    public Boutique getBoutique(String _ipPort) throws RemoteException 
    {
        for(Boutique b : boutiques)
        {
            if(b.ipPortToString().equals(_ipPort))
            {
                return b;
            }
        }
        
        return null;
    }

    @Override
    public ArrayList<Boutique> getBoutiques() throws RemoteException {
        return (ArrayList<Boutique>) boutiques;
    }

    @Override
    public void creerBoutique(String _nom, String _loginAdmin) throws RemoteException {
        Boutique b = new Boutique(_nom, _loginAdmin);
        boutiques.add(b);
    }

    @Override
    public void supprimerBoutique(String _ipPort) throws RemoteException {
        Boutique b = null;
        if((b = getBoutique(_ipPort)) != null)
        {
            boutiques.remove(b);
        }
    }
    
}
