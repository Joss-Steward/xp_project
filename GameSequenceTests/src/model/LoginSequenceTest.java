package model;

import java.io.IOException;
import java.util.ArrayList;

import communication.messages.ConnectMessage;
import communication.messages.InitializeThisClientsPlayerMessage;
import communication.messages.LoginMessage;
import communication.messages.LoginSuccessfulMessage;
import communication.messages.MapFileMessage;
import communication.messages.PlayerJoinedMessage;

import datasource.LevelRecord;
import datasource.LevelsForTest;
import datasource.PlayersForTest;
import datasource.ServersForTest;

/**
 * Defines the protocol for a successful login sequence
 * 
 * @author Merlin
 *
 */
public class LoginSequenceTest extends SequenceTest
{

	private MessageFlow[] messageSequence =
	{
			new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.LOGIN_SERVER,
					new LoginMessage(PlayersForTest.MERLIN.getPlayerName(),
							PlayersForTest.MERLIN.getPlayerPassword())),
			new MessageFlow(ServerType.LOGIN_SERVER, ServerType.THIS_PLAYER_CLIENT,
					new LoginSuccessfulMessage(PlayersForTest.MERLIN.getPlayerID(),
							ServersForTest.CURRENT.getHostName(),
							ServersForTest.CURRENT.getPortNumber(), 33)),
			new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
					new ConnectMessage(PlayersForTest.MERLIN.getPlayerID(), 33)),
			new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
					new PlayerJoinedMessage(PlayersForTest.MERLIN.getPlayerID(),
							PlayersForTest.MERLIN.getPlayerName(),
							PlayersForTest.MERLIN.getAppearanceType(),
							PlayersForTest.MERLIN.getPosition())),
			new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
					new PlayerJoinedMessage(PlayersForTest.MERLIN.getPlayerID(),
							PlayersForTest.MERLIN.getPlayerName(),
							PlayersForTest.MERLIN.getAppearanceType(),
							PlayersForTest.MERLIN.getPosition())),
			new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
					new MapFileMessage(ServersForTest.CURRENT.getMapName())),
			// TODO I need to put merlin's quest/adventure state into the
			// message 
			new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
					new InitializeThisClientsPlayerMessage(
							new ArrayList<ClientPlayerQuest>(),
							PlayersForTest.MERLIN.getExperiencePoints(), new LevelRecord(
									LevelsForTest.TWO.getDescription(),
									LevelsForTest.TWO.getLevelUpPoints()))) };

	/**
	 * @throws IOException
	 *             shouldn't
	 */
	public LoginSequenceTest() throws IOException
	{

	}

	/**
	 * @see SequenceTest#getInitiatingCommand()
	 */
	public Command getInitiatingCommand()
	{
		return new CommandLogin(PlayersForTest.MERLIN.getPlayerName(),
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
}
