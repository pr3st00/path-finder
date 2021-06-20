package br.com.fernandoalmeida.pathfinder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * A graphical board implementation
 * 
 * @author Fernando Costa de Almeida
 *
 */
public class TwoDimensionalBoard extends BoardElement {

	private static final String RESULT_MESSAGE_TITLE = "Results";

	JFrame f = new JFrame();
	
	private Image startImage; 
	private Image endImage;
	private Image canBeSearchedImage; 
	private Image cannotBeSearchedImage; 
	private Image currentPathImage;
	
	private static int celwidth = 50;
	
	private String successMessage = "Path found.";
	private String failedMessage = "Path not found.";

	class Component extends JComponent {

		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g1) {
			Graphics2D g = (Graphics2D) g1;

			for (int y = 0; y < getBoardSize(); y++) {
				for (int x = 0; x < getBoardSize(); x++) {
					g.setColor(Color.black);
					g.draw(new Rectangle(10 + x * celwidth, 10 + y * celwidth, celwidth, celwidth));

					if (getCurrentCoordinate().getX() == x && getCurrentCoordinate().getY() == y) {
						g.drawImage(startImage, 10 + x * celwidth, 10 + y * celwidth, null);
					} else if (getCel(new Coordinate(x, y)).isPartofCurrentPath()) {
						g.drawImage(currentPathImage, 10 + x * celwidth, 10 + y * celwidth, null);
					} else if (getCel(new Coordinate(x, y)).isEndPoint()) {
						g.drawImage(endImage, 10 + x * celwidth, 10 + y * celwidth, null);
					} else if (getCel(new Coordinate(x, y)).canBeSearched()) {
						g.drawImage(canBeSearchedImage, 10 + x * celwidth, 10 + y * celwidth, null);
					} else {
						g.drawImage(cannotBeSearchedImage, 10 + x * celwidth, 10 + y * celwidth, null);
					}
				}
			}
		}
	}

	TwoDimensionalBoard(int size, String sucessMessage, String failedMessage) {

		super(size);
		
		this.successMessage = sucessMessage;
		this.failedMessage = failedMessage;

		try {
			InputStream is = getClass().getResourceAsStream("/br/com/fernandoalmeida/pathfinder/resources/chicoh.png");
			startImage = ImageIO.read(is).getScaledInstance(celwidth, celwidth, Image.SCALE_DEFAULT);

			is = getClass().getResourceAsStream("/br/com/fernandoalmeida/pathfinder/resources/privada.png");
			endImage = ImageIO.read(is).getScaledInstance(celwidth, celwidth, Image.SCALE_DEFAULT);

			is = getClass().getResourceAsStream("/br/com/fernandoalmeida/pathfinder/resources/floor.png");
			canBeSearchedImage = ImageIO.read(is).getScaledInstance(celwidth, celwidth, Image.SCALE_DEFAULT);

			is = getClass().getResourceAsStream("/br/com/fernandoalmeida/pathfinder/resources/tree.png");
			cannotBeSearchedImage = ImageIO.read(is).getScaledInstance(celwidth, celwidth, Image.SCALE_DEFAULT);

			is = getClass().getResourceAsStream("/br/com/fernandoalmeida/pathfinder/resources/steps.png");
			currentPathImage = ImageIO.read(is).getScaledInstance(celwidth, celwidth, Image.SCALE_DEFAULT);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(f, e.getMessage(), RESULT_MESSAGE_TITLE, 0);
		}

		Component mycomponent = new Component();
		
		f.add(mycomponent);
		f.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setSize(getBoardSize() * celwidth + 30, getBoardSize() * celwidth + 50);
		f.setVisible(true);
		f.setLocationRelativeTo(null);

	}

	public void draw() {
		f.repaint();
	}

	public void redraw(long delay) {

		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
		}

		draw();
	}

	public void findPathEndingEvent(boolean success) {
		if (success)
			JOptionPane.showMessageDialog(f, successMessage, RESULT_MESSAGE_TITLE, 0);
		else
			JOptionPane.showMessageDialog(f, failedMessage, RESULT_MESSAGE_TITLE, 0);
		f.dispose();
	}

}
