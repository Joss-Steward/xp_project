package datasource;

import data.Position;
import datasource.DatabaseException;
import datasource.PlayerRowDataGateway;
import datasource.PlayerRowDataGatewayRDS;


/**
 * Tests for the RDS version of the gateway
 * @author Merlin
 *
 */
public class PlayerRowDataGatewayRDSTest extends PlayerRowDataGatewayTest
{

	/**
	 * @see datasource.PlayerRowDataGatewayTest#findGateway(int)
	 */
	@Override
	PlayerRowDataGateway findGateway(int playerID) throws DatabaseException
	{
		return new PlayerRowDataGatewayRDS(playerID);
	}

	/**
	 * @see datasource.PlayerRowDataGatewayTest#createGateway(java.lang.String, data.Position, java.lang.String, int, int)
	 */
	@Override
	PlayerRowDataGateway createGateway(String mapName, Position position,
			String appearanceType, int quizScore, int experiencePoints) throws DatabaseException
	{
		return new PlayerRowDataGatewayRDS(position, appearanceType, quizScore, experiencePoints);
	}

	

}
