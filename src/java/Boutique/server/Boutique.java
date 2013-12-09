/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Boutique.server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author valentin
 */
public class Boutique {
    private Inet4Address ip;
    private ServerProduit tcpServer;
    private ServerCommande udpServer;
    private String nom;
    private String loginAdmin;
    private static RequestHandler rh;
    
    public Boutique(String _nom, String _loginAdmin)
    {
        this.nom = _nom;
        this.loginAdmin = _loginAdmin;
        try {
            this.ip = (Inet4Address) Inet4Address.getLocalHost();
            this.tcpServer = new ServerProduit(rh, 65432);
            this.udpServer = new ServerCommande(rh, 65433);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Boutique.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Boutique.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Boutique(String _nom, String _loginAdmin, int _tcpPort, int _udpPort)
    {
        this.nom = _nom;
        this.loginAdmin = _loginAdmin;
        try {
            this.ip = (Inet4Address) Inet4Address.getLocalHost();
            this.tcpServer = new ServerProduit(rh, _tcpPort);
            this.udpServer = new ServerCommande(rh, _udpPort);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Boutique.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Boutique.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void start()
    {
        this.tcpServer.start();
        this.udpServer.start();
    }
    
    public static void main(String[] args)
    {
        Boutique boutique = new Boutique("Boutique de Valcou", "Valcou");
        boutique.start();
    }
    
    
}
