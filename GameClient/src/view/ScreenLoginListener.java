package view;

import java.util.ArrayList;
import java.util.Observable;

import model.QualifiedObservableReport;
import model.reports.LoginFailedReport;
import model.reports.LoginInitiatedReport;

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
		if (arg.getClass().equals(LoginInitiatedReport.class))
		{
			this.switchToScreen(Screens.MAP_SCREEN);
		}
		
		if (arg.getClass().equals(LoginFailedReport.class))
		{
			ScreenLogin screen = (ScreenLogin)Screens.LOGIN_SCREEN.getScreen();
			LoginFailedReport report = (LoginFailedReport)arg;
			screen.showFlagMessage(report.toString());
			this.switchToScreen(Screens.LOGIN_SCREEN);
		}

	}

	/**
	 * @see view.ScreenListener#getReportTypes()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = new ArrayList<Class<? extends QualifiedObservableReport>>();
		reportTypes.add(LoginInitiatedReport.class);
		reportTypes.add(LoginFailedReport.class);
		return reportTypes;
	}

}
