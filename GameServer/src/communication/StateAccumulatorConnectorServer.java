package communication;

/**
 * Configures StateAccumulators in the game server
 * 
 * @author merlin
 * 
 */
public class StateAccumulatorConnectorServer implements
		StateAccumulatorConnector
{

	private MessagePackerSet packerSet;

	/**
	 * 
	 */
	public StateAccumulatorConnectorServer()
	{
		packerSet = new MessagePackerSet();
//		packerSet.registerPacker( MovementMessage.class,
//				new MovementMessagePacker());
	}

	/**
	 * @see StateAccumulatorConnector#setUpObserverLinks(StateAccumulator)
	 */
	@Override
	public void setUpObserverLinks(StateAccumulator accumulator)
	{
		//TODO we need to connect it to some place!
	}

	/**
	 * @see StateAccumulatorConnector#getMessagePackerSet()
	 */
	@Override
	public MessagePackerSet getMessagePackerSet()
	{
		return packerSet;
	}

	/**
	 * @see communication.StateAccumulatorConnector#destroyObserverLinks(communication.StateAccumulator)
	 */
	@Override
	public void destroyObserverLinks(StateAccumulator accumulator)
	{
//		MovementNotifier.getSingleton().deleteObserver(accumulator);
	}
}