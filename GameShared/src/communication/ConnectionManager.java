package communication;

import java.io.IOException;
import java.net.Socket;

import model.OptionsManager;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.LogoutReport;
import model.reports.PlayerDisconnectedReport;
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
public class ConnectionManager implements QualifiedObserver
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
	 * @param sock the socket connection we are managing
	 * @param stateAccumulator the accumulator connecting us to the rest of the
	 *            system
	 * @param messageHandlerSet the set of MessageHandlers hat will process the
	 *            incoming messages on this connection
	 * @param messagePackerSet the set of MessagePackers that will pack
	 *            notifications from the model into outgoing messages
	 * @param watchForSocketClosure true if we should shut things down when the
	 *            socket gets closed by the other side
	 * 
	 * @throws IOException caused by socket issues
	 */
	public ConnectionManager(Socket sock, StateAccumulator stateAccumulator, MessageHandlerSet messageHandlerSet,
			MessagePackerSet messagePackerSet, boolean watchForSocketClosure) throws IOException
	{
		System.out.println("Starting new ConnectionManager");
		this.socket = sock;
		this.messagePackerSet = messagePackerSet;
		this.handlerSet = messageHandlerSet;

		outgoing = new ConnectionOutgoing(sock, stateAccumulator, messagePackerSet);
		outgoingThread = new Thread(outgoing);
		// for simplictly
		// T.setDaemon(true);
		outgoingThread.start();
		this.stateAccumulator = stateAccumulator;
		messageHandlerSet.setConnectionManager(this);

		incoming = new ConnectionIncoming(sock, messageHandlerSet);
		incomingThread = new Thread(incoming);
		// for simplictly
		// T.setDaemon(true);
		incomingThread.start();

		if (watchForSocketClosure)
		{
			ConnectionListener cl = new ConnectionListener(outgoing.getStream(), 5000);
			cl.setDisconnectionAction(new Runnable()
			{
				public void run()
				{
					disconnect();
					QualifiedObservableConnector.getSingleton()
							.sendReport(new PlayerDisconnectedReport(stateAccumulator.getPlayerID()));
				}
			});
			watcherThread = new Thread(cl);
			watcherThread.start();
		}
		QualifiedObservableConnector.getSingleton().registerObserver(this, LogoutReport.class);
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
	 * @param sock the new socket
	 * @throws IOException shouldn't
	 */
	public void moveToNewSocket(Socket sock) throws IOException
	{
		if (!OptionsManager.getSingleton().isTestMode())
		{
			disconnect();
			this.socket = sock;

			outgoing = new ConnectionOutgoing(sock, this.stateAccumulator, messagePackerSet);
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

	}

	/**
	 * Used by the client change which server we are connected to
	 * 
	 * @param sock the new socket
	 * @param playerID the playerID we were given to connect
	 * @param pin the pin we were given to connect
	 * @throws IOException shouldn't
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
		return outgoing.getStateAccumulator();
	}

	/**
	 * Kill the threads and let go of the socket
	 */
	public void disconnect()
	{
		try
		{
			System.out.println("Trying to disconnect from our current socket");
			socket.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
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
	 * @param playerID the playerID
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

	/**
	 * Receives a logout report and connects to the login server
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		if (report.getClass() == LogoutReport.class)
		{
			try
			{

				moveToNewSocket(new Socket(OptionsManager.getSingleton().getLoginHost(), 1871));

			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
