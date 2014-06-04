package Map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Templates.v;

public class BackGround {

	double dx;
	double x = 0;
	BufferedImage image;

	public BackGround(String loc) {
		this(loc, 0);
	}

	public BackGround(String loc, double dx) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(loc));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dx = dx;

		System.gc();
		if (v.Game.showClean)
			System.out.println("cleaned (Background constructor)");
	}

	public void update() {
		x += dx;

		if ((int) x >= (v.Screen.width * v.Screen.scale))
			x = 0;
	}

	public void draw(Graphics2D g) {
		// g.drawImage(image,0,0,null);

		g.drawImage(image, (int) x, 0, v.Screen.width * v.Screen.scale,
				v.Screen.height * v.Screen.scale, null);

		if (x > 0)
			g.drawImage(image, (int) x - v.Screen.width * v.Screen.scale, 0,
					v.Screen.width * v.Screen.scale, v.Screen.height
							* v.Screen.scale, null);
		else if (x < 0)
			g.drawImage(image, (int) x + v.Screen.width * v.Screen.scale, 0,
					v.Screen.width * v.Screen.scale, v.Screen.height
							* v.Screen.scale, null);
	}

}
