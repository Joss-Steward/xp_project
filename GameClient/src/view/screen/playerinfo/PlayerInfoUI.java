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
	
	/**
	 * sets up UI for top 10 experience points
	 */
	public PlayerInfoUI()
	{
		super();
		setUpListening();
		playerTable = new PlayerInfoTable();
		playerTable.setFillParent(true);
		container.addActor(playerTable);
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
	 * Toggle the invisibility of the player info screen
	 */
	public void togglePlayerInfoScreen()
	{
		if (isVisible())
		{
			addAction(Actions.hide());
		} 
		else
		{	
			ThisClientsPlayer player = ClientPlayerManager.getSingleton().getThisClientsPlayer();
			playerTable.updatePlayerInfo(player);
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