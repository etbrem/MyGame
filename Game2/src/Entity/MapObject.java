package Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Levels.Level;
import StatusBar.StatusBar;
import Templates.v;

public abstract class MapObject extends Object {

	
	double dx, dy;
	boolean isPlayer = false;
	boolean meleeAttacking = false, rangeAttacking = false;
	
	double tempSpeedx, tempSpeedy;
	double acceleration, deceleration, maxSpeed;
	
	Rectangle hitbox;
	long attackDelay, rangeAttackStart = 0;
	boolean drawHitboxes;
	int health, maxHealth, meleeDamage, rangeDamage, mana, maxMana,
			manaRecharge;
	long manaRate = -1, lastMana;
	
	ArrayList<Artillery> artillery;
	Rectangle range;
	StatusBar bar;

	public MapObject(Level lvl) {
		super(lvl);
		artillery = new ArrayList<Artillery>();

		drawHitboxes = lvl.drawHitboxes();
	}

	public void updateArtillery() {
		if (artillery != null)
			for (int i = 0; i < artillery.size(); i++) {
				artillery.get(i).update();
				if (artillery.get(i).shouldDelete()) {
					artillery.remove(i);
					i--;
				}
			}
	}

	public void recieveDamage(int d) {
		health -= d;
	}

	public abstract void init();

	public abstract void addAnimations();

	public abstract void meleeAttack();

	public abstract void rangeAttack();

	public void update() {
		anim.get(currentAnim).update();

		getAvailableDirections();
		checkInput();
		move();
		updateHitbox();
		updateAnimation();
		updateArtillery();
		chargeMana();
		if(bar != null)
			bar.update();
	}

	public void chargeMana() {
		if (manaRecharge > 0) {
			if (System.currentTimeMillis() - lastMana > manaRate) {
				if (mana + manaRecharge <= maxMana) {
					mana += manaRecharge;
					lastMana = System.currentTimeMillis();
				} else if (mana + manaRecharge > maxMana)
					mana = maxMana;
			}
		}
	}

	public abstract void updateAnimation();

	

	public void checkInput() {

		decide();

		upL = w && a;
		upR = w && d;
		downL = s && a;
		downR = s && d;

		if (hasRight && d) {
			speedx += acceleration;
			if (speedx > maxSpeed)
				speedx = maxSpeed;
		} else if (hasLeft && a) {
			speedx -= acceleration;
			if (speedx < -maxSpeed)
				speedx = -maxSpeed;
		} else {
			if (speedx > 0)
				speedx -= deceleration;
			else if (speedx < 0)
				speedx += deceleration;
		}
		if (hasUp && w) {
			speedy -= acceleration;
			if (speedy < -maxSpeed)
				speedy = -maxSpeed;
		} else if (hasDown && s) {
			speedy += acceleration;
			if (speedy > maxSpeed)
				speedy = maxSpeed;
		} else {
			if (speedy > 0)
				speedy -= deceleration;
			else if (speedy < 0)
				speedy += deceleration;
		}

		
		
		if (speedx > 0 && !hasRight)
			speedx = 0;
		if (speedx < 0 && !hasLeft)
			speedx = 0;

		if (speedy > 0 && !hasDown)
			speedy = 0;
		if (speedy < 0 && !hasUp)
			speedy = 0;

		tempSpeedx = speedx / v.Val.sqrt2;
		tempSpeedy = speedy / v.Val.sqrt2;

		if (!isAttacking()) {
			if (speedx > 0)
				facingRight = true;
			if (speedx < 0)
				facingRight = false;
		}

		
	}

	public void decide() {

	}

	public void move() {
		// TODO Auto-generated method stub

		if ((upL || upR || downR || downL)) {
			x += tempSpeedx;
			y += tempSpeedy;

			dx = tempSpeedx;
			dy = tempSpeedy;
		} else {
			x += speedx;
			y += speedy;

			dx = speedx;
			dy = speedy;
		}
	}

	public void draw(Graphics2D g) {
		BufferedImage im = anim.get(currentAnim).getImage();
		if (!meleeAttacking) {
			if (facingRight)
				g.drawImage(im, (int) x, (int) y,
						(int) (im.getWidth() * objectScale),
						(int) (im.getHeight() * objectScale), null);
			else
				g.drawImage(im, (int) x + im.getWidth() * 2, (int) y,
						(int) (-im.getWidth() * objectScale),
						(int) (im.getHeight() * objectScale), null);
		} else {
			if (facingRight)
				g.drawImage(im, (int) x - im.getWidth() / 2, (int) y,
						(int) (im.getWidth() * objectScale),
						(int) (im.getHeight() * objectScale), null);
			else
				g.drawImage(im, (int) x + im.getWidth() + im.getWidth() / 2,
						(int) y, (int) (-im.getWidth() * objectScale),
						(int) (im.getHeight() * objectScale), null);
		}
		for (int i = 0; i < artillery.size(); i++)
			artillery.get(i).draw(g);

		if (drawHitboxes) {
			if (hitbox != null)
				g.draw(hitbox);
			if (range != null)
				g.draw(range);
		}
		
		if(bar != null)
			bar.draw(g);
	}

	public void updateHitbox() {
		int tileSize = (int) (v.Tile.tileSize * objectScale);
		int s2 = tileSize / 3;
		hitbox = new Rectangle((int) x + s2 / 2, (int) y + s2 / 2, tileSize
				- s2, tileSize - s2);
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public void setSpeed(double speedX, double speedY) {
		speedx = speedX;
		speedy = speedY;
	}

	public void setLocation(int xx, int yy) {
		x = xx;
		y = yy;
	}


	public int getRangeDamage() {
		return rangeDamage;
	}

	public void removeArtillery(Artillery a) {
		artillery.remove(a);
	}

	public boolean isAttacking() {
		return rangeAttacking || meleeAttacking;
	}

	public boolean shouldRemove() {
		return health <= 0;
	}

	public int getMana() {
		return mana;
	}

	public int getHealth() {
		return health;
	}

	public void restoreMana(int num) {
		mana += num;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}
	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}

	public boolean isPlayer() {
		return isPlayer;
	}
}
