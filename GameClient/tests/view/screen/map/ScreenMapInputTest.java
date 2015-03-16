package view.screen.map;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import org.junit.Before;
import org.junit.Test;

import view.screen.map.ScreenMapInput;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import data.Position;
import edu.ship.shipsim.client.model.MapManager;
import edu.ship.shipsim.client.model.ModelFacade;
import edu.ship.shipsim.client.model.PlayerManager;
import edu.ship.shipsim.client.model.ThisClientsPlayer;
import static org.junit.Assert.*;

/**
 * @author nhydock
 */
public class ScreenMapInputTest
{

	/**
	 * Reset the player manager before starting testing
	 */
	@Before
	public void setup()
	{
		PlayerManager.resetSingleton();

		boolean[][] passability =
		{
		{ true, true, true },
		{ true, false, true },
		{ true, true, true } };

		MapManager.resetSingleton();
		MapManager.getSingleton().setPassability(passability);
	}

	/**
	 * Test the input listener sending movement commands
	 * 
	 * @throws InterruptedException
	 *             when thread used to allow model facade command processing
	 *             interrupts
	 */
	@Test
	public void testMovementCommandIssuing() throws InterruptedException
	{
		InputProcessor input = new ScreenMapInput();
		// Gdx.input.setInputProcessor(input);

		// Make the model facade be headless, but not mocked so we can make sure
		// that the movement commands generated when we hit a key cause the
		// appropriate change in position
		ModelFacade.resetSingleton();
		ModelFacade.getSingleton(true, false);
		// setup initial player for testing
		PlayerManager pm = PlayerManager.getSingleton();
		pm.initiateLogin("john", "pw");
		try
		{
			pm.finishLogin(1);
		} catch (AlreadyBoundException | NotBoundException e)
		{
			e.printStackTrace();
			fail("We have had trouble setting up this client's player for testing");
		}
		ThisClientsPlayer testPlayer = PlayerManager.getSingleton()
				.getThisClientsPlayer();
		assertEquals(new Position(0, 0), testPlayer.getPosition());

		MapManager.resetSingleton();
		MapManager.getSingleton().setHeadless(true);
		MapManager.getSingleton().changeToNewFile("current.tmx");

		// move player south
		input.keyDown(Keys.DOWN);
		pauseForCommandExecution();
		assertEquals(new Position(1, 0), testPlayer.getPosition());
		
		// move player north
		input.keyDown(Keys.UP);
		pauseForCommandExecution();
		assertEquals(new Position(0, 0), testPlayer.getPosition());

		// move player east
		input.keyDown(Keys.RIGHT);
		pauseForCommandExecution();
		assertEquals(new Position(0, 1), testPlayer.getPosition());

		// move player west
		input.keyDown(Keys.LEFT);
		pauseForCommandExecution();
		assertEquals(new Position(0, 0), testPlayer.getPosition());
	}

	private void pauseForCommandExecution() throws InterruptedException
	{
		while (ModelFacade.getSingleton().queueSize() > 0)
		{
			Thread.sleep(100);
		}
	}
}
