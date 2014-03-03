package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Position;

/**
 * @author Josh
 *
 */
public class CommandMovePlayerTest 
{
	/**
	 * Create the passability map to simulate a map being loaded in.
	 */
	@Before
	public void setup()
	{
		boolean[][] passability = {{true, true, true},
			   	   				   {true, false, true},
			   	   				   {true, true, true}};
		
		MapManager.getSingleton().setPassability(passability);
	}
	
	
	/**
	 * Reset the MapManager after we're done.
	 */
	@After
	public void cleanup()
	{
		MapManager.resetSingleton();
	}
	

	/**
	 * Testing the command to move other player
	 */
	@Test
	public void testMovePlayer() 
	{
		Player p1 = new Player(1);
		p1.move(new Position(0, 0));
		Player p = PlayerManager.getSingleton().addPlayer(1);
		assertEquals(new Position(0, 0), p.getPlayerPosition());
		
		CommandMovePlayer cm = new CommandMovePlayer(1,new Position(1, 0));
		cm.execute();
		assertEquals(new Position(1, 0), p.getPlayerPosition());
	}
	
	
	/**
	 * Test attempting to move into an impassable position
	 */
	@Test
	public void testIllegalMove()
	{
		Player someGuy = new Player(1);
		someGuy.move(new Position(0, 0));
		Player p = PlayerManager.getSingleton().addPlayer(1);
		assertEquals(new Position(0, 0), p.getPlayerPosition());
		
		CommandMovePlayer cm = new CommandMovePlayer(1,new Position(1, 1));
		cm.execute();
		
		assertEquals(new Position(0, 0), p.getPlayerPosition());
	}
}
