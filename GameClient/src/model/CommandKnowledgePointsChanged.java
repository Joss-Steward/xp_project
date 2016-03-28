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
	private int playerID;

	/**
	 * constructor for the command
	 * @param msg InitializeClientsPlayerMessage which contains all the data to
	 * initialize this client player
	 */
	public CommandKnowledgePointsChanged(InitializeThisClientsPlayerMessage msg) 
	{
		this.knowledge = msg.getKnowledgePoints();
	}
	
	
	/**
	 * @param playerID of the player
	 * @param knowledgePoints of the player
	 */
	public CommandKnowledgePointsChanged(int playerID, int knowledgePoints) 
	{
		this.knowledge = knowledgePoints;
		this.playerID = playerID;
	}

	/**
	 * @return the knowledgePoints for this player
	 */
	public int getKnowledge() {
		return knowledge;
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
		thisClientsPlayer.knowledgePointsChanged(knowledge);
		return true;
	}

}
