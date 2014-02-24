package model;

import static org.junit.Assert.*;

import org.junit.Test;

import data.Position;

/**
 * @author Josh
 *
 */
public class CommandMoveOtherPlayerTest 
{

	/**
	 * Testing the command to move other player
	 */
	
	@Test
	public void testMoveOtherPlayer() 
	{
		Player p1 = new Player("TestPlayer");
		p1.move(new Position(0, 0));
		PlayerManager.getSingleton().addPlayer("TestPlayer");
		CommandMoveOtherPlayer cm = new CommandMoveOtherPlayer("TestPlayer",new Position(1, 1));
		cm.execute();
		assertEquals(new Position(1, 1), PlayerManager.getSingleton().getPlayerNamed("TestPlayer").getPlayerPosition());
	}

}
