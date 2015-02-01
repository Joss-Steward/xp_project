package edu.ship.shipsim.areaserver.model;

import java.util.ArrayList;

import model.OptionsManager;
import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.datasource.NPCRowDataGateway;
import edu.ship.shipsim.areaserver.datasource.NPCRowDataGatewayMock;
import edu.ship.shipsim.areaserver.datasource.NPCRowDataGatewayRDS;

public class NPCMapper extends PlayerMapper
{

	private NPCRowDataGateway NPCGateway;

	public NPCMapper(int playerID) throws DatabaseException
	{
		super(playerID);
		if (OptionsManager.getSingleton().isTestMode())
		{
			this.NPCGateway = new NPCRowDataGatewayMock(playerID);
		} else
		{
			this.NPCGateway = new NPCRowDataGatewayRDS(playerID);
		}
		NPC thisPlayer = (NPC)player;
		thisPlayer.setBehaviorClass(NPCGateway.getBehaviorClass());
	}

	@Override
	protected Player createPlayerObject()
	{
		return new NPC();
	}

	public static ArrayList<NPCMapper> findNPCsOnMap(String string) throws DatabaseException
	{
		ArrayList<NPCRowDataGateway> gateways;
		if (OptionsManager.getSingleton().isTestMode())
		{
			gateways = NPCRowDataGatewayMock.getNPCsForMap(string);
		} else
		{
			gateways = NPCRowDataGatewayRDS.getNPCsForMap(string);
		}
		ArrayList<NPCMapper> mappers = new ArrayList<NPCMapper>();
		for (NPCRowDataGateway gateway: gateways)
		{
			mappers.add(new NPCMapper(gateway.getPlayerID()));
		}
		return mappers;
	}

}
