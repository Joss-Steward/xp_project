package view;

import model.CommandMovePlayer;
import model.CommandQuestScreenOpen;
import model.ModelFacade;
import model.PlayerManager;
import model.ThisClientsPlayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import data.Position;

/**
 * A basic screen that, for now, just displays the map
 * 
 * @author Merlin
 * 
 */
public class ScreenMap extends ScreenBasic
{
	OrthogonalTiledMapRenderer mapRenderer;
	PlayerSpriteFactory playerFactory;
	Array<PlayerSprite> characters;
	PlayerSprite mySprite;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private final float unitScale;

	/**
	 * 
	 */
	public ScreenMap()
	{
		stage = new Stage();
		//unitScale = 1 / 32f;
		unitScale = .5f;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		stage.setCamera(camera);
		batch = new SpriteBatch();
		characters = new Array<PlayerSprite>();
	}

	/**
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose()
	{
	}

	/**
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide()
	{
	}

	/**
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause()
	{
	}

	/**
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta)
	{
		// TODO once movement is working, uncomment these so the camera follows the player
		//camera.position.x = this.mySprite.getX();
		//camera.position.y = this.mySprite.getY();
		camera.update();
		stage.act();
		stage.draw();

		Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// int[] backgroundLayers = { 0, 1 }; // don't allocate every frame!
		// int[] foregroundLayers = { 2 }; // don't allocate every frame!
		if (mapRenderer != null)
		{
			mapRenderer.setView(camera);
			mapRenderer.render();
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			for (PlayerSprite s : this.characters) {
				s.update(delta);
				s.draw(batch);
			}
			batch.end();
		}
		// mapRenderer.render(backgroundLayers);
		// renderMyCustomSprites();
		// mapRenderer.render(foregroundLayers);
		CommandMovePlayer cmd = null;
		if (Gdx.input.isKeyPressed(Keys.Q))
		{
			System.out.println("quest button is pressed");

			CommandQuestScreenOpen lc = new CommandQuestScreenOpen();
			ModelFacade.getSingleton().queueCommand(lc);
			
		}else if(Gdx.input.isKeyPressed(Keys.UP))
		{
			cmd=createMoveCommand(Direction.North);
		}else if(Gdx.input.isKeyPressed(Keys.DOWN))
		{
			cmd=createMoveCommand(Direction.South);
		}else if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			cmd=createMoveCommand(Direction.East);
		}else if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			cmd=createMoveCommand(Direction.West);
		}
		if(cmd != null)
		{
			executeMoveCommand(cmd);
		}
	}
	
	private CommandMovePlayer createMoveCommand(Direction dir)
	{
		System.out.println("Created move command " + dir.toString());
		CommandMovePlayer cm;
		ThisClientsPlayer cp = PlayerManager.getSingleton().getThisClientsPlayer();
		Position position = cp.getPosition();
		Position to;
		
		to = Direction.getPositionInDirection(position, dir);
		cm = new CommandMovePlayer(cp.getID(), to);
		
		return cm;
	}
	
	private void executeMoveCommand(CommandMovePlayer command)
	{
		ModelFacade.getSingleton().queueCommand(command);
	}

	/**
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height)
	{
	}

	/**
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume()
	{
	}

	/**
	 * Set the TiledMap we should be drawing
	 * 
	 * @param tiledMap
	 *            the map
	 */
	public void setTiledMap(TiledMap tiledMap)
	{
		System.out.println("updated tile map in the screen " + tiledMap);
		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);
	}

	/**
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show()
	{
		playerFactory = new PlayerSpriteFactory(Gdx.files.internal("data/characters.pack"));
	}

	/**
	 * Adds a new renderable instance of a player, identified by their name, to the map
	 * @param playerID 
	 * 			the unique identifier of the player being added
	 * @param type
	 * 			sprite type visible to others
	 * @param pos
	 * 			location to spawn the player
	 * @param isThisClientsPlayer
	 * 			if the player to spawn is controlled by this client
	 */
	public void addPlayer(int playerID, PlayerType type, Position pos, boolean isThisClientsPlayer) {
		PlayerSprite sprite = playerFactory.create(type);
		sprite.setPosition(pos.getRow(), pos.getColumn());
		characters.add(sprite);
		//detect when the player being added is the client's player for finer control
		if (isThisClientsPlayer) {
			mySprite = sprite;
		}
	}
}
