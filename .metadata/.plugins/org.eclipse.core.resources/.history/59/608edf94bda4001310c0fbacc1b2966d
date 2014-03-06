package model;

/**
 * @author Merlin
 * 
 */
public class CommandNewMap extends Command {

	private String fileTitle;

	/**
	 * @param title
	 *            the title of the tmx file containing the new map
	 */
	public CommandNewMap(String title) {
		this.fileTitle = title;
	}

	/**
	 * @see model.Command#execute()
	 */
	@Override
	protected
	boolean execute()
	{
		System.out.println("changing to new map with title:" + fileTitle);
		MapManager.getSingleton().changeToNewFile(fileTitle);
		PlayerManager.getSingleton().getThisClientsPlayer().getQuestManager().getTriggersFromMap();
		return true;
	}
}
