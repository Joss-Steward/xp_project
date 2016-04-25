package view.screen.playerinfo;
import model.ThisClientsPlayer;
import view.screen.OverlayingScreenTable;
import view.screen.SkinPicker;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @author TJ Renninger and Ian Keefer
 *
 */
public class PlayerInfoTable extends OverlayingScreenTable
{
	/**
	 * @param scrollable Whether or not the the overlaying screen table is scrollable
	 */
	public PlayerInfoTable(boolean scrollable) 
	{
		super(scrollable);
	}

	/**
	 * @param player that is currently logged into the client
	 */
	public void updatePlayerInfo(ThisClientsPlayer player)
	{
		String name = player.getName();
		String crew = player.getCrew().name();
		String major = player.getMajor().name();
		int exp = player.getExperiencePoints();
		int know = player.getKnowledgePoints();
		int level = player.getLevelRecord().getLevelUpPoints();
		String deadline = player.getTimeLeft();
		
		container.clear();
		//NAME
		Table labelGroup = new Table();
		Label label1 = new Label("Name: ", SkinPicker.getSkinPicker().getCrewSkin());
		Label label2 = new Label(name, SkinPicker.getSkinPicker().getCrewSkin());
		label2.setColor(Color.GREEN);
		labelGroup.add(label1).left();
		labelGroup.add(label2).left();
		container.add(labelGroup).left().row();
		
		//CREW
		labelGroup = new Table();
		label1 = new Label("Crew: ", SkinPicker.getSkinPicker().getCrewSkin());
		label2 = new Label(crew, SkinPicker.getSkinPicker().getCrewSkin());
		label2.setColor(Color.GREEN);
		labelGroup.add(label1).left();
		labelGroup.add(label2).left();
		container.add(labelGroup).left().row();

		//MAJOR
		labelGroup = new Table();
		label1 = new Label("Major: ", SkinPicker.getSkinPicker().getCrewSkin());
		label2 = new Label(major, SkinPicker.getSkinPicker().getCrewSkin());
		label2.setColor(Color.GREEN);
		labelGroup.add(label1).left();
		labelGroup.add(label2).left();
		container.add(labelGroup).left().row();
		
		//EXP
		labelGroup = new Table();
		label1 = new Label("Experience: ", SkinPicker.getSkinPicker().getCrewSkin());
		label2 = new Label("" + exp, SkinPicker.getSkinPicker().getCrewSkin());
		label2.setColor(Color.GREEN);
		labelGroup.add(label1).left();
		labelGroup.add(label2).left();
		container.add(labelGroup).left().row();
		
		//KNOW
		labelGroup = new Table();
		label1 = new Label("Knowledge Points: ", SkinPicker.getSkinPicker().getCrewSkin());
		label2 = new Label("" + know, SkinPicker.getSkinPicker().getCrewSkin());
		label2.setColor(Color.GREEN);
		labelGroup.add(label1).left();
		labelGroup.add(label2).left();
		container.add(labelGroup).left().row();
		
		//LEVEL
		labelGroup = new Table();
		label1 = new Label("Level Points: ", SkinPicker.getSkinPicker().getCrewSkin());
		label2 = new Label("" + level, SkinPicker.getSkinPicker().getCrewSkin());
		label2.setColor(Color.GREEN);
		labelGroup.add(label1).left();
		labelGroup.add(label2).left();
		container.add(labelGroup).left().row();
		
		//DEADLINE TO LEVEL UP
		labelGroup = new Table();
		label1 = new Label("Level Up Deadline: ", SkinPicker.getSkinPicker().getCrewSkin());
		label2 = new Label(deadline, SkinPicker.getSkinPicker().getCrewSkin());
		label2.setColor(Color.GREEN);
		labelGroup.add(label1).left();
		labelGroup.row();
		labelGroup.add(label2).left();
		container.add(labelGroup).left().row();
	}
	
}
