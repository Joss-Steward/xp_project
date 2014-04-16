package view;

import model.CommandQuestScreenOpen;
import model.ModelFacade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;

import data.ChatType;
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
	PlayerSprite mySprite;
	
	PlayerSpriteFactory playerFactory;
	IntMap<PlayerSprite> characters;

	//holds characters that need to be added until the map is loaded
	IntMap<Position> characterQueue;	

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private final float unitScale;
	private ScreenMapInput mapInput;
	private ChatUi chatArea;
	
	//tile size that we will be moving in according to the collision masking tileset
	private Vector2 tileSize;
	private Vector2 mapPixelSize;
	private Vector2 mapSize;
	
	private BitmapFont loadingFont;
	
	private int[] bgLayers, fgLayers;
	private Color clearColor;
	
	/**
	 * 
	 */
	public ScreenMap()
	{
		// unitScale = 1 / 32f;
		unitScale = 1f;
		batch = new SpriteBatch();
		characters = new IntMap<PlayerSprite>();
		characterQueue = new IntMap<Position>();
		mapInput = new ScreenMapInput();
		
		tileSize = new Vector2(16,16);
		mapSize = new Vector2(1,1);
		mapPixelSize = new Vector2(16,16);
		
		clearColor = new Color(0.7f, 0.7f, 1.0f, 1);
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
		camera.update();
		stage.act();
		stage.draw();

		if (mapRenderer != null && mySprite != null)
		{
			Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
			//insures players will be positioned at the right location when a map is set
			IntMap.Keys ids = this.characterQueue.keys();
			while (ids.hasNext)
			{
				int id = ids.next();
				Position pos = this.characterQueue.remove(id);
				Vector2 where = this.positionToScale(pos);
				PlayerSprite sprite = this.characters.get(id);
				sprite.setPosition(where.x, where.y);
				if (sprite == this.mySprite)
				{
					camera.position.set(where.x, where.y, 0);
				}
			}
			
			mapRenderer.setView(camera);
			mapRenderer.render(bgLayers);
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			for (PlayerSprite s : this.characters.values())
			{
				s.update(delta);
				s.draw(batch);
			}
			batch.end();
			mapRenderer.render(fgLayers);
			
			chatArea.draw(delta);
			
			//have the camera follow the player when moving
			camera.position.set(this.mySprite.getPosition(), 0);
		}
		else
		{
			Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			batch.begin();
			loadingFont.draw(batch, "Loading...", Gdx.graphics.getWidth() - loadingFont.getBounds("Loading...").width - 10, 10 + loadingFont.getLineHeight());
			batch.end();
			// fake load time to test the loading screen's visibility
			//int i = 100000;
			//while (i > 0) i--;
		}
		
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
		camera.setToOrtho(false, width, height);
		camera.update();
		if (mapRenderer != null)
		{
			mapRenderer.setView(camera);
		}
		chatArea.resize(width, height);
		System.out.println(width + " " + height);
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
		
		//determine constant sizing properties of the map
		MapProperties prop = tiledMap.getProperties();
		int mapWidth = prop.get("width", Integer.class);
		int mapHeight = prop.get("height", Integer.class);
		int tilePixelWidth = prop.get("tilewidth", Integer.class);
		int tilePixelHeight = prop.get("tileheight", Integer.class);

		tileSize.set(tilePixelWidth, tilePixelHeight);
		tileSize.scl(unitScale);
		mapSize.set(mapWidth, mapHeight);
		mapPixelSize.set(mapSize.x * tileSize.x, mapSize.y * tileSize.y);
		
		//figure out which layers are foreground and background layers;
		IntArray background = new IntArray();
		IntArray foreground = new IntArray();
		
		MapLayers layers = mapRenderer.getMap().getLayers();
		for (int i = 0; i < layers.getCount(); i++)
		{
			MapLayer layer = layers.get(i);
			if (layer.getName().startsWith("foreground"))
			{
				foreground.add(i);
			}
			else
			{
				background.add(i);
			}
		}
		
		bgLayers = background.toArray();
		fgLayers = foreground.toArray();
		
		String colProp = prop.get("color", String.class);
		System.out.println(colProp);
		if (colProp != null)
		{
			String[] col = colProp.split("[\r\b\t\n ]+");
			clearColor = new Color(Float.parseFloat(col[0]), Float.parseFloat(col[1]), Float.parseFloat(col[2]), 1.0f);
		}
		else
		{
			clearColor.set(0.7f, 0.7f, 1.0f, 1);
		}
	}

	/**
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show()
	{
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		stage = new Stage();
		stage.setCamera(camera);
		
		final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		loadingFont = skin.getFont("default-font");
	
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
		characterQueue.put(playerID, pos);
		characters.put(playerID, sprite);
		// detect when the player being added is the client's player for finer control
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
		Vector2 tmp = new Vector2(pos.getColumn()*tileSize.x, mapPixelSize.y - (pos.getRow()+1)*tileSize.y);
		return tmp;
	}
	
	/**
	 * Adds a chat message from another player to the chat history of the ui
	 * @param message
	 * 	message to add to the ui
	 * @param type
	 *  type of broadcasting of the message
	 */
	public void addChat(String message, ChatType type)
	{
		chatArea.addMessage(message, type);
	}
}
