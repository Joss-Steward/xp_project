import model.OptionsManager;
import testData.AdventuresForTest;
import datasource.AdventureTableDataGatewayRDS;
import datasource.DatabaseException;


public class IntroductoryAdventuresLoader
{

	public static void main(String[] args) throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingTestDB(false);
		OptionsManager.getSingleton().setTestMode(false);
		for(AdventuresForTest adventure:AdventuresForTest.values())
		{
			if (adventure.getQuestID() == 100)
			{
				AdventureTableDataGatewayRDS.createRow(adventure.getAdventureID(),adventure.getAdventureDescription(),
						adventure.getQuestID(), adventure.getExperiencePointsGained(),
						adventure.getCompletionType(),
						adventure.getCompletionCriteria() );
			}
		}

	}

}
