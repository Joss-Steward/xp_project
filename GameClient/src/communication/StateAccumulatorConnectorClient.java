package communication;


/**
 * Configures StateAccumulators in the game client
 * 
 * @author merlin
 * 
 */
public class StateAccumulatorConnectorClient extends
		StateAccumulatorConnector
{
	/**
	 * 
	 */
	public StateAccumulatorConnectorClient()
	{
		packerSet = new MessagePackerSet();
//		packerSet.registerPacker(Player.class, Position.class,
//				new MovementMessagePacker());
		
	}

	/**
	 * @see communication.StateAccumulatorConnector#getMessagePackersFor(communication.StateAccumulator)
	 */
	@Override
	protected void getMessagePackersFor(StateAccumulator accumulator)
	{
		// TODO Auto-generated method stub
		
	}


}