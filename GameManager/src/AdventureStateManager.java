import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JFrame;

import model.PlayerID;
import datasource.AdventureStateViewTableDataGatewayRDS;
import datasource.DatabaseException;
import datasource.PlayerLoginTableDataGatewayRDS;
import edu.ship.shipsim.areaserver.datasource.AdventureRecord;

/**
 * A stand alone app that manages data in the database
 * 
 * @author Merlin
 *
 */
public class AdventureStateManager
{

	private JFrame window;
	private JFrame window2;
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

	
	
	private AdventureJList buildAdventureList() throws DatabaseException 
	{
		List<AdventureRecord> adventures = AdventureStateViewTableDataGatewayRDS.getPendingAdventureRecords(lastSelectedPlayerID.getPlayerID());
		AdventureJList adventureList = new AdventureJList(adventures);
		return adventureList;
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
						if (lastSelectedPlayerID.getPlayerID() > 0)
						{
							System.out.println("Selected "  + lastSelectedPlayerID.getPlayerID() + " " + lastSelectedPlayerID.getPlayerName() );
							
							
							window2 = new JFrame("Adventure List");
							window2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							AdventureJList adventureList = null;
							try 
							{
								//System.out.println("Entered TRY");
								adventureList = buildAdventureList();
							} catch (DatabaseException e) 
							{
								//System.out.println("Entered CATCH");
								e.printStackTrace();
							}
							
							window2.add(adventureList);
							window2.pack();
							window2.setVisible(true);
							window2.setSize(500, 500);
							window2.setLocation(150, 0);
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
