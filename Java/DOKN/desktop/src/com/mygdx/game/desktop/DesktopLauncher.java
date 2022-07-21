package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.DOKN;

import static com.mygdx.game.DOKN.HEIGHT;
import static com.mygdx.game.DOKN.WIDTH;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "DOKN";
		config.width = 1056;
		config.height = 480;
		config.resizable = false;
		new LwjglApplication(new DOKN(), config);
	}
}
