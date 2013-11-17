package Quest;
import com.badlogic.gdx.maps.MapProperties;

import data.Position;

/**
 * Converter that takes mapObjects and parses them into correct types
 * 
 * @author Joshua
 * 
 */
public class QuestLayerObjectReader
{
	/**
	 * Creates a Quest from the properties
	 * 
	 * @param properties
	 *            MapProperties that hold Quest information
	 * @return Quest instantiated from the properties
	 */
	public static Quest getQuest(MapProperties properties)
	{
		// grab name from property
		String name = (String) properties.get("name");
		Quest tempQuest = new Quest(name);

		Position position = getPositionFromProperty(properties);
		// grabs description
		String description = (String) properties.get("description");

		// sets information
		tempQuest.setDescription(description);
		tempQuest.setPosition(position);
		return tempQuest;
	}

	/**
	 * Creates a Task from the properties
	 * 
	 * @param properties
	 *            MapProperties that hold Task information
	 * @return Task instantiated by the properties
	 */
	public static Task getTask(MapProperties properties)
	{
		// grab name from property
		String name = (String) properties.get("name");
		Task tempTask = new Task(name);

		// grab position from property
		Position position = getPositionFromProperty(properties);
		tempTask.setPosition(position);
		// grabs description
		String description = (String) properties.get("description");
		tempTask.setDescription(description);

		String parentName = (String) properties.get("parent name");
		tempTask.setParentName(parentName);

		return tempTask;
	}

	/**
	 * Creates a trigger with the properties
	 * 
	 * @param properties
	 *            MapProperties that hold trigger information
	 * @return Trigger instantiated by the properties
	 */
	public static Trigger getTrigger(MapProperties properties)
	{
		Trigger tempTrigger = new Trigger();

		// grab name from property
		String name = (String) properties.get("name");
		tempTrigger.setName(name);

		// grab position from property
		Position position = getPositionFromProperty(properties);
		tempTrigger.setPosition(position);

		String parentName = (String) properties.get("parent name");
		tempTrigger.setParentName(parentName);

		return tempTrigger;
	}

	/**
	 * grabs the position of an object from the property
	 * 
	 * @param properties
	 * @return
	 */
	private static Position getPositionFromProperty(MapProperties properties)
	{
		// grab x from property
		String row = (String) (properties.get("x"));
		int r = -1;
		if (row != null)
		{
			r = Integer.parseInt(row);
		}
		// grabs y from property
		String col = (String) (properties.get("y"));
		int c = -1;
		if (col != null)
		{
			c = Integer.parseInt(col);
		}
		// instantiates position as long as both coordinates are found
		Position position = null;
		if (r != -1 && c != -1)
		{
			position = new Position(r, c);
		}
		return position;
	}
}
