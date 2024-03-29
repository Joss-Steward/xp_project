

import java.io.IOException;

import testData.PlayersForTest;
import model.Command;
import model.MessageFlow;
import model.OptionsManager;
import model.PlayerManager;
import model.SequenceTest;
import model.ServerType;
import communication.messages.ConnectMessage;
import communication.messages.PinFailedMessage;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;

/**
 * Defines the protocol for what happens when the client tries to connect to an
 * area server giving an invalid pin
 * 
 * @author Merlin
 *
 */
public class LoginBadPinSequenceTest extends SequenceTest
{

	private MessageFlow[] sequence =
	{
			new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
					new ConnectMessage(PlayersForTest.MERLIN.getPlayerID(),
							PlayersForTest.MERLIN.getPin() + 3), false),
			new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
					new PinFailedMessage(PlayersForTest.MERLIN.getPlayerID()), true) };

	/**
	 * @throws IOException
	 *             shouldn't
	 */
	public LoginBadPinSequenceTest() throws IOException
	{
		for (MessageFlow mf : sequence)
		{
			messageSequence.add(mf);
		}
	}

	/**
	 * @see SequenceTest#getInitiatingCommand()
	 */
	public Command getInitiatingCommand()
	{
		return null;
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
