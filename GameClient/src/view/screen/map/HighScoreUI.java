package view.screen.map;



import java.util.ArrayList;

import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
	
	private Label header;
	
	private Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	
	/**
	 * sets up UI for top 10 experience points
	 */
	public HighScoreUI()
	{
		this.show();
		setUpListening();
	}
	
	/**
	 * Sets up the QualifiedObserver for HighScoreResponseReport
	 */
	public void setUpListening()
	{
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		cm.registerObserver(this, HighScoreResponseReport.class);

	}
	
	/**
	 * @return the screen showing state
	 */
	public boolean isHighScoreScreenShowing()
	{
		return HS_ScreenShowing;
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
	 * set up the UI to show the top 10 players' XP points
	 */
	public void show()
	{
		this.setSize(Gdx.graphics.getWidth()*.25f, Gdx.graphics.getHeight());
		
		initializeTableContents();
		
		this.addActor(highScoreTable);
		
		toggleHSScreenVisible();

	}
	
	private void initializeTableContents()
	{
		header = new Label("Top 10", skin);
		// Table setup
		highScoreTable = new Table();
		highScoreTable.setFillParent(true);
		highScoreTable.top().left();
		highScoreTable.setBackground(new NinePatchDrawable(getNinePatch("data/backgroundHS.9.png")));
		highScoreTable.add(header).expandX();
		
		// Clear table
		highScoreTable.clearChildren();
		
		// Set header
		highScoreTable.add(header).expandX();
		highScoreTable.row();
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
			updateTable(list);
		}
		
		
	}
	private void updateTable(ArrayList<PlayerScoreRecord> list)
	{
		highScoreTable.setBackground(new NinePatchDrawable(getNinePatch("data/backgroundHS.9.png")));
		highScoreTable.clearChildren();
		highScoreTable.add(header).colspan(3).center();
		highScoreTable.row();
		
		for(int i = 1; i <= 10; i++)
		{
			String playerName = list.get(i-1).getPlayerName();
			highScoreTable.add(new Label(i + ".",skin)).left().padRight((float) .5);
			highScoreTable.add(new Label(playerName, skin)).padRight(1);
			Label xp = new Label("    " + list.get(i-1).getExperiencePoints(), skin);
			xp.setColor(Color.GREEN);
			highScoreTable.add(xp).padRight((float) .5);
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
