package view;

import model.CommandMovePlayer;
import model.ModelFacade;
import model.PlayerManager;
import model.ThisClientsPlayer;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import data.Position;
import static view.Direction.*;

/**
 * Issues movement commands of the client player using
 * keyboard input.
 * @author nhydock
 */
public class ScreenMapInput implements InputProcessor {

	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean keyDown(int keycode) {
		CommandMovePlayer cm = null;
		ThisClientsPlayer cp = PlayerManager.getSingleton().getThisClientsPlayer();
		Position position = cp.getPosition();
		Position to;
		switch (keycode){
			case Keys.UP:
				to = Direction.getPositionInDirection(position, North);
				cm = new CommandMovePlayer(cp.getID(), to);
				break;
				
			case Keys.DOWN:
				to = Direction.getPositionInDirection(position, South);
				cm = new CommandMovePlayer(cp.getID(), to);
				break;
				
			case Keys.LEFT:
				to = Direction.getPositionInDirection(position, West);
				cm = new CommandMovePlayer(cp.getID(), to);
				break;
				
			case Keys.RIGHT:
				to = Direction.getPositionInDirection(position, East);
				cm = new CommandMovePlayer(cp.getID(), to);
				break;
		}
		
		if (cm != null)
			ModelFacade.getSingleton(false, false).queueCommand(cm);
		
		return false;
	}

	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
