package view.screen.map;

import static view.screen.Screens.DEFAULT_RES;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entry;
import com.badlogic.gdx.utils.Sort;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import datatypes.ChatType;
import datatypes.Crew;
import datatypes.Position;
import model.ClientModelFacade;
import model.CommandKeyInputSent;
import view.player.PlayerSprite;
import view.player.PlayerSpriteFactory;
import view.player.PlayerType;
import view.screen.ScreenBasic;
import view.screen.SkinPicker;
import view.screen.highscore.HighScoreUI;
import view.screen.playerinfo.PlayerInfoUI;
import view.screen.popup.LogoutNotificationBehavior;
import view.screen.popup.PopUpDisplay;
import view.screen.popup.TwoChoiceScreenPopup;
import view.screen.qas.QuestUI;

/**
 * A basic screen that, for now, just displays the map
 * 
 * @author Merlin
 * 
 */
public class ScreenMap extends ScreenBasic
{
	InputMultiplexer multiplexer;
	OrthogonalTiledMapRenderer mapRenderer;
	PlayerSprite mySprite;

	PlayerSpriteFactory playerFactory;
	Stage worldStage;
	IntMap<PlayerSprite> characters;

	// holds characters that need to be added until the map is loaded
	IntMap<Position> characterQueue;
	// holds list of characters that need to be removed after the render cycle
	// is complete
	IntArray characterDequeue;

	private OrthographicCamera camera;
	private final float unitScale;
	private ScreenMapInput mapInput;

	// GUIs that get displayed on the map
	private QuestUI qaScreen;
	// private ExperienceDisplay expDisplay;
	private ChatUi chatArea;
	private HighScoreUI highScoreUI;
	private MenuUI menuArea;
	private PlayerInfoUI playerInfoUI;

	private PopUpDisplay popUpDisplay;

	// tile size that we will be moving in according to the collision masking
	// tileset
	private Vector2 tileSize;
	private Vector2 mapPixelSize;
	private Vector2 mapSize;

	private int[] bgLayers, fgLayers;
	private Color clearColor;

	boolean loading;

	// these frame buffers are used to so we can apply OpenGL Gaussian blur to
	// the UI, giving
	// it an Aero Glass like appearance based on UI alpha levels
	private FrameBuffer mapBuffer;
	private Texture mapTexture;
	private FrameBuffer uiBuffer;
	private Texture uiTexture;
	private ShaderProgram aeroGlass;
	private OrthographicCamera defaultCamera;
	private boolean bufferSet;
	// batch specifically used for post shader effects
	private SpriteBatch blurBatch;
	private Group loadingLayer;
	private OrthographicCamera worldCamera;

	/**
	 * 
	 */
	public ScreenMap()
	{

		// super.setUpListening();

		// unitScale = 1 / 32f;
		unitScale = 1f;
		characters = new IntMap<PlayerSprite>();
		characterQueue = new IntMap<Position>();
		characterDequeue = new IntArray();
		mapInput = new ScreenMapInput();

		tileSize = new Vector2(16, 16);
		mapSize = new Vector2(1, 1);
		mapPixelSize = new Vector2(16, 16);

		clearColor = new Color(0.7f, 0.7f, 1.0f, 1);

		multiplexer = new InputMultiplexer();
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
		multiplexer.clear();
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
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		camera.update();
		stage.act();

		if (!loading)
		{
			Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			mapInput.update(delta);

			// insures players will be positioned at the right location when a
			// map is set
			IntMap.Keys ids = this.characterQueue.keys();
			while (ids.hasNext)
			{
				int id = ids.next();
				Position pos = this.characterQueue.remove(id);
				float[] where = this.positionToScale(pos);
				PlayerSprite sprite = this.characters.get(id);
				sprite.setPosition(where[0], where[1]);
				if (sprite == this.mySprite)
				{
					worldCamera.position.set(where[0], where[1], 0);
				}
				worldStage.addActor(sprite);
			}
			this.characterQueue.clear();

			// make sure the shader effect frame buffers are properly created
			// when needed
			if (!bufferSet)
			{
				mapBuffer = new FrameBuffer(Format.RGBA8888, (int) width, (int) height,
						false);
				mapTexture = mapBuffer.getColorBufferTexture();
				uiBuffer = new FrameBuffer(Format.RGBA8888, (int) width, (int) height,
						false);
				uiTexture = uiBuffer.getColorBufferTexture();
				bufferSet = true;
			}

			// render the map buffer
			mapBuffer.begin();
			{
				// remember to clear in here else the buffer will smear the map
				// where the
				// tiled map renderer doesn't draw
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				mapRenderer.setView(worldCamera);
				mapRenderer.render(bgLayers);

				worldStage.act(delta);
				Sort.instance().sort(worldStage.getActors());
				worldStage.draw();

				mapRenderer.render(fgLayers);
			}
			mapBuffer.end();

			// render the ui buffer
			uiBuffer.begin();
			{
				Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, 0);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				stage.draw();
			}
			uiBuffer.end();

			// use the ui's alpha as our blur mask multiplier
			uiTexture.bind(1);

			// reset glActiveTexture to zero since SpriteBatch doesn't do this
			// for us
			Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);

			// draw the map/blur
			blurBatch.setProjectionMatrix(camera.combined);
			blurBatch.setShader(aeroGlass);
			blurBatch.begin();
			blurBatch.draw(mapTexture, 0f, 0f);
			blurBatch.end();

			// draw the ui
			blurBatch.setShader(null);
			blurBatch.begin();
			blurBatch.draw(uiTexture, 0f, 0f);
			blurBatch.end();

			// have the camera follow the player when moving
			worldCamera.position.set(this.mySprite.getX(), this.mySprite.getY(), 0);

			// insures players are removed at the end of the render cycle to
			// prevent race conditions
			for (int i = 0; i < characterDequeue.size; i++)
			{
				int id = characterDequeue.get(i);
				PlayerSprite s = this.characters.remove(id);
				s.remove();
			}
			characterDequeue.clear();

		}
		// draw a loading screen
		else
		{
			Gdx.input.setInputProcessor(null);
			Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			loadingLayer.setVisible(true);
			stage.act(delta);
			stage.draw();

			if (mapRenderer != null && mySprite != null)
			{
				mapInput.initialize();
				Gdx.input.setInputProcessor(multiplexer);
				loading = false;
				loadingLayer.setVisible(false);
			}
		}
	}

	/**
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height)
	{
		stage.getViewport().update(width, height, true);
		worldStage.getViewport().update(width, height, true);
		if (mapRenderer != null)
		{
			worldCamera.position.set(mySprite.getX(), mySprite.getY(), 0);
			worldCamera.update();
		}
		camera.setToOrtho(true, width, height);
		blurBatch.setProjectionMatrix(defaultCamera.combined);

		// make sure buffers are resized
		bufferSet = false;
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
		loading = true;

		// clear things when changing maps
		if (tiledMap == null)
		{
			System.out.println("clearing tile map");
			for (Entry<PlayerSprite> c : characters)
			{
				c.value.remove();
			}
			characters.clear();
			this.mySprite = null;
			mapInput.setSprite(null);
			mapRenderer = null;
			return;
		}

		System.out.println("updated tile map in the screen " + tiledMap);
		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);

		// determine constant sizing properties of the map
		MapProperties prop = tiledMap.getProperties();
		int mapWidth = prop.get("width", Integer.class);
		int mapHeight = prop.get("height", Integer.class);
		int tilePixelWidth = prop.get("tilewidth", Integer.class);
		int tilePixelHeight = prop.get("tileheight", Integer.class);

		tileSize.set(tilePixelWidth, tilePixelHeight);
		tileSize.scl(unitScale);
		mapSize.set(mapWidth, mapHeight);
		mapPixelSize.set(mapSize.x * tileSize.x, mapSize.y * tileSize.y);

		// figure out which layers are foreground and background layers;
		IntArray background = new IntArray();
		IntArray foreground = new IntArray();

		MapLayers layers = mapRenderer.getMap().getLayers();
		for (int i = 0; i < layers.getCount(); i++)
		{
			MapLayer layer = layers.get(i);
			if (layer.getName().startsWith("foreground"))
			{
				foreground.add(i);
			} else
			{
				background.add(i);
			}
		}

		bgLayers = background.toArray();
		fgLayers = foreground.toArray();

		String colProp = prop.get("color", String.class);
		if (colProp != null)
		{
			String[] col = colProp.split("[\r\b\t\n ]+");
			clearColor = new Color(Float.parseFloat(col[0]), Float.parseFloat(col[1]),
					Float.parseFloat(col[2]), 1.0f);
		} else
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
		// expDisplay = new ExperienceDisplay();

		worldStage = new Stage();
		blurBatch = new SpriteBatch();

		stage = new Stage(new ExtendViewport(DEFAULT_RES[0], DEFAULT_RES[1]));

		popUpDisplay = new PopUpDisplay(stage);

		Gdx.input.setInputProcessor(stage);
		defaultCamera = (OrthographicCamera) stage.getCamera();
		worldStage = new Stage(new ExtendViewport(DEFAULT_RES[0], DEFAULT_RES[1]));
		worldCamera = (OrthographicCamera) worldStage.getCamera();
		camera = new OrthographicCamera();

		final Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

		playerFactory = new PlayerSpriteFactory(
				Gdx.files.internal("data/characters.pack"));
		chatArea = new ChatUi();
		stage.addListener(new InputListener()
		{
			@Override
			public boolean keyDown(InputEvent event, int keycode)
			{
				char ch = getCharForKey(event, keycode);
				CommandKeyInputSent cmd = new CommandKeyInputSent("" + ch);
				ClientModelFacade.getSingleton().queueCommand(cmd);
				if (keycode == Keys.Q) // Key to open the quest/adventure screen
				{
					if (stage.getKeyboardFocus() == null)
					{
						if (!qaScreen.isVisible())
						{
							menuArea.closeAllOverlayingScreens();
						}
						qaScreen.toggleVisibility();
					}
					return true;
				}
				if (keycode == Keys.H) // Key to open the HighScore screen
				{
					if (stage.getKeyboardFocus() == null)
					{
						if (!highScoreUI.isVisible())
						{
							menuArea.closeAllOverlayingScreens();
						}
						highScoreUI.toggleVisibility();
					}

					return true;
				}
				if (keycode == Keys.P) // Key to open the player screen
				{
					if (stage.getKeyboardFocus() == null)
					{
						if (!playerInfoUI.isVisible())
						{
							menuArea.closeAllOverlayingScreens();
						}
						playerInfoUI.toggleVisibility();
					}

					return true;
				}
				if (keycode == Keys.ESCAPE)
				{
					if (stage.getKeyboardFocus() == chatArea.messageBox)
					{
						stage.setKeyboardFocus(null);
					}
					menuArea.closeAllOverlayingScreens();
					return true;
				}
				if (keycode == Keys.C)
				{
					if (stage.getKeyboardFocus() == null)
					{
						chatArea.setVisible(!chatArea.isVisible());
					}
				}
				return false;
			}

			private char getCharForKey(InputEvent event, int keyCode)
			{
				switch (keyCode)
				{
				case Keys.UP:
					return '\u2191';
				case Keys.DOWN:
					return '\u2193';
				case Keys.LEFT:
					return '\u2190';
				case Keys.RIGHT:
					return '\u2192';
				default:
					return event.getCharacter();
				}
			}
		});

		// stage.addActor(expDisplay);
		stage.addActor(chatArea);

		loadingLayer = new Group();
		loadingLayer.setSize(stage.getWidth(), stage.getHeight());
		TextureRegion fillTex = new TextureRegion(new Texture(
				Gdx.files.internal("data/fill.png")));
		Image fill = new Image(new TextureRegionDrawable(fillTex));
		fill.setFillParent(true);
		fill.setSize(stage.getWidth(), stage.getHeight());
		loadingLayer.addActor(fill);
		Label l = new Label("Loading...", skin, "default");
		l.setPosition(10, 10);
		loadingLayer.addActor(l);
		loadingLayer.setVisible(false);
		stage.addActor(loadingLayer);

		multiplexer.addProcessor(mapInput);
		multiplexer.addProcessor(stage);

		// prepare the shader
		// create the Blur Shader for our pretty ui
		aeroGlass = new ShaderProgram(
				Gdx.files.classpath("util/shaders/Aero.vertex.glsl"),
				Gdx.files.classpath("util/shaders/Aero.fragment.glsl"));
		if (!aeroGlass.isCompiled())
		{
			System.err.println(aeroGlass.getLog());
			System.exit(0);
		}
		if (aeroGlass.getLog().length() != 0)
		{
			System.out.println(aeroGlass.getLog());
		}
		aeroGlass.begin();
		aeroGlass.setUniformi("u_mask", 1);
		aeroGlass.end();
		blurBatch.setShader(aeroGlass);

		loading = true;
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
		System.out.println("type is " + type.regionName);
		System.out.println("position is " + pos.toString());
		PlayerSprite sprite = playerFactory.create(type);
		characterQueue.put(playerID, pos);
		characters.put(playerID, sprite);
		// detect when the player being added is the client's player for finer
		// control
		if (isThisClientsPlayer)
		{
			mySprite = sprite;
			mapInput.setSprite(mySprite);
		}
	}

	/**
	 * Adds all the UIs that are dependent on the player type to the the map
	 * 
	 * @param crew
	 *            of UIs specific to a player
	 */
	public void addUIs(Crew crew)
	{
		SkinPicker.getSkinPicker().setCrew(crew.name());
		qaScreen = new QuestUI();
		highScoreUI = new HighScoreUI();
		playerInfoUI = new PlayerInfoUI();
		menuArea = new MenuUI();
		menuArea.addOverlayingScreenToggle(qaScreen, "Q");
		menuArea.addOverlayingScreenToggle(highScoreUI, "H");
		menuArea.addOverlayingScreenToggle(playerInfoUI, "P");
		menuArea.addButton("Logout", logoutClickListener());
		stage.addActor(highScoreUI);
		stage.addActor(playerInfoUI);
		stage.addActor(qaScreen);
		stage.addActor(menuArea);
	}

	/**
	 * Creates the method behind the logoutClickListener
	 * 
	 * @return the click Listener with the required behavior
	 */
	private ClickListener logoutClickListener()
	{
		return new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				popUpDisplay.addWaitingPopUp(new TwoChoiceScreenPopup(
						"Are you sure you want to logout?", "Yes", "No", stage,
						new LogoutNotificationBehavior(), null));
				popUpDisplay.showNextPopUp();
			}
		};
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
		if (sprite != null)
		{
			float[] loc = positionToScale(pos);
			sprite.move(loc[0], loc[1]);
		}
	}

	/**
	 * Scales a position to the proper pixel ratio of the map
	 * 
	 * @param pos
	 *            Position to scale into the proper ratio
	 * @return the scaled position as a float array
	 */
	private float[] positionToScale(Position pos)
	{
		float[] tmp =
		{ pos.getColumn() * tileSize.x, mapPixelSize.y - (pos.getRow() + 1) * tileSize.y };
		return tmp;
	}

	/**
	 * Adds a chat message from another player to the chat history of the ui
	 * 
	 * @param message
	 *            message to add to the ui
	 * @param type
	 *            type of broadcasting of the message
	 */
	public void addChat(String message, ChatType type)
	{
		chatArea.addMessage(message, type);
	}

	/**
	 * Removes a player from this display
	 * 
	 * @param playerID
	 *            the player's unique identifying code
	 */
	public void removePlayer(int playerID)
	{
		PlayerSprite s = this.characters.remove(playerID);
		if (s != null)
		{
			s.remove();
		}
		// characterDequeue.add(playerID);
	}
}
