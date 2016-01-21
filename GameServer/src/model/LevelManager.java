package model;

import java.util.ArrayList;
import java.util.Collections;

import model.OptionsManager;
import datasource.DatabaseException;
import datasource.LevelRecord;
import datasource.LevelTableDataGateway;
import datasource.LevelTableDataGatewayMock;
import datasource.LevelTableDataGatewayRDS;

/**
 * @author Merlin
 *
 */
public class LevelManager
{

	private static LevelManager singleton;
	private static ArrayList<LevelRecord> allLevels = new ArrayList<LevelRecord>();
	
	/**
	 * @return the only LevelManager in the system
	 * @throws DatabaseException shouln't
	 */
	public static synchronized LevelManager getSingleton() throws DatabaseException
	{
		if (singleton == null)
		{
			singleton = new LevelManager();
		}
		return singleton;
	}

	private LevelTableDataGateway gateway;
	
	private LevelManager() throws DatabaseException
	{
		if (OptionsManager.getSingleton().isTestMode())
		{
			gateway = new LevelTableDataGatewayMock();
		} else
		{
			gateway = new LevelTableDataGatewayRDS();
		}
		allLevels = gateway.getAllLevels();
		Collections.sort(allLevels);
	}
	
	/**
	 * Return the level record based on the amount of points sent in
	 * @param levelUpPoints the experience points being sent in
	 * @return level record based on the level up points
	 */
	public LevelRecord getLevelForPoints(int levelUpPoints)
	{
		int i = 0;
		while(levelUpPoints >= allLevels.get(i).getLevelUpPoints())
		{
			i++;
		}
		return allLevels.get(i);
	}

}
