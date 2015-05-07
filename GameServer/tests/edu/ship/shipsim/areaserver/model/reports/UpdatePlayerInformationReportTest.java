package edu.ship.shipsim.areaserver.model.reports;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;
import model.OptionsManager;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.PlayersForTest;
import edu.ship.shipsim.areaserver.datasource.AdventureStatesForTest;
import edu.ship.shipsim.areaserver.datasource.AdventuresForTest;
import edu.ship.shipsim.areaserver.datasource.LevelsForTest;
import edu.ship.shipsim.areaserver.datasource.QuestStatesForTest;
import edu.ship.shipsim.areaserver.datasource.QuestsForTest;
import edu.ship.shipsim.areaserver.model.IllegalQuestChangeException;
import edu.ship.shipsim.areaserver.model.Player;
import edu.ship.shipsim.areaserver.model.PlayerManager;
import edu.ship.shipsim.areaserver.model.QuestManager;

/**
 * Test the CurrentQuestStateReport
 * @author Ryan, LaVonne, Olivia
 *
 */
public class UpdatePlayerInformationReportTest {
	
	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setTestMode(true);
		QuestManager.resetSingleton();
	}
	

	
	/**
	 * Tests that we can combine a quest description and state
	 * @throws DatabaseException shouldn't
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void testCombineQuestDescriptionAndState() throws DatabaseException, IllegalQuestChangeException
	{
		Player john = PlayerManager.getSingleton().addPlayer(1);
		UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(john);
		
		assertEquals(1, report.getPlayerID());
		assertEquals(QuestManager.getSingleton().getQuestList(john.getPlayerID()).size(), report.getClientPlayerQuestList().size());
		
		int i = 1;
		for(ClientPlayerQuest q: report.getClientPlayerQuestList())
		{
			if(i == 1)
			{
				assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestDescription(), q.getQuestDescription());
				assertEquals(QuestStatesForTest.PLAYER1_QUEST1.getState(), q.getQuestState());
			}
			if(i == 2)
			{
				assertEquals(QuestsForTest.THE_OTHER_QUEST.getQuestDescription(), q.getQuestDescription());
				assertEquals(QuestStatesForTest.PLAYER1_QUEST2.getState(), q.getQuestState());
			}
			i++;
		}
	}

	/**
	 * Tests that we can combine a quest description and state
	 * @throws DatabaseException shouldn't
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void testCombineAdventureDescriptionAndState() throws DatabaseException, IllegalQuestChangeException
	{
		Player john = PlayerManager.getSingleton().addPlayer(1);
		UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(john);
		
		int i = 1;
		for(ClientPlayerQuest q: report.getClientPlayerQuestList())
		{
			ArrayList<ClientPlayerAdventure> adventureList = q.getAdventureList();
			
			if(i == 1)
			{				
				int j = 1;
				for(ClientPlayerAdventure a: adventureList)
				{
					if(j == 1)
					{
						assertEquals(AdventuresForTest.ONE.getAdventureDescription(), a.getAdventureDescription());
						assertEquals(AdventureStatesForTest.PLAYER1_QUEST1_ADV1.getState(), a.getAdventureState());
					}
					if(j == 2)
					{
						assertEquals(AdventuresForTest.TWO.getAdventureDescription(), a.getAdventureDescription());
						assertEquals(AdventureStatesForTest.PLAYER1_QUEST1_ADV2.getState(), a.getAdventureState());
					}
					j++;
				}
				
			}
			if(i == 2)
			{
				int j = 1;
				for(ClientPlayerAdventure a: adventureList)
				{
					if(j == 1)
					{
						assertEquals(AdventuresForTest.THREE.getAdventureDescription(), a.getAdventureDescription());
						assertEquals(AdventureStatesForTest.PLAYER1_QUEST2_ADV1.getState(), a.getAdventureState());
					}
					if(j == 2)
					{
						assertEquals(AdventuresForTest.FOUR.getAdventureDescription(), a.getAdventureDescription());
						assertEquals(AdventureStatesForTest.PLAYER1_QUEST2_ADV2.getState(), a.getAdventureState());
					}
					j++;
				}
			}
			i++;
		}
	}
	
	/**
	 * Tests that we can report a players experience points
	 * and their levels description and the points level requires
	 * @throws DatabaseException shouldn't
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void testExperienceAndLevelPoints() throws DatabaseException, IllegalQuestChangeException
	{
		Player john = PlayerManager.getSingleton().addPlayer(1);
		
		UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(john);
		assertEquals(PlayersForTest.JOHN.getExperiencePoints(), report.getExperiencePts());
		assertEquals(LevelsForTest.ONE.getDescription(), report.getLevel().getDescription());
		assertEquals(LevelsForTest.ONE.getLevelUpPoints(), report.getLevel().getLevelUpPoints());
		
	}
}
