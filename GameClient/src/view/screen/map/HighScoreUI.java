package view.screen.map;



import java.util.ArrayList;

import model.CommandHighScoreRequest;
import model.ClientModelFacade;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.HighScoreResponseReport;

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

import data.PlayerScoreRecord;

/**
 * @author ck4124, Scott
 * @fixedby Ian Keefer and TJ Renninger
 */
public class HighScoreUI extends Group implements QualifiedObserver
{
	private final float WIDTH = 200f;
	private final float HEIGHT = 300f;
	private final float POS_X = (Gdx.graphics.getWidth() - WIDTH) / 2;
	private final float POS_Y = (Gdx.graphics.getHeight() - HEIGHT) / 1.1f;
	private HighScoreTable highScoreTable;
	private boolean HS_ScreenShowing;
	private Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	
	/**
	 * sets up UI for top 10 experience points
	 */
	public HighScoreUI()
	{
		setUpListening();
		setSize(WIDTH, HEIGHT);
		setPosition(POS_X, POS_Y);
		highScoreTable = new HighScoreTable();
		highScoreTable.setFillParent(true);
		addActor(highScoreTable);
		setVisible(false);
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
	 * set visibility of High Score Screen
	 * @param b sets visibility of screen
	 */
	public void setHighScoreScreenVisibility(boolean b)
	{
		HS_ScreenShowing = b;
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
			this.addAction(Actions.hide());
		} else
		{	
			CommandHighScoreRequest cmd = new CommandHighScoreRequest();
			ClientModelFacade.getSingleton().queueCommand(cmd);
			HS_ScreenShowing = true;
			this.addAction(Actions.show());
		}
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
	private void updateTable(ArrayList<PlayerScoreRecord> players)
	{
		highScoreTable.updateHighScores(players);
	}
}
