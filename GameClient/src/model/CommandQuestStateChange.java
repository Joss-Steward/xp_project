package model;

import communication.messages.QuestStateChangeMessage;
import datasource.QuestStateEnum;

/**
 * @author Ryan
 *
 */
public class CommandQuestStateChange extends Command
{
	
	private int questID;
	private String questDescription;
	private QuestStateEnum questState;

	/**
	 * @param message the QuestStateChangeMesage
	 */
	public CommandQuestStateChange(QuestStateChangeMessage message) 
	{
		this.questID = message.getQuestID();
		this.questDescription = message.getQuestDescription();
		this.questState = message.getNewState();
	}

	@Override
	protected boolean execute() 
	{
		ClientPlayerManager.getSingleton().getThisClientsPlayer().sendQuestStateChangeReport(questID, questDescription, questState);
		return true;
	}

	/**
	 * The ID of the quest
	 * @return questID
	 */
	public int getQuestID() 
	{
		return questID;
	}

	/**
	 * The description of the quest
	 * @return questDescription
	 */
	public String getQuestDescription() 
	{
		return questDescription;
	}

	/**
	 * The State of the quest
	 * @return questState
	 */
	public QuestStateEnum getQuestState() 
	{
		return questState;
	}

}
