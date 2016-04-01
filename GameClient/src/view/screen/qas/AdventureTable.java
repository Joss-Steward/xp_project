package view.screen.qas;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import data.AdventureStateEnum;
import model.ClientPlayerAdventure;

/**
 * @author TJ Renninger and Ian Keefer
 *
 */
public class AdventureTable extends ScrollPane
{
	private final float PADDING = 10f;
	private Table table;
	
	/**
	 * 
	 */
	public AdventureTable()
	{
		super(null, ScreenQAs.skin);
		setFadeScrollBars(false);
		//Build the table that holds all of the quests
		buildTable();
		setWidget(table);
	}

	/**
	 * Creates a new table that will hold all of the quests and sets it to the top left position of the scroll pane
	 */
	private void buildTable()
	{
		table = new Table();
		table.left().top();
		table.pad(PADDING);
	}
	
	/**
	 * Updates the adventure table when a new quest is selected.
	 * @param questDesc The description of the quest
	 * @param adventureList The list of adventures the quest has
	 */
	public void updateAdventures(String questDesc, ArrayList<ClientPlayerAdventure> adventureList)
	{
		table.clear();
		Label l = new Label("Description:\n" + questDesc + "\nAdventures:\n", ScreenQAs.skin);
		l.setWrap(true);
		table.add(l).left().top().width(table.getWidth() - PADDING * 4f);		//Width is used to tell the label when to wrap its text.
		table.row();
		//for (int i = 0; i < 100; i++)  //Add a stupid amount of adventures to test scrolling
		for (ClientPlayerAdventure cpa : adventureList)
		{
			l = createAdventureLabel(cpa);
			table.add(l).left().top().width(table.getWidth() - PADDING * 4f);	//Width is used to tell the label when to wrap its text.
			table.row();
		}
	}

	/**
	 * Creates the label for a given adventure. The colour of the label is determined by that State of the adventure.
	 * Green: Complete
	 * Red: Not Started
	 * @param cpa  The adventure to make the label for
	 * @return The created adventure label
	 * We made need to refactor this into its own class depending on how fancy we want the labels.
	 */
	private Label createAdventureLabel(ClientPlayerAdventure cpa)
	{
		Label l = new Label(cpa.getAdventureDescription(), ScreenQAs.skin);
		l.setWrap(true);
		if (cpa.getAdventureState() == AdventureStateEnum.COMPLETED)
		{
			l.setColor(Color.GREEN);
		}
		else
		{
			l.setColor(Color.RED);
		}
		return l;
	}
}
