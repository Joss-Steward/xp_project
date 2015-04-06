package view.screen.popup;

import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import view.screen.ScreenBasic;
import edu.ship.shipsim.client.model.reports.AdventuresNeedingNotificationReport;

/**
 * @author Cody/Scott
 * Sends popup data to constructor from reports observed
 */
public abstract class PopUpDisplay extends ScreenBasic implements QualifiedObserver
{

	
	@SuppressWarnings("javadoc")
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		if (report.getClass().equals(AdventuresNeedingNotificationReport.class))
		{
			AdventuresNeedingNotificationReport r = (AdventuresNeedingNotificationReport) report;
			@SuppressWarnings("unused")
			ScreenPopUp popup = new ScreenPopUp("Adventure Completed",r.getAdventuresDescriptionList()+" completed", stage);
		}
	}
	
	/**
	 * Sets up the QualifiedObserver for AdventuresNeedingNotificationReport
	 */
	public void setUpListening()
	{
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		cm.registerObserver(this, AdventuresNeedingNotificationReport.class);
	}


}
