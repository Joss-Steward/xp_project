package view;

import java.util.ArrayList;
import java.util.Observable;

import model.QualifiedObservableReport;
import model.reports.NewMapReport;

/**
 * Every screen has one of these and it is responsible for listening for the
 * reports that are associated with this screen and updating the state as
 * necessary
 * 
 * @author Merlin
 * 
 */
public class ScreenLoginListener extends ScreenListener
{

	/**
	 * 
	 */
	public ScreenLoginListener()
	{
		super.setUpListening();
	}

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg)
	{
		if (arg.getClass().equals(NewMapReport.class))
		{
			NewMapReport report = (NewMapReport) arg;
			ScreenMap nextScreen = (ScreenMap) Screens.MAP_SCREEN.getScreen();
			nextScreen.setTiledMap(report.getTiledMap());
			Screens.display(Screens.MAP_SCREEN);
		}

	}

	/**
	 * @see view.ScreenListener#getReportTypes()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = new ArrayList<Class<? extends QualifiedObservableReport>>();
		reportTypes.add(NewMapReport.class);
		return reportTypes;
	}

}
