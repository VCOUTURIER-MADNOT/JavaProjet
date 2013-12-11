/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet.Interfaces;

import JavaRMI.Classes.Boutique;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author valentin
 */
public interface IGestionnaireBoutiques extends Remote{
    public void creerBoutique(String _nom, String _loginAdmin, int _tcpPort, int _udpPort) throws RemoteException;
    public void supprimerBoutique(String _ipPort, String _loginAdmin) throws RemoteException;
    public Boutique getBoutique(String _ipPort) throws RemoteException;
    public ArrayList<Boutique> getBoutiques() throws RemoteException;
}
