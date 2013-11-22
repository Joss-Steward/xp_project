package Quest;

import com.badlogic.gdx.maps.MapProperties;

import data.Position;

/**
 * Converter that takes mapObjects and parses them into correct types
 * 
 * @author Joshua
 * 
 */
public class QuestPropertiesObjectReader
{

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
		if (properties != null)
		{
			try
			{
				// grab name from property
				String name = (String) properties.get("name");
				tempTrigger.setName(name);

				// grab position from property
				Position position = getPositionFromProperty(properties);
				tempTrigger.setPosition(position);

				String parentName = (String) properties.get("parent name");
				tempTrigger.setParentName(parentName);

				String myStringType = (String) properties.get("enum");
				TriggerTypes myType = TriggerTypes.valueOf(myStringType);

				tempTrigger.setTriggerType(myType);
				int x = (Integer.parseInt((String) properties.get("positionX")));
				int y = (Integer.parseInt((String) properties.get("positionY")));

				tempTrigger.setPosition(new Position(x, y));
			} catch (NullPointerException e)
			{
				System.out
						.println("Trigger did not load from the map correctly");
				e.printStackTrace();
			}
		}
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
