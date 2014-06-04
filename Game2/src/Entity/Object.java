package Entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Levels.Level;
import Map.TileMap;
import Templates.Animation;
import Templates.v;

public abstract class Object {

	ArrayList<Animation> anim;
	int currentAnim;
	TileMap tm;
	Level lvl;
	boolean w, a, s, d;
	boolean up, right, left, down;
	boolean upR, upL, downR, downL;
	boolean hasRight, hasLeft,hasUp,hasDown;
	double x, y;
	boolean facingRight = true;
	double speedx, speedy;
	double objectScale = 1;
	boolean drawHitboxes = v.Game.drawBoxes;
	
	public Object(Level lvl) {
		this.lvl = lvl;
		this.tm = lvl.getTileMap();
		
		
		anim = new ArrayList<Animation>();


		init();
		addAnimations();

		hasRight = true;
		hasLeft = true;
		hasUp = true;
		hasDown = true;
	}

	public abstract void updateAnimation();
	
	public abstract void addAnimations();

	public abstract void init();
	
	public abstract void update();
	
	public abstract void move();
	
	public abstract void updateHitbox();
	
	public void getAvailableDirections() {
		tm.availableDirections(this);
	}
	
	public BufferedImage getCurrentImage() {
		return anim.get(currentAnim).getImage();
	}

	public double getScale() {
		return objectScale;
	}

	public Level getLevel() {
		return lvl;
	}


	public boolean isW() {
		return w;
	}

	public void setW(boolean w) {
		this.w = w;
	}

	public boolean isA() {
		return a;
	}

	public void setA(boolean a) {
		this.a = a;
	}

	public boolean isS() {
		return s;
	}

	public void setS(boolean s) {
		this.s = s;
	}

	public boolean isD() {
		return d;
	}

	public void setD(boolean d) {
		this.d = d;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	

	public boolean isHasLeft() {
		return hasLeft;
	}

	public void setHasLeft(boolean hasLeft) {
		this.hasLeft = hasLeft;
	}

	public boolean isHasRight() {
		return hasRight;
	}

	public void setHasRight(boolean hasRight) {
		this.hasRight = hasRight;
	}

	public boolean isHasUp() {
		return hasUp;
	}

	public void setHasUp(boolean hasUp) {
		this.hasUp = hasUp;
	}

	public boolean isHasDown() {
		return hasDown;
	}

	public void setHasDown(boolean hasDown) {
		this.hasDown = hasDown;
	}

	public void setFacingRight(boolean b) {
		// if (!meleeAttacking && !rangeAttacking)
		facingRight = b;
	}

}
