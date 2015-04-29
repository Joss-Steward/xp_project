package view.screen.map;



import model.QualifiedObservableReport;
import model.QualifiedObserver;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

/**
 * @author ck4124, Scott
 *
 */
public class HighScoreUI extends Group implements QualifiedObserver
{
	
	Table highScoreTable;
	
	boolean HS_ScreenShowing = true;
	
	/**
	 * sets up UI for top 10 experience points
	 */
	public HighScoreUI()
	{
		setUpUI();
	}

	/**
	 * set up the UI to show the top 10 players' XP points
	 */
	private void setUpUI()
	{
		final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		this.setSize(Gdx.graphics.getWidth()*.25f, Gdx.graphics.getHeight());
		
		highScoreTable = new Table();
		highScoreTable.setFillParent(true);
		highScoreTable.top().left();
		highScoreTable.setBackground(new NinePatchDrawable(getNinePatch("data/backgroundHS.9.png")));
		
		highScoreTable.add(new Label("Top 10 List", skin)).colspan(2).center();
		highScoreTable.row();
		
		this.addActor(highScoreTable);
		
		toggleHSScreenVisible();

	}
	
	/**
	 * Toggle the invisibility of the highscore list
	 */
	public void toggleHSScreenVisible()
	{
		if (isHighScoreScreenShowing())
		{
			HS_ScreenShowing = false;
			this.addAction(Actions.moveTo(-this.getWidth(), 0, .3f));
		} else
		{
			HS_ScreenShowing = true;
			this.addAction(Actions.moveTo(0, 0, .3f));
		}
	}

	/**
	 * @return the screen showing state
	 */
	public boolean isHighScoreScreenShowing()
	{
		return HS_ScreenShowing;
	}

	/** (Javadoc)
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		
		
	}

	private NinePatch getNinePatch(String fileName)
	{
		// get the image
		final Texture t = new Texture(Gdx.files.internal(fileName));
		return new NinePatch(new TextureRegion(t));
	}

}
