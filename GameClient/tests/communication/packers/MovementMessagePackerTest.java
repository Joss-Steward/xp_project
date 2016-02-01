package communication.packers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.ClientPlayerManager;
import model.reports.PlayerMovedReport;

import org.junit.Before;
import org.junit.Test;

import communication.messages.MovementMessage;
import data.Position;

/**
 * 
 * @author merlin
 * 
 */
public class MovementMessagePackerTest
{

	/**
	 * Just make sure the right stuff goes into the message when we pack it
	 */
	@Test
	public void test()
	{
		MovementMessagePacker packer = new MovementMessagePacker();
		MovementMessage msg = (MovementMessage) packer.pack(new PlayerMovedReport(1,
				new Position(4, 3)));
		assertEquals(1, msg.getPlayerID());
		assertEquals(new Position(4, 3), msg.getPosition());
	};

	/**
	 * If the packer sees a report that a different player moved, it shouldn't
	 * pack anything (just returning null)
	 * 
	 * @throws AlreadyBoundException
	 *             shouldn't
	 * @throws NotBoundException
	 *             shouldn't
	 */
	@Test
	public void onlySendIfThisPlayer() throws AlreadyBoundException, NotBoundException
	{
		setup();
		MovementMessagePacker packer = new MovementMessagePacker();
		MovementMessage msg = (MovementMessage) packer.pack(new PlayerMovedReport(2,
				new Position(4, 3)));
		assertNull(msg);
	}

	/**
	 * Set up this client's player to be player number 1
	 * 
	 * @throws AlreadyBoundException
	 *             shouldn't
	 * @throws NotBoundException
	 *             shouldn't
	 */
	@Before
	public void setup() throws AlreadyBoundException, NotBoundException
	{
		ClientPlayerManager.resetSingleton();
		ClientPlayerManager.getSingleton().initiateLogin("john", "pw");
		ClientPlayerManager.getSingleton().finishLogin(1);
	}

}
