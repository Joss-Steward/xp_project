package edu.ship.shipsim.areaserver.model;

import java.util.ArrayList;

import model.OptionsManager;
import data.Position;
import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.datasource.AdventureRecord;
import edu.ship.shipsim.areaserver.datasource.AdventureTableDataGateway;
import edu.ship.shipsim.areaserver.datasource.AdventureTableDataGatewayMock;
import edu.ship.shipsim.areaserver.datasource.AdventureTableDataGatewayRDS;
import edu.ship.shipsim.areaserver.datasource.QuestRowDataGateway;
import edu.ship.shipsim.areaserver.datasource.QuestRowDataGatewayMock;
import edu.ship.shipsim.areaserver.datasource.QuestRowDataGatewayRDS;

/**
 * Retrieves the list of quest and adventures from the database
 * and sends them to the PlayerManager?
 * @author lavonne
 */
public class QuestManager 
{
	
	private QuestRowDataGateway questGateway;
	private AdventureTableDataGateway adventureGateway;
	

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
		
		if(OptionsManager.getSingleton().isTestMode())
		{
			this.questGateway = new QuestRowDataGatewayMock(questID);
			this.adventureGateway = new AdventureTableDataGatewayMock();
		} else
		{
			this.questGateway = new QuestRowDataGatewayRDS(questID);
			this.adventureGateway = new AdventureTableDataGatewayRDS();
		}
		
		ArrayList<Adventure> adventureList = new ArrayList<Adventure>();
				
		for(AdventureRecord ar : adventureGateway.getAdventuresForQuest(questID))
		{
			adventureList.add(new Adventure(ar.getAdventureID(), ar.getAdventureDescription()));
		}
		
		Quest quest = new Quest(questGateway.getQuestID(), questGateway.getQuestDescription(), null, null, adventureList);
		return quest;
	}

	/**
	 * Return the quests that are associated with a certain map and position
	 * @param pos the position of the quest
	 * @param mapName the map that the quest is on
	 * @return an array list of questIDs
	 * @throws DatabaseException shouldn't
	 */
	public ArrayList<Integer> getQuestsByPosition(Position pos, String mapName) throws DatabaseException {
		if(OptionsManager.getSingleton().isTestMode())
		{
			return QuestRowDataGatewayMock.getQuestsForMapLocation(mapName, pos);
		} 
		return null;
	}
}
