import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.OptionsManager;
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
public class LoginServer implements Runnable
{
	private ServerSocket servSock;
	private MessageHandlerSet handlers;
	private MessagePackerSet packers;

	/**
	 * 
	 */
	public LoginServer()
	{
		initializeMessageHandlers();
	}

	private void initializeMessageHandlers()
	{

	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		int i = 0;
		try
		{
			servSock = new ServerSocket(1871, 10);
			OptionsManager.getSingleton(false);
			while (true)
			{
				System.out.println("Login Server Listening");
				Socket sock = servSock.accept();
				System.out.println(i + ":  got something from " + sock);
				i++;
				packers = new MessagePackerSet();
				StateAccumulator stateAccumulator = new StateAccumulator(packers);
				
				handlers = new MessageHandlerSet(stateAccumulator);
				new ConnectionManager(sock, stateAccumulator,
						handlers, packers);

			}

		} catch (Throwable e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
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
	 */
	public static void main(String args[])
	{
		for(String arg: args)
		{
			String[] splitArg = arg.split("=");
			if(splitArg[0].equals("--localhost"))
			{
				OptionsManager.getSingleton(false);
			}
		}
		LoginServer S = new LoginServer();
		S.run();
	}
}
