package datasource;

import data.Crew;
import data.Position;
import datasource.DatabaseException;
import datasource.PlayerRowDataGateway;
import datasource.PlayerRowDataGatewayMock;


/**
 * Tests for the mock version of the gateway
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
	 * @see datasource.PlayerRowDataGatewayTest#createGateway(java.lang.String, data.Position, java.lang.String, int, int, Crew)
	 */
	@Override
	PlayerRowDataGateway createGateway(String mapName, Position position,
			String appearanceType, int quizScore, int experiencePoints, Crew crew) throws DatabaseException
	{
		return new PlayerRowDataGatewayMock(position, appearanceType, quizScore, experiencePoints, crew);
	}

	

}
