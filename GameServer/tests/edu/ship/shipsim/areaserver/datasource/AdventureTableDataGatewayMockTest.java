package edu.ship.shipsim.areaserver.datasource;

import static org.junit.Assert.*;
import org.junit.Test;

public class AdventureTableDataGatewayMockTest extends
		AdventureTableDataGatewayTest
{

	@Override
	public AdventureTableDataGateway getGateway()
	{
		return  AdventureTableDataGatewayMock.getSingleton();
	}

}
