package model;

import static org.junit.Assert.assertNull;
import model.CommandRemovePlayer;
import model.OptionsManager;
import model.PlayerManager;
import model.PlayerNotFoundException;
import model.QuestManager;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Merlin
 * 
 */
public class CommandRemovePlayerTest
{

	/**
	 * 
	 */
	@Before
	public void setup()
	{
		PlayerManager.resetSingleton();
		OptionsManager.getSingleton().setTestMode(true);
	}

	/**
	 * If we remove a player, the player manager and quest manager should
	 * forget about it
	 * 
	 * @throws PlayerNotFoundException
	 *             shouldn't
	 */
	@Test
	public void test() throws PlayerNotFoundException
	{
		PlayerManager.getSingleton().addPlayer(1);
		CommandRemovePlayer cmd = new CommandRemovePlayer(1);
		cmd.execute();
		assertNull(PlayerManager.getSingleton().getPlayerFromID(1));
		assertNull(QuestManager.getSingleton().getQuestList(1));
	}

}
