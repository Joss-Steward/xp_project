package model;

import java.io.Serializable;

import model.OptionsManager;
import datasource.DatabaseException;
import datasource.NPCQuestionRowDataGateway;
import datasource.NPCQuestionRowDataGatewayMock;
import datasource.NPCQuestionRowDataGatewayRDS;

/**
 * NPCQuestion class that utilizes ORMLite's DAO to store and retrieve NPCQuestions from the database.
 * 
 * Will return a random question from the database with getRandomQuestion()
 * 
 * @author Ga and Frank
 *
 */
public class NPCQuestion implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private NPCQuestionRowDataGateway gateway;

	/**
	 * @return a random question from the data source
	 * @throws DatabaseException if the data source can't complete the request
	 */
	public static NPCQuestion getRandomQuestion() throws DatabaseException
	{
		NPCQuestion q = new NPCQuestion();
		if (OptionsManager.getSingleton().isTestMode())
		{
			q.gateway = NPCQuestionRowDataGatewayMock.findRandomGateway();
		} else
		{
			q.gateway = NPCQuestionRowDataGatewayRDS.findRandomGateway();
		}
		return q;
	}
	
	private NPCQuestion()
	{
	}

	/**
	 * 
	 * @return
	 * 			answer to question
	 */
	public String getAnswer()
	{
		return gateway.getAnswer();
	}

	/**
	 * 
	 * @return
	 * 			question
	 */
	public String getQuestionStatement()
	{
		return gateway.getQuestionStatement();
	}

}
