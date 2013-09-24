import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import communication.ConnectMessageHandler;
import communication.ConnectionManager;
import communication.MessageHandlerSet;
import communication.MessagePackerSetServer;
import communication.messages.ConnectMessage;
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
	private MessageHandlerSet handlers;
	/**
	 * 
	 */
	public Server()
	{
		initializeMessageHandlers();
	}

	private void initializeMessageHandlers()
	{
		handlers = new MessageHandlerSet();
		handlers.registerHandler(ConnectMessage.class, new ConnectMessageHandler());
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		int i = 0;
		try
		{
			servSock = new ServerSocket(1872, 10);
			while (true)
			{
				System.out.println("Listening");
				Socket sock = servSock.accept();
				System.out.println(i + ":  got something from " + sock);
				i++;
				ConnectionManager.getSingleton().createInitialConnection(sock, handlers, new MessagePackerSetServer());
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
		Server S = new Server();
		S.run();
	}
}
