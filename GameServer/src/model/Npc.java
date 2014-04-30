package model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * NPC extends Player to be compatible with the player manager and simplify many
 * messages that can go to the client.
 * 
 * @author Steve
 *
 */
@DatabaseTable(tableName = "Npc")
public class Npc extends Player
{
	@DatabaseField(dataType = DataType.SERIALIZABLE)
	private Behavior behavior;
	
	@DatabaseField(canBeNull = false)
	private String mapName;

	/**
	 * Set the map this npc is on
	 * @param name The map name
	 */
	public void setMap(String name) 
	{
		mapName = name;
	}
	
	
}
