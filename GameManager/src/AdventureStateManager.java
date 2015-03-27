
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import datasource.DatabaseException;
import datasource.PlayerLoginRowDataGatewayRDS;


public class AdventureStateManager
{

	JFrame window;
	
	public AdventureStateManager() throws DatabaseException
	{
		window = new JFrame ("Adventure Manager");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel playerSelectionPanel = new JPanel();
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
	public static void main(String[] args) throws DatabaseException
	{
		new AdventureStateManager();

	}

}
