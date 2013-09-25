import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import communication.ConnectionManager;
import communication.LoginMessageHandler;
import communication.MessageHandlerSet;
import communication.messages.LoginMessage;
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
	/**
	 * 
	 */
	public LoginServer()
	{
		initializeMessageHandlers();
	}

	private void initializeMessageHandlers()
	{
		handlers = new MessageHandlerSet();
		handlers.registerHandler(LoginMessage.class, new LoginMessageHandler());
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
				ConnectionManager.getSingleton().createInitialConnection(sock, handlers, new MessagePackerSet());
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