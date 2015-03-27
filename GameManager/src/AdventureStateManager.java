import java.util.List;

import javax.swing.JFrame;

import datasource.DatabaseException;
import datasource.PlayerLoginRowDataGatewayRDS;

/**
 * A stand alone app that manages data in the database
 * 
 * @author Merlin
 *
 */
public class AdventureStateManager
{

	private JFrame window;

	/**
	 * 
	 * @throws DatabaseException
	 *             if we can't talk to the RDS data source
	 */
	public AdventureStateManager() throws DatabaseException
	{
		window = new JFrame("Adventure Manager");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		AutoCompleteJComboBox studentCombo = buildNameComboBox();
		window.add(studentCombo);
		window.pack();
		window.setVisible(true);

	}

	private AutoCompleteJComboBox buildNameComboBox() throws DatabaseException
	{
		List<String> names = PlayerLoginRowDataGatewayRDS.getAllPlayerNames();

		SearchableString playerNames = new SearchableString(names);

		AutoCompleteJComboBox studentCombo = new AutoCompleteJComboBox(playerNames);
		return studentCombo;
	}

	/**
	 * @param args none are used
	 * @throws DatabaseException if things go terribly awry!
	 */
	public static void main(String[] args) throws DatabaseException
	{
		new AdventureStateManager();

	}

}
