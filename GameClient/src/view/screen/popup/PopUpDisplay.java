package view.screen.popup;

import java.util.ArrayList;

import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.AdventureStateChangeReport;
import model.reports.AdventureNeedingNotificationReport;
import model.reports.QuestStateChangeReport;
import model.reports.QuestNeedingNotificationReport;

import com.badlogic.gdx.scenes.scene2d.Stage;

import datatypes.AdventureStateEnum;
import datatypes.QuestStateEnum;

/**
 * @author Cody/Scott Sends popup data to constructor from reports observed
 */
public class PopUpDisplay implements QualifiedObserver
{

	private Stage stage;
	private ArrayList<ScreenPopUp> waitingPopUps;

	/**
	 * Constructor for pop up display, set up observer
	 * 
	 * @param stage
	 *            stage of screen map
	 */
	public PopUpDisplay(Stage stage)
	{
		this.stage = stage;
		waitingPopUps = new ArrayList<ScreenPopUp>();
		setUpListening();
	}

	/**
	 * Sets up the QualifiedObserver for AdventuresNeedingNotificationReport
	 */
	public void setUpListening()
	{
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
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
			AdventureNotificationCompleteBehavior behavior = new AdventureNotificationCompleteBehavior(
					r.getPlayerID(), r.getQuestID(), r.getAdventureID());
			AdventureStateEnum state = r.getState();
			addWaitingPopUp(new OneChoiceScreenPopUp("Adventure " + state.getDescription(),
					r.getAdventureDescription(), this.stage, behavior, this));
		} 
		
		else if (report.getClass().equals(QuestNeedingNotificationReport.class))
		{
			QuestNeedingNotificationReport r = (QuestNeedingNotificationReport) report;

			QuestNotificationCompleteBehavior behavior = new QuestNotificationCompleteBehavior(
					r.getPlayerID(), r.getQuestID());
			QuestStateEnum state = r.getState();
			addWaitingPopUp(new OneChoiceScreenPopUp("Quest " + state.getDescription(), r.getQuestDescription(),
					this.stage, behavior, this));
		} 
		
		else if (report.getClass().equals(QuestStateChangeReport.class))
		{
			QuestStateChangeReport r = (QuestStateChangeReport) report;
			if (r.getNewState() == QuestStateEnum.FULFILLED)
			{
				addWaitingPopUp(new OneChoiceScreenPopUp("Quest Fulfilled",
						r.getQuestDescription() + " fulfilled", this.stage,
						new QuestNotificationCompleteBehavior(r.getPlayerID(),
								r.getQuestID()), this));
			} 
			else if (r.getNewState() == QuestStateEnum.COMPLETED)
			{
				addWaitingPopUp(new OneChoiceScreenPopUp("Quest Completed",
						r.getQuestDescription() + " completed", this.stage,
						new QuestNotificationCompleteBehavior(r.getPlayerID(),
								r.getQuestID()), this));
			}
		} 
		
		else if (report.getClass().equals(AdventureStateChangeReport.class))
		{
			AdventureStateChangeReport r = (AdventureStateChangeReport) report;
			if (r.getNewState() == AdventureStateEnum.COMPLETED)
			{
				addWaitingPopUp(new OneChoiceScreenPopUp("Adventure Completed", r.getAdventureDescription()
						+ " completed", this.stage, new AdventureNotificationCompleteBehavior(
								r.getPlayerID(), r.getQuestID(), r.getAdventureID()), this));
			}
		}
	}
	
	/**
	 * First popup was closed, show next one
	 * @param popup the popup that closed
	 */
	public void dialogClosed(ScreenPopUp popup)
	{
		if (waitingPopUps.contains(popup))
		{
			waitingPopUps.remove(popup);
			showNextPopUp();
		}
	}
	
	/**
	 * @param popup to be added
	 */
	public void addWaitingPopUp(ScreenPopUp popup)
	{
		waitingPopUps.add(popup);
		
		//Display the first one in the list right away
		if (waitingPopUps.size() == 1)
		{
			showNextPopUp();			
		}
	}
	
	/**
	 * causes the next popUp to be shown;
	 */
	public void showNextPopUp()
	{
		if (waitingPopUps.size() > 0)
		{
			waitingPopUps.get(0).showDialog();
		}
	}
}
