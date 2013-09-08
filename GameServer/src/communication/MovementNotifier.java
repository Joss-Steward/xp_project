package communication;
import java.util.Observable;

import communication.messages.MovementMessage;

/**
 * This singleton is responsible for reporting one player's movements to
 * everyone else on this server. Anyone who is interested in where people are
 * should observe this singleton.
 * 
 * @author merlin
 * 
 */
public class MovementNotifier extends Observable
{

	private static MovementNotifier singleton;

	/**
	 * Only to be used in testing
	 */
	public static void resetSingleton()
	{
		singleton = null;
	}

	/**
	 * There is only ever one of these
	 * 
	 * @return the only one of these there is
	 */
	public synchronized static MovementNotifier getSingleton()
	{
		if (singleton == null)
		{
			singleton = new MovementNotifier();
		}
		return singleton;
	}

	/**
	 * When a player has moved, this method will notify everyone about that
	 * movement pushing the MovementMessage that describes the movement
	 * 
	 * @param msg
	 *            a msg reporting the movement that has occurred.
	 */
	public void playerMoved(MovementMessage msg)
	{
		this.setChanged();
		this.notifyObservers(msg);
	}

}
