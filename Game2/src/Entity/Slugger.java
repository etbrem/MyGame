package Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Levels.Level;
import StatusBar.StatusBar;
import Templates.Animation;
import Templates.v;

public class Slugger extends Enemy {

	public Slugger(Level lvl) {
		super(lvl);
		meleeDamage = 5;
		attackDelay = 300;

		acceleration = 0.5;
		maxSpeed = 3;
		health = 200;
		maxHealth = health;
		bar = new StatusBar(this,(int)x,(int)y,objectScale/2);
	}

	@Override
	public void init() {
	}

	@Override
	public void addAnimations() {
		String loc = "/Sprites/Enemies/slugger.gif";
		anim.add(new Animation(loc, 100));
		anim.get(0).cutRowUntil(0, 2);
	}

	@Override
	public void rangeAttack() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAnimation() {
		anim.get(currentAnim).update();
	}

	@Override
	public void decide() {
		Player player =  lvl.getPlayer();

		w = false;
		a = false;
		s = false;
		d = false;

		stuck = !hasDown||!hasRight||!hasLeft||!hasUp;
		
		if(player.dx != 0 || player.dy != 0)
			stuck = false;
		
		if(!stuck){
		if (x - player.getX() > 0)
			a = true;
		if (x - player.getX() < 0)
			d = true;

		if (y - player.getY() < 0)
			s = true;
		if (y - player.getY() > 0)
			w = true;

		}
		
	/*	if (w && !hasUp) {
			if (speedx == 0)
				speedx = maxSpeed;
		}
		else if (s && !hasDown) {
			if (speedx == 0)
				speedx = -maxSpeed;
		}
		if (a && !hasLeft) {
			if (speedy == 0)
				speedy = -maxSpeed;
		}
		else if (d && !hasRight) {
			if (speedy == 0)
				speedy = maxSpeed;
		}*/
		
		if (!hasUp) {
			if (speedx == 0)
				speedx = maxSpeed;
		}
		else if (!hasDown) {
			if (speedx == 0)
				speedx = -maxSpeed;
		}
		if (!hasLeft) {
			if (speedy == 0)
				speedy = -maxSpeed;
		}
		else if (!hasRight) {
			if (speedy == 0)
				speedy = maxSpeed;
		}

		if(!hasUp&&!hasRight){
			speedy = maxSpeed;
		}
		if(!hasDown&&!hasRight){
			speedx = -maxSpeed;
		}
		
		bar.update();
	}

	@Override
	public void updateHitbox() {
		int tileSize = (int) (v.Tile.tileSize * objectScale);
		int s = tileSize / 2;
		int s2 = tileSize / 3;
		if (facingRight)
			hitbox = new Rectangle((int) x + s / 2 + s2 / 2, (int) y + s2 / 2,
					tileSize - s2, tileSize - s2);
		else
			hitbox = new Rectangle((int) x - s - s / 2 + s2 / 2 + tileSize,
					(int) y + s2 / 2, tileSize - s2, tileSize - s2);
	}

	public void draw(Graphics2D g) {
		BufferedImage im = anim.get(currentAnim).getImage();
		int s = im.getWidth() / 2;
		if (facingRight)
			g.drawImage(im, (int) x + s / 2, (int) y, null);
		else
			g.drawImage(im, (int) x - s + im.getWidth() * 2 - s / 2, (int) y,
					-im.getWidth(), im.getHeight(), null);

		if (drawHitboxes)
			if (hitbox != null)
				g.draw(hitbox);
		
		bar.draw(g);
	}
}
