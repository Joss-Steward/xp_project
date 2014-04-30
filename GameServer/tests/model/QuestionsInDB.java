package model;

public enum QuestionsInDB
{

	ONE("First question", "First answer"), TWO("Second question",
			"Second answer");

	private String q;

	public String getQ()
	{
		return q;
	}

	public String getA()
	{
		return a;
	}

	private String a;

	QuestionsInDB(String q, String a)
	{
		this.q = q;
		this.a = a;
	}

}
