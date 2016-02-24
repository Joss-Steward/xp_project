package model;

/**
 * Command of receiving a key input message
 * @author Ian Keefer & TJ Renninger
 *
 */
public class CommandKeyInputMessageReceived extends Command {
	
	private String input;
	
	/**
	 * @param input user key input
	 */
	public CommandKeyInputMessageReceived(String input) {
		this.input = input;
	}
	
	/**
	 * @return user key input
	 */
	public String getInput() {
		return input;
	}

	//TODO for now we force this to return true until further functionality is added
	@Override
	protected boolean execute() {
		// create report
		return true;
	}

}
