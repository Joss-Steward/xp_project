package view.screen.map;

import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import edu.ship.shipsim.client.model.PlayerManager;
import edu.ship.shipsim.client.model.ThisClientsPlayer;
import edu.ship.shipsim.client.model.reports.QuestStateReport;

/**
 * @author ck4124
 * Displays ThisClientPlayer's experiencePoints and progress for the current level
 */
public class ExperienceDisplay extends Group implements QualifiedObserver
{
	private int experiencePoints;
	private int numPointsLvlRequries;
	private String playersLevel;
	
	private Table experienceDisplay;
	
	private final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	
	/**
	 * constructor to create the player's experience points display
	 */	
	public ExperienceDisplay()
	{
		this.show();
		setUpListening();
	}
	
	
	private void setUpListening()
	{
//		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
//		cm.registerObserver(this, QuestStateReport.class);
	}


	private void show()
	{
		this.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		experienceDisplay = new Table();
		experienceDisplay.setFillParent(true);
		experienceDisplay.top().right();
		
		experienceDisplay.add(new Label("Experience Display goes here", skin)).row();
			
		this.addActor(experienceDisplay);
	}


	public int getExperiencePoints()
	{
		return experiencePoints;
	}
	public void setExperiencePoints(int experiencePoints)
	{
		this.experiencePoints = experiencePoints;
	}
	public int getNumPointsLvlRequries()
	{
		return numPointsLvlRequries;
	}
	public void setNumPointsLvlRequries(int numPointsLvlRequries)
	{
		this.numPointsLvlRequries = numPointsLvlRequries;
	}
	public String getPlayersLevel()
	{
		return playersLevel;
	}
	public void setPlayersLevel(String playersLevel)
	{
		this.playersLevel = playersLevel;
	}


	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		// TODO Auto-generated method stub
		
	}
	
}
