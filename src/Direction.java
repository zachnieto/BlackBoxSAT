/**
 * This enum represents the 4 cardinal directions that a ray can travel in.
 */
public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    /**
     * Returns the direction directly clockwise of the current.
     *
     * @return the next direction clockwise
     * @throws IllegalArgumentException if the current direction is invalid
     */
    public Direction nextClockwiseDirection() {
        switch (this) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
        }

        throw new IllegalArgumentException("illegal direction: " + this);
    }

    /**
     * Returns the direction directly counterclockwise of the current.
     *
     * @return the next direction counterclockwise
     * @throws IllegalArgumentException if the current direction is invalid
     */
    public Direction nextCounterClockwiseDirection() {
        switch (this) {
            case NORTH:
                return WEST;
            case EAST:
                return NORTH;
            case SOUTH:
                return EAST;
            case WEST:
                return SOUTH;
        }

        throw new IllegalArgumentException("illegal direction: " + this);
    }

    /**
     * Returns the direction opposite of the current.
     *
     * @return the opposite direction
     * @throws IllegalArgumentException if the current direction is invalid
     */
    public Direction nextDoubleDirection() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
        }

        throw new IllegalArgumentException("illegal direction: " + this);
    }

    /**
     * Returns the position of the atom causing a deflection clockwise.
     *
     * @param cur position of the ray being deflected.
     * @return the position of the atom
     * @throws IllegalArgumentException if the direction is not valid
     */
    public Posn atomCW(Posn cur) {
        switch (this) {
            case NORTH:
                return new Posn(cur.getX() - 1, cur.getY() - 1);
            case EAST:
                return new Posn(cur.getX() + 1, cur.getY() - 1);
            case SOUTH:
                return new Posn(cur.getX() + 1, cur.getY() + 1);
            case WEST:
                return new Posn(cur.getX() - 1, cur.getY() + 1);


        }
        throw new IllegalArgumentException("illegal direction: " + this);

    }

	/**
	 * Returns the position of the atom causing a deflection counterclockwise.
	 *
	 * @param cur position of the ray being deflected.
	 * @throws IllegalArgumentException if the direction is not valid
	 * @return the position of the atom
	 */
    public Posn atomCCW(Posn cur) {
        switch (this) {
            case NORTH:
                return new Posn(cur.getX() + 1, cur.getY() - 1);
            case EAST:
                return new Posn(cur.getX() + 1, cur.getY() + 1);
            case SOUTH:
                return new Posn(cur.getX() - 1, cur.getY() + 1);
            case WEST:
                return new Posn(cur.getX() - 1, cur.getY() - 1);
        }
        throw new IllegalArgumentException("illegal direction: " + this);
    }

	/**
	 * Returns the next position according to the current position and the direction of travel.
	 *
	 * @param cur the current position
	 * @throws IllegalArgumentException if the direction is not valid
	 * @return the next position
	 */
    public Posn getNextPosn(Posn cur) {
        switch (this) {
            case NORTH:
                return new Posn(cur.getX(), cur.getY() - 1);
            case EAST:
                return new Posn(cur.getX() + 1, cur.getY());
            case SOUTH:
                return new Posn(cur.getX(), cur.getY() + 1);
            case WEST:
                return new Posn(cur.getX() - 1, cur.getY());
        }
        throw new IllegalArgumentException("illegal direction: " + this);

    }
}
