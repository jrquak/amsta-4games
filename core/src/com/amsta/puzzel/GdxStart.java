package com.amsta.puzzel;

import com.amsta.puzzel.manager.InputManager;
import com.amsta.puzzel.manager.ScreenManager;
import com.amsta.puzzel.manager.SoundManager;
import com.amsta.puzzel.screens.MainMenu;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * The type Gdx start.
 */
public class GdxStart extends Game {
	private SpriteBatch batch;
	private ScreenManager manager;

	private StretchViewport viewport;

	/**
	 * The constant camera.
	 */
	public static OrthographicCamera camera;

	/**
	 * The constant V_WIDTH.
	 */
	public final static int V_WIDTH = 1024;
	/**
	 * The constant V_HEIGHT.
	 */
	public final static int V_HEIGHT = 1366;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		MainMenu mainMenu = new MainMenu();
		mainMenu.initialize();
		manager = ScreenManager.getInstance();
		manager.changeScreen(mainMenu);

		camera = new OrthographicCamera();
		viewport = new StretchViewport(V_WIDTH, V_HEIGHT, camera);
		camera.translate(V_WIDTH / 2, V_HEIGHT / 2);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.3f, 0.5f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		InputManager.getInstance().update();
		manager.update();

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		manager.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
	}


	@Override
	public void dispose()
	{
		ScreenManager.getInstance().dispose();
		SoundManager.getInstance().dispose();
	}
}
