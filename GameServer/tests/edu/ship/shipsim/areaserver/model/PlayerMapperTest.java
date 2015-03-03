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
import edu.ship.shipsim.areaserver.datasource.QuestStatesForTest;

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
		PlayerMapper pm = getMapper();
		Player p = pm.getPlayer();
		PlayersForTest testPlayer = getPlayerWeAreTesting();
		assertEquals(testPlayer.getAppearanceType(),p.getAppearanceType());
		assertEquals(testPlayer.getPlayerName(),p.getPlayerName());
		assertEquals(testPlayer.getPosition(), p.getPlayerPosition());
		assertEquals(testPlayer.getQuizScore(), p.getQuizScore());
		assertEquals(testPlayer.getMapName(), p.getMapName());
		
		for (QuestStatesForTest qs:QuestStatesForTest.values())
		{
			if (qs.getPlayerID() == testPlayer.getPlayerID())
			{
				assertEquals(qs.getState(), p.getQuestStateByID(qs.getQuestID()));
			}
		}
	}


	/**
	 * This must be player 2 for the quest and adventure states to match up
	 * @return the player whose mapper we are testing
	 */
	protected PlayersForTest getPlayerWeAreTesting()
	{
		return PlayersForTest.MERLIN;
	}

	/**
	 * @return the mapper we are testing
	 * @throws DatabaseException if we can't create the mapper
	 */
	protected PlayerMapper getMapper() throws DatabaseException
	{
		return new PlayerMapper(getPlayerWeAreTesting().getPlayerID());
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
		PlayerMapper pm = getMapper();
		Player p = pm.getPlayer();
		p.setAppearanceType("silly");
		p.setPlayerPositionWithoutNotifying(new Position(42,24));
		p.setQuizScore(666);
		p.setMapName("sillyMap");
		p.persist();
		
		PlayerMapper pm2 = getMapper();
		Player p2 = pm2.getPlayer();
		assertEquals(p.getAppearanceType(),p2.getAppearanceType());
		assertEquals(p.getPlayerPosition(),p2.getPlayerPosition());
		assertEquals(p.getQuizScore(), p2.getQuizScore());
		assertEquals(p.getMapName(), p2.getMapName());
	}

}

