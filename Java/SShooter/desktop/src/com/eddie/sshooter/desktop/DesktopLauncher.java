package com.eddie.sshooter.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.eddie.sshooter.Main.MainGame;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Sshooter";
		config.width = 800;
		config.height = 480;
		config.foregroundFPS = 60;
		new LwjglApplication(new MainGame(), config);
	}
}
