import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.OptionsManager;
import data.AdventureCompletionCriteria;
import data.AdventureCompletionType;
import data.CriteriaString;
import datasource.AdventureTableDataGatewayRDS;
import datasource.DatabaseException;

public class ExternalAdventureLoader
{

	public static void main(String[] args) throws FileNotFoundException, NumberFormatException, DatabaseException
	{
		OptionsManager.getSingleton().setUsingTestDB(false);
		OptionsManager.getSingleton().setTestMode(false);
		Scanner file = new Scanner(new File("adventures.csv"));
		file.nextLine();
		while (file.hasNextLine())
		{
			String line = file.nextLine();
			String[] split = line.split(",");
			AdventureTableDataGatewayRDS.createRow(Integer.parseInt(split[0].trim()), split[2].trim(),
					getIdForQuest(split[1].trim()), Integer.parseInt(split[5].trim()),
					AdventureCompletionType.REAL_LIFE,
					new CriteriaString(split[6].trim()) );
			
		}
		file.close();
	}

	private static int getIdForQuest(String string)
	{
		String[] questNames = {"Well-Prepared","Early Bird","Well-Rounded","Well-Connected","Ready For Fun","Researcher","Going Beyond","Campus Aware"};

		for(int i=0;i<questNames.length;i++)
		{
			if (questNames[i].equals(string))
			{
				return i+1;
			}
		}
		return -1;
	}

}
