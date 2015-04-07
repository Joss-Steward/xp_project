import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.ship.shipsim.areaserver.datasource.AdventureRecord;

/**
 * Creates a window containing a list of Pending Adventures 
 * @author Olivia and LaVonne
 *
 */
public class AdventureJList extends JList<AdventureRecord>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JList<Object> list;

	/**
	 * Constructs a new object based upon the parameter adventures
	 * @param adventures
	 * 					the list of pending adventures for one PlayerID
	 */
	public AdventureJList(List<AdventureRecord> adventures) 
	{
		setLayout(new BorderLayout());
		list = new JList<Object>(adventures.toArray());
		JScrollPane pane = new JScrollPane(list);

		DefaultListSelectionModel model = new DefaultListSelectionModel();
		model.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		model.setLeadAnchorNotificationEnabled(false);
		list.setSelectionModel(model);

		list.addListSelectionListener(new ListSelectionListener()
		{
			@SuppressWarnings("deprecation")
			@Override
			public void valueChanged(ListSelectionEvent event) 
			{
				boolean adjust = event.getValueIsAdjusting();
				
				if(!adjust)
				{
					@SuppressWarnings("unchecked")
					JList<String> l = (JList<String>) event.getSource();
					int selections[] = l.getSelectedIndices();
					Object selectionValues[] = l.getSelectedValues();
					
					System.out.println("Selections: ");
					for(int i = 0, n = selections.length; i < n; i++)
					{
						System.out.println(selectionValues[i] + " ");
					}
				}
			}

		});
		add(pane, BorderLayout.NORTH);
	}

}
