package com.defensetower.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.defensetower.game.managers.GameInputProcessor;
import com.defensetower.game.managers.GameKeys;
import com.defensetower.game.managers.GameStateManager;

public class MainGame extends ApplicationAdapter {
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
		//cam.translate(WIDTH / 2, HEIGHT / 2);
		cam.update();
		Gdx.input.setInputProcessor(new GameInputProcessor());
		Jukebox.load("sounds/shotgun.mp3", "shotgun");
		Jukebox.load("sounds/fancySound.mp3", "fancy");
		Jukebox.load("sounds/katen.mp3", "katen");
		Jukebox.load("sounds/bow.mp3", "bow");
		Jukebox.load("sounds/peacock.mp3", "peacock");
		gsm = new GameStateManager();



	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.draw();
		GameKeys.update();
	}

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
	public void dispose () {

	}
}
