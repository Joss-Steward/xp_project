package model.reports;

import model.QualifiedObservableReport;

/**
 * @author Merlin
 * 
 */
public class LoginInitiatedReport implements QualifiedObservableReport
{

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		LoginInitiatedReport other = (LoginInitiatedReport) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null)
		{
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	private String name;
	private String password;

	/**
	 * @param name
	 * @param password
	 */
	public LoginInitiatedReport(String name, String password)
	{
		this.name = name;
		this.password = password;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

}
