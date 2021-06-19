package br.com.fernandoalmeida.pathfinder;

public class TextBoard extends BoardElement {

	TextBoard(int size) {
		super(size);
	}

	public void draw() {
		
		for (int x=0;x<this.getBoardSize();x++) 
			System.out.print(" _");
		System.out.println();
		for (int y=0;y<this.getBoardSize();y++) {
			for (int x=0;x<this.getBoardSize();x++) {
				System.out.print("|"+this.getCel(new Coordinate(x, y)).toString());
				if (x == this.getBoardSize()-1) System.out.print("|");
			}
			if (y < this.getBoardSize()) System.out.println();
		}
		for (int x=0;x<this.getBoardSize();x++) 
			System.out.print(" -");
		System.out.println("\n\n");

	}

	public void redraw(long delay) {
		
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		draw();
		
	}

	public void findPathEndingEvent(boolean success) {
		System.out.println("Processing finished.");
	}

}
