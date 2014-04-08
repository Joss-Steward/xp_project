import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import communication.ConnectionManager;
import communication.StateAccumulator;
import communication.handlers.MessageHandlerSet;
import communication.packers.MessagePackerSet;

/**
 * A daemon that resides on the server listening to the gigabuds and to client
 * requests
 * 
 * @author Merlin
 * 
 */
public class Server implements Runnable
{
	private ServerSocket servSock;
	private int port;
	
	/**
	 * Create a new Server listening on a given port
	 * @param port The port to listen on
	 */
	public Server(int port) 
	{
		this.port = port;
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		int i = 0;
		try
		{
			servSock = new ServerSocket(port, 10);
			while (true)
			{
				System.out.println("Listening on port " + port);
				Socket sock = servSock.accept();
				System.out.println(i + ":  got something from " + sock);
				i++;
				MessagePackerSet messagePackerSet = new MessagePackerSet();
				StateAccumulator stateAccumulator = new StateAccumulator(messagePackerSet);
				
				new ConnectionManager(sock, new MessageHandlerSet(stateAccumulator),
						messagePackerSet);
			}

		} catch (Throwable e)
		{
			e.printStackTrace();
		}
	}

	protected void finalize()
	{
		try
		{
			servSock.close();
		} catch (IOException e)
		{
			System.out.println("Could not close socket");
			System.exit(-1);
		}
	}

	/**
	 * @param args
	 *            Main runner
	 * @throws IllegalArgumentException Thrown when the port is not given as an argument to the execution
	 */
	public static void main(String args[]) throws IllegalArgumentException
	{
		if(args.length != 1) {
			throw new IllegalArgumentException("Port is required to run the server. Run the server like 'java server 1872'");
		}
		int port = Integer.parseInt(args[0]);
		Server S = new Server(port);
		S.run();
	}
}
