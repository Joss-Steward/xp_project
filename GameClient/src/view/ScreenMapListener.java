package view;

import java.util.ArrayList;
import java.util.Observable;

import data.Position;
import model.QualifiedObservableReport;
import model.reports.*;

/**
 * Not much for now!
 * 
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
		if (arg.getClass().equals(PlayerMovedReport.class))
		{
			PlayerMovedReport report = (PlayerMovedReport) arg;
			map.movePlayer(report.getID(), report.getNewPosition());
		}
		// moves the player to a new position
		// adds a message spoken by users to be displayed in the UI
		else if (arg.getClass().equals(ChatReceivedReport.class))
		{
			ChatReceivedReport report = (ChatReceivedReport) arg;
			map.addChat(report.toString(), report.getType());
		}
		else if (arg.getClass().equals(PlayerConnectedToAreaServerReport.class))
		{
			PlayerConnectedToAreaServerReport report = (PlayerConnectedToAreaServerReport) arg;
			PlayerType type = PlayerType.valueOf(report.getPlayerAppearanceType());
			Position pos = report.getPlayerPosition();
			map.addPlayer(report.getPlayerID(), type, pos, report.isThisClientsPlayer());
		}
		else if (arg.getClass().equals(NewMapReport.class))
		{
			NewMapReport report = (NewMapReport) arg;
			map.setTiledMap(report.getTiledMap());
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
		reportTypes.add(PlayerMovedReport.class);
		reportTypes.add(ChatReceivedReport.class);
		reportTypes.add(NewMapReport.class);
		// reportTypes.add(OtherPlayerMovedReport.class);

		return reportTypes;
	}

}
