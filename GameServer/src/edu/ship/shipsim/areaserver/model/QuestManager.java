package edu.ship.shipsim.areaserver.model;

import java.util.ArrayList;

import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.datasource.AdventureRecord;
import edu.ship.shipsim.areaserver.datasource.AdventureTableDataGatewayMock;
import edu.ship.shipsim.areaserver.datasource.QuestRowDataGatewayMock;

/**
 * Retrieves the list of quest and adventures from the database
 * and sends them to the PlayerManager?
 * @author lavonne
 */
public class QuestManager 
{

	/**
	 * The method returns a singleton of QuestManager
	 * @return the only QuestManager in the system
	 */
	public synchronized static QuestManager getSingleton()
	{
		if(singleton == null)
		{
			singleton = new QuestManager();
		}
		
		return singleton;
	}
	
	/**
	 * Reset the singleton to null
	 */
	public static void resetSingleton()
	{
		if(singleton != null)
		{
			singleton = null;
		}
	}
	
	private static QuestManager singleton;

	/**
	 * Gets a specific quest from the database. Creates a Quest and passes it on the player mapper?
	 * @param questID the quest to retrieve
	 * @return quest the retrieved request
	 * @throws DatabaseException throw an exception if the quest id isn't found
	 */
	public Quest getQuest(int questID) throws DatabaseException {
		
		QuestRowDataGatewayMock mock = new QuestRowDataGatewayMock(questID);
		AdventureTableDataGatewayMock adventureMock = new AdventureTableDataGatewayMock();
		ArrayList<Adventure> adventureList = new ArrayList<Adventure>();
				
		for(AdventureRecord ar : adventureMock.getAdventuresForQuest(questID))
		{
			adventureList.add(new Adventure(ar.getAdventureID(), ar.getAdventureDescription()));
		}
		
		Quest quest = new Quest(mock.getQuestID(), mock.getQuestDescription(), adventureList);
		return quest;
	}
}
