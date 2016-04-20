package view.screen.playerinfo;
import view.screen.OverlayingScreen;
import model.ClientPlayerManager;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.ThisClientsPlayer;
import model.reports.TimeToLevelUpDeadlineReport;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * @author TJ Renninger and Ian Keefer
 *
 */
public class PlayerInfoUI extends OverlayingScreen implements QualifiedObserver
{
	private final float WIDTH = 200f;
	private final float HEIGHT = 300f;
	private PlayerInfoTable playerTable;
	private boolean PI_ScreenShowing;
	
	/**
	 * sets up UI for top 10 experience points
	 */
	public PlayerInfoUI()
	{
		setUpListening();
		playerTable = new PlayerInfoTable();
		playerTable.setFillParent(true);
		container.add(playerTable);
	}	
	
	/**
	 * Sets up the QualifiedObserver for PlayerInfoResponseReport
	 */
	public void setUpListening()
	{
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		cm.registerObserver(this, TimeToLevelUpDeadlineReport.class);
	}
	
	/**
	 * set visibility of Player Info
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
	 * Toggle the invisibility of the player info screen
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
			ThisClientsPlayer player = ClientPlayerManager.getSingleton().getThisClientsPlayer();
			playerTable.updatePlayerInfo(player);
			PI_ScreenShowing = true;
			addAction(Actions.show());
		}
	}
	
	/** 
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{

	}

	/**
	 * @see view.screen.OverlayingScreen#getWidth()
	 */
	@Override
	public float getMyWidth()
	{
		return WIDTH;
	}

	/**
	 * @see view.screen.OverlayingScreen#getHeight()
	 */
	@Override
	public float getMyHeight()
	{
		return HEIGHT;
	}
}