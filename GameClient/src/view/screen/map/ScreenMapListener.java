package view.screen.map;

import java.util.ArrayList;

import view.player.PlayerType;
import view.screen.ScreenListener;
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
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport arg)
	{
		ScreenMap map = (ScreenMap) this.screen;

		// adds your player's sprite to this client
		if (arg.getClass().equals(ThisClientsPlayerMovedReport.class))
		{
			ThisClientsPlayerMovedReport report = (ThisClientsPlayerMovedReport) arg;
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
			map.addUIs(type);
		}
		else if (arg.getClass().equals(PlayerDisconnectedFromAreaServerReport.class))
		{
			PlayerDisconnectedFromAreaServerReport report = (PlayerDisconnectedFromAreaServerReport) arg;
			map.removePlayer(report.getPlayerID());
		}
		else if (arg.getClass().equals(ChangeMapReport.class))
		{
			map.setTiledMap(null);
		}
		else if (arg.getClass().equals(NewMapReport.class))
		{
			NewMapReport report = (NewMapReport) arg;
			map.setTiledMap(report.getTiledMap());
		}
	}

	/**
	 * @see view.screen.ScreenListener#getReportTypes()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = new ArrayList<Class<? extends QualifiedObservableReport>>();
		reportTypes.add(PlayerConnectedToAreaServerReport.class);
		reportTypes.add(PlayerDisconnectedFromAreaServerReport.class);
		reportTypes.add(ThisClientsPlayerMovedReport.class);
		reportTypes.add(ChatReceivedReport.class);
		reportTypes.add(NewMapReport.class);
		reportTypes.add(ChangeMapReport.class);
		reportTypes.add(PinFailedReport.class);
		// reportTypes.add(OtherPlayerMovedReport.class);

		return reportTypes;
	}

}
