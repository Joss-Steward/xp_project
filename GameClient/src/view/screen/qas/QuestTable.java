package view.screen.qas;
import java.util.ArrayList;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import model.ClientPlayerQuest;

/**
 * @author TJ Renninger and Ian Keefer
 *
 */
public class QuestTable extends ScrollPane
{

	private Table table;
	private AdventureTable adventureTable;
	//private ArrayList<ClientPlayerQuest> questList;
	
	/**
	 * @param questList
	 */
	public QuestTable(ArrayList<ClientPlayerQuest> ql)
	{
		super(null, ScreenQAs.skin);	//Null is passed in because the widget has not been created yet.
		setFadeScrollBars(false);
		//questList = ql;
		
		//Build the table that holds all of the quests
		buildTable();
		setWidget(table);
		
		updateQuests(ql);
		requestFoucus();
	}
	
	/**
	 * @param at
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
		Label l = new Label(cpq.getQuestTitle(), ScreenQAs.skin);
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
	 * @param questList
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
