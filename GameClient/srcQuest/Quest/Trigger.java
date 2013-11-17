package Quest;
import data.Position;

/**
 * Class that is activated as a player walks over
 * 
 * @author joshua
 * 
 */
public class Trigger
{
	private boolean active;
	private String name;
	private String type;
	private String parentName;
	private Position position;

	/**
	 * Constructor is created as inactive
	 */
	public Trigger()
	{
		this.active = false;
	}

	/**
	 * activates the trigger
	 * 
	 * @param b
	 *            boolean
	 */
	public void activateTrigger(boolean b)
	{
		this.active = b;

	}

	/**
	 * Gets whether
	 * 
	 * @return boolean active
	 */
	public boolean isActive()
	{
		return this.active;
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
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
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

	/**
	 * @return the type
	 */
	public String getTriggerType()
	{
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setTriggerType(String type)
	{
		this.type = type;
	}
}
