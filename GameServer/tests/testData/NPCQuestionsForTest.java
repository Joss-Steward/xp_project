package testData;

/**
 * Create test questions for the DB.
 * 
 * @author gl9859
 *
 */
public enum NPCQuestionsForTest
{
	/**
	 * 
	 */
	ONE(1, "First question", "First answer"),

	/**
	 * 
	 */
	TWO(2, "Second question", "Second answer");

	private String q;

	private String a;

	private int questionID;

	/**
	 * Constructor
	 * 
	 * @param questionID
	 *            this question's unique ID
	 * @param q
	 *            question
	 * @param a
	 *            answer
	 */
	NPCQuestionsForTest(int questionID, String q, String a)
	{
		this.questionID = questionID;
		this.q = q;
		this.a = a;
	}
	/**
	 * @return the answer
	 */
	public String getA()
	{
		return a;
	}

	/**
	 * @return the question
	 */
	public String getQ()
	{
		return q;
	}

	/**
	 * @return the question's unique ID
	 */
	public int getQuestionID()
	{
		return questionID;
	}

}
