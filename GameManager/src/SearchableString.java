import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

public class SearchableString implements Searchable<String, String>
{

	private List<String> terms = new ArrayList<String>();

	/**
	 * 
	 * Constructs a new object based upon the parameter terms.
	 * 
	 * @param terms
	 *            The inventory of terms to search.
	 */
	public SearchableString(List<String> terms)
	{
		this.terms.addAll(terms);
	}

	/**
	 * @see Searchable#search(java.lang.Object)
	 */
	@Override
	public Collection<String> search(String value)
	{
		List<String> founds = new ArrayList<String>();
		for (String s : terms)
		{
			if (s.toLowerCase().indexOf(value.toLowerCase()) == 0)
			{
				founds.add(s);
			}
		}
		return founds;
	}

}