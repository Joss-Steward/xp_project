package communication;

import model.reports.LoginInitiatedReport;


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
		
	}

	/**
	 * @see communication.StateAccumulatorConnector#setUpPackersAndObservation(communication.StateAccumulator)
	 */
	@Override
	protected MessagePackerSet setUpPackersAndObservation(StateAccumulator accumulator)
	{
		packerSet = new MessagePackerSet();
		packerSet.registerPacker(LoginInitiatedReport.class, new LoginMessagePacker(accumulator));	
		return packerSet;
	}

	


}