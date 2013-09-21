package communication;


/**
 * Interface for connecting StateAccumulators to the objects they need to observer.  This is the interface in the 
 * strategy pattern because the things we need to observe depend on whether we are in the client or the server
 * @author merlin
 *
 */
public abstract class StateAccumulatorConnector
{
	

	protected MessagePackerSet packerSet;

	protected abstract void getMessagePackersFor(StateAccumulator accumulator);
	
	/** 
	 * This method should detach the accumulator from everything it is observing
	 * @param accumulator the accumulator we are disconnecting
	 */
	protected void destroyObserverLinks(StateAccumulator accumulator)
	{
		//TODO need to figure out how to disconnect everything
	}
	
	/**
	 * Get the set of MessagePackers for handling the events from the observables we are connected to
	 * @return the set
	 */
	protected MessagePackerSet getMessagePackerSet(StateAccumulator accumulator)
	{
		return packerSet;
	}

}
