package model;

/**
 * @author Merlin
 */
public class DatabaseException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String simpleDescription;

	/**
	 * @return simple Description
	 */
	public String getSimpleDescription()
	{
		return simpleDescription;
	}

	private Exception rootCause;

	/**
	 * 
	 * @return the original exception, if any, that occurred
	 */
	public Exception getRootCause()
	{
		return rootCause;
	}

	/**
	 * @param msg description of complication
	 * @param e exception being thrown
	 */
	public DatabaseException(String msg, Exception e)
	{
		simpleDescription = msg;
		rootCause = e;
	}

}
