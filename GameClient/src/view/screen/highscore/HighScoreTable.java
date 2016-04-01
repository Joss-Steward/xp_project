package view.screen.highscore;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import data.PlayerScoreRecord;
import view.screen.SkinPicker;
/**
 * @author TJ Renninger and Ian Keefer
 *
 */
public class HighScoreTable extends ScrollPane
{
	private final float tablePadding = 10f;
	private Table table;

	/**
	 * 
	 */
	public HighScoreTable() 
	{
		super(null, SkinPicker.getSkinPicker().getCrewSkin());
		setFadeScrollBars(false);
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
	 * @param players that are on the highscore list
	 */
	public void updateHighScores(ArrayList<PlayerScoreRecord> players)
	{
		table.clear();
		int i = 0;
		for (PlayerScoreRecord p : players)
		{
			addPlayerLabel(p, i + 1);
			i++;
		}
	}
	
	private void addPlayerLabel(PlayerScoreRecord p, int playerRank)
	{
		final float spaceBetweenNumberAndPlayer  = 15f;
		final float spaceBetweenPlayerAndXp = 10f;
		Label rank = new Label(playerRank + ".", SkinPicker.getSkinPicker().getCrewSkin());
		table.add(rank).left().padRight(spaceBetweenNumberAndPlayer);
		Label player = new Label(p.getPlayerName(), SkinPicker.getSkinPicker().getCrewSkin());
		table.add(player).left().padRight(spaceBetweenPlayerAndXp);
		Label xp = new Label("" + p.getExperiencePoints(), SkinPicker.getSkinPicker().getCrewSkin());
		xp.setColor(Color.GREEN);
		table.add(xp).right().row();
	}
}
