

import java.io.IOException;

import model.Command;
import model.CommandLogin;
import model.MessageFlow;
import model.OptionsManager;
import model.SequenceTest;
import model.ServerType;
import communication.messages.LoginFailedMessage;
import communication.messages.LoginMessage;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;
import datasource.PlayersForTest;

/**
 * Defines the protocol for a successful login sequence
 * 
 * @author Merlin
 *
 */
public class LoginBadPWSequenceTest extends SequenceTest
{

	private MessageFlow[] sequence =
	{
			new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.LOGIN_SERVER,
					new LoginMessage(PlayersForTest.MERLIN.getPlayerName(),
							PlayersForTest.MERLIN.getPlayerPassword()+"Z"), true),
			new MessageFlow(ServerType.LOGIN_SERVER, ServerType.THIS_PLAYER_CLIENT,
					new LoginFailedMessage(), true)
			 };

	/**
	 * @throws IOException
	 *             shouldn't
	 */
	public LoginBadPWSequenceTest() throws IOException
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
		return new CommandLogin(PlayersForTest.MERLIN.getPlayerName(),
				PlayersForTest.MERLIN.getPlayerPassword()+"Z");
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
			(new PlayerConnectionRowDataGatewayMock(2)).resetData();
		} catch (DatabaseException e)
		{
			e.printStackTrace();
		}
	}
}
