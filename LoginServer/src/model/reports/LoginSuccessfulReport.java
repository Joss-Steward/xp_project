package model.reports;

import model.QualifiedObservableReport;

/**
 * @author Merlin
 *
 */
public class LoginSuccessfulReport implements QualifiedObservableReport
{

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

	private String hostname;
	/**
	 * @return the hostname
	 */
	public String getHostname()
	{
		return hostname;
	}

	/**
	 * @return the port
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * @return the pin
	 */
	public int getPin()
	{
		return pin;
	}

	private int port;
	private int pin;
	private int userID;

	/**
	 * @param string
	 */
	public LoginSuccessfulReport(int userID, String hostname, int port, int pin)
	{
		this.hostname = hostname;
		this.port = port;
		this.pin = pin;
		this.userID = userID;
	}

	/**
	 * @return
	 */
	public int getUserID()
	{
		return userID;
	}
}
