package datasource;

import datasource.DatabaseException;
import datasource.PlayerRowDataGateway;
import datasource.PlayerRowDataGatewayRDS;
import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;


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
	 * @see datasource.PlayerRowDataGatewayTest#createGateway(java.lang.String, datatypes.Position, java.lang.String, int, int, datatypes.Crew)
	 */
	@Override
	PlayerRowDataGateway createGateway(String mapName, Position position,
			String appearanceType, int quizScore, int experiencePoints, Crew crew, Major major) throws DatabaseException
	{
		return new PlayerRowDataGatewayRDS(position, appearanceType, quizScore, experiencePoints, crew, major);
	}

	

}
