package view.screen.qas;

import view.screen.SkinPicker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @author TJ Renninger, Ian Keefer
 *
 */
public class LegendTable extends ScrollPane
{
		private Table table;
		
		/**
		 * Table that displays all of the adventures of a selected quest for the player
		 */
		public LegendTable()
		{
			super(null, SkinPicker.getSkinPicker().getCrewSkin());
			setFadeScrollBars(true);
			buildTable();
			setScrollingDisabled(true, true);
			setWidget(table);
		}

		/**
		 * Creates a new table that will hold all of the quests and sets it to the top left position of the scroll pane
		 */
		private void buildTable()
		{
			table = new Table();
			table.left().top();
			
			Label label = new Label("Completion Legend:", SkinPicker.getSkinPicker().getCrewSkin());
			table.add(label).right().padRight(10f);
			label = new Label("Completed", SkinPicker.getSkinPicker().getCrewSkin());
			label.setColor(Color.GREEN);
			table.add(label).right().padRight(10f);
			label = new Label("Started", SkinPicker.getSkinPicker().getCrewSkin());
			label.setColor(Color.YELLOW);
			table.add(label).right().padRight(10f);
			label = new Label("Not Started", SkinPicker.getSkinPicker().getCrewSkin());
			Color red = Color.valueOf("fa6b7b");
			label.setColor(red);
			table.add(label).right().padRight(10f);
			
		}

	}
