package communication;

/**
 * Configures StateAccumulators in the game server
 * 
 * @author merlin
 * 
 */
public class StateAccumulatorConnectorServer extends
		StateAccumulatorConnector
{
	/**
	 * 
	 */
	public StateAccumulatorConnectorServer()
	{
		packerSet = new MessagePackerSet();
//		packerSet.registerPacker(LoginReport.class, new LoginMessagePacker());
//		packerSet.registerPacker( MovementMessage.class,
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