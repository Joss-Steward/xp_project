package datasource;

import static org.junit.Assert.*;

import java.util.List;

import model.PlayerID;

import org.junit.Test;

/**
 * @author Merlin
 *
 */
public class PlayerLoginTableDataGatewayRDSTest
{

	/**
	 * make sure we get all of the players
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void test() throws DatabaseException
	{
		List<PlayerID> players = PlayerLoginTableDataGatewayRDS.getPlayerIDList();
		
		PlayersForTest[] expectedPlayers = PlayersForTest.values();
		assertEquals(players.size(), expectedPlayers.length);
		for (PlayersForTest p:expectedPlayers)
		{
			PlayerID pid = new PlayerID(p.getPlayerID(), p.getPlayerName());
			assertTrue(players.contains(pid));
		}
	}

}
