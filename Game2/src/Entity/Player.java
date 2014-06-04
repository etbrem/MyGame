package Entity;

import java.awt.Rectangle;
import java.util.ArrayList;

import Levels.Level;
import Map.TileMap;
import StatusBar.StatusBar;
import Templates.Animation;
import Templates.v;

public class Player extends MapObject {

	public Player(Level lvl) {
		super(lvl);
		bar = new StatusBar(this, 10, 20, 1);
		init();
	}

	@Override
	public void init() {
		isPlayer = true;
		acceleration = 1;
		deceleration = 0.5;
		maxSpeed = 9;
		objectScale = 2;

		rangeDamage = 100;
		meleeDamage = 500;
		attackDelay = 150;

		health = 100;
		maxHealth = health;
		mana = 800;
		maxMana = mana;
		manaRate = 20;
		manaRecharge = 1;

	}

	@Override
	public void addAnimations() {
		// TODO Auto-generated method stub
		String loc = "/Sprites/Player/playersprites.gif";

		anim.add(new Animation(loc, 700));
		anim.get(0).cutRowUntil(0, 1);
		anim.add(new Animation(loc, 50));
		anim.get(1).cutRowUntil(1, 7);
		anim.add(new Animation(loc, 100));
		// anim.get(2).cutRowUntil(2, 0);
		anim.add(new Animation(loc, 100));
		// anim.get(3).cutRowUntil(3, 1);
		anim.add(new Animation(loc, 100));
		// anim.get(4).cutRowUntil(4, 3);
		anim.add(new Animation(loc, 300));
		anim.get(5).cutRowUntil(5, 1);
		anim.add(new Animation(loc, 100));
		anim.get(6).setTileSizeX(v.Tile.tileSize * 2);
		anim.get(6).cutRowUntil(6, 4);

	}

	public void decide() {
		// if a shoot key is down, then shoot
		if (isUp() || isDown() || isRight() || isLeft())
			rangeAttack();

		lvl.getInfo().setFireballs(artillery.size());
	}

	@Override
	public void updateAnimation() {

		if (speedx == 0 && speedy == 0)
			currentAnim = 0;
		else if (speedx != 0 || speedy != 0)
			currentAnim = 1;

		if (rangeAttacking) {
			currentAnim = v.Player.FIREBALL;
			if (anim.get(currentAnim).wasPlayed()) {
				rangeAttacking = false;
				updateAnimation();
			}
		}
		if (meleeAttacking) {
			currentAnim = v.Player.SCRATCHING;
			if (anim.get(currentAnim).wasPlayed()) {
				meleeAttacking = false;
				updateAnimation();
			}
		}
	}

	@Override
	public void meleeAttack() {
		currentAnim = v.Player.SCRATCHING;

		if (!meleeAttacking) {
			anim.get(currentAnim).reset();
			meleeAttacking = true;
			int s = (int) (v.Tile.tileSize * objectScale);
			int s1 = s;
			if (!facingRight)
				s = -s;
			range = new Rectangle((int) (x + s - s / 1.5), (int) y, s1, s1);

			ArrayList<MapObject> foes = lvl.getEnemies();

			for (int i = 0; i < foes.size(); i++) {
				MapObject e = foes.get(i);
				if (e != null)
					if (range.intersects(e.getHitbox()))
						e.recieveDamage(meleeDamage);
			}
		}
	}

	@Override
	public synchronized void rangeAttack() {
		if (System.currentTimeMillis() - rangeAttackStart > attackDelay) {
			currentAnim = v.Player.FIREBALL;

			if (!rangeAttacking) {
				anim.get(currentAnim).reset();
				rangeAttacking = true;
			}

			if (mana >= v.Player.fireCost) {
				mana -= v.Player.fireCost;
				artillery.add(new FireBall(this));
				rangeAttackStart = System.currentTimeMillis();
			}
			if (right)
				setFacingRight(true);
			else if (left)
				setFacingRight(false);
		}
	}
}
