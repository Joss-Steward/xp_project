package communication;
import java.io.IOException;
import java.net.Socket;


/**
 * All of the pieces necessary to manage the connection between the client and the game server.  Each side has one of these.
 * It contains two threads (one for reading the socket and one for writing to it).  The incoming messages are processed by a MessageProcessor (to which
 * appropriate MessageHandlers should be registered).  A StateAccumulator holds a queue of the messages that should go out on the outgoing connection.
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

	/**
	 * Create everything necessary for building messages to send to the other side and handling messages that come from the other side.
	 * @param sock the socket connection we are managing
	 * @param handlerSet the set of MessageHandlers hat will process the incoming messages on this connection
	 * @param accumulatorConnector TODO
	 * @throws IOException caused by socket issues
	 */
	public ConnectionManager(Socket sock, MessageHandlerSet handlerSet, StateAccumulatorConnector accumulatorConnector) throws IOException
	{
		this.socket = sock;
		
		outgoing = new ConnectionOutgoing(sock, accumulatorConnector);
		outgoingThread = new Thread(outgoing);
		// for simplictly
		// T.setDaemon(true);
		outgoingThread.start();
		
		incoming = new ConnectionIncoming(sock, handlerSet, outgoing.getStateAccumulator());
		incomingThread = new Thread(incoming);
		// for simplictly
		// T.setDaemon(true);
		incomingThread.start();
		
	}

	/**
	 * 
	 * @return the state accumulator that is queuing messages for the outgoing part of this connection
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
}
