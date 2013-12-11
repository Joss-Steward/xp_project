package view;

import java.util.ArrayList;
import java.util.Observable;

import model.QualifiedObservableReport;
import model.reports.NewMapReport;
import model.reports.QuestScreenReport;
import model.reports.QuestUpdateReport;

/**
 * @author Joshua Beck
 * 
 */
public class ScreenQuestListener extends ScreenListener
{

	/**
	 * Constructor
	 */
	public ScreenQuestListener()
	{
		super.setUpListening();
	}

	/**
	 * TODO: make report for quest information
	 * 
	 * @param o
	 *            Observable object passed through update
	 * @param arg
	 *            Report object pas
	 */
	public void update(Observable o, Object arg)
	{
		if (arg.getClass().equals(QuestUpdateReport.class))
		{
			System.out
					.print("hello from QuestScreen listener : QuestScreenReport");
			NewMapReport report = (NewMapReport) arg;
			ScreenMap nextScreen = (ScreenMap) Screens.QUEST_SCREEN.getScreen();
			nextScreen.setTiledMap(report.getTiledMap());
			this.switchToScreen(Screens.QUEST_SCREEN);
		}

		if (arg.getClass().equals(QuestScreenReport.class))
		{
			System.out
					.print("hello from QuestScreen listener : QuestScreenReport");
			QuestScreenReport report = (QuestScreenReport) arg;
			if (report.getLoadState() == true)
			{
				ScreenQuest nextScreen = (ScreenQuest) Screens.QUEST_SCREEN
						.getScreen();

				// TODO transfer quest information in Quest
				 nextScreen.setQuests(null);

				this.switchToScreen(Screens.QUEST_SCREEN);
			} else
			{
				this.switchToScreen(Screens.MAP_SCREEN);
			}
		}

	}

	/**
	 * @see view.ScreenListener#getReportTypes()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
	{

		ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = new ArrayList<Class<? extends QualifiedObservableReport>>();
		// TODO: change to quest update report
		reportTypes.add(QuestUpdateReport.class);
		reportTypes.add(QuestScreenReport.class);
		return reportTypes;
	}

}