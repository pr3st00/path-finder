package br.com.fernandoalmeida.pathfinder;

/**
 * Main class
 * 
 * @author Fernando Costa de Almeida
 *
 */
public class PathFinderMain {

	private static final String FAILED_MESSAGE = "Caguei na calca...";
	private static final String SUCCESS_MESSAGE = "Fuuuuuck";
	
	private static int boardsize = 10;

	public static void main(String[] args) {

		BoardElement board;

		if (args.length > 0 && args[0].equals("-t"))
			board = new TextBoard(boardsize);
		else
			board = new TwoDimensionalBoard(boardsize, SUCCESS_MESSAGE, FAILED_MESSAGE);

		board.setEndPoint(new Coordinate((int) (Math.random() * boardsize), (int) (Math.random() * boardsize)));
		board.setStartingPoint(new Coordinate((int) (Math.random() * boardsize), (int) (Math.random() * boardsize)));
		board.randomize(70);

		if (board.hasPath(true))
			System.out.println("Found the path!!!");
		else
			System.out.println("Path not found...");
	}

}
