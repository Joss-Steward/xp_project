package datasource;

import datasource.DatabaseException;
import datasource.PlayerRowDataGateway;
import datasource.PlayerRowDataGatewayMock;
import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;

/**
 * Tests for the mock version of the gateway
 * 
 * @author Merlin
 *
 */
public class PlayerRowDataGatewayMockTest extends PlayerRowDataGatewayTest
{

	/**
	 * @see datasource.PlayerRowDataGatewayTest#findGateway(int)
	 */
	@Override
	PlayerRowDataGateway findGateway(int playerID) throws DatabaseException
	{
		return new PlayerRowDataGatewayMock(playerID);
	}

	/**
	 * @see datasource.PlayerRowDataGatewayTest#createGateway(java.lang.String,
	 *      datatypes.Position, java.lang.String, int, int, Crew, major)
	 */
	@Override
	PlayerRowDataGateway createGateway(String mapName, Position position, String appearanceType, int quizScore,
			int experiencePoints, Crew crew, Major major) throws DatabaseException
	{
		return new PlayerRowDataGatewayMock(position, appearanceType, quizScore, experiencePoints, crew, major);
	}
}
