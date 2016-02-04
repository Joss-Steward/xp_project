import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.ClientPlayerManager;
import model.Command;
import model.CommandMovePlayer;
import model.MessageFlow;
import model.OptionsManager;
import model.PlayerManager;
import model.SequenceTest;
import model.ServerType;
import communication.messages.PlayerMovedMessage;
import data.Position;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGatewayMock;
import datasource.PlayersForTest;

/**
 * Defines the protocol for a successful login sequence
 * 
 * @author Merlin
 *
 */
public class MovementBasicSequenceTest extends SequenceTest
{

	private MessageFlow[] sequence =
	{
			new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
					new PlayerMovedMessage(PlayersForTest.MATT.getPlayerID(),
							new Position(PlayersForTest.MATT.getPosition().getRow(),
									PlayersForTest.MATT.getPosition().getColumn() + 1)),
					true),
			new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
					new PlayerMovedMessage(PlayersForTest.MATT.getPlayerID(),
							new Position(PlayersForTest.MATT.getPosition().getRow(),
									PlayersForTest.MATT.getPosition().getColumn() + 1)),
					true) };

	/**
	 * @throws IOException
	 *             shouldn't
	 */
	public MovementBasicSequenceTest() throws IOException
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
		return new CommandMovePlayer(PlayersForTest.MATT.getPlayerID(), new Position(
				PlayersForTest.MATT.getPosition().getRow(), PlayersForTest.MATT
						.getPosition().getColumn() + 1));
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
//		try
//		{
			ClientPlayerManager clientPlayerManager = ClientPlayerManager.getSingleton();
			PlayerManager.getSingleton().addPlayer(PlayersForTest.MATT.getPlayerID());
//			clientPlayerManager.finishLogin(PlayersForTest.MATT.getPlayerID());
//		} catch (AlreadyBoundException | NotBoundException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		.initializePlayer(
//				PlayersForTest.MATT.getPlayerID(), PlayersForTest.MATT.getPlayerName(),
//				PlayersForTest.MATT.getAppearanceType(),
//				PlayersForTest.MATT.getPosition());
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
