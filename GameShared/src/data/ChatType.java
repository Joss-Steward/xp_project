package data;
/**
 * Types of chat messages that can be sent
 */
public enum ChatType
{
	/**
	 * Chat is available to anyone on all servers
	 */
	World,
	/**
	 * Chat is available to anyone in this area server
	 */
	Zone,
	/**
	 * Chat is only visible to nearby entities
	 */
	Local;
}