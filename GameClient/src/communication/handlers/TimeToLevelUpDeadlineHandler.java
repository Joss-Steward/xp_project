package communication.handlers;

import model.QualifiedObservableConnector;
import model.reports.TimeToLevelUpDeadlineReport;
import communication.messages.Message;
import communication.messages.PlayerLeaveMessage;
import communication.messages.TimeToLevelUpDeadlineMessage;

/**
 * @author Evan, Marty, Chris
 *
 */
public class TimeToLevelUpDeadlineHandler extends MessageHandler
{

    /**
     * A player has joined our area server, so notify the PlayerManager of his
     * presence
     * 
     * @see MessageHandler#process(Message)
     */
    @Override
    public void process(Message msg)
    {
        System.out.println("received " + msg);
        if (msg.getClass().equals(PlayerLeaveMessage.class))
        {
            TimeToLevelUpDeadlineMessage realMsg = (TimeToLevelUpDeadlineMessage) msg;
            TimeToLevelUpDeadlineReport report = new TimeToLevelUpDeadlineReport(realMsg.getPlayerID(), realMsg.getTimeToDeadline(), realMsg.getNextLevel());
            QualifiedObservableConnector.getSingleton().sendReport(report);
        }
        
    }

    /**
     * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return TimeToLevelUpDeadlineMessage.class;
    }

}
