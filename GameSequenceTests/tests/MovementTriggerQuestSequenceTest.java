import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.ClientPlayerManager;
import model.Command;
import model.CommandClientMovePlayer;
import model.MapManager;
import model.MessageFlow;
import model.PlayerManager;
import model.SequenceTest;
import model.ServerType;

import communication.messages.OtherPlayerMovedMessage;
import communication.messages.PlayerMovedMessage;
import communication.messages.QuestStateChangeMessage;

import datasource.PlayersForTest;
import datasource.QuestStateEnum;
import datasource.QuestsForTest;

/**
 * Defines the protocol for a successful login sequence
 * 
 * @author Merlin
 *
 */
public class MovementTriggerQuestSequenceTest extends SequenceTest
{

	private MessageFlow[] sequence =
	{
			new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
					new PlayerMovedMessage(PlayersForTest.MATT.getPlayerID(),
							QuestsForTest.THE_LITTLE_QUEST.getPosition()), true),
			new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
					new OtherPlayerMovedMessage(PlayersForTest.MATT.getPlayerID(),
							QuestsForTest.THE_LITTLE_QUEST.getPosition()), true),
			new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
					new QuestStateChangeMessage(PlayersForTest.MATT.getPlayerID(),
							QuestsForTest.THE_LITTLE_QUEST.getQuestID(),
							QuestsForTest.THE_LITTLE_QUEST.getQuestDescription(),
							QuestStateEnum.TRIGGERED), true) };

	/**
	 * @throws IOException
	 *             shouldn't
	 */
	public MovementTriggerQuestSequenceTest() throws IOException
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
		return new CommandClientMovePlayer(PlayersForTest.MATT.getPlayerID(),
				QuestsForTest.THE_LITTLE_QUEST.getPosition());
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
		return PlayersForTest.MATT.getPlayerID();
	}

	/**
	 * @see model.SequenceTest#setUpServer()
	 */
	@Override
	public void setUpServer()
	{
		try
		{
			MapManager.getSingleton().changeToNewFile(PlayersForTest.MATT.getMapName());
			ClientPlayerManager clientPlayerManager = ClientPlayerManager.getSingleton();
			clientPlayerManager.initiateLogin(PlayersForTest.MATT.getPlayerName(),
					PlayersForTest.MATT.getPlayerPassword());
			clientPlayerManager.finishLogin(PlayersForTest.MATT.getPlayerID());
			PlayerManager.getSingleton().addPlayer(PlayersForTest.MATT.getPlayerID());
		} catch (AlreadyBoundException | NotBoundException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see model.SequenceTest#resetDataGateways()
	 */
	public void resetDataGateways()
	{
		PlayerManager.resetSingleton();
	}
}
