package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import datasource.DatabaseException;

/**
 * @author Merlin
 * 
 */
public class DatabaseManager
{
	private static DatabaseManager singleton;

	/**
	 * @return the only one
	 * @throws DatabaseException
	 *             if we are unable to connect to the db
	 */
	public static DatabaseManager getSingleton() throws DatabaseException
	{
		if (singleton == null)
		{
			singleton = new DatabaseManager();
		}
		return singleton;
	}

	private Connection connection;
	private boolean testing;

	private DatabaseManager() throws DatabaseException
	{
		openConnectionTo("jdbc:mysql://shipsim.cbzhjl6tpflt.us-east-1.rds.amazonaws.com:3306/Players");
		testing = false;
	}

	/**
	 * @throws DatabaseException
	 *             if we can't complete the commit
	 * 
	 */
	public void commit() throws DatabaseException
	{
		try
		{
			if (!testing)
			{
				connection.commit();
				connection.setSavepoint();
			} else
			{
				connection.rollback();
			}
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to commit changes ", e);
		}
	}

	/**
	 * @return the connection to the db
	 */
	public Connection getConnection()
	{
		return connection;
	}

	private void openConnectionTo(String url) throws DatabaseException
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		try
		{
			connection = DriverManager.getConnection(url, "program", "ShipSim");
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to connect to database ", e);
		}
	}

	/**
	 * Roll back the current transaction
	 * 
	 * @throws SQLException
	 *             if the rollback fails
	 */
	public void rollBack() throws SQLException
	{
		Statement stmt = connection.createStatement();
		stmt.execute("ROLLBACK");
		connection.setAutoCommit(true);
	}

	/**
	 * When we are testing, use a different db
	 * 
	 * @throws DatabaseException
	 *             if we can't connect
	 */
	public void setTesting() throws DatabaseException
	{
		startTransaction();
		testing = true;
	}

	/**
	 * remember a rollback point in case a series of transactions doesn't all
	 * work
	 * 
	 * @throws DatabaseException
	 *             if the save point cannot be created
	 */
	public void startTransaction() throws DatabaseException
	{
		try
		{
			connection.setAutoCommit(false);
			Statement stmt = connection.createStatement();
			stmt.execute("START TRANSACTION");
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to start transation ", e);
		}
	}

	/**
	 * For testing purposes only
	 */
	public static void reset()
	{
		try
		{
			if (singleton != null)
			{
				singleton.rollBack();
				singleton.connection.close();
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		singleton = null;
	}

}
