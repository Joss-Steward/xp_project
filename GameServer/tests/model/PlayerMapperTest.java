package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import model.AdventureState;
import model.IllegalAdventureChangeException;
import model.IllegalQuestChangeException;
import model.OptionsManager;
import model.Player;
import model.PlayerMapper;
import model.QuestManager;
import model.QuestState;

import org.junit.Before;
import org.junit.Test;

import data.Position;
import datasource.AdventureStateEnum;
import datasource.AdventureStateTableDataGatewayMock;
import datasource.AdventureStatesForTest;
import datasource.DatabaseException;
import datasource.DatabaseTest;
import datasource.PlayerConnectionRowDataGatewayMock;
import datasource.PlayerRowDataGatewayMock;
import datasource.PlayersForTest;
import datasource.QuestStateTableDataGatewayMock;
import datasource.QuestStatesForTest;

/**
 * Tests the PlayerMapper class
 * 
 * @author Merlin
 *
 */
public class PlayerMapperTest extends DatabaseTest
{
	/**
	 * @throws DatabaseException shouldn't
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		super.setUp();
		OptionsManager.getSingleton().setTestMode(true);
		new PlayerRowDataGatewayMock().resetData();
		QuestStateTableDataGatewayMock.getSingleton().resetData();
		AdventureStateTableDataGatewayMock.getSingleton().resetData();
		QuestManager.resetSingleton();
		new PlayerConnectionRowDataGatewayMock(1).resetData();
	}

	/**
	 * make sure the mapper retrieves all of the necessary information for the
	 * player it is finding
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void canFindExisting() throws DatabaseException, IllegalQuestChangeException
	{
		PlayerMapper pm = getMapper();
		Player p = pm.getPlayer();
		PlayersForTest testPlayer = getPlayerWeAreTesting();
		assertEquals(testPlayer.getAppearanceType(), p.getAppearanceType());
		assertEquals(testPlayer.getPlayerName(), p.getPlayerName());
		assertEquals(testPlayer.getPosition(), p.getPlayerPosition());
		assertEquals(testPlayer.getQuizScore(), p.getQuizScore());
		assertEquals(testPlayer.getMapName(), p.getMapName());
		assertEquals(testPlayer.getExperiencePoints(), p.getExperiencePoints());

		for (QuestStatesForTest qs : QuestStatesForTest.values())
		{
			if (qs.getPlayerID() == testPlayer.getPlayerID())
			{
				QuestState playerQuestState = QuestManager.getSingleton()
						.getQuestStateByID(testPlayer.getPlayerID(), qs.getQuestID());
				assertEquals(qs.getState(), playerQuestState.getStateValue());
				assertEquals(qs.isNeedingNotification(), playerQuestState.isNeedingNotification());
				for (AdventureStatesForTest as : AdventureStatesForTest.values())
				{
					ArrayList<AdventureState> adventureList = playerQuestState
							.getAdventureList();
					if ((as.getPlayerID() == testPlayer.getPlayerID())
							&& (as.getQuestID() == playerQuestState.getID()))
					{
						AdventureState expected = new AdventureState(as.getAdventureID(),
								as.getState(), as.isNeedingNotification());
						expected.setParentQuest(playerQuestState);
						assertTrue(
								"questID " + qs.getQuestID() + " adventureID "
										+ as.getAdventureID() + " state " + as.getState(),
								adventureList.contains(expected));
					}
				}
			}
		}
	}

	/**
	 * This must be player 2 for the quest and adventure states to match up
	 * 
	 * @return the player whose mapper we are testing
	 */
	protected PlayersForTest getPlayerWeAreTesting()
	{
		return PlayersForTest.MERLIN;
	}

	/**
	 * @return the mapper we are testing
	 * @throws DatabaseException
	 *             if we can't create the mapper
	 */
	protected PlayerMapper getMapper() throws DatabaseException
	{
		return new PlayerMapper(getPlayerWeAreTesting().getPlayerID());
	}

	/**
	 * An exception should be thrown if we are trying to create a mapper for a
	 * player that doesn't exist
	 * 
	 * @throws DatabaseException
	 *             should
	 */
	@Test(expected = DatabaseException.class)
	public void cantFindNonExisting() throws DatabaseException
	{
		new PlayerMapper(PlayersForTest.values().length + 10);
	}

	/**
	 * Make sure that all of the relevant information gets persisted to the data
	 * source
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 */
	@Test
	public void persists() throws DatabaseException, IllegalAdventureChangeException, IllegalQuestChangeException
	{
		PlayerMapper pm = getMapper();
		Player p = pm.getPlayer();
		p.setAppearanceType("silly");
		p.setPlayerPositionWithoutNotifying(new Position(42, 24));
		p.setQuizScore(666);
		p.setMapName("sillyMap");
		p.setExperiencePoints(424);
		QuestState questState = null;
		if (p.getClass() == Player.class)
		{
			questState = QuestManager.getSingleton().getQuestStateByID(p.getPlayerID(),
					QuestStatesForTest.PLAYER2_QUEST2.getQuestID());
			questState.trigger();
		}
		p.persist();

		PlayerMapper pm2 = getMapper();
		Player p2 = pm2.getPlayer();
		assertEquals(p.getAppearanceType(), p2.getAppearanceType());
		assertEquals(p.getPlayerPosition(), p2.getPlayerPosition());
		assertEquals(p.getQuizScore(), p2.getQuizScore());
		assertEquals(p.getMapName(), p2.getMapName());
		assertEquals(p.getExperiencePoints(), p2.getExperiencePoints());
		if (p.getClass() == Player.class)
		{
			QuestState retrievedQuestState = QuestManager.getSingleton()
					.getQuestStateByID(p2.getPlayerID(),
							QuestStatesForTest.PLAYER2_QUEST1.getQuestID());
			assertEquals(questState.getStateValue(), retrievedQuestState.getStateValue());
			for (AdventureState a : retrievedQuestState.getAdventureList())
			{
				assertEquals(AdventureStateEnum.TRIGGERED, a.getState());
			}
		}
	}

}