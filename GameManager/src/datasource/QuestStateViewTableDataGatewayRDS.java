package datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import datatypes.AdventureStateEnum;
import datatypes.QuestStateEnum;

public class QuestStateViewTableDataGatewayRDS
{

	public static List<QuestStateRecord> getAllQuestStateRecords(int playerID) throws DatabaseException
	{
		ArrayList<QuestStateRecord> records = new ArrayList<QuestStateRecord>();

		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(
					connection,
					"SELECT * FROM Quests INNER JOIN QuestStates ON Quests.QuestID = QuestStates.QuestID "
							+ " where playerID = ?");
			stmt.setInt(1, playerID);
			ResultSet result = stmt.executeQuery();
			while (result.next())
			{
				records.add(new QuestStateRecord(playerID, 
						result.getInt("QuestStates.questID"), 
						QuestStateEnum.values()[result
								.getInt("QuestStates.questState")],
						result.getInt("Quests.experiencePointsGained")));
			}

		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Unable to retrieve quests for player #"
							+ playerID, e);
		}
		return records;
	}

}
