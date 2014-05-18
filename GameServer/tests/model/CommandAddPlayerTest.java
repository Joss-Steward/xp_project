package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Merlin
 * 
 */
public class CommandAddPlayerTest
{

	/**
	 * 
	 */
	@Before
	public void setup()
	{
		PlayerManager.resetSingleton();
	}

	/**
	 * If we add a player, the playermanager should know about it
	 * 
	 * @throws PlayerNotFoundException
	 *             shouldn't
	 */
	@Test
	public void test() throws PlayerNotFoundException
	{
		CommandAddPlayer cmd = new CommandAddPlayer(1, PlayerConnection.DEFAULT_PIN);
		cmd.execute();
		assertNotNull(PlayerManager.getSingleton().getPlayerFromID(1));
	}

}
