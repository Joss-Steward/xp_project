package view.screen.qas;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import data.AdventureStateEnum;
import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;

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
	 * 
	 */
	private void buildTable()
	{
		table = new Table();
		table.left().top();
		table.pad(PADDING);
	}
	
	/**
	 * @param questDesc 
	 * @param adventureList
	 */
	public void updateAdventures(String questDesc, ArrayList<ClientPlayerAdventure> adventureList)
	{
		table.clear();
		Label l = new Label("Description:\n" + questDesc + "\nAdventures:\n", ScreenQAs.skin);
		l.setWrap(true);
		table.add(l).left().top().width(table.getWidth() - PADDING * 4f);		//Width is used to tell the label when to wrap its text.
		table.row();
		for (int i = 0; i < 100; i++)  //Add a stupid amount of adventures to test scrolling
		for (ClientPlayerAdventure cpa : adventureList)
		{
			l = createAdventureLabel(cpa);
			table.add(l).left().top().width(table.getWidth() - PADDING * 4f);	//Width is used to tell the label when to wrap its text.
			table.row();
		}
	}

	private Label createAdventureLabel(ClientPlayerAdventure cpa)
	{
		Label l = new Label(cpa.getAdventureDescription(), ScreenQAs.skin);
		l.setWrap(true);
		if (cpa.getAdventureState() == AdventureStateEnum.COMPLETED)
		{
			l.setColor(Color.GREEN);
		}
		return l;
	}
}
