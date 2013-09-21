package communication;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import communication.messages.Message;


/**
 * Responsible for communication between the server and a single connected
 * player
 * 
 * @author Merlin
 * 
 */
public class ConnectionOutgoing implements Runnable
{

	private ObjectOutputStream ostream;
	StateAccumulator stateAccumulator;
	/**
	 * @return the stateAccumulator
	 */
	public StateAccumulator getStateAccumulator()
	{
		return stateAccumulator;
	}

	private StateAccumulatorConnector stateAccumulatorConnector;

	/**
	 * @param socket
	 *            Socket being used - will be null for JUnit tests
	 * @param accumulatorConnector TODO
	 * @throws IOException
	 *             Exception thrown for invalid input or output
	 */
	public ConnectionOutgoing(Socket socket, StateAccumulatorConnector accumulatorConnector) throws IOException
	{
		if (socket != null)
		{
			this.ostream = new ObjectOutputStream(socket.getOutputStream());
		}
		this.stateAccumulator = new StateAccumulator(accumulatorConnector);
		this.stateAccumulatorConnector = accumulatorConnector;
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		try
		{
			System.out.println("Connection to client created");
			
			while (!Thread.currentThread().isInterrupted())
			{
				ArrayList<Message> msgs = stateAccumulator.getPendingMsgs();
				if (msgs.size() == 0)
				{
					Thread.sleep(100);
					continue;
				}
				System.out.println("starting to write");
				if (ostream != null)
				{
					for (Message msg : msgs)
					{
						System.out.println("Writing " + msg);
						this.ostream.writeObject(msg);
					}
				}

			}
		} catch (InterruptedException E)
		{
			stateAccumulatorConnector.destroyObserverLinks(stateAccumulator);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Outgoing thread finished");
	}

}
