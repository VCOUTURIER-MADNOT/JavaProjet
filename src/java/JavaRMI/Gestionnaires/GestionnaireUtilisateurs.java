package JavaRMI.Gestionnaires;

import JavaRMI.Classes.Utilisateur;
import JavaRMI.Interfaces.IGestionnaireUtilisateurs;
import JavaRMI.NotifyLists.UtilisateurNotifyList;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
        if(this.getUtilisateur(_loginAdmin).isAdmin() || _login.equals(_loginAdmin))
        {
            unl.remove(getUtilisateur(_login));
        }
    }
    
    public Utilisateur getUtilisateur(String _login) throws RemoteException
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

    @Override
    public boolean isValidAuthentication(String _login, String _password) throws RemoteException {
        Utilisateur u = this.getUtilisateur(_login);
        if(u != null)
        {
            return u.getPassword().equals(_password);
        }
        else
        {
            return false;
        }
    }
}
