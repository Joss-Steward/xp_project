package communication;

/**
 * Interface for connecting StateAccumulators to the objects they need to observer.  This is the interface in the 
 * strategy pattern because the things we need to observe depend on whether we are in the client or the server
 * @author merlin
 *
 */
public interface StateAccumulatorConnector
{

	/**
	 * This method should attach the given accumulator to all of the points in the system that need to be observed for
	 * state changes that have to be reported to the other side
	 * @param accumulator the accumulater we are initializing
	 */
	void setUpObserverLinks(StateAccumulator accumulator);
	
	/**
	 * Get the set of MessagePackers for handling the events from the observables we are connected to
	 * @return the set
	 */
	MessagePackerSet getMessagePackerSet();

}
