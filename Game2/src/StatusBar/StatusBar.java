package StatusBar;

import java.awt.Color;
import java.awt.Graphics2D;

import Templates.v;

import Entity.MapObject;

public class StatusBar {

	StatusComponent health, mana;
	MapObject mo;
	double rescale = 1;

	public StatusBar(MapObject mo, int x, int y, double rescale) {
		this.mo = mo;
		this.rescale = rescale;

		if (mo.getMaxHealth() > 0)
			health = new StatusComponent("Health: ", mo.getMaxHealth(),
					Color.RED, x, y);

		int newY = (int) (y + health.height * rescale);

		if (mo.isPlayer())
			newY = (int) (y + health.height * 1.5);

		if (mo.getMaxMana() > 0)
			mana = new StatusComponent("Mana: ", mo.getMaxMana(), Color.BLUE,
					x, newY);

		if (health != null)
			health.setRescale(rescale);
		if (mana != null)
			mana.setRescale(rescale);
	}

	public void update() {
		if (health != null) {
			health.setCurrentValue(mo.getHealth());
			if(!mo.isPlayer())
				health.setLocation(mo.getX()-v.Tile.tileSize/2, mo.getY()+10);
		}
		if (mana != null) {
			mana.setCurrentValue(mo.getMana());
			if(!mo.isPlayer())
				mana.setLocation(mo.getX()-v.Tile.tileSize/2, mo.getY()+10+health.height*rescale);
		}
	}

	public void draw(Graphics2D g) {
		if (health != null)
			health.draw(g);
		if (mana != null)
			mana.draw(g);
	}

	public StatusComponent getHealth() {
		return health;
	}

	public StatusComponent getMana() {
		return mana;
	}

}
