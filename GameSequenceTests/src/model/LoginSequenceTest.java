package model;

import java.io.IOException;
import java.util.ArrayList;

import communication.messages.ConnectMessage;
import communication.messages.InitializeThisClientsPlayerMessage;
import communication.messages.LoginMessage;
import communication.messages.LoginSuccessfulMessage;
import communication.messages.MapFileMessage;
import communication.messages.PlayerJoinedMessage;
import communication.packers.MapFileMessagePacker;

import datasource.AdventureRecord;
import datasource.AdventureStateRecord;
import datasource.AdventureStateTableDataGateway;
import datasource.AdventureStateTableDataGatewayMock;
import datasource.AdventureTableDataGateway;
import datasource.AdventureTableDataGatewayMock;
import datasource.DatabaseException;
import datasource.LevelRecord;
import datasource.LevelsForTest;
import datasource.PlayersForTest;
import datasource.QuestRowDataGateway;
import datasource.QuestRowDataGatewayMock;
import datasource.QuestStateRecord;
import datasource.QuestStateTableDataGateway;
import datasource.QuestStateTableDataGatewayMock;
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
					new ConnectMessage(PlayersForTest.MERLIN.getPlayerID(),
							PlayersForTest.MERLIN.getPin())),
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
					new MapFileMessage(MapFileMessagePacker.DIRECTORY_PREFIX
							+ ServersForTest.CURRENT.getMapName())),
			new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
					new InitializeThisClientsPlayerMessage(
							getPlayersQuest(PlayersForTest.MERLIN.getPlayerID()),
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

	private ArrayList<ClientPlayerQuest> getPlayersQuest(int playerID)
	{
		ArrayList<ClientPlayerQuest> result = new ArrayList<ClientPlayerQuest>();
		QuestStateTableDataGateway qsGateway = QuestStateTableDataGatewayMock
				.getSingleton();
		try
		{
			for (QuestStateRecord q : qsGateway.getQuestStates(playerID))
			{
				result.add(createClientPlayerQuestFor(q));
			}
		} catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	private ClientPlayerQuest createClientPlayerQuestFor(QuestStateRecord q)
			throws DatabaseException
	{
		QuestRowDataGateway qGateway = new QuestRowDataGatewayMock(q.getQuestID());
		ClientPlayerQuest cpq = new ClientPlayerQuest(q.getQuestID(),
				qGateway.getQuestDescription(), q.getState(),
				qGateway.getExperiencePointsGained(),
				qGateway.getAdventuresForFulfillment());
		AdventureStateTableDataGateway asGateway = AdventureStateTableDataGatewayMock
				.getSingleton();
		ArrayList<AdventureStateRecord> adventuresForPlayer = asGateway
				.getAdventureStates(q.getPlayerID(), q.getQuestID());
		for (AdventureStateRecord adv : adventuresForPlayer)
		{

			AdventureTableDataGateway aGateway = AdventureTableDataGatewayMock
					.getSingleton();
			AdventureRecord adventureRecord = aGateway.getAdventure(q.getQuestID(),
					adv.getAdventureID());

			cpq.addAdventure(new ClientPlayerAdventure(adv.getAdventureID(),
					adventureRecord.getAdventureDescription(), adventureRecord
							.getExperiencePointsGained(), adv.getState(), adv
							.isNeedingNotification()));

		}
		return cpq;
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
