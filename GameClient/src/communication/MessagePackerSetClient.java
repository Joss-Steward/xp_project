package communication;

/**
 * @author Merlin
 *
 */
public class MessagePackerSetClient extends MessagePackerSet
{

	/**
	 * Just fill the map with the packers for this part of the system
	 */
	public MessagePackerSetClient()
	{
		super();
		this.registerPacker(new LoginMessagePacker());
		this.registerPacker(new MovementMessagePacker());
	}

}
