package communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import communication.messages.Message;
import communication.packers.MessagePackerSet;

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
	 * @param socket
	 *            Socket being used - will be null for JUnit tests
	 * @param messagePackerSet
	 *            the set of messagepackers the outgoing connection should use
	 * @throws IOException
	 *             Exception thrown for invalid input or output
	 */
	public ConnectionOutgoing(Socket socket, MessagePackerSet messagePackerSet) throws IOException
	{
		if (socket != null)
		{
			this.ostream = new ObjectOutputStream(socket.getOutputStream());
		}
		this.stateAccumulator = new StateAccumulator(messagePackerSet);

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
			// TODO need to get rid of observation links when we are getting
			// torn down
			// messagePackerSet.destroyObserverLinks(stateAccumulator);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Outgoing thread finished");
	}

}
