package communication;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import communication.messages.Message;


/**
 * Responsible for communication between the server and a single connected
 * player
 * 
 * @author Merlin
 * 
 */
public class ConnectionIncoming implements Runnable
{

	private ObjectInputStream istream;
	private Socket socket;
	private MessageHandlerSet messageHandlers;
	private StateAccumulator stateAccumulator;
	
	/**
	 * @param socket
	 *            Socket being used.  Will be null for JUnit testing
	 * @param processor the message processor which should handle messages that come in via this connection
	 * @param stateAccumulator the accumulator associated with the outgoing portion of this connection
	 * @throws IOException
	 *             Exception thrown for invalid input or output
	 */
	public ConnectionIncoming(Socket socket, MessageHandlerSet processor, StateAccumulator stateAccumulator) throws IOException
	{
		
		this.socket = socket;
		this.messageHandlers = processor;
		this.stateAccumulator = stateAccumulator;
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		try
		{	
			if (socket != null)
			{
				this.istream = new ObjectInputStream(socket.getInputStream());
			}
			while (!Thread.currentThread().isInterrupted())
			{
				if (socket != null)
				{
					if (socket.getInputStream().available() == 0)
					{
						Thread.sleep(100);
						continue;
					}
				} else
				{
					Thread.sleep(100);
					continue;
				}
				System.out.println("starting to read from "+ socket);
				if (socket != null)
				{
					Object inputObject = this.istream.readObject();
					// this.ostream.write(bytes,0,read);

					System.out.println("processing:  ");
					System.out.println(inputObject.getClass());

					processRequest((Message) inputObject);
				}
				

			}
		} catch (InterruptedException E)
		{
			// ok - we just need to quit
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Incoming thread finished");
	}

	protected void processRequest(Message request)
	{
		try
		{
			messageHandlers.process(request, stateAccumulator);
		} catch (CommunicationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
