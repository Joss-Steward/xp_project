package view.screen.qas;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import model.ClientPlayerQuest;
import view.screen.SkinPicker;

/**
 * @author TJ Renninger and Ian Keefer
 *
 */
public class QuestTable extends ScrollPane
{

	private Table table;
	private AdventureTable adventureTable;
	
	/**
	 * @param questList The list of quest that the player has
	 */
	public QuestTable(ArrayList<ClientPlayerQuest> questList)
	{
		super(null, SkinPicker.getSkinPicker().getCrewSkin());	//Null is passed in because the widget has not been created yet.
		setFadeScrollBars(false);
		//questList = ql;
		
		//Build the table that holds all of the quests
		buildTable();
		setWidget(table);
		
		updateQuests(questList);
		requestFoucus();
	}
	
	/**
	 * Sets the adventure table so that the quest table is able to send the adventure table
	 * which quest to display adventures for.
	 * @param at The table that hold all of the adventures
	 */
	public void setAdventureTable(AdventureTable at)
	{
		adventureTable = at;
	}
	
	/**
	 * Build the table that will hold all of the quests.
	 */
	private void buildTable()
	{
		table = new Table();
		table.left().top();
		table.pad(10f);
	}

	/**
	 * Creates a label for a quest to be displayed in the quest table.
	 * The label will be clickable
	 * @param questName
	 * @return
	 */
	private Label createQuestLabel(final ClientPlayerQuest cpq) 
	{
		Label l = new Label(cpq.getQuestTitle(), SkinPicker.getSkinPicker().getCrewSkin());
		switch (cpq.getQuestState()) 
		{
		case TRIGGERED:
			l.setColor(Color.valueOf("e6853c"));
			break;
		case FULFILLED:
			l.setColor(Color.YELLOW);
			break;
		case FINISHED:
			l.setColor(Color.GREEN);
			break;
		case EXPIRED:
            l.setColor(Color.DARK_GRAY);
            break;
		default:
			l.setColor(Color.WHITE);
			break;
		}
		ClickListener clickListener = new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				adventureTable.updateAdventures(cpq.getQuestDescription(), cpq.getAdventureList());
				super.clicked(event, x, y);
			}
		};
		l.addListener(clickListener);
		return l;
	}

	/**
	 * Update the quest table to the current quest that the player has.
	 * @param questList The list of quest that the player has
	 */
	public void updateQuests(ArrayList<ClientPlayerQuest> questList) 
	{
		table.clear();
		for (ClientPlayerQuest cpq : questList)
		//for (int i = 0; i < 30; i++)
		{
			Label l = createQuestLabel(cpq);
			table.add(l).top().row();
		}
	}
	
	/**
	 * This does not work yet, it needs some tweaking
	 */
	public void requestFoucus()
	{
	}
}
