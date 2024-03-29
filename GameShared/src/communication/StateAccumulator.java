package communication;

import java.util.ArrayList;

import model.QualifiedObservableReport;
import model.QualifiedObserver;
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
public class StateAccumulator implements QualifiedObserver
{

	/**
	 * need this to be visible to the tests
	 */
	protected ArrayList<Message> pendingMsgs;
	private MessagePackerSet packerSet;
	private int playerID;

	/**
	 * @param messagePackerSet the set of MessagePackers we should use to build
	 *            the outgoing messages we are queueing (may be null during
	 *            testing)
	 * 
	 */
	public StateAccumulator(MessagePackerSet messagePackerSet)
	{
		pendingMsgs = new ArrayList<Message>();
		this.packerSet = messagePackerSet;
		if (packerSet != null)
		{
			packerSet.hookUpObservationFor(this);
		}

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
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	public void receiveReport(QualifiedObservableReport arg1)
	{
		ArrayList<Message> msgs;
		try
		{
			synchronized (pendingMsgs)
			{
				msgs = packerSet.pack((QualifiedObservableReport) arg1);
				if (msgs != null)
				{
					for (Message msg : msgs)
					{
						pendingMsgs.add(msg);
					}
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
	 * @param msg the msg we want to send
	 */
	public void queueMessage(Message msg)
	{
		synchronized (pendingMsgs)
		{
			pendingMsgs.add(msg);
		}
	}

	/**
	 * @param i the playerID of the player associated with this accumulator
	 */
	public void setPlayerId(int i)
	{
		this.playerID = i;
	}

	/**
	 * @return the player ID of the player using this accumulator
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * Delete and return the first message in the accumulator (used for message
	 * sequence testing only)
	 * 
	 * @return the first message
	 */
	public Message getFirstMessage()
	{
		return pendingMsgs.remove(0);
	}

}
