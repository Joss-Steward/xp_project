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
		Player p1 = new Player(1);
		p1.move(new Position(0, 0));
		PlayerManager.getSingleton().addPlayer(1);
		CommandMoveOtherPlayer cm = new CommandMoveOtherPlayer(1,new Position(1, 1));
		cm.execute();
		assertEquals(new Position(1, 1), PlayerManager.getSingleton().getPlayerFromID(1).getPlayerPosition());
	}

}
