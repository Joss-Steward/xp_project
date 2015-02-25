package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.OptionsManager;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.datasource.MapAreaRowDataGatewayMock;
import edu.ship.shipsim.areaserver.datasource.MapAreasForTest;

/**
 * Tests that CommandAreaCollision triggers a quest on a player.
 * 
 * @author Ethan
 *
 */
public class CommandAreaCollisionTest 
{
	/**
	 * @throws DatabaseException exception if can't reset the database
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp() throws DatabaseException 
	{
		PlayerManager.resetSingleton();
		OptionsManager.getSingleton(true);
		new MapAreaRowDataGatewayMock(MapAreasForTest.ONE_MAP_AREA.getAreaName()).resetData();
	}
	
	/**
	 * Tests that a CommandAreaCollision when executed will 
	 * trigger a quest that a player has.
	 */
	@Test
	public void testCreateCommand() 
	{
		PlayerManager.getSingleton().addPlayer(1);
		int playerID = 1;
		Player p = PlayerManager.getSingleton().getPlayerFromID(playerID);
		ArrayList<AdventureState> adventureList = new ArrayList<AdventureState>();
		AdventureState a = new AdventureState(1, "hidde");
		adventureList.add(a);
		QuestState q = new QuestState(2, "hidden");
		q.addAdventures(adventureList);
		p.addQuest(q);
		String areaName = MapAreasForTest.ONE_MAP_AREA.getAreaName();
		CommandAreaCollision cac = new CommandAreaCollision(playerID, areaName);
		cac.execute();
		
		assertEquals(playerID, cac.getPlayerID());
		assertEquals(areaName, cac.getAreaName());
	}

}
