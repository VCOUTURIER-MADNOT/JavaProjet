package Boutique.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerProduit extends Thread {

	private RequestHandler rh;
	private ServerSocket socketServ;
	
	public ServerProduit(RequestHandler _rh, int port) throws IOException
	{
		rh = _rh;
		socketServ = new ServerSocket(port);
	}
	
	public void run()
	{
		while(true)
			try {
				rh.register(socketServ.accept());
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}
