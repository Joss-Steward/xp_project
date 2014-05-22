package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;
import model.PlayersInDB;

import org.junit.Before;
import org.junit.Test;

import data.Position;
import edu.ship.shipsim.areaserver.model.Player;
import edu.ship.shipsim.areaserver.model.PlayerManager;

/**
 * Test that a player is persisted
 * @author Steve
 *
 */
public class CommandPersistPlayerTest 
{

	/**
	 * Reset singletons
	 */
	@Before
	public void setUp()
	{
		PlayerManager.resetSingleton();
	}

	/**
	 * Test that persistence happens
	 */
	@Test
	public void testPersists()
	{
		Player player = PlayerManager.getSingleton().addPlayer(PlayersInDB.MERLIN.getPlayerID());
		player.setPlayerPositionWithoutNotifying(new Position(101, 101));
		player.setAppearanceType("appearance");
		PlayerManager.getSingleton().persistPlayer(player.getID());
		
		PlayerManager.resetSingleton();
		
		Player fetched = PlayerManager.getSingleton().addPlayer(PlayersInDB.MERLIN.getPlayerID());
		assertEquals(player.getPlayerPosition(), fetched.getPlayerPosition());
		assertEquals(player.getAppearanceType(), fetched.getAppearanceType());
	}

}
