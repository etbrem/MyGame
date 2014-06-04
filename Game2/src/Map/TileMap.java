package Map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import Entity.MapObject;
import Entity.Object;
import Templates.v;

public class TileMap {

	BufferedImage tileSet;
	int[][] map;
	int numTilesX, numTilesY;
	Tile[][] tileMap;
	ArrayList<Tile> tiles;
	Rectangle[][] blocked;
	ArrayList<Rectangle> toDraw;

	boolean drawCollisionBoxes = v.Game.drawBoxes;

	public TileMap(String location, String loc) {
		tiles = new ArrayList<Tile>();
		toDraw = new ArrayList<Rectangle>();
		try {
			tileSet = ImageIO.read(getClass().getResourceAsStream(location));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		loadMap(loc);
		loadTiles();
		loadTileMap();

		System.gc();
		if (v.Game.showClean)
			System.out.println("cleaned (TileMap constructor)");
	}

	private void loadMap(String loc) {
		Scanner x, y;
		String tempx = "", tempy = "";
		y = new Scanner(getClass().getResourceAsStream(loc));

		String st = y.nextLine();
		numTilesX = Integer.parseInt(st);

		st = y.nextLine();
		numTilesY = Integer.parseInt(st);

		map = new int[numTilesY][numTilesX];
		blocked = new Rectangle[numTilesY][numTilesX];

		for (int yy = 0; yy < numTilesY; yy++) {
			tempy = y.nextLine();
			x = new Scanner(tempy);
			for (int xx = 0; xx < numTilesX; xx++) {
				tempx = x.next();
				map[yy][xx] = Integer.parseInt(tempx);
			}
		}
		y.close();
	}

	public void loadTiles() {
		int tileSize = v.Tile.tileSize;

		int tilesAcross = tileSet.getWidth() / tileSize;
		int numOfColumns = tileSet.getHeight() / tileSize;

		int done = 0;
		BufferedImage subimage;

		for (int i = 0; i < numOfColumns; i++)
			for (int j = 0; j < tilesAcross; j++) {
				subimage = tileSet.getSubimage(j * tileSize, i * tileSize,
						tileSize, tileSize);

				Tile tile = new Tile(subimage, done);

				tiles.add(tile);

				done++;
			}

	}

	private void loadTileMap() {
		tileMap = new Tile[numTilesY][numTilesX];
		int tileSize = v.Tile.tileSize;

		for (int i = 0; i < numTilesY; i++)
			for (int j = 0; j < numTilesX; j++) {
				tileMap[i][j] = tiles.get(map[i][j]);
				if (tileMap[i][j].getType() == v.Tile.BLOCKED)
					blocked[i][j] = (new Rectangle(j * tileSize, i * tileSize,
							tileSize, tileSize));
			}
	}

	public void availableDirections(Object object) {
		Rectangle up;
		Rectangle down;
		Rectangle left;
		Rectangle right;

		int tileSize = (int) (v.Tile.tileSize * object.getScale());

		int x, y;

		x = (int) object.getX();
		y = (int) object.getY();

		int s = tileSize / 5;

		up = new Rectangle(x + s, y, tileSize - 2 * s, s);
		down = new Rectangle(x + s, y + tileSize - s, tileSize - 2 * s, s);
		left = new Rectangle(x, y + s, s, tileSize - s - tileSize / 4);
		right = new Rectangle(x + tileSize - s, y + s, s, tileSize - s
				- tileSize / 4);

		if (drawCollisionBoxes) {
			toDraw.add(up);
			toDraw.add(down);
			toDraw.add(left);
			toDraw.add(right);
		}

		object.setHasDown(true);
		object.setHasUp(true);
		object.setHasRight(true);
		object.setHasLeft(true);

		x = x / v.Tile.tileSize;
		y = y / v.Tile.tileSize;

		int lim = (int) object.getScale();
		if (object.getScale() > lim)
			lim++;
		lim++;

		for (int i = y; i < y + lim; i++)
			for (int j = x; j < x + lim; j++) {
				if (i < 0 || j < 0)
					continue;
				if (i >= numTilesY || j >= numTilesX)
					continue;
				Rectangle temp = blocked[i][j];
				if (temp == null)
					continue;

				if (right.intersects(temp))
					object.setHasRight(false);
				if (left.intersects(temp))
					object.setHasLeft(false);
				if (up.intersects(temp))
					object.setHasUp(false);
				if (down.intersects(temp))
					object.setHasDown(false);
			}
	}

	public void drawCollisionBoxes(Graphics2D g) {
		for (int y = 0; y < numTilesY; y++)
			for (int x = 0; x < numTilesX; x++)
				if (blocked[y][x] != null)
					g.draw(blocked[y][x]);

		g.setColor(Color.blue);
		for (int i = 0; i < toDraw.size(); i++)
			g.draw(toDraw.get(i));

	}

	public void draw(Graphics2D g) {
		g.setColor(Color.yellow);
		for (int y = 0; y < numTilesY; y++)
			for (int x = 0; x < numTilesX; x++)
				g.drawImage(tileMap[y][x].getImage(), x * v.Tile.tileSize, y
						* v.Tile.tileSize, null);

		if (drawCollisionBoxes) {
			drawCollisionBoxes(g);

			toDraw = new ArrayList<Rectangle>();
		}
	}

	public Rectangle[][] getBlocked() {
		return blocked;
	}

}
