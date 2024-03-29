package model;

import model.reports.KeyInputSentReport;

/**
 * Command for user key input
 * 
 * @author Ian Keefer & TJ Renninger
 */
public class CommandKeyInputSent extends Command
{

	private String input;

	/**
	 * @param input
	 *            user key input
	 */
	public CommandKeyInputSent(String input)
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

	@Override
	protected boolean execute()
	{
		KeyInputSentReport report = new KeyInputSentReport(input);
		QualifiedObservableConnector.getSingleton().sendReport(report);
		return true;
	}

}
