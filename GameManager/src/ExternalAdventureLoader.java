import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import model.OptionsManager;
import data.GameLocation;
import data.QuestCompletionActionType;
import datasource.DatabaseException;
import datasource.QuestRowDataGatewayRDS;
import datatypes.Position;

public class ExternalAdventureLoader
{

	static String[] questNames = {"Well-Prepared","Early Bird","Well-Rounded","Well-Connected","Ready For Fun","Researcher","Going Beyond","Campus Aware"};

	public static void main(String[] args) throws FileNotFoundException, NumberFormatException, DatabaseException
	{
		OptionsManager.getSingleton().setUsingTestDB(false);
		OptionsManager.getSingleton().setTestMode(false);
		
		buildQuestTable();
		Scanner file = new Scanner(new File("adventures.csv"));
		file.nextLine();
//		while (file.hasNextLine())
//		{
//			String line = file.nextLine();
//			String[] split = line.split(",");
//			AdventureTableDataGatewayRDS.createRow(Integer.parseInt(split[0].trim()), split[2].trim(),
//					getIdForQuest(split[1].trim()), Integer.parseInt(split[5].trim()),
//					AdventureCompletionType.REAL_LIFE,
//					new CriteriaString(split[6].trim()) );
//			
//		}
		file.close();
	}

	private static void buildQuestTable() throws DatabaseException
	{
		QuestRowDataGatewayRDS.createTable();
		int count = 1;
		for(String qName:questNames)
		{
			new QuestRowDataGatewayRDS(count, qName, "Description for " + qName, "current.tmx", new Position(4,13), 5, 3, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), 
				    new GregorianCalendar(9999, Calendar.MARCH, 21).getTime());
			count++;
		}
		new QuestRowDataGatewayRDS(100, "On Ramping Quest", "Learn the game's controls", null, new Position(4,17), 6, 6, QuestCompletionActionType.TELEPORT, new GameLocation("current.tmx",
				new Position(4, 13)), new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), 
			    new GregorianCalendar(9999, Calendar.MARCH, 21).getTime());
		new QuestRowDataGatewayRDS(101, "Wandering around Quest", "Learn the game's maps", null, new Position(4,17), 6, 6, QuestCompletionActionType.NO_ACTION, null, new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), 
			    new GregorianCalendar(9999, Calendar.MARCH, 21).getTime());
	}

}
