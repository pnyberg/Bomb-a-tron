/**
 * The bombs for the game
 * Each type has their own customization
 * 
 * @TODO: make explosions better looking
 **/

import java.awt.Color;
import java.awt.Graphics;

public class Bombabomb {
	private final int 	SIZE = 6,
						OFFSET = 4,
						TEXT_OFFSET = 10;

	private int x;
	private int y;
	private int type;
	private int timer;
	private Color color;

	public Bombabomb(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;

		initType(type);
	}

	private void initType(int type) {
		if (type == 1) {
			color = Color.orange;
			timer = 2;
		} else {
			color = Color.blue;
			timer = 4;
		}
	}

	/**
	 * If the method returns true the bomb will be removed from the field
	 */
	public boolean tick() {
		return (--timer) == -1;
	}

	public boolean isPosition(int x, int y) {
		return this.x == x && this.y == y;
	}

	/**
	 * @TODO: nicer way to paint bomb exploded
	 **/ 
	public void paint(Graphics g) {
		if (timer == 0) {
			g.setColor(Color.red);
			g.fillRect(x - 20, y - 20, 20 * 3, 20 * 3);
		}
		g.setColor(color);
		g.fillOval(x + OFFSET, y + OFFSET, SIZE, SIZE);

		if (timer > 0) {
			g.setColor(Color.black);
			g.drawString("" + timer + "", x + TEXT_OFFSET, y + 2 * TEXT_OFFSET - 2);
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}