package communication.packers;

import java.util.ArrayList;

import model.PlayerManager;
import model.QualifiedObservableReport;
import model.reports.TeleportOnQuestCompletionReport;

import communication.messages.Message;
import communication.messages.TeleportationContinuationMessage;

import datasource.DatabaseException;

/**
 * Packer telling the client to teleport on quest completion
 * @author Zach Thompson, Chris Hershey, Abdul 
 *
 */
public class TeleportOnQuestCompletionPacker extends MessagePacker 
{

    /** 
     * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
     */
    @Override
    public Message pack(QualifiedObservableReport object)
    {
        if(object.getClass().equals(TeleportOnQuestCompletionReport.class))
        {
            TeleportOnQuestCompletionReport report = (TeleportOnQuestCompletionReport)object;
            try
            {
               TeleportationContinuationMessage msg = new  TeleportationContinuationMessage(report.getMapName(), report.getHostName(),
                        report.getPortNumber(), report.getPlayerID(), PlayerManager.getSingleton().getNewPinFor(report.getPlayerID()));
                return msg;
            }
            catch (DatabaseException e)
            {
                System.out.println("Unable to set up the completion info for teleporting");
                e.printStackTrace();
            }
        }
        return null;
    }

    /** 
     * @see communication.packers.MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
    {
    	ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<Class<? extends QualifiedObservableReport>>();
    	result.add(TeleportOnQuestCompletionReport.class);
        return result;
    }

}
