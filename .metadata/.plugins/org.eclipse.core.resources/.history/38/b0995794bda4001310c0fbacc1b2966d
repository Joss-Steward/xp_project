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
		if (arg.getClass().equals(ThisPlayerConnectedToAreaServerReport.class)) 
		{
			ThisPlayerConnectedToAreaServerReport report = (ThisPlayerConnectedToAreaServerReport) arg;
			PlayerType type = PlayerType.valueOf(report.getPlayerType());
			PlayerSprite sprite = map.addPlayer(report.getPlayerName(), type);
			
			//detect when the player being added is the client's player for finer control
			if (report.getPlayerName().equals(PlayerManager.getSingleton().getThisClientsPlayer().getName())) {
				map.mySprite = sprite;
			}
		}
		// adds another player when they connect to the server while you're connected to your client's view
		else if (arg.getClass().equals(OtherPlayerConnectedToAreaServerReport.class)) 
		{
			OtherPlayerConnectedToAreaServerReport report = (OtherPlayerConnectedToAreaServerReport) arg;
			PlayerType type = PlayerType.valueOf(report.getPlayerType());
			map.addPlayer(report.getPlayerName(), type);
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
		reportTypes.add(OtherPlayerConnectedToAreaServerReport.class);
		reportTypes.add(ThisPlayerConnectedToAreaServerReport.class);
		reportTypes.add(ThisPlayerMovedReport.class);
		//reportTypes.add(OtherPlayerMovedReport.class);
		
		return reportTypes;
	}

}
