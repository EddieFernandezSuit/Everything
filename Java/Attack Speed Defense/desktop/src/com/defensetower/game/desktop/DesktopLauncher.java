package com.defensetower.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.defensetower.game.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Attack Speed Defense";
		config.width = 800;
		config.height = 640;
		config.useGL30  = false;
		config.resizable= false;
		new LwjglApplication(new MainGame(), config);
	}
}
