package view.screen.playerinfo;

import view.screen.qas.ScreenQAs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import model.ThisClientsPlayer;
/**
 * @author tr3897
 *
 */
public class PlayerInfoTable extends ScrollPane
{
	private final float tablePadding = 10f;
	private Table table;
	
	/**
	 * 
	 */
	public PlayerInfoTable() 
	{
		super(null, ScreenQAs.skin);
		setScrollingDisabled(true, true);
		buildTable();
		setWidget(table);
	}
	
	private void buildTable()
	{
		table = new Table();
		table.setFillParent(true);
		table.left().top();
		table.pad(tablePadding);
	}
	
	/**
	 * @param player 
	 */
	public void updatePlayerInfo(ThisClientsPlayer player)
	{
		String name = player.getName();
		String crew = player.getCrew().name();
		int exp = player.getExperiencePoints();
		int know = player.getKnowledgePoints();
		int level = player.getLevelRecord().getLevelUpPoints();
		
		table.clear();
		//NAME
		Label label = new Label("Name: ", ScreenQAs.skin);
		table.add(label).left();
		label = new Label(name, ScreenQAs.skin);
		label.setColor(Color.GREEN);
		table.add(label).right().row();
		
		//EXP
		label = new Label("Crew: ", ScreenQAs.skin);
		table.add(label).left();
		label = new Label(crew, ScreenQAs.skin);
		label.setColor(Color.GREEN);
		table.add(label).right().row();
		
		//EXP
		label = new Label("Experience: ", ScreenQAs.skin);
		table.add(label).left();
		label = new Label("" + exp, ScreenQAs.skin);
		label.setColor(Color.GREEN);
		table.add(label).right().row();
		
		//KNOW
		label = new Label("Knowledge Points: ", ScreenQAs.skin);
		table.add(label).left();
		label = new Label("" + know, ScreenQAs.skin);
		label.setColor(Color.GREEN);
		table.add(label).right().row();
		
		//LEVEL
		label = new Label("Level Points: ", ScreenQAs.skin);
		table.add(label).left();
		label = new Label("" + level, ScreenQAs.skin);
		label.setColor(Color.GREEN);
		table.add(label).right().row();
	}
	
}
