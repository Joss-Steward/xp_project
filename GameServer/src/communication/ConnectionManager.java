package communication;

import java.io.IOException;
import java.net.Socket;

import model.OptionsManager;
import communication.handlers.MessageHandlerSet;
import communication.messages.ConnectMessage;
import communication.messages.DisconnectMessage;
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
	private Thread watcherThread;
	private Socket socket;
	private MessagePackerSet messagePackerSet;
	private MessageHandlerSet handlerSet;
	/**
	 * 
	 */
	StateAccumulator stateAccumulator;
	private int playerID;

	/**
	 * Create everything necessary for building messages to send to the other
	 * side and handling messages that come from the other side.
	 * 
	 * @param sock
	 *            the socket connection we are managing
	 * @param stateAccumulator
	 *            the accumulator that will send our responses
	 * @param messageHandlerSet
	 *            the set of MessageHandlers hat will process the incoming
	 *            messages on this connection
	 * @param messagePackerSet
	 *            the set of MessagePackers that will pack notifications from
	 *            the model into outgoing messages
	 * @throws IOException
	 *             caused by socket issues
	 */
	public ConnectionManager(Socket sock, StateAccumulator stateAccumulator,
			MessageHandlerSet messageHandlerSet, MessagePackerSet messagePackerSet)
			throws IOException
	{
		System.out.println("Starting new ConnectionManager");
		this.socket = sock;

		this.messagePackerSet = messagePackerSet;
		this.handlerSet = messageHandlerSet;

		outgoing = new ConnectionOutgoing(sock, stateAccumulator, messagePackerSet);
		outgoingThread = new Thread(outgoing);
		// for simplicity
		// T.setDaemon(true);
		outgoingThread.start();
		this.stateAccumulator = stateAccumulator;
		messageHandlerSet.setConnectionManager(this);

		incoming = new ConnectionIncoming(sock, messageHandlerSet);
		incomingThread = new Thread(incoming);
		// for simplictly
		// T.setDaemon(true);
		incomingThread.start();

		ConnectionListener cl = new ConnectionListener(outgoing.getStream(), 5000);
		cl.setDisconnectionAction(new Runnable()
		{
			public void run()
			{
				disconnect();
			}
		});
		watcherThread = new Thread(cl);
		watcherThread.start();
	}

	/**
	 * For testing purposes only
	 */
	public ConnectionManager()
	{
	}

	/**
	 * Used by the client change which server we are connected to
	 * 
	 * @param sock
	 *            the new socket
	 * @param playerID
	 *            the playerID we were given to connect
	 * @param pin
	 *            the pin we were given to connect
	 * @throws IOException
	 *             shouldn't
	 */
	public void moveToNewSocket(Socket sock, int playerID, double pin) throws IOException
	{
		disconnect();
		this.socket = sock;

		outgoing = new ConnectionOutgoing(sock, this.stateAccumulator, messagePackerSet);
		outgoingThread = new Thread(outgoing);
		// for simplictly
		// T.setDaemon(true);
		outgoingThread.start();
		stateAccumulator.queueMessage(new ConnectMessage(playerID, pin));

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
		return stateAccumulator;
	}

	/**
	 * Kill the threads and let go of the socket
	 */
	public void disconnect()
	{
		try
		{
			if (!OptionsManager.getSingleton().isTestMode())
			{
				System.out.println("Trying to disconnect from our current socket");
				socket.close();
			}
			handlerSet.process(new DisconnectMessage(playerID));
		} catch (IOException | CommunicationException e)
		{
			e.printStackTrace();
		}

		watcherThread.interrupt();
		incomingThread.interrupt();
		outgoingThread.interrupt();
	}

	/**
	 * @return the player ID for the player connected through this connection
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * set the playerID for the player connected through this connection
	 * 
	 * @param playerID
	 *            the playerID
	 * 
	 */
	public void setPlayerID(int playerID)
	{
		this.playerID = playerID;
		if (stateAccumulator != null)
		{
			stateAccumulator.setPlayerId(playerID);
		}
	}
}
