package Templates;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

// GameInfo is a window with information about the game which could prove valuable for development.

public class GameInfo extends JFrame {

	JLabel fps, ups, loops, enemies;
	JLabel fireballs, health, mana;
	JTextField loadEnemies;
	int loaden = 1;

	public GameInfo() {
		super("Game Information");

		setLayout(new FlowLayout());

		fps = new JLabel("FPS");
		ups = new JLabel("UPS");
		loops = new JLabel("LPS");
		enemies = new JLabel("Number of enemies");
		fireballs = new JLabel("Number of fireballs");
		health = new JLabel("Health");
		mana = new JLabel("Mana");
		loadEnemies = new JTextField("540", 5);

		add(fps);
		add(ups);
		add(loops);
		add(enemies);/*
					 * add(health); add(mana);
					 */
		add(fireballs);
		add(loadEnemies);

		setFocusable(false);
		setVisible(v.Game.devMode);
		setSize(200, 800);
		setLocation(v.Screen.width * v.Screen.scale + 15, 0);
	}

	public void setFPS(int num) {
		fps.setText("FPS: " + num);
	}

	public void setUPS(int num) {
		ups.setText("UPS: " + num);
	}

	public void setEnemies(int num) {
		enemies.setText("Number of enemies: " + num);
	}

	public void setFireballs(int num) {
		fireballs.setText("Number of fireballs: " + num);
	}

	public void setMana(int num) {
		mana.setText("Mana: " + num);
	}

	public void setHealth(int num) {
		health.setText("Health: " + num);
	}

	public int getLoadEnemies() {
		try {
			loaden = Integer.parseInt(loadEnemies.getText());
			if (loaden < 0)
				loaden = 1;
		} catch (Exception e) {
			loaden = 1;
		}
		loadEnemies.setText("" + loaden);

		return loaden;
	}

	public void setLPS(int num) {
		loops.setText("LPS: " + num);
	}
}
