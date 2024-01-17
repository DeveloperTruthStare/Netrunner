package com.smith.netrunner;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.codedisaster.steamworks.SteamAPI;
import com.codedisaster.steamworks.SteamException;
import com.smith.netrunner.RootApplication;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	private static class SteamThread implements Runnable {
		private Thread mainThread;
		public SteamThread(Thread mainThread) {
			this.mainThread = mainThread;
		}

		@Override
		public void run() {
			while (this.mainThread.isAlive()) {
				SteamAPI.runCallbacks();
				try {
					Thread.sleep(1000 / 20);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
	public static boolean steam = false;
	public static void main (String[] arg) {
		if (steam) {
			try {
				SteamAPI.loadLibraries();
				if (!SteamAPI.init()) {
					System.err.println("Could not initialize Steam, steam is most likely not running");
					// Steam is required so, we want to show a popup window
				}
				SteamThread steam = new SteamThread(Thread.currentThread());
				Thread steamThread = new Thread(steam);
				steamThread.start();
			} catch (SteamException e) {

				System.err.println(e.getMessage());
			}
		}

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Netrunning through Time");
		config.setWindowedMode(1920, 1080);
		new Lwjgl3Application(new RootApplication(), config);
	}
}
