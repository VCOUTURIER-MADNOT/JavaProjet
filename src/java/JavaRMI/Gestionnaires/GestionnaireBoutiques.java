/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaRMI.Gestionnaires;

import JavaRMI.Classes.Boutique;
import JavaRMI.Classes.Utilisateur;
import JavaRMI.Interfaces.IGestionnaireBoutiques;
import JavaRMI.Interfaces.IGestionnaireUtilisateurs;
import JavaRMI.NotifyLists.BoutiqueNotifyList;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
    public Boutique getBoutiqueByAdmin(String _loginBoutiqueAdmin) throws RemoteException 
    {
        for(Boutique b : boutiques)
        {
            if(b.getAdmin().getLogin().equals(_loginBoutiqueAdmin))
            {
                return b;
            }
        }
        
        return null;
    }
    
    @Override
    public Boutique getBoutiqueByName(String _nomBoutique) throws RemoteException 
    {
        for(Boutique b : boutiques)
        {
            if(b.getNom().equals(_nomBoutique))
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
    public void creerBoutique(String _loginAdmin, int _tcpPort, int _udpPort) throws RemoteException {
        Boutique b = new Boutique(_loginAdmin, _tcpPort, _udpPort);
        boutiques.add(b);
    }

    @Override
    public void supprimerBoutique(String _nomBoutique, String _loginAdmin) throws RemoteException {
        Boutique b = null;
        Gestionnaire ge = new Gestionnaire();
        Utilisateur u = ge.GU.getUtilisateur(_loginAdmin);
        if((b = getBoutiqueByName(_nomBoutique)) != null)
        {
            if(b.getAdmin().equals(u) || u.isAdmin())
            {
                boutiques.remove(b);
            }
        }
    }
    
}
