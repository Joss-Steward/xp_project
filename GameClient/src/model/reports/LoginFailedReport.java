package model.reports;

import model.QualifiedObservableReport;

/**
 * @author Dave, Andy, Matt
 *
 */
public final class LoginFailedReport implements QualifiedObservableReport
{
	private final String errMsg;
	
	/**
	 * @param err - type of login fail error
	 */
	public LoginFailedReport(String err)
	{
		this.errMsg = err;
	}
	
	/**
	 * @return the kind of login error message this is
	 */
	public String toString()
	{
		return errMsg;
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
		LoginFailedReport other = (LoginFailedReport) obj;
		if (errMsg == null)
		{
			if (other.errMsg != null)
				return false;
		} else if (!errMsg.equals(other.errMsg))
			return false;
		return true;
	}
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errMsg == null) ? 0 : errMsg.hashCode());
		
		return result;
	}

}
