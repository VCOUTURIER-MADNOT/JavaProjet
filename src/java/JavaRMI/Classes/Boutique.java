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
    private int             tcpPort;
    private int             udpPort;
    private Inet4Address    ip;
    private Utilisateur     admin;
    
    public Boutique(String _nom, String _loginAdmin, int _tcpPort, int _udpPort)
    {
        try {
            this.nom = _nom;
            this.ip = (Inet4Address) Inet4Address.getAllByName("0.0.0.0")[0];
            this.admin = GestionnaireUtilisateurs.getUtilisateur(_loginAdmin);
            this.tcpPort = _tcpPort;
            this.udpPort = _udpPort;
        } catch (UnknownHostException ex) {
            Logger.getLogger(Boutique.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    public int getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }

    public Inet4Address getIp() {
        return this.ip;
    }

    public void setIp(Inet4Address _ip) {
        this.ip = ip;
    }

    public String getNom() {
        return nom;
    }

    public Utilisateur getAdmin() {
        return admin;
    }
    
    public String ipToString()
    {
        return this.ip.getHostAddress();
    }
    
    public void setIpFromString(String _ipPort)
    {
        String[] split = _ipPort.split(":");
        try {
            this.ip = (Inet4Address) InetAddress.getAllByName(split[0])[0];
        } catch (UnknownHostException ex) {
            Logger.getLogger(Boutique.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String toString() {
        return "Boutique{" + "nom=" + nom + ", ip=" + ip.getHostAddress() + ", admin=" + admin + '}';
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
        if (!Objects.equals(this.admin, other.admin)) {
            return false;
        }
        return true;
    }
}
