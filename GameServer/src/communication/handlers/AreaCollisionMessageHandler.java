package communication.handlers;

import communication.messages.AreaCollisionMessage;
import communication.messages.Message;
import edu.ship.shipsim.areaserver.model.CommandAreaCollision;
import edu.ship.shipsim.areaserver.model.ModelFacade;

public class AreaCollisionMessageHandler extends MessageHandler
{

	@Override
	public void process(Message msg) {
		if(msg.getClass().equals(AreaCollisionMessage.class)){
			AreaCollisionMessage acMsg = (AreaCollisionMessage) msg;
			
			CommandAreaCollision cmd = 
					new CommandAreaCollision(acMsg.getPlayerID(), acMsg.getAreaName());
			
			ModelFacade.getSingleton().queueCommand(cmd);
		}		
	}

	@Override
	public Class<?> getMessageTypeWeHandle() {
		return AreaCollisionMessage.class;
	}

}
