package datasource;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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
		private Date startDate;
		private Date endDate;

		public NPCQuestionInfo(String questionStatement, String answer, Date startDate, Date endDate)
		{
			this.questionStatement = questionStatement;
			this.answer = answer;
			this.startDate = startDate;
			this.endDate = endDate;
		}

		public String getAnswer()
		{
			return answer;
		}

		public String getQuestionStatement()
		{
			return questionStatement;
		}

		public Date getStartDate()
		{
			return startDate;
		}

		public Date getEndDate()
		{
			return endDate;
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
	 * @param questionID the ID of the player we are looking for
	 * @throws DatabaseException if the playerID isn't in the data source
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
			npcQuestionInfo.put(p.getQuestionID(),
					new NPCQuestionInfo(p.getQ(), p.getA(), p.getStartDate(), p.getEndDate()));
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
	 * @see datasource.NPCQuestionRowDataGateway#getStartDate()
	 */
	@Override
	public Date getStartDate()
	{
		return info.getStartDate();
	}

	/**
	 * @see datasource.NPCQuestionRowDataGateway#getEndDate()
	 */
	@Override
	public Date getEndDate()
	{
		return info.getEndDate();
	}

	/**
	 * @return a gateway for a random row in the table that is available
	 * @throws DatabaseException if the datasource cannot complete the request
	 */
	public static NPCQuestionRowDataGateway findRandomGateway() throws DatabaseException
	{
		new NPCQuestionRowDataGatewayMock(1).resetData();

		ArrayList<Integer> avaiableQuestions = new ArrayList<Integer>();

		GregorianCalendar now = new GregorianCalendar();

		for (Integer i : npcQuestionInfo.keySet())
		{
			if (now.after(npcQuestionInfo.get(i).startDate) && now.before(npcQuestionInfo.get(i).endDate))
			{
				avaiableQuestions.add(i);
			}
		}

		int questionID = (int) (Math.random() * avaiableQuestions.size()) + 1;
		return new NPCQuestionRowDataGatewayMock(questionID);
	}

}
