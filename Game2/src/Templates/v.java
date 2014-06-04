package Templates;

import java.awt.event.KeyEvent;


// this v for (V)alues class contains subclasses with all final variables needed for the game
// e.g Screen height, desired FPS,...

public class v {

	public class Game {
		public static final int UPS = 65;
		public static final int FPS = 65;
		public static final boolean devMode = true;
		public static final boolean drawBoxes = false;
		public static final boolean showClean = false;
		public static final int pressDelay = 400;
	}

	public static class Controls {
		public static final int left = KeyEvent.VK_NUMPAD1;
		public static final int up = KeyEvent.VK_NUMPAD5;
		public static final int right = KeyEvent.VK_NUMPAD3;
		public static final int down = KeyEvent.VK_NUMPAD2;
	}

	public static class Screen {
		public final static int width = 450;
		public final static int height = 240;
		public final static int scale = 2;

		public final static int barHeight = 15;
		public final static int barWidth = 100;
	}

	public static class Tile {
		public static final int NORMAL = 0;
		public final static int BLOCKED = 1;
		public final static int[] BLOCKEDTILES = { 22 };
		public final static int tileSize = 30;
	}

	public static class World {
		public static final double artilleryBoost = 12 * 65 / (double) v.Game.UPS;
	}

	public static class Player {
		public final static int playerScale = 2;
		public final static int fireCost = 20;

		public static final int IDLE = 0;
		public static final int WALKING = 1;
		public static final int JUMPING = 2;
		public static final int FALLING = 3;
		public static final int GLIDING = 4;
		public static final int FIREBALL = 5;
		public static final int SCRATCHING = 6;
	}
	public static class Val{

		public static final double sqrt2 = Math.sqrt(2);
	}
}
