import java.sql.SQLException;

import testData.LevelsForTest;
import model.OptionsManager;
import datasource.DatabaseException;
import datasource.LevelTableDataGatewayRDS;

/**
 * Builds the Level portion of the database
 * 
 * @author Merlin
 * 
 */
public class BuildTestLevels
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
		createLevelTable();
	}

	/**
	 * Create a table of levels
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	private static void createLevelTable() throws SQLException, DatabaseException
	{
		LevelTableDataGatewayRDS.createTable();
		for (LevelsForTest level : LevelsForTest.values())
		{
			LevelTableDataGatewayRDS.getSingleton().createRow(level.getDescription(), level.getLevelUpPoints(), level.getLevelUpMonth(), level.getLevelUpDayOfMonth());
		}
	}
}
