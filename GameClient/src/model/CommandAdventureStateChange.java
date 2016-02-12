package model;

import data.AdventureStateEnum;
import communication.messages.AdventureStateChangeMessage;

/**
 * @author sl6469, Cody
 *
 */
public class CommandAdventureStateChange extends Command
{
	
	private int adventureID, questID;
	private String adventureDescription;
	private AdventureStateEnum adventureState;
	
	/**
	 * @param message that AdventureStateChangeMessage
	 */
	public CommandAdventureStateChange(AdventureStateChangeMessage message)
	{
		this.questID = message.getQuestID();
		this.adventureID = message.getAdventureID();
		this.adventureDescription = message.getAdventureDescription();
		this.adventureState = message.getNewState();
	}

	@Override
	protected boolean execute()
	{
		ClientPlayerManager.getSingleton().getThisClientsPlayer().sendAdventureStateChangeReport(questID, adventureID, adventureDescription, adventureState);
		return true;
	}
	
	/**
	 * @return the quest ID
	 */
	public int getQuestID()
	{
		return questID;
	}
	
	/**
	 * @return the adventureID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * @return the adventureDescription
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}
	/**
	 * @return the adventureState
	 */
	public AdventureStateEnum getAdventureState()
	{
		return adventureState;
	}

	

	

}
