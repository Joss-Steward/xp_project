package communication;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import model.QualifiedObservableReport;
import communication.messages.Message;
import communication.packers.MessagePackerSet;

/**
 * Manages a queue of the events that have occurred that have not been sent to
 * the other side of our connection (if it is on the client, it holds messages
 * that should go to the server and vice versa)
 * 
 * @author merlin
 * 
 */
public class StateAccumulator implements Observer
{

	// need this to be visible to the tests
	protected ArrayList<Message> pendingMsgs;
	private MessagePackerSet packerSet;

	/**
	 * Only used for tests
	 * 
	 * @return the packerSet
	 */
	public MessagePackerSet getPackerSet()
	{
		return packerSet;
	}

	/**
	 * @param messagePackerSet
	 *            the set of MessagePackers we should use to build the outgoing
	 *            messages we are queueing
	 * 
	 */
	public StateAccumulator(MessagePackerSet messagePackerSet)
	{
		pendingMsgs = new ArrayList<Message>();
		this.packerSet = messagePackerSet;
		packerSet.hookUpObservationFor(this);

	}

	/**
	 * Gives a list of all of the messages that are pending and empties this
	 * accumulator
	 * 
	 * @return the current pending messages
	 */
	public synchronized ArrayList<Message> getPendingMsgs()
	{
		ArrayList<Message> temp = pendingMsgs;
		pendingMsgs = new ArrayList<Message>();
		return temp;
	}

	/**
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1)
	{
		Message msg;
		try
		{
			synchronized (pendingMsgs)
			{
				msg = packerSet.pack((QualifiedObservableReport) arg1);
				if (msg != null)
				{
					pendingMsgs.add(msg);
				}
			}
		} catch (CommunicationException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Force a specific message to be put into the queue
	 * 
	 * @param msg
	 *            the msg we want to send
	 */
	public void queueMessage(Message msg)
	{
		synchronized (pendingMsgs)
		{
			pendingMsgs.add(msg);
		}
	}
}
