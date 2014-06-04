package Map;

import java.awt.image.BufferedImage;

import Templates.v;

public class Tile {

	int type;
	BufferedImage image;
	int num;
	public Tile(BufferedImage image, int num) {
		this.image = image;
		this.num = num;
		decideType();
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getType() {
		return type;
	}

	public void decideType(){
		if(isBlocked())
			type = v.Tile.BLOCKED;
		else type = v.Tile.NORMAL;
	}
	public boolean isBlocked(){		
		for(int i=0;i<v.Tile.BLOCKEDTILES.length;i++)
			if (num == v.Tile.BLOCKEDTILES[i])return true;
		return false;
	}
}
