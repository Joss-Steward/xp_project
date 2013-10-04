package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MapScreen implements BasicScreen
{
	private TiledMap tiledMap;
	OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private Stage stage;

	public MapScreen()
	{
		
		stage = new Stage();
		float unitScale = 1 / 32f;
		tiledMap = new TmxMapLoader().load("maps/current.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camera.update();
		stage.setCamera(camera);
	}
	@Override
	public void render(float delta)
	{
		
		stage.act();
		stage.draw();
		
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

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void show()
	{
		
	}

	@Override
	public void hide()
	{	
	}

	@Override
	public void pause()
	{	
	}

	@Override
	public void resume()
	{
	}

	@Override
	public void dispose()
	{
	}

	@Override
	public Stage getStage()
	{
		return stage;
	}

}
