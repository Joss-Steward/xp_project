package view.screen.qas;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

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
	private LegendTable legendTable;
	private Table container; 											//This is required to hold both the quest table and the adventure table.
	private Table subContainer;
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
		legendTable = new LegendTable();
		questTable.setAdventureTable(adventureTable);  					//Set the adventure table so that when a quest is clicked it can update it.
		
		//Make the container
		container = new Table();
		container.setFillParent(true);  								//Sets the container to the same size as the quest screen
		container.left().top();  										//Sets the container to be at the top left of the quest screen (not the game screen).
		
		//
		subContainer = new Table();
		subContainer.add(questTable).width(.40f * WIDTH).height(.90f * HEIGHT); 	//Add quest table and tell it how much space to take;
		subContainer.add(adventureTable).width(.60f * WIDTH).height(.90f * HEIGHT);
		container.add(subContainer);
		container.row();
		subContainer = new Table();
		subContainer.add(legendTable).width(.90f * WIDTH);
		
		//Make the print button
		printButton = new TextButton("Print", SkinPicker.getSkinPicker().getCrewSkin());
		//printButton.right();
		subContainer.add(printButton).width(.10f * WIDTH);
		addPrintButtonListener();
		container.add(subContainer);
		
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
	 * When the print button is click, this handles creating a new file chooser and then
	 * sends the print command if a valid file is chosen.
	 */
	private void addPrintButtonListener()
	{
		printButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) 
			{
				JFileChooser fileChooser = openFileChooser();			//Creates a new file chooser, set up the way we need it
				int option = fileChooser.showOpenDialog(null);			//Records the button that the user clicked
				if (option == JFileChooser.APPROVE_OPTION)				//Only save the file is the user clicks "Save" not "Cancel"
				{
					File file = fileChooser.getSelectedFile();			//Gets the files the the user has chosen to save
					String path = file.getAbsolutePath();				//Gets the absolute path of that chosen file
					if (!path.toLowerCase().endsWith(".pdf"))			//Makes sure that the the file extension is PDF
					{
						path += ".pdf";									//If the file extension is not PDF, it sets it to PDF
					}
					//Create and queues the print Adventures command
					CommandPrintAdventures cpa = new CommandPrintAdventures(path);
					ClientModelFacade.getSingleton().queueCommand(cpa);
				}
				super.clicked(event, x, y);
			}
		});
	}
	
	/**
	 * Creates a file chooser with the filters and parameters that we need
	 * for creating a pdf file for the quests and adventures.
	 * @return The file chooser that is created
	 */
	private JFileChooser openFileChooser()
	{
		JFileChooser fileChooser = new JFileChooser();					//Sets up a new file chooser to
		fileChooser.setDialogTitle("Print PDF");						//Sets the tile of the of the window that pops up to Print PDF
		fileChooser.setApproveButtonText("Save");						//Set the button text to save instead of open
		fileChooser.setApproveButtonToolTipText("Save selected file");	//Hovering over the save button will display this text
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);		//Makes it so that directories cannot be chosen
		fileChooser.setCurrentDirectory(new File("~"));					//Sets the default save directory to the home directory
		fileChooser.setFileFilter(new FileFilter() 						//Filers the type of files allowed
		{
			@Override
			public String getDescription() 
			{
				return "PDF File (*.pdf)";								//Only PDF files are allowed
			}															//This does not prevent the use of another file extension though

			@Override
			public boolean accept(File f) { return true; }
		});
		return fileChooser;
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
