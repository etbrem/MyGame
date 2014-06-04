package Entity;

import Levels.Level;

public abstract class Enemy extends MapObject {

	long lastMelee = 0;
	boolean stuck;
	
	public Enemy(Level lvl) {
		super(lvl);
		// TODO Auto-generated constructor stub

	}

	@Override
	public void meleeAttack() {
		// TODO Auto-generated method stub
		if (System.currentTimeMillis() - lastMelee > attackDelay) {
			if (hitbox.intersects(lvl.getPlayer().hitbox))
				lvl.getPlayer().recieveDamage(meleeDamage);
			lastMelee = System.currentTimeMillis();
		}
	}

	public void update() {
		anim.get(currentAnim).update();

		getAvailableDirections();
		checkInput();
		move();

		updateHitbox();
		meleeAttack();
		updateAnimation();
		updateArtillery();
	}


	public abstract void decide();

}
