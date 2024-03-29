package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import testData.PlayersForTest;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;

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
		try
		{
			(new PlayerConnectionRowDataGatewayMock(1)).resetData();
		} catch (DatabaseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * If we add a player, the playermanager should know about it
	 * 
	 * @throws PlayerNotFoundException shouldn't
	 */
	@Test
	public void test() throws PlayerNotFoundException
	{
		CommandAddPlayer cmd = new CommandAddPlayer(PlayersForTest.JOHN.getPlayerID(), PlayersForTest.JOHN.getPin());
		cmd.execute();
		Player player = PlayerManager.getSingleton().getPlayerFromID(1);
		assertNotNull(player);
		assertEquals("John", player.getPlayerName());
	}

}
