package Templates;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Levels.*;
import States.GameState;
import States.Menu;

public class GameStateManager {

	ArrayList<GameState> list;
	int currentState = 0;
	Level level;
	GameInfo info;

	public GameStateManager(GameInfo in) {
		list = new ArrayList<GameState>();
		info = in;
		level = new Level1(this, info);
		list.add(new Menu(this));
		list.add(level);

		setState(0);
	}

	public void setState(int state) {
		currentState = state;
		list.get(currentState).init();
		System.gc();
		if (v.Game.showClean)
			System.out.println("cleaned (gsm.setState)");
	}

	public void keyPressed(int c) {
		list.get(currentState).keyPressed(c);
	}

	public void keyReleased(int c) {
		list.get(currentState).keyReleased(c);
	}

	public void update() {
		list.get(currentState).update();
	}

	public void draw(Graphics2D g) {
		list.get(currentState).draw(g);
	}

}
