package model;

/**
 * @author Merlin
 * 
 */
public class CommandNewMap extends Command
{

	private String fileTitle;

	/**
	 * @param title
	 *            the title of the tmx file containing the new map
	 */
	public CommandNewMap(String title)
	{
		this.fileTitle = title;
	}

	/**
	 * @see Command#execute()
	 */
	@Override
	protected boolean execute()
	{
		System.out.println("changing to new map with title:" + fileTitle);
		MapManager.getSingleton().changeToNewFile(fileTitle);
		ClientPlayerManager.getSingleton().removeOtherPlayers();
		// PlayerManager.getSingleton().getThisClientsPlayer().getQuestManager().getTriggersFromMap();
		return true;
	}

	/**
	 * 
	 * @return the file title of the new map file
	 */
	public String getFileTitle()
	{
		return fileTitle;
	}
}
