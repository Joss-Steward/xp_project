package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest
{

	@Test
	public void canGetUserName() throws DatabaseException
	{
		Player p = new Player(1);
		assertEquals("John", p.getPlayerName());
	}

}
