package testData;


/**
 * The players that are in the test database
 * 
 * @author Merlin
 * 
 */
public enum NPCsForTest
{

	/**
	 * 
	 */
	NPC1(PlayersForTest.MOCK_NPC.getPlayerID(),
			"model.NPCMockBehavior"),
	/**
	 * 
	 */
	NPC2(PlayersForTest.QUIZBOT.getPlayerID(),
			"model.QuizBotBehavior"),
	/**
	 * 
	 */
	NPC3(PlayersForTest.MOCK_NPC3.getPlayerID(),
			"model.NPCMockBehavior"),
	
	/**
	 * 
	 */
	REDHAT(PlayersForTest.RED_HAT.getPlayerID(),"model.RedHatBehavior");

	private String behaviorClass;
	private int playerID;

	NPCsForTest(int playerID, String behaviorClass)
	{
		this.playerID = playerID;
		this.behaviorClass = behaviorClass;
	}

	/**
	 * @return the map name the pin for the current connection is good for
	 */
	public String getBehaviorClass()
	{
		return behaviorClass;
	}

	/**
	 * @return this NPCs playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

}