package edu.ship.shipsim.areaserver.model;

/**
 * Create test questions for the DB.
 * 
 * @author gl9859
 *
 */
public enum QuestionsInDB
{
	/**
	 * 
	 */
	ONE("First question", "First answer"), 
	
	/**
	 * 
	 */
	TWO("Second question", "Second answer");

	private String q;

	/**
	 * @return
	 * 			the question
	 */
	public String getQ()
	{
		return q;
	}

	/**
	 * @return
	 * 			the answer
	 */
	public String getA()
	{
		return a;
	}

	private String a;

	/**
	 * Constructor
	 * @param q
	 * 			question
	 * @param a
	 * 			answer
	 */
	QuestionsInDB(String q, String a)
	{
		this.q = q;
		this.a = a;
	}

}
