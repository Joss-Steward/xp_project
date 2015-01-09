package edu.ship.shipsim.areaserver.datasource;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class PlayerRowDataGatewayTest
{

	abstract PlayerRowDataGateway createGateway();
	
	@Test
	public void finder()
	{
		PlayerRowDataGateway getway = createGateway();
	}

}
