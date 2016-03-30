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
		Table labelGroup = new Table();
		Label label1 = new Label("Name: ", ScreenQAs.skin);
		Label label2 = new Label(name, ScreenQAs.skin);
		label2.setColor(Color.GREEN);
		labelGroup.add(label1).left();
		labelGroup.add(label2).left();
		table.add(labelGroup).left().row();
		
		//CREW
		labelGroup = new Table();
		label1 = new Label("Crew: ", ScreenQAs.skin);
		label2 = new Label(crew, ScreenQAs.skin);
		label2.setColor(Color.GREEN);
		labelGroup.add(label1).left();
		labelGroup.add(label2).left();
		table.add(labelGroup).left().row();
		
		//EXP
		labelGroup = new Table();
		label1 = new Label("Experience: ", ScreenQAs.skin);
		label2 = new Label("" + exp, ScreenQAs.skin);
		label2.setColor(Color.GREEN);
		labelGroup.add(label1).left();
		labelGroup.add(label2).left();
		table.add(labelGroup).left().row();
		
		//KNOW
		labelGroup = new Table();
		label1 = new Label("Knowledge Points: ", ScreenQAs.skin);
		label2 = new Label("" + know, ScreenQAs.skin);
		label2.setColor(Color.GREEN);
		labelGroup.add(label1).left();
		labelGroup.add(label2).left();
		table.add(labelGroup).left().row();
		
		//LEVEL
		labelGroup = new Table();
		label1 = new Label("Level Points: ", ScreenQAs.skin);
		label2 = new Label("" + level, ScreenQAs.skin);
		label2.setColor(Color.GREEN);
		labelGroup.add(label1).left();
		labelGroup.add(label2).left();
		table.add(labelGroup).left().row();
	}
	
}
