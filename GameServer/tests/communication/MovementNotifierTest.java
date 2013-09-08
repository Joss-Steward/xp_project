package communication;
import static org.junit.Assert.*;

import java.util.Observer;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import communication.MovementNotifier;
import communication.messages.MovementMessage;
import data.Position;

/**
 * Makes sure the MovementNotifier works
 * @author merlin
 *
 */
public class MovementNotifierTest
{

	/**
	 * Just reset the singleton
	 */
	@Before
	public void setup()
	{
		MovementNotifier.resetSingleton();
	}
	
	/**
	 * Make sure the movement notifier is a singleton
	 */
	@Test
	public void isSingleton()
	{
		MovementNotifier x = MovementNotifier.getSingleton();
		assertSame(x, MovementNotifier.getSingleton());
	}
	
	/**
	 * Make sure that the MovementNotifier notifies its observers when a movement is
	 * reported to it.
	 */
	@Test
	public void notifies()
	{
		MovementNotifier x = MovementNotifier.getSingleton();
		Observer mock = EasyMock.createMock(Observer.class);
		x.addObserver(mock);
		MovementMessage msg = new MovementMessage(32, new Position(42, 43));
		mock.update(x, msg);
		EasyMock.replay(mock);
		
		x.playerMoved(msg);
		EasyMock.verify(mock);
	}

}
