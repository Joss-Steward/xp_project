package model.reports;

import model.QualifiedObservableReport;

/**
 * Report that the client needs to be teleported somewhere
 * @author Chris Hersh, Zach Thompson
 *
 */
public class TeleportOnQuestCompletionReport implements QualifiedObservableReport
{
    
    private final int playerID;
    private final int questID;
    
    private final String mapName;
    private final String hostName;
    private final int portNumber;
    
    /**
     * 
     * @param id id of the player
     * @param questID id of the quest
     * @param mapName name of the map
     * @param hostName name of the host
     * @param port port the client should connect to
     */
    public TeleportOnQuestCompletionReport(int id, int questID, String mapName, String hostName, int port) 
    {
        this.playerID = id;
        this.questID = questID;
        this.mapName = mapName;
        this.hostName = hostName;
        this.portNumber = port;
    }

    /**
     * @return player ID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return quest ID
     */
    public int getQuestID()
    {
        return questID;
    }

    /**
     * @return map name
     */
    public String getMapName()
    {
        return mapName;
    }

    /**
     * @return host name
     */
    public String getHostName()
    {
        return hostName;
    }

    /**
     * @return port number
     */
    public int getPortNumber()
    {
        return portNumber;
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() 
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((mapName == null) ? 0 : mapName.hashCode());
        result = prime * result
                + ((hostName == null) ? 0 : hostName.hashCode());
        result = prime * result + portNumber;
        result = prime * result + playerID;
        result = prime * result + questID;
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TeleportOnQuestCompletionReport other = (TeleportOnQuestCompletionReport) obj;
        if (playerID != other.getPlayerID())
            return false;
        if (questID != other.getQuestID())
            return false;
        if (portNumber != other.getPortNumber())
            return false;
        if (!mapName.equals(other.getMapName()))
            return false;
        if (!hostName.equals(other.getHostName()))
            return false;
        return true;
    }

    
}
