import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import model.PlayerID;

/**
 * 
 * JComboBox with an autocomplete drop down menu. This class is hard-coded for
 * String objects, but can be altered into a generic form to allow for any
 * searchable item. Adapted from
 * http://www.algosome.com/articles/java-jcombobox-autocomplete.html
 * 
 * @author G. Cope
 * 
 *
 */
public class AutoCompletePlayerJComboBox extends JComboBox<PlayerID>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Searchable<PlayerID, String> searchable;

	/**
	 * 
	 * Constructs a new object based upon the parameter searchable
	 * 
	 * @param playerNames
	 *            the searchable list the box should display
	 */
	public AutoCompletePlayerJComboBox(SearchablePlayerIDList playerNames)
	{
		super();

		this.searchable = playerNames;
		setEditable(true);
		Component c = getEditor().getEditorComponent();
		if (c instanceof JTextComponent)
		{
			final JTextComponent tc = (JTextComponent) c;

			tc.getDocument().addDocumentListener(new DocumentListener()
			{

				@Override
				public void changedUpdate(DocumentEvent arg0)
				{
				}

				@Override
				public void insertUpdate(DocumentEvent arg0)
				{
					update();
				}

				@Override
				public void removeUpdate(DocumentEvent arg0)
				{
					update();
				}

				public void update()
				{

					// perform separately, as listener conflicts between the
					// editing component

					// and JComboBox will result in an IllegalStateException due
					// to editing

					// the component when it is locked.

					SwingUtilities.invokeLater(new Runnable()
					{

						@Override
						public void run()
						{
							String text = tc.getText();
							System.out.println("Search for " + text);
							List<PlayerID> founds = searchable.search(text);
							Set<String> foundSet = new HashSet<String>();
							for (PlayerID s : founds)
							{

								foundSet.add(s.getPlayerName().toLowerCase());

							}

							Collections.sort(founds);// sort alphabetically
							setEditable(false);
							removeAllItems();

							// if founds contains the search text, then only add
							// once.
							if (!foundSet.contains(text.toLowerCase()))
							{
								addItem(new PlayerID(-1,text));
							}

							for (PlayerID s : founds)
							{
								addItem(s);
							}

							setEditable(true);
							setPopupVisible(true);
						}

					});

				}

			});

			// When the text component changes, focus is gained
			// and the menu disappears. To account for this, whenever the focus
			// is gained by the JTextComponent and it has searchable values, we
			// show the popup.

			tc.addFocusListener(new FocusListener()
			{

				@Override
				public void focusGained(FocusEvent arg0)
				{
					if (tc.getText().length() > 0)
					{
						setPopupVisible(true);
					}
				}

				@Override
				public void focusLost(FocusEvent arg0)
				{
				}

			});

		} else
		{

			throw new IllegalStateException("Editing component is not a JTextComponent!");

		}

	}

}