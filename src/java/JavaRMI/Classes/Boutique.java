/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JavaRMI.Classes;

import JavaRMI.Gestionnaires.GestionnaireUtilisateurs;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author valentin
 */
public class Boutique {

    private String          nom;
    private Inet4Address    ip;
    private int             port;
    private Utilisateur     admin;
    
    public Boutique(String _nom, String _loginAdmin)
    {
        this.nom = _nom;
        this.ip = null;
        this.port = 0;
        //this.admin = GestionnaireUtilisateurs.getUtilisateur(_loginAdmin);
    }

    public Inet4Address getIp() {
        return this.ip;
    }

    public void setIp(Inet4Address _ip) {
        this.ip = ip;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int _port) {
        this.port = port;
    }

    public String getNom() {
        return nom;
    }

    public Utilisateur getAdmin() {
        return admin;
    }
    
    public String ipPortToString()
    {
        return this.ip.getHostAddress() + ":" + this.port;
    }
    
    public void setIpPortFromString(String _ipPort)
    {
        String[] split = _ipPort.split(":");
        try {
            this.ip = (Inet4Address) InetAddress.getAllByName(split[0])[0];
            this.port = Integer.parseInt(split[1]);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Boutique.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String toString() {
        return "Boutique{" + "nom=" + nom + ", ip=" + ip.getHostAddress() + ", port=" + port + ", admin=" + admin + '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Boutique other = (Boutique) obj;
        if (!Objects.equals(this.ip, other.ip)) {
            return false;
        }
        if (this.port != other.port) {
            return false;
        }
        return true;
    }
    
    public static void main(String[] args)
    {
        Boutique b = new Boutique("Essai", "Coutcout");
        b.setIpPortFromString("192.168.0.1:99");
        
        System.out.println(b);
        System.out.println(b.ipPortToString());
    }
}
