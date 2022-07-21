package com.eddie.playground.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.neet.eddie.playground.PlaygroundGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Asteroids";
		config.width = 500;
		config.height = 400;
		config.useGL30  = false;
		config.resizable= false;

		new LwjglApplication(new PlaygroundGame(), config);
	}
}
