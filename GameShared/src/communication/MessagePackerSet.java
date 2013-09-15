package communication;
import java.util.HashMap;

import model.QualifiedObservableReport;
import communication.messages.Message;


/**
 * Holds the set of MessagePackers that should translate notifications from observables into messages to be sent to the other side
 * @author merlin
 *
 */
public final class MessagePackerSet
{
	
	
	private HashMap<Class <? extends QualifiedObservableReport>, MessagePacker> packers;
	
	/**
	 * 
	 */
	public MessagePackerSet()
	{
		packers = new HashMap<Class <? extends QualifiedObservableReport>, MessagePacker>();
	}

	MessagePacker getPackerFor(Class < ? extends QualifiedObservableReport> reportType)
			throws CommunicationException
	{
		if (!packers.containsKey(reportType))
		{
			throw new CommunicationException("No MessagePacker for " + reportType);
		}
		MessagePacker packer = packers.get(reportType);
		return packer;
	}

	 /**
	 * translate the report into the message that should travel over the connection
	 * @param report the report that we are packing for transport
	 * @return the message describing the event
	 * @throws CommunicationException if there is no packer registered for this type of event
	 */
	public Message pack(QualifiedObservableReport report) throws CommunicationException
	{
		MessagePacker packer = getPackerFor(report.getClass());
		return packer.pack(report);
		
	}

	/**
	 * Add a MessagePacker to this set
	 * @param reportType the class of the report we are supposed to react to
	 * @param packer the packer that should interpret this type of notification
	 */
	public void registerPacker(Class<? extends QualifiedObservableReport> reportType,
			MessagePacker packer)
	{
		packers.put(reportType,packer);
	}
}
