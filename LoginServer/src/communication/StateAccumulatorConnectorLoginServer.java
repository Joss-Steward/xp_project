package communication;


import communication.MessagePackerSet;
import communication.StateAccumulator;
import communication.StateAccumulatorConnector;
import model.reports.LoginSuccessfulReport;

/**
 * Configures StateAccumulators in the game server
 * 
 * @author merlin
 * 
 */
public class StateAccumulatorConnectorLoginServer extends
		StateAccumulatorConnector
{
	/**
	 * 
	 */
	public StateAccumulatorConnectorLoginServer()
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