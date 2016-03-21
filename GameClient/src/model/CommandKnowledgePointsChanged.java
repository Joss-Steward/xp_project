package model;

import communication.messages.InitializeThisClientsPlayerMessage;

import datasource.LevelRecord;

/**
 * @author Matthew Croft
 * @author Evan Stevenson
 *
 */
public class CommandKnowledgePointsChanged extends Command
{


	private int knowledge;
	private LevelRecord record;
	private int playerID;

	/**
	 * constructor for the command
	 * @param msg InitializeClientsPlayerMessage which contains all the data to
	 * initialize this client player
	 */
	public CommandKnowledgePointsChanged(InitializeThisClientsPlayerMessage msg) 
	{
		this.knowledge = msg.getKnowledgePoints();
		this.record = msg.getLevel();
	}
	
	
	/**
	 * @param playerID of the player
	 * @param knowledgePoints of the player
	 * @param level LevelRecord for this player
	 */
	public CommandKnowledgePointsChanged(int playerID, int knowledgePoints,	LevelRecord level) 
	{
		this.knowledge = knowledgePoints;
		this.playerID = playerID;
		this.record = level;
	}

	/**
	 * @return the knowledgePoints for this player
	 */
	public int getKnowledge() {
		return knowledge;
	}

	/**
	 * @return the level record for this player
	 */
	public LevelRecord getRecord() {
		return record;
	}

	/**
	 * @return the playerID for this player
	 */
	public int getPlayerID() {
		return playerID;
	}
	
	@Override
	protected boolean execute() {
		ThisClientsPlayer thisClientsPlayer = ClientPlayerManager.getSingleton().getThisClientsPlayer();
		thisClientsPlayer.knowledgePointsChanged(knowledge, record);
		return true;
	}

}
