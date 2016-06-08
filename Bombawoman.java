/**
 * The character of the game
 * SIZE and OFFSET defines the appearance of the game
 **/

import java.awt.Color;
import java.awt.Graphics;

public class Bombawoman {
	private final int SIZE = 16;
	private final int OFFSET = 2;

	private int x;
	private int y;
	private Color color;

	public Bombawoman(int x, int y) {
		color = Color.black;

		setPosition(x, y);
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(x + OFFSET, y + OFFSET, SIZE, SIZE);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}