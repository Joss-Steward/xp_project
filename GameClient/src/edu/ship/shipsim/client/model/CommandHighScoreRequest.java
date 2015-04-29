package edu.ship.shipsim.client.model;

import model.QualifiedObservableConnector;
import edu.ship.shipsim.client.model.reports.HighScoreRequestReport;

/**
 * Command that sends the high score request report
 * @author Ryan
 *
 */
public class CommandHighScoreRequest extends Command
{

	@Override
	protected boolean execute() 
	{
		HighScoreRequestReport r = new HighScoreRequestReport();
		QualifiedObservableConnector.getSingleton().sendReport(r);
		
		return true;
	}

}
