/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaRMI.Gestionnaires;

import JavaRMI.Classes.Utilisateur;
import JavaRMI.Interfaces.IGestionnaireUtilisateurs;
import JavaRMI.NotifyLists.UtilisateurNotifyList;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Valentin
 */
public class GestionnaireUtilisateurs extends UnicastRemoteObject implements IGestionnaireUtilisateurs{
    
    private static UtilisateurNotifyList unl = new UtilisateurNotifyList();
    
    public GestionnaireUtilisateurs() throws RemoteException
    {
        super();
    }
    
    public void inscrire(String _login, String _password, String _nom) throws RemoteException
    {
        if(getUtilisateur(_login) == null)
        {
            unl.add(new Utilisateur(_login, _password, _nom, 2));
        }
    }
    
    public void desinscrire(String _login, String _loginAdmin) throws RemoteException
    {
        if(getUtilisateur(_loginAdmin).isAdmin())
        {
            unl.remove(getUtilisateur(_login));
        }
    }
    
    public static Utilisateur getUtilisateur(String _login)
    {
        int i = 0;
        Utilisateur utilisateur = null;
        while(i < unl.size() && utilisateur == null)
        {
                if (unl.get(i).getLogin().equals(_login))
                {
                        System.out.println(i);
                        utilisateur = unl.get(i);
                }
                i++;
        }

        return utilisateur;
    }
}
