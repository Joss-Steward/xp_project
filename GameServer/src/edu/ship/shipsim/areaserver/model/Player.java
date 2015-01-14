package edu.ship.shipsim.areaserver.model;

import model.DatabaseException;
import model.OptionsManager;
import model.PlayerConnection;
import model.PlayerLogin;
import model.QualifiedObservable;
import data.Position;
import edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway;
import edu.ship.shipsim.areaserver.datasource.PlayerRowDataGatewayMock;
import edu.ship.shipsim.areaserver.datasource.PlayerRowDataGatewayRDS;
import edu.ship.shipsim.areaserver.model.reports.PinFailedReport;
import edu.ship.shipsim.areaserver.model.reports.PlayerMovedReport;
import edu.ship.shipsim.areaserver.model.reports.QuestScreenReport;

/**
 * Very simple for now . . .
 * 
 * @author Merlin
 * 
 */
public class Player extends QualifiedObservable
{

	private PlayerRowDataGateway gateway;

	private PlayerLogin playerLogin;

	private int quizScore;

	Player()
	{
		registerOurReportTypes();
	}

	/**
	 * Finder constructor
	 * 
	 * @param playerID
	 *            the player's unique ID
	 * @throws DatabaseException
	 *             if we can't find the player
	 */
	public Player(int playerID) throws DatabaseException
	{
		this();

		try
		{
			if (OptionsManager.getSingleton().isTestMode())
			{
				this.gateway = new PlayerRowDataGatewayMock(playerID);
			} else
			{
				this.gateway = new PlayerRowDataGatewayRDS(playerID);
			}
		} catch (DatabaseException e)
		{
			throw new DatabaseException("no login information for player with id "
					+ playerID);
		}
		this.playerLogin = new PlayerLogin(playerID);
	}

	/**
	 * Check the pin to make sure it is correct with regards to contents and
	 * expiration
	 * 
	 * @param pinToCheck
	 *            the pin we gave the player to connect to this area server
	 * @return true or false with pin validity
	 * @throws DatabaseException
	 *             if the data source had an exception
	 */

	public boolean isPinValid(double pinToCheck) throws DatabaseException
	{
		PlayerConnection pl = new PlayerConnection(gateway.getPlayerID());
		PinFailedReport report = null;

		if (!pl.isPinValid(pinToCheck))
		{
			report = new PinFailedReport(PlayerConnection.ERROR_PIN_NOT_EXIST);
		} else if (pl.isExpired())
		{
			report = new PinFailedReport(PlayerConnection.ERROR_PIN_EXPIRED);
		}

		if (report != null)
		{
			System.err.println("Pin is not valid for " + gateway.getPlayerID()
					+ " because " + report.toString());
			this.notifyObservers(report);
			return false;
		}
		return true;
	}

	/**
	 * Get the appearance type for how this player should be drawn
	 * 
	 * @return a string matching one of the enum names in the PlayerType enum
	 */
	public String getAppearanceType()
	{
		return gateway.getAppearanceType();
	}

	/**
	 * @return the id of this player
	 */
	public int getID()
	{
		return gateway.getPlayerID();
	}

	/**
	 * Get the unique player name of this player
	 * 
	 * @return the player's name
	 */
	public String getPlayerName()
	{
		return playerLogin.getPlayerName();
	}

	/**
	 * Get the player's position
	 * 
	 * @return playerPosition Returns the player position. If a position is not
	 *         set should return null.
	 */
	public Position getPlayerPosition()
	{
		return gateway.getPosition();
	}

	private void registerOurReportTypes()
	{
		reportTypes.add(PlayerMovedReport.class);
		reportTypes.add(QuestScreenReport.class);
		reportTypes.add(PinFailedReport.class);

		this.registerReportTypesWeNotify();
	}

	/**
	 * Set the appearance type for this player
	 * 
	 * @param appearanceType
	 *            the new appearance type
	 */
	public void setAppearanceType(String appearanceType)
	{
		gateway.setAppearanceType(appearanceType);
	}

	/**
	 * Set the player's position
	 * 
	 * @param playerPosition
	 *            The new location the player is Assuming a valid position.
	 *            Error checking else where
	 */
	public void setPlayerPosition(Position playerPosition)
	{
		setPlayerPositionWithoutNotifying(playerPosition);
		PlayerMovedReport report = new PlayerMovedReport(gateway.getPlayerID(),
				this.getPlayerName(), playerPosition);
		this.notifyObservers(report);
	}

	/**
	 * Tell this player what his login information is
	 * 
	 * @param pl
	 *            his login information
	 */
	public void setPlayerLogin(PlayerLogin pl)
	{
		this.playerLogin = pl;
	}

	/**
	 * Move a player without notifying the other players (used when moving from
	 * one map to another
	 * 
	 * @param newPosition
	 *            the position the player should move to
	 */
	public void setPlayerPositionWithoutNotifying(Position newPosition)
	{
		gateway.setPosition(newPosition);
	}

	/**
	 * Get the quizScore
	 * 
	 * @return the quiz score
	 */
	public int getQuizScore()
	{
		return this.quizScore;
	}

	/**
	 * Set the quizScore
	 * 
	 * @param score
	 *            the new quiz score
	 */
	public void setQuizScore(int score)
	{
		this.quizScore = score;
	}

	/**
	 * Increment quiz score;
	 */
	public void incrementQuizScore()
	{
		this.quizScore++;
	}

	/**
	 * store the information into the data source
	 * 
	 * @throws DatabaseException if the data source fails to complete the persistance
	 */
	public void persist() throws DatabaseException
	{
		gateway.persist();
	}
}
