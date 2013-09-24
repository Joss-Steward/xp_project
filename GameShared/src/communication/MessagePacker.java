package communication;

import model.QualifiedObservableReport;
import communication.messages.Message;

/**
 * These classes translates events in the system (as reported by Observables)
 * into Messages
 * 
 * @author merlin
 * 
 */
public interface MessagePacker
{

	/**
	 * Build a message describing an event
	 * 
	 * @param object
	 *            the object pushed by the observable in its notification
	 * @return the appropriate message
	 */
	public Message pack(QualifiedObservableReport object);

	/**
	 * Tell which type of report we pack
	 * 
	 * @return the type of report we pack
	 */
	public Class<?> getReportWePack();
}
