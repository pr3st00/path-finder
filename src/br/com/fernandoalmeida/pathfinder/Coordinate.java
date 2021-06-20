package br.com.fernandoalmeida.pathfinder;

/**
 * A given X/Y coordinate
 * 
 * @author Fernando Costa de Almeida
 *
 */
public class Coordinate implements Comparable<Coordinate> {

	private int x;
	private int y;
	
	private BoardElement board;

	public Coordinate(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Coordinate(int x, int y, BoardElement board) {
		super();
		this.x = x;
		this.y = y;
		this.board = board;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setBoard(BoardElement board) {
		this.board = board;
	}

	public BoardElement getBoard() {
		return board;
	}

	public double distanceTo(Coordinate c) {
		return (Math.sqrt(Math.pow(this.getX() - c.getX(), 2) + Math.pow(this.getY() - c.getY(), 2)));
	}

	public int compareTo(Coordinate c) {
		if (getBoard() == null)
			return 0;
		if (this.distanceTo(getBoard().getDestinationCoordinate()) < c
				.distanceTo(getBoard().getDestinationCoordinate()))
			return -1;
		else
			return 1;
	}

}
