/**
 * @TODO: a lot of stuff, check the other TODO's
 */

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

public class Bombafield extends JPanel implements MouseListener {
	private int tileSize;
	private int rows;
	private int columns;

	private Bombawoman woman;
	private LinkedList<Bombabomb> bombs;

	public Bombafield(int width, int height, int tileSize) {
		columns = width;
		rows = height;
		this.tileSize = tileSize;

		woman = new Bombawoman((columns / 2) * tileSize, (rows / 2) * tileSize);
		bombs = new LinkedList<Bombabomb>();

		addMouseListener(this);

		repaint();
	}

	/**
	 * Removes bombs and resets the position of the character, then repaints
	 */
	public void newGame() {
		bombs.clear();

		woman.setPosition((columns / 2) * tileSize, (rows / 2) * tileSize);

		repaint();
	}

	/**
	 * Creates a new bomb and adds it to the bomb-list
	 **/
	public void makeBomb() {
		int posX = woman.getX();
		int posY = woman.getY();

		while(true) {
			int x = (int)(Math.random() * 100) % columns;
			int y = (int)(Math.random() * 100) % rows;

			boolean placeTaken = false;

			// if it's the characters position
			if (x == posX && y == posY) {
				continue;
			}

			// if the position is already taken by another bomb
			for (Bombabomb bomb : bombs) {
				if (bomb.isPosition(x, y)) {
					placeTaken = true;
					break;
				}
			}

			if (!placeTaken) {
				bombs.add(new Bombabomb(x * tileSize, y * tileSize, 1));
				break;
			}
		}
	}

	/**
	 * Check if the given coordinate (x,y) is in a tile that the character 
	 *  may move to (adjacent and not occupied)
	 */
	public boolean checkMovable(int mouseX, int mouseY) {
		// adjusts the mouse-position to the tile-positions
		int x = mouseX - mouseX % tileSize;
		int y = mouseY - mouseY % tileSize;

		int posX = woman.getX();
		int posY = woman.getY();

		// the coordinates for adjacent tiles to the clicked tile
		int leftX = x - tileSize;
		int rightX = x + tileSize;
		int upY = y - tileSize;
		int downY = y + tileSize;

		// if the character is not in the bound created by the 
		//  tile clicked by the mouse
		if (!inBetween(posX, leftX, rightX) ||
			!inBetween(posY, upY, downY)) {
			return false;
		}

		if (occupiedByBomb(x, y)) {
			return false;
		}

		// if the new position is the same as the current position
		if (posX == x && posY == y) {
			return false;
		}

		return true;
	}

	public boolean inBetween(int value, int min, int max) {
		return value >= min && value <= max;
	}

	public boolean occupiedByBomb(int x, int y) {
		for (Bombabomb bomb : bombs)
			if (bomb.isPosition(x, y))
				return true;
		return false;
	}

	public void mouseReleased(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();

		if (checkMovable(mouseX, mouseY)) {
			woman.move(mouseX - mouseX % tileSize, mouseY - mouseY % tileSize);

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
		g.fillRect(0, 0, rows * tileSize, columns * tileSize);
		for (int n = 0 ; n < rows ; n++) {
			for (int i = 0 ; i < columns ; i++) {
				if (checkMovable(i * tileSize, n * tileSize))
					g.setColor(Color.green);
				else
					g.setColor(Color.white);
				g.fillRect(i * tileSize, n * tileSize, tileSize, tileSize);
			}
		}

		g.setColor(Color.black);
		for (int i = 0 ; i <= columns ; i++)
			g.drawLine(0, i * tileSize, columns * tileSize, i * tileSize);
		for (int i = 0 ; i <= rows ; i++)
			g.drawLine(i * tileSize, 0, i * tileSize, rows * tileSize);
	}
}