package view;

import model.ModelFacade;
import model.PlayerManager;
import model.ThisClientsPlayer;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import data.Position;

import static org.junit.Assert.*;

/**
 * @author nhydock
 */
public class ScreenMapInputTest {

	/**
	 * Reset the player manager before starting testing
	 */
	@Before
	public void setup(){
		PlayerManager.resetSingleton();
	}
	
	/**
	 * Test the input listener sending movement commands
	 * @throws InterruptedException 
	 */
	@Test
	public void testMovementCommandIssuing() throws InterruptedException
	{
		InputProcessor input = new ScreenMapInput();
		//Gdx.input.setInputProcessor(input);
		ModelFacade mf = ModelFacade.getSingleton(true);
		//setup initial player for testing
		PlayerManager pm = PlayerManager.getSingleton();
		pm.addPlayer(1);
		pm.setThisClientsPlayer(1);
		ThisClientsPlayer testPlayer = pm.getThisClientsPlayer();
		assertEquals(new Position(0, 0), testPlayer.getPosition());
		
		//move player north
		assertEquals(0, mf.getCommandQueueLength());
		input.keyDown(Keys.UP);
		assertEquals(1, mf.getCommandQueueLength());
		while (mf.getCommandQueueLength() > 0) Thread.sleep(100);
		assertEquals(new Position(-1, 0), testPlayer.getPosition());
		
		//move player south
		input.keyDown(Keys.DOWN);
		while (mf.getCommandQueueLength() > 0) Thread.sleep(100);
		assertEquals(new Position(0, 0), testPlayer.getPosition());
		
		//move player east
		input.keyDown(Keys.RIGHT);
		while (mf.getCommandQueueLength() > 0) Thread.sleep(100);
		assertEquals(new Position(0, 1), testPlayer.getPosition());
		
		//move player east
		input.keyDown(Keys.LEFT);
		while (mf.getCommandQueueLength() > 0) Thread.sleep(100);
		assertEquals(new Position(0, 0), testPlayer.getPosition());
	}
}
