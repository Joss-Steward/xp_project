package view.screen.playerinfo;

import model.ClientModelFacade;
import model.CommandHighScoreRequest;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.ExperiencePointsChangeReport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PlayerInfoUI extends Group implements QualifiedObserver
{
	private final float WIDTH = 200f;
	private final float HEIGHT = 300f;
	private final float POS_X = (Gdx.graphics.getWidth() - WIDTH) / 2;
	private final float POS_Y = (Gdx.graphics.getHeight() - HEIGHT) / 1.1f;
	private PlayerInfoTable playerTable;
	private boolean PI_ScreenShowing;
	private Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	
	/**
	 * sets up UI for top 10 experience points
	 */
	public PlayerInfoUI()
	{
		setUpListening();
		setSize(WIDTH, HEIGHT);
		setPosition(POS_X, POS_Y);
		playerTable = new PlayerInfoTable();
		playerTable.setFillParent(true);
		addActor(playerTable);
		playerTable.updatePlayerInfo(24444444);
		setVisible(false);
	}
	
	/**
	 * Sets up the QualifiedObserver for HighScoreResponseReport
	 */
	public void setUpListening()
	{
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		cm.registerObserver(this, ExperiencePointsChangeReport.class);
	}
	
	/**
	 * set visibility of High Score Screen
	 * @param b sets visibility of screen
	 */
	public void setPlayerInfoScreenVisibility(boolean b)
	{
		PI_ScreenShowing = b;
	}
	
	/**
	 * @return the screen showing state
	 */
	public boolean isPlayerInfoScreenShowing()
	{
		return PI_ScreenShowing;
	}
	
	/**
	 * Toggle the invisibility of the highscore list
	 */
	public void togglePlayerInfoScreen()
	{
		if (isPlayerInfoScreenShowing())
		{
			PI_ScreenShowing = false;
			addAction(Actions.hide());
		} 
		else
		{	
//			CommandHighScoreRequest cmd = new CommandHighScoreRequest();
//			ClientModelFacade.getSingleton().queueCommand(cmd);
			PI_ScreenShowing = true;
			addAction(Actions.show());
		}
	}
	
	/** (Javadoc)
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		if (report.getClass().equals(ExperiencePointsChangeReport.class))
		{
			ExperiencePointsChangeReport rep = (ExperiencePointsChangeReport) report;
			int exp = rep.getExperiencePoints();
			playerTable.updatePlayerInfo(exp);
		}	
	}
}