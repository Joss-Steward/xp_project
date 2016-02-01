package model;

import static org.junit.Assert.*;
import model.CommandAddPlayer;
import model.OptionsManager;
import model.Player;
import model.PlayerConnection;
import model.PlayerManager;
import model.PlayerNotFoundException;

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
		OptionsManager.getSingleton().setTestMode(true);
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
		Player player = PlayerManager.getSingleton().getPlayerFromID(1);
		assertNotNull(player);
		assertEquals("John", player.getPlayerName());
	}

}
