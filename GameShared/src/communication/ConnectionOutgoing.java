package communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
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
	 * @param stateAccumulator TODO
	 * @param messagePackerSet
	 *            the set of messagepackers the outgoing connection should use
	 * @throws IOException
	 *             Exception thrown for invalid input or output
	 */
	public ConnectionOutgoing(Socket socket, StateAccumulator stateAccumulator, MessagePackerSet messagePackerSet)
			throws IOException
	{
		if (socket != null)
		{
			this.ostream = new ObjectOutputStream(socket.getOutputStream());
		}
		this.stateAccumulator = stateAccumulator;

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
						System.out.println(this + " Writing " + msg);
						try
						{
							this.ostream.writeObject(msg);
						} catch (SocketException e)
						{
							System.out.println("Write failed");
							cleanUpAndExit();
						}
					}
				}

			}
		} catch (InterruptedException E)
		{
			cleanUpAndExit();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Outgoing thread finished");
	}

	/**
	 * 
	 */
	private void cleanUpAndExit()
	{
		// TODO need to unobserve all of the things from the message packers

	}

	/**
	 * @return the state accumulator associated with this outgoing connecting
	 */
	public StateAccumulator getStateAccumulator()
	{
		return stateAccumulator;
	}

}
