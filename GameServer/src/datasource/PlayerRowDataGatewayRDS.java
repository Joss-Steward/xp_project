package datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import data.Position;
import datasource.ClosingPreparedStatement;
import datasource.DatabaseException;
import datasource.DatabaseManager;

/**
 * The RDS version of the gateway
 * 
 * @author Merlin
 *
 */
public class PlayerRowDataGatewayRDS implements PlayerRowDataGateway
{

	/**
	 * Drop the table if it exists and re-create it empty
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public static void createTable() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"DROP TABLE IF EXISTS Players");
			stmt.executeUpdate();
			stmt.close();

			stmt = new ClosingPreparedStatement(
					connection,
					"Create TABLE Players (playerID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  row INTEGER, col INTEGER, " +
					"appearanceType VARCHAR(255), quizScore INTEGER, experiencePoints INTEGER)");
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the player table", e);
		}
	}

	private int playerID;
	private Position position;
	private String appearanceType;
	private int quizScore;
	private int experiencePoints;
	
	private Connection connection;


	/**
	 * finder constructor
	 * 
	 * @param playerID
	 *            the unique ID of the player we are working with
	 * @throws DatabaseException
	 *             if we cannot find that player in the database
	 */
	public PlayerRowDataGatewayRDS(int playerID) throws DatabaseException
	{
		this.playerID = playerID;
		this.connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"SELECT * FROM Players WHERE playerID = ?");
			stmt.setInt(1, playerID);
			ResultSet result = stmt.executeQuery();
			result.next();
			this.position = (Position) new Position(result.getInt("row"),
					result.getInt("col"));
			this.appearanceType = result.getString("appearanceType");
			this.quizScore = result.getInt("quizScore");
			this.experiencePoints = result.getInt("experiencePoints");

		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find a player with ID " + playerID, e);
		}
	}

	/**
	 * create constructor
	 * 
	 * @param position
	 *            the row/column of the position the player is in
	 * @param appearanceType
	 *            the appearance type this player should be rendered with
	 * @param quizScore this player's current quiz score
	 * @param experiencePoints this player's experience points
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public PlayerRowDataGatewayRDS(Position position,
			String appearanceType, int quizScore, int experiencePoints) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(
					connection,
					"Insert INTO Players SET row = ?, col = ?, appearanceType = ?, quizScore = ?, experiencePoints = ?",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, position.getRow());
			stmt.setInt(2, position.getColumn());
			stmt.setString(3, appearanceType);
			stmt.setInt(4,  quizScore);
			stmt.setInt(5, experiencePoints);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next())
			{
				playerID = rs.getInt(1);
			}

		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't create a player record for player with ID " + playerID, e);
		}
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getAppearanceType()
	 */
	@Override
	public String getAppearanceType()
	{
		return appearanceType;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getPlayerID()
	 */
	@Override
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getPosition()
	 */
	@Override
	public Position getPosition()
	{
		return position;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getQuizScore()
	 */
	@Override
	public int getQuizScore()
	{
		return quizScore;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#persist()
	 */
	@Override
	public void persist() throws DatabaseException
	{
		this.connection = DatabaseManager.getSingleton().getConnection();

		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"UPDATE Players SET row = ?, col = ?, appearanceType = ?, quizScore = ?, experiencePoints = ? WHERE playerID = ?");
			stmt.setInt(1, position.getRow());
			stmt.setInt(2, position.getColumn());
			stmt.setString(3, appearanceType);
			stmt.setInt(4, quizScore);
			stmt.setInt(5, experiencePoints);
			stmt.setInt(6, playerID);
			stmt.executeUpdate();
		} catch (SQLException e)
		{

			throw new DatabaseException("Couldn't persist info for player with ID "
					+ playerID, e);
		}
	}

	/**
	 * @see datasource.PlayerRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setAppearanceType(java.lang.String)
	 */
	@Override
	public void setAppearanceType(String appearanceType)
	{
		this.appearanceType = appearanceType;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setPosition(data.Position)
	 */
	@Override
	public void setPosition(Position position)
	{
		this.position = position;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setQuizScore(int)
	 */
	@Override
	public void setQuizScore(int quizScore)
	{
		this.quizScore = quizScore;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getExperiencePoints()
	 */
	@Override
	public int getExperiencePoints()
	{
		return experiencePoints;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setExperiencePoints(int)
	 */
	@Override
	public void setExperiencePoints(int experiencePoints)
	{
		this.experiencePoints = experiencePoints;
	}

}
