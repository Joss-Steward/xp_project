package view.screen.popup;

import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.AdventureStateChangeReport;
import model.reports.AdventureNeedingNotificationReport;
import model.reports.QuestStateChangeReport;
import model.reports.QuestNeedingNotificationReport;

import com.badlogic.gdx.scenes.scene2d.Stage;

import data.AdventureStateEnum;
import data.QuestStateEnum;

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
		cm.registerObserver(this, AdventureNeedingNotificationReport.class);
		cm.registerObserver(this, QuestNeedingNotificationReport.class);
		cm.registerObserver(this, QuestStateChangeReport.class);
		cm.registerObserver(this, AdventureStateChangeReport.class);

	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		if (report.getClass().equals(AdventureNeedingNotificationReport.class))
		{
			AdventureNeedingNotificationReport r = (AdventureNeedingNotificationReport) report;
			
			AdventureCompleteBehavior behavior = new AdventureCompleteBehavior(r.getPlayerID(), r.getQuestID(), r.getAdventureID());
			AdventureStateEnum state = r.getState();
			new ScreenPopUp("Adventure " + state.getDescription(),
					r.getAdventureDescription() + " completed", this.stage, behavior);
		} else if (report.getClass().equals(QuestNeedingNotificationReport.class))
		{
			QuestNeedingNotificationReport r = (QuestNeedingNotificationReport) report;
			
			QuestCompleteBehavior behavior = new QuestCompleteBehavior(r.getPlayerID(), r.getQuestID());
			QuestStateEnum state = r.getState();
			new ScreenPopUp("Adventure " + state.getDescription(),
					r.getQuestDescription() + " completed", this.stage, behavior);
		} else if (report.getClass().equals(QuestStateChangeReport.class))
		{
			QuestStateChangeReport r = (QuestStateChangeReport) report;
			if (r.getNewState() == QuestStateEnum.FULFILLED)
			{
				new ScreenPopUp("Quest Fulfilled",
						r.getQuestDescription() + " fulfilled", this.stage, new SilentBehavior());
			} else if (r.getNewState() == QuestStateEnum.FINISHED)
			{
				new ScreenPopUp("Quest Completed",
						r.getQuestDescription() + " completed", this.stage, new SilentBehavior());
			}
		} else if (report.getClass().equals(AdventureStateChangeReport.class))
		{
			AdventureStateChangeReport r = (AdventureStateChangeReport) report;
			if(r.getNewState() == AdventureStateEnum.COMPLETED)
			{
				new ScreenPopUp("Adventure Completed",
						r.getAdventureDescription() + " completed", this.stage, new SilentBehavior());
			}
		}
	}
}
