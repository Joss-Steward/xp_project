package view;

import model.CommandQuestScreenOpen;
import model.ModelFacade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * A basic screen that, for now, just displays the map
 * 
 * @author Merlin
 * 
 */
public class ScreenMap extends ScreenBasic
{
	OrthogonalTiledMapRenderer mapRenderer;

	private OrthographicCamera camera;
	private float unitScale;

	/**
	 * 
	 */
	public ScreenMap()
	{

		stage = new Stage();
		unitScale = 1 / 32f;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camera.update();
		stage.setCamera(camera);
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

	}

}
