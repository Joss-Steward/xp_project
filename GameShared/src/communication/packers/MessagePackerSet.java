package communication.packers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import communication.CommunicationException;
import communication.TypeDetector;
import communication.messages.Message;

/**
 * Holds the set of MessagePackers that should translate notifications from
 * observables into messages to be sent to the other side
 * 
 * @author merlin
 * 
 */
public class MessagePackerSet extends TypeDetector
{

	protected HashMap<Class<?>, MessagePacker> packers;

	/**
	 * 
	 */
	public MessagePackerSet()
	{
		packers = new HashMap<Class<?>, MessagePacker>();
		ArrayList<Class<?>> packerTypes = this.detectAllImplementorsInPackage(MessagePacker.class) ;
		for(Class<?> packerType:packerTypes)
		{
			try
			{
				MessagePacker packer = (MessagePacker) packerType.newInstance();
				System.out.println("Registering Packer" + packerType);
				registerPacker(packer);
			} catch (InstantiationException | IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * Get the packer associated with a given type of report
	 * 
	 * @param reportType
	 *            the report type we are interested
	 * @return the packer that will handle reports of that type
	 * @throws CommunicationException
	 *             if we have no handler for that report type
	 */
	public MessagePacker getPackerFor(Class<? extends QualifiedObservableReport> reportType)
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
	 * translate the report into the message that should travel over the
	 * connection
	 * 
	 * @param report
	 *            the report that we are packing for transport
	 * @return the message describing the event
	 * @throws CommunicationException
	 *             if there is no packer registered for this type of event
	 */
	public Message pack(QualifiedObservableReport report) throws CommunicationException
	{
		Class<? extends QualifiedObservableReport> classWeArePacking = report.getClass();
		MessagePacker packer = getPackerFor(classWeArePacking);
		return packer.pack(report);

	}

	/**
	 * Add a MessagePacker to this set
	 * 
	 * @param packer
	 *            the packer that should interpret this type of notification
	 */
	public void registerPacker(MessagePacker packer)
	{
		Class<?> reportWePack = packer.getReportTypeWePack();
		packers.put(reportWePack, packer);
	}

	/**
	 * Register the given observer with the report types that this message
	 * packer set can pack
	 * 
	 * @param obs
	 *            the observer to be registered
	 */
	public void hookUpObservationFor(Observer obs)
	{
		for (MessagePacker packer : packers.values())
		{
			QualifiedObservableConnector.getSingleton().registerObserver(obs,
					packer.getReportTypeWePack());
		}
	}

}
