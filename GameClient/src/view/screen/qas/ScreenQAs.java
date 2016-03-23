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
	public static final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	private QuestTable questTable;
	private Table container;
	boolean qaScreenShowing = false;

	private ArrayList<ClientPlayerQuest> questList = new ArrayList<ClientPlayerQuest>();

	/**
	 * Basic constructor. will call show() to initialize all the data in the
	 * tables.
	 */
	public ScreenQAs()
	{
		setUpListening();
		setSize(500, 500);
		setPosition((Gdx.graphics.getWidth() - getWidth()) / 2, (Gdx.graphics.getHeight() - getHeight()) / 1.1f);
		container = new Table();
		container.setFillParent(true);
		container.left().top();
		questTable = new QuestTable(questList);
		container.add(questTable);
		addActor(container);
	}
		
	/**
	 * Sets up the QualifiedObserver for QuestStateReport
	 */
	public void setUpListening()
	{
		QualifiedObservableConnector cm = QualifiedObservableConnector
				.getSingleton();
		cm.registerObserver(this, QuestStateReport.class);
	}
	
	/**
	 * Set the visibility of the QAScreen to the given boolean
	 * 
	 * @param b
	 *            boolean given for showing
	 */
	public void setQAScreenVisibility(boolean b)
	{
		qaScreenShowing = b;
	}

	/**
	 * Is the quest table on the screen
	 * 
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
		}
	}
	
	private NinePatch getNinePatch(String fileName)
	{
		// get the image
		final Texture t = new Texture(Gdx.files.internal(fileName));
		return new NinePatch(new TextureRegion(t));
	}
}
