package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.base.*;

public class DOKN extends ApplicationAdapter {

	public static int WIDTH;
	public static int HEIGHT;

	public static OrthographicCamera cam;
	private GameStateManager gsm;
	
	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		cam = new OrthographicCamera();
		cam.setToOrtho(false, WIDTH, HEIGHT);
		cam.update();

		Gdx.input.setInputProcessor(new GameInputProcessor());
		com.mygdx.game.base.Jukebox.load("hitmarker.mp3", "hitmarker.mp3");
		com.mygdx.game.base.Jukebox.load("bow.mp3", "kat");
		gsm = new GameStateManager();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.draw();
		GameKeys.update();
	}
	
	@Override
	public void dispose () {
	}
}
