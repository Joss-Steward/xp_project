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
 * Issues movement commands of the client player using keyboard input.
 * 
 * @author nhydock
 */
public class ScreenMapInput implements InputProcessor
{
	PlayerSprite sprite;
	boolean up, down, left, right;
	
	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean keyDown(int keycode)
	{
		CommandMovePlayer cm = null;
		ThisClientsPlayer cp = PlayerManager.getSingleton().getThisClientsPlayer();
		Position position = cp.getPosition();
		Position to;
		switch (keycode)
		{
			case Keys.UP:
				to = Direction.getPositionInDirection(position, North);
				cm = new CommandMovePlayer(cp.getID(), to);
				up = true;
				break;
	
			case Keys.DOWN:
				to = Direction.getPositionInDirection(position, South);
				cm = new CommandMovePlayer(cp.getID(), to);
				down = true;
				break;
	
			case Keys.LEFT:
				to = Direction.getPositionInDirection(position, West);
				cm = new CommandMovePlayer(cp.getID(), to);
				left = true;
				break;
	
			case Keys.RIGHT:
				to = Direction.getPositionInDirection(position, East);
				cm = new CommandMovePlayer(cp.getID(), to);
				right = true;
				break;
		}

		if (cm != null)
			ModelFacade.getSingleton().queueCommand(cm);

		return false;
	}

	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean keyUp(int keycode)
	{
		switch (keycode)
		{
			case Keys.UP:
				up = false;
				break;
	
			case Keys.DOWN:
				down = false;
				break;
	
			case Keys.LEFT:
				left = false;
				break;
	
			case Keys.RIGHT:
				right = false;
				break;
		}
		return false;
	}

	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}

	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}

	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}

	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
	
	/**
	 * Handle held down keys for sending input
	 * @param delta
	 * 	time resolution between frames
	 */
	public void update(float delta)
	{
		if (sprite != null)
		{
			if (sprite.doneWalking())
			{
				CommandMovePlayer cm = null;
				ThisClientsPlayer cp = PlayerManager.getSingleton().getThisClientsPlayer();
				Position position = cp.getPosition();
				Position to;
				if (up)
				{
					to = Direction.getPositionInDirection(position, North);
					cm = new CommandMovePlayer(cp.getID(), to);
				}
				else if (down)
				{
					to = Direction.getPositionInDirection(position, South);
					cm = new CommandMovePlayer(cp.getID(), to);	
				}
				else if (left)
				{
					to = Direction.getPositionInDirection(position, West);
					cm = new CommandMovePlayer(cp.getID(), to);	
				}
				else if (right)
				{
					to = Direction.getPositionInDirection(position, East);
					cm = new CommandMovePlayer(cp.getID(), to);	
				}
				
				if (cm != null)
					ModelFacade.getSingleton().queueCommand(cm);
			}
		}
	}

	/**
	 * Set the sprite of the player in which we're following the state of
	 * @param sprite
	 * 	the player's sprite
	 */
	public void setSprite(PlayerSprite sprite)
	{
		this.sprite = sprite;
	}
}
