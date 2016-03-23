package view.screen.qas;
import java.util.ArrayList;

import model.ClientPlayerQuest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * @author TJ Renninger and Ian Keefer
 *
 */
public class QuestTable extends ScrollPane
{

	private Table table;
	
	/**
	 * @param questList
	 */
	public QuestTable(ArrayList<ClientPlayerQuest> questList)
	{
		super(null, ScreenQAs.skin);
		setScrollBarPositions(false, true);
		setFillParent(true);
		table = new Table();
		table.left().top();
		//table.setFillParent(true);
//		Sprite sprite = new Sprite(new Texture(Gdx.files.internal("data/background.9.png")));
//		table.setBackground(new SpriteDrawable(sprite));
		updateQuests(questList);
		setWidget(table);
		layout();
	}

	private Label createClickableLabel(String questName) 
	{
		Label l = new Label(questName, ScreenQAs.skin);
		return l;
	}

	/**
	 * @param questList
	 */
	public void updateQuests(ArrayList<ClientPlayerQuest> questList) 
	{
		table.clear();
		//for (ClientPlayerQuest cpq : questList)
		for (int i = 0; i < 30; i++)
		{
			Label l = createClickableLabel("Quest: " /*+ cpq.getQuestID()*/);
			table.add(l).top().row();
		}
		layout();
	}
	
	
}
