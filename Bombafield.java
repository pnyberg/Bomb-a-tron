/**
 * @TODO: a lot of stuff, check the other TODO's
 */

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

public class Bombafield extends JPanel implements MouseListener {
	private int fieldSize = 20;
	private int rows = 10;
	private int columns = 10;
	private boolean[][] pressed;
	private Bombawoman woman;
	private LinkedList<Bombabomb> bombs;

	public Bombafield(int width, int height, int tileSize) {
		pressed = new boolean[columns][rows];
		woman = new Bombawoman((int)(columns/2) * fieldSize, (int)(rows/2) * fieldSize);
		bombs = new LinkedList<Bombabomb>();

		addMouseListener(this);

		repaint();
	}

	/**
	 * @TODO: this method should init the game (so it can be played multiple times)
	 */
	public void newGame() {
		System.out.println("INIT");
	}

	/**
	 * @TODO: rewrite this method
	 **/
	public void makeBomb() {
		int woX = woman.getX();
		int woY = woman.getY();
		boolean bool = true;

		while(bool) {
			int x = woX;
			int y = woY;

			while(x == woX && y == woY) {
				x = (int)(Math.random() * 100) % columns;
				y = (int)(Math.random() * 100) % rows;
			}

			bool = false;
			for (Bombabomb bomb : bombs)
				if (bomb.isPosition(x, y))
					bool = true;

			if (!bool) {
				bombs.add(new Bombabomb(x * fieldSize, y * fieldSize, 1));
			}
		}
	}

	/**
	 * @TODO: rewrite this method
	 */
	public boolean checkMovable(int x, int y) {
		// to check their "left-upper"-value
		x = x - x % fieldSize;
		y = y - y % fieldSize;

		int woX = woman.getX();
		int woY = woman.getY();

		return notInBetween(woX, x - fieldSize, x + fieldSize) &&
				notInBetween(woY, y - fieldSize, y + fieldSize) &&
				notOccupiedByBomb(x, y) &&
				(woX != x || woY != y);
	}

	public boolean notInBetween(int value, int min, int max) {
		return value >= min && value <= max;
	}

	public boolean notOccupiedByBomb(int x, int y) {
		for (Bombabomb bomb : bombs)
			if (bomb.isPosition(x, y))
				return false;
		return true;
	}

	public void mouseReleased(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();

		System.out.println(mouseX + "," + mouseY);

		if (checkMovable(mouseX, mouseY)) {
			woman.move(mouseX - mouseX%fieldSize, mouseY - mouseY%fieldSize);

			for (int i = 0 ; i < bombs.size() ; i++)
				if (bombs.get(i).tick()) {
					bombs.remove(i);
					i--;
				}

			makeBomb();
		}

		repaint();
	}

	public void mousePressed(MouseEvent e) { /* do nothing */ }

	public void mouseClicked(MouseEvent e) { /* do nothing */ }

	public void mouseEntered(MouseEvent e) { /* do nothing */ }

	public void mouseExited(MouseEvent e) { /* do nothing */ }

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		paintField(g);

		woman.paint(g);

		for (Bombabomb bomb : bombs)
			bomb.paint(g);
	}

	/**
	 * @TODO: rewrite this method
	 */
	private void paintField(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, rows * fieldSize, columns * fieldSize);
		for (int n = 0 ; n < rows ; n++) {
			for (int i = 0 ; i < columns ; i++) {
				if (checkMovable(i * fieldSize, n * fieldSize))
					g.setColor(Color.green);
				else
					g.setColor(Color.white);
				g.fillRect(i * fieldSize, n * fieldSize, fieldSize, fieldSize);
			}
		}

		g.setColor(Color.black);
		for (int i = 0 ; i <= columns ; i++)
			g.drawLine(0, i * fieldSize, columns * fieldSize, i * fieldSize);
		for (int i = 0 ; i <= rows ; i++)
			g.drawLine(i * fieldSize, 0, i * fieldSize, rows * fieldSize);
	}
}