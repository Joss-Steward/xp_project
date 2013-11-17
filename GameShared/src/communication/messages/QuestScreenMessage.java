package communication.messages;

import java.io.Serializable;

/**
 * 
 * @author Joshua
 * 
 */
public class QuestScreenMessage implements Message, Serializable
{

	/**
	 * Serialized ID
	 */
	private static final long serialVersionUID = 1L;

	boolean loadState;

	private int playerID;

	/**
	 * constructor
	 * 
	 * @param loadState
	 *            boolean opening or closing the quest screen
	 * @param playerID
	 *            the player's id
	 * 
	 *            TODO: do I need the player ID anymore??
	 */
	public QuestScreenMessage(boolean loadState, int playerID)
	{
		this.loadState = loadState;
		this.setPlayerID(playerID);
	}

	/**
	 * getter for load state
	 * 
	 * @return boolean opening or closeing screen
	 */
	public boolean getLoadState()
	{
		return loadState;
	}

	/**
	 * setter for load state
	 * 
	 * @param loadState
	 *            whether the quest screen is opening or closing
	 */
	public void setLoadState(boolean loadState)
	{
		this.loadState = loadState;
	}

	/**
	 * getter for playerID
	 * 
	 * @return int the player ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @param playerID
	 *            the playerID to set
	 */
	public void setPlayerID(int playerID)
	{
		this.playerID = playerID;
	}

}
