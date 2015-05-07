package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;
import model.OptionsManager;

import org.junit.Before;
import org.junit.Test;

import datasource.AdventureStateEnum;
import datasource.DatabaseException;
import datasource.PlayersForTest;
import edu.ship.shipsim.areaserver.datasource.AdventureStatesForTest;

/**
 * @author Ryan
 *
 */
public class CommandAdventureNotificationCompleteTest 
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
	 * Test getters and init
	 */
	@Test
	public void testInit() 
	{
		CommandAdventureNotificationComplete cmd = new CommandAdventureNotificationComplete(1,2, 3);
		
		assertEquals(1, cmd.getPlayerID());
		assertEquals(2, cmd.getQuestID());
		assertEquals(3, cmd.getAdventureID());
	}
	
	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testExecute() throws DatabaseException 
	{
		int playerID = PlayersForTest.MERLIN.getPlayerID();
		int questID = 4;
		int adventureID = AdventureStatesForTest.PLAYER2_QUEST4_ADV2.getAdventureID();
				
		PlayerManager.getSingleton().addPlayer(playerID);
		
		CommandAdventureNotificationComplete cmd = new CommandAdventureNotificationComplete(playerID, questID, adventureID);
		cmd.execute();
		
		assertEquals(AdventureStateEnum.COMPLETED, QuestManager.getSingleton().getAdventureStateByID(playerID, questID, adventureID).getState());
	}

}
