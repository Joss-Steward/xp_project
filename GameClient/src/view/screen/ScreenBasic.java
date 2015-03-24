package view.screen;

import model.QualifiedObservableConnector;
import model.QualifiedObserver;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

import edu.ship.shipsim.client.model.reports.QuestStateReport;

/**
 * Everything a basic screen needs
 * 
 * @author Merlin
 * 
 */
public abstract class ScreenBasic implements Screen, QualifiedObserver
{
	protected Stage stage;

	/**
	 * Get the stage that this screen displays
	 * 
	 * @return the stage
	 */
	public Stage getStage()
	{
		return stage;
	}

	/**
	 * Called in the constructor of subclasses to set up Qualified Observer
	 */
	public void setUpListening()
	{
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		cm.registerObserver(this, QuestStateReport.class);
	}
}
