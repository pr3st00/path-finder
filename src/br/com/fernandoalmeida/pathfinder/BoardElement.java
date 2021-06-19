package br.com.fernandoalmeida.pathfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class BoardElement implements Cloneable {

	private CelElement[][] cels;
	private CelElement currentCel;
	private int boardSize = 0;
	private Coordinate currentCoordinate,destinationCoordinate,startingCoordinate;
	private boolean hasEndPoint;
	private boolean hasStartingPoint;
	private static boolean foundpath;
	public static int GOING_UP = 1, GOING_DOWN = 2, GOING_LEFT = 3, GOING_RIGHT = 4;
	private int direction = 0,  redrawDelay = 350;

	public abstract void draw();
	public abstract void redraw(long delay);
	public abstract void findPathEndingEvent(boolean success);

	BoardElement(int size) {
		boardSize = size;
		hasEndPoint = false;
		hasStartingPoint = false;
		foundpath = false;
		currentCoordinate = new Coordinate(0,0);
		cels = new CelElement[size][size];
		initialize();
	}
	
	BoardElement(int size, int redrawDelay) {
		this.redrawDelay = redrawDelay;
		boardSize = size;
		hasEndPoint = false;
		hasStartingPoint = false;
		foundpath = false;
		currentCoordinate = new Coordinate(0,0);
		cels = new CelElement[size][size];
		initialize();
	}

	public int getBoardSize() {
		return boardSize;
	}

	private boolean isValidCoordinate(Coordinate where) {
		return (where.getX() < boardSize && where.getX() >= 0 && where.getY() < boardSize && where.getY() >= 0);
	}

	private List<Coordinate> getAvailableCoordinatesFromHere(Coordinate c, boolean sort) {
		List<Coordinate> paths = new ArrayList<Coordinate>();

		if ( isValidCoordinate(new Coordinate(c.getX()+1,c.getY()))              &&
				getCel(new Coordinate(c.getX()+1,c.getY())).canBeSearched()      &&
				!getCel(new Coordinate(c.getX()+1,c.getY())).hasBeenSelected()   &&
				!getCel(new Coordinate(c.getX()+1,c.getY())).hasBeenUsed()    	 &&
				!getCel(new Coordinate(c.getX()+1,c.getY())).isPartofCurrentPath()
		) 
			paths.add(new Coordinate(c.getX()+1,c.getY(),this));

		if ( isValidCoordinate(new Coordinate(c.getX(),c.getY()+1))               &&
				getCel(new Coordinate(c.getX(),c.getY()+1)).canBeSearched()       &&
				!getCel(new Coordinate(c.getX(),c.getY()+1)).hasBeenSelected()    &&
				!getCel(new Coordinate(c.getX(),c.getY()+1)).hasBeenUsed()    	  &&
				!getCel(new Coordinate(c.getX(),c.getY()+1)).isPartofCurrentPath()
		) 
			paths.add(new Coordinate(c.getX(),c.getY()+1,this));

		if ( isValidCoordinate(new Coordinate(c.getX()-1,c.getY()))                &&
				getCel(new Coordinate(c.getX()-1,c.getY())).canBeSearched()        &&
				!getCel(new Coordinate(c.getX()-1,c.getY())).hasBeenSelected()     &&
				!getCel(new Coordinate(c.getX()-1,c.getY())).hasBeenUsed()    	   &&
				!getCel(new Coordinate(c.getX()-1,c.getY())).isPartofCurrentPath()
		) 
			paths.add(new Coordinate(c.getX()-1,c.getY(),this));

		if ( isValidCoordinate(new Coordinate(c.getX(),c.getY()-1))               &&
				getCel(new Coordinate(c.getX(),c.getY()-1)).canBeSearched()       &&
				!getCel(new Coordinate(c.getX(),c.getY()-1)).hasBeenSelected()    &&
				!getCel(new Coordinate(c.getX(),c.getY()-1)).hasBeenUsed()    	  &&
				!getCel(new Coordinate(c.getX(),c.getY()-1)).isPartofCurrentPath()
		) 
			paths.add(new Coordinate(c.getX(),c.getY()-1,this));

		if (sort) Collections.sort(paths);

		return paths;

	}

	public boolean setStartingPoint(Coordinate where) {
		if (!isValidCoordinate(where) || hasStartingPoint)	{
			return false;
		}
		cels[where.getX()][where.getY()].setAsStartPoint();
		this.startingCoordinate=where;
		this.setCurrentCel(where);
		return(hasStartingPoint = true);
	}

	public boolean setEndPoint(Coordinate where) {
		if (!isValidCoordinate(where) || hasEndPoint) {
			return false;
		}
		cels[where.getX()][where.getY()].setAsEndPoint();
		this.destinationCoordinate=where;
		return(hasEndPoint = true);
	}

	public CelElement getCel(Coordinate where) {
		if (!isValidCoordinate(where)) {
			return null;
		}
		else {
			return this.cels[where.getX()][where.getY()];
		}

	}

	private void initialize() {
		for (int x=0;x<this.getBoardSize();x++) {
			for (int y=0;y<this.getBoardSize();y++) {
				cels[x][y] = new CelElement();
			}
		}
	}

	public void setCurrentCel(Coordinate where) {
		if (isValidCoordinate(where)) {
			currentCel = cels[where.getX()][where.getY()];
			currentCel.select();
			if      (where.getX() > currentCoordinate.getX()) direction = GOING_RIGHT;
			else if (where.getX() < currentCoordinate.getX()) direction = GOING_LEFT;
			else if (where.getY() > currentCoordinate.getY()) direction = GOING_UP;
			else if (where.getY() < currentCoordinate.getY()) direction = GOING_DOWN;
			currentCoordinate = where;
		}
	}

	public CelElement getCurrentCel() {
		return currentCel;
	}

	public void randomize(int probability) {
		for (int x=0;x<this.getBoardSize();x++) {
			for (int y=0;y<this.getBoardSize();y++) {
				if (! cels[x][y].isEndPoint() && ! cels[x][y].isStartingPoint() && (Math.random() * 100 < probability) ) {
					cels[x][y].selectToBeSearched();
				}
			}
		}
	}

	public boolean hasPath(boolean sort) {
		boolean pathfound = false;
		pathfound = findPath(sort);
		findPathEndingEvent(pathfound);
		return pathfound;
	}

	private boolean findPath(boolean sort) {

		if (foundpath) return true;

		redraw(redrawDelay);

		// No path
		if (! hasStartingPoint || ! hasEndPoint ) return false;

		// Found the end point!
		if (this.getCurrentCel().isEndPoint()) 
			return (foundpath=true);

		List<Coordinate> availablePaths = getAvailableCoordinatesFromHere(currentCoordinate,sort);
		Iterator<Coordinate> i = availablePaths.iterator();

		while (i.hasNext() && ! foundpath) {

			Coordinate newCoordinate = i.next();

			try {
				BoardElement nextboard = (BoardElement) this.clone();
				nextboard.setCurrentCel(newCoordinate);
				nextboard.getCurrentCel().setasPartofCurrentPath(true);
				nextboard.getCurrentCel().markAsUsed();
				foundpath = foundpath || (nextboard.findPath(sort));
			} catch (CloneNotSupportedException e) {
			}

		}

		if (! foundpath ) {
			getCurrentCel().setasPartofCurrentPath(false);
			redraw(redrawDelay);
		}

		return foundpath;
	}

	public Coordinate getCurrentCoordinate() {
		return currentCoordinate;
	}

	public void setCurrentCoordinate(Coordinate currentCoordinate) {
		this.currentCoordinate = currentCoordinate;
	}

	public Coordinate getDestinationCoordinate() {
		return destinationCoordinate;
	}

	public Coordinate getStartingCoordinate() {
		return startingCoordinate;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
