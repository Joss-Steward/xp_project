package model;

/**
 * The kinds of servers in the system
 * 
 * @author Merlin
 *
 */
public enum ServerType
{
	/**
	 * the client that a player making the request is using
	 */
	THIS_PLAYER_CLIENT, /**
	 * the client of another player who should
	 * participate even though they did not initiate the sequence
	 */
	OTHER_CLIENT, /**
	 * the server that manages logins
	 */
	LOGIN_SERVER, /**
	 * An area server
	 */
	AREA_SERVER, /**
	 * the standalone management app
	 */
	MANAGER
}