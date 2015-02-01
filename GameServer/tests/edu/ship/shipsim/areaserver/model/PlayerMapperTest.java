package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.assertEquals;
import model.OptionsManager;

import org.junit.Before;
import org.junit.Test;

import data.Position;
import datasource.DatabaseException;
import datasource.DatabaseTest;
import datasource.PlayersForTest;
import edu.ship.shipsim.areaserver.datasource.PlayerRowDataGatewayMock;

/**
 * Tests the PlayerMapper class
 * @author Merlin
 *
 */
public class PlayerMapperTest extends DatabaseTest
{
	/**
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton(true);
		new PlayerRowDataGatewayMock().resetData();
	}
	
	/**
	 * make sure the mapper retrieves all of the necessary information for the player it is finding
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void canFindExisting() throws DatabaseException
	{
		PlayerMapper pm = new PlayerMapper(PlayersForTest.MERLIN.getPlayerID());
		Player p = pm.getPlayer();
		assertEquals(PlayersForTest.MERLIN.getAppearanceType(),p.getAppearanceType());
		assertEquals(PlayersForTest.MERLIN.getPlayerName(),p.getPlayerName());
		assertEquals(PlayersForTest.MERLIN.getPosition(), p.getPlayerPosition());
		assertEquals(PlayersForTest.MERLIN.getQuizScore(), p.getQuizScore());
		assertEquals(PlayersForTest.MERLIN.getMapName(), p.getMapName());
	}
	
	/**
	 * An exception should be thrown if we are trying to create a mapper for a player that doesn't exist
	 * @throws DatabaseException should
	 */
	@Test(expected = DatabaseException.class)
	public void cantFindNonExisting() throws DatabaseException
	{
		new PlayerMapper(PlayersForTest.values().length+10);
	}
	
	/**
	 * Make sure that all of the relevant information gets persisted to the data source
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void persists() throws DatabaseException
	{
		PlayerMapper pm = new PlayerMapper(PlayersForTest.MERLIN.getPlayerID());
		Player p = pm.getPlayer();
		p.setAppearanceType("silly");
		p.setPlayerPositionWithoutNotifying(new Position(42,24));
		p.setQuizScore(666);
		p.setMapName("sillyMap");
		p.persist();
		
		PlayerMapper pm2 = new PlayerMapper(PlayersForTest.MERLIN.getPlayerID());
		Player p2 = pm2.getPlayer();
		assertEquals(p.getAppearanceType(),p2.getAppearanceType());
		assertEquals(p.getPlayerPosition(),p2.getPlayerPosition());
		assertEquals(p.getQuizScore(), p2.getQuizScore());
		assertEquals(p.getMapName(), p2.getMapName());
	}

}

