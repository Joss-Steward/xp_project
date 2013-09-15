package communication;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import model.QualifiedObservableReport;
import communication.messages.Message;
//TODO modify this to have independent interpretation of updates into messages



/**
 * Manages a queue of the events that have occurred that have not been sent to the other side of our connection
 * (if it is on the client, it holds messages that should go to the server and vice versa)
 * @author merlin
 *
 */
public class StateAccumulator implements Observer
{

	ArrayList<Message> pendingMsgs;
	private MessagePackerSet packerSet;
	
	/**
	 * 
	 * @param conn An object that will register this object as an observer of all of the appropriate places.  This is using the
	 * strategy pattern because the places we need to observe will be different on the client than on the server
	 */
	public StateAccumulator(StateAccumulatorConnector conn)
	{
		pendingMsgs = new ArrayList<Message> ();
		this.packerSet = conn.getMessagePackerSet();
		conn.setUpObserverLinks(this);
	}
	
	/**
	 * Gives a list of all of the messages that are pending and empties this accumulator
	 * @return the current pending messages
	 */
	public synchronized ArrayList<Message> getPendingMsgs()
	{
		ArrayList<Message> temp = pendingMsgs;
		pendingMsgs = new ArrayList<Message> ();
		return temp;
	}

	/**
	 * @see #Observer.update(Observable arg0, Object arg1)
	 */
	@Override
	public void update(Observable arg0, Object arg1)
	{
		Message msg;
		try
		{
			msg = packerSet.pack( (QualifiedObservableReport) arg1);
			if (msg!= null)
			{
				pendingMsgs.add(msg);
			}
		} catch (CommunicationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
