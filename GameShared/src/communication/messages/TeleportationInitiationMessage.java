package communication.messages;

import java.io.Serializable;

import datatypes.Position;

/**
 * A message that allows a client to ask for the server & port number that are
 * managing a given map (tmx file)
 * 
 * @author Merlin
 * 
 */
public class TeleportationInitiationMessage implements Message, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mapName;
	private Position position;
	private int playerId;

	/**
	 * Create the request message
	 * 
	 * @param playerId the player that is making this move
	 * @param mapName the name of the tmx file of the map we are interested in
	 * @param newPosition the position in which the player should be placed when
	 *            the move is complete
	 */
	public TeleportationInitiationMessage(int playerId, String mapName, Position newPosition)
	{
		this.playerId = playerId;
		this.mapName = mapName;
		this.position = newPosition;
	}

	/**
	 * Get the name of the map we are interested in
	 * 
	 * @return the map name
	 */
	public String getMapName()
	{
		return mapName;
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "TeleportationInitiationMessage: playerID = " + playerId + " mapName = " + mapName + " position = "
				+ position;
	}

	/**
	 * Get the position the player should be in in the new map
	 * 
	 * @return the position
	 */
	public Position getPosition()
	{
		return position;
	}

	/**
	 * Get the ID of the player who is teleporting
	 * 
	 * @return the playerID
	 */
	public int getPlayerId()
	{
		return playerId;
	}

}
