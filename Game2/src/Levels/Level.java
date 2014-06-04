package Levels;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Enemy;
import Entity.MapObject;
import Entity.Player;
import Entity.Slugger;
import Map.BackGround;
import Map.TileMap;
import States.GameState;
import Templates.GameInfo;
import Templates.GameStateManager;
import Templates.v;

public class Level extends GameState {

	GameInfo info;

	BackGround bg;
	TileMap tm;
	Player player;
	GameStateManager gm;
	ArrayList<MapObject> enemies;
	
	boolean drawHitboxes = v.Game.drawBoxes;

	public Level(GameStateManager gm, GameInfo in) {
		init();
		this.gm = gm;
		info = in;
	}

	public void init() {
		bg = new BackGround("/Backgrounds/menubg.gif");
		
		tm = new TileMap("/Tilesets/grasstileset.gif","/Maps/map1.map");
		
		player = new Player(this);
				
		enemies = new ArrayList<MapObject>();
		
		player.setLocation(50, 50);
	}

	public void update() {
		player.update();

		info.setMana(player.getMana());
		info.setHealth(player.getHealth());

		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).shouldRemove()) {
				enemies.remove(i);
				i--;
			} else
				enemies.get(i).update();
		}

		info.setEnemies(enemies.size());

		if (bg != null)
			bg.update();
	}

	public void draw(Graphics2D g) {
		if (bg != null)
			bg.draw(g);

		tm.draw(g);

		for (int i = 0; i < enemies.size(); i++)
			enemies.get(i).draw(g);

		player.draw(g);
	}

	public void keyPressed(int e) {
		
	//	if(false)
		switch (e) {
		case KeyEvent.VK_W:
			player.setW(true);
			player.setS(false);
			break;
		case KeyEvent.VK_S:
			player.setS(true);
			player.setW(false);
			break;
		case KeyEvent.VK_D:
			player.setA(false);
			player.setD(true);
			break;
		case KeyEvent.VK_A:
			player.setA(true);
			player.setD(false);
			break;
		case KeyEvent.VK_SPACE:
			player.meleeAttack();
			break;
		case v.Controls.up:
			player.setUp(true);
			player.setDown(false);
			break;
		case v.Controls.down:
			player.setUp(false);
			player.setDown(true);
			break;
		case v.Controls.right:
			player.setLeft(false);
			player.setRight(true);
			break;
		case v.Controls.left:
			player.setRight(false);
			player.setLeft(true);
			break;
		}

		/*if (e == KeyEvent.VK_R)
			loadEnemies(info.getLoadEnemies());*/
	}

/*	public void loadEnemies(int lim){
		for (int i = 0; i < lim; i++) {

			Rectangle[][] blocked = tm.getBlocked();

			int x, y;

			do {
				y = (int) (Math.random() * (blocked.length));
				x = (int) (Math.random() * (blocked[0].length));
			} while (blocked[y][x] != null);

			enemies.add(new Slugger(this));
		//	if(false)
			enemies.get(enemies.size() - 1).setLocation(
					x * v.Tile.tileSize, y * v.Tile.tileSize);
			
			if (i == info.getLoadEnemies() - 1)
				{System.gc();
				System.out.println("cleaned (level.loadEnemies)");
		}}
	}*/
	public void keyReleased(int e) {
		switch (e) {
		case KeyEvent.VK_W:
			player.setW(false);
			break;
		case KeyEvent.VK_S:
			player.setS(false);
			break;
		case KeyEvent.VK_D:
			player.setD(false);
			break;
		case KeyEvent.VK_A:
			player.setA(false);
			break;
		case v.Controls.right:
			player.setRight(false);
			break;
		case v.Controls.left:
			player.setLeft(false);
			break;
		case v.Controls.up:
			player.setUp(false);
			break;
		case v.Controls.down:
			player.setDown(false);
			break;
		}
	}


	public Player getPlayer() {
		return player;
	}

	public ArrayList<MapObject> getEnemies() {
		return enemies;
	}

	public TileMap getTileMap(){
		return tm;
	}
	
	public boolean drawHitboxes(){
		return drawHitboxes;
	}
	
	public GameInfo getInfo(){
		return info;
	}
}
