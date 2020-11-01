/**
 * This class represents a posistion with x and y components.
 */
public class Posn {
	private int x; 
	private int y;

	/**
	 * Constructs a Posn with x and y.
	 *
	 * @param x x of position
	 * @param y y of position
	 */
	Posn(int x, int y) {
		this.x = x; 
		this.y = y;
	}

	/**
	 * Gets the x value of the position.
	 *
	 * @return the x value
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x value of the position.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y value of the position.
	 *
	 * @return the y value
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y value of the position.
	 */
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public boolean equals(Object other) {
		if(this == other)
			return true; 
		
		if(!(other instanceof Posn))
			return false; 
		
		return this.x == ((Posn) other).getX() && this.y == ((Posn) other).getY();  
	}

}
