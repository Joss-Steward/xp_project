import java.sql.SQLException;

import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGatewayRDS;
import edu.ship.shipsim.areaserver.datasource.AdventureStatesForTest;
import edu.ship.shipsim.areaserver.datasource.AdventureTableDataGatewayRDS;
import edu.ship.shipsim.areaserver.datasource.AdventuresForTest;
import edu.ship.shipsim.areaserver.datasource.QuestRowDataGatewayRDS;
import edu.ship.shipsim.areaserver.datasource.QuestStateTableDataGatewayRDS;
import edu.ship.shipsim.areaserver.datasource.QuestStatesForTest;
import edu.ship.shipsim.areaserver.datasource.QuestsForTest;

/**
 * Builds the Quests and Adventures portion of the database
 * 
 * @author Merlin
 * 
 */
public class BuildQuestsAndAdventures
{

	/**
	 * 
	 * @param args
	 *            unused
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws SQLException
	 *             shouldn't
	 */
	public static void main(String[] args) throws DatabaseException, SQLException
	{
		createAdventureTable();
		createQuestTable();
		createQuestStateTable();
		createAdventureStateTable();
	}

	private static void createQuestStateTable() throws DatabaseException
	{
		QuestStateTableDataGatewayRDS.getSingleton().createTable();
		for (QuestStatesForTest quest : QuestStatesForTest.values())
		{
			QuestStateTableDataGatewayRDS.getSingleton().createRow(quest.getPlayerID(), quest.getQuestID(),quest.getState());
		}
	}
	
	private static void createAdventureStateTable() throws DatabaseException
	{
		AdventureStateTableDataGatewayRDS.createTable();
		for (AdventureStatesForTest adventure : AdventureStatesForTest.values())
		{
			AdventureStateTableDataGatewayRDS.createRow(adventure.getQuestID(),adventure.getAdventureID(), adventure.getPlayerID(), adventure.getState());
		}
	}

	/**
	 * Create a table of quests
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	private static void createQuestTable() throws SQLException, DatabaseException
	{
		QuestRowDataGatewayRDS.createTable();
		for (QuestsForTest quest : QuestsForTest.values())
		{
			new QuestRowDataGatewayRDS(quest.getQuestID(),quest.getQuestDescription(), quest.getMapName(), quest.getPosition());
		}
	}
	/**
	 * Create a table of adventures
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	private static void createAdventureTable() throws SQLException, DatabaseException
	{
		AdventureTableDataGatewayRDS.createTable();
		for (AdventuresForTest adventure : AdventuresForTest.values())
		{
			AdventureTableDataGatewayRDS.createRow(adventure.getAdventureID(),adventure.getAdventureDescription(),
					adventure.getQuestID());
		}
	}
}
