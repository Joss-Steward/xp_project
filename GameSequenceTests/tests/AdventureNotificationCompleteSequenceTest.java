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

/**
 * Tests that a notification is sent when an adventure is completed
 * @author Ronald Sease & Evan Stevenson
 */
public class AdventureNotificationCompleteSequenceTest extends SequenceTest
{
	private MessageFlow[] sequence = 
		{
				new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
						new AdventureNotificationCompleteMessage(PlayersForTest.MERLIN.getPlayerID(), 1, 1), false)
		};
	
	/**
	 * @throws IOException
	 *         shouldn't
	 */
	public AdventureNotificationCompleteSequenceTest() throws IOException
	{
		for(MessageFlow mf: sequence)
		{
			messageSequence.add(mf);
		}
	}

	/** 
	 * @see model.SequenceTest#getInitiatingCommand()
	 */
	@Override
	public Command getInitiatingCommand() 
	{
		return new CommandAdventureNotificationComplete(PlayersForTest.MERLIN.getPlayerID(), 1, 1);
	}

	/** 
	 * @see model.SequenceTest#getInitiatingServerType()
	 */
	
	@Override
	public ServerType getInitiatingServerType() 
	{
		return ServerType.THIS_PLAYER_CLIENT;
	}

	/** 
	 * @see model.SequenceTest#getInitiatingPlayerID()
	 */
	@Override
	public int getInitiatingPlayerID() 
	{
		return PlayersForTest.MERLIN.getPlayerID();
	}

	/** 
	 * @see model.SequenceTest#setUpServer()
	 */
	@Override
	public void setUpServer() 
	{
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

	/**
	 * @see model.SequenceTest#resetDataGateways()
	 */
	@Override
	public void resetDataGateways() 
	{
		PlayerManager.resetSingleton();
	}
}
