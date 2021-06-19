/**
 * 
 */
package br.com.fernandoalmeida.pathfinder;

/**
 * @author pr3st00
 *
 */
public class CelElement {

	private final static int STARTING_POINT = 0;
	private final static int NORMAL_POINT = 1;
	private final static int END_POINT = 2;
	private boolean isSearchable;
	private boolean isSelected = false;
	private boolean isPartofCurrentPath = false;
	private boolean hasBeenUsed = false;
	private int type = NORMAL_POINT;

	public CelElement() {
		isSearchable = false;
	}

	public CelElement(boolean searchable) {
		isSearchable = searchable;
	}

	public void selectToBeSearched() {
		this.isSearchable = true;
	}
	
	public void unselectToBeSearched() {
		this.isSearchable = false;
	}

	public boolean hasBeenSelected() { 
		return isSelected;
	}
	
	public void select() {
		isSelected = true;
	}
	
	public void unSelect() {
		isSelected = false;
	}

	public boolean canBeSearched() { 
		return isSearchable;
	}
	
	public boolean isEndPoint() {
		return type == END_POINT;
	}

	public boolean isStartingPoint() {
		return type == STARTING_POINT;
	}
	
	public boolean isNormalPoint() {
		return type == NORMAL_POINT;
	}

	public void setAsEndPoint() {
		type = END_POINT;
		isSearchable = true;
	}

	public void setAsStartPoint() {
		type = STARTING_POINT;
		isSearchable = true;
	}
	
	public void setAsNormalPoint() {
		type = NORMAL_POINT;
	}

	public String toString() {
		switch (type) {
			case STARTING_POINT: {
				return "S";
			}
			case END_POINT: {
				return "E";
			}
			default: {
				if (this.isPartofCurrentPath()) return "*";
				if (this.canBeSearched())       return "O";
				else return " ";
			}
		}
	}

	public boolean isPartofCurrentPath() {
		return isPartofCurrentPath;
	}

	public void setasPartofCurrentPath(boolean isPartofCurrentPath) {
		this.isPartofCurrentPath = isPartofCurrentPath;
	}

	public boolean hasBeenUsed() {
		return hasBeenUsed;
	}

	public void markAsUsed() {
		this.hasBeenUsed = true;
	}
}
