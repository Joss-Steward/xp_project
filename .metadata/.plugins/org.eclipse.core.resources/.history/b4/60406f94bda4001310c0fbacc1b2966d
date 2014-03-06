package view;

import model.CommandQuestScreenOpen;
import model.ModelFacade;
import model.PlayerManager;
import model.reports.ThisPlayerConnectedToAreaServerReport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

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
		if (Gdx.input.isKeyPressed(Keys.Q))
		{
			System.out.println("quest button is pressed");

			CommandQuestScreenOpen lc = new CommandQuestScreenOpen();
			ModelFacade.getSingleton(false).queueCommand(lc);
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
		playerFactory = new PlayerSpriteFactory(Gdx.files.internal("data/characters.pack"));

		// FIXME forcibly send the thisplayerconnectedreport so we can have visual feedback until it is implemented
		ThisPlayerConnectedToAreaServerReport report = new ThisPlayerConnectedToAreaServerReport(
					PlayerManager.getSingleton().getThisClientsPlayer().getName(), 
					PlayerType.MALEA.toString());
		Screens.MAP_SCREEN.getScreenListener().update(null, report);
		
		this.mySprite.setPosition(32, 32);
	}

	/**
	 * Adds a new renderable instance of a player, identified by their name, to the map
	 * @param playerName
	 * 			name of the player being added
	 * @param type
	 * 			sprite type visible to others
	 * @return The sprite created if successfully added
	 */
	public PlayerSprite addPlayer(String playerName, PlayerType type) {
		PlayerSprite sprite = playerFactory.create(type);
		characters.add(sprite);
		
		return sprite;
	}
}
