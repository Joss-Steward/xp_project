package communication.packers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import model.LevelManager;
import model.OptionsManager;
import model.PlayerManager;
import model.QuestManager;
import model.reports.KnowledgePointsChangeReport;

import org.junit.Before;
import org.junit.Test;

import testData.PlayersForTest;
import communication.StateAccumulator;
import communication.messages.KnowledgeChangedMessage;
import datasource.DatabaseException;
import datasource.LevelRecord;

/**
 * @author Matthew Croft
 *
 */
public class KnowledgeChangedMessagePackerTest {
	private StateAccumulator stateAccumulator;
	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setTestMode(true);
		QuestManager.resetSingleton();
		
		PlayerManager playerManager = PlayerManager.getSingleton();
		playerManager.addPlayer(PlayersForTest.JOHN.getPlayerID());
		playerManager.addPlayer(PlayersForTest.MERLIN.getPlayerID());
		stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(PlayersForTest.MERLIN.getPlayerID());
	}
	/**
	 * 
	 */
	@Test
	public void testReportWePack()
	{
		KnowledgeChangePacker packer = new KnowledgeChangePacker();
		
		assertEquals(KnowledgePointsChangeReport.class, packer.getReportTypesWePack().get(0));
	}
	
	/**
	 * the message should contain the appropriate player's ID, experience points and level object
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testPackedObjectIsCurrentPlayer() throws DatabaseException
	{
		LevelRecord record = LevelManager.getSingleton().getLevelForPoints(PlayersForTest.MERLIN.getKnowledgeScore()); 
		KnowledgePointsChangeReport report = new KnowledgePointsChangeReport(PlayersForTest.MERLIN.getPlayerID(), PlayersForTest.MERLIN.getKnowledgeScore(), record);
		KnowledgeChangePacker packer = new KnowledgeChangePacker();
		packer.setAccumulator(stateAccumulator);
 
		KnowledgeChangedMessage msg = (KnowledgeChangedMessage) packer.pack(report);
		
		assertEquals(PlayersForTest.MERLIN.getKnowledgeScore(), msg.getKnowledgePoints());
		assertEquals(record, msg.getLevel());
	}
	
	/**
	 * If the report is not about the player we are communicating with, we should ignore it
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void ifItIsntAboutUsDontPack() throws DatabaseException
	{

		KnowledgeChangePacker packer = new KnowledgeChangePacker();
		packer.setAccumulator(stateAccumulator);

		KnowledgePointsChangeReport report = new KnowledgePointsChangeReport(PlayersForTest.JOHN.getPlayerID(), PlayersForTest.JOHN.getKnowledgeScore(), LevelManager.getSingleton().getLevelForPoints(PlayersForTest.JOHN.getKnowledgeScore()));
		assertNull(packer.pack(report));
	}
}
