package view.screen.popup;

import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;

import com.badlogic.gdx.scenes.scene2d.Stage;

import edu.ship.shipsim.client.model.reports.AdventuresNeedingNotificationReport;

/**
 * @author Cody/Scott
 * Sends popup data to constructor from reports observed
 */
public class PopUpDisplay implements QualifiedObserver
{

	private Stage stage;
	/**
	 * Constructor for pop up display, set up observer
	 * @param stage stage of screen map
	 */
	public PopUpDisplay(Stage stage)
	{
		this.stage = stage;
		setUpListening();
	}
	
	/**
	 * Sets up the QualifiedObserver for AdventuresNeedingNotificationReport
	 */
	public void setUpListening()
	{
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		cm.registerObserver(this, AdventuresNeedingNotificationReport.class);
	}
	
	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		if (report.getClass().equals(AdventuresNeedingNotificationReport.class))
		{
			AdventuresNeedingNotificationReport r = (AdventuresNeedingNotificationReport) report;
			@SuppressWarnings("unused")
			ScreenPopUp popup = new ScreenPopUp("Adventure Completed",r.getAdventuresDescriptionList()+" completed", this.stage);
		}
	}
}
