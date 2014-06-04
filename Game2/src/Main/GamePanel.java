package Main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;

import Templates.GameInfo;
import Templates.GameStateManager;
import Templates.v;

public class GamePanel extends Canvas implements Runnable, KeyListener {

	static Thread thread;
	static GameStateManager gsm;
	static GameInfo info;

	static int frameCount, updateCount, loop;
	static boolean running = false, paused = false, first = true;

	static final boolean printInput = false;

	public GamePanel() {
		super();
		setPreferredSize(new Dimension(v.Screen.width * v.Screen.scale,
				v.Screen.height * v.Screen.scale));
		setFocusable(true);
		setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		/*
		 * if(System.currentTimeMillis() - lastPress < pressDelay) return;
		 * lastPress = System.currentTimeMillis();
		 */
		int last = e.getKeyCode();
		{
			if (!paused)
				gsm.keyPressed(last);
			else if (last == KeyEvent.VK_R)
				gsm.keyPressed(last);

			if (last == KeyEvent.VK_ESCAPE)
				System.exit(0);
			if (last == KeyEvent.VK_P) {
				paused = !paused;
				if (paused)
					first = true;
			}

		}

		if (printInput)
			System.out.println("Pressed: " + e.getKeyChar());
	}

	public boolean arrowKey(int e) {
		int[] arrows = { KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP,
				KeyEvent.VK_DOWN };

		for (int i : arrows)
			if (e == i)
				return true;
		return false;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int last = e.getKeyCode();
		gsm.keyReleased(last);

		if (printInput)
			System.out.println("Released: " + e.getKeyChar());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (printInput)
			System.out.println("Typed: " + e.getKeyChar());
	}

	@Override
	public void run() {
		init();

		TimerTask updateInfo = new TimerTask() {
			public void run() {
				task();
			}

			public synchronized void task() {
				info.setFPS(frameCount);
				info.setUPS(updateCount);
				info.setLPS(loop);
				frameCount = 0;
				updateCount = 0;
				loop = 0;
			}
		};

		Timer t = new Timer();
		t.scheduleAtFixedRate(updateInfo, 1000, 1000);
		System.gc();
		if (v.Game.showClean)
			System.out.println("cleaned (before gameLoop)");

		gameLoop();
	}

	public synchronized void gameLoop() {
		double deltaU = 0;
		double deltaF = 0;
		final double upns = 1000000000 / v.Game.UPS;
		final double fpns = 1000000000 / v.Game.FPS;
		long elapsed = System.nanoTime(), start = System.nanoTime();
		boolean updated = false;

		while (running) {

			updated = false;

			if (paused && first) {
				System.gc();
				first = false;
				if (v.Game.showClean)
					System.out.println("cleaned (paused)");
			}

			start = System.nanoTime();
			deltaU += (start - elapsed) / upns;
			deltaF += (start - elapsed) / fpns;
			elapsed = start;

			{

				while (deltaU >= 1) {
					deltaU--;
					if (!paused) {
						update();
						updated = true;
						updateCount++;
					}
				}

				if (updated)
					while (deltaF >= 1) {
						deltaF--;
						draw();
						frameCount++;
					}
				loop++;
			}

		}
	}

	public void init() {

		running = true;

		info = new GameInfo();

		gsm = new GameStateManager(info);

		super.requestFocus();

		System.gc();
	}

	public void drawToScreen() {
		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();

		gsm.draw(g2);

		g2.dispose();

		bs.show();
	}

	public void addNotify() {
		if (thread == null) {
			super.addNotify();
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	public void update() {
		gsm.update();
	}

	public void draw() {
		/*
		 * g = (Graphics2D) image.getGraphics(); gsm.draw(g);
		 */
		drawToScreen();
	}
}
