package datasource;

import java.util.HashMap;

import testData.NPCQuestionsForTest;
import datasource.DatabaseException;

/**
 * A mock implementation for PlayerRowDataGateway
 * 
 * @author Merlin
 *
 */
public class NPCQuestionRowDataGatewayMock implements NPCQuestionRowDataGateway
{

	private class NPCQuestionInfo
	{
		private String answer;
		private String questionStatement;

		public NPCQuestionInfo(String questionStatement, String answer)
		{
			this.questionStatement = questionStatement;
			this.answer = answer;
		}

		public String getAnswer()
		{
			return answer;
		}

		public String getQuestionStatement()
		{
			return questionStatement;
		}
	}

	
	/**
	 * Map question ID to question information
	 */
	private static HashMap<Integer, NPCQuestionInfo> npcQuestionInfo;
	private int questionID;

	private NPCQuestionInfo info;

	/**
	 * Just used by tests to reset static information
	 */
	public NPCQuestionRowDataGatewayMock()
	{
	}

	/**
	 * Finder constructor - will initialize itself from the stored information
	 * 
	 * @param questionID
	 *            the ID of the player we are looking for
	 * @throws DatabaseException
	 *             if the playerID isn't in the data source
	 */
	public NPCQuestionRowDataGatewayMock(int questionID) throws DatabaseException
	{
		if (npcQuestionInfo == null)
		{
			resetData();
		}

		if (npcQuestionInfo.containsKey(questionID))
		{
			info = npcQuestionInfo.get(questionID);
			this.questionID = questionID;
		} else
		{
			throw new DatabaseException("Couldn't find NPC with ID " + questionID);
		}
	}


	/**
	 * @see datasource.NPCQuestionRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		npcQuestionInfo = new HashMap<Integer, NPCQuestionInfo>();
		for (NPCQuestionsForTest p : NPCQuestionsForTest.values())
		{
			npcQuestionInfo.put(p.getQuestionID(), new NPCQuestionInfo(p.getQ(), p.getA()));
		}
	}

	/**
	 * @see datasource.NPCQuestionRowDataGateway#getQuestionStatement()
	 */
	@Override
	public String getQuestionStatement()
	{
		return info.getQuestionStatement();
	}

	/**
	 * @return this question's unique ID
	 */
	public int getQuestionID()
	{
		return questionID;
	}

	/**
	 * @see datasource.NPCQuestionRowDataGateway#getAnswer()
	 */
	@Override
	public String getAnswer()
	{
		return info.getAnswer();
	}

	/**
	 * @return a gateway for a random row in the table
	 * @throws DatabaseException if the datasource cannot complete the request
	 */
	public static NPCQuestionRowDataGateway findRandomGateway() throws DatabaseException
	{
		new NPCQuestionRowDataGatewayMock(1).resetData();
		int numberOfQuestions = npcQuestionInfo.values().size();
		int questionID = (int) (Math.random()*numberOfQuestions)+1;
		return new NPCQuestionRowDataGatewayMock(questionID);
	}

}
