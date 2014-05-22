package edu.ship.shipsim.areaserver.model;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.DatabaseTable;

/**
 * This class is used solely for having a shared auto-incrementing id
 * among players and npcs.
 * 
 * @author Josh
 *
 */
@DatabaseTable(tableName = "CharacterID")
public class CharacterIDGenerator
{
	@DatabaseField(generatedId = true)
	private int id;
	
	/**
	 * Can't create an instance publically, only through the sequence generator
	 */
	private CharacterIDGenerator()
	{
		
	}
	
	private static Dao<CharacterIDGenerator, Integer> dao = null;
	/**
	 * 
	 * @return A DAO For Character ID
	 * @throws SQLException when database things go wrong
	 */
	public static Dao<CharacterIDGenerator, Integer> getDao() throws SQLException
	{
		if(dao == null)
		{
			JdbcConnectionSource connectionSource = new JdbcConnectionSource(PlayerManager.DATABASE_URL, "program", "ShipSim");
			dao = DaoManager.createDao(connectionSource, CharacterIDGenerator.class);
		}
		return dao;
	}
	
	/**
	 * Acts as a unique id sequence generator for characters
	 * @return Integer containing the next id in sequence
	 * @throws SQLException when database things go wrong
	 */
	public static int getNextId() throws SQLException 
	{
		Dao<CharacterIDGenerator, Integer> dao = getDao();
		CharacterIDGenerator idObj = new CharacterIDGenerator();
		dao.create(idObj);
		dao.delete(idObj);
		
		return idObj.id;
	}
}
