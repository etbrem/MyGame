package Templates;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Animation {

	int currentFrame = 0;
	long startTime;
	int tileSizeX = v.Tile.tileSize;
	int tileSizeY = v.Tile.tileSize;
	int delay;
	boolean playedOnce = false;

	BufferedImage spriteSheet;
	ArrayList<BufferedImage> images;

	public Animation(String loc, int delay) {
		this.delay = delay;
		images = new ArrayList<BufferedImage>();

		try {
			spriteSheet = ImageIO.read(getClass().getResourceAsStream(loc));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// cuts images from loaded spriteSheet starting from desired row until desired end
	// starts at index 0
	public void cutRowUntil(int row, int end) {
		cutImages(row, 0, end);
	}

	public void cutImages(int row, int start, int end) {
		if (end == 0)
			end = (spriteSheet.getWidth() / tileSizeX) - 1;

		for (int i = start; i <= end; i++) {
			images.add(spriteSheet.getSubimage(i * tileSizeX, row * tileSizeY,
					tileSizeX, tileSizeY));
		}
		startTime = System.currentTimeMillis();
	}

	public void setTileSizeX(int ts) {
		tileSizeX = ts;
	}

	public void setTileSizeY(int ts) {
		tileSizeY = ts;
	}

	public void update() {
		long elapsed = System.currentTimeMillis() - startTime;

		if (elapsed > delay) {
			currentFrame++;
			startTime = System.currentTimeMillis();
		}
		if (currentFrame >= images.size()) {
			currentFrame = 0;
			playedOnce = true;
		}
	}

	public void reset() {
		playedOnce = false;
		currentFrame = 0;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public BufferedImage getImage() {
		return images.get(currentFrame);
	}

	public boolean wasPlayed() {
		return playedOnce;
	}
}
