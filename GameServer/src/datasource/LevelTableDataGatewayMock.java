package datasource;

import java.util.ArrayList;
import java.util.HashMap;

import datasource.DatabaseException;
import datasource.LevelRecord;
import datasource.LevelsForTest;

/**
 * The mock version of LevelTableDataGatewas
 * 
 * @author Merlin
 *
 */
public class LevelTableDataGatewayMock implements LevelTableDataGateway
{

	private static LevelTableDataGateway singleton;

	/**
	 * @return the only one of these we can have
	 */
	public static LevelTableDataGateway getSingleton()
	{
		if (singleton == null)
		{
			singleton = new LevelTableDataGatewayMock();
		}
		return singleton;
	}

	private HashMap<String, Integer> data = new HashMap<String, Integer>();

	/**
	 * Constructor for LevelTableDataGatewayMock
	 */
	public LevelTableDataGatewayMock()
	{
		for (LevelsForTest l : LevelsForTest.values())
		{
			data.put(l.getDescription(), l.getLevelUpPoints());
		}
	}

	/**
	 * @see datasource.LevelTableDataGateway#getAllLevels()
	 */
	@Override
	public ArrayList<LevelRecord> getAllLevels() throws DatabaseException
	{
		ArrayList<LevelRecord> results = new ArrayList<LevelRecord>();
		for (String desc : data.keySet())
		{
			results.add(new LevelRecord(desc, data.get(desc)));
		}
		return results;
	}

}