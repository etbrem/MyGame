package Entity;

import Templates.Animation;

public class FireBall extends Artillery{

	public FireBall(MapObject mo) {
		super(mo);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public void addAnimations() {
		String loc = "/Sprites/Player/fireball.gif";
		anim.add(new Animation(loc,100));
		anim.get(0).cutRowUntil(0, 3);
		anim.add(new Animation(loc,100));
		anim.get(1).cutRowUntil(1, 3);
	}


}
