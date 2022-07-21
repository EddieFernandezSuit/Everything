package com.eddie.sshooter.Main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eddie.sshooter.Managers.GameInputProcessor;
import com.eddie.sshooter.Managers.GameKeys;
import com.eddie.sshooter.Managers.GameStateManager;

public class MainGame extends ApplicationAdapter {

	public static int WIDTH;
	public static int HEIGHT;

	public static OrthographicCamera camera;
	private GameStateManager gsm;

	@Override
	public void create () {

		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		camera.translate(WIDTH / 2, HEIGHT / 2);
		camera.update();

		Gdx.input.setInputProcessor(new GameInputProcessor());
		gsm = new GameStateManager();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.draw();

		GameKeys.update();
	}

	@Override
	public void dispose () {}
}
