/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaRMI.Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author valentin
 */
public interface IGestionnaireUtilisateurs extends Remote{
    public void inscrire(String _login, String _password, String _nom) throws RemoteException;
    public void desinscrire(String _login, String _loginAdmin) throws RemoteException;
}
