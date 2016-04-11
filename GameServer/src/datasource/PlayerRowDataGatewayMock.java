package datasource;

import java.util.HashMap;

import testData.PlayersForTest;
import data.Crew;
import data.Major;
import data.Position;
import datasource.DatabaseException;

/**
 * A mock implementation for PlayerRowDataGateway
 * 
 * @author Merlin
 *
 */
public class PlayerRowDataGatewayMock implements PlayerRowDataGateway
{

	private class PlayerInfo
	{
		private Position position;
		private String appearanceType;
		private int quizScore;
		private int experiencePoints;
		private Crew crew;
		private Major major;

		public PlayerInfo(Position position, String appearanceType,
				int quizScore, int experiencePoints, Crew crew, Major major)
		{
			this.position = position;
			this.appearanceType = appearanceType;
			this.quizScore = quizScore;
			this.experiencePoints = experiencePoints;
			this.crew = crew;
			this.major = major;
		}

		public Position getPosition()
		{
			return position;
		}

		public String getAppearanceType()
		{
			return appearanceType;
		}
	}

	/**
	 * Map player ID to player information
	 */
	private static HashMap<Integer, PlayerInfo> playerInfo;
	private static int nextKey = 1;
	private int playerID;
	private PlayerInfo info;

	/**
	 * Finder constructor - will initialize itself from the stored information
	 * 
	 * @param playerID
	 *            the ID of the player we are looking for
	 * @throws DatabaseException
	 *             if the playerID isn't in the data source
	 */
	public PlayerRowDataGatewayMock(int playerID) throws DatabaseException
	{
		if (playerInfo == null)
		{
			resetData();
		}

		if (playerInfo.containsKey(playerID))
		{
			info = playerInfo.get(playerID);
			this.playerID = playerID;
		} else
		{
			throw new DatabaseException("Couldn't find player with ID " + playerID);
		}
	}

	/**
	 * Create constructor - will add the information as a new row in the data source as
	 * the object is constructed
	 * @param position the position of the player on the map
	 * @param appearanceType the appearance type of the player
	 * @param quizScore this player's current quiz score
	 * @param experiencePoints this player's experience points
	 * @param crew the crew to which this player belongs
	 * @param major the major of this player
	 */
	public PlayerRowDataGatewayMock(Position position,
			String appearanceType, int quizScore, int experiencePoints, Crew crew, Major major) 
	{
		if (playerInfo == null)
		{
			resetData();
		}
		playerID = nextKey;
		nextKey++;
		
		playerInfo.put(playerID, new PlayerInfo(position, appearanceType,
				quizScore, experiencePoints, crew, major));
	}

	/**
	 * Just used by tests to reset static information
	 */
	public PlayerRowDataGatewayMock()
	{
	}

	/**
	 * @see datasource.PlayerRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		playerInfo = new HashMap<Integer, PlayerInfo>();
		nextKey = 1;
		for (PlayersForTest p : PlayersForTest.values())
		{
			playerInfo.put(
					nextKey,
					new PlayerInfo(p.getPosition(), p
							.getAppearanceType(), p.getKnowledgeScore(), p.getExperiencePoints(), p.getCrew(), p.getMajor()));
			nextKey++;
		}
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getPlayerID()
	 */
	@Override
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getPosition()
	 */
	@Override
	public Position getPosition()
	{
		return info.getPosition();
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getAppearanceType()
	 */
	@Override
	public String getAppearanceType()
	{
		return info.getAppearanceType();
	}

	/**
	 * @see datasource.PlayerRowDataGateway#persist()
	 */
	@Override
	public void persist()
	{
		playerInfo.put(playerID, info);
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setPosition(data.Position)
	 */
	@Override
	public void setPosition(Position position)
	{
		info.position = position;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setAppearanceType(java.lang.String)
	 */
	@Override
	public void setAppearanceType(String appearanceType)
	{
		info.appearanceType = appearanceType;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getQuizScore()
	 */
	@Override
	public int getQuizScore()
	{
		return info.quizScore;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setQuizScore(int)
	 */
	@Override
	public void setQuizScore(int quizScore)
	{
		info.quizScore = quizScore;
		
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getExperiencePoints()
	 */
	@Override
	public int getExperiencePoints()
	{
		return info.experiencePoints;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setExperiencePoints(int)
	 */
	@Override
	public void setExperiencePoints(int experiencePoints)
	{
		info.experiencePoints = experiencePoints;		
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getCrew()
	 */
	@Override
	public Crew getCrew()
	{
		return info.crew;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setCrew(data.Crew)
	 */
	@Override
	public void setCrew(Crew crew)
	{
		info.crew = crew;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#getMajor()
	 */
	@Override
	public Major getMajor() 
	{
		return info.major;
	}

	/**
	 * @see datasource.PlayerRowDataGateway#setMajor(data.Major)
	 */
	@Override
	public void setMajor(Major major) 
	{
		info.major = major;
	}

}
