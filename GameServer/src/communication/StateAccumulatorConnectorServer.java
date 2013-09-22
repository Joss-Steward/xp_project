package communication;

import model.reports.LoginSuccessfulReport;

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
		packerSet.registerPacker(LoginSuccessfulReport.class, new LoginResponseMessagePacker(accumulator));	
		return packerSet;
	}

	
}