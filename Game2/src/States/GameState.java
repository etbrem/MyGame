package States;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Templates.GameStateManager;


public abstract class GameState {

	protected GameStateManager gsm;

	public abstract void init();

	public abstract void draw(Graphics2D g);

	public abstract void update();

	public abstract void keyPressed(int e);

	public abstract void keyReleased(int e);

}
