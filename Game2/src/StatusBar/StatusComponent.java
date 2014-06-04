package StatusBar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Templates.v;

public class StatusComponent {

	Color c;
	String type, st = "";
	double maxValue, curValue;
	int x, y;
	int width, height;
	double rescale = 1;

	public StatusComponent(String type, int max, Color co, int x, int y) {
		c = co;
		maxValue = max;
		this.type = type;
		this.x = x;
		this.y = y;

		height = v.Screen.barHeight;
		width = (int) maxValue;
	}

	public void setRescale(double num) {
		rescale = num;
	}

	public void setCurrentValue(int num) {
		curValue = num;
		st = (int) curValue + "/" + (int) maxValue;
	}

	public void draw(Graphics2D g) {
		double scale = curValue / maxValue;
		double size = width * scale * rescale;

		Rectangle bar = new Rectangle(x, y, (int) (size*rescale),
				(int) (height * rescale));

		g.setColor(c);
		g.fill(bar);
		 {
			g.setColor(Color.YELLOW);
			g.setFont(new Font("TimesNewRoman", Font.PLAIN, (int) (12*rescale*2)));
			g.drawString(st, (int) (x + size *rescale/4), (int) (y + height*rescale));
		}
	}

	public void setLocation(double xx, double yy) {
		x = (int) xx;
		y = (int) yy;
	}
}
