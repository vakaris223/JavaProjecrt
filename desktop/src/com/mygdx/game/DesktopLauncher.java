package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import com.mygdx.game.game;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

    public static void main (String[] arg) {
		SetupWindow();
	}

    public static void SetupWindow()
    {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("GameEngine"); // Set the window title
        //config.setIdleFPS(144);
        config.setMaximized(true);
        new Lwjgl3Application(new game(), config);
    }

	
}
