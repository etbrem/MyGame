package States;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Map.BackGround;
import Templates.GameStateManager;
import Templates.v;

public class Menu extends GameState {

	GameStateManager gm;
	BackGround bg;
	boolean unhide, unhide2;
	int selected = 0;
	String[] option = { "Play", "Help", "Exit" };

	public Menu(GameStateManager gm) {
		this.gm = gm;
		bg = new BackGround("/Backgrounds/menubg.gif", 0.7);
	}

	public void update() {
		if (bg != null)
			bg.update();
	}

	public void draw(Graphics2D g) {
		if (unhide)
			option[1] = "Stop being such a pussy";
		else
			option[1] = "Help";

		if (unhide2)
			option[0] = "Maybe if you stop being such a faggot";
		else
			option[0] = "Play";

		bg.draw(g);

		g.setColor(Color.black);
		g.setFont(new Font("Verdana", Font.BOLD, 24));
		g.drawString("Itamar's Game",
				v.Screen.width / 2 * v.Screen.scale - 100, 100);

		g.setFont(new Font("TimesNewRoman", Font.BOLD, 16));

		for (int i = 0; i < option.length; i++) {
			if (i == selected)
				g.setColor(Color.YELLOW);
			else
				g.setColor(Color.RED);
			g.drawString(option[i], v.Screen.width / 2 * v.Screen.scale - 50,
					200 + i * 15);
		}
		if (unhide)
			g.drawString("fuck off", 200, 200);

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int e) {
		// TODO Auto-generated method stub
		if (e == KeyEvent.VK_ENTER) {
			switch (selected) {
			case 0:
				if (!unhide)
					gm.setState(1);
				else {
					unhide2 = true;
				}
				break;
			case 1:
				unhide = !unhide;
				if (!unhide)
					unhide2 = false;
				break;
			case 2:
				System.exit(0);
				break;
			}
			System.gc();
			if (v.Game.showClean)
				System.out.println("cleaned (menu.keyPressed(enter))");
		}
	}

	@Override
	public void keyReleased(int e) {
		// TODO Auto-generated method stub
		if (e == v.Controls.down)
			selected++;
		if (e == v.Controls.up)
			selected--;

		if (selected < 0)
			selected = option.length - 1;
		if (selected >= option.length)
			selected = 0;
		if (e == KeyEvent.VK_ENTER)
			keyPressed(e);
	}

}
