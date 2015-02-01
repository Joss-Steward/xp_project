package edu.ship.shipsim.areaserver.model;

import datasource.DatabaseException;
import model.OptionsManager;
import model.PlayerLogin;
import edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway;
import edu.ship.shipsim.areaserver.datasource.PlayerRowDataGatewayMock;
import edu.ship.shipsim.areaserver.datasource.PlayerRowDataGatewayRDS;

/**
 * 
 * Manages retrieving and persisting all of the data associated with Players
 * (basically, connector between the Player class and the gateways in the data
 * source)
 * 
 * @author Merlin
 *
 */
public class PlayerMapper
{

	private PlayerRowDataGateway playerGateway;
	private Player player;

	/**
	 * Create one for this player
	 * 
	 * @param playerID the player's unique ID
	 * @throws DatabaseException if we can't find the given player
	 */
	public PlayerMapper(int playerID) throws DatabaseException
	{
		if (OptionsManager.getSingleton().isTestMode())
		{
			this.playerGateway = new PlayerRowDataGatewayMock(playerID);
		} else
		{
			this.playerGateway = new PlayerRowDataGatewayRDS(playerID);
		}
		this.player = new Player();
		player.setAppearanceType(playerGateway.getAppearanceType());
		player.setPlayerPositionWithoutNotifying(playerGateway.getPosition());
		player.setQuizScore(playerGateway.getQuizScore());
		player.setPlayerLogin(new PlayerLogin(playerID));
		player.setPlayerID(playerID);
		player.setMapName(playerGateway.getMapName());
		player.setDataMapper(this);
	}

	/**
	 * Get the player that this Mapper is responsible for
	 * @return the player this mapper manages
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * Persist the current state of the player into the data source
	 */
	public void persist()
	{
		playerGateway.setAppearanceType(player.getAppearanceType());
		playerGateway.setMapName(player.getMapName());
		playerGateway.setPosition(player.getPlayerPosition());
		playerGateway.setQuizScore(player.getQuizScore());
	}

}
