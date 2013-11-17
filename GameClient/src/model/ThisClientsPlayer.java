package model;

import model.reports.LoginInitiatedReport;
import model.reports.QuestScreenReport;
import model.reports.ThisPlayerMovedReport;
import data.Position;

/**
 * The player who is playing the game
 * 
 * @author merlin
 * 
 */
public class ThisClientsPlayer extends QualifiedObservable
{

	private static ThisClientsPlayer singleton;

	/**
	 * There should be only one
	 * 
	 * @return the only player
	 */
	public static synchronized ThisClientsPlayer getSingleton()
	{
		if (singleton == null)
		{
			singleton = new ThisClientsPlayer();
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

	private boolean loginInProgress;

	private ThisClientsPlayer()
	{
		this.position = new Position(0, 0);
		QualifiedObservableConnector.getSingleton()
				.registerQualifiedObservable(this, LoginInitiatedReport.class);

		QualifiedObservableConnector.getSingleton()
				.registerQualifiedObservable(this, QuestScreenReport.class);
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
		if (reportType.equals(ThisPlayerMovedReport.class)
				|| reportType.equals(LoginInitiatedReport.class)
				|| reportType.equals(QuestScreenReport.class))
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

	/**
	 * @return true if we are in the process of trying to log into the server
	 */
	public boolean isLoginInProgress()
	{
		return loginInProgress;
	}

	/**
	 * Attempt to login with a given name and password
	 * 
	 * @param password
	 *            the password
	 * @param name
	 *            the user name
	 * 
	 */
	public void initiateLogin(String name, String password)
	{
		loginInProgress = true;
		this.name = name;
		notifyObservers(new LoginInitiatedReport(name, password));
	}

	/**
	 * notifies that the player has updated quest information
	 * 
	 * @param b
	 *            closeing quest screen or opening
	 */
	public void showQuests(boolean b)
	{
		System.out.println("hello from the player. Showing quests is " + b);
		notifyObservers(new QuestScreenReport(b));
	}
}
