package datasource;

import data.QuestCompletionActionParameter;
import data.QuestCompletionActionType;
import datatypes.Position;

import java.util.Date;

/**
 * A row data gateway for the quest table
 * @author merlin
 *
 */
public interface QuestRowDataGateway
{

	/**
	 * Used for testing - tells the mock version to reset its data back to the original state
	 */
	public void resetData();

	/**
	 * @return the unique quest ID for the row this gateway is managing
	 */
	public int getQuestID();

	/**
	 * @return the description for the quest this gateway is managing
	 */
	public String getQuestDescription();

	/**
	 * 
	 * @return the name of the map that contains the trigger point for this quest
	 */
	public String getTriggerMapName();

	/**
	 * 
	 * @return the position that should trigger this quest
	 */
	public Position getTriggerPosition();

	/**
	 * @return the number of adventures you must complete to fulfill this quest
	 */
	public int getAdventuresForFulfillment();

	/**
	 * @return the number of experience points you gain when you fulfill this quest
	 */
	int getExperiencePointsGained();

	/**
	 * @return the type of action that should be taken when this quest is complete
	 */
	public QuestCompletionActionType getCompletionActionType();

	/**
	 * @return an object describing the details of the completion action for this quest
	 */
	public QuestCompletionActionParameter getCompletionActionParameter();

	/**
	 * 
	 * @return the title of this quest
	 */
	public String getQuestTitle();

	/**
	 * @return the first day the quest is available
	 */
    public Date getStartDate();
    
    
    /**
     * @return the last day the quest is available
     */
    public Date getEndDate();
}
