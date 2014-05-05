package model;

import model.reports.PinFailedReport;
import model.reports.PlayerMovedReport;
import model.reports.QuestScreenReport;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import data.Position;

/**
 * Very simple for now . . .
 * 
 * @author Merlin
 * 
 */
@DatabaseTable(tableName = "Player")
public class Player extends QualifiedObservable
{

	@DatabaseField(id = true)
	private int id;

	@DatabaseField(foreign = true)
	private PlayerLogin playerLogin;

	@DatabaseField
	private String appearanceType;

	@DatabaseField(dataType = DataType.SERIALIZABLE)
	private Position playerPosition;

	/**
	 * No arg constructor for ORMLite
	 */
	public Player()
	{
		registerOurReportTypes();
	}

	/**
	 * Check the pin to make sure it is correct with regards to contents and expiration
	 * 
	 * @param pinToCheck
	 *            the pin we gave the player to connect to this area server
	 * @return true or false with pin validity
	 */

	public boolean isPinValid(double pinToCheck)
	{
		PlayerPin pl = new PlayerPin(id);
		PinFailedReport report = null;
		
		if(!pl.isPinValid(pinToCheck))
		{
			report = new PinFailedReport(PlayerPin.ERROR_PIN_NOT_EXIST);
		}
		else if (pl.isExpired())
		{
			report = new PinFailedReport(PlayerPin.ERROR_PIN_EXPIRED);
		}
		
		if(report != null)
		{
			System.err.println("Pin is not valid for " + id + " because " + report.toString());
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
		return appearanceType;
	}

	/**
	 * @return the id of this player
	 */
	public int getID()
	{
		return id;
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
		return this.playerPosition;
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
		this.appearanceType = appearanceType;
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
		PlayerMovedReport report = new PlayerMovedReport(this.id, this.getPlayerName(),
				playerPosition);
		this.notifyObservers(report);
	}

	/**
	 * Tell this player what his login information is
	 * @param pl his login information
	 */
	public void setPlayerLogin(PlayerLogin pl)
	{
		this.playerLogin = pl;
	}

	/**
	 * Move a player without notifying the other players (used when moving from one map to another
	 * @param newPosition the position the player should move to
	 */
	public void setPlayerPositionWithoutNotifying(Position newPosition)
	{
		this.playerPosition = newPosition;
	}

	/**
	 * Set the id
	 * @param id the new id
	 */
	public void setId(int id) 
	{
		this.id = id;
	}
}
