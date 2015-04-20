import java.sql.SQLException;

import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.datasource.LevelTableDataGatewayRDS;
import edu.ship.shipsim.areaserver.datasource.LevelsForTest;

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
			LevelTableDataGatewayRDS.getSingleton().createRow(level.getDescription(), level.getLevelUpPoints());
		}
	}
}
