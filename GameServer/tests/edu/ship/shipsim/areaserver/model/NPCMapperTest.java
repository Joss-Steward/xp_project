package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import datasource.DatabaseException;
import datasource.PlayersForTest;
import edu.ship.shipsim.areaserver.datasource.NPCsForTest;

/**
 * Test NPC Mappers
 * 
 * @author Merlin
 *
 */
public class NPCMapperTest extends PlayerMapperTest
{
	@Override
	protected PlayerMapper getMapper() throws DatabaseException
	{
		return new NPCMapper(getPlayerWeAreTesting().getPlayerID());
	}

	@Override
	protected PlayersForTest getPlayerWeAreTesting()
	{
		return PlayersForTest.QUIZBOT;
	}

	/**
	 * When we get the mapper for an existing player, make sure that the NPC
	 * specific information is created
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void fillsExisting() throws DatabaseException
	{
		NPCMapper pm = new NPCMapper(NPCsForTest.NPC2.getPlayerID());
		NPC p = (NPC) pm.getPlayer();
		NPCsForTest testPlayer = NPCsForTest.NPC2;
		assertEquals(testPlayer.getBehaviorClass(), p.getBehaviorClass());
	}

	/**
	 * Make sure we can find all of the NPCs for a given map
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void findsForMap() throws DatabaseException
	{
		ArrayList<NPCMapper> npcs = NPCMapper.findNPCsOnMap("current.tmx");
		assertEquals(2, npcs.size());
		assertEquals(PlayersForTest.QUIZBOT.getPlayerID(), npcs.get(0).getPlayer()
				.getID());
		assertEquals(PlayersForTest.MOCK_NPC3.getPlayerID(), npcs.get(1).getPlayer()
				.getID());
	}
}
