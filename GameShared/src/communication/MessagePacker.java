package communication;
import java.util.Observable;

import communication.messages.Message;


/**
 * These classes translates events in the system (as reported by Observables) into Messages
 * @author merlin
 *
 */
public interface MessagePacker
{

	/**
	 * Build a message describing an event
	 * @param obs the observable that reported the event
	 * @param object the object pushed by the observable in its notification
	 * @return the appropriate message
	 */
	Message pack(Observable obs, Object object);

	
}
