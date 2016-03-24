package view.screen.qas;

import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;
import model.CommandSendQuestState;
import model.ClientModelFacade;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.QuestStateReport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import data.AdventureStateEnum;
import data.QuestStateEnum;

/**
 * A basic screen that displays the quests and adventure states.
 * 
 * @author ck4124
 *
 */
public class ScreenQAs extends Group implements QualifiedObserver
{
	/**
	 * Default skin for gui
	 */
	public static final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	private final float WIDTH = 600f;
	private final float HEIGHT = 500f;
	private final float POS_X = (Gdx.graphics.getWidth() - WIDTH) / 2;
	private final float POS_Y = (Gdx.graphics.getHeight() - HEIGHT) / 1.1f;
	private QuestTable questTable;
	private AdventureTable adventureTable;
	private Table container; //This is required to hold both the quest table and the adventure table.
	boolean qaScreenShowing;
	private ArrayList<ClientPlayerQuest> questList;

	/**
	 * Basic constructor. will call show() to initialize all the data in the
	 * tables.
	 */
	public ScreenQAs()
	{
		questList = new ArrayList<ClientPlayerQuest>();
		setUpListening();
		setSize(WIDTH, HEIGHT);
		setPosition(POS_X, POS_Y);
		questTable = new QuestTable(questList);
		adventureTable = new AdventureTable();
		questTable.setAdventureTable(adventureTable);  //Set the adventure table so that when a quest is clicked it can update it.
		
		//Make the container
		container = new Table();
		container.setFillParent(true);  //Sets the container to the same size as the quest screen
		container.left().top();  //Sets the container to be at the top left of the quest screen (not the game screen).
		container.add(questTable).width(.40f * WIDTH).height(HEIGHT); //Add quest table and tell it how much space to take;
		container.add(adventureTable).width(.60f * WIDTH).height(HEIGHT);
		
		addActor(container);
		setVisible(false);
	}
		
	/**
	 * Sets up the QualifiedObserver for QuestStateReport
	 */
	public void setUpListening()
	{
		QualifiedObservableConnector cm = QualifiedObservableConnector.getSingleton();
		cm.registerObserver(this, QuestStateReport.class);
	}
	
	/**
	 * Set the visibility of the QAScreen to the given boolean
	 * @param b boolean given for showing
	 */
	public void setQAScreenVisibility(boolean b)
	{
		qaScreenShowing = b;
	}

	/**
	 * Is the quest table on the screen
	 * @return showing ; is there quests currently displaying on the screen
	 */
	public boolean isQAScreenShowing()
	{
		return qaScreenShowing;
	}

	/**
	 * Toggle the invisibility of the quest list
	 */
	public void toggleQAScreenVisible()
	{
		VisibleAction action;
		if (isQAScreenShowing())
		{
			qaScreenShowing = false;
			action = Actions.hide();
		} else
		{
			qaScreenShowing = true;
			action = Actions.show();
			CommandSendQuestState cmd = new CommandSendQuestState();
			ClientModelFacade.getSingleton().queueCommand(cmd);
		}
		addAction(action);
	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		if (report.getClass().equals(QuestStateReport.class))
		{
			QuestStateReport r = (QuestStateReport) report;
			questList = r.getClientPlayerQuestList();
			questTable.updateQuests(questList);
			questTable.requestFoucus();
		}
	}
	
	private NinePatch getNinePatch(String fileName)
	{
		// get the image
		final Texture t = new Texture(Gdx.files.internal(fileName));
		return new NinePatch(new TextureRegion(t));
	}
}
