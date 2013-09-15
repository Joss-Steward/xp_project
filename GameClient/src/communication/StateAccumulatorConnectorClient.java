package communication;
import model.Player;
import model.QualifiedObservableConnector;
import model.reports.LoginInitiatedReport;
import communication.MessagePackerSet;
import communication.StateAccumulator;
import communication.StateAccumulatorConnector;
import data.Position;


/**
 * Configures StateAccumulators in the game server
 * 
 * @author merlin
 * 
 */
public class StateAccumulatorConnectorClient implements
		StateAccumulatorConnector
{

	private MessagePackerSet packerSet;

	/**
	 * 
	 */
	public StateAccumulatorConnectorClient()
	{
		packerSet = new MessagePackerSet();
//		packerSet.registerPacker(Player.class, Position.class,
//				new MovementMessagePacker());
		packerSet.registerPacker(LoginInitiatedReport.class,
				new LoginMessagePacker());
	}

	/**
	 * @see StateAccumulatorConnector#setUpObserverLinks(StateAccumulator)
	 */
	@Override
	public void setUpObserverLinks(StateAccumulator accumulator)
	{
		QualifiedObservableConnector.getSingleton().registerObserver(accumulator, LoginInitiatedReport.class);
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
		QualifiedObservableConnector.getSingleton().unregisterObserver(accumulator, LoginInitiatedReport.class);
	}
}