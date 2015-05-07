package edu.ship.shipsim.client.model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;

import org.junit.Test;

import communication.messages.AdventureStateChangeMessage;
import data.Position;
import datasource.AdventureStateEnum;
import datasource.QuestStateEnum;

/**
 * @author sl6469
 *
 */

public class CommandAdventureStateChangeTest
{

	/**
	 * test the setup of the constructor
	 */
	@Test
	public void testInitialization()
	{
		CommandAdventureStateChange x = new CommandAdventureStateChange(new AdventureStateChangeMessage(1,2,5, "Silly Adventure", AdventureStateEnum.COMPLETED));
		assertEquals(2,x.getQuestID());
		assertEquals(5, x.getAdventureID());
		assertEquals("Silly Adventure", x.getAdventureDescription());
		assertEquals(AdventureStateEnum.COMPLETED, x.getAdventureState());
	}

	/**
	 * Test that when we execute the CommandQuestStateChange
	 * ThisClientsPlayer ClientPlayerQuest's state changes
	 * @throws AlreadyBoundException shouldn't
	 * @throws NotBoundException shouldn't
	 */
	@Test
	public void testChangingAdventure() throws AlreadyBoundException, NotBoundException
	{		
		
		int playerID = 1;
		int questID = 1;
		int adventureID = 1;
		
		ClientPlayerQuest q = new ClientPlayerQuest(questID, "silly quest", QuestStateEnum.TRIGGERED, 3, 0);
		ClientPlayerAdventure a1 = new ClientPlayerAdventure(adventureID,"adventure 1", 3, AdventureStateEnum.TRIGGERED, false);
		q.getAdventureList().add(a1);
		
		Position pos = new Position(1, 2);
		PlayerManager pm = PlayerManager.getSingleton();
		pm.initializePlayer(playerID, "Player 1", "Player 1 Type", pos);

		pm.initiateLogin("john", "pw");
		pm.finishLogin(playerID);

		PlayerManager.getSingleton().getThisClientsPlayer().addQuest(q);
		
		CommandAdventureStateChange x = new CommandAdventureStateChange
				(new AdventureStateChangeMessage(playerID, questID,adventureID, "adventure 1", AdventureStateEnum.COMPLETED));
		x.execute();
		
		for(ClientPlayerQuest quest : PlayerManager.getSingleton().getThisClientsPlayer().getQuests())
		{
			if(quest.getQuestID() == questID)
			{
				for(ClientPlayerAdventure adventure: quest.getAdventureList())
				{
					if(adventure.getAdventureID() == adventureID)
					{
						assertEquals(AdventureStateEnum.COMPLETED, adventure.getAdventureState());
					}
				}
			}
		}
	}

}
