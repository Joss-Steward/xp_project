package datasource;

import java.util.ArrayList;
import java.util.Collections;

import testData.PlayersForTest;
import data.PlayerScoreRecord;

/**
 * A mock data source that provides a table data gateway view into the Players
 * table
 * 
 * @author Merlin
 *
 */
public class PlayerTableDataGatewayMock implements PlayerTableDataGateway
{

	private static PlayerTableDataGateway singleton;

	private ArrayList<PlayerScoreRecord> data;

	/**
	 * Retrieves the mock gateway singleton.
	 * 
	 * @return singleton
	 */
	public static synchronized PlayerTableDataGateway getSingleton()
	{
		if (singleton == null)
		{
			singleton = new PlayerTableDataGatewayMock();
		}
		return singleton;
	}

	/**
	 * just build the data from the PlayersForTest enum
	 */
	public PlayerTableDataGatewayMock()
	{
		resetData();
	}

	/**
	 * @see datasource.PlayerTableDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		data = new ArrayList<PlayerScoreRecord>();
		for (PlayersForTest p : PlayersForTest.values())
		{
			data.add(new PlayerScoreRecord(p.getPlayerName(), p.getExperiencePoints()));
		}
		Collections.sort(data);

	}

	/**
	 * @see datasource.PlayerTableDataGateway#getTopTenList()
	 */
	@Override
	public ArrayList<PlayerScoreRecord> getTopTenList()
	{
		ArrayList<PlayerScoreRecord> result = new ArrayList<PlayerScoreRecord>();
		for (int i = 0; i < 10; i++)
		{
			if (i < data.size())
			{
				result.add(data.get(i));
			}
		}
		return result;
	}
}
