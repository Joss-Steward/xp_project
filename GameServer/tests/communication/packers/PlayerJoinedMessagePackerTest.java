package communication.packers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import model.OptionsManager;
import model.Player;
import model.PlayerManager;
import model.reports.AddExistingPlayerReport;
import model.reports.PlayerConnectionReport;

import org.junit.Before;
import org.junit.Test;

import testData.PlayersForTest;
import communication.StateAccumulator;
import communication.messages.PlayerJoinedMessage;
import datasource.DatabaseException;
import datasource.PlayerRowDataGatewayMock;

/**
 * @author Merlin
 * 
 */
public class PlayerJoinedMessagePackerTest
{

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		PlayerManager.resetSingleton();
		OptionsManager.getSingleton().setTestMode(true);
		new PlayerRowDataGatewayMock().resetData();
	}

	/**
	 * Checks that existing players are notified when a player is added
	 * 
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void ifThePlayerIsNotOnThisConnection() throws DatabaseException
	{
		PlayerManager playerManager = PlayerManager.getSingleton();
		playerManager.addPlayer(PlayersForTest.MERLIN.getPlayerID());
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(PlayersForTest.MERLIN.getPlayerID());
		playerManager.addPlayer(PlayersForTest.JOHN.getPlayerID());

		Player playerFromID = playerManager.getPlayerFromID(PlayersForTest.JOHN.getPlayerID());
		PlayerConnectionReport report = new PlayerConnectionReport(playerFromID.getPlayerID(),
				playerFromID.getPlayerName(), playerFromID.getAppearanceType(), playerFromID.getPlayerPosition(),
				playerFromID.getCrew(), playerFromID.getMajor());
		PlayerJoinedMessagePacker packer = new PlayerJoinedMessagePacker();
		packer.setAccumulator(stateAccumulator);
		PlayerJoinedMessage msg = (PlayerJoinedMessage) packer.pack(report);
		assertEquals(PlayersForTest.JOHN.getPlayerName(), msg.getPlayerName());
		assertEquals(PlayersForTest.JOHN.getAppearanceType(), msg.getAppearanceType());
		assertEquals(PlayersForTest.JOHN.getPlayerID(), msg.getPlayerID());
		assertEquals(PlayersForTest.JOHN.getPosition(), msg.getPosition());
		assertEquals(PlayersForTest.JOHN.getCrew(), msg.getCrew());
	}

	/**
	 * When a player logs it, we get an AddExistingPlayerReport for each player
	 * on the server. If the message is targeted at our accumulator, we should
	 * pack a PlayerJoinedMessage
	 * 
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void addNotifiesAboutExistingPlayer() throws DatabaseException
	{
		PlayerManager playerManager = PlayerManager.getSingleton();
		playerManager.addPlayer(PlayersForTest.JOHN.getPlayerID());
		playerManager.addPlayer(PlayersForTest.MERLIN.getPlayerID());
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(PlayersForTest.MERLIN.getPlayerID());

		AddExistingPlayerReport report = new AddExistingPlayerReport(PlayersForTest.MERLIN.getPlayerID(),
				PlayersForTest.JOHN.getPlayerID(), PlayersForTest.JOHN.getPlayerName(),
				PlayersForTest.JOHN.getAppearanceType(), PlayersForTest.JOHN.getPosition(),
				PlayersForTest.JOHN.getCrew(), PlayersForTest.JOHN.getMajor());
		PlayerJoinedMessagePacker packer = new PlayerJoinedMessagePacker();
		packer.setAccumulator(stateAccumulator);
		PlayerJoinedMessage msg = (PlayerJoinedMessage) packer.pack(report);
		assertEquals(PlayersForTest.JOHN.getPlayerName(), msg.getPlayerName());
		assertEquals(PlayersForTest.JOHN.getAppearanceType(), msg.getAppearanceType());
		assertEquals(PlayersForTest.JOHN.getPlayerID(), msg.getPlayerID());
		assertEquals(PlayersForTest.JOHN.getPosition(), msg.getPosition());
		assertEquals(PlayersForTest.JOHN.getCrew(), msg.getCrew());
	}

	/**
	 * Add existing player reports should only be sent by the accumulator that
	 * is talking to the recipient player
	 * 
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void ignoresExistingPlayerWhenNotMine() throws DatabaseException
	{
		PlayerManager playerManager = PlayerManager.getSingleton();
		playerManager.addPlayer(PlayersForTest.JOHN.getPlayerID());
		playerManager.addPlayer(PlayersForTest.MERLIN.getPlayerID());
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(PlayersForTest.JOHN.getPlayerID());

		AddExistingPlayerReport report = new AddExistingPlayerReport(PlayersForTest.MERLIN.getPlayerID(),
				PlayersForTest.JOHN.getPlayerID(), PlayersForTest.JOHN.getPlayerName(),
				PlayersForTest.JOHN.getAppearanceType(), PlayersForTest.JOHN.getPosition(),
				PlayersForTest.JOHN.getCrew(), PlayersForTest.JOHN.getMajor());
		PlayerJoinedMessagePacker packer = new PlayerJoinedMessagePacker();
		packer.setAccumulator(stateAccumulator);
		PlayerJoinedMessage msg = (PlayerJoinedMessage) packer.pack(report);
		assertNull(msg);
	}
}
