package edu.ship.shipsim.client.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ship.shipsim.client.model.CommandLoginFailed;
import edu.ship.shipsim.client.model.PlayerManager;

/**
 * @author Matt and Andy
 *
 */
public class CommandLoginFailedTest 
{

	/**
	 * reset the player to make sure the login in cleat
	 */
	@Before
	public void setup()
	{
		PlayerManager.resetSingleton();
	}

	/**
	 * We just need to tell the player to initiate this
	 */
	@Test
	public void shouldTellPlayer()
	{
		CommandLoginFailed cmd = new CommandLoginFailed();
		cmd.execute();
		PlayerManager p = PlayerManager.getSingleton();
		assertFalse(p.isLoginInProgress());

	}

}
