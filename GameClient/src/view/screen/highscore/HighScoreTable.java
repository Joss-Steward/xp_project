package view.screen.highscore;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import data.PlayerScoreRecord;
import view.screen.qas.ScreenQAs;

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
		super(null, ScreenQAs.skin);
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
	 * @param players
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
		Label rank = new Label(playerRank + ".", ScreenQAs.skin);
		table.add(rank).left().padRight(spaceBetweenNumberAndPlayer);
		Label player = new Label(p.getPlayerName(), ScreenQAs.skin);
		table.add(player).left().padRight(spaceBetweenPlayerAndXp);
		Label xp = new Label("" + p.getExperiencePoints(), ScreenQAs.skin);
		xp.setColor(Color.GREEN);
		table.add(xp).right().row();
	}
}
