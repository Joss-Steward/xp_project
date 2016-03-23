package datasource;

import java.util.Date;

/**
 * Behavior required by gateways for the NPC Question table used by the quizbot
 * @author Merlin
 *
 */
public interface NPCQuestionRowDataGateway
{

	/**
	 * For Testing Only
	 */
	public void resetData();
	
	/**
	 * 
	 * @return the text of a question
	 */
	public String getQuestionStatement();

	/**
	 * The correct answer for this question
	 * @return the answer
	 */
	public String getAnswer();

	/**
	 * The date this question is available
	 * @return The date this question is available
	 */
    public Date getStartDate();
    
    /**
     * The last day the question is available
     * @return The last day this question is available
     */
    public Date getEndDate();

}
