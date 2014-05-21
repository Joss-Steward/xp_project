package edu.ship.shipsim.areaserver.model.reports;

import model.QualifiedObservableReport;

/**
 * Reports movement of any player playing on this server
 * 
 * @author Matt and Andy
 * 
 */
public final class PinFailedReport implements QualifiedObservableReport
{

private final String errMsg;
	
	/**
	 * @param err - type of pin fail error
	 */
	public PinFailedReport(String err)
	{
		this.errMsg = err;
	}
	
	/**
	 * @return the kind of pin error message this is
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
		PinFailedReport other = (PinFailedReport) obj;
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
