import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
	private PlayerID lastSelectedPlayerID;
	private JPanel adventureCompletionPanel;
	private AdventureJList adventureList;

	/**
	 * 
	 * @throws DatabaseException
	 *             if we can't talk to the RDS data source
	 */
	public AdventureStateManager() throws DatabaseException
	{
		window = new JFrame("Adventure Manager");
		window.setLayout(new FlowLayout());

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		AutoCompletePlayerJComboBox studentCombo = buildNameComboBox();
		window.add(studentCombo);
		adventureCompletionPanel = buildAdventureCompletionPanel();
		window.add(adventureCompletionPanel);

		window.pack();
		window.setVisible(true);
	}

	private JPanel buildAdventureCompletionPanel() throws DatabaseException
	{
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		adventureList = buildAdventureList();
		p.add(adventureList);
		JButton completeAdventuresButton = new JButton("Complete");
		completeAdventuresButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				for (AdventureRecord rec : adventureList.getSelectedItems())
				{
					try
					{
						AdventureStateViewTableDataGatewayRDS.moveToNeedNotification(
								lastSelectedPlayerID.getPlayerID(), rec.getQuestID(),
								rec.getAdventureID());
					} catch (DatabaseException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				reloadAdventureList();

			}

		});
		p.add(completeAdventuresButton);
		return p;
	}

	private AdventureJList buildAdventureList() throws DatabaseException
	{
		if (lastSelectedPlayerID != null)
		{
			List<AdventureRecord> adventures = AdventureStateViewTableDataGatewayRDS
					.getPendingAdventureRecords(lastSelectedPlayerID.getPlayerID());
			AdventureJList adventureList = new AdventureJList(adventures);
			return adventureList;
		}
		return new AdventureJList(new ArrayList<AdventureRecord>());
	}

	private AutoCompletePlayerJComboBox buildNameComboBox() throws DatabaseException
	{
		List<PlayerID> names = PlayerLoginTableDataGatewayRDS.getPlayerIDList();
		SearchablePlayerIDList playerNames = new SearchablePlayerIDList(names);
		AutoCompletePlayerJComboBox studentCombo = new AutoCompletePlayerJComboBox(
				playerNames);

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
							System.out.println("Selected "
									+ lastSelectedPlayerID.getPlayerID() + " "
									+ lastSelectedPlayerID.getPlayerName());

							reloadAdventureList();

						}
					}
				}
			}

		});

		return studentCombo;
	}

	private void reloadAdventureList()
	{
		try
		{
			adventureList.setListData(AdventureStateViewTableDataGatewayRDS
					.getPendingAdventureRecords(lastSelectedPlayerID.getPlayerID()));
		} catch (DatabaseException e)
		{
			e.printStackTrace();
		}
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
