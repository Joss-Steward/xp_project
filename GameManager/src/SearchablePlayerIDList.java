import java.util.ArrayList;
import java.util.List;

import model.PlayerID;

/**
 * 
 * Implementation of the Searchable interface that searches a List of String
 * objects.
 * 
 * This implementation searches only the beginning of the words, and is not be
 * optimized for very large Lists.
 * 
 * @author G. Cope
 * 
 *
 */

public class SearchablePlayerIDList implements Searchable<PlayerID, String>
{

	private List<PlayerID> terms = new ArrayList<PlayerID>();

	/**
	 * 
	 * Constructs a new object based upon the parameter terms.
	 * 
	 * @param terms
	 *            The inventory of terms to search.
	 */
	public SearchablePlayerIDList(List<PlayerID> terms)
	{
		this.terms.addAll(terms);
	}

	
	/**
	 * @see Searchable#search(java.lang.Object)
	 */
	@Override
	public List<PlayerID> search(String value)
	{
		ArrayList<PlayerID> matching = new ArrayList<PlayerID>();
		for (PlayerID pid:terms)
		{
			if (pid.getPlayerName().toLowerCase().startsWith(value.toLowerCase()))
			{
				matching.add(pid);
			}
		}
		return matching;
	}

}