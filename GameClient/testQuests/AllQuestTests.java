import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import Quest.QuestLayerObjectReaderTest;
import Quest.QuestLayerReaderTest;
import Quest.QuestManagerTest;
import Quest.QuestSystemLargeTest;
import Quest.QuestTest;
import Quest.TaskTest;
import Quest.TriggerTest;

/**
 * @author Joshua
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{ 
		// Quest system Tests
		QuestSystemLargeTest.class, 
		QuestTest.class, 
		TaskTest.class,
		TriggerTest.class, 
		QuestManagerTest.class,
		QuestLayerObjectReaderTest.class,
		QuestLayerReaderTest.class,

})
public class AllQuestTests
{

}
