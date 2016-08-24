import data.Crew;
import data.Major;
import data.Position;
import data.QuestStateEnum;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayRDS;
import datasource.PlayerLoginRowDataGatewayRDS;
import datasource.PlayerRowDataGatewayRDS;
import datasource.QuestStateTableDataGateway;
import datasource.QuestStateTableDataGatewayRDS;
import model.OptionsManager;


public class PlayerLoader
{
	private static int[] EXISTING_QUEST_IDS = {1,2,3,4,5,6,7,8,100,101};
	public static void main(String[] args) throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingTestDB(false);
		OptionsManager.getSingleton().setTestMode(false);
		new PlayerRowDataGatewayRDS(new Position(11,17),"male_a",0,0,Crew.OUT_OF_BOUNDS,Major.SOFTWARE_ENGINEERING);
		new PlayerLoginRowDataGatewayRDS( "NEWBIE" , "pw" );
		new PlayerConnectionRowDataGatewayRDS(1, 111, "sortingRoom.tmx");
		QuestStateTableDataGateway gateway = QuestStateTableDataGatewayRDS.getSingleton();
		for (int qestID:EXISTING_QUEST_IDS)
		{
			gateway.createRow(1, qestID, QuestStateEnum.HIDDEN, false);
		}
	}

}
