package edu.ship.shipsim.areaserver.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.DatabaseManager;
import data.Position;
import datasource.ClosingPreparedStatement;
import datasource.DatabaseException;

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
					"Create TABLE Players (playerID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, mapName VARCHAR(80), row INTEGER, col INTEGER, " +
					"appearanceType VARCHAR(255), quizScore INTEGER, experiencePoints INTEGER)");
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the player table", e);
		}
	}

	private int playerID;
	private String mapName;
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
			this.mapName = result.getString("mapName");
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
	 * @param mapName
	 *            the name of the map this player is on
	 * @param position
	 *            the row/column of the position the player is in
	 * @param appearanceType
	 *            the appearance type this player should be rendered with
	 * @param quizScore TODO
	 * @param experiencePoints TODO
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public PlayerRowDataGatewayRDS(String mapName, Position position,
			String appearanceType, int quizScore, int experiencePoints) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(
					connection,
					"Insert INTO Players SET mapName = ?, row = ?, col = ?, appearanceType = ?, quizScore = ?, experiencePoints = ?",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, mapName);
			stmt.setInt(2, position.getRow());
			stmt.setInt(3, position.getColumn());
			stmt.setString(4, appearanceType);
			stmt.setInt(5,  quizScore);
			stmt.setInt(6, experiencePoints);
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
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#getAppearanceType()
	 */
	@Override
	public String getAppearanceType()
	{
		return appearanceType;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#getMapName()
	 */
	@Override
	public String getMapName()
	{
		return mapName;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#getPlayerID()
	 */
	@Override
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#getPosition()
	 */
	@Override
	public Position getPosition()
	{
		return position;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#getQuizScore()
	 */
	@Override
	public int getQuizScore()
	{
		return quizScore;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#persist()
	 */
	@Override
	public void persist() throws DatabaseException
	{
		this.connection = DatabaseManager.getSingleton().getConnection();

		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"UPDATE Players SET mapName = ?, row = ?, col = ?, appearanceType = ?, quizScore = ? WHERE playerID = ?");
			stmt.setString(1, mapName);
			stmt.setInt(2, position.getRow());
			stmt.setInt(3, position.getColumn());
			stmt.setString(4, appearanceType);
			stmt.setInt(5, quizScore);
			stmt.setInt(6, playerID);
			stmt.executeUpdate();
		} catch (SQLException e)
		{

			throw new DatabaseException("Couldn't persist info for player with ID "
					+ playerID, e);
		}
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#setAppearanceType(java.lang.String)
	 */
	@Override
	public void setAppearanceType(String appearanceType)
	{
		this.appearanceType = appearanceType;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#setMapName(java.lang.String)
	 */
	@Override
	public void setMapName(String mapName)
	{
		this.mapName = mapName;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#setPosition(data.Position)
	 */
	@Override
	public void setPosition(Position position)
	{
		this.position = position;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#setQuizScore(int)
	 */
	@Override
	public void setQuizScore(int quizScore)
	{
		this.quizScore = quizScore;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#getExperiencePoints()
	 */
	@Override
	public int getExperiencePoints()
	{
		return experiencePoints;
	}

}
