package Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Levels.Level;
import Map.TileMap;
import Templates.Animation;
import Templates.v;

public abstract class Artillery extends Object {

	MapObject sender;
	boolean playerShot;
	boolean hitWall = false, hitFoe = false;
	String loc;
	Level level;
	ArrayList<MapObject> foes;
	boolean delete = false;
	double boost = v.World.artilleryBoost;
	int rangeDamage;
	Rectangle hitbox;
	boolean rangeAttacking;
	
	double tempSpeedx,tempSpeedy;

	public Artillery(MapObject mo) {
		super(mo.getLevel());

		foes = new ArrayList<MapObject>();

		sender = mo;

		if (!sender.isPlayer) {
			if (foes.size() == 0)
				foes.add(lvl.getPlayer());
		} else
			foes = lvl.getEnemies();
		
		objectScale = 1;
		calcInfo();
	}

	public void update() {
		anim.get(currentAnim).update();

		getAvailableDirections();
		checkInput();
		move();
		updateHitbox();
		updateAnimation();
	}

	public void updateHitbox() {
		int tileSize = (int) (v.Tile.tileSize * objectScale);
		int s2 = tileSize / 3;
		hitbox = new Rectangle((int) x + s2 / 2, (int) y + s2 / 2, tileSize
				- s2, tileSize - s2);
	}
	public void calcInfo() {
		// TODO Auto-generated method stub

		facingRight = sender.facingRight;
		rangeDamage = sender.getRangeDamage();

		up = sender.isUp();
		down = sender.isDown();
		right = sender.isRight();
		left = sender.isLeft();

		if (up) {
			upL = up && left;
			upR = up && right;
		} else if (down) {
			downL = down && left;
			downR = down && right;
		}

		speedx = 0;
		speedy = 0;

		if (right) {
			speedx = boost;
		} else if (left) {
			speedx = -boost;
		}

		if (up) {
			speedy = -boost;
		} else if (down) {
			speedy = boost;
		}

		tempSpeedx = speedx / v.Val.sqrt2;
		tempSpeedy = speedy / v.Val.sqrt2;

		int va = (int) (v.Tile.tileSize / 2 * (sender.objectScale));

		va -= va / 2;

		x = sender.getX() + va;
		y = sender.getY() + va;

		if (speedx == 0 && speedy == 0)
			delete = true;

		 updateHitbox();
	}

	public void checkInput() {
		if (speedx > 0 && !hasRight)
			hitWall = true;
		if (speedx < 0 && !hasLeft)
			hitWall = true;
		if (speedy > 0 && !hasDown)
			hitWall = true;
		if (speedy < 0 && !hasUp)
			hitWall = true;

		

		for (int i = 0; i < foes.size(); i++) {
			MapObject mo = foes.get(i);
			if (mo.getHitbox() != null)
				if (hitbox.intersects(mo.getHitbox())) {
					if (hitWall || hitFoe)
						break;
					mo.recieveDamage(rangeDamage);
					hitFoe = true;
				}
		}
	}

	@Override
	public void init() {
	}

	@Override
	public abstract void addAnimations();

	public void setLocation(String lo) {
		loc = lo;
	}

	@Override
	public void updateAnimation() {
		if (hitWall || hitFoe) {
			rangeAttack();
			if (anim.get(currentAnim).wasPlayed())
				delete = true;
		}
	}

	

	public void rangeAttack() {
		if (!rangeAttacking) {
			rangeAttacking = true;
			currentAnim = 1;
			anim.get(currentAnim).reset();
		}
	}

	public boolean shouldDelete() {
		return delete;
	}

	public void move() {
		if (currentAnim == 0) {
			if ((upL || upR || downR || downL)) {
				x += tempSpeedx;
				y += tempSpeedy;

			} else {
				x += speedx;
				y += speedy;

				
			}
		} else {
			if (speedx > 0)
				x += boost / 8;
			if (speedx < 0)
				x -= boost / 8;
			if (speedy > 0)
				y += boost / 8;
			if (speedy < 0)
				y -= boost / 8;
		}

	}

	public void draw(Graphics2D g) {
		BufferedImage im = anim.get(currentAnim).getImage();

		int v = (int) (im.getWidth() * objectScale);

		g.drawImage(im, (int) x, (int) y, (int) v, (int) v, null);

		if (drawHitboxes)
			g.draw(hitbox);
	}

}
