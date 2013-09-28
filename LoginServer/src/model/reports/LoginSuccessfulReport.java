package model.reports;

import model.QualifiedObservableReport;

/**
 * @author Merlin
 *
 */
public final class LoginSuccessfulReport implements QualifiedObservableReport
{

	private final String hostname;
	private final int port;
	private final int pin;
	private final int userID;

	/**
	 * @param userID the userID who was successful
	 * @param hostname the hostname of the area server the client should connect to
	 * @param port the port number of the area server the client should connect to
	 * @param pin the pin the client should use in its connection
	 * 
	 */
	public LoginSuccessfulReport(int userID, String hostname, int port, int pin)
	{
		this.hostname = hostname;
		this.port = port;
		this.pin = pin;
		this.userID = userID;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginSuccessfulReport other = (LoginSuccessfulReport) obj;
		if (hostname == null)
		{
			if (other.hostname != null)
				return false;
		} else if (!hostname.equals(other.hostname))
			return false;
		if (pin != other.pin)
			return false;
		if (port != other.port)
			return false;
		if (userID != other.userID)
			return false;
		return true;
	}

	/**
	 * @return the hostname
	 */
	public String getHostname()
	{
		return hostname;
	}
	/**
	 * @return the pin
	 */
	public int getPin()
	{
		return pin;
	}
	/**
	 * @return the port
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * @return the userID in this report
	 */
	public int getUserID()
	{
		return userID;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
		result = prime * result + pin;
		result = prime * result + port;
		result = prime * result + userID;
		return result;
	}
}
