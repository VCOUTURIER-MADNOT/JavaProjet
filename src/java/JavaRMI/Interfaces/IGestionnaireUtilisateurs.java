package JavaRMI.Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGestionnaireUtilisateurs extends Remote{
    public void inscrire(String _login, String _password, String _nom) throws RemoteException;
    public void desinscrire(String _login, String _loginAdmin) throws RemoteException;
}
