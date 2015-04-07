package view.screen.map;

import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import edu.ship.shipsim.client.model.reports.ExperiencePointsChangeReport;

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
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		cm.registerObserver(this, ExperiencePointsChangeReport.class);
	}


	private void show()
	{
		
		experienceDisplay = new Table();
		experienceDisplay.setFillParent(true);
		experienceDisplay.top().right();
		this.setSize(300, 50);
		
		experienceDisplay.add(new Label("Experience Display goes here", skin)).row();
			
		this.addActor(experienceDisplay);
	}


	/**
	 * @return experiencePoints
	 */
	public int getExperiencePoints()
	{
		return experiencePoints;
	}
	
	/**
	 * Set player's experience points
	 * @param experiencePoints player's experience points
	 */
	public void setExperiencePoints(int experiencePoints)
	{
		this.experiencePoints = experiencePoints;
	}
	
	/**
	 * @return numPointsLvlRequries
	 */
	public int getNumPointsLvlRequries()
	{
		return numPointsLvlRequries;
	}
	
	/**
	 * Set number of points level requires
	 * @param numPointsLvlRequries player's number of points level requires
	 */
	public void setNumPointsLvlRequries(int numPointsLvlRequries)
	{
		this.numPointsLvlRequries = numPointsLvlRequries;
	}
	
	/**
	 * Get player's level
	 * @return playersLevel
	 */
	public String getPlayersLevel()
	{
		return playersLevel;
	}
	
	/**
	 * Set the player's level
	 * @param playersLevel this player's level
	 */
	public void setPlayersLevel(String playersLevel)
	{
		this.playersLevel = playersLevel;
	}


	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		if (report.getClass().equals(ExperiencePointsChangeReport.class))
		{
			ExperiencePointsChangeReport r = (ExperiencePointsChangeReport) report;
			setExperiencePoints(r.getExperiencePoints());
			setNumPointsLvlRequries(r.getLevelRecord().getLevelUpPoints());
			setPlayersLevel(r.getLevelRecord().getDescription());
			
			updateExperienceDisplay();
		}
		
	}

	/**
	 * clears the experiencePoints UI display and sets the new values from the report
	 */
	private void updateExperienceDisplay()
	{
		experienceDisplay.clear();
		
		experienceDisplay.add(new Label(getPlayersLevel() + " " + 
				getExperiencePoints() + " / " + getNumPointsLvlRequries(), skin)).row();
	}
	
}
