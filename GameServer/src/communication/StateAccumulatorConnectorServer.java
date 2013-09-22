package communication;

import model.reports.PlayerConnectionReport;

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
	 * @see communication.StateAccumulatorConnector#setUpPackersAndObservation(communication.StateAccumulator)
	 */
	@Override
	protected MessagePackerSet setUpPackersAndObservation(StateAccumulator accumulator)
	{
		packerSet = new MessagePackerSet();
		packerSet.registerPacker(PlayerConnectionReport.class, new PlayerJoinedMessagePacker(accumulator));
		return packerSet;
	}

	
}