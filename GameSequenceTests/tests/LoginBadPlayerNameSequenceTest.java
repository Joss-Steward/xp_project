

import java.io.IOException;

import testData.PlayersForTest;
import model.Command;
import model.CommandLogin;
import model.MessageFlow;
import model.OptionsManager;
import model.PlayerManager;
import model.SequenceTest;
import model.ServerType;
import communication.messages.LoginFailedMessage;
import communication.messages.LoginMessage;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;

/**
 * Defines the protocol for a successful login sequence
 * 
 * @author Merlin
 *
 */
public class LoginBadPlayerNameSequenceTest extends SequenceTest
{

	private MessageFlow[] sequence =
	{
			new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.LOGIN_SERVER,
					new LoginMessage(PlayersForTest.MERLIN.getPlayerName()+"Z",
							PlayersForTest.MERLIN.getPlayerPassword()), true),
			new MessageFlow(ServerType.LOGIN_SERVER, ServerType.THIS_PLAYER_CLIENT,
					new LoginFailedMessage(), true)
			 };

	/**
	 * @throws IOException
	 *             shouldn't
	 */
	public LoginBadPlayerNameSequenceTest() throws IOException
	{
		for (MessageFlow mf:sequence)
		{
			messageSequence.add(mf);
		}
	}

	/**
	 * @see SequenceTest#getInitiatingCommand()
	 */
	public Command getInitiatingCommand()
	{
		return new CommandLogin(PlayersForTest.MERLIN.getPlayerName()+"Z",
				PlayersForTest.MERLIN.getPlayerPassword());
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
		OptionsManager.getSingleton().setMapName(PlayersForTest.MERLIN.getMapName());
	}
	
	/**
	 * @see model.SequenceTest#resetDataGateways()
	 */
	public void resetDataGateways()
	{
		try
		{
			PlayerManager.resetSingleton();
			(new PlayerConnectionRowDataGatewayMock(2)).resetData();
		} catch (DatabaseException e)
		{
			e.printStackTrace();
		}
	}
}
