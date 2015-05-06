import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.OptionsManager;
import model.PlayerID;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
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
	private JComboBox<PlayerID> comboBox;

	/**
	 * 
	 * @throws DatabaseException
	 *             if we can't talk to the RDS data source
	 */
	public AdventureStateManager() throws DatabaseException
	{
		OptionsManager.getSingleton(false);
		window = new JFrame("Adventure Manager");
		window.setLayout(new BorderLayout());

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try
		{
			SwingUtilities.invokeAndWait(setUpStudentComboBox);
		} catch (InvocationTargetException | InterruptedException e)
		{
			e.printStackTrace();
		}

		JPanel studentPanel = new JPanel();
		studentPanel.add(comboBox);
		window.add(studentPanel, BorderLayout.WEST);
		adventureCompletionPanel = buildAdventureCompletionPanel();
		window.add(adventureCompletionPanel, BorderLayout.EAST);

		window.pack();
		window.setVisible(true);
	}

	private final Runnable setUpStudentComboBox = new Runnable()
	{
		public void run()
		{

			comboBox = new JComboBox<PlayerID>();
			comboBox.addItemListener(new ItemListener()
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

			try
			{
				List<PlayerID> playerIDs = PlayerLoginTableDataGatewayRDS
						.getPlayerIDList();
				final EventList<PlayerID> playerIDEvents = GlazedLists
						.eventList(playerIDs);

				AutoCompleteSupport.install(comboBox, playerIDEvents,
						new PlayerIDTextFilterator());
				// support.setStrict(true);
			} catch (DatabaseException e)
			{
				e.printStackTrace();
			}

			System.out.println("Is editable - " + comboBox.isEditable() + ". Surprise!");
		}
	};

	private static final class PlayerIDTextFilterator implements TextFilterator<PlayerID>
	{
		public void getFilterStrings(List<String> baseList, PlayerID element)
		{
			final String name = element.getPlayerName();
			baseList.add(name);
		}
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
		AdventureJList adventureJList = new AdventureJList(
				new ArrayList<AdventureRecord>());
		return adventureJList;
	}

	private void reloadAdventureList()
	{
		try
		{
			if (lastSelectedPlayerID != null && adventureList != null)
			{
				adventureList.setListData(AdventureStateViewTableDataGatewayRDS
						.getPendingAdventureRecords(lastSelectedPlayerID.getPlayerID()));
			}
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
