package model;

import model.reports.LogoutReport;

/**
 * CommandLogout.java
 * @author Zachary & Abdul
 *
 */
public class CommandLogout extends Command {

	@Override
	protected boolean execute() 
	{
		LogoutReport report = new LogoutReport();
		QualifiedObservableConnector.getSingleton().sendReport(report);
		return true;
	}

}
