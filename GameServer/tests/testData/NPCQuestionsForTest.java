package testData;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

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
	ONE(1, "First question", "First answer", new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), 
	new GregorianCalendar(2017, Calendar.MARCH, 21).getTime()),
	

	/**
	 * 
	 */
	TWO(2, "Second question", "Second answer", new GregorianCalendar(2024, Calendar.FEBRUARY, 11).getTime(),
	        new GregorianCalendar(2026, Calendar.FEBRUARY, 11).getTime()),
	
	/**
	 * 
	 */
	MULTIPLE_CHOICE(3, "Would you like to pick A for correct answer?\nA. First Choice\nB. Second Choice\nC. Third Choice\nD. Fourth Choice\n", "A",
			new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(), new GregorianCalendar(2017, Calendar.MARCH, 28).getTime());

	private String q;

	private String a;

	private int questionID;

	private Date startDate;
	
	private Date endDate;
	
	
	/**
	 * Constructor
	 * 
	 * @param questionID
	 *            this question's unique ID
	 * @param q
	 *            question
	 * @param a
	 *            answer
     * @param startDate
     *            first day the question is available
     * @param endDate
     *            last day the question is available
	 */
	NPCQuestionsForTest(int questionID, String q, String a, Date startDate, Date endDate)
	{
		this.questionID = questionID;
		this.q = q;
		this.a = a;
		this.startDate = startDate;
		this.endDate = endDate;
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
	
	/**
	 * @return the first day the question is available
	 */
	public Date getStartDate()
	{
	    return startDate;
	}
	
	/**
	 * @return the last day the question is available
	 */
	public Date getEndDate()
	{
	    return endDate;
	}
	

}
