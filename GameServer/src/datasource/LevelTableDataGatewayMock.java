package datasource;

import java.util.ArrayList;
import java.util.HashMap;

import testData.LevelsForTest;
import datasource.DatabaseException;
import datasource.LevelRecord;

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

	private HashMap<String, LevelRecord> data = new HashMap<String, LevelRecord>();


	/**
	 * Constructor for LevelTableDataGatewayMock
	 */
	public LevelTableDataGatewayMock()
	{
		for (LevelsForTest l : LevelsForTest.values())
		{
			data.put(l.getDescription(), new LevelRecord(l.getDescription(),l.getLevelUpPoints(),l.getLevelUpMonth(),l.getLevelUpDayOfMonth()));
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
			results.add(data.get(desc));
		}
		return results;
	}

}
