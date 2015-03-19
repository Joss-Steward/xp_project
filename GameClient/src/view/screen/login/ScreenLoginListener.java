package view.screen.login;

import java.util.ArrayList;

import view.screen.ScreenListener;
import view.screen.Screens;
import edu.ship.shipsim.client.model.reports.LoginFailedReport;
import edu.ship.shipsim.client.model.reports.LoginInitiatedReport;
import edu.ship.shipsim.client.model.reports.PinFailedReport;
import model.QualifiedObservableReport;

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
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport arg)
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
		
		if (arg.getClass().equals(PinFailedReport.class))
		{
			ScreenLogin screen = (ScreenLogin)Screens.LOGIN_SCREEN.getScreen();
			PinFailedReport report = (PinFailedReport)arg;
			screen.showFlagMessage(report.toString());
			this.switchToScreen(Screens.LOGIN_SCREEN);
		}


	}

	/**
	 * @see view.screen.ScreenListener#getReportTypes()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = new ArrayList<Class<? extends QualifiedObservableReport>>();
		reportTypes.add(LoginInitiatedReport.class);
		reportTypes.add(LoginFailedReport.class);
		reportTypes.add(PinFailedReport.class);
		return reportTypes;
	}

}
