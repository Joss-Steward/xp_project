import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import communication.messages.AdventureNotificationCompleteMessage;
import model.ClientPlayerManager;
import model.Command;
import model.CommandAdventureNotificationComplete;
import model.MapManager;
import model.MessageFlow;
import model.PlayerManager;
import model.SequenceTest;
import model.ServerType;
import testData.PlayersForTest;

public class AdventureNotificationCompleteSequenceTest extends SequenceTest
{
	private MessageFlow[] sequence = 
		{
				new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
						new AdventureNotificationCompleteMessage(PlayersForTest.MERLIN.getPlayerID(), 1, 1), false)
						//quest adventure
		};
	
	public AdventureNotificationCompleteSequenceTest() throws IOException
	{
		for(MessageFlow mf: sequence)
		{
			messageSequence.add(mf);
		}
	}

	@Override
	public Command getInitiatingCommand() {
		// TODO Auto-generated method stub
		return new CommandAdventureNotificationComplete(PlayersForTest.MERLIN.getPlayerID(), 1, 1);
	}

	@Override
	public ServerType getInitiatingServerType() {
		// TODO Auto-generated method stub
		return ServerType.THIS_PLAYER_CLIENT;
	}

	@Override
	public int getInitiatingPlayerID() {
		// TODO Auto-generated method stub
		return PlayersForTest.MERLIN.getPlayerID();
	}

	@Override
	public void setUpServer() {
		try
		{
			MapManager.getSingleton().changeToNewFile(PlayersForTest.MERLIN.getMapName());
			ClientPlayerManager clientPlayerManager = ClientPlayerManager.getSingleton();
			clientPlayerManager.initiateLogin(PlayersForTest.MERLIN.getPlayerName(), PlayersForTest.MATT.getPlayerPassword());
			clientPlayerManager.finishLogin(PlayersForTest.MERLIN.getPlayerID());
			PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		} catch (AlreadyBoundException | NotBoundException e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void resetDataGateways() {
		PlayerManager.resetSingleton();
		// TODO Auto-generated method stub

	}

}
