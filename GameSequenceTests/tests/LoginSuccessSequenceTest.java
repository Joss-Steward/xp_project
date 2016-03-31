import java.io.IOException;
import java.util.ArrayList;

import testData.LevelsForTest;
import testData.PlayersForTest;
import testData.ServersForTest;
import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;
import model.Command;
import model.CommandLogin;
import model.MessageFlow;
import model.OptionsManager;
import model.PlayerManager;
import model.SequenceTest;
import model.ServerType;
import communication.messages.ConnectMessage;
import communication.messages.InitializeThisClientsPlayerMessage;
import communication.messages.LoginMessage;
import communication.messages.LoginSuccessfulMessage;
import communication.messages.MapFileMessage;
import communication.messages.PlayerJoinedMessage;
import communication.packers.MapFileMessagePacker;
import data.AdventureRecord;
import data.AdventureStateRecord;
import data.QuestStateRecord;
import datasource.AdventureStateTableDataGateway;
import datasource.AdventureStateTableDataGatewayMock;
import datasource.AdventureTableDataGateway;
import datasource.AdventureTableDataGatewayMock;
import datasource.DatabaseException;
import datasource.LevelRecord;
import datasource.PlayerConnectionRowDataGatewayMock;
import datasource.QuestRowDataGateway;
import datasource.QuestRowDataGatewayMock;
import datasource.QuestStateTableDataGateway;
import datasource.QuestStateTableDataGatewayMock;

/**
 * Defines the protocol for a successful login sequence
 * 
 * @author Merlin
 *
 */
public class LoginSuccessSequenceTest extends SequenceTest
{

	private MessageFlow[] sequence =
	{
			new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.LOGIN_SERVER,
					new LoginMessage(PlayersForTest.MERLIN.getPlayerName(),
							PlayersForTest.MERLIN.getPlayerPassword()), true),
			new MessageFlow(ServerType.LOGIN_SERVER, ServerType.THIS_PLAYER_CLIENT,
					new LoginSuccessfulMessage(PlayersForTest.MERLIN.getPlayerID(),
							ServersForTest.CURRENT.getHostName(),
							ServersForTest.CURRENT.getPortNumber(), 33), true),
			new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
					new ConnectMessage(PlayersForTest.MERLIN.getPlayerID(),
							PlayersForTest.MERLIN.getPin()), true),
			new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
					new PlayerJoinedMessage(PlayersForTest.MERLIN.getPlayerID(),
							PlayersForTest.MERLIN.getPlayerName(),
							PlayersForTest.MERLIN.getAppearanceType(),
							PlayersForTest.MERLIN.getPosition(),
							PlayersForTest.MERLIN.getCrew()), true),
			new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
					new PlayerJoinedMessage(PlayersForTest.MERLIN.getPlayerID(),
							PlayersForTest.MERLIN.getPlayerName(),
							PlayersForTest.MERLIN.getAppearanceType(),
							PlayersForTest.MERLIN.getPosition(),
							PlayersForTest.MERLIN.getCrew()), true),
			new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
					new MapFileMessage(MapFileMessagePacker.DIRECTORY_PREFIX
							+ ServersForTest.CURRENT.getMapName()), true),
			new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
					new InitializeThisClientsPlayerMessage(
							getPlayersQuest(PlayersForTest.MERLIN.getPlayerID()),
							PlayersForTest.MERLIN.getExperiencePoints(), new LevelRecord(
									LevelsForTest.TWO.getDescription(),
									LevelsForTest.TWO.getLevelUpPoints(),
									LevelsForTest.TWO.getLevelUpMonth(),
									LevelsForTest.TWO.getLevelUpDayOfMonth())), true) };

	/**
	 * @throws IOException
	 *             shouldn't
	 */
	public LoginSuccessSequenceTest() throws IOException
	{
		for (MessageFlow mf : sequence)
		{
			messageSequence.add(mf);
		}
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
				qGateway.getQuestTitle(), qGateway.getQuestDescription(), q.getState(),
				qGateway.getExperiencePointsGained(),
				qGateway.getAdventuresForFulfillment(), q.isNeedingNotification());
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
							.isNeedingNotification(), adventureRecord.isRealLifeAdventure(), adventureRecord.getCompletionCriteria().toString()));

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
		PlayerManager.resetSingleton();

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
