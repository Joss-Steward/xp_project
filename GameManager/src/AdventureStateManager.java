import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JFrame;

import model.PlayerID;
import datasource.DatabaseException;
import datasource.PlayerLoginTableDataGatewayRDS;

/**
 * A stand alone app that manages data in the database
 * 
 * @author Merlin
 *
 */
public class AdventureStateManager
{

	private JFrame window;
	private PlayerID lastSelectedPlayerID;

	/**
	 * 
	 * @throws DatabaseException
	 *             if we can't talk to the RDS data source
	 */
	public AdventureStateManager() throws DatabaseException
	{
		window = new JFrame("Adventure Manager");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		AutoCompletePlayerJComboBox studentCombo = buildNameComboBox();
		window.add(studentCombo);
		window.pack();
		window.setVisible(true);

	}

	private AutoCompletePlayerJComboBox buildNameComboBox() throws DatabaseException
	{
		List<PlayerID> names = PlayerLoginTableDataGatewayRDS.getPlayerIDList();

		SearchablePlayerIDList playerNames = new SearchablePlayerIDList(names);

		AutoCompletePlayerJComboBox studentCombo = new AutoCompletePlayerJComboBox(playerNames);
		studentCombo.addItemListener(new ItemListener()
		{
			

			public void itemStateChanged(ItemEvent arg0)
			{
				if (arg0.getStateChange() == ItemEvent.SELECTED)
				{
					if (!arg0.getItem().equals(lastSelectedPlayerID))
					{
						lastSelectedPlayerID = (PlayerID) arg0.getItem();
						if (lastSelectedPlayerID.getPlayerID()>0)
						{
							System.out.println("Selected "  + lastSelectedPlayerID.getPlayerID() + " " + lastSelectedPlayerID.getPlayerName() );
							
						}
					}
				}
			}
		});

		return studentCombo;
	}

	/**
	 * @param args
	 *            none are used
	 * @throws DatabaseException
	 *             if things go terribly awry!
	 */
	public static void main(String[] args) throws DatabaseException
	{
		new AdventureStateManager();

	}

}
