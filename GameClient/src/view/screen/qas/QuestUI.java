package view.screen.qas;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import view.screen.SkinPicker;
import model.ClientModelFacade;
import model.ClientPlayerQuest;
import model.CommandPrintAdventures;
import model.CommandSendQuestState;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.QuestStateReport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A basic screen that displays the quests and adventure states.
 * 
 * @author ck4124
 * @rewritten Ian Keefer and TJ Renninger
 */
public class QuestUI extends Group implements QualifiedObserver
{
	private final float WIDTH = 600f;
	private final float HEIGHT = 300f;
	private final float POS_X = (Gdx.graphics.getWidth() - WIDTH) / 2;
	private final float POS_Y = (Gdx.graphics.getHeight() - HEIGHT) / 1.1f;
	private QuestTable questTable;
	private AdventureTable adventureTable;
	private Table container; //This is required to hold both the quest table and the adventure table.
	private TextButton printButton;
	boolean qaScreenShowing;
	private ArrayList<ClientPlayerQuest> questList;

	/**
	 * Basic constructor. will call show() to initialize all the data in the
	 * tables.
	 */
	public QuestUI()
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
		
		//Make header label
		container.add(questTable).width(.40f * WIDTH).height(HEIGHT); //Add quest table and tell it how much space to take;
		container.add(adventureTable).width(.60f * WIDTH).height(HEIGHT);
		
		//Make the print button
		printButton = new TextButton("Print", SkinPicker.getSkinPicker().getCrewSkin());
		addPrintButtonListener();
	
		addActor(container);
		addActor(printButton);
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
	 * 
	 */
	private void addPrintButtonListener()
	{
		printButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) 
			{
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setApproveButtonText("Save");
				fileChooser.setApproveButtonToolTipText("Save selected file");
				int option = fileChooser.showOpenDialog(null);
				if (option == JFileChooser.APPROVE_OPTION)
				{
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					if (path.contains("."))
					{
						path = path.substring(0, path.indexOf("."));
					}
					CommandPrintAdventures cpa = new CommandPrintAdventures(path + ".pdf");
					ClientModelFacade.getSingleton().queueCommand(cpa);
				}
				super.clicked(event, x, y);
			}
		});
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
			ClientPlayerQuest firstQuest= questList.get(0);
			adventureTable.updateAdventures(firstQuest.getQuestDescription(), firstQuest.getAdventureList());
			questTable.requestFoucus();
		}
	}
}
