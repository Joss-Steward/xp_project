package model;

import model.reports.ThisPlayerMovedReport;
import data.Position;

/**
 * The player who is playing the game
 * 
 * @author merlin
 * 
 */
public class Player extends QualifiedObservable
{

	private static Player singleton;

	/**
	 * There should be only one
	 * 
	 * @return the only player
	 */
	public static synchronized Player getSingleton()
	{
		if (singleton == null)
		{
			singleton = new Player();
		}
		return singleton;
	}

	/**
	 * Used only in testing to re-initialize the singleton
	 */
	public static void resetSingleton()
	{
		singleton = null;
	}
	private Position position;

	private int id;
	private String name;

	private Player()
	{
		this.position = new Position(0, 0);
	}

	/**
	 * 
	 * @return the ID of this player
	 */
	public int getID()
	{
		return id;
	}

	/**
	 * 
	 * @return this Player's name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the position
	 */
	public Position getPosition()
	{
		return position;
	}

	/**
	 * Move this player to a given position
	 * 
	 * @param p
	 *            where the player should move to
	 */
	public void move(Position p)
	{
		position = p;
		this.notifyObservers(new ThisPlayerMovedReport(p));
	}

	/**
	 * @see model.QualifiedObservable#notifiesOn(java.lang.Class)
	 */
	@Override
	public boolean notifiesOn(Class<?> reportType)
	{
		if (reportType.equals(ThisPlayerMovedReport.class))
		{
			return true;
		}
		return false;
	}

	/**
	 * Set the ID of this player
	 * 
	 * @param id
	 *            what our ID should be
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Set the name of this player
	 * 
	 * @param name
	 *            what this player should be called
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
