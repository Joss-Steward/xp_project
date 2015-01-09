package datasource;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import model.DatabaseException;

/**
 * A mock version of a PlayerConnectionRowDataGateway built from the data in
 * PlayersForTest
 * 
 * @author Merlin
 *
 */
public class PlayerConnectionRowDataGatewayMock implements PlayerConnectionRowDataGateway
{
	private class ConnectionInfo
	{
		private int pin;
		private String changedOn;

		private String mapName;

		public ConnectionInfo(int pin, String changedOn, String mapName)
		{
			this.pin = pin;
			this.changedOn = changedOn;
			this.mapName = mapName;
		}
	}
	private int playerID;

	private ConnectionInfo connectionInfo;

	/**
	 * Map player ID to player connection information
	 */
	private static HashMap<Integer, ConnectionInfo> playerConnections;

	/**
	 * Finder Constructor
	 * @param playerID the playerID we are looking for
	 * @throws DatabaseException if the player isn't in the data source
	 */
	public PlayerConnectionRowDataGatewayMock(int playerID) throws DatabaseException
	{
		if (playerConnections == null)
		{
			resetData();
		}
		this.playerID = playerID;
		if (!playerConnections.containsKey(playerID))
		{
			throw new DatabaseException(
					"Unable to find connection information for player ID = " + playerID);
		}
		connectionInfo = playerConnections.get(playerID);

	}


	/**
	 * Creation constructor: will put the given data into the data source (committed)
	 * @param playerID the player's ID number
	 * @param pin the pin the player needs to connect
	 * @param mapName the name of the map file the player is joining
	 */
	public PlayerConnectionRowDataGatewayMock(int playerID, int pin, String mapName)
	{
		if (playerConnections == null)
		{
			resetData();
		}
		this.playerID = playerID;
		connectionInfo = new ConnectionInfo(pin, "1963-10-07 01:02:03", mapName);
		updateChangedOn();
		playerConnections.put(playerID, connectionInfo);
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#deleteRow()
	 */
	@Override
	public void deleteRow()
	{
		playerConnections.remove(playerID);
		connectionInfo = null;
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#getChangedOn()
	 */
	@Override
	public String getChangedOn() throws DatabaseException
	{
		if (connectionInfo == null)
		{
			throw new DatabaseException("No time stamp for player ID " + playerID);
		}
		return connectionInfo.changedOn;
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#getMapName()
	 */
	@Override
	public String getMapName()
	{
		return connectionInfo.mapName;
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#getPin()
	 */
	@Override
	public int getPin()
	{
		return connectionInfo.pin;
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		playerConnections = new HashMap<Integer, ConnectionInfo>();
		for (PlayersForTest p : PlayersForTest.values())
		{
			playerConnections
					.put(p.getPlayerID(), new ConnectionInfo(p.getPin(),
							p.getChangedOn(), p.getMapNameForPin()));
		}
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#setChangedOn(java.lang.String)
	 */
	@Override
	public void setChangedOn(String newTime) throws DatabaseException
	{
		connectionInfo.changedOn = newTime;
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#storeMapName(java.lang.String)
	 */
	@Override
	public void storeMapName(String mapName)
	{
		connectionInfo.mapName = mapName;
		updateChangedOn();
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#storePin(int)
	 */
	@Override
	public void storePin(int newPin)
	{
		connectionInfo.pin = newPin;
		updateChangedOn();
	}

	private String twoDigits(int month)
	{
		String result = "" + month;
		if (result.length() < 2)
		{
			result = '0' + result;
		}
		return result;
	}

	private void updateChangedOn()
	{
		GregorianCalendar now = new GregorianCalendar();
		connectionInfo.changedOn = now.get(Calendar.YEAR) + "-"
				+ twoDigits(now.get(Calendar.MONTH + 1)) + "-"
				+ twoDigits(now.get(Calendar.DAY_OF_MONTH)) + " "
				+ twoDigits(now.get(Calendar.HOUR_OF_DAY)) + ":"
				+ twoDigits(now.get(Calendar.MINUTE)) + ":"
				+ twoDigits(now.get(Calendar.SECOND));
	}

}
