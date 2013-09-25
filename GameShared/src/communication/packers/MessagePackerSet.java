package communication.packers;

import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

import org.reflections.Reflections;

import com.google.common.collect.Multimap;

import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import communication.CommunicationException;
import communication.messages.Message;

/**
 * Holds the set of MessagePackers that should translate notifications from
 * observables into messages to be sent to the other side
 * 
 * @author merlin
 * 
 */
public class MessagePackerSet
{

	protected HashMap<Class<?>, MessagePacker> packers;

	/**
	 * 
	 */
	public MessagePackerSet()
	{
		packers = new HashMap<Class<?>, MessagePacker>();
		detectAndRegisterAllPackersInPackage();
	}

	/**
	 * 
	 */
	private void detectAndRegisterAllPackersInPackage()
	{
		Reflections reflections = new Reflections(this.getClass().getPackage().getName());
		for (String mmapName : reflections.getStore().getStoreMap().keySet())
		{
			Multimap<String, String> mmap = reflections.getStore().getStoreMap().get(mmapName);
			for (Map.Entry<String, String> entry : mmap.entries())
			{
				// skip over private classes that are inside another class
				if (!entry.getValue().contains("$"))
				{
					try
					{
						Class<?> classToRegister = Class.forName(entry.getValue());
						if (implementsMessagePacker(classToRegister))
						{
							MessagePacker packer = (MessagePacker) classToRegister.newInstance();
							System.out.println("Registering " + classToRegister);
							registerPacker(packer);
						}
					} catch (ClassNotFoundException e)
					{
						e.printStackTrace();
					} catch (InstantiationException e)
					{
						e.printStackTrace();
					} catch (IllegalAccessException e)
					{
						e.printStackTrace();
					}
				}

			}
		}
	}

	/**
	 * @param classToRegister
	 * @return
	 */
	private boolean implementsMessagePacker(Class<?> classToRegister)
	{
		if (classToRegister == MessagePacker.class)
		{
			System.out.println("Regected MessagePacker");
			return false;
		}
		Class<?>[] list = classToRegister.getInterfaces();
		boolean found = false;
		for (Class<?> item : list)
		{
			if (recursivelyImplementsMessagePacker(item))
			{
				found = true;
			}
		}

		return found;

	}

	/**
	 * @param item
	 * @return
	 */
	private boolean recursivelyImplementsMessagePacker(Class<?> item)
	{
		if (item == MessagePacker.class)
		{
			return true;
		}
		Class<?>[] list = item.getInterfaces();
		boolean found = false;
		for (Class<?> nextItem : list)
		{
			if (recursivelyImplementsMessagePacker(nextItem))
			{
				found = true;
			}
		}

		return found;
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
	protected void registerPacker(MessagePacker packer)
	{
		Class<?> reportWePack = packer.getReportWePack();
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
					packer.getReportWePack());
		}
	}

}
