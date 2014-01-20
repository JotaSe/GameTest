package com.me.mygdxgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jotase.mygame.screens.gamescreens.GameTest;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "my Game Test";
		cfg.useGL20 = false;
		cfg.width = 480*2;
		cfg.height = 320*2;
				
		new LwjglApplication(new GameTest(), cfg);
	}
}
