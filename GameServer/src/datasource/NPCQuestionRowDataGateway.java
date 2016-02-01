package datasource;

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

}
