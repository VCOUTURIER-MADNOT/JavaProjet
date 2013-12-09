package Boutique.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerCommande extends Thread {
    
	private DatagramSocket ds;
	
	public ServerCommande(InetAddress ip, int port) throws IOException
        {
		this.ds = new DatagramSocket(port, ip);
	}
	
        @Override
	public void run()
	{
		while(true)
		{
			byte[] byteToPacket = new byte[1024];
			
			DatagramPacket dp = new DatagramPacket(byteToPacket, byteToPacket.length);
			
			try {
				ds.receive(dp);
                                UDPRequestHandler udpClient = new UDPRequestHandler();
                                udpClient.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
