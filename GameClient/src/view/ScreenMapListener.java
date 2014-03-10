package view;

import java.util.ArrayList;
import java.util.Observable;

import data.Position;
import model.PlayerManager;
import model.QualifiedObservableReport;
import model.reports.*;

/**
 * Not much for now!
 * @author Merlin
 *
 */
public class ScreenMapListener extends ScreenListener
{
	/**
	 * 
	 */
	public ScreenMapListener()
	{
		super.setUpListening();
	}

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg)
	{
		ScreenMap map = (ScreenMap) this.screen;
		
		// adds your player's sprite to this client
		if (arg.getClass().equals(PlayerConnectedToAreaServerReport.class)) 
		{
			PlayerConnectedToAreaServerReport report = (PlayerConnectedToAreaServerReport) arg;
			PlayerType type = PlayerType.valueOf(report.getPlayerAppearanceType());
			PlayerSprite sprite = map.addPlayer(report.getPlayerName(), type);
			
			//detect when the player being added is the client's player for finer control
			if (report.getPlayerName().equals(PlayerManager.getSingleton().getThisClientsPlayer().getName())) {
				map.mySprite = sprite;
			}
		}
		// moves the player to a new position
		else if (arg.getClass().equals(ThisPlayerMovedReport.class))
		{
			ThisPlayerMovedReport report = (ThisPlayerMovedReport) arg;
			Position p = report.getNewPosition();
			map.mySprite.setPosition(p.getColumn(), p.getRow());
		}
	}

	/**
	 * @see view.ScreenListener#getReportTypes()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = new ArrayList<Class<? extends QualifiedObservableReport>>();
		reportTypes.add(PlayerConnectedToAreaServerReport.class);
		reportTypes.add(ThisPlayerMovedReport.class);
		//reportTypes.add(OtherPlayerMovedReport.class);
		
		return reportTypes;
	}

}
