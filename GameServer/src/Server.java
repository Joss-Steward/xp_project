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
	 * @param map The map that this server will serve
	 * @param port The port to listen on
	 */
	public Server(String map, int port) 
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
	 * Run like java -jar server.jar --port=1000 map=quiznasium
	 * @param args
	 *            Main runner
	 * @throws IllegalArgumentException Thrown when the port is not given as an argument to the execution
	 */
	public static void main(String args[]) throws IllegalArgumentException
	{
		String map = null;
		Integer port = null;
		for(String arg: args)
		{
			String[] splitArg = arg.split("=");
			if(splitArg[0].equals("--port"))
			{
				port = Integer.parseInt(splitArg[1]);
			}
			else if(splitArg[0].equals("--map"))
			{
				map = splitArg[1];
			}
		}
		if(map == null)
		{
			throw new IllegalArgumentException("Map name is required to run the server. Use the --map=STRING option.");
		}
		else if(port == null)
		{
			throw new IllegalArgumentException("Port is required to run the server. Use the --port=INTEGER option.");
		}
		Server S = new Server(map, port);
		S.run();
	}
}
