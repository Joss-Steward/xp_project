package model.reports;

import java.util.Date;
import model.QualifiedObservableReport;

/**
 *
 * 
 * @author Evan, Chris, Marty
 */
public final class TimeToLevelUpDeadlineReport implements QualifiedObservableReport
{
    private int playerID;
    private Date timeToDeadline;
    private String nextLevel;
    private Date passedInDate;
    /**
     * @param playerID id of the player
     * @param timeToDeadline time the user has to level up before being penalized
     * @param nextLevel next level the player needs to get to
     */
    public TimeToLevelUpDeadlineReport(int playerID, Date timeToDeadline, String nextLevel)
    {
        this.playerID = playerID;
        this.timeToDeadline = timeToDeadline;
        this.nextLevel = nextLevel;
       
        
    }
    
    
    /**
     * @return The player id
     */
    public int getPlayerID() 
    {
        return playerID;
    }
    
    /**
     * @return The time the user has to level up before being penalized
     */
    public Date getTimeToDeadline() 
    {
        return timeToDeadline;
    }

    /**
     * @return The next level the player needs to get to
     */
    public String getNextLevel() 
    {
        return nextLevel;
    }

    /**
     * Override the default hashCode() so that instances of this class can be
     * properly compared with one another.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
//        result = prime * result
//                + ((location == null) ? 0 : location.hashCode());
//        result = prime * result + ((name == null) ? 0 : name.hashCode());
//        result = prime * result + ((text == null) ? 0 : text.hashCode());
//        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }
    
    /**
     * Override the default equals() so that instances of this class can be
     * properly compared.
     */
    @Override
    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        SendChatMessageReport other = (SendChatMessageReport) obj;
//        if (location == null) {
//            if (other.location != null)
//                return false;
//        } else if (!location.equals(other.location))
//            return false;
//        if (name == null) {
//            if (other.name != null)
//                return false;
//        } else if (!name.equals(other.name))
//            return false;
//        if (text == null) {
//            if (other.text != null)
//                return false;
//        } else if (!text.equals(other.text))
//            return false;
//        if (type != other.type)
//            return false;
        return true;
    }
}

