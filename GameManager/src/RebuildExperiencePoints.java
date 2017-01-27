import java.util.List;

import model.OptionsManager;
import model.PlayerID;
import datasource.AdventureStateRecord;
import datasource.AdventureStateViewTableDataGatewayRDS;
import datasource.DatabaseException;
import datasource.PlayerLoginTableDataGatewayRDS;
import datasource.PlayerRowDataGateway;
import datasource.PlayerRowDataGatewayRDS;
import datasource.QuestStateRecord;
import datasource.QuestStateViewTableDataGatewayRDS;
import datatypes.AdventureStateEnum;
import datatypes.QuestStateEnum;

public class RebuildExperiencePoints
{

	public static void main(String[] args) throws DatabaseException
	{

		OptionsManager.getSingleton().setTestMode(false);
		OptionsManager.getSingleton().setUsingTestDB(false);
		List<PlayerID> players = PlayerLoginTableDataGatewayRDS
				.getPlayerIDList();
		for (PlayerID playerID : players)
		{
			int sum = 0;
			List<AdventureStateRecord> adventures = AdventureStateViewTableDataGatewayRDS
					.getAllAdventureStateRecords(playerID.getPlayerID());
			for (AdventureStateRecord adventure : adventures)
			{
				if (adventure.getState().equals(AdventureStateEnum.COMPLETED))
				{
					sum = sum + adventure.getExperiencePoints();
				}
			}
			List<QuestStateRecord> quests = QuestStateViewTableDataGatewayRDS
					.getAllQuestStateRecords(playerID.getPlayerID());
			for (QuestStateRecord quest : quests)
			{
				if (quest.getState().equals(QuestStateEnum.COMPLETED) ||quest.getState().equals(QuestStateEnum.FULFILLED))
				{
					sum = sum + quest.getExperiencePoints();
				}
			}
			
			PlayerRowDataGateway playerGateway = new PlayerRowDataGatewayRDS(playerID.getPlayerID());
			int currentPoints = playerGateway.getExperiencePoints();
			if (currentPoints != sum)
			{
				System.out.println("Updating player " + playerID.getPlayerName() + " from " + currentPoints + " to " + sum);
				playerGateway.setExperiencePoints(sum);
				playerGateway.persist();
			}
		}

	}

}
