package view.screen.map;

import model.CommandClientMovePlayer;
import model.ClientModelFacade;
import model.ClientPlayerManager;
import model.ThisClientsPlayer;
import view.player.Direction;
import view.player.PlayerSprite;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import datatypes.Position;
import static view.player.Direction.*;

/**
 * Issues movement commands of the client player using keyboard input.
 * 
 * @author nhydock
 */
public class ScreenMapInput implements InputProcessor
{
	PlayerSprite sprite;
	boolean up, down, left, right;
	float delay;
	
	/**
	 * @seecom.badlogic.gdx.InputProcessor
	 */
	@Override
	public boolean keyDown(int keycode)
	{
		if (sprite == null) {
//			return false;
		}
		
		//System.out.println("Key down received: " + keycode);
		CommandClientMovePlayer cm = null;
		ThisClientsPlayer cp = ClientPlayerManager.getSingleton().getThisClientsPlayer();
		Position position = cp.getPosition();
		Position to;
		switch (keycode)
		{
			case Keys.UP:
				to = Direction.getPositionInDirection(position, North);
				cm = new CommandClientMovePlayer(cp.getID(), to);
				up = true;
				break;
	
			case Keys.DOWN:
				to = Direction.getPositionInDirection(position, South);
				cm = new CommandClientMovePlayer(cp.getID(), to);
				down = true;
				break;
	
			case Keys.LEFT:
				to = Direction.getPositionInDirection(position, West);
				cm = new CommandClientMovePlayer(cp.getID(), to);
				left = true;
				break;
	
			case Keys.RIGHT:
				to = Direction.getPositionInDirection(position, East);
				cm = new CommandClientMovePlayer(cp.getID(), to);
				right = true;
				break;
				
		}

		if (cm != null )//&& sprite != null && sprite.doneWalking()) 
		{
			ClientModelFacade.getSingleton().queueCommand(cm);
		}

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
			delay += delta;
			if (delay > PlayerSprite.MOVESPEED)
			{
				CommandClientMovePlayer cm = null;
				ThisClientsPlayer cp = ClientPlayerManager.getSingleton().getThisClientsPlayer();
				Position position = cp.getPosition();
				Position to;
				if (up)
				{
					to = Direction.getPositionInDirection(position, North);
					cm = new CommandClientMovePlayer(cp.getID(), to);
				}
				else if (down)
				{
					to = Direction.getPositionInDirection(position, South);
					cm = new CommandClientMovePlayer(cp.getID(), to);	
				}
				else if (left)
				{
					to = Direction.getPositionInDirection(position, West);
					cm = new CommandClientMovePlayer(cp.getID(), to);	
				}
				else if (right)
				{
					to = Direction.getPositionInDirection(position, East);
					cm = new CommandClientMovePlayer(cp.getID(), to);	
				}
				
				if (cm != null)
					ClientModelFacade.getSingleton().queueCommand(cm);
				
				delay = 0f;
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

	/**
	 * Initializes key held input values of the input processor
	 */
	public void initialize() 
	{
		up = false;
		down = false;
		left = false;
		right = false;
	}
}
