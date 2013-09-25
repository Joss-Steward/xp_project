package communication;

import java.io.IOException;
import java.net.Socket;

import communication.handlers.MessageHandlerSet;
import communication.messages.ConnectMessage;
import communication.packers.MessagePackerSet;

/**
 * All of the pieces necessary to manage the connection between the client and
 * the game server. Each side has one of these. It contains two threads (one for
 * reading the socket and one for writing to it). The incoming messages are
 * processed by a MessageProcessor (to which appropriate MessageHandlers should
 * be registered). A StateAccumulator holds a queue of the messages that should
 * go out on the outgoing connection.
 * 
 * @author merlin
 * 
 */
public class ConnectionManager
{

	private ConnectionIncoming incoming;
	private ConnectionOutgoing outgoing;
	private Thread outgoingThread;
	private Thread incomingThread;
	private Socket socket;
	private MessagePackerSet messagePackerSet;
	private MessageHandlerSet handlerSet;

	private static ConnectionManager singleton;

	/**
	 * Create everything necessary for building messages to send to the other
	 * side and handling messages that come from the other side.
	 * 
	 * @param sock
	 *            the socket connection we are managing
	 * @param handlerSet
	 *            the set of MessageHandlers hat will process the incoming
	 *            messages on this connection
	 * @param messagePackerSet
	 *            the set of MessagePackers that will pack notifications from
	 *            the model into outgoing messages
	 * @throws IOException
	 *             caused by socket issues
	 */
	public void createInitialConnection(Socket sock, MessageHandlerSet handlerSet,
			MessagePackerSet messagePackerSet) throws IOException
	{
		this.socket = sock;
		this.messagePackerSet = messagePackerSet;
		this.handlerSet = handlerSet;

		outgoing = new ConnectionOutgoing(sock, messagePackerSet);
		outgoingThread = new Thread(outgoing);
		// for simplictly
		// T.setDaemon(true);
		outgoingThread.start();

		incoming = new ConnectionIncoming(sock, handlerSet);
		incomingThread = new Thread(incoming);
		// for simplictly
		// T.setDaemon(true);
		incomingThread.start();

	}

	/**
	 * Used by the client change which server we are connected to
	 * 
	 * @param sock
	 *            the new socket
	 * @param userID
	 *            the userid we were given to connect
	 * @param pin
	 *            the pin we were given to connect
	 * @throws IOException
	 *             shouldn't
	 */
	public void moveToNewSocket(Socket sock, int userID, int pin) throws IOException
	{

		disconnect();
		this.socket = sock;

		outgoing = new ConnectionOutgoing(sock, messagePackerSet);
		outgoingThread = new Thread(outgoing);
		// for simplictly
		// T.setDaemon(true);
		outgoingThread.start();
		getStateAccumulator().queueMessage(new ConnectMessage(userID, pin));

		incoming = new ConnectionIncoming(sock, handlerSet);
		incomingThread = new Thread(incoming);
		// for simplictly
		// T.setDaemon(true);
		incomingThread.start();

	}

	/**
	 * 
	 * @return the state accumulator that is queuing messages for the outgoing
	 *         part of this connection
	 */
	public StateAccumulator getStateAccumulator()
	{
		return outgoing.stateAccumulator;
	}

	/**
	 * Kill the threads and let go of the socket
	 */
	public void disconnect()
	{
		try
		{
			socket.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		incomingThread.interrupt();
		outgoingThread.interrupt();

	}

	/**
	 * @return the only one of these that exists
	 */
	public synchronized static ConnectionManager getSingleton()
	{
		if (singleton == null)
		{
			singleton = new ConnectionManager();
		}
		return singleton;
	}

	/**
	 * reset the singleton for testing purposes
	 */
	public static void resetSingleton()
	{
		singleton = null;
	}
}
