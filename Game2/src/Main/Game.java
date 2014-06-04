package Main;

import javax.swing.*;

public class Game {

	//Yet again this is a test.

	public static void main(String[] args) {
		JFrame window = new JFrame("My Game");
		window.add(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
		window.requestFocusInWindow();
	}
}
