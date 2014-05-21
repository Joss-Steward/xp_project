package edu.ship.shipsim.areaserver.model;

import data.Position;
import edu.ship.shipsim.areaserver.model.NPCBehavior;


/**
 * The players that are in the test database
 * 
 * @author Merlin
 * 
 */
public enum NpcsInDB
{
	/**
	 * 
	 */
	QUIZ_BOT(11, "Quizard", "magi", 4, 19, "quiznasium.tmx", "QuizBotBehavior");
	/**
	 * 
	 */
	//OTHER_BOT(101, "Other Npc", "male_b", 4, 13);

	private int playerID;

	private String appearanceType;

	private int row;

	private int col;

	private String playerName;

	private String mapName;

	private NPCBehavior npcBehavior;

	NpcsInDB(int id, String playerName, String type, int row, int col, String mapName, String behaviorClassName)
	{
		this.playerID = id;
		this.playerName = playerName;
		this.appearanceType = type;
		this.row = row;
		this.col = col;
		this.mapName = mapName;
		Class<?> behaviorClass;
		try
		{
			behaviorClass = Class.forName("model."+behaviorClassName);
			this.npcBehavior = (NPCBehavior) behaviorClass.newInstance();
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		
	}

	/**
	 * Get the player's player type
	 * 
	 * @return a string that matches the name of one of the members of the enum
	 *         PlayerTypes
	 */
	public String getAppearanceType()
	{
		return appearanceType;
	}

	/**
	 * Get the name of the map file this player is associated with
	 * @return the file title
	 */
	public String getMapName()
	{
		return mapName;
	}

	/**
	 * @return the NPCs behavior
	 */
	public NPCBehavior getNpcBehavior()
	{
		return npcBehavior;
	}

	/**
	 * Get the player's unique ID
	 * 
	 * @return the id
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * Get the player's unique name
	 * 
	 * @return the name
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * get this player's most recent position
	 * 
	 * @return this player's position
	 */
	public Position getPosition()
	{
		return new Position(row, col);
	}

}