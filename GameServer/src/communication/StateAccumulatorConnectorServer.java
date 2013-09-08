package communication;

import communication.messages.MovementMessage;

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
		packerSet.registerPacker(MovementNotifier.class, MovementMessage.class,
				new MovementMessagePacker());
	}

	/**
	 * @see StateAccumulatorConnector#setUpObserverLinks(StateAccumulator)
	 */
	@Override
	public void setUpObserverLinks(StateAccumulator accumulator)
	{
		MovementNotifier.getSingleton().addObserver(accumulator);
	}

	/**
	 * @see StateAccumulatorConnector#getMessagePackerSet()
	 */
	@Override
	public MessagePackerSet getMessagePackerSet()
	{
		return packerSet;
	}
}