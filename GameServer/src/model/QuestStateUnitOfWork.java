package model;

import java.util.ArrayList;

import model.OptionsManager;
import datasource.AdventureStateTableDataGateway;
import datasource.AdventureStateTableDataGatewayMock;
import datasource.AdventureStateTableDataGatewayRDS;
import datasource.DatabaseException;
import datasource.QuestStateTableDataGateway;
import datasource.QuestStateTableDataGatewayMock;
import datasource.QuestStateTableDataGatewayRDS;

/**
 * Encapsulates one set of changes to the states of quests and adventures
 * 
 * @author Merlin
 *
 */
public class QuestStateUnitOfWork
{

	private int playerID;
	private QuestStateTableDataGateway questStateGateway;
	private AdventureStateTableDataGateway adventureStateGateway;

	/**
	 * @param playerID the player whose quests we are monitoring
	 * @throws DatabaseException if we can't get a gateway to the quests or
	 *             adventures for this player
	 */
	public QuestStateUnitOfWork(int playerID) throws DatabaseException
	{
		this.playerID = playerID;
		if (OptionsManager.getSingleton().isTestMode())
		{
			this.questStateGateway = QuestStateTableDataGatewayMock.getSingleton();
			this.adventureStateGateway = AdventureStateTableDataGatewayMock.getSingleton();
		} else
		{
			this.questStateGateway = QuestStateTableDataGatewayRDS.getSingleton();
			this.adventureStateGateway = AdventureStateTableDataGatewayRDS.getSingleton();
		}
	}

	/**
	 * Persist all of the changes at once
	 * 
	 * @throws DatabaseException if we can't persist all the way to the db
	 */
	public void persist() throws DatabaseException
	{
		ArrayList<QuestState> questList = QuestManager.getSingleton().getQuestList(playerID);
		if (questList != null)
		{
			for (QuestState quest : questList)
			{
				questStateGateway.udpateState(playerID, quest.getID(), quest.getStateValue(),
						quest.isNeedingNotification());
				for (AdventureState a : quest.getAdventureList())
				{
					adventureStateGateway.updateState(playerID, quest.getID(), a.getID(), a.getState(),
							a.isNeedingNotification());
				}
			}
		}
	}

}
