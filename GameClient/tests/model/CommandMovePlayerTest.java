package model;

import static org.junit.Assert.*;

import org.junit.Test;

import data.Position;

/**
 * @author Josh
 *
 */
public class CommandMovePlayerTest 
{

	/**
	 * Testing the command to move other player
	 */
	
	@Test
	public void testMovePlayer() 
	{
		Player p1 = new Player(1);
		p1.move(new Position(0, 0));
		Player p = PlayerManager.getSingleton().addPlayer(1);
		assertEquals(new Position(0, 0), p.getPosition());
		
		CommandMovePlayer cm = new CommandMovePlayer(1,new Position(1, 1));
		cm.execute();
		assertEquals(new Position(1, 1), p.getPosition());
	}

}
