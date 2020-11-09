import java.util.ArrayList;

/**
 * This class represents a ray that exits the board.
 */
public class Exit extends AHint {
    Posn startPos;
    Posn endPos;

    /**
     * Constructs an Exit Hint expected to exit the board at endPos. Used internally
     * when generating the boolean encoding
     *
     * @param boardDim dimensions of board
     * @param p        current position
     * @param dir      direction of travel
     * @param startPos starting position
     * @param endPos   ending position
     */
    Exit(int boardDim, Posn p, Direction dir, Posn startPos, Posn endPos) {
	super(boardDim, p, dir);
	this.startPos = startPos;
	this.endPos = endPos;
    }

    /**
     * Initializes an exit hint, setting current position to be the given startPos.
     * Eases initialization for testing.
     *
     * @param boardDim dimensions of board
     * @param dir      direction of travel
     * @param startPos starting position
     * @param endPos   ending position
     */
    Exit(int boardDim, Posn startPos, Direction dir, Posn endPos) {
	super(boardDim, startPos, dir);
	this.startPos = startPos;
	this.endPos = endPos;
    }

    // Generate the boolean expression that determines whether or not this Exit hint
    // is satisfied given some set of atoms (entering from the border--Section 3.5)
    public String generate() {
	// Corner case: if the CW atom is out of bounds,
	if (this.outOfBounds(this.dir.atomCW(this.startPos))) {
	    // then if this Exit is a reflection, there must be a CCW atom and not a hit
	    // atom
	    if (this.startPos.equals(this.endPos)) {
		return String.format("(P%d%d & ~P%d%d)", 
			this.dir.atomCCW(this.startPos).getX(),
			this.dir.atomCCW(this.startPos).getY(), 
			this.dir.getNextPosn(this.startPos).getX(),
			this.dir.getNextPosn(this.startPos).getY());
	    }
	    // and if this Exit is not a reflection, there must neither be a CCW atom nor a
	    // hit atom
	    else {
		return String.format("(~P%d%d) & (~P%d%d ", 
			this.dir.atomCCW(this.startPos).getX(),
			this.dir.atomCCW(this.startPos).getY(),
			this.dir.getNextPosn(this.startPos).getX(),
			this.dir.getNextPosn(this.startPos).getY()) 
			+ " & ("
			+ new Exit(
				this.boardDim, 
				this.dir.getNextPosn(this.startPos), 
				this.dir, 
				this.startPos,
				this.endPos)
			.generate(new ArrayList<>())
			+ "))";
	    }
	}
	// Corner case: if the CCW atom is out of bounds,
	else if (this.outOfBounds(this.dir.atomCCW(this.startPos))) {
	    // then if this Exit is a reflection, there must be a CW atom and not a hit atom
	    if (this.startPos.equals(this.endPos)) {
		return String.format("(P%d%d & ~P%d%d)", 
			this.dir.atomCW(this.startPos).getX(),
			this.dir.atomCW(this.startPos).getY(), 
			this.dir.getNextPosn(this.startPos).getX(),
			this.dir.getNextPosn(this.startPos).getY());
	    }
	    // and if this Exit is not a reflection, there must neither be a CW atom nor a
	    // hit atom
	    else {
		return String.format("(~P%d%d) & (~P%d%d ", 
			this.dir.atomCW(this.startPos).getX(),
			this.dir.atomCW(this.startPos).getY(), 
			this.dir.getNextPosn(this.startPos).getX(),
			this.dir.getNextPosn(this.startPos).getY()) 
			+ " & ("
			+ new Exit(
				this.boardDim, 
				this.dir.getNextPosn(this.startPos),
				this.dir, this.startPos,
				this.endPos)
			.generate(new ArrayList<>())
			+ "))";
	    }
	}
	// Normal entry case:
	else {
	    // If this Exit is a reflection,
	    if (this.startPos.equals(this.endPos)) {
		// then there must not be a hit atom, and there could either be a CW or CCW atom
		// or a reflection later
		return String.format("~P%d%d & ((P%d%d | P%d%d) | ", 
			this.dir.getNextPosn(this.startPos).getX(),
			this.dir.getNextPosn(this.startPos).getY(), 
			this.dir.atomCW(this.startPos).getX(),
			this.dir.atomCW(this.startPos).getY(), 
			this.dir.atomCCW(this.startPos).getX(),
			this.dir.atomCCW(this.startPos).getY())
			+ new Exit(
				this.boardDim, 
				this.dir.getNextPosn(this.startPos), 
				this.dir, 
				this.startPos,
				this.endPos)
			.generate(new ArrayList<>())
			+ ")";
	    }
	    // and if this Exit is not a reflection,
	    else {
		// then there cannot be any border reflecting atoms or hit atoms, and there must
		// be an exit later
		return String.format("(~P%d%d & ~P%d%d) & (~P%d%d & ", 
			this.dir.atomCW(this.startPos).getX(),
			this.dir.atomCW(this.startPos).getY(), 
			this.dir.atomCCW(this.startPos).getX(),
			this.dir.atomCCW(this.startPos).getY(),
			this.dir.getNextPosn(this.startPos).getX(),
			this.dir.getNextPosn(this.startPos).getY())
			+ new Exit(
				this.boardDim,
				this.dir.getNextPosn(this.startPos),
				this.dir, 
				this.startPos,
				this.endPos)
			.generate(new ArrayList<>())
			+ ")";
	    }
	}
    }

    // Continue generating the expression for a ray that has already entered the
    // board (Section 3.3)
    public String generate(ArrayList<AHint> checked) {
	if (checked.contains(this)) {
	    return AHint.nil;
	}

	checked.add(this);

	// get the position of an atom that would deflect the ray clockwise
	Posn atomCW = this.dir.atomCW(this.position);
	// get the position of an atom that would deflect the ray counterclockwise
	Posn atomCCW = this.dir.atomCCW(this.position);

	// First check the border cases (Section 3.6): towards the border, going
	// clockwise along the border, and going counterclockwise along the border
	// (Section 3.6)
	// If the ray is about to exit the board,
	if (this.outOfBounds(atomCCW) && this.outOfBounds(atomCW)) {
	    // then if this ray is about to exit from the correct end position,
	    if (this.dir.getNextPosn(this.position).equals(endPos)) {
		// there must be no atom blocking its exit at this position
		return String.format("(~P%d%d)",
			this.position.getX(), 
			this.position.getY());
	    }
	    // and if this ray is about to exit from the wrong position,
	    else
		// the hint is unsatisfied.
		return AHint.nil;
	}
	// If the ray would exit on a CCW deflection,
	else if (this.outOfBounds(atomCW)) {
	    // then if this ray would exit from the correct end position upon CCW
	    // deflection,
	    if (this.dir.nextCounterClockwiseDirection().getNextPosn(this.position).equals(endPos)) {
		// there must be a CCW deflecting atom
		return String.format("(P%d%d)",
			atomCCW.getX(),
			atomCCW.getY());
	    }
	    // and if this ray would exit from the wrong end position upon CCW deflection,
	    else {
		// there must not be a CCW deflecting atom here and a proper exit later
		return String.format("(~P%d%d & ~P%d%d) & (", 
			atomCCW.getX(),
			atomCCW.getY(),
			this.dir.getNextPosn(this.position).getX(), 
			this.dir.getNextPosn(this.position).getY())
			+ new Exit(
				this.boardDim,
				this.dir.getNextPosn(this.position),
				this.dir,
				this.startPos,
				this.endPos)
			.generate(checked)
			+ ")";
	    }
	}
	// If the ray would exit on a CW deflection,
	else if (this.outOfBounds(atomCCW)) {
	    // then if this ray would exit from the correct end position upon CW deflection,
	    if (this.dir.nextClockwiseDirection().getNextPosn(this.position).equals(endPos)) {
		// there must be a CW deflecting atom
		return String.format("(P%d%d)",
			atomCW.getX(),
			atomCW.getY());
	    }
	    // and if this ray would exit from the wrong end position upon CW deflection,
	    else {
		// there must not be a CW deflecting atom here and a proper exit later
		return String.format("(~P%d%d & ~P%d%d) & (",
			atomCW.getX(),
			atomCW.getY(),
			this.dir.getNextPosn(this.position).getX(),
			this.dir.getNextPosn(this.position).getY())
			+ new Exit(
				this.boardDim, 
				this.dir.getNextPosn(this.position),
				this.dir, 
				this.startPos,
				this.endPos)
			.generate(checked)
			+ ")";
	    }
	}
	// If the ray is somewhere in the middle of the board, then check that there is
	// no hit atom here and there is a correct exit after the ray continues in the
	// deflected direction.
	else {
	    return String.format("(~P%d%d & (",
		    this.dir.getNextPosn(this.position).getX(),
		    this.dir.getNextPosn(this.position).getY())
		    // Case 1: no deflection
		    + String.format("((~P%d%d & ~P%d%d) => ", 
			    atomCW.getX(),
			    atomCW.getY(),
			    atomCCW.getX(),
			    atomCCW.getY())
		    + new Exit(
			    this.boardDim,
			    this.dir.getNextPosn(this.position), 
			    this.dir, 
			    this.startPos,
			    this.endPos)
		    .generate(new ArrayList<>(checked))
		    + ")"
		    // Case 2: clockwise deflection
		    + String.format(" & ((P%d%d & ~P%d%d) => ",
			    atomCW.getX(),
			    atomCW.getY(),
			    atomCCW.getX(),
			    atomCCW.getY())
		    + new Exit(
			    this.boardDim,
			    this.dir.nextClockwiseDirection().getNextPosn(this.position),
			    this.dir.nextClockwiseDirection(), 
			    this.startPos,
			    this.endPos)
		    .generate(new ArrayList<>(checked))
		    + ")"
		    // Case 3: counterclockwise deflection
		    + String.format(" & ((~P%d%d & P%d%d) => ",
			    atomCW.getX(), 
			    atomCW.getY(),
			    atomCCW.getX(),
			    atomCCW.getY())
		    + new Exit(
			    this.boardDim,
			    this.dir.nextCounterClockwiseDirection().getNextPosn(this.position),
			    this.dir.nextCounterClockwiseDirection(),
			    this.startPos,
			    this.endPos)
		    .generate(new ArrayList<>(checked))
		    + ")"
		    // Case 4: reflection
		    + String.format(" & ((P%d%d & P%d%d) => ",
			    atomCW.getX(),
			    atomCW.getY(), 
			    atomCCW.getX(),
			    atomCCW.getY())
		    + ((startPos.equals(endPos)) ? AHint.t + ")" : AHint.nil + ")") + "))";
	}
    }
}