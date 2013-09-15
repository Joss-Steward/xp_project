package communication;
import java.util.HashMap;
import java.util.Observable;

import communication.messages.Message;


/**
 * Holds the set of MessagePackers that should translate notifications from observables into messages to be sent to the other side
 * @author merlin
 *
 */
public final class MessagePackerSet
{
	private class Key
	{

		private final Class<? extends Observable> observableClass;

		private final Class<? extends Object> objectClass;

		
		public Key(Class<? extends Observable> class1,
			Class<? extends Object> class2)
		{
			observableClass = class1;
			objectClass = class2;
		}
		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Key)) 
				return false;
			Key other = (Key) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (objectClass == null)
			{
				if (other.objectClass != null)
					return false;
			} else if (!objectClass.equals(other.objectClass))
				return false;
			if (observableClass == null)
			{
				if (other.observableClass != null)
					return false;
			} else if (!observableClass.equals(other.observableClass))
				return false;
			return true;
		}
		
		private MessagePackerSet getOuterType()
		{
			return MessagePackerSet.this;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((objectClass == null) ? 0 : objectClass.hashCode());
			result = prime * result
					+ ((observableClass == null) ? 0 : observableClass.hashCode());
			return result;
		}
	}
	
	private HashMap<Key, MessagePacker> packers;
	
	/**
	 * 
	 */
	public MessagePackerSet()
	{
		packers = new HashMap<Key, MessagePacker>();
	}

	MessagePacker getPackerFor(Observable obs, Object object)
			throws CommunicationException
	{
		Key key = new Key(obs.getClass(), object.getClass());
		if (!packers.containsKey(key))
		{
			throw new CommunicationException("No MessagePacker for " + obs.getClass() + " sending a " + object.getClass());
		}
		MessagePacker packer = packers.get(key);
		return packer;
	}

	 /**
	 * Get the appropriate packer to pack this notification into a message
	 * @param obs the type of the observer that made the notification
	 * @param object the type of object pushed by the observer
	 * @return the message describing the event
	 * @throws CommunicationException if there is no packer registered for this type of event
	 */
	public Message pack(Observable obs, Object object) throws CommunicationException
	{
		MessagePacker packer = getPackerFor(obs, object);
		return packer.pack(obs, object);
		
	}

	/**
	 * Add a MessagePacker to this set
	 * @param class1 the class of observable that made the notification
	 * @param class2 the class of the object pushed by the observer
	 * @param packer the packer that should interpret this type of notification
	 */
	public void registerPacker(Class<? extends Observable> class1,
			Class<? extends Object> class2, MessagePacker packer)
	{
		packers.put(new Key(class1, class2),packer);
	}
}
