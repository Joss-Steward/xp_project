package view.screen.map;



import java.util.ArrayList;

import model.QualifiedObservableConnector;
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

import datasource.PlayerScoreRecord;
import edu.ship.shipsim.client.model.CommandHighScoreRequest;
import edu.ship.shipsim.client.model.ModelFacade;
import edu.ship.shipsim.client.model.reports.HighScoreResponseReport;

/**
 * @author ck4124, Scott
 *
 */
public class HighScoreUI extends Group implements QualifiedObserver
{
	
	Table highScoreTable;
	
	boolean HS_ScreenShowing = true;
	
	private Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	
	/**
	 * sets up UI for top 10 experience points
	 */
	public HighScoreUI()
	{
		setUpListening();
		setUpUI();
	}
	
	/**
	 * Sets up the QualifiedObserver for HighScoreResponseReport
	 */
	public void setUpListening()
	{
		QualifiedObservableConnector cm = QualifiedObservableConnector
				.getSingleton();
		cm.registerObserver(this, HighScoreResponseReport.class);

	}

	/**
	 * set up the UI to show the top 10 players' XP points
	 */
	private void setUpUI()
	{
		this.setSize(Gdx.graphics.getWidth()*.25f, Gdx.graphics.getHeight());
		highScoreTable.add(new Label("Not Loaded", skin)).colspan(2).center();
		highScoreTable.row();
		highScoreTable = new Table();
		highScoreTable.setFillParent(true);
		highScoreTable.top().left();
		
		
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
			
			CommandHighScoreRequest cmd = new CommandHighScoreRequest();
			ModelFacade.getSingleton().queueCommand(cmd);
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
		if (report.getClass().equals(HighScoreResponseReport.class))
		{
			HighScoreResponseReport rep = (HighScoreResponseReport) report;
			
			ArrayList<PlayerScoreRecord> list = rep.getScoreList();
			updateHighScoreList(list);
		}
		
		
	}

	private void updateHighScoreList(ArrayList<PlayerScoreRecord> list)
	{
		highScoreTable.clearChildren();
		highScoreTable.setBackground(new NinePatchDrawable(getNinePatch("data/backgroundHS.9.png")));
		
		highScoreTable.add(new Label("Top 10 List", skin)).colspan(2).center();
		highScoreTable.row();
		
		for(int i = 1; i <= 10; i++)
		{
			highScoreTable.add(new Label(""+ i + ". ",skin));
			highScoreTable.add(new Label(list.get(i-1).getPlayerName(), skin));
			highScoreTable.add(new Label(""+list.get(i-1).getExperiencePoints(),skin));
			highScoreTable.row();
		}

	}

	private NinePatch getNinePatch(String fileName)
	{
		// get the image
		final Texture t = new Texture(Gdx.files.internal(fileName));
		return new NinePatch(new TextureRegion(t));
	}

}
