package model;

import java.io.IOException;

import communication.messages.LoginFailedMessage;
import communication.messages.LoginMessage;

import datasource.PlayersForTest;

/**
 * Defines the protocol for a successful login sequence
 * 
 * @author Merlin
 *
 */
public class LoginBadPlayerNameSequenceTest extends SequenceTest
{

	private MessageFlow[] messageSequence =
	{
			new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.LOGIN_SERVER,
					new LoginMessage(PlayersForTest.MERLIN.getPlayerName()+"Z",
							PlayersForTest.MERLIN.getPlayerPassword())),
			new MessageFlow(ServerType.LOGIN_SERVER, ServerType.THIS_PLAYER_CLIENT,
					new LoginFailedMessage())
			 };

	/**
	 * @throws IOException
	 *             shouldn't
	 */
	public LoginBadPlayerNameSequenceTest() throws IOException
	{

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
	 * @see model.SequenceTest#getMessageSequence()
	 */
	@Override
	public MessageFlow[] getMessageSequence()
	{
		return messageSequence;
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
}
