import java.util.ArrayList;

import model.OptionsManager;
import datasource.DatabaseException;
import datasource.PlayerTableDataGatewayRDS;
import datatypes.PlayerScoreRecord;

public class HighScoreReport
{

	public static void main(String[] args) throws DatabaseException
	{

		OptionsManager.getSingleton().setTestMode(false);
		OptionsManager.getSingleton().setUsingTestDB(false);
		ArrayList<PlayerScoreRecord> data = PlayerTableDataGatewayRDS.getSingleton().getHighScoreList();
		for (PlayerScoreRecord rec:data)
		{
			System.out.println(rec.getCrewName() + ", " +rec.getPlayerName() + ", " + rec.getExperiencePoints());
		}

	}

}
