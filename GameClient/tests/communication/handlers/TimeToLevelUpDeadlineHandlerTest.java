package communication.handlers;

import static org.junit.Assert.*;



import model.ClientModelFacade;


import org.junit.Before;
import org.junit.Test;


/**
 * @author Evan
 *
 */
public class TimeToLevelUpDeadlineHandlerTest
{

    /**
     * Resets singleton
     */
    @Before
    public void reset()
    {
        ClientModelFacade.resetSingleton();
        ClientModelFacade.getSingleton(true, false);
    }

    /**
     * Test the type of Message that we expect
     */
    @Test
    public void typeWeHandle()
    {
        TimeToLevelUpDeadlineHandler h = new TimeToLevelUpDeadlineHandler();
        assertEquals(TimeToLevelUpDeadlineHandler.class, h.getClass());
    }
    
}
