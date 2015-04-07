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
public class PopUpDisplay extends ScreenBasic implements QualifiedObserver
{

	/**
	 * Constructor for pop up display, set up oberserver
	 */
	public PopUpDisplay()
	{
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
			ScreenPopUp popup = new ScreenPopUp("Adventure Completed",r.getAdventuresDescriptionList()+" completed", stage);
		}
	}

	/**
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta)
	{
		
	}

	/**
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height)
	{
		
	}

	/**
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show()
	{
		
	}

	/**
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide()
	{
		
	}

	/**
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause()
	{
		
	}

	/**
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume()
	{
		
	}

	/**
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose()
	{
		
	}

}
