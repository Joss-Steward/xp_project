package model;

import Quest.QuestManager;
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

	private Position position;
	private QuestManager questManager;
	private int id;
	private String name;

	private boolean loginInProgress;

	protected ThisClientsPlayer()
	{
		this.position = new Position(0, 0);
		this.questManager = new QuestManager();
		reportTypes.add(LoginInitiatedReport.class);
		reportTypes.add(QuestScreenReport.class);
		
		this.registerReportTypesWeNotify();
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
	 *            Closing quest screen or opening
	 */
	public void showQuests(boolean b)
	{
		System.out.println("hello from the player. Showing quests is " + b);
		notifyObservers(new QuestScreenReport(b));
	}

	/**
	 * getter for quest manager
	 * 
	 * @return QuestManager this client's manager
	 */
	public QuestManager getQuestManager()
	{
		return questManager;
	}
}
