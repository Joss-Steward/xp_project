import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import data.AdventureRecord;
import data.AdventureStateEnum;
import data.Crew;
import data.Major;
import data.Position;
import data.QuestStateEnum;
import datasource.AdventureStateTableDataGateway;
import datasource.AdventureStateTableDataGatewayRDS;
import datasource.AdventureTableDataGateway;
import datasource.AdventureTableDataGatewayRDS;
import datasource.DatabaseException;
import datasource.NPCRowDataGatewayRDS;
import datasource.PlayerConnectionRowDataGatewayRDS;
import datasource.PlayerLoginRowDataGatewayRDS;
import datasource.PlayerRowDataGatewayRDS;
import datasource.QuestStateTableDataGateway;
import datasource.QuestStateTableDataGatewayRDS;
import model.OptionsManager;

public class PlayerLoader
{
	private static int[] EXISTING_QUEST_IDS =
	{ 1, 2, 3, 4, 5, 6, 7, 8, 100, 101 };

	public static void main(String[] args) throws DatabaseException, FileNotFoundException
	{
		OptionsManager.getSingleton().setUsingTestDB(false);
		OptionsManager.getSingleton().setTestMode(false);

		PlayerRowDataGatewayRDS.createTable();
		PlayerLoginRowDataGatewayRDS.createPlayerLoginTable();
		PlayerConnectionRowDataGatewayRDS.createPlayerConnectionTable();
		QuestStateTableDataGatewayRDS.getSingleton().createTable();
		NPCRowDataGatewayRDS.createTable();
		
		// make the red hat
		PlayerRowDataGatewayRDS redHat = new PlayerRowDataGatewayRDS(new Position(9, 7), "RedHat", 0, 0,
				Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING);
		new PlayerLoginRowDataGatewayRDS("Red Hat", "");
		new PlayerConnectionRowDataGatewayRDS(redHat.getPlayerID(), 111, "sortingRoom.tmx");
		new NPCRowDataGatewayRDS(redHat.getPlayerID(), "model.RedHatBehavior");
		// make the quizbot
		PlayerRowDataGatewayRDS quizBot = new PlayerRowDataGatewayRDS(new Position(4, 19), "Magi", 0, 0,
				Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING);
		new PlayerLoginRowDataGatewayRDS("QuizBot", "");
		new PlayerConnectionRowDataGatewayRDS(quizBot.getPlayerID(), 111, "quiznasium.tmx");
		new NPCRowDataGatewayRDS(quizBot.getPlayerID(), "model.QuizBotBehavior");
		
		//create newbie
		PlayerRowDataGatewayRDS thisPlayer = new PlayerRowDataGatewayRDS(new Position(11, 7), "male_a", 0, 0,
				Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING);
		new PlayerLoginRowDataGatewayRDS("NEWBIE", "pw");
		new PlayerConnectionRowDataGatewayRDS(thisPlayer.getPlayerID(), 111, "sortingRoom.tmx");
		loadQuestStates(thisPlayer.getPlayerID());
		loadAdventureStates(thisPlayer.getPlayerID());
		
		createPlayersFromFile("CSCF16.csv");
	}

	private static void createPlayersFromFile(String string) throws FileNotFoundException
	{
		Scanner input = new Scanner(new File(string));
		
	}

	private static void loadQuestStates(int playerID) throws DatabaseException
	{
		QuestStateTableDataGateway gateway = QuestStateTableDataGatewayRDS.getSingleton();
		gateway.createTable();
		for (int questID : EXISTING_QUEST_IDS)
		{
			if (questID == 100)
			{
				gateway.createRow(playerID, questID, QuestStateEnum.TRIGGERED, true);
			} else
			{
				gateway.createRow(playerID, questID, QuestStateEnum.AVAILABLE, false);
			}
		}
	}

	private static void loadAdventureStates(int playerID) throws DatabaseException
	{
		AdventureTableDataGateway adventureGateway = AdventureTableDataGatewayRDS
				.getSingleton();
		AdventureStateTableDataGateway adventureStateGateway = AdventureStateTableDataGatewayRDS
				.getSingleton();
		adventureStateGateway.createTable();
		for (int questID : EXISTING_QUEST_IDS)
		{
			ArrayList<AdventureRecord> adventures = adventureGateway
					.getAdventuresForQuest(questID);
			for (AdventureRecord adventure : adventures)
			{
				AdventureStateEnum state;
			
				if (questID == 100)
					state = AdventureStateEnum.TRIGGERED;
				else
					state = AdventureStateEnum.HIDDEN;
				
				adventureStateGateway.createRow(playerID, questID, adventure.getAdventureID(), state,
						false);
				
			}
		}
	}

}
