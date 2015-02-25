import java.sql.SQLException;

import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.datasource.MapAreaRowDataGatewayRDS;
import edu.ship.shipsim.areaserver.datasource.MapAreasForTest;

/**
 * Builds the Map Area portion of the database
 * 
 * @author Merlin
 * 
 */
public class BuildMapAreas
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
		createMapAreaTable();
	}

	/**
	 * Create a table of map areas
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	private static void createMapAreaTable() throws SQLException, DatabaseException
	{
		MapAreaRowDataGatewayRDS.createTable();
		for (MapAreasForTest area : MapAreasForTest.values())
		{
			new MapAreaRowDataGatewayRDS(area.getAreaName(), area.getQuestID());
		}
	}
}
