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
     * Initializes starting conditions and generates the rest of the expressions.
     *
     * @return the boolean expression
     */
    public abstract String generate();

    /**
     * Generates all possible outcomes as a boolean expression.
     *
     * @param checked List of all checked cells
     * @return the boolean expression
     */
    public abstract String generate(ArrayList<AHint> checked);

    /**
     * Gets the next position according to the current position and the direction.
     *
     * @return the next position
     */
    public Posn getNextPosn() {
        return dir.getNextPosn(this.position);
    }

    /**
     * Determines if the given position is off the board.
     *
     * @param position position to be checked
     * @return whether or not the position is valid
     */
    public boolean outOfBounds(Posn position) {
        return position.getX() == -1
                || position.getY() == -1
                || position.getX() == this.boardDim
                || position.getY() == this.boardDim;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Hit)) {
            return false;
        }

        AHint other1 = (AHint) other;
        return other1.position.getX() == this.position.getX()
                && other1.position.getY() == this.position.getY()
                && other1.dir == this.dir;
    }
}
