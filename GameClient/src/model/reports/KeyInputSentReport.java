package model.reports;

import model.QualifiedObservableReport;

/**
 * Report for when key input is given.
 * @author Ian Keefer & TJ Renninger
 */
public class KeyInputSentReport implements QualifiedObservableReport {
	
	private String input;

	/**
	 * @param input user key input
	 */
	public KeyInputSentReport(String input) {
		this.input = input;
	}

	/**
	 * @return user key input
	 */
	public String getInput() {
		return input;
	}

}
