package Quest;
import data.Position;

/**
 * @author joshua Used for a single purpose accomplished by activating a trigger
 */
public class Task
{

	private Trigger trigger;
	private String name;
	private String parentName;
	private String description;
	private Position position;
	private boolean active;
	private boolean completed;

	/**
	 * Constructor that passes in name
	 * 
	 * @param name String the name 
	 */
	public Task(String name)
	{
		this.name = name;
		this.active = false;
	}

	/**
	 * 
	 * @return String the name of the task
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * changes the name of the task
	 * 
	 * @param name
	 *            String the name
	 */
	public void changeName(String name)
	{
		this.name = name;
	}

	/**
	 * sets the trigger
	 * 
	 * @param trigger Trigger to give the task
	 */
	public void setTrigger(Trigger trigger)
	{
		this.trigger = trigger;
	}

	/**
	 * 
	 * @return Trigger the trigger associated with this task
	 */
	public Trigger getTrigger()
	{
		return this.trigger;
	}

	/**
	 * makes the task active or inactive
	 * 
	 * @param b
	 *            boolean
	 */
	public void activateTask(boolean b)
	{
		this.active = b;
		if (b == false)
		{
			if (this.trigger != null)
			{
				this.trigger.activateTrigger(false);
			}
		}

	}

	/**
	 * Gets whether the task is active or not
	 * 
	 * @return boolean active
	 */
	public boolean isActive()
	{
		return this.active;
	}

	/**
	 * sets the task as completed or not
	 * 
	 * @param completed boolean 
	 */
	public void setCompleted(boolean completed)
	{
		this.completed = completed;
	}

	/**
	 * @return boolean whether the task was completed
	 */
	public boolean isCompleted()
	{
		return this.completed;
	}

	/**
	 * getter for description
	 * 
	 * @return String 
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * setter for description
	 * 
	 * @param description String the description of the task
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the position
	 */
	public Position getPosition()
	{
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Position position)
	{
		this.position = position;
	}

	/**
	 * @return the parentName
	 */
	public String getParentName()
	{
		return parentName;
	}

	/**
	 * @param parentName
	 *            the parentName to set
	 */
	public void setParentName(String parentName)
	{
		this.parentName = parentName;
	}

}
