package model;

import java.util.ArrayList;

import model.QualifiedObservableConnector;
import model.reports.HighScoreResponseReport;
import datasource.PlayerScoreRecord;

/**
 * @author nk3668
 *
 */
public class CommandHighScoreResponse extends Command
{
	ArrayList<PlayerScoreRecord> scores = new ArrayList<PlayerScoreRecord>();
	
	/**
	 * Constructor for CommandHighScoreResponse
	 * @param scoreReports the high scores
	 */
	public CommandHighScoreResponse(ArrayList<PlayerScoreRecord> scoreReports) 
	{
		this.scores = scoreReports;
	}

	/**
	 * Generate a HighScoreReponseReport and send it off
	 */
	@Override
	protected boolean execute() 
	{
		HighScoreResponseReport report = new HighScoreResponseReport(scores);
		QualifiedObservableConnector.getSingleton().sendReport(report);
		return true;
	}

	/**
	 * Return score reports
	 * @return score reports
	 */
	public Object getScoreRecord() {
		return scores;
	}

}
