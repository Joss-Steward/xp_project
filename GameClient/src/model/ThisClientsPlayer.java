package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import data.AdventureStateEnum;
import data.Position;
import data.QuestStateEnum;
import datasource.LevelRecord;
import model.reports.AdventureNeedingNotificationReport;
import model.reports.AdventureStateChangeReport;
import model.reports.KnowledgePointsChangeReport;
import model.reports.QuestNeedingNotificationReport;
import model.reports.QuestStateChangeReport;
import model.reports.QuestStateReport;
import model.reports.TimeToLevelUpDeadlineReport;

/**
 * The player who is playing the game
 * 
 * @author merlin
 * 
 */
public class ThisClientsPlayer extends ClientPlayer implements QualifiedObserver
{
	ArrayList<ClientPlayerQuest> questList = new ArrayList<ClientPlayerQuest>();

	private int experiencePoints;
	private LevelRecord record;
	private int knowledgePoints;
	private Timer levelUpTimer = null;
	private String timeLeft;
	private Date timeToLevelUp;

	protected ThisClientsPlayer(int playerID)
	{
		super(playerID);
		
		QualifiedObservableConnector.getSingleton().registerObserver(this,
                TimeToLevelUpDeadlineReport.class);
	}
	
	
	/**
     * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
     */
    @Override
    public void receiveReport(QualifiedObservableReport report)
    {
        if (report.getClass() == TimeToLevelUpDeadlineReport.class)
        {
            updateDeadlineTimeToLevelUp(report);
        }
    }
    
    /**
     * 
     * @return the time deadline to level up
     */
    public String getDeadlineToLevelUp()
    {
        return timeToLevelUp.toString();
    }
    
    /**
     * Updates the time to level up deadline for each player
     * @param report time to level up deadline report
     */
    private void updateDeadlineTimeToLevelUp(QualifiedObservableReport report)
    {
        timeToLevelUp = ((TimeToLevelUpDeadlineReport) report).getTimeToDeadline();
        
        updateDeadlineTimeToLevelUp();
        
        if(levelUpTimer == null){
            levelUpTimer = new Timer();
            levelUpTimer.scheduleAtFixedRate(new UpdateLevelUpDeadline(), 0, 60*1000);
        }
    }
    
    /**
     * Updates the time to level up. If there is more than 24 hours remaining to level up, 
     * the number of days remaining is written to the GUI. If there is less than 24 hours, the number
     * of hours remaining is written to the GUI.
     */
    protected synchronized void updateDeadlineTimeToLevelUp()
    {
        Date now = new Date();
        
        long dateDifference = timeToLevelUp.getTime() - now.getTime();
        
        int milliSecondsPerHour = (1000 * 60 * 60);
        long hoursBetween = dateDifference / milliSecondsPerHour;
        
        if(hoursBetween > 24)
        {
            long daysBetween = hoursBetween / 24;
            timeLeft = daysBetween + " day(s)";
        }
        else
        {
            timeLeft = hoursBetween + " hour(s)";
        }
    }
    

	/**
	 * Moves this player and report if they have entered into any regions
	 * 
	 * @param pos
	 *            the location to move to
	 */
	public void move(Position pos)
	{
		super.move(pos);

		// Commented out because we are not using AreaCollision right now

		// check if entering a region
		// String region = MapManager.getSingleton().getIsInRegion(pos);
		// if (region != null)
		// {
		// QualifiedObservableConnector.getSingleton().sendReport(new
		// AreaCollisionReport(this.id, region));
		// }
	}

	/**
	 * Returns the list of quests contained by the local player.
	 * 
	 * @return the quest list
	 */
	public ArrayList<ClientPlayerQuest> getQuests()
	{
		return questList;
	}

	/**
	 * Adds a quest to the local players quest list
	 * 
	 * @param q
	 *            the quest being added
	 */
	public void addQuest(ClientPlayerQuest q)
	{
		questList.add(q);
	}

	/**
	 * Overwrite ThisClientPlayer's quest list
	 * 
	 * @param qList
	 *            current quest list
	 */
	public void overwriteQuestList(ArrayList<ClientPlayerQuest> qList)
	{
		questList.clear();
		questList = qList;

		for (ClientPlayerQuest q : questList)
		{
			if (q.isNeedingNotification())
			{
				QuestNeedingNotificationReport report = new QuestNeedingNotificationReport(
						ClientPlayerManager.getSingleton().getThisClientsPlayer().getID(),
						q.getQuestID(), q.getQuestDescription(), q.getQuestState());
				QualifiedObservableConnector.getSingleton().sendReport(report);
			}
			for (ClientPlayerAdventure a : q.getAdventureList())
			{
				if (a.isNeedingNotification())
				{
					AdventureNeedingNotificationReport report = new AdventureNeedingNotificationReport(
							ClientPlayerManager.getSingleton().getThisClientsPlayer()
									.getID(), q.getQuestID(), a.getAdventureID(),
							a.getAdventureDescription(), a.getAdventureState(),
							a.isNeedingNotification(), a.getWitnessTitle());
					QualifiedObservableConnector.getSingleton().sendReport(report);
				}
			}
		}
	}

	/**
	 * Sends questList to QuestStateReport and notifies the Observers
	 */
	public void sendCurrentQuestStateReport()
	{
		QuestStateReport r = new QuestStateReport(questList);
		QualifiedObservableConnector.getSingleton().sendReport(r);
	}

	/**
	 * @return the number of experience points for ThisClientsPlayer
	 */
	public int getExperiencePoints()
	{
		return experiencePoints;
	}

	/**
	 * @param record
	 *            level record
	 * @param expPoints
	 *            experience points
	 */
	public void setLevelInfo(LevelRecord record, int expPoints)
	{
		this.record = record;
		this.experiencePoints = expPoints;

		sendExperiencePointsChangeReport();
	}

	/**
	 * Sends the report to say that experience points have changed.
	 */
	public void sendExperiencePointsChangeReport()
	{
		KnowledgePointsChangeReport r = new KnowledgePointsChangeReport(experiencePoints);
		QualifiedObservableConnector.getSingleton().sendReport(r);
	}

	/**
	 * Sends the report to say that the quest state has changed.
	 * 
	 * @param questID
	 *            for ThisClientsPlayer's quest
	 * @param questDescription
	 *            for ThisClientsPlayer's quest description
	 * @param newState
	 *            enum for the current quest
	 */
	public void sendQuestStateChangeReport(int questID, String questDescription,
			QuestStateEnum newState)
	{
		for (ClientPlayerQuest q : questList)
		{
			if (q.getQuestID() == questID)
			{
				q.setState(newState);

				QuestStateChangeReport r = new QuestStateChangeReport(this.getID(),
						q.getQuestID(), questDescription, newState);
				QualifiedObservableConnector.getSingleton().sendReport(r);
			}
		}
	}

	/**
	 * @param questID
	 *            for ThisClientsPlayer's quest that the adventure is in
	 * @param adventureID
	 *            for the adventure who are looking for
	 * @param adventureDescription
	 *            for the target adventure
	 * @param adventureState
	 *            enum for the target adventure
	 */
	public void sendAdventureStateChangeReport(int questID, int adventureID,
			String adventureDescription, AdventureStateEnum adventureState)
	{
		for (ClientPlayerQuest q : questList)
		{
			if (q.getQuestID() == questID)
			{
				for (ClientPlayerAdventure a : q.getAdventureList())
				{
					if (a.getAdventureID() == adventureID)
					{
						a.setAdventureState(adventureState);
						AdventureStateChangeReport r = new AdventureStateChangeReport(
								this.getID(), questID, adventureID, adventureDescription,
								adventureState);
						QualifiedObservableConnector.getSingleton().sendReport(r);
					}
				}

			}
		}
	}

	/**
	 * returns the Level Record
	 * 
	 * @return record the Level Record
	 */
	public LevelRecord getLevelRecord()
	{
		return record;
	}

	/**
	 * Overwrite the experience and level record in the clients player
	 * 
	 * @param knowledge
	 *            current knowledge
	 */
	public void knowledgePointsChanged(int knowledge)
	{
		setLevelInfoKnowledge(knowledge);
	}

	/**
	 * Overwrite the experience and level record in the clients player
	 * 
	 * @param experience
	 *            current experience
	 * @param rec
	 *            level report
	 */
	public void overwriteExperiencePoints(int experience, LevelRecord rec)
	{
		setLevelInfo(rec, experience);
	}

	/**
	 * @param knowledge
	 *            points of this player
	 */
	public void setLevelInfoKnowledge(int knowledge)
	{
		this.knowledgePoints = knowledge;

		sendKnowledgePointsChangeReport();

	}

	/**
	 * sending the knowledge changed report
	 */
	public void sendKnowledgePointsChangeReport()
	{

		KnowledgePointsChangeReport r = new KnowledgePointsChangeReport(
				this.knowledgePoints);
		QualifiedObservableConnector.getSingleton().sendReport(r);

	}

	/**
	 * 
	 * @return the knowledgePoints for this player
	 */
	public int getKnowledgePoints()
	{
		return this.knowledgePoints;
	}


    /**
     * @return the string for how much time is left until the level up deadline
     */
    public String getTimeLeft()
    {
        return timeLeft;
    }
}

/**
 * This class is a simple timer for updating the 
 * @author ch5968
 *
 */
class UpdateLevelUpDeadline extends TimerTask{

    @Override
    public void run()
    {
        ClientPlayerManager.getSingleton().getThisClientsPlayer().updateDeadlineTimeToLevelUp();
    }
}
