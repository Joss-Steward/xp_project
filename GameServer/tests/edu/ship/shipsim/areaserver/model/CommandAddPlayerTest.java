package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;
import model.OptionsManager;
import model.PlayerConnection;

import org.junit.Before;
import org.junit.Test;

import edu.ship.shipsim.areaserver.model.CommandAddPlayer;
import edu.ship.shipsim.areaserver.model.PlayerManager;
import edu.ship.shipsim.areaserver.model.PlayerNotFoundException;

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
