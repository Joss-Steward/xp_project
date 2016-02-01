package datasource;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Test;

import data.Position;
import datasource.DatabaseException;
import datasource.DatabaseTest;
import datasource.PlayerRowDataGateway;
import datasource.PlayersForTest;

/**
 * Tests required of all player gateways
 * @author Merlin
 *
 */
public abstract class PlayerRowDataGatewayTest extends DatabaseTest
{

	private PlayerRowDataGateway gateway;

	/**
	 * Find the gateway for a given player
	 * @param playerID the ID of the player we are testing
	 * @return the gateway
	 * @throws DatabaseException if the playerID can't be found in the data source
	 */
	abstract PlayerRowDataGateway findGateway(int playerID) throws DatabaseException;
	
	/**
	 * Make sure any static information is cleaned up between tests
	 * @throws SQLException shouldn't
	 * @throws DatabaseException shouldn't
	 */
	@After
	public void cleanup() throws DatabaseException, SQLException
	{
		super.tearDown();
		if (gateway != null)
		{
			gateway.resetData();
		}
	}
	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void finder() throws DatabaseException
	{
		PlayersForTest john = PlayersForTest.JOHN;
		gateway = findGateway(john.getPlayerID());
		assertEquals(john.getPlayerID(), gateway.getPlayerID());
		assertEquals(john.getPosition(), gateway.getPosition());
		assertEquals(john.getAppearanceType(), gateway.getAppearanceType());
		assertEquals(john.getExperiencePoints(), gateway.getExperiencePoints());
	}

	/**
	 * Make sure we can add a new user to the system
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void creation() throws DatabaseException
	{
		gateway = createGateway("mapName", new Position(3,2), "Warrior", 2, 13);

		PlayerRowDataGateway after = findGateway(gateway.getPlayerID());
		assertEquals(new Position(3,2), after.getPosition());
		assertEquals("Warrior", after.getAppearanceType());
		assertEquals(2,after.getQuizScore());
		assertEquals(13, after.getExperiencePoints());
	}

	/**
	 * Get a gateway that creates a row in the data source with the given information
	 * @param mapName the name of the map
	 * @param position the position on the map
	 * @param appearanceType the appearance type of the player
	 * @param quizScore This player's quiz score
	 * @param experiencePoints this player's experience points
	 * @return the gateway
	 * @throws DatabaseException if we fail to create the row
	 */
	abstract PlayerRowDataGateway createGateway(String mapName, Position position,
			String appearanceType, int quizScore, int experiencePoints) throws DatabaseException;
	
	/**
	 * make sure we get the right exception if we try to find someone who
	 * doesn't exist
	 * 
	 * @throws DatabaseException
	 *             should
	 */
	@Test(expected = DatabaseException.class)
	public void findNotExisting() throws DatabaseException
	{
		gateway = findGateway(11111);
	}

	
	/**
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void changePosition() throws DatabaseException
	{
		gateway = findGateway(PlayersForTest.MERLIN.getPlayerID());
		gateway.setPosition(new Position(42,32));
		gateway.persist();
		PlayerRowDataGateway after = findGateway(PlayersForTest.MERLIN
				.getPlayerID());
		assertEquals(new Position(42,32), after.getPosition());
	}
	
	/**
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void changeQuizScore() throws DatabaseException
	{
		gateway = findGateway(PlayersForTest.MERLIN.getPlayerID());
		gateway.setQuizScore(666);
		gateway.persist();
		PlayerRowDataGateway after = findGateway(PlayersForTest.MERLIN
				.getPlayerID());
		assertEquals(666, after.getQuizScore());
	}
	
	/**
	 * @throws DatabaseException 
	 * 				shouldn't
	 */
	@Test
	public void changeExperiencePoints() throws DatabaseException
	{
		gateway = findGateway(PlayersForTest.MERLIN.getPlayerID());
		gateway.setExperiencePoints(424);
		gateway.persist();
		
		PlayerRowDataGateway after = findGateway(PlayersForTest.MERLIN
				.getPlayerID());
		assertEquals(424, after.getExperiencePoints());		
	}
	
	/**
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void changeAppearanceType() throws DatabaseException
	{
		gateway = findGateway(PlayersForTest.MERLIN.getPlayerID());
		gateway.setAppearanceType("Ugly!");
		gateway.persist();
		PlayerRowDataGateway after = findGateway(PlayersForTest.MERLIN
				.getPlayerID());
		assertEquals("Ugly!", after.getAppearanceType());
	}
}
