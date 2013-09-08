package communication;
import model.Player;
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
		packerSet.registerPacker(Player.class, Position.class,
				new MovementMessagePacker());
		packerSet.registerPacker(Player.class, String.class,
				new LoginMessagePacker());
	}

	/**
	 * @see StateAccumulatorConnector#setUpObserverLinks(StateAccumulator)
	 */
	@Override
	public void setUpObserverLinks(StateAccumulator accumulator)
	{
		Player.getSingleton().addObserver(accumulator);
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