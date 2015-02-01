package edu.ship.shipsim.areaserver.datasource;

import datasource.PlayersForTest;
/**
 * The players that are in the test database
 * 
 * @author Merlin
 * 
 */
public enum NPCsForTest
{
	
	/**
	 * 
	 */
	NPC1(PlayersForTest.MOCK_NPC.getPlayerID(),"edu.ship.shipsim.areaserver.model.NPCMockBehavior"),
	/**
	 * 
	 */
	NPC2(PlayersForTest.MOCK_NPC2.getPlayerID(),"edu.ship.shipsim.areaserver.model.QuizBotBehavior");

	private String behaviorClass;
	private int playerID;

	

	NPCsForTest(int playerID, String behaviorClass)
	{
		this.playerID = playerID;
		this.behaviorClass = behaviorClass;
	}

	/**
	 * @return the map name the pin for the current connection is good for
	 */
	public String getBehaviorClass()
	{
		return behaviorClass;
	}

	/**
	 * @return this NPCs playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

}