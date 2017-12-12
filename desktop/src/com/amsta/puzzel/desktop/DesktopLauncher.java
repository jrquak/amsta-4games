package com.amsta.puzzel.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.amsta.puzzel.GdxStart;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GdxStart.V_WIDTH;
		config.height = GdxStart.V_HEIGHT;
		new LwjglApplication(new GdxStart(), config);
	}
}
