import java.util.ArrayList;

/**
 * Represents an Abstract Hint, which can be a Hit or Exit
 */
public abstract class AHint {
    public final static String t = "(A | ~A)";
    public final static String nil = "(A & ~A)";

    protected int boardDim;
    protected Posn position;
    protected Direction dir;

	/**
	 * Constructs an AHint using the board dimensions, current position, and the direction.
	 *
	 * @param boardDim dimensions of board
	 * @param p current position
	 * @param dir direction of travel
	 */
    AHint(int boardDim, Posn p, Direction dir) {
        this.setBoardDim(boardDim);
        this.position = p;
        this.dir = dir;
    }

    /**
     * Gets the dimensions of the board.
     *
     * @return the board dimensions
     */
    public int getBoardDim() {
        return boardDim;
    }

    /**
     * Sets the boards dimensions. The board is always a square.
     *
     * @param boardDim dimensions of board
     */
    public void setBoardDim(int boardDim) {
        this.boardDim = boardDim;
    }

    /**
     * Checks for any special starting conditions and revursively generates the characteristic expression for this board.
     *
     * @return the boolean expression
     */
    public abstract String generate();

    /**
     * Helper for generate() that keeps track of hints that have already been checked to prevent cycles
     *
     * @param checked List of all checked cells
     * @return the boolean expression
     */
    public abstract String generate(ArrayList<AHint> checked);

    /**
     * Determines if the given position is off the board this hint is on.
     *
     * @param position - position to be checked
     * @return whether or not the position is valid
     */
    public boolean outOfBounds(Posn position) {
        return position.getX() <= -1
                || position.getY() <= -1
                || position.getX() >= this.boardDim
                || position.getY() >= this.boardDim;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof AHint)) {
            return false;
        }

        //two hints are equal iff they have the same position and direction. 
        AHint other1 = (AHint) other;
        return other1.position.getX() == this.position.getX()
                && other1.position.getY() == this.position.getY()
                && other1.dir == this.dir;
    }
}
