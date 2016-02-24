package model.reports;

import model.QualifiedObservableReport;

/**
 * @author Ian Keefer
 * @author TJ Renninger
 *
 */
public class KeyInputRecievedReport implements QualifiedObservableReport
{

	private String input;

	/**
	 * @param input the key that is pressed
	 */
	public KeyInputRecievedReport(String input)
	{
		this.input = input;
	}

	/**
	 * @return user key input
	 */
	public String getInput() 
	{
		return input;
	}
}
