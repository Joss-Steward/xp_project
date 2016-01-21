package view.screen.popup;

import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.AdventureStateChangeReport;
import model.reports.AdventuresNeedingNotificationReport;
import model.reports.QuestStateChangeReport;

import com.badlogic.gdx.scenes.scene2d.Stage;

import datasource.AdventureStateEnum;
import datasource.QuestStateEnum;

/**
 * @author Cody/Scott Sends popup data to constructor from reports observed
 */
public class PopUpDisplay implements QualifiedObserver
{

	private Stage stage;

	/**
	 * Constructor for pop up display, set up observer
	 * 
	 * @param stage
	 *            stage of screen map
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
		QualifiedObservableConnector cm = QualifiedObservableConnector
				.getSingleton();
		cm.registerObserver(this, AdventuresNeedingNotificationReport.class);
		cm.registerObserver(this, QuestStateChangeReport.class);
		cm.registerObserver(this, AdventureStateChangeReport.class);

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
			
			AdventureCompleteBehavior behavior = new AdventureCompleteBehavior(r.getPlayerID(), r.getQuestID(), r.getAdventureID());
			@SuppressWarnings("unused")
			ScreenPopUp popup = new ScreenPopUp("Adventure Completed",
					r.getAdventureDescription() + " completed", this.stage, behavior);
		} else if (report.getClass().equals(QuestStateChangeReport.class))
		{
			QuestStateChangeReport r = (QuestStateChangeReport) report;
			if (r.getNewState() == QuestStateEnum.FULFILLED)
			{
				@SuppressWarnings("unused")
				ScreenPopUp popup = new ScreenPopUp("Quest Fulfilled",
						r.getQuestDescription() + " fulfilled", this.stage, new SilentBehavior());
			} else if (r.getNewState() == QuestStateEnum.FINISHED)
			{
				@SuppressWarnings("unused") 
				ScreenPopUp popup = new ScreenPopUp("Quest Completed",
						r.getQuestDescription() + " completed", this.stage, new SilentBehavior());
			}
		} else if (report.getClass().equals(AdventureStateChangeReport.class))
		{
			AdventureStateChangeReport r = (AdventureStateChangeReport) report;
			if(r.getNewState() == AdventureStateEnum.COMPLETED)
			{
				@SuppressWarnings("unused") 
				ScreenPopUp popup = new ScreenPopUp("Adventure Completed",
						r.getAdventureDescription() + " completed", this.stage, new SilentBehavior());
			}
		}
	}
}
