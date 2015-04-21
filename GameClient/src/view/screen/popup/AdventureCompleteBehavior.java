package view.screen.popup;

import edu.ship.shipsim.client.model.CommandAdventureNotificationComplete;

/**
 * Handles behavior for adventure being completed
 * @author Ryan
 *
 */
public class AdventureCompleteBehavior implements PopupBehavior 
{

	private int playerID;
	private int questID;
	private int adventureID;
	
	/**
	 * @param playerID the id of the player
	 * @param questID the id of the quest
	 * @param adventureID the id of the adventure
	 */
	public AdventureCompleteBehavior(int playerID, int questID, int adventureID)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
	}
	/**
	 * @see view.screen.popup.PopupBehavior#clicked()
	 * 
	 * Create a CommandAdventureNotificationComplete command
	 */
	@Override
	public void clicked() 
	{
		CommandAdventureNotificationComplete cmd = new CommandAdventureNotificationComplete(playerID, questID, adventureID);
		cmd.execute();
	}
	/**
	 * @return the player's ID
	 */
	public int getPlayerID() 
	{
		return playerID;
	}
	
	/**
	 * @return the quest ID
	 */
	public int getQuestID() 
	{
		return questID;
	}
	
	/**
	 * @return the adventure ID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}
}
