
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * The most basic gui!
 * @author Merlin
 *
 */
public class GameLibGDX implements ApplicationListener
{
	private TiledMap tiledMap;
	OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;

	/**
	 * 
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	public void create()
	{

		float unitScale = 1 / 32f;
		tiledMap = new TmxMapLoader().load("maps/current.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camera.update();
	}

	/**
	 * @see com.badlogic.gdx.ApplicationListener#render()
	 */
	public void render()
	{

		Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// int[] backgroundLayers = { 0, 1 }; // don't allocate every frame!
		// int[] foregroundLayers = { 2 }; // don't allocate every frame!
		mapRenderer.setView(camera);
		mapRenderer.render();
		// mapRenderer.render(backgroundLayers);
		// renderMyCustomSprites();
		// mapRenderer.render(foregroundLayers);

	}

	/**
	 * @see com.badlogic.gdx.ApplicationListener#resize(int, int)
	 */
	public void resize(int width, int height)
	{
	}

	/**
	 * @see com.badlogic.gdx.ApplicationListener#pause()
	 */
	public void pause()
	{
	}

	/**
	 * @see com.badlogic.gdx.ApplicationListener#resume()
	 */
	public void resume()
	{
	}

	/**
	 * @see com.badlogic.gdx.ApplicationListener#dispose()
	 */
	public void dispose()
	{
	}
}