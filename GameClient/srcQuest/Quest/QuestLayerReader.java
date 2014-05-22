package Quest;

import java.util.ArrayList;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;

import edu.ship.shipsim.client.model.MapManager;

/**
 * 
 * @author Joshua
 * 
 */
public class QuestLayerReader
{
	// reading in quests from map
	/**
	 * The name for the quest layer in every map
	 */
	public static final String QUEST_LAYER_NAME = "Quest";
	private MapLayer questLayer;
	private MapObject mapObject;
	private MapObjects allObjects;

	private ArrayList<Quest> quests;
	private ArrayList<Task> tasks;
	private ArrayList<Trigger> triggers;

	/**
	 * gets the QuestLayer from the MapManager's singletone
	 * 
	 * @return boolean success of pulling the questlayer
	 */
	public boolean pullQuestLayerFromMapManager()
	{

		setQuestLayer(MapManager.getSingleton().getMapLayer(QUEST_LAYER_NAME));
		// if a quest layer could not be found, old info is cleared out to stay
		// current
		if (getQuestLayer() != null)
		{
			mapObject = null;
			allObjects = null;
			setQuests(null);
			setTasks(null);
			setTriggers(null);
			return true;
		}

		return false;
	}

	/**
	 * pulls the object and properties from the QuestLayer
	 */
	public void parsePropertiesFromLayer()
	{
		// Initializes new quests
		setQuests(new ArrayList<Quest>());
		setTasks(new ArrayList<Task>());
		setTriggers(new ArrayList<Trigger>());

		// pull objects from layer
		if (getQuestLayer() != null)
		{
			allObjects = getQuestLayer().getObjects();
		}

		if (allObjects != null)
		{
			// walk through all objects
			for (int i = 0; i < allObjects.getCount(); i++)
			{
				mapObject = allObjects.get(i);
				MapProperties property = mapObject.getProperties();
				String type = (String) property.get("type");
				// uses object reader to parse information from specific
				// MapObject
				if (type != "trigger")
				{
					triggers.add(QuestPropertiesObjectReader.getTrigger(property));
					break;
				}
			}
		}
	}

	/**
	 * connects the classes to their "parent" classes
	 */
	public void buildRelationships()
	{
		// triggers to tasks
		if (getTriggers() != null && getTasks() != null)
		{
			for (int i = 0; i < getTriggers().size(); i++)
			{
				for (int j = 0; j < getTasks().size(); j++)
				{
					// matches trigger to task
					if (getTriggers().get(i).getParentName()
							.equals(getTasks().get(j).getName()))
					{
						getTasks().get(j).setTrigger(getTriggers().get(i));
					}
				}

			}
		}

		if (getTasks() != null && getQuests() != null)
		{
			// tasks mapped to quests
			for (int i = 0; i < getTasks().size(); i++)
			{
				for (int j = 0; j < getQuests().size(); j++)
				{
					if (getTasks().get(i).getParentName()
							.equals(getQuests().get(j).getName()))
					{
						getQuests().get(j).addTask(getTasks().get(i));
					}
				}

			}
		}
	}

	/**
	 * Getter for the quests from the parsed file
	 * 
	 * @return ArrayList<Quest>
	 */
	public ArrayList<Quest> getQuestList()
	{
		return getQuests();
	}

	/**
	 * @return the questLayer
	 */
	public MapLayer getQuestLayer()
	{
		return questLayer;
	}

	/**
	 * @param questLayer
	 *            the questLayer to set
	 */
	public void setQuestLayer(MapLayer questLayer)
	{
		this.questLayer = questLayer;
	}

	/**
	 * @return the quests
	 */
	public ArrayList<Quest> getQuests()
	{
		return quests;
	}

	/**
	 * @param quests
	 *            the quests to set
	 */
	public void setQuests(ArrayList<Quest> quests)
	{
		this.quests = quests;
	}

	/**
	 * @return the triggers
	 */
	public ArrayList<Trigger> getTriggers()
	{
		return triggers;
	}

	/**
	 * @param triggers
	 *            the triggers to set
	 */
	public void setTriggers(ArrayList<Trigger> triggers)
	{
		this.triggers = triggers;
	}

	/**
	 * @return the tasks
	 */
	public ArrayList<Task> getTasks()
	{
		return tasks;
	}

	/**
	 * @param tasks
	 *            the tasks to set
	 */
	public void setTasks(ArrayList<Task> tasks)
	{
		this.tasks = tasks;
	}
}
