package sequenceTests;
import java.io.IOException;
import java.util.ArrayList;

import communication.messages.ConnectMessage;
import communication.messages.InitializeThisClientsPlayerMessage;
import communication.messages.LoginMessage;
import communication.messages.LoginSuccessfulMessage;
import communication.messages.MapFileMessage;
import communication.messages.Message;
import communication.messages.PlayerJoinedMessage;
import datasource.LevelRecord;
import datasource.LevelsForTest;
import datasource.PlayersForTest;
import datasource.ServersForTest;

public class LoginSequenceTest extends SequenceTest
{


	private ArrayList<MessageFlow> messageSequence = new ArrayList<MessageFlow>();

	public LoginSequenceTest() throws IOException
	{
		addMessage(SequenceTestRunner.ServerType.THIS_PLAYER_CLIENT, SequenceTestRunner.ServerType.LOGIN_SERVER,
				new LoginMessage(PlayersForTest.MERLIN.getMapName(),
						PlayersForTest.MERLIN.getPlayerPassword()));
		addMessage(SequenceTestRunner.ServerType.LOGIN_SERVER, SequenceTestRunner.ServerType.THIS_PLAYER_CLIENT,
				new LoginSuccessfulMessage(PlayersForTest.MERLIN.getPlayerID(),
						ServersForTest.FIRST_SERVER.getHostName(),
						ServersForTest.FIRST_SERVER.getPortNumber(), 33));
		addMessage(SequenceTestRunner.ServerType.THIS_PLAYER_CLIENT, SequenceTestRunner.ServerType.AREA_SERVER, new ConnectMessage(
				PlayersForTest.MERLIN.getPlayerID(), 33));
		addMessage(
				SequenceTestRunner.ServerType.AREA_SERVER,
				SequenceTestRunner.ServerType.THIS_PLAYER_CLIENT,
				new PlayerJoinedMessage(PlayersForTest.MERLIN.getPlayerID(),
						PlayersForTest.MERLIN.getPlayerName(), PlayersForTest.MERLIN
								.getAppearanceType(), PlayersForTest.MERLIN.getPosition()));
		addMessage(
				SequenceTestRunner.ServerType.AREA_SERVER,
				SequenceTestRunner.ServerType.OTHER_CLIENT,
				new PlayerJoinedMessage(PlayersForTest.MERLIN.getPlayerID(),
						PlayersForTest.MERLIN.getPlayerName(), PlayersForTest.MERLIN
								.getAppearanceType(), PlayersForTest.MERLIN.getPosition()));
		addMessage(SequenceTestRunner.ServerType.AREA_SERVER, SequenceTestRunner.ServerType.THIS_PLAYER_CLIENT, new MapFileMessage(
				ServersForTest.FIRST_SERVER.getMapName()));
		// TODO I need to put merlin's quest/adventure state into the message
		addMessage(
				SequenceTestRunner.ServerType.AREA_SERVER,
				SequenceTestRunner.ServerType.THIS_PLAYER_CLIENT,
				new InitializeThisClientsPlayerMessage(null, PlayersForTest.MERLIN
						.getExperiencePoints(), new LevelRecord(LevelsForTest.TWO
						.getDescription(), LevelsForTest.TWO.getLevelUpPoints())));
	}

	private void addMessage(SequenceTestRunner.ServerType source, SequenceTestRunner.ServerType destination, Message message)
	{
		messageSequence.add(new MessageFlow(source, destination, message));
	}

	public SequenceTestRunner.ServerType getInitiatingServerType()
	{
		return SequenceTestRunner.ServerType.THIS_PLAYER_CLIENT;
	}

	public Command getInitiatingCommand()
	{
		
	}
}
