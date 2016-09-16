import testData.PlayersForTest;
import model.OptionsManager;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayRDS;
import datasource.PlayerLoginRowDataGatewayRDS;

/**
 * Builds the login portion of the database
 * 
 * @author Merlin
 * 
 */
public class BuildTestDBPlayerLogin
{

	/**
	 * 
	 * @param args
	 *            unused
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public static void main(String[] args) throws DatabaseException
	{
		OptionsManager.getSingleton().setTestMode(true);
		OptionsManager.getSingleton().setUsingTestDB(true);
		createPlayerLoginTable();
		createPlayerConnectionTable();
	}

	private static void createPlayerLoginTable() throws DatabaseException
	{
		PlayerLoginRowDataGatewayRDS.createPlayerLoginTable();
		
		for (PlayersForTest p : PlayersForTest.values())
		{
			new PlayerLoginRowDataGatewayRDS( p.getPlayerName() , p.getPlayerPassword() );
		}
		
	}

	private static void createPlayerConnectionTable() throws DatabaseException
	{
		PlayerConnectionRowDataGatewayRDS.createPlayerConnectionTable();
		
		for (PlayersForTest p : PlayersForTest.values())
		{
			new PlayerConnectionRowDataGatewayRDS(p.getPlayerID(), p.getPin(), p.getMapName());
			
		}
	}

	
}
