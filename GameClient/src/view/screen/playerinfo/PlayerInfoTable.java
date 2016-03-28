package view.screen.playerinfo;

import view.screen.qas.ScreenQAs;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
	 * @param exp 
	 * @param players
	 */
	public void updatePlayerInfo(int exp)
	{
		table.clear();
		Label label = new Label("Exp: " + exp, ScreenQAs.skin);
		table.add(label);
	}
	
}
