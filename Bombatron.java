/**
 * The frame on which the game is placed
 * The button inits a new game
 *
 * @TODO: center game-field
 **/

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Bombatron extends JFrame implements ActionListener {
	private final int 	WIDTH = 10,
						HEIGHT = 10,
						TILE_SIZE = 20;

	private JPanel buttonPanel;
	private JButton startButton;
	private Bombafield field;

	public Bombatron() {
		buttonPanel = new JPanel();
		startButton = new JButton("Start new game");
		field = new Bombafield(WIDTH, HEIGHT, TILE_SIZE);

		startButton.addActionListener(this);

		buttonPanel.add(startButton);

		add(field); // TODO - put in the center
		add(buttonPanel, BorderLayout.SOUTH);

		setSize(30 + TILE_SIZE * WIDTH, 50 + TILE_SIZE * HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			field.newGame();
		}
	}

	public static void main(String[] arg) {
		new Bombatron();
	}
}