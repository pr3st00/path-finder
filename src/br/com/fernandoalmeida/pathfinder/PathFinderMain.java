/**
 * 
 */
package br.com.fernandoalmeida.pathfinder;

/**
 * @author pr3st00
 *
 */
public class PathFinderMain {
	
	private static int boardsize = 10;

	public static void main(String[] args) {

		BoardElement board;

		if (args.length > 0 && args[0].equals("-t"))
			board = new TextBoard(boardsize);
		else 
			board = new TwoDimensionalBoard(boardsize,"Fuuuuuck","Caguei na calca...");

		board.setEndPoint(new Coordinate((int)(Math.random()*boardsize) , (int)(Math.random()*boardsize)));
		board.setStartingPoint(new Coordinate((int)(Math.random()*boardsize), (int)(Math.random()*boardsize)));
		board.randomize(70);
		
		if (board.hasPath(true))
			System.out.println("Found the path!!!");
		else
			System.out.println("Path not found...");
	}

}
