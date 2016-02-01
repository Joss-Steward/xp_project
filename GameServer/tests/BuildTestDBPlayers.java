
import java.sql.SQLException;

import model.OptionsManager;
import datasource.DatabaseException;
import datasource.NPCRowDataGatewayRDS;
import datasource.NPCsForTest;
import datasource.PlayerRowDataGatewayRDS;
import datasource.PlayersForTest;

/**
 * Builds the Player portion of the database
 * 
 * @author Merlin
 * 
 */
public class BuildTestDBPlayers
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
		OptionsManager.getSingleton().setTestMode(false);
		OptionsManager.getSingleton().setUsingTestDB(true);
		createPlayerTable();
		createNpcTable();
	}
	
	private static void createPlayerTable() throws SQLException, DatabaseException
	{
		PlayerRowDataGatewayRDS.createTable();

		for (PlayersForTest p : PlayersForTest.values())
		{
			new PlayerRowDataGatewayRDS(p.getPosition(), p.getAppearanceType(), p.getQuizScore(), p.getExperiencePoints());
		}

	}
	
	private static void createNpcTable() throws SQLException, DatabaseException
	{
		NPCRowDataGatewayRDS.createTable();
		
		for (NPCsForTest p : NPCsForTest.values())
		{
			new NPCRowDataGatewayRDS(p.getPlayerID(),p.getBehaviorClass());
		}
	}
}
