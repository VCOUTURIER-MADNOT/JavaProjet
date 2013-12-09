/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Boutique.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URL;

/**
 *
 * @author valentin
 */
public class Boutique {
    private InetAddress ip;
    private ServerProduit tcpServer;
    private ServerCommande udpServer;
    private String nom;
    private String loginAdmin;
    
    public Boutique(String _nom, String _loginAdmin)
    {
        this.nom = _nom;
        this.loginAdmin = _loginAdmin;
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com/");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            String ip = in.readLine(); 
            
            this.ip = InetAddress.getByName(ip);
            this.tcpServer = new ServerProduit(this.ip, 65432);
            this.udpServer = new ServerCommande(this.ip,65433);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Boutique(String _nom, String _loginAdmin, int _tcpPort, int _udpPort)
    {
        this.nom = _nom;
        this.loginAdmin = _loginAdmin;
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com/");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            String ip = in.readLine(); 
            
            this.ip = InetAddress.getByName(ip);
            this.tcpServer = new ServerProduit(this.ip, _tcpPort);
            this.udpServer = new ServerCommande(this.ip, _udpPort);
        } catch (Exception ex) {
            ex.printStackTrace();
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
