import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.AdventureRecord;

/**
 * Creates a window containing a list of Pending Adventures
 * 
 * @author Olivia and LaVonne
 *
 */
public class AdventureJList extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList<AdventureRecord> list;
	/**
	 * Constructs a new object based upon the parameter adventures
	 * 
	 * @param advs
	 *            the list of pending adventures for one PlayerID
	 */
	public AdventureJList(final List<AdventureRecord> advs)
	{
		this.list = new JList<AdventureRecord>();
		list.setLayout(new BorderLayout());
		list.setFixedCellWidth(50);
		list = new JList<AdventureRecord>();

		setListData(advs);
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

				if (!adjust)
				{
					@SuppressWarnings("unchecked")
					JList<String> l = (JList<String>) event.getSource();
					int selections[] = l.getSelectedIndices();
					Object selectionValues[] = l.getSelectedValues();

					System.out.println("Selections: ");
					for (int i = 0, n = selections.length; i < n; i++)
					{
						System.out.println(selectionValues[i] + " ");
					}
				}
			}

		});
		add(pane, BorderLayout.NORTH);
	}

	/**
	 * @return the items that are selected in the list
	 */
	List<AdventureRecord> getSelectedItems()
	{
		return list.getSelectedValuesList();
	}

	/**
	 * @param advs the adventure records we should have in the list
	 */
	void setListData(List<AdventureRecord> advs)
	{
		AdventureRecord[] x = new AdventureRecord[advs.size()];
		for (int i = 0; i < x.length; i++)
		{
			x[i] = advs.get(i);
		}
		list.setListData(x);
	}

}
