package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;

public interface AdventureStateTableDataGateway
{

	ArrayList<AdventureStateRecord> getAdventureStates(int playerID, int questID);

}
