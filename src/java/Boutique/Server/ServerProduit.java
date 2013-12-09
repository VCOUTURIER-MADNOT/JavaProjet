package Boutique.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerProduit extends Thread {

	private ServerSocket socketServ;
	
	public ServerProduit(InetAddress ip,int port) throws IOException
	{
		this.socketServ = new ServerSocket(port, 1000 , ip);
	}
	
	public void run()
	{
		while(true)
                {
                    try
                    {
                        Socket client = socketServ.accept();
                        TCPRequestHandler tcpClient = new TCPRequestHandler(client);
                        tcpClient.start();
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
	}
	
}
