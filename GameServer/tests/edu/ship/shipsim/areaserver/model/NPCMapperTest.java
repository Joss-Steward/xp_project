package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import datasource.DatabaseException;
import datasource.PlayersForTest;
import edu.ship.shipsim.areaserver.datasource.NPCsForTest;

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
		return PlayersForTest.MOCK_NPC2;
	}
	
	@Test
	public void fillsExisting() throws DatabaseException
	{
		NPCMapper pm = new NPCMapper(NPCsForTest.NPC2.getPlayerID());
		NPC p = (NPC) pm.getPlayer();
		NPCsForTest testPlayer = NPCsForTest.NPC2;
		assertEquals(testPlayer.getBehaviorClass(),p.getBehaviorClass());
	}
	
	@Test
	public void findsForMap() throws DatabaseException
	{
		ArrayList<NPCMapper> npcs = NPCMapper.findNPCsOnMap("current.tmx");
		assertEquals(2, npcs.size());
		assertEquals(PlayersForTest.MOCK_NPC2.getPlayerID(),npcs.get(0).getPlayer().getID());
		assertEquals(PlayersForTest.MOCK_NPC3.getPlayerID(),npcs.get(1).getPlayer().getID());
	}
}
