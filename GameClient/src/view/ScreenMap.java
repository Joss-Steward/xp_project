package view;

import model.CommandQuestScreenOpen;
import model.ModelFacade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.IntMap;

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
	IntMap<PlayerSprite> characters;
	PlayerSprite mySprite;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private final float unitScale;
	private ScreenMapInput mapInput;
	private ChatUi chatArea;
	
	/**
	 * 
	 */
	public ScreenMap()
	{
		stage = new Stage();
		// unitScale = 1 / 32f;
		unitScale = .5f;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		stage.setCamera(camera);
		batch = new SpriteBatch();
		characters = new IntMap<PlayerSprite>();
		mapInput = new ScreenMapInput();
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
		// TODO once movement is working, uncomment these so the camera follows
		// the player
		// camera.position.x = this.mySprite.getX();
		// camera.position.y = this.mySprite.getY();
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
			for (PlayerSprite s : this.characters.values())
			{
				s.update(delta);
				s.draw(batch);
			}
			batch.end();
			
			chatArea.draw(delta);
		}
		// mapRenderer.render(backgroundLayers);
		// renderMyCustomSprites();
		// mapRenderer.render(foregroundLayers);
		if (Gdx.input.isKeyPressed(Keys.Q))
		{
			System.out.println("quest button is pressed");

			CommandQuestScreenOpen lc = new CommandQuestScreenOpen();
			ModelFacade.getSingleton().queueCommand(lc);
		}
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
		playerFactory = new PlayerSpriteFactory(
				Gdx.files.internal("data/characters.pack"));
		chatArea = new ChatUi();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(mapInput);
		chatArea.addToInput(multiplexer);
		
		Gdx.input.setInputProcessor(multiplexer);
	}

	/**
	 * Adds a new renderable instance of a player, identified by their name, to
	 * the map
	 * 
	 * @param playerID
	 *            the unique identifier of the player being added
	 * @param type
	 *            sprite type visible to others
	 * @param pos
	 *            location to spawn the player
	 * @param isThisClientsPlayer
	 *            if the player to spawn is controlled by this client
	 */
	public void addPlayer(int playerID, PlayerType type, Position pos,
			boolean isThisClientsPlayer)
	{
		PlayerSprite sprite = playerFactory.create(type);
		Vector2 loc = positionToScale(pos);
		sprite.setPosition(loc.x, loc.y);
		characters.put(playerID, sprite);
		// detect when the player being added is the client's player for finer
		// control
		if (isThisClientsPlayer)
		{
			mySprite = sprite;
		}
	}

	/**
	 * Moves a player on the map using the map's unit scaling
	 * 
	 * @param id
	 *            unique player identifier
	 * @param pos
	 *            position to move to
	 */
	public void movePlayer(int id, Position pos)
	{
		PlayerSprite sprite = this.characters.get(id);
		Vector2 loc = positionToScale(pos);
		sprite.move(loc.x, loc.y);
	}

	/**
	 * Scales a position to the proper pixel ratio of the map
	 * 
	 * @param pos
	 *            Position to scale into the proper ratio
	 * @return the scaled position as a Vector
	 */
	private Vector2 positionToScale(Position pos)
	{
	
		float y = Gdx.graphics.getHeight()/16f-pos.getRow()-1;
		System.out.println("height = " + Gdx.graphics.getHeight()/16f + "y = " + y + " row = " + pos.getRow());
		Vector2 tmp = new Vector2(pos.getColumn(), y);
		tmp.scl(16f);
		return tmp;
	}
}
