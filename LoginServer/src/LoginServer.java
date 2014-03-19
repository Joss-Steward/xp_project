import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import communication.ConnectionManager;
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
			while (true)
			{
				System.out.println("Login Server Listening");
				Socket sock = servSock.accept();
				System.out.println(i + ":  got something from " + sock);
				i++;
				handlers = new MessageHandlerSet();
				packers = new MessagePackerSet();
				new ConnectionManager(sock, handlers, packers);
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
	 */
	public static void main(String args[])
	{
		LoginServer S = new LoginServer();
		S.run();
	}
}
