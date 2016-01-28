package model;

/**
 * Defines what is required to model one message in a message protocol sequence
 * @author Merlin
 *
 */
public abstract class SequenceTest
{

	/**
	 * @return the command that will initiate the sequence
	 */
	public abstract Command getInitiatingCommand();

	/**
	 * @return the type of server where the initiating command is run
	 */
	public abstract ServerType getInitiatingServerType();

	/**
	 * @return the sequence of message flows that define the protocol
	 */
	public abstract MessageFlow[] getMessageSequence();

	/**
	 * @return the player ID of the player that is initiating this sequence
	 */
	public abstract int getInitiatingPlayerID();
	
	/**
	 * Set up anything in the singletons (like OptionsManager) that is required by this test
	 */
	public abstract void setUpServer();
}
