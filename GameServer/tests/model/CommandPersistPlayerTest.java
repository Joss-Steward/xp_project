package model;

import static org.junit.Assert.*;
import model.IllegalQuestChangeException;
import model.OptionsManager;
import model.Player;
import model.PlayerManager;

import org.junit.Before;
import org.junit.Test;

import testData.PlayersForTest;
import datasource.DatabaseException;
import datatypes.Position;

/**
 * Test that a player is persisted
 * 
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
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setTestMode(true);
	}

	/**
	 * Test that persistence happens
	 * 
	 * @throws DatabaseException shouldn't
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void testPersists() throws DatabaseException, IllegalQuestChangeException
	{
		Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		player.setPlayerPositionWithoutNotifying(new Position(101, 101));
		player.setAppearanceType("appearance");
		PlayerManager.getSingleton().persistPlayer(player.getPlayerID());

		PlayerManager.resetSingleton();

		Player fetched = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		assertEquals(player.getPlayerPosition(), fetched.getPlayerPosition());
		assertEquals(player.getAppearanceType(), fetched.getAppearanceType());
	}

}
